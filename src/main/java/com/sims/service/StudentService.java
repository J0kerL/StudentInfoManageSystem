package com.sims.service;

import com.sims.model.dto.student.StudentDTO;
import com.sims.model.vo.StudentVO;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-25 12:10
 */
public interface StudentService {

    StudentVO addStudent(StudentDTO studentDTO);

    void deleteStudents(List<Long> ids);
}
