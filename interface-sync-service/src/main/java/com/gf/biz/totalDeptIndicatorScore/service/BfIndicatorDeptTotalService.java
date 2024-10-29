package com.gf.biz.totalDeptIndicatorScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gf.biz.common.GfResult;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalDto;
import com.gf.biz.totalDeptIndicatorScore.po.BfIndicatorDeptTotal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gf
 * @since 2024-10-29 16:29:41
 */
public interface BfIndicatorDeptTotalService extends IService<BfIndicatorDeptTotal> {
    GfResult<String>  createOrUpdateData(BfIndicatorDeptTotalDto bfIndicatorDeptTotalDto);
}
