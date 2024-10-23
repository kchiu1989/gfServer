package com.gf.biz.funcDeptIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.fangdengRead.entity.BfRecordFdStatistic;
import com.gf.biz.fangdengRead.mapper.BfRecordFdStatisticMapper;
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
 * 数据源：樊登读书部门得分表
 * 职能部门
 * 考核公式：
 * 三个季度取平均
 */
public class CalcFuncFdReadScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcFuncFdReadScoreJobHandler.class);
    private static final String PI_CODE = "PI0011";
    private static final String PI_NAME = "樊登读书量";


    private static final BigDecimal dimensionWeight = new BigDecimal("0.05");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.5");
    private static final BigDecimal secondLevelIndicatorWeight = BigDecimal.ONE;

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

                queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_FUNC);
                queryWrapper.isNotNull("dept_code");
                List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
                if (deptList != null && deptList.size() > 0) {
                    for (LcapDepartment4a79f3 dept : deptList) {
                        this.calculateScore(dept, jobYear, jobQuarter);
                    }
                }

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
            queryWrapper.eq("dept_classify",BizCommonConstant.DEPT_CLASSIFY_FUNC);
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
        Integer[] months = TimeUtil.getSeasonMonths(jobQuarter);
        BfRecordFdStatisticMapper bfRecordFdStatisticMapper = SpringBeanUtil.getBean(BfRecordFdStatisticMapper.class);
        QueryWrapper<BfRecordFdStatistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", dept.getDeptCode());
        //queryWrapper.eq("dept_type_code", BizCommonConstant.DEPT_CLASSIFY_FUNC);
        queryWrapper.eq("year", jobYear);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status", "2");
        queryWrapper.in("month", Arrays.asList(months));
        List<BfRecordFdStatistic> bfList = bfRecordFdStatisticMapper.selectList(queryWrapper);

        String remark=null;
        BigDecimal weightedScore = null;


        if (bfList != null && bfList.size() > 0) {
            BigDecimal middleScore = BigDecimal.ZERO;

            for(BfRecordFdStatistic single:bfList){
                middleScore = middleScore.add(single.getFinalScore()==null? BigDecimal.ZERO:single.getFinalScore());
            }

            weightedScore = middleScore.multiply(dimensionWeight).multiply(firstLevelIndicatorWeight)
                    .multiply(secondLevelIndicatorWeight).divide(new BigDecimal(3),3, RoundingMode.HALF_UP);
        }else{
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }


        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(jobYear, jobQuarter, dept.getName(), dept.getDeptCode(),
                dept.getId(), dept.getDeptClassify(), weightedScore, null,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}