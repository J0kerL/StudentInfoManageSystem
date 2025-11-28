package com.sims.service;

import com.sims.model.dto.major.MajorDTO;
import com.sims.model.dto.major.PageQueryMajorDTO;
import com.sims.model.vo.MajorVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-27 17:29
 */
public interface MajorService {

    /**
     * 添加专业
     */
    MajorVO addMajor(MajorDTO majorDTO);

    /**
     * 批量删除专业
     */
    void deleteMajors(List<Long> ids);

    /**
     * 修改专业信息
     */
    void updateMajor(MajorDTO majorDTO);

    /**
     * 分页查询专业
     */
    PageResult page(PageQueryMajorDTO pageQueryMajorDTO);

    /**
     * 根据ID查询专业
     */
    MajorVO getMajorById(Long id);
}
