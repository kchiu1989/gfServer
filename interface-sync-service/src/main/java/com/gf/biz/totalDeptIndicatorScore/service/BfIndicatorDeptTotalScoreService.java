package com.gf.biz.totalDeptIndicatorScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gf.biz.common.GfResult;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalScoreDto;
import com.gf.biz.totalDeptIndicatorScore.po.BfIndicatorDeptTotalScore;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gf
 * @since 2024-10-28 11:41:04
 */
public interface BfIndicatorDeptTotalScoreService extends IService<BfIndicatorDeptTotalScore> {
    GfResult<String> createOrUpdateBfIndicatorDeptTotalScore(BfIndicatorDeptTotalScoreDto bfIndicatorDeptTotalScoreDto);
}
