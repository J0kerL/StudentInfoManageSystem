package com.sims.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 班级实体类
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class Clazz {
    private Long id;
    // 班级名称
    private String name;
    // 专业ID
    private Long majorId;
    // 年级
    private String grade;
    // 辅导员
    private String advisor;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
