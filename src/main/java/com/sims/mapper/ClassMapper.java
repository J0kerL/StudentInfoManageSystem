package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.clazz.ClazzDTO;
import com.sims.model.dto.clazz.PageQueryClazzDTO;
import com.sims.model.entity.Clazz;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-26 11:27
 */
public interface ClassMapper {

    @Select("select grade from class where id = #{classId}")
    String findGradeById(Long classId);

    @Select("select count(*) from class where id = #{classId}")
    int existsById(Long classId);

    @Insert("insert into class(name, major_id, grade, advisor, create_time, update_time) " +
            "values(#{name}, #{majorId}, #{grade}, #{advisor}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ClazzDTO clazzDTO);

    @Select("select * from class where id = #{id}")
    Clazz findById(Long id);

    @Select("select * from class where name = #{name}")
    Clazz findByName(String name);

    @Select("select count(*) from class where name = #{name}")
    int existsByName(String name);

    void deleteByIds(@Param("ids") List<Long> ids);

    void update(ClazzDTO clazzDTO);

    Page<Clazz> page(PageQueryClazzDTO pageQueryClazzDTO);

    @Select("select count(*) from class where major_id = #{majorId}")
    int countByMajorId(Long majorId);
}
