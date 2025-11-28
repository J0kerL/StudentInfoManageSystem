package com.sims.model.dto.user;

import lombok.Data;

/**
 * 修改密码请求 DTO（已登录用户）
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ChangePasswordDTO {
    
    /**
     * 旧密码
     */
    private String oldPassword;
    
    /**
     * 新密码
     */
    private String newPassword;
    
    /**
     * 确认新密码
     */
    private String confirmPassword;
}
