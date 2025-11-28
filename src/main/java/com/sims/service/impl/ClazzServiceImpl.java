package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.exception.BusinessException;
import com.sims.mapper.ClassMapper;
import com.sims.mapper.MajorMapper;
import com.sims.mapper.StudentMapper;
import com.sims.model.dto.clazz.ClazzDTO;
import com.sims.model.dto.clazz.PageQueryClazzDTO;
import com.sims.model.entity.Clazz;
import com.sims.model.entity.Major;
import com.sims.model.vo.ClazzVO;
import com.sims.result.PageResult;
import com.sims.service.ClazzService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 班级服务实现
 *
 * @author Diamond
 * @create 2025-11-28
 */
@Service
@Slf4j
public class ClazzServiceImpl implements ClazzService {

    @Resource
    private ClassMapper classMapper;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public ClazzVO addClazz(ClazzDTO clazzDTO) {
        log.info("添加班级，名称：{}", clazzDTO.getName());

        // 必填字段校验
        if (StrUtil.isBlank(clazzDTO.getName())) {
            throw new BusinessException(400, "班级名称不能为空");
        }
        if (clazzDTO.getMajorId() == null) {
            throw new BusinessException(400, "专业ID不能为空");
        }
        if (StrUtil.isBlank(clazzDTO.getGrade())) {
            throw new BusinessException(400, "年级不能为空");
        }

        // 校验专业是否存在
        if (majorMapper.existsById(clazzDTO.getMajorId()) == 0) {
            throw new BusinessException(400, "专业不存在");
        }

        // 唯一性校验
        if (classMapper.existsByName(clazzDTO.getName()) > 0) {
            throw new BusinessException(400, "班级名称已存在");
        }

        classMapper.insert(clazzDTO);
        log.info("班级添加成功，ID：{}，名称：{}", clazzDTO.getId(), clazzDTO.getName());

        ClazzVO clazzVO = new ClazzVO();
        BeanUtil.copyProperties(clazzDTO, clazzVO);
        return clazzVO;
    }

    @Override
    @Transactional
    public void deleteClazzs(List<Long> ids) {
        log.info("批量删除班级，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }

        // 检查是否有学生关联该班级
        for (Long id : ids) {
            int studentCount = studentMapper.countByClassId(id);
            if (studentCount > 0) {
                Clazz clazz = classMapper.findById(id);
                throw new BusinessException(400, "班级【" + (clazz != null ? clazz.getName() : id) + "】下还有" + studentCount + "名学生，无法删除");
            }
        }

        classMapper.deleteByIds(ids);
        log.info("班级批量删除成功，IDs：{}", ids);
    }

    @Override
    public void updateClazz(ClazzDTO clazzDTO) {
        if (clazzDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (clazzDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改班级信息，ID：{}", clazzDTO.getId());

        // 校验班级是否存在
        Clazz currentClazz = classMapper.findById(clazzDTO.getId());
        if (currentClazz == null) {
            throw new BusinessException(400, "该班级不存在");
        }

        // 若修改名称，校验新名称是否与其他班级冲突
        if (StrUtil.isNotBlank(clazzDTO.getName())) {
            Clazz existClazz = classMapper.findByName(clazzDTO.getName());
            if (existClazz != null && !existClazz.getId().equals(clazzDTO.getId())) {
                throw new BusinessException(400, "班级名称已被其他班级使用");
            }
        }

        // 若修改专业ID，校验专业是否存在
        if (clazzDTO.getMajorId() != null && majorMapper.existsById(clazzDTO.getMajorId()) == 0) {
            throw new BusinessException(400, "专业不存在");
        }

        classMapper.update(clazzDTO);
        log.info("班级信息修改成功，ID：{}", clazzDTO.getId());
    }

    @Override
    public PageResult page(PageQueryClazzDTO pageQueryClazzDTO) {
        PageHelper.startPage(pageQueryClazzDTO.getPage(), pageQueryClazzDTO.getPageSize());
        Page<Clazz> pageResult = classMapper.page(pageQueryClazzDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    @Override
    public ClazzVO getClazzById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        Clazz clazz = classMapper.findById(id);
        if (clazz == null) {
            throw new BusinessException(400, "班级不存在");
        }
        ClazzVO clazzVO = new ClazzVO();
        BeanUtil.copyProperties(clazz, clazzVO);

        // 查询专业名称
        if (clazz.getMajorId() != null) {
            Major major = majorMapper.findById(clazz.getMajorId());
            if (major != null) {
                clazzVO.setMajorName(major.getName());
            }
        }
        return clazzVO;
    }
}
