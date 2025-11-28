package com.sims.model.dto.major;

import lombok.Data;

import java.io.Serializable;

/**
 * 专业分页查询DTO
 *
 * @author Diamond
 * @create 2025-11-28 09:48
 */
@Data
public class PageQueryMajorDTO implements Serializable {
    // 专业名称
    private String name;
    // 专业代码
    private String code;
    // 所属学院
    private String college;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
