package com.sims.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-11-27 17:22
 */
@Data
public class Major {

    private Long id;

    private String name;

    // 专业代码
    private String code;

    private String description;

    // 所属学院
    private String college;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
