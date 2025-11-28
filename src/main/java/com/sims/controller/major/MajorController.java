package com.sims.controller.major;

import com.sims.model.dto.major.MajorDTO;
import com.sims.model.vo.MajorVO;
import com.sims.result.Result;
import com.sims.service.MajorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
