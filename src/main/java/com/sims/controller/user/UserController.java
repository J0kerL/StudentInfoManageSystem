package com.sims.controller.user;

import com.sims.model.dto.user.*;
import com.sims.model.vo.UserVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-12 16:13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody RegisterRequest registerRequest) {
        return Result.success(userService.register(registerRequest));
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return Result.success(token);
    }

    /**
     * 分页查询
     *
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        PageResult pageResult = userService.page(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 退出登录
     *
     * @param request HTTP请求对象
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 从request header中获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.logout(token);
        return Result.success("退出登录成功", null);
    }

    /**
     * 查看个人信息
     *
     * @return
     */
    @GetMapping("/profile")
    public Result<UserVO> profile() {
        return Result.success(userService.profile());
    }

    /**
     * 更新个人信息
     *
     * @param updateProfileDTO
     * @return
     */
    @PutMapping("/updateProfile")
    public Result<String> updateProfile(@RequestBody UpdateProfileDTO updateProfileDTO) {
        userService.updateProfile(updateProfileDTO);
        return Result.success(null);
    }

    /**
     * 添加用户
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/addUser")
    public Result<UserVO> addUser(@RequestBody UserDTO userDTO) {
        return Result.success(userService.addUser(userDTO));
    }

    /**
     * 批量删除系统用户
     *
     * @param ids 用户ID列表
     * @return
     */
    @DeleteMapping("/deleteUser")
    public Result<String> deleteUser(@RequestBody List<Long> ids) {
        userService.deleteUser(ids);
        return Result.success(null);
    }

    /**
     * 修改用户信息
     *
     * @param updateUserDTO
     * @return
     */
    @PutMapping("/updateUser")
    public Result<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(updateUserDTO);
        return Result.success(null);
    }
}