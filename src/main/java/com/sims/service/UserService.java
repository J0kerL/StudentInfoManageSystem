package com.sims.service;

import com.sims.model.dto.user.LoginRequest;
import com.sims.model.dto.user.PageQueryDTO;
import com.sims.model.dto.user.RegisterRequest;
import com.sims.model.dto.user.UpdateProfileDTO;
import com.sims.model.vo.UserVO;
import com.sims.result.PageResult;

/**
 * @author Diamond
 * @create 2025-11-12 16:21
 */
public interface UserService {
    String login(LoginRequest loginRequest);

    PageResult page(PageQueryDTO pageQueryDTO);

    void register(RegisterRequest registerRequest);

    void logout(String token);

    UserVO profile();

    void updateProfile(UpdateProfileDTO updateProfileDTO);
}
