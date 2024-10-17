package com.gf.biz.fangdengRead.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gf.biz.common.GfResult;
import com.gf.biz.fangdengRead.entity.BfRecordFdStatistic;
import com.gf.biz.fangdengRead.entity.IfRecordFdScore;

/**
 * <p>
 * 樊登人员读书统计表 服务类
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 17:57:03
 */
public interface BfRecordFdStatisticService extends IService<BfRecordFdStatistic> {
    GfResult<String> generateOrUpdateBizForm(IfRecordFdScore record);
}
