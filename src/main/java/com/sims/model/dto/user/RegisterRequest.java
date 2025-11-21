package com.sims.model.dto.user;

import lombok.Data;

/**
 * 注册请求 DTO
 * @author Diamond
 * @create 2025-11-18
 */
@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;

}
