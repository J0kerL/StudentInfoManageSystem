package com.sims.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程VO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class CourseVO {
    private Long id;
    private String code;
    private String name;
    private BigDecimal credit;
    private Integer hours;
    private String description;
    private String teacher;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
