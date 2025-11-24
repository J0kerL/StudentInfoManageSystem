package com.sims.model.dto.user;

import com.sims.constant.Status;
import com.sims.constant.UserType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-11-24 15:54
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private UserType userType;
    private Status status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
