package com.sims.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-11-27 17:28
 */
@Data
public class MajorVO {

    private Long id;
    private String name;
    private String code;
    private String description;
    private String college;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
