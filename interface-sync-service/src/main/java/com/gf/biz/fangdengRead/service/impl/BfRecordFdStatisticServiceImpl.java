package com.gf.biz.fangdengRead.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.GfResult;
import com.gf.biz.common.GfResultCode;
import com.gf.biz.fangdengRead.entity.BfRecordFdStatistic;
import com.gf.biz.fangdengRead.entity.IfRecordFdScore;
import com.gf.biz.fangdengRead.mapper.BfRecordFdStatisticMapper;
import com.gf.biz.fangdengRead.mapper.IfRecordFdScoreMapper;
import com.gf.biz.fangdengRead.service.BfRecordFdStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 樊登人员读书统计表 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 17:57:03
 */

@Service
public class BfRecordFdStatisticServiceImpl extends ServiceImpl<BfRecordFdStatisticMapper, BfRecordFdStatistic> implements BfRecordFdStatisticService {
    private final static Logger logger = LoggerFactory.getLogger(BfRecordFdStatisticServiceImpl.class);
    @Autowired
    private BfRecordFdStatisticMapper bfRecordFdStatisticMapper;
    @Autowired
    private IfRecordFdScoreMapper ifRecordFdScoreMapper;


    @Override
    @Transactional(rollbackFor =Exception.class)
    public GfResult<String> generateOrUpdateBizForm(IfRecordFdScore record) {
        //先查询是否存在表单业务表，且状态未生效，若已生效，不做插入
        //若表单业务表未生效，则更新底表和业务表
        QueryWrapper<BfRecordFdStatistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", record.getYear());
        queryWrapper.eq("month", record.getMonth());
        queryWrapper.eq("dept_id", record.getDeptId());
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        List<BfRecordFdStatistic> dbList = bfRecordFdStatisticMapper.selectList(queryWrapper);
        if(dbList!=null&&dbList.size()>0){
            BfRecordFdStatistic dbRecord = dbList.get(0);
            if("2".equals(dbRecord.getStatus())){
                logger.error("业务单据已生效，不进行操作");
                return GfResult.failed(GfResultCode.BUSINESS_COMMON_EXCEPTION,"业务单据已生效，不再进行数据更新");
            }

            //开始更新接口数据和报表单数据
            Long ifId = dbRecord.getIfId();
            IfRecordFdScore ifRecordFdScore = ifRecordFdScoreMapper.selectById(ifId);
            if(ifRecordFdScore!=null){
                UpdateWrapper<IfRecordFdScore> toUpdIfWrapper = new UpdateWrapper<>();
                toUpdIfWrapper.set("final_score", record.getFinalScore());
                toUpdIfWrapper.set("remark",record.getRemark());
                toUpdIfWrapper.set("update_time", new Date());
                toUpdIfWrapper.eq("id",ifRecordFdScore.getId());


                ifRecordFdScoreMapper.update(null,toUpdIfWrapper);

                UpdateWrapper<BfRecordFdStatistic> toUpdBfWrapper = new UpdateWrapper<>();
                toUpdBfWrapper.set("final_score", record.getFinalScore());
                toUpdBfWrapper.set("update_time", new Date());
                toUpdBfWrapper.eq("id",dbRecord.getId());
                bfRecordFdStatisticMapper.update(null,toUpdBfWrapper);
                return GfResult.success("数据更新完毕");
            }
            return GfResult.failed(GfResultCode.BUSINESS_COMMON_EXCEPTION,"关联底表数据不存在，不进行数据更新");
        }else{
            logger.info("开始新增数据...");

            IfRecordFdScore toAddIfData = new IfRecordFdScore();
            BeanUtils.copyProperties(record, toAddIfData);

            toAddIfData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
            toAddIfData.setCreatedTime(new Date());
            toAddIfData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);

            ifRecordFdScoreMapper.insert(toAddIfData);

            BfRecordFdStatistic toAddBfRecord = new BfRecordFdStatistic();
            BeanUtils.copyProperties(record, toAddBfRecord);
            toAddBfRecord.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
            toAddBfRecord.setCreatedTime(new Date());
            toAddBfRecord.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            toAddBfRecord.setStatus("1");
            toAddBfRecord.setIfId(toAddIfData.getId());
            bfRecordFdStatisticMapper.insert(toAddBfRecord);
            return GfResult.success("数据新增处理完毕");

        }

    }
}
