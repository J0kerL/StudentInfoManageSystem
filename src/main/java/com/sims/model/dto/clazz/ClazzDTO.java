package com.sims.model.dto.clazz;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 班级DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ClazzDTO {
    private Long id;
    private String name;
    private Long majorId;
    private String grade;
    private String advisor;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
