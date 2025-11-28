package com.sims.service;

import com.sims.model.dto.teacher.PageQueryTeacherDTO;
import com.sims.model.dto.teacher.TeacherDTO;
import com.sims.model.vo.TeacherVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * 教师服务接口
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface TeacherService {

    /**
     * 添加教师
     */
    TeacherVO addTeacher(TeacherDTO teacherDTO);

    /**
     * 批量删除教师
     */
    void deleteTeachers(List<Long> ids);

    /**
     * 修改教师信息
     */
    void updateTeacher(TeacherDTO teacherDTO);

    /**
     * 分页查询教师
     */
    PageResult page(PageQueryTeacherDTO pageQueryTeacherDTO);

    /**
     * 根据ID查询教师
     */
    TeacherVO getTeacherById(Long id);
}
