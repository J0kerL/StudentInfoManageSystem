package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.constant.Status;
import com.sims.constant.UserType;
import com.sims.context.UserContext;
import com.sims.exception.BusinessException;
import com.sims.mapper.UserMapper;
import com.sims.model.dto.user.*;
import com.sims.model.entity.User;
import com.sims.model.vo.UserVO;
import com.sims.result.PageResult;
import com.sims.service.TokenBlacklistService;
import com.sims.service.UserService;
import com.sims.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-12 16:21
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private TokenBlacklistService tokenBlacklistService;

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @Override
    public String login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 防御性校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BusinessException(400, "用户名或密码不能为空");
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new BusinessException(401, "密码错误");
        }
        if (user.getStatus() == Status.INACTIVE) {
            throw new BusinessException(403, "账号已锁定，请联系管理员");
        }

        // 生成JWT token
        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }

    /**
     * 分页查询
     *
     * @param pageQueryUserDTO
     * @return
     */
    @Override
    public PageResult page(PageQueryUserDTO pageQueryUserDTO) {
        PageHelper.startPage(pageQueryUserDTO.getPage(), pageQueryUserDTO.getPageSize());
        Page<User> page = userMapper.page(pageQueryUserDTO);
        long total = page.getTotal();
        List<User> result = page.getResult();
        result.forEach(user -> user.setPassword("******"));
        return new PageResult(total, result);
    }

    /**
     * 用户注册
     *
     * @param registerRequest
     */
    @Override
    public UserVO register(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String realName = registerRequest.getRealName();
        String phone = registerRequest.getPhone();
        String email = registerRequest.getEmail();

        // 防御性校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }

        // 校验用户名是否已存在
        if (userMapper.findByUsername(username) != null) {
            throw new BusinessException(400, "用户名已存在");
        }

        // 创建新用户
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        // 加密密码
        userDTO.setPassword(BCrypt.hashpw(password));
        userDTO.setRealName(realName);
        userDTO.setPhone(phone);
        userDTO.setEmail(email);

        // 默认普通用户类型
        userDTO.setUserType(UserType.TEACHER);
        // 默认激活状态
        userDTO.setStatus(Status.ACTIVE);

        userMapper.insert(userDTO);

        // 将UserDTO直接转换为UserVO
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userDTO, userVO);
        userVO.setPassword("******");
        return userVO;
    }

    /**
     * 退出登录
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        log.info("用户退出登录，token：{}", token);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(400, "token不能为空");
        }

        // 计算token剩余有效期
        Long remainingTime = jwtUtil.getTokenRemainingTime(token);
        if (remainingTime <= 0) {
            throw new BusinessException(400, "token已过期");
        }

        // 将token加入黑名单，过期时间与token的剩余有效期一致
        tokenBlacklistService.addToBlacklist(token, remainingTime);
    }

    /**
     * 查看个人信息
     *
     * @return
     */
    @Override
    public UserVO profile() {
        Long userId = UserContext.getUserId();
        log.info("查看用户信息，用户ID：{}", userId);
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 更新个人信息
     *
     * @param updateProfileDTO
     */
    @Override
    public void updateProfile(UpdateProfileDTO updateProfileDTO) {
        Long currentUserId = UserContext.getUserId();
        log.info("用户：{}正在修改个人信息...", currentUserId);

        // 查询当前用户
        User user = userMapper.findById(currentUserId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }

        // 如果用户名被修改，检查新用户名是否已被其他用户占用
        if (updateProfileDTO.getUsername() != null &&
                !updateProfileDTO.getUsername().equals(user.getUsername())) {
            User existUser = userMapper.findByUsername(updateProfileDTO.getUsername());
            if (existUser != null && !existUser.getId().equals(currentUserId)) {
                throw new BusinessException(400, "用户名已被占用");
            }
        }

        // 复制属性并更新
        BeanUtil.copyProperties(updateProfileDTO, user);
        // updateTime由MyBatis拦截器自动填充
        userMapper.updateProfile(user, currentUserId);

        log.info("用户：{}个人信息更新成功", currentUserId);
    }

    /**
     * 添加用户
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserVO addUser(UserDTO userDTO) {
        // 必填字段校验
        String username = userDTO.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(400, "用户名不能为空");
        }
        if (StringUtils.isBlank(userDTO.getPassword())) {
            throw new BusinessException(400, "密码不能为空");
        }
        if (userDTO.getUserType() == null) {
            throw new BusinessException(400, "用户类型不能为空");
        }
        // 唯一性校验
        if (userMapper.findByUsername(username) != null) {
            throw new BusinessException(400, "用户名已存在");
        }
        // 加密密码
        userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword()));
        // 默认激活状态
        if (userDTO.getStatus() == null) {
            userDTO.setStatus(Status.ACTIVE);
        }
        userMapper.insert(userDTO);

        // 将UserDTO直接转换为UserVO
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userDTO, userVO);
        userVO.setPassword("******");
        return userVO;
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     */
    @Override
    public void deleteUser(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "用户ID列表不能为空");
        }
        Long currentUserId = UserContext.getUserId();
        if (ids.contains(currentUserId)) {
            throw new BusinessException(401, "不能删除自己");
        }
        log.info("批量删除用户，用户ID列表：{}", ids);
        userMapper.deleteByIds(ids);
    }

    /**
     * 修改用户信息
     *
     * @param updateUserDTO
     */
    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        Long id = updateUserDTO.getId();
        String username = updateUserDTO.getUsername();
        log.info("更新用户信息，用户ID：{}", id);
        if (id == null) {
            throw new BusinessException(400, "用户ID不能为空");
        }
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }
        // 如果用户名被修改，检查新用户名是否已被其他用户占用
        if (username != null && !username.equals(user.getUsername())) {
            User existUser = userMapper.findByUsername(username);
            if (existUser != null && !existUser.getId().equals(id)) {
                throw new BusinessException(400, "用户名已被占用");
            }
        }
        // 加密密码
        if (StringUtils.isNotBlank(updateUserDTO.getPassword())) {
            updateUserDTO.setPassword(BCrypt.hashpw(updateUserDTO.getPassword()));
        }
        // updateTime由MyBatis拦截器自动填充
        userMapper.update(updateUserDTO);
    }
    
    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    @Override
    public UserVO getUserById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
    }
    User user = userMapper.findById(id);
    if (user == null) {
        throw new BusinessException(400, "用户不存在");
    }
    UserVO userVO = new UserVO();
    BeanUtil.copyProperties(user, userVO);
    userVO.setPassword("******");
    return userVO;
}

