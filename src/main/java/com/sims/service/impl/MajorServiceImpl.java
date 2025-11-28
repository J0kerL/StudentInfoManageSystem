package com.sims.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.sims.exception.BusinessException;
import com.sims.mapper.MajorMapper;
import com.sims.model.dto.major.MajorDTO;
import com.sims.model.vo.MajorVO;
import com.sims.service.MajorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Diamond
 * @create 2025-11-27 17:29
 */
@Service
@Slf4j
public class MajorServiceImpl implements MajorService {

    @Resource
    private MajorMapper majorMapper;

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
}
