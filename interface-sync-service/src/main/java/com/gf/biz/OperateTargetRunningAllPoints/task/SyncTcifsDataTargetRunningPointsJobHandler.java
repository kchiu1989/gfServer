package com.gf.biz.OperateTargetRunningAllPoints.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.OperateTargetRunningAllPoints.entity.BdIndicatorDeptScore;
import com.gf.biz.OperateTargetRunningAllPoints.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.tiancaiIfsData.entity.BfScoreCeStatistics;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.BfScoreCeStatisticsMapper;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SyncTcifsDataTargetRunningPointsJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    public void execute() throws Exception {
        // TODO Auto-generated method stub
        logger.info("开始执行天财目标分值计算");
        syncTcGetTargetRunningPointsData();

    }
    public void syncTcGetTargetRunningPointsData() throws Exception {
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);//Mapper注入
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        BfScoreCeStatisticsMapper bfScoreCeStatisticsMapper = SpringBeanUtil.getBean(BfScoreCeStatisticsMapper.class);
        QueryWrapper<LcapDepartment4a79f3> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_classify","0");
        List<LcapDepartment4a79f3> lcapDepartment4a79f3List = lcapDepartment4a79f3Mapper.selectList(wrapper);
        for(LcapDepartment4a79f3 lcapDepartment4a79f3:lcapDepartment4a79f3List){
            logger.info("deptName:{}",lcapDepartment4a79f3.getName()+"-"+lcapDepartment4a79f3.getDeptCode());
            QueryWrapper<BfScoreCeStatistics> bfScoreCeStatisticsQueryWrapper= new QueryWrapper<>();
            bfScoreCeStatisticsQueryWrapper.eq("dept_code",lcapDepartment4a79f3.getDeptCode());
            //bfScoreCeStatisticsQueryWrapper.eq("status","1");//已确认
            //bfScoreCeStatisticsQueryWrapper.eq("year",2019);//业务年
            //bfScoreCeStatisticsQueryWrapper.eq("month",12);业务日
            List<BfScoreCeStatistics> bfScoreCeStatisticsList = bfScoreCeStatisticsMapper.selectList(bfScoreCeStatisticsQueryWrapper);
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
            bdIndicatorDeptScore.setIndicatorName("顾客反馈");//预留指标名称字段
            bdIndicatorDeptScore.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            bdIndicatorDeptScore.setCreatedTime(new Date());
            bdIndicatorDeptScore.setDeletedFlag(CommonConstant.STATUS_DEL);
            BigDecimal totalScore = new BigDecimal(0);
            if(bfScoreCeStatisticsList.size()>0) {
                for (BfScoreCeStatistics bfScoreCeStatistics : bfScoreCeStatisticsList) {
                    if(bfScoreCeStatistics.getGetPoint().compareTo(BigDecimal.ZERO)>0){
                        totalScore = totalScore.add(bfScoreCeStatistics.getItemScore1());
                        totalScore = totalScore.add(bfScoreCeStatistics.getItemScore2());
                        totalScore = totalScore.add(bfScoreCeStatistics.getItemScore3());
                        totalScore = totalScore.add(bfScoreCeStatistics.getItemScore4());
                        totalScore = totalScore.add(bfScoreCeStatistics.getItemScore5());
                    }else{
                        totalScore =BigDecimal.ZERO;
                    }
                }
                if(totalScore.compareTo(BigDecimal.ZERO)>0) {
                    bdIndicatorDeptScore.setFinalScore(totalScore.divide(new BigDecimal(25), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(0.49)).multiply(new BigDecimal(0.10)).multiply(new BigDecimal(100)).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP));
                }else{
                    bdIndicatorDeptScore.setRemark("该数据不合格，请查询相应表单");
                }

            }else {
                bdIndicatorDeptScore.setRemark("未查询到该部门天财评价数据信息，无法计算");
            }
            bdIndicatorDeptScoreMapper.insert(bdIndicatorDeptScore);
        }
    }
}
