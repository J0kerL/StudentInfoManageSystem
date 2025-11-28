package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sims.exception.BusinessException;
import com.sims.mapper.MajorMapper;
import com.sims.mapper.StudentMapper;
import com.sims.model.dto.major.MajorDTO;
import com.sims.model.dto.major.PageQueryMajorDTO;
import com.sims.model.entity.Major;
import com.sims.model.vo.MajorVO;
import com.sims.result.PageResult;
import com.sims.service.MajorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-27 17:29
 */
@Service
@Slf4j
public class MajorServiceImpl implements MajorService {

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public MajorVO addMajor(MajorDTO majorDTO) {
        log.info("添加专业，名称：{}，代码：{}", majorDTO.getName(), majorDTO.getCode());
        String name = majorDTO.getName();
        String code = majorDTO.getCode();
        if (StrUtil.isBlank(name)) {
            throw new BusinessException(400, "专业名称不能为空");
        }
        if (StrUtil.isBlank(code)) {
            throw new BusinessException(400, "专业代码不能为空");
        }
        if (majorMapper.existsByName(name) > 0) {
            throw new BusinessException(400, "专业名称已存在");
        }
        if (majorMapper.existsByCode(code) > 0) {
            throw new BusinessException(400, "专业代码已存在");
        }
        majorMapper.insert(majorDTO);
        log.info("专业添加成功，ID：{}，名称：{}", majorDTO.getId(), majorDTO.getName());
        MajorVO majorVO = new MajorVO();
        BeanUtil.copyProperties(majorDTO, majorVO);
        return majorVO;
    }

    /**
     * 批量删除专业
     *
     * @param ids 专业ID列表
     */
    @Override
    @Transactional
    public void deleteMajors(List<Long> ids) {
        log.info("批量删除专业，IDs：{}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "ID列表不能为空");
        }
        // 检查是否有学生关联该专业
        for (Long id : ids) {
            int studentCount = studentMapper.countByMajorId(id);
            if (studentCount > 0) {
                Major major = majorMapper.findById(id);
                throw new BusinessException(400, "专业【" + (major != null ? major.getName() : id) + "】下还有" + studentCount + "名学生，无法删除");
            }
        }
        majorMapper.deleteByIds(ids);
        log.info("专业批量删除成功，IDs：{}", ids);
    }

    /**
     * 修改专业信息
     *
     * @param majorDTO
     */
    @Override
    public void updateMajor(MajorDTO majorDTO) {
        // 基本校验
        if (majorDTO == null) {
            throw new BusinessException(400, "参数不能为空");
        }
        if (majorDTO.getId() == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        log.info("修改专业信息，ID：{}", majorDTO.getId());

        // 校验专业是否存在
        Major currentMajor = majorMapper.findById(majorDTO.getId());
        if (currentMajor == null) {
            throw new BusinessException(400, "该专业不存在");
        }

        // 若修改名称，校验新名称是否与其他专业冲突
        if (StrUtil.isNotBlank(majorDTO.getName())) {
            Major existMajor = majorMapper.findByName(majorDTO.getName());
            if (existMajor != null && !existMajor.getId().equals(majorDTO.getId())) {
                throw new BusinessException(400, "专业名称已被其他专业使用");
            }
        }

        // 若修改代码，校验新代码是否与其他专业冲突
        if (StrUtil.isNotBlank(majorDTO.getCode())) {
            Major existMajor = majorMapper.findByCode(majorDTO.getCode());
            if (existMajor != null && !existMajor.getId().equals(majorDTO.getId())) {
                throw new BusinessException(400, "专业代码已被其他专业使用");
            }
        }

        majorMapper.update(majorDTO);
        log.info("专业信息修改成功，ID：{}", majorDTO.getId());
    }

    /**
     * 分页查询专业
     *
     * @param pageQueryMajorDTO
     * @return
     */
    @Override
    public PageResult page(PageQueryMajorDTO pageQueryMajorDTO) {
        PageHelper.startPage(pageQueryMajorDTO.getPage(), pageQueryMajorDTO.getPageSize());
        Page<Major> pageResult = majorMapper.page(pageQueryMajorDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    /**
     * 根据ID查询专业
     *
     * @param id
     * @return
     */
    @Override
    public MajorVO getMajorById(Long id) {
        if (id == null) {
            throw new BusinessException(400, "ID不能为空");
        }
        Major major = majorMapper.findById(id);
        if (major == null) {
            throw new BusinessException(400, "专业不存在");
        }
        MajorVO majorVO = new MajorVO();
        BeanUtil.copyProperties(major, majorVO);
        return majorVO;
    }
}
