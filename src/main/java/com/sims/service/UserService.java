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

    PageResult page(PageQueryDTO pageQueryDTO);

    UserVO register(RegisterRequest registerRequest);

    void logout(String token);

    UserVO profile();

    void updateProfile(UpdateProfileDTO updateProfileDTO);

    UserVO addUser(UserDTO userDTO);

    void deleteUser(List<Long> ids);

    void updateUser(UpdateUserDTO updateUserDTO);
}
