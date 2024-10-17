package com.gf.biz.OperateTargetRunningAllPoints.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.shenfangData.entity.BfScoreMv;
import com.gf.biz.shenfangData.mapper.BfScoreMvMapper;
import com.gf.biz.OperateTargetRunningAllPoints.entity.BdIndicatorDeptScore;
import com.gf.biz.OperateTargetRunningAllPoints.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SyncSfTargetRunningJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    public void execute() throws Exception {
        // TODO Auto-generated method stub
        logger.info("开始执行神访目标分值计算");
        syncSfGetTargetRunningPointsData();


    }
    public void syncSfGetTargetRunningPointsData() throws Exception {
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);//Mapper注入
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        BfScoreMvMapper bfScoreMvMapper = SpringBeanUtil.getBean(BfScoreMvMapper.class);
        QueryWrapper<LcapDepartment4a79f3> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_classify","0");
        List<LcapDepartment4a79f3> lcapDepartment4a79f3List = lcapDepartment4a79f3Mapper.selectList(wrapper);
        for(LcapDepartment4a79f3 lcapDepartment4a79f3:lcapDepartment4a79f3List){
            QueryWrapper<BfScoreMv> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_code",lcapDepartment4a79f3.getDeptCode());
            //queryWrapper.eq("status","1");//已确认
            //queryWrapper.eq("year",2019);//业务年
            //queryWrapper.eq("month",12);业务日
            List<BfScoreMv> bfScoreMvList = bfScoreMvMapper.selectList(queryWrapper);
            BdIndicatorDeptScore bdIndicatorDeptScore = new BdIndicatorDeptScore();
            bdIndicatorDeptScore.setDeptCode(lcapDepartment4a79f3.getDeptCode());
            bdIndicatorDeptScore.setDeptName(lcapDepartment4a79f3.getName());
            bdIndicatorDeptScore.setDeptId(lcapDepartment4a79f3.getId());
            bdIndicatorDeptScore.setDeptClassifyFlag(lcapDepartment4a79f3.getDeptClassify());
            bdIndicatorDeptScore.setYear(2024);//预留年份字段
            bdIndicatorDeptScore.setMonthQuarter(1);//预留月份字段
            bdIndicatorDeptScore.setRelateId(1L);//预留季度字段
            bdIndicatorDeptScore.setDimensionFlag("1");//预留维度字段
            bdIndicatorDeptScore.setIndicatorCode("1");//预留指标字段
            bdIndicatorDeptScore.setIndicatorName("神秘访客");//预留指标名称字段
            bdIndicatorDeptScore.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            bdIndicatorDeptScore.setCreatedTime(new Date());
            bdIndicatorDeptScore.setDeletedFlag(CommonConstant.STATUS_DEL);
            BigDecimal finalScore = BigDecimal.ZERO;
            if(bfScoreMvList.size()>0) {
                for(BfScoreMv bfScoreMv:bfScoreMvList){
                    finalScore = finalScore.add(bfScoreMv.getTotalScore());
                }
                if(finalScore.compareTo(BigDecimal.ZERO)>0){
                    finalScore=finalScore.divide(new BigDecimal(bfScoreMvList.size()),3, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100),3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(0.23)).multiply(new BigDecimal(49)).divide(new BigDecimal(1),3, BigDecimal.ROUND_HALF_UP);
                    bdIndicatorDeptScore.setFinalScore(finalScore);
                }else{
                    bdIndicatorDeptScore.setRemark("该数据为0，请查询相应表单");
                }
            }else{
                bdIndicatorDeptScore.setRemark("该数据未查询到，请查询相应表单");
            }
            bdIndicatorDeptScoreMapper.insert(bdIndicatorDeptScore);

        }
        logger.info("神访目标分值计算完成");

    }
}
