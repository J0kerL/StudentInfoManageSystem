package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.teacher.PageQueryTeacherDTO;
import com.sims.model.dto.teacher.TeacherDTO;
import com.sims.model.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 教师Mapper
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface TeacherMapper {

    @Select("select count(*) from teacher where id = #{id}")
    int existsById(Long id);

    @Insert("insert into teacher(employee_number, name, gender, phone, email, department, title, hire_date, status, create_time, update_time) " +
            "values(#{employeeNumber}, #{name}, #{gender}, #{phone}, #{email}, #{department}, #{title}, #{hireDate}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TeacherDTO teacherDTO);

    @Select("select * from teacher where id = #{id}")
    Teacher findById(Long id);

    @Select("select * from teacher where employee_number = #{employeeNumber}")
    Teacher findByEmployeeNumber(String employeeNumber);

    @Select("select count(*) from teacher where employee_number = #{employeeNumber}")
    int existsByEmployeeNumber(String employeeNumber);

    void deleteByIds(@Param("ids") List<Long> ids);

    void update(TeacherDTO teacherDTO);

    Page<Teacher> page(PageQueryTeacherDTO pageQueryTeacherDTO);
}
