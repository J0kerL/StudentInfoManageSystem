package com.sims.model.dto.student;

import com.sims.constant.Status;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Diamond
 * @create 2025-11-26 12:02
 */
@Data
public class PageQueryStudentDTO implements Serializable {
    private String studentNumber;
    private String name;
    private String gender;
    private Long majorId;
    private Long classId;
    private Status status;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;
}
