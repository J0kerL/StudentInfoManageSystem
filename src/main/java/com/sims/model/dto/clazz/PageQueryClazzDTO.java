package com.sims.model.dto.clazz;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级分页查询DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class PageQueryClazzDTO implements Serializable {
    // 班级名称
    private String name;
    // 专业ID
    private Long majorId;
    // 年级
    private String grade;
    // 辅导员
    private String advisor;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
