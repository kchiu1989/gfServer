package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BdMonthBudgetMapper;
import com.gf.biz.codewaveBizForm.po.BdMonthBudget;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalcBusinessAllProceedsJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    private static final String PI_CODE = "PI0014";
    private static final String PI_NAME = "主营业务总收入";
    private static final BigDecimal dimensionWeight = new BigDecimal("0.28");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.45");
    private static final String status = "1";//代表生效
    private static final String bdItemCode = "1001";
    private static final List<BigDecimal> rateArray = Collections.unmodifiableList(Arrays.asList(new BigDecimal("1.1"), new BigDecimal("0.5")));

    public void execute() throws Exception{
        //定时任务默认当前月跑上个月的数据
        Integer currentYear = TimeUtil.getNowYear();
        Integer currentMonth = TimeUtil.getNowMonth();
        if (currentMonth == 1) {
            currentYear--;
            currentMonth = 12;
        } else {
            currentMonth--;
        }

        String jobParam = XxlJobHelper.getJobParam();
        String jobDeptCode = null;


        if (StringUtils.isNotBlank(jobParam)) {
            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("month")) {
                currentYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                currentMonth = Integer.parseInt(xxlJobJsonObj.getString("month"));
            }

            logger.info("使用动态传参");

            if (xxlJobJsonObj.containsKey("deptCode")) {
                jobDeptCode = xxlJobJsonObj.getString("deptCode");
            }
        }

        logger.info("年:{},月:{},部门编码:{}", currentYear, currentMonth, jobDeptCode);

        if (jobDeptCode != null) {
            logger.info("特定部门跑分");
            //先跑运营部门
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_code", jobDeptCode);
            queryWrapper.eq("dept_classify", "0");
            queryWrapper.isNotNull("dept_code");
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.getProceedsScore(dept, currentYear, currentMonth);
                }
            }

            return;
        }

        //先跑运营部门
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_classify", "0");
        queryWrapper.isNotNull("dept_code");
        List<LcapDepartment4a79f3> optDeptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
        if (optDeptList != null && optDeptList.size() > 0) {
            for (LcapDepartment4a79f3 dbDept : optDeptList) {
                logger.info("开始进行对部门:{},部门编码:{},的处理", dbDept.getName(), dbDept.getDeptCode());
                this.getProceedsScore(dbDept, currentYear, currentMonth);
            }
        }

    }
    public void getProceedsScore(LcapDepartment4a79f3 currentDept, Integer year, Integer month) throws Exception{
        BdMonthBudgetMapper bfBusinessAllProceedsMapper = SpringBeanUtil.getBean(BdMonthBudgetMapper.class);
        QueryWrapper<BdMonthBudget> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", currentDept.getDeptCode());
        queryWrapper.eq("year", year);
        queryWrapper.eq("month", month);
        queryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status", status);
        queryWrapper.eq("bd_item_code", bdItemCode);
        queryWrapper.eq("dept_classify_code","0");
        List<BdMonthBudget> bfBusinessAllProceedsList = bfBusinessAllProceedsMapper.selectList(queryWrapper);
        BigDecimal finalScore = BigDecimal.ZERO;
        BigDecimal middleScore = BigDecimal.ZERO;
        BigDecimal actualValue = BigDecimal.ZERO;
        BigDecimal planValue = BigDecimal.ZERO;
        BigDecimal achieveRate = BigDecimal.ZERO;
        int count = 0;
        String remark = "";
        if (bfBusinessAllProceedsList != null && bfBusinessAllProceedsList.size() > 0) {
            BdMonthBudget bfBusinessAllProceeds = bfBusinessAllProceedsList.get(0);
            if(bfBusinessAllProceeds.getActualValue()!= null){
                actualValue = actualValue.add(bfBusinessAllProceeds.getActualValue());
            }else{
                actualValue = actualValue.add(new BigDecimal(0));
            }
            if(bfBusinessAllProceeds.getActualAdjustmentValue()!= null){
                actualValue = actualValue.add(bfBusinessAllProceeds.getActualAdjustmentValue());
            }else{
                actualValue = actualValue.add(new BigDecimal(0));
            }
            if(bfBusinessAllProceeds.getPlanValue()!= null){
                planValue = actualValue.add(bfBusinessAllProceeds.getPlanValue());
            }else{
                planValue = actualValue.add(new BigDecimal(0));
            }
            if(bfBusinessAllProceeds.getPlanAdjustmentValue()!= null){
                planValue = actualValue.add(bfBusinessAllProceeds.getPlanAdjustmentValue());
            }else{
                planValue = actualValue.add(new BigDecimal(0));
            }
            if(planValue.compareTo(BigDecimal.ZERO)==0){
                logger.error("计划值为0，无法计算");
                remark = "计划值为0，无法计算";
            }else{
                achieveRate = actualValue.divide(planValue, 3, BigDecimal.ROUND_HALF_UP);
                if(achieveRate.compareTo(rateArray.get(0))>0){
                    middleScore = new BigDecimal(110);
                }else if ( achieveRate.compareTo(rateArray.get(1))>=0 && achieveRate.compareTo(rateArray.get(0))<=0) {
                    middleScore = achieveRate.multiply(new BigDecimal(100));
                } else if (achieveRate.compareTo(rateArray.get(1))<=0 ) {
                    middleScore = new BigDecimal(50);
                }
                finalScore = middleScore.divide(new BigDecimal(110),3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).multiply(firstLevelIndicatorWeight).multiply(dimensionWeight);
            }

        } else{
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }
        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(year, month, currentDept.getName(), currentDept.getDeptCode(),
                currentDept.getId(), "0", finalScore, middleScore,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);
        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);
    }
}
