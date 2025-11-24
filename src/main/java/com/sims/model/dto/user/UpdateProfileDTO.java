package com.sims.model.dto.user;

import lombok.Data;

/**
 * @author Diamond
 * @create 2025-11-24 11:42
 */
@Data
public class UpdateProfileDTO {
    private String username;
    private String realName;
    private String phone;
    private String email;
}
