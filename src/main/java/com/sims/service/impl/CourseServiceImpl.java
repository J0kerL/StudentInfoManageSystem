package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.exception.BusinessException;
import com.sims.mapper.CourseMapper;
import com.sims.mapper.ScoreMapper;
import com.sims.model.dto.course.CourseDTO;
import com.sims.model.dto.course.PageQueryCourseDTO;
import com.sims.model.entity.Course;
import com.sims.model.vo.CourseVO;
import com.sims.result.PageResult;
import com.sims.service.CourseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程服务实现
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public CourseVO addCourse(CourseDTO courseDTO) {
        log.info("添加课程，名称：{}，代码：{}", courseDTO.getName(), courseDTO.getCode());

        // 必填字段校验
        if (StrUtil.isBlank(courseDTO.getCode())) {
            throw new BusinessException(400, "课程代码不能为空");
        }
        if (StrUtil.isBlank(courseDTO.getName())) {
            throw new BusinessException(400, "课程名称不能为空");
        }
        if (courseDTO.getCredit() == null) {
            throw new BusinessException(400, "学分不能为空");
        }
        if (courseDTO.getHours() == null) {
            throw new BusinessException(400, "学时不能为空");
        }

        // 唯一性校验
        if (courseMapper.existsByCode(courseDTO.getCode()) > 0) {
            throw new BusinessException(400, "课程代码已存在");
        }
        if (courseMapper.existsByName(courseDTO.getName()) > 0) {
            throw new BusinessException(400, "课程名称已存在");
        }

        courseMapper.insert(courseDTO);
        log.info("课程添加成功，ID：{}，名称：{}", courseDTO.getId(), courseDTO.getName());

        CourseVO courseVO = new CourseVO();
        BeanUtil.copyProperties(courseDTO, courseVO);
        return courseVO;
    }

    @Override
    @Transactional
    public void deleteCourses(List<Long> ids) {
        log.info("批量删除课程，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }

        // 检查是否有成绩关联该课程
        for (Long id : ids) {
            int scoreCount = scoreMapper.countByCourseId(id);
            if (scoreCount > 0) {
                Course course = courseMapper.findById(id);
                throw new BusinessException(400, "课程【" + (course != null ? course.getName() : id) + "】下还有" + scoreCount + "条成绩记录，无法删除");
            }
        }

        courseMapper.deleteByIds(ids);
        log.info("课程批量删除成功，IDs：{}", ids);
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {
        if (courseDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (courseDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改课程信息，ID：{}", courseDTO.getId());

        // 校验课程是否存在
        Course currentCourse = courseMapper.findById(courseDTO.getId());
        if (currentCourse == null) {
            throw new BusinessException(400, "该课程不存在");
        }

        // 若修改代码，校验新代码是否与其他课程冲突
        if (StrUtil.isNotBlank(courseDTO.getCode())) {
            Course existCourse = courseMapper.findByCode(courseDTO.getCode());
            if (existCourse != null && !existCourse.getId().equals(courseDTO.getId())) {
                throw new BusinessException(400, "课程代码已被其他课程使用");
            }
        }

        // 若修改名称，校验新名称是否与其他课程冲突
        if (StrUtil.isNotBlank(courseDTO.getName())) {
            Course existCourse = courseMapper.findByName(courseDTO.getName());
            if (existCourse != null && !existCourse.getId().equals(courseDTO.getId())) {
                throw new BusinessException(400, "课程名称已被其他课程使用");
            }
        }

        courseMapper.update(courseDTO);
        log.info("课程信息修改成功，ID：{}", courseDTO.getId());
    }

    @Override
    public PageResult page(PageQueryCourseDTO pageQueryCourseDTO) {
        PageHelper.startPage(pageQueryCourseDTO.getPage(), pageQueryCourseDTO.getPageSize());
        Page<Course> pageResult = courseMapper.page(pageQueryCourseDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    @Override
    public CourseVO getCourseById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        Course course = courseMapper.findById(id);
        if (course == null) {
            throw new BusinessException(400, "课程不存在");
        }
        CourseVO courseVO = new CourseVO();
        BeanUtil.copyProperties(course, courseVO);
        return courseVO;
    }
}
