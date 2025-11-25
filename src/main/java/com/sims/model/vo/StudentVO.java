package com.sims.model.vo;

import com.sims.constant.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-11-25 12:07
 */
@Data
public class StudentVO {
    private Long id;
    private String studentNumber;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String address;
    // 入学日期
    private LocalDate enrollmentDate;
    private Long majorId;
    private Long classId;
    private Status status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
