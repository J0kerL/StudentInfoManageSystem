package com.sims.controller.teacher;

import com.sims.model.dto.teacher.PageQueryTeacherDTO;
import com.sims.model.dto.teacher.TeacherDTO;
import com.sims.model.vo.TeacherVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师管理控制器
 *
 * @author Diamond
 * @create 2025-11-28
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    /**
     * 添加教师
     */
    @PostMapping("/add")
    public Result<TeacherVO> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        return Result.success(teacherService.addTeacher(teacherDTO));
    }

    /**
     * 批量删除教师
     */
    @DeleteMapping("/delete")
    public Result<String> deleteTeachers(@RequestBody List<Long> ids) {
        teacherService.deleteTeachers(ids);
        return Result.success(null);
    }

    /**
     * 修改教师信息
     */
    @PutMapping("/update")
    public Result<String> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
        teacherService.updateTeacher(teacherDTO);
        return Result.success(null);
    }

    /**
     * 分页查询教师
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryTeacherDTO pageQueryTeacherDTO) {
        PageResult pageResult = teacherService.page(pageQueryTeacherDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询教师
     */
    @GetMapping("/{id}")
    public Result<TeacherVO> getTeacherById(@PathVariable Long id) {
        return Result.success(teacherService.getTeacherById(id));
    }
}
