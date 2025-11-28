package com.sims.controller.score;

import com.sims.model.dto.score.PageQueryScoreDTO;
import com.sims.model.dto.score.ScoreDTO;
import com.sims.model.vo.ScoreVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.ScoreService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩管理控制器
 *
 * @author Diamond
 * @create 2025-11-28
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    /**
     * 添加成绩
     */
    @PostMapping("/add")
    public Result<ScoreVO> addScore(@RequestBody ScoreDTO scoreDTO) {
        return Result.success(scoreService.addScore(scoreDTO));
    }

    /**
     * 批量删除成绩
     */
    @DeleteMapping("/delete")
    public Result<String> deleteScores(@RequestBody List<Long> ids) {
        scoreService.deleteScores(ids);
        return Result.success(null);
    }

    /**
     * 修改成绩信息
     */
    @PutMapping("/update")
    public Result<String> updateScore(@RequestBody ScoreDTO scoreDTO) {
        scoreService.updateScore(scoreDTO);
        return Result.success(null);
    }

    /**
     * 分页查询成绩
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryScoreDTO pageQueryScoreDTO) {
        PageResult pageResult = scoreService.page(pageQueryScoreDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询成绩
     */
    @GetMapping("/{id}")
    public Result<ScoreVO> getScoreById(@PathVariable Long id) {
        return Result.success(scoreService.getScoreById(id));
    }
}
