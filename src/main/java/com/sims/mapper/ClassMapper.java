package com.sims.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author Diamond
 * @create 2025-11-26 11:27
 */
public interface ClassMapper {

    @Select("select grade from class where id = #{classId}")
    String findGradeById(Long classId);

    @Select("select count(*) from class where id = #{classId}")
    int existsById(Long classId);
}
