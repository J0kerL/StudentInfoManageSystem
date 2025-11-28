package com.sims.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class Course {
    private Long id;
    // 课程代码
    private String code;
    // 课程名称
    private String name;
    // 学分
    private BigDecimal credit;
    // 学时
    private Integer hours;
    // 课程描述
    private String description;
    // 授课教师
    private String teacher;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
