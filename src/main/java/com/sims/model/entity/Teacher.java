package com.sims.model.entity;

import com.sims.constant.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 教师实体类
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class Teacher {
    private Long id;
    // 工号
    private String employeeNumber;
    // 姓名
    private String name;
    // 性别
    private String gender;
    // 联系电话
    private String phone;
    // 电子邮箱
    private String email;
    // 所属部门
    private String department;
    // 职称
    private String title;
    // 入职日期
    private LocalDate hireDate;
    // 状态
    private Status status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
