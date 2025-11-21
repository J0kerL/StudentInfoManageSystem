package com.sims.model.entity;

import com.sims.constant.Status;
import com.sims.constant.UserType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-11-12 16:58
 */
@Data
public class User {

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
