package com.sims.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-26 10:33
 */
public interface ScoreMapper {

    void deleteByStudentIds(@Param("ids") List<Long> ids);
}
