package com.sims.controller.major;

import com.sims.model.dto.major.MajorDTO;
import com.sims.model.dto.major.PageQueryMajorDTO;
import com.sims.model.vo.MajorVO;
import com.sims.result.PageResult;
import com.sims.result.Result;
import com.sims.service.MajorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-11-27 17:25
 */
@RestController
@RequestMapping("/major")
public class MajorController {

    @Resource
    private MajorService majorService;

    /**
     * 添加专业
     *
     * @param majorDTO
     * @return
     */
    @PostMapping("/add")
    public Result<MajorVO> addMajor(@RequestBody MajorDTO majorDTO) {
        return Result.success(majorService.addMajor(majorDTO));
    }

    /**
     * 批量删除专业
     *
     * @param ids 专业ID列表
     * @return
     */
    @DeleteMapping("/delete")
    public Result<String> deleteMajors(@RequestBody List<Long> ids) {
        majorService.deleteMajors(ids);
        return Result.success(null);
    }

    /**
     * 修改专业信息
     *
     * @param majorDTO
     * @return
     */
    @PutMapping("/update")
    public Result<String> updateMajor(@RequestBody MajorDTO majorDTO) {
        majorService.updateMajor(majorDTO);
        return Result.success(null);
    }

    /**
     * 分页查询专业
     *
     * @param pageQueryMajorDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(PageQueryMajorDTO pageQueryMajorDTO) {
        PageResult pageResult = majorService.page(pageQueryMajorDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询专业
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<MajorVO> getMajorById(@PathVariable Long id) {
        return Result.success(majorService.getMajorById(id));
    }
}
