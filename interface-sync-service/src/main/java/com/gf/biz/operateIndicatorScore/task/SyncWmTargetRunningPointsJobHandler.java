package com.gf.biz.operateIndicatorScore.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.gf.biz.wmPerformanceAllPoints.entity.BfTakeoutAssessSurvey;
import com.gf.biz.wmPerformanceAllPoints.mapper.BfTakeoutAssessSurveyMapper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SyncWmTargetRunningPointsJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    public void execute() throws Exception {
        // TODO Auto-generated method stub
        logger.info("开始执行外卖目标分值计算");
        syncGetTargetRunningPointsData();

    }
    public void syncGetTargetRunningPointsData() throws Exception{
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        BfTakeoutAssessSurveyMapper bfTakeoutAssessSurveyMapper = SpringBeanUtil.getBean(BfTakeoutAssessSurveyMapper.class);
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        QueryWrapper<LcapDepartment4a79f3> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_classify","0");
        List<LcapDepartment4a79f3> lcapDepartment4a79f3List = lcapDepartment4a79f3Mapper.selectList(wrapper);
        for(LcapDepartment4a79f3 lcapDepartment4a79f3:lcapDepartment4a79f3List){
            QueryWrapper<BfTakeoutAssessSurvey> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_code",lcapDepartment4a79f3.getDeptCode());
            //queryWrapper.eq("status","1");//已确认
            //queryWrapper.eq("year",2019);//业务年
            //queryWrapper.eq("month",12);业务日
            List<BfTakeoutAssessSurvey> bfTakeoutAssessSurveyList =bfTakeoutAssessSurveyMapper.selectList(queryWrapper);
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
            bdIndicatorDeptScore.setIndicatorName("外卖");//预留指标名称字段
            bdIndicatorDeptScore.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            bdIndicatorDeptScore.setCreatedTime(new Date());
            bdIndicatorDeptScore.setDeletedFlag(CommonConstant.STATUS_DEL);
            BigDecimal MTScore = BigDecimal.ZERO;
            BigDecimal finalScore = BigDecimal.ZERO;
            BigDecimal elmeScore = BigDecimal.ZERO;
            BigDecimal MTTimeScore = BigDecimal.ZERO;
            BigDecimal transitionScore = BigDecimal.ZERO;
            if(bfTakeoutAssessSurveyList.size()>0) {
                for (BfTakeoutAssessSurvey bfTakeoutAssessSurvey : bfTakeoutAssessSurveyList) {
                    if (bfTakeoutAssessSurvey.getMeituanPoints()!=null) {
                        logger.info("外卖得分为：" + bfTakeoutAssessSurvey.getMeituanPoints());
                        MTScore = MTScore.add(new BigDecimal(bfTakeoutAssessSurvey.getMeituanPoints()));
                    } else {
                        MTScore = MTScore.add(new BigDecimal(0));
                    }
                    if (bfTakeoutAssessSurvey.getElemePoints()!=null) {
                        elmeScore = elmeScore.add(new BigDecimal(bfTakeoutAssessSurvey.getElemePoints()));

                    } else {
                        elmeScore = elmeScore.add(new BigDecimal(0));
                    }
                    if (bfTakeoutAssessSurvey.getMeituanTakeoutDeliveryTimePoints() != null) {
                        MTTimeScore = MTTimeScore.add(new BigDecimal(bfTakeoutAssessSurvey.getMeituanTakeoutDeliveryTimePoints()));
                    } else {
                        MTTimeScore = MTTimeScore.add(new BigDecimal(0));
                    }
                }
                String elemeErrorMsg = null;
                String MtErrorMsg = null;
                String MTTimeErrorMsg = null;
                if (MTScore.compareTo(BigDecimal.valueOf(0)) > 0) {//避免除0
                    MTScore = MTScore.divide(new BigDecimal(bfTakeoutAssessSurveyList.size()), 3, BigDecimal.ROUND_HALF_UP);
                    MTScore = MTScore.divide(new BigDecimal(110),3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1)).divide(new BigDecimal(3),3,BigDecimal.ROUND_HALF_UP);
                    MtErrorMsg = "美团得分非空;";
                } else {
                    MtErrorMsg = "美团得分为空;";
                }
                if (elmeScore.compareTo(BigDecimal.valueOf(0)) > 0) {
                    elmeScore = elmeScore.divide(new BigDecimal(bfTakeoutAssessSurveyList.size()), 3, BigDecimal.ROUND_HALF_UP);
                    elmeScore = elmeScore.divide(new BigDecimal(110),3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1)).divide(new BigDecimal(3),3,BigDecimal.ROUND_HALF_UP);
                    elemeErrorMsg = "饿了么得分非空;";
                } else {
                     elemeErrorMsg = "饿了么得分为空;";
                }
                if (MTTimeScore.compareTo(BigDecimal.valueOf(0)) > 0) {
                    MTTimeScore = MTTimeScore.divide(new BigDecimal(bfTakeoutAssessSurveyList.size()), 3, BigDecimal.ROUND_HALF_UP);
                    MTTimeScore = MTTimeScore.divide(new BigDecimal(100),3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1)).divide(new BigDecimal(3), 3,BigDecimal.ROUND_HALF_UP);
                    MTTimeErrorMsg = "外卖配送时间得分非空。";;
                } else {
                    MTTimeErrorMsg = "外卖配送时间得分为空。";
                }
                if ((MTScore.compareTo(BigDecimal.valueOf(0))) > 0 && (elmeScore.compareTo(BigDecimal.valueOf(0)) > 0) && (MTTimeScore.compareTo(BigDecimal.valueOf(0)) > 0)) {
                    finalScore = MTScore.add(elmeScore).add(MTTimeScore).multiply(new BigDecimal(0.22)).multiply(new BigDecimal(0.49)).multiply(new BigDecimal(100)).divide(new BigDecimal(1),3,BigDecimal.ROUND_HALF_UP);
                    bdIndicatorDeptScore.setFinalScore(finalScore);
                } else {
                    bdIndicatorDeptScore.setRemark(MtErrorMsg + elemeErrorMsg + MTTimeErrorMsg);
                }
            }else{
                bdIndicatorDeptScore.setRemark("未查询到该部门外卖数据信息，无法计算");
            }
            bdIndicatorDeptScoreMapper.insert(bdIndicatorDeptScore);

        }
        logger.info("外卖目标分值计算完成");
    }
}
