package com.sims.model.vo;

import com.sims.constant.Status;
import com.sims.constant.UserType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户视图对象，用于向前端返回用户信息
 *
 * @author Diamond
 * @create 2025-11-21 16:21
 */
@Data
public class UserVO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户状态
     */
    private Status status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}