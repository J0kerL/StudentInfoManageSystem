package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.score.PageQueryScoreDTO;
import com.sims.model.dto.score.ScoreDTO;
import com.sims.model.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-26 10:33
 */
public interface ScoreMapper {

    void deleteByStudentIds(@Param("ids") List<Long> ids);

    @Select("select count(*) from score where course_id = #{courseId}")
    int countByCourseId(Long courseId);

    @Select("select count(*) from score where student_id = #{studentId}")
    int countByStudentId(Long studentId);

    @Select("select count(*) from score where id = #{id}")
    int existsById(Long id);

    @Insert("insert into score(student_id, course_id, score, grade, semester, exam_date, create_time, update_time) " +
            "values(#{studentId}, #{courseId}, #{score}, #{grade}, #{semester}, #{examDate}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ScoreDTO scoreDTO);

    @Select("select * from score where id = #{id}")
    Score findById(Long id);

    @Select("select * from score where student_id = #{studentId} and course_id = #{courseId} and semester = #{semester}")
    Score findByStudentCourseAndSemester(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("semester") String semester);

    void deleteByIds(@Param("ids") List<Long> ids);

    void update(ScoreDTO scoreDTO);

    Page<Score> page(PageQueryScoreDTO pageQueryScoreDTO);
}
