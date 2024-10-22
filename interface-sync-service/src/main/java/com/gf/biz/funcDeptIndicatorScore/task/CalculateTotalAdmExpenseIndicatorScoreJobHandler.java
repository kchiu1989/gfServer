package com.gf.biz.funcDeptIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BdMonthBudgetMapper;
import com.gf.biz.codewaveBizForm.po.BdMonthBudget;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.service.BdIndicatorDeptScoreService;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 职能
 * CPI ->管理指标 ->管理费用合计
 * M≦100% 得100分
 * 100%<M≦110% 得90分；
 * 110%<M≦120% 得80分
 * 120%<M≦130% 得50分；
 * 130%< M 得0分
 */
public class CalculateTotalAdmExpenseIndicatorScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalculateTotalAdmExpenseIndicatorScoreJobHandler.class);
    private static final String PI_CODE="PI0005";
    private static final String PI_NAME="管理费用合计";
    private static final BigDecimal[] rateArray = new BigDecimal[]{new BigDecimal("1"),
            new BigDecimal("1.1"),new BigDecimal("1.2"),new BigDecimal("1.3")};
    private static final BigDecimal[] middleScoreArray = new BigDecimal[]{new BigDecimal("100"),
            new BigDecimal("90"),new BigDecimal("80"),new BigDecimal("50"),new BigDecimal("0")};
    private static final BigDecimal dimensionWeight = new BigDecimal("0.3");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.7");
    private static final BigDecimal secondLevelIndicatorWeight = new BigDecimal("0.45");

    @Override
    public void execute() throws Exception {

        Date currentDate = new Date();

        //定时任务默认当前月跑上个月的数据
        Integer currentYear = TimeUtil.getYear(currentDate);
        Integer currentQuarter = TimeUtil.getSeason(currentDate);
        Integer currentMonth = TimeUtil.getMonth(currentDate);

        Integer jobYear = null;
        Integer jobQuarter = null;



        String jobParam = XxlJobHelper.getJobParam();


        if (StringUtils.isNotBlank(jobParam)) {


            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("quarter")) {
                jobYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                jobQuarter = Integer.parseInt(xxlJobJsonObj.getString("quarter"));

                logger.info("系统使用了正确的动态传参，将执行动态传参逻辑");
                String jobDeptCode = null;
                if (xxlJobJsonObj.containsKey("deptCode")) {
                    jobDeptCode = xxlJobJsonObj.getString("deptCode");
                }

                logger.info("年:{},季度:{},部门编码:{}", jobYear, jobQuarter, jobDeptCode);


                if (jobDeptCode != null) {
                    logger.info("执行特定部门绩效跑分...,部门:{}", jobDeptCode);
                }

                LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
                QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
                //queryWrapper.eq("dept_classify", jobDeptClassify);
                if (StringUtils.isNotBlank(jobDeptCode)) {
                    queryWrapper.eq("dept_code", jobDeptCode);
                }

                queryWrapper.eq("dept_classify", "1");
                queryWrapper.isNotNull("dept_code");
                List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
                if (deptList != null && deptList.size() > 0) {
                    for (LcapDepartment4a79f3 dept : deptList) {
                        this.calculateScore(dept, jobYear, jobQuarter);
                    }
                }

                return;

            }else{
                logger.error("任务参数缺失");
            }


        } else {
            logger.info("执行所有职能部门绩效跑分...");
            int quarterFirstMonth = TimeUtil.getFirstSeasonMonth(currentDate);
            if(currentMonth!=quarterFirstMonth){
                logger.info("当前月:{}，不为季度第一个月:{},不执行任务", currentMonth, quarterFirstMonth);
                return;
            }

            logger.info("年:{},季度:{}", jobYear, jobQuarter);
            if (currentQuarter == 1) {
                jobYear = --currentYear;
                jobQuarter = 4;
            } else {
                jobQuarter = --currentQuarter;
            }

            QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("dept_classify", jobDeptClassify);
            queryWrapper.eq("dept_classify", "1");
            queryWrapper.isNotNull("dept_code");
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.calculateScore(dept, jobYear, jobQuarter);
                }
            }
        }

    }

    private void calculateScore(LcapDepartment4a79f3 dept, Integer jobYear, Integer jobQuarter) {
        Integer[] months=TimeUtil.getSeasonMonths(jobQuarter);
        BdMonthBudgetMapper bdMonthBudgetMapper = SpringBeanUtil.getBean(BdMonthBudgetMapper.class);
        QueryWrapper<BdMonthBudget> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", dept.getDeptCode());
        queryWrapper.eq("dept_classify", "1");
        queryWrapper.eq("year", jobYear);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        queryWrapper.in("month", Arrays.asList(months));
        List<BdMonthBudget> monthBudgetList = bdMonthBudgetMapper.selectList(queryWrapper);

        String remark=null;
        BigDecimal achieveRate = null;
        BigDecimal score = null;
        BigDecimal weightedScore = null;

        BigDecimal sumPlanValue = BigDecimal.ZERO;
        BigDecimal sumActualValue = BigDecimal.ZERO;

        if(monthBudgetList != null && monthBudgetList.size() > 0) {

            for(BdMonthBudget monthBudget : monthBudgetList) {
                sumPlanValue=sumPlanValue.add(monthBudget.getPlanValue()==null?BigDecimal.ZERO:monthBudget.getPlanValue())
                        .add(monthBudget.getPlanAdjustmentValue()==null?BigDecimal.ZERO:monthBudget.getPlanAdjustmentValue());

                sumActualValue=sumActualValue.add(monthBudget.getActualValue()==null?BigDecimal.ZERO:monthBudget.getActualValue())
                        .add(monthBudget.getActualAdjustmentValue()==null?BigDecimal.ZERO:monthBudget.getActualAdjustmentValue());
            }

            if(sumPlanValue.compareTo(BigDecimal.ZERO)==0){
                logger.error("计划值为0，无法计算");
                remark = "计划值为0，无法计算";
            }else{
                achieveRate = sumActualValue.divide(sumPlanValue, 3, BigDecimal.ROUND_HALF_UP);

                if(achieveRate.compareTo(rateArray[0])<=0){
                    score=middleScoreArray[0];
                }else if ( achieveRate.compareTo(rateArray[0])>0 && achieveRate.compareTo(rateArray[1])<=0) {
                    score=middleScoreArray[1];
                } else if (achieveRate.compareTo(rateArray[1])>0 && achieveRate.compareTo(rateArray[2])<=0) {
                    score=middleScoreArray[2];
                } else if(achieveRate.compareTo(rateArray[2])>0 && achieveRate.compareTo(rateArray[3])<=0){
                    score=middleScoreArray[3];
                }else{
                    score=middleScoreArray[4];
                }

                weightedScore = score.multiply(dimensionWeight).multiply(firstLevelIndicatorWeight)
                        .multiply(secondLevelIndicatorWeight).setScale(3, RoundingMode.HALF_UP);
            }



        }else{
            logger.error("未查询到该季度的管理费用数据...");
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }

        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(jobYear,jobQuarter,dept.getName(),dept.getDeptCode(),
                dept.getId(),dept.getDeptClassify(),weightedScore,score,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}
