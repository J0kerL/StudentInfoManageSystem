package com.sims.mapper;

import com.sims.model.dto.student.StudentDTO;
import com.sims.model.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-25 12:11
 */
public interface StudentMapper {

    @Select("select *from student where student_number = #{studentNumber}")
    Student findByStudentNumber(String studentNumber);

    @Insert("insert into student(student_number, name, gender, birth_date, phone, email, address, enrollment_date, major_id, class_id, status, create_time, update_time) " +
            "values(#{studentNumber}, #{name}, #{gender}, #{birthDate}, #{phone}, #{email}, #{address}, #{enrollmentDate}, #{majorId}, #{classId}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(StudentDTO studentDTO);

    void deleteByIds(@Param("ids") List<Long> ids);
}
