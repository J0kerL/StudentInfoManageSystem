package com.sims.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成绩VO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ScoreVO {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentNumber;
    private Long courseId;
    private String courseName;
    private String courseCode;
    private BigDecimal score;
    private String grade;
    private String semester;
    private LocalDate examDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
