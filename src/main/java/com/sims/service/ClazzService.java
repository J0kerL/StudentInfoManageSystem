package com.sims.service;

import com.sims.model.dto.clazz.ClazzDTO;
import com.sims.model.dto.clazz.PageQueryClazzDTO;
import com.sims.model.vo.ClazzVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * 班级服务接口
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface ClazzService {

    /**
     * 添加班级
     */
    ClazzVO addClazz(ClazzDTO clazzDTO);

    /**
     * 批量删除班级
     */
    void deleteClazzs(List<Long> ids);

    /**
     * 修改班级信息
     */
    void updateClazz(ClazzDTO clazzDTO);

    /**
     * 分页查询班级
     */
    PageResult page(PageQueryClazzDTO pageQueryClazzDTO);

    /**
     * 根据ID查询班级
     */
    ClazzVO getClazzById(Long id);
}