/**
 * 修改密码（已登录用户）
 *
 * @param changePasswordDTO
 */
@Override
public void changePassword(ChangePasswordDTO changePasswordDTO) {
    Long currentUserId = UserContext.getUserId();
    log.info("用户：{}正在修改密码...", currentUserId);

    // 参数校验
    String oldPassword = changePasswordDTO.getOldPassword();
    String newPassword = changePasswordDTO.getNewPassword();
    String confirmPassword = changePasswordDTO.getConfirmPassword();

    if (StringUtils.isBlank(oldPassword)) {
        throw new BusinessException(400, "旧密码不能为空");
    }
    if (StringUtils.isBlank(newPassword)) {
        throw new BusinessException(400, "新密码不能为空");
    }
    if (StringUtils.isBlank(confirmPassword)) {
        throw new BusinessException(400, "确认密码不能为空");
    }
    if (!newPassword.equals(confirmPassword)) {
        throw new BusinessException(400, "两次输入的新密码不一致");
    }
    if (oldPassword.equals(newPassword)) {
        throw new BusinessException(400, "新密码不能与旧密码相同");
    }

    // 查询当前用户
    User user = userMapper.findById(currentUserId);
    if (user == null) {
        throw new BusinessException(401, "用户不存在");
    }

    // 验证旧密码
    if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
        throw new BusinessException(400, "旧密码错误");
    }

    // 加密新密码并更新
    String encryptedPassword = BCrypt.hashpw(newPassword);
    userMapper.updatePassword(currentUserId, encryptedPassword);

    log.info("用户：{}密码修改成功", currentUserId);
}

/**
 * 重置密码（忘记密码场景）
 *
 * @param resetPasswordDTO
 */
@Override
public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
    log.info("用户正在重置密码，用户名：{}", resetPasswordDTO.getUsername());

    // 参数校验
    String username = resetPasswordDTO.getUsername();
    String email = resetPasswordDTO.getEmail();
    String phone = resetPasswordDTO.getPhone();
    String newPassword = resetPasswordDTO.getNewPassword();
    String confirmPassword = resetPasswordDTO.getConfirmPassword();

    if (StringUtils.isBlank(username)) {
        throw new BusinessException(400, "用户名不能为空");
    }
    if (StringUtils.isBlank(newPassword)) {
        throw new BusinessException(400, "新密码不能为空");
    }
    if (StringUtils.isBlank(confirmPassword)) {
        throw new BusinessException(400, "确认密码不能为空");
    }
    if (!newPassword.equals(confirmPassword)) {
        throw new BusinessException(400, "两次输入的新密码不一致");
    }
    if (StringUtils.isBlank(email) && StringUtils.isBlank(phone)) {
        throw new BusinessException(400, "邮箱或手机号至少填写一项用于身份验证");
    }

    // 身份验证：通过用户名+邮箱或用户名+手机号验证
    User user = null;
    if (StringUtils.isNotBlank(email)) {
        user = userMapper.findByUsernameAndEmail(username, email);
    }
    if (user == null && StringUtils.isNotBlank(phone)) {
        user = userMapper.findByUsernameAndPhone(username, phone);
    }

    if (user == null) {
        throw new BusinessException(400, "用户信息验证失败，请检查用户名和邮箱/手机号是否匹配");
    }

    // 检查账号状态
    if (user.getStatus() == Status.INACTIVE) {
        throw new BusinessException(403, "账号已锁定，请联系管理员");
    }

    // 加密新密码并更新
    String encryptedPassword = BCrypt.hashpw(newPassword);
    userMapper.updatePassword(user.getId(), encryptedPassword);

    log.info("用户：{}密码重置成功", username);
}
}