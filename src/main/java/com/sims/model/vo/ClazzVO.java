package com.sims.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 班级VO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class ClazzVO {
    private Long id;
    private String name;
    private Long majorId;
    private String majorName;
    private String grade;
    private String advisor;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
