package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.entity.BfMtdzPlatformData;
import com.gf.biz.operateIndicatorScore.mapper.BfMtdzPlatformDataMapper;
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

public class CaleMtdzPlatdormJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    private static final String PI_CODE = "PI0013";
    private static final String PI_NAME = "美团大众评分";
    private static final BigDecimal dimensionWeight = new BigDecimal("0.12");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.49");
    private static final String status = "2";//代表生效
    public void execute() throws Exception {
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
                    this.getMtdzPlatformData(dept, currentYear, currentMonth);
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
                this.getMtdzPlatformData(dbDept, currentYear, currentMonth);
            }
        }

    }
    public void getMtdzPlatformData(LcapDepartment4a79f3 currentDept,Integer year, Integer month) throws Exception{
        BfMtdzPlatformDataMapper bfMtdzPlatformDataMapper = SpringBeanUtil.getBean(BfMtdzPlatformDataMapper.class);
        QueryWrapper<BfMtdzPlatformData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code",currentDept.getDeptCode());
        queryWrapper.eq("business_year",year);
        queryWrapper.eq("business_month",month);
        queryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status",status);
        List<BfMtdzPlatformData> bfMtdzPlatformDataList = bfMtdzPlatformDataMapper.selectList(queryWrapper);
        BigDecimal MTMiddleScore = BigDecimal.ZERO;
        BigDecimal MTFinalScore = BigDecimal.ZERO;
        BigDecimal DZMiddleScore = BigDecimal.ZERO;
        BigDecimal DZFinalScore = BigDecimal.ZERO;
        BigDecimal finalScore = BigDecimal.ZERO;
        BigDecimal middleScore = BigDecimal.ZERO;
        BigDecimal achieveRate = BigDecimal.ZERO;
        int count = 0;
        String remark = "";
        if(bfMtdzPlatformDataList != null && bfMtdzPlatformDataList.size()>0) {
            BfMtdzPlatformData bfMtdzPlatformData = bfMtdzPlatformDataList.get(0);
            if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(5)) >= 0) {
                MTMiddleScore = new BigDecimal(110);
            } else if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(4.9)) >= 0) {
                MTMiddleScore = new BigDecimal(105);
            } else if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(4.8)) >= 0) {
                MTMiddleScore = new BigDecimal(100);
            } else if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(4.7)) >= 0) {
                MTMiddleScore = new BigDecimal(90);
            } else if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(4.6)) >= 0) {
                MTMiddleScore = new BigDecimal(80);
            } else if (bfMtdzPlatformData.getMtAchieveScore().compareTo(new BigDecimal(4.5)) >= 0) {
                MTMiddleScore = new BigDecimal(70);
            } else {
                MTMiddleScore = new BigDecimal(60);
            }
            achieveRate = new BigDecimal(bfMtdzPlatformData.getDzPositiveReviewNumber()).divide(new BigDecimal(bfMtdzPlatformData.getDzTotalReviewNumber()), 2, BigDecimal.ROUND_HALF_UP);
            DZMiddleScore = achieveRate.multiply(new BigDecimal(100)).divide(new BigDecimal(1),3, BigDecimal.ROUND_HALF_UP);

            MTFinalScore= MTMiddleScore.multiply(new BigDecimal(0.5));
            DZFinalScore= DZMiddleScore.multiply(new BigDecimal(0.5));
        }else{
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }
        middleScore =MTMiddleScore.add(DZMiddleScore);
        finalScore = MTFinalScore.add(DZFinalScore).multiply(firstLevelIndicatorWeight).multiply(dimensionWeight);

        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(year, month, currentDept.getName(), currentDept.getDeptCode(),
                currentDept.getId(), "0", finalScore, middleScore,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);
        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);
    }


}
