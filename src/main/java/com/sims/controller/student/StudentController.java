package com.sims.controller.student;

import com.sims.model.dto.student.StudentDTO;
import com.sims.model.vo.StudentVO;
import com.sims.result.Result;
import com.sims.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
