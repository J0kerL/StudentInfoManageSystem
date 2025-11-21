package com.sims.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-18 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

    //总记录数
    private long total;

    //当前页数据集合
    private List<?> records;
}
