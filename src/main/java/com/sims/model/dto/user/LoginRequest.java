package com.sims.model.dto.user;

import lombok.Data;

/**
 * @author Diamond
 * @create 2025-11-12 16:17
 */
@Data
public class LoginRequest {

    private String username;
    private String password;

}