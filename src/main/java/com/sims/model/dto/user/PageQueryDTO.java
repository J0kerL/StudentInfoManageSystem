package com.sims.model.dto.user;

import com.sims.constant.Status;
import com.sims.constant.UserType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Diamond
 * @create 2025-11-18 16:14
 */
@Data
public class PageQueryDTO implements Serializable {

    private String username;
    private String realName;
    private String phone;
    private String email;
    private UserType userType;
    private Status status;
    // 页码
    private int page;
    // 每页显示记录数
    private int pageSize;

}
