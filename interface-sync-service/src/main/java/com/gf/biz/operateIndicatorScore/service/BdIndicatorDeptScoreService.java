package com.gf.biz.operateIndicatorScore.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gf.biz.common.GfResult;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 16:53:16
 */
public interface BdIndicatorDeptScoreService extends IService<BdIndicatorDeptScore> {
    GfResult<String> createOrAddBdIndicatorDeptScore(BdIndicatorDeptScoreDto bdIndicatorDeptScoreDto);
}
