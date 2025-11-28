package com.sims.service;

import com.sims.model.dto.user.*;
import com.sims.model.vo.UserVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-12 16:21
 */
public interface UserService {
    String login(LoginRequest loginRequest);

    PageResult page(PageQueryUserDTO pageQueryUserDTO);

    UserVO register(RegisterRequest registerRequest);

    void logout(String token);

    UserVO profile();

    void updateProfile(UpdateProfileDTO updateProfileDTO);

    UserVO addUser(UserDTO userDTO);

    void deleteUser(List<Long> ids);

    void updateUser(UpdateUserDTO updateUserDTO);

    UserVO getUserById(Long id);

    /**
     * 修改密码（已登录用户）
     *
     * @param changePasswordDTO
     */
    void changePassword(ChangePasswordDTO changePasswordDTO);

    /**
     * 重置密码（忘记密码场景）
     *
     * @param resetPasswordDTO
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}