package com.sims.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author Diamond
 * @create 2025-11-26 11:43
 */
public interface MajorMapper {

    @Select("select count(*) from major where id = #{majorId}")
    int existsById(Long majorId);
}
