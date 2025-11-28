package com.sims.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成绩实体类
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class Score {
    private Long id;
    // 学生ID
    private Long studentId;
    // 课程ID
    private Long courseId;
    // 成绩分数
    private BigDecimal score;
    // 成绩等级
    private String grade;
    // 学期
    private String semester;
    // 考试日期
    private LocalDate examDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
