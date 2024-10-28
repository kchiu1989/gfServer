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
import java.util.List;

public class CalcRegulableCostJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    private static final String PI_CODE = "PI0016";
    private static final String PI_NAME = "可控费用率";
    private static final BigDecimal dimensionWeight = new BigDecimal("0.215");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.45");
    private static final String status = "1";//代表生效
    private static final String bdItemCode = "J30003";
    private static final String bdItemAllProceeds = "1001";


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
                    this.getRegulableCostScore(dept, currentYear, currentMonth);
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
                this.getRegulableCostScore(dbDept, currentYear, currentMonth);
            }
        }

    }
    public void getRegulableCostScore(LcapDepartment4a79f3 currentDept, Integer year, Integer month) throws Exception{
        BdMonthBudgetMapper bfBusinessAllProceedsMapper = SpringBeanUtil.getBean(BdMonthBudgetMapper.class);
        QueryWrapper<BdMonthBudget> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", currentDept.getDeptCode());
        queryWrapper.eq("year", year);
        queryWrapper.eq("month", month);
        queryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status", status);
        queryWrapper.eq("bd_item_code", bdItemCode);
        queryWrapper.eq("dept_classify_code","0");
        List<BdMonthBudget> bfRegulableCostList = bfBusinessAllProceedsMapper.selectList(queryWrapper);
        QueryWrapper<BdMonthBudget> allProceedsQueryWrapper = new QueryWrapper<>();
        allProceedsQueryWrapper.eq("dept_code", currentDept.getDeptCode());
        allProceedsQueryWrapper.eq("year", year);
        allProceedsQueryWrapper.eq("month", month);
        allProceedsQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        allProceedsQueryWrapper.eq("status", status);
        allProceedsQueryWrapper.eq("bd_item_code", bdItemCode);
        allProceedsQueryWrapper.eq("dept_classify_code","0");
        List<BdMonthBudget> bfBusinessAllProceedsList = bfBusinessAllProceedsMapper.selectList(allProceedsQueryWrapper);
        BigDecimal finalScore = BigDecimal.ZERO;
        BigDecimal middleScore = BigDecimal.ZERO;
        BigDecimal actualValue = BigDecimal.ZERO;
        BigDecimal achieveRate = BigDecimal.ZERO;
        BigDecimal planRate = BigDecimal.ZERO;
        BigDecimal M=BigDecimal.ZERO;
        int count = 0;
        String remark = "";
        if (bfBusinessAllProceedsList != null && bfBusinessAllProceedsList.size() > 0&& bfRegulableCostList != null && bfRegulableCostList.size()>0) {
            BdMonthBudget bfRegulableCost = bfRegulableCostList.get(0);
            BdMonthBudget bfBusinessAllProceeds = bfRegulableCostList.get(0);
            if(bfRegulableCost.getActualValue()!=null) {
                if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(1600000)) >= 0) {
                    planRate = new BigDecimal(0.19);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(1400000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(1600000)) < 0) {
                    planRate = new BigDecimal(0.2);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(1200000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(1400000)) < 0) {
                    planRate = new BigDecimal(0.22);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(1100000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(1200000)) < 0) {
                    planRate = new BigDecimal(0.23);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(1000000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(1100000)) < 0) {
                    planRate = new BigDecimal(0.245);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(900000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(1000000)) < 0) {
                    planRate = new BigDecimal(0.255);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(800000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(900000)) < 0) {
                    planRate = new BigDecimal(0.265);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(700000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(800000)) < 0) {
                    planRate = new BigDecimal(0.275);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(600000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(700000)) < 0) {
                    planRate = new BigDecimal(0.285);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(500000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(600000)) < 0) {
                    planRate = new BigDecimal(0.33);
                } else if (bfRegulableCost.getActualValue().compareTo(new BigDecimal(400000)) >= 0 && bfRegulableCost.getActualValue().compareTo(new BigDecimal(500000)) < 0) {
                    planRate = new BigDecimal(0.37);
                } else {
                    planRate = new BigDecimal(0.39);
                }
                if(bfRegulableCost.getActualValue()!= null){
                    M=bfRegulableCost.getActualValue().subtract(planRate);
                    if(M.compareTo(new BigDecimal(0))<=0){
                        middleScore = new BigDecimal(100);
                    } else if (M.compareTo(new BigDecimal(0))>0&&M.compareTo(new BigDecimal(0))<=0.015) {
                        middleScore = new BigDecimal(90);
                    } else if (M.compareTo(new BigDecimal(0.015))>0&&M.compareTo(new BigDecimal(0))<=0.03) {
                        middleScore = new BigDecimal(80);
                    } else if (M.compareTo(new BigDecimal(0.03))>0&&M.compareTo(new BigDecimal(0))<=0.045) {
                        middleScore = new BigDecimal(70);
                    }else if(M.compareTo(new BigDecimal(0.045))>=0){
                        middleScore = new BigDecimal(50);
                    }
                    finalScore = middleScore.multiply(firstLevelIndicatorWeight).multiply(dimensionWeight);
                }else{
                    remark = "未查询到实际可控费用率";
                }




            }else{
                remark = "未查询到营业额";
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
