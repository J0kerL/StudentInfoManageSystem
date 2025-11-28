package com.sims.model.dto.score;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成绩DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ScoreDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private BigDecimal score;
    private String grade;
    private String semester;
    private LocalDate examDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
