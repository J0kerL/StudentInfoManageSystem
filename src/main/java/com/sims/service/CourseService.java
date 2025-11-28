package com.sims.service;

import com.sims.model.dto.course.CourseDTO;
import com.sims.model.dto.course.PageQueryCourseDTO;
import com.sims.model.vo.CourseVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * 课程服务接口
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface CourseService {

    /**
     * 添加课程
     */
    CourseVO addCourse(CourseDTO courseDTO);

    /**
     * 批量删除课程
     */
    void deleteCourses(List<Long> ids);

    /**
     * 修改课程信息
     */
    void updateCourse(CourseDTO courseDTO);

    /**
     * 分页查询课程
     */
    PageResult page(PageQueryCourseDTO pageQueryCourseDTO);

    /**
     * 根据ID查询课程
     */
    CourseVO getCourseById(Long id);
}
