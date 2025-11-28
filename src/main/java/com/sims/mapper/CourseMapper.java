package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.course.CourseDTO;
import com.sims.model.dto.course.PageQueryCourseDTO;
import com.sims.model.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课程Mapper
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface CourseMapper {

    @Select("select count(*) from course where id = #{courseId}")
    int existsById(Long courseId);

    @Insert("insert into course(code, name, credit, hours, description, teacher, create_time, update_time) " +
            "values(#{code}, #{name}, #{credit}, #{hours}, #{description}, #{teacher}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(CourseDTO courseDTO);

    @Select("select * from course where id = #{id}")
    Course findById(Long id);

    @Select("select * from course where code = #{code}")
    Course findByCode(String code);

    @Select("select * from course where name = #{name}")
    Course findByName(String name);

    @Select("select count(*) from course where code = #{code}")
    int existsByCode(String code);

    @Select("select count(*) from course where name = #{name}")
    int existsByName(String name);

    void deleteByIds(@Param("ids") List<Long> ids);

    void update(CourseDTO courseDTO);

    Page<Course> page(PageQueryCourseDTO pageQueryCourseDTO);
}
