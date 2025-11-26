package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sims.constant.Status;
import com.sims.exception.BusinessException;
import com.sims.mapper.ScoreMapper;
import com.sims.mapper.StudentMapper;
import com.sims.model.dto.student.StudentDTO;
import com.sims.model.vo.StudentVO;
import com.sims.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-25 12:11
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ScoreMapper scoreMapper;

    /**
     * 添加学生
     *
     * @param studentDTO
     * @return
     */
    @Override
    public StudentVO addStudent(StudentDTO studentDTO) {
        log.info("添加学生，学号：{}，姓名：{}", studentDTO.getStudentNumber(), studentDTO.getName());
        // 必填字段校验
        String studentNumber = studentDTO.getStudentNumber();
        if (studentNumber == null || studentNumber.isBlank()) {
            throw new BusinessException(400, "学号不能为空");
        }
        if (studentDTO.getName() == null || studentDTO.getName().isBlank()) {
            throw new BusinessException(400, "姓名不能为空");
        }
        if (studentDTO.getGender() == null) {
            throw new BusinessException(400, "性别不能为空");
        }
        if (studentDTO.getEnrollmentDate() == null) {
            throw new BusinessException(400, "入学日期不能为空");
        }
        // 唯一性校验
        if (studentMapper.findByStudentNumber(studentNumber) != null) {
            throw new BusinessException(400, "学号已存在");
        }
        // 默认激活状态
        if (studentDTO.getStatus() == null) {
            studentDTO.setStatus(Status.ACTIVE);
        }
        // 插入数据，创建时间和修改时间由MyBatis拦截器自动填充
        studentMapper.insert(studentDTO);
        log.info("学生添加成功，ID：{}，学号：{}", studentDTO.getId(), studentDTO.getStudentNumber());
        StudentVO studentVO = new StudentVO();
        BeanUtil.copyProperties(studentDTO, studentVO);
        return studentVO;
    }

    /**
     * 批量删除学生
     *
     * @param ids 学生ID列表
     */
    @Override
    @Transactional
    public void deleteStudents(List<Long> ids) {
        log.info("批量删除学生，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }
        // 根据学生id批量删除对应成绩
        scoreMapper.deleteByStudentIds(ids);
        log.info("批量删除学生对应的成绩，IDs：{}", ids);
        // 批量删除学生
        studentMapper.deleteByIds(ids);
        log.info("学生批量删除成功，IDs：{}", ids);
    }
}
