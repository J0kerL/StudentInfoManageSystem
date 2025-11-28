package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.exception.BusinessException;
import com.sims.mapper.TeacherMapper;
import com.sims.model.dto.teacher.PageQueryTeacherDTO;
import com.sims.model.dto.teacher.TeacherDTO;
import com.sims.model.entity.Teacher;
import com.sims.model.vo.TeacherVO;
import com.sims.result.PageResult;
import com.sims.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教师服务实现
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public TeacherVO addTeacher(TeacherDTO teacherDTO) {
        log.info("添加教师，姓名：{}，工号：{}", teacherDTO.getName(), teacherDTO.getEmployeeNumber());

        // 必填字段校验
        if (StrUtil.isBlank(teacherDTO.getEmployeeNumber())) {
            throw new BusinessException(400, "工号不能为空");
        }
        if (StrUtil.isBlank(teacherDTO.getName())) {
            throw new BusinessException(400, "姓名不能为空");
        }
        if (StrUtil.isBlank(teacherDTO.getGender())) {
            throw new BusinessException(400, "性别不能为空");
        }
        if (teacherDTO.getHireDate() == null) {
            throw new BusinessException(400, "入职日期不能为空");
        }

        // 唯一性校验
        if (teacherMapper.existsByEmployeeNumber(teacherDTO.getEmployeeNumber()) > 0) {
            throw new BusinessException(400, "工号已存在");
        }

        // 默认状态为ACTIVE
        if (StrUtil.isBlank(teacherDTO.getStatus())) {
            teacherDTO.setStatus("ACTIVE");
        }

        teacherMapper.insert(teacherDTO);
        log.info("教师添加成功，ID：{}，姓名：{}", teacherDTO.getId(), teacherDTO.getName());

        TeacherVO teacherVO = new TeacherVO();
        BeanUtil.copyProperties(teacherDTO, teacherVO);
        return teacherVO;
    }

    @Override
    @Transactional
    public void deleteTeachers(List<Long> ids) {
        log.info("批量删除教师，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }

        teacherMapper.deleteByIds(ids);
        log.info("教师批量删除成功，IDs：{}", ids);
    }

    @Override
    public void updateTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (teacherDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改教师信息，ID：{}", teacherDTO.getId());

        // 校验教师是否存在
        Teacher currentTeacher = teacherMapper.findById(teacherDTO.getId());
        if (currentTeacher == null) {
            throw new BusinessException(400, "该教师不存在");
        }

        // 若修改工号，校验新工号是否与其他教师冲突
        if (StrUtil.isNotBlank(teacherDTO.getEmployeeNumber())) {
            Teacher existTeacher = teacherMapper.findByEmployeeNumber(teacherDTO.getEmployeeNumber());
            if (existTeacher != null && !existTeacher.getId().equals(teacherDTO.getId())) {
                throw new BusinessException(400, "工号已被其他教师使用");
            }
        }

        teacherMapper.update(teacherDTO);
        log.info("教师信息修改成功，ID：{}", teacherDTO.getId());
    }

    @Override
    public PageResult page(PageQueryTeacherDTO pageQueryTeacherDTO) {
        PageHelper.startPage(pageQueryTeacherDTO.getPage(), pageQueryTeacherDTO.getPageSize());
        Page<Teacher> pageResult = teacherMapper.page(pageQueryTeacherDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    @Override
    public TeacherVO getTeacherById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        Teacher teacher = teacherMapper.findById(id);
        if (teacher == null) {
            throw new BusinessException(400, "教师不存在");
        }
        TeacherVO teacherVO = new TeacherVO();
        BeanUtil.copyProperties(teacher, teacherVO);
        return teacherVO;
    }
}
