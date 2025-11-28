package com.sims.controller.clazz;

import com.sims.model.dto.clazz.ClazzDTO;
import com.sims.model.dto.clazz.PageQueryClazzDTO;
import com.sims.model.vo.ClazzVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.ClazzService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理控制器
 *
 * @author Diamond
 * @create 2025-11-28
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Resource
    private ClazzService clazzService;

    /**
     * 添加班级
     */
    @PostMapping("/add")
    public Result<ClazzVO> addClazz(@RequestBody ClazzDTO clazzDTO) {
        return Result.success(clazzService.addClazz(clazzDTO));
    }

    /**
     * 批量删除班级
     */
    @DeleteMapping("/delete")
    public Result<String> deleteClazzs(@RequestBody List<Long> ids) {
        clazzService.deleteClazzs(ids);
        return Result.success(null);
    }

    /**
     * 修改班级信息
     */
    @PutMapping("/update")
    public Result<String> updateClazz(@RequestBody ClazzDTO clazzDTO) {
        clazzService.updateClazz(clazzDTO);
        return Result.success(null);
    }

    /**
     * 分页查询班级
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryClazzDTO pageQueryClazzDTO) {
        PageResult pageResult = clazzService.page(pageQueryClazzDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询班级
     */
    @GetMapping("/{id}")
    public Result<ClazzVO> getClazzById(@PathVariable Long id) {
        return Result.success(clazzService.getClazzById(id));
    }
}
