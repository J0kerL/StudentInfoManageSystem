package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.exception.BusinessException;
import com.sims.mapper.CourseMapper;
import com.sims.mapper.ScoreMapper;
import com.sims.mapper.StudentMapper;
import com.sims.model.dto.score.PageQueryScoreDTO;
import com.sims.model.dto.score.ScoreDTO;
import com.sims.model.entity.Course;
import com.sims.model.entity.Score;
import com.sims.model.entity.Student;
import com.sims.model.vo.ScoreVO;
import com.sims.result.PageResult;
import com.sims.service.ScoreService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成绩服务实现
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public ScoreVO addScore(ScoreDTO scoreDTO) {
        log.info("添加成绩，学生ID：{}，课程ID：{}", scoreDTO.getStudentId(), scoreDTO.getCourseId());

        // 必填字段校验
        if (scoreDTO.getStudentId() == null) {
            throw new BusinessException(400, "学生ID不能为空");
        }
        if (scoreDTO.getCourseId() == null) {
            throw new BusinessException(400, "课程ID不能为空");
        }
        if (scoreDTO.getScore() == null) {
            throw new BusinessException(400, "成绩分数不能为空");
        }
        if (StrUtil.isBlank(scoreDTO.getSemester())) {
            throw new BusinessException(400, "学期不能为空");
        }

        // 校验学生是否存在
        Student student = studentMapper.findById(scoreDTO.getStudentId());
        if (student == null) {
            throw new BusinessException(400, "学生不存在");
        }

        // 校验课程是否存在
        Course course = courseMapper.findById(scoreDTO.getCourseId());
        if (course == null) {
            throw new BusinessException(400, "课程不存在");
        }

        // 校验该学生该课程该学期是否已有成绩
        Score existScore = scoreMapper.findByStudentCourseAndSemester(
                scoreDTO.getStudentId(), scoreDTO.getCourseId(), scoreDTO.getSemester());
        if (existScore != null) {
            throw new BusinessException(400, "该学生在该学期的该课程成绩已存在");
        }

        // 自动计算成绩等级
        scoreDTO.setGrade(calculateGrade(scoreDTO.getScore()));

        scoreMapper.insert(scoreDTO);
        log.info("成绩添加成功，ID：{}", scoreDTO.getId());

        ScoreVO scoreVO = new ScoreVO();
        BeanUtil.copyProperties(scoreDTO, scoreVO);
        scoreVO.setStudentName(student.getName());
        scoreVO.setStudentNumber(student.getStudentNumber());
        scoreVO.setCourseName(course.getName());
        scoreVO.setCourseCode(course.getCode());
        return scoreVO;
    }

    @Override
    @Transactional
    public void deleteScores(List<Long> ids) {
        log.info("批量删除成绩，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }

        scoreMapper.deleteByIds(ids);
        log.info("成绩批量删除成功，IDs：{}", ids);
    }

    @Override
    public void updateScore(ScoreDTO scoreDTO) {
        if (scoreDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (scoreDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改成绩信息，ID：{}", scoreDTO.getId());

        // 校验成绩是否存在
        Score currentScore = scoreMapper.findById(scoreDTO.getId());
        if (currentScore == null) {
            throw new BusinessException(400, "该成绩记录不存在");
        }

        // 若修改学生ID，校验学生是否存在
        if (scoreDTO.getStudentId() != null) {
            Student student = studentMapper.findById(scoreDTO.getStudentId());
            if (student == null) {
                throw new BusinessException(400, "学生不存在");
            }
        }

        // 若修改课程ID，校验课程是否存在
        if (scoreDTO.getCourseId() != null) {
            Course course = courseMapper.findById(scoreDTO.getCourseId());
            if (course == null) {
                throw new BusinessException(400, "课程不存在");
            }
        }

        // 若修改了学生、课程或学期，检查是否与其他记录冲突
        Long studentId = scoreDTO.getStudentId() != null ? scoreDTO.getStudentId() : currentScore.getStudentId();
        Long courseId = scoreDTO.getCourseId() != null ? scoreDTO.getCourseId() : currentScore.getCourseId();
        String semester = StrUtil.isNotBlank(scoreDTO.getSemester()) ? scoreDTO.getSemester() : currentScore.getSemester();

        Score existScore = scoreMapper.findByStudentCourseAndSemester(studentId, courseId, semester);
        if (existScore != null && !existScore.getId().equals(scoreDTO.getId())) {
            throw new BusinessException(400, "该学生在该学期的该课程成绩已存在");
        }

        // 若修改分数，自动重新计算等级
        if (scoreDTO.getScore() != null) {
            scoreDTO.setGrade(calculateGrade(scoreDTO.getScore()));
        }

        scoreMapper.update(scoreDTO);
        log.info("成绩信息修改成功，ID：{}", scoreDTO.getId());
    }

    @Override
    public PageResult page(PageQueryScoreDTO pageQueryScoreDTO) {
        PageHelper.startPage(pageQueryScoreDTO.getPage(), pageQueryScoreDTO.getPageSize());
        Page<Score> pageResult = scoreMapper.page(pageQueryScoreDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    @Override
    public ScoreVO getScoreById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        Score score = scoreMapper.findById(id);
        if (score == null) {
            throw new BusinessException(400, "成绩记录不存在");
        }
        ScoreVO scoreVO = new ScoreVO();
        BeanUtil.copyProperties(score, scoreVO);

        // 查询学生信息
        if (score.getStudentId() != null) {
            Student student = studentMapper.findById(score.getStudentId());
            if (student != null) {
                scoreVO.setStudentName(student.getName());
                scoreVO.setStudentNumber(student.getStudentNumber());
            }
        }

        // 查询课程信息
        if (score.getCourseId() != null) {
            Course course = courseMapper.findById(score.getCourseId());
            if (course != null) {
                scoreVO.setCourseName(course.getName());
                scoreVO.setCourseCode(course.getCode());
            }
        }

        return scoreVO;
    }

    /**
     * 根据分数计算成绩等级
     */
    private String calculateGrade(BigDecimal score) {
        if (score == null) {
            return null;
        }
        double s = score.doubleValue();
        if (s >= 95) {
            return "A+";
        }
        if (s >= 90) {
            return "A";
        }
        if (s >= 85) {
            return "B+";
        }
        if (s >= 80) {
            return "B";
        }
        if (s >= 75) {
            return "C+";
        }
        if (s >= 70) {
            return "C";
        }
        if (s >= 60) {
            return "D";
        }
        return "F";
    }
}
