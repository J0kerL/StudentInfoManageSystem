package com.sims.model.dto.teacher;

import lombok.Data;

import java.io.Serializable;

/**
 * 教师分页查询DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class PageQueryTeacherDTO implements Serializable {
    // 工号
    private String employeeNumber;
    // 姓名
    private String name;
    // 所属部门
    private String department;
    // 职称
    private String title;
    // 状态
    private String status;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
