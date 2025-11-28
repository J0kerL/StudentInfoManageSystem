package com.sims.model.dto.score;

import lombok.Data;

import java.io.Serializable;

/**
 * 成绩分页查询DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class PageQueryScoreDTO implements Serializable {
    // 学生ID
    private Long studentId;
    // 课程ID
    private Long courseId;
    // 学期
    private String semester;
    // 成绩等级
    private String grade;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
