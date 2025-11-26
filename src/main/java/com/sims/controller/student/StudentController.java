package com.sims.controller.student;

import com.sims.model.dto.student.StudentDTO;
import com.sims.model.vo.StudentVO;
import com.sims.result.Result;
import com.sims.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-25 12:05
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 添加学生
     *
     * @param studentDTO
     * @return
     */
    @PostMapping("/add")
    public Result<StudentVO> addStudent(@RequestBody StudentDTO studentDTO) {
        return Result.success(studentService.addStudent(studentDTO));
    }

    /**
     * 批量删除学生
     *
     * @param ids 学生ID列表
     * @return
     */
    @DeleteMapping("/delete")
    public Result<String> deleteStudents(@RequestBody List<Long> ids) {
        studentService.deleteStudents(ids);
        return Result.success(null);
    }

    /**
     * 修改学生信息
     *
     * @param studentDTO
     * @return
     */
    @PutMapping("/update")
    public Result<String> updateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(studentDTO);
        return Result.success(null);
    }
}
