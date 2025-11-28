package com.sims.model.dto.teacher;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 教师DTO
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Data
public class TeacherDTO {
    private Long id;
    private String employeeNumber;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String department;
    private String title;
    private LocalDate hireDate;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
