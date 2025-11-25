package com.sims.service;

import com.sims.model.dto.student.StudentDTO;
import com.sims.model.vo.StudentVO;

/**
 * @author Diamond
 * @create 2025-11-25 12:10
 */
public interface StudentService {

    StudentVO addStudent(StudentDTO studentDTO);

}
