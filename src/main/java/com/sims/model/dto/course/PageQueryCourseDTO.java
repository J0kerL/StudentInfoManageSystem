package com.sims.model.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程分页查询DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class PageQueryCourseDTO implements Serializable {
    // 课程代码
    private String code;
    // 课程名称
    private String name;
    // 授课教师
    private String teacher;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
