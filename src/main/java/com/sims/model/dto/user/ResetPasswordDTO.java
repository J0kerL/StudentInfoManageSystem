package com.sims.model.dto.user;

import lombok.Data;

/**
 * 重置密码请求 DTO（忘记密码场景）
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ResetPasswordDTO {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱（用于身份验证）
     */
    private String email;
    
    /**
     * 手机号（用于身份验证）
     */
    private String phone;
    
    /**
     * 新密码
     */
    private String newPassword;
    
    /**
     * 确认新密码
     */
    private String confirmPassword;
}
