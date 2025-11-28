package com.sims.service;

import com.sims.model.dto.major.MajorDTO;
import com.sims.model.vo.MajorVO;

/**
 * @author Diamond
 * @create 2025-11-27 17:29
 */
public interface MajorService {
    MajorVO addMajor(MajorDTO majorDTO);
}
