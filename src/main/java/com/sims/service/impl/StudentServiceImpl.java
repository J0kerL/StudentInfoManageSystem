package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.constant.Status;
import com.sims.exception.BusinessException;
import com.sims.mapper.ClassMapper;
import com.sims.mapper.MajorMapper;
import com.sims.mapper.ScoreMapper;
import com.sims.mapper.StudentMapper;
import com.sims.model.dto.student.PageQueryStudentDTO;
import com.sims.model.dto.student.StudentDTO;
import com.sims.model.entity.Student;
import com.sims.model.vo.StudentVO;
import com.sims.result.PageResult;
import com.sims.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Resource
    private ClassMapper classMapper;
    @Resource
    private MajorMapper majorMapper;

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

    /**
     * 修改学生信息
     *
     * @param studentDTO
     */
    @Override
    public void updateStudent(StudentDTO studentDTO) {
        // 基本校验
        if (studentDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (studentDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改学生信息，ID：{}", studentDTO.getId());

        // 校验学生是否存在（用ID查询）
        Student currentStudent = studentMapper.findById(studentDTO.getId());
        if (currentStudent == null) {
            throw new BusinessException(400, "该学生不存在");
        }

        // 若修改学号，校验新学号是否与其他学生冲突
        if (studentDTO.getStudentNumber() != null) {
            Student existStudent = studentMapper.findByStudentNumber(studentDTO.getStudentNumber());
            if (existStudent != null && !existStudent.getId().equals(studentDTO.getId())) {
                throw new BusinessException(400, "学号已有其他学生使用");
            }
        }

        // 若传入班级ID，校验班级是否存在
        if (studentDTO.getClassId() != null && classMapper.existsById(studentDTO.getClassId()) == 0) {
            throw new BusinessException(400, "班级不存在");
        }

        // 若传入专业ID，校验专业是否存在
        if (studentDTO.getMajorId() != null && majorMapper.existsById(studentDTO.getMajorId()) == 0) {
            throw new BusinessException(400, "专业不存在");
        }

        // 若修改了入学日期或班级ID，校验入学年份与班级年级是否一致
        // 优先用传入的值，没传则用当前学生的值
        Long classIdToCheck = studentDTO.getClassId() != null ? studentDTO.getClassId() : currentStudent.getClassId();
        if (classIdToCheck != null) {
            // 获取入学日期：优先用传入的，没传则用当前的
            LocalDate enrollmentDate = studentDTO.getEnrollmentDate() != null
                    ? studentDTO.getEnrollmentDate() : currentStudent.getEnrollmentDate();
            if (enrollmentDate != null) {
                String enrollmentYear = String.valueOf(enrollmentDate.getYear());
                String classGrade = classMapper.findGradeById(classIdToCheck);
                if (classGrade != null && !classGrade.equals(enrollmentYear)) {
                    throw new BusinessException(400, "入学年份(" + enrollmentYear + ")与班级年级(" + classGrade + ")不一致，请选择正确的班级");
                }
            }
        }

        studentMapper.update(studentDTO);
        log.info("学生信息修改成功，ID：{}", studentDTO.getId());
    }

    /**
     * 分页查询
     *
     * @param pageQueryStudentDTO
     * @return
     */
    @Override
    public PageResult page(PageQueryStudentDTO pageQueryStudentDTO) {
        PageHelper.startPage(pageQueryStudentDTO.getPage(), pageQueryStudentDTO.getPageSize());
        Page<Student> pageResult = studentMapper.page(pageQueryStudentDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }
}
