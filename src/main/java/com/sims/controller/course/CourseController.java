package com.sims.controller.course;

import com.sims.model.dto.course.CourseDTO;
import com.sims.model.dto.course.PageQueryCourseDTO;
import com.sims.model.vo.CourseVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理控制器
 *
 * @author Diamond
 * @create 2025-11-28
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 添加课程
     */
    @PostMapping("/add")
    public Result<CourseVO> addCourse(@RequestBody CourseDTO courseDTO) {
        return Result.success(courseService.addCourse(courseDTO));
    }

    /**
     * 批量删除课程
     */
    @DeleteMapping("/delete")
    public Result<String> deleteCourses(@RequestBody List<Long> ids) {
        courseService.deleteCourses(ids);
        return Result.success(null);
    }

    /**
     * 修改课程信息
     */
    @PutMapping("/update")
    public Result<String> updateCourse(@RequestBody CourseDTO courseDTO) {
        courseService.updateCourse(courseDTO);
        return Result.success(null);
    }

    /**
     * 分页查询课程
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryCourseDTO pageQueryCourseDTO) {
        PageResult pageResult = courseService.page(pageQueryCourseDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询课程
     */
    @GetMapping("/{id}")
    public Result<CourseVO> getCourseById(@PathVariable Long id) {
        return Result.success(courseService.getCourseById(id));
    }
}
