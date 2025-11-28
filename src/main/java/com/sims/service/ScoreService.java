package com.sims.service;

import com.sims.model.dto.score.PageQueryScoreDTO;
import com.sims.model.dto.score.ScoreDTO;
import com.sims.model.vo.ScoreVO;
import com.sims.result.PageResult;

import java.util.List;

/**
 * 成绩服务接口
 *
 * @author Diamond
 * @create 2025-11-28
 */
public interface ScoreService {

    /**
     * 添加成绩
     */
    ScoreVO addScore(ScoreDTO scoreDTO);

    /**
     * 批量删除成绩
     */
    void deleteScores(List<Long> ids);

    /**
     * 修改成绩信息
     */
    void updateScore(ScoreDTO scoreDTO);

    /**
     * 分页查询成绩
     */
    PageResult page(PageQueryScoreDTO pageQueryScoreDTO);

    /**
     * 根据ID查询成绩
     */
    ScoreVO getScoreById(Long id);
}
