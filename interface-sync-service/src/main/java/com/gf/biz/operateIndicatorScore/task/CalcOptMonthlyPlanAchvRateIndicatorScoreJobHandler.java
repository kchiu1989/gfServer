package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BfBudgetPlanMonthlySummaryMapper;
import com.gf.biz.codewaveBizForm.po.BfBudgetPlanMonthlySummary;
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
import java.util.Date;
import java.util.List;

/**
 * 运营部门月度计划达成率绩效算分
 *
 * 职能：除核心指标以外的其他月度计划达成率（月度计划条数最低5条，否则此指标直接为0）
 * 直接取业务表的月度计划达成率
 * 取季度平均
 * <p>
 * 运营：除核心指标以外的其他月度计划达成率（月度计划条数最低5条，否则此指标直接为0）
 * 直接取业务表的月度计划达成率
 * 直接获取月度平均
 */
public class CalcOptMonthlyPlanAchvRateIndicatorScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcOptMonthlyPlanAchvRateIndicatorScoreJobHandler.class);
    private static final String PI_CODE = "PI0007";
    private static final String PI_NAME = "月度计划达成率";


    @Override
    public void execute() throws Exception {

        Date currentDate = new Date();

        //定时任务默认当前月跑上个月的数据
        Integer currentYear = TimeUtil.getYear(currentDate);
        Integer currentMonth = TimeUtil.getMonth(currentDate);

        Integer jobYear = null;
        Integer jobMonth = null;



        String jobParam = XxlJobHelper.getJobParam();


        if (StringUtils.isNotBlank(jobParam)) {


            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("month")) {
                jobYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                jobMonth = Integer.parseInt(xxlJobJsonObj.getString("month"));

                logger.info("系统使用了正确的动态传参，将执行动态传参逻辑");
                String jobDeptCode = null;
                if (xxlJobJsonObj.containsKey("deptCode")) {
                    jobDeptCode = xxlJobJsonObj.getString("deptCode");
                }

                logger.info("年:{},月度:{},部门编码:{}", jobYear, jobMonth, jobDeptCode);


                if (jobDeptCode != null) {
                    logger.info("执行特定部门绩效跑分...,部门:{}", jobDeptCode);
                }

                LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
                QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
                //queryWrapper.eq("dept_classify", jobDeptClassify);
                if (StringUtils.isNotBlank(jobDeptCode)) {
                    queryWrapper.eq("dept_code", jobDeptCode);
                }

                queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
                queryWrapper.isNotNull("dept_code");
                List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
                if (deptList != null && deptList.size() > 0) {
                    for (LcapDepartment4a79f3 dept : deptList) {
                        this.calculateScore(dept, jobYear, jobMonth);
                    }
                }

            }else{
                logger.error("任务参数缺失");
            }


        } else {
            logger.info("执行所有职能部门绩效跑分...");


            if (currentMonth == 1) {
                jobYear = --currentYear;
                jobMonth = 12;
            } else {
                jobMonth = --currentMonth;
            }

            logger.info("年:{},季度:{}", jobYear, jobMonth);

            QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("dept_classify", jobDeptClassify);
            queryWrapper.eq("dept_classify",BizCommonConstant.DEPT_CLASSIFY_OPT);
            queryWrapper.isNotNull("dept_code");
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.calculateScore(dept, jobYear, jobMonth);
                }
            }
        }
    }

    private void calculateScore(LcapDepartment4a79f3 dept, Integer jobYear, Integer jobMonth) {

        BfBudgetPlanMonthlySummaryMapper bfBudgetPlanMonthlySummaryMapper = SpringBeanUtil.getBean(BfBudgetPlanMonthlySummaryMapper.class);
        QueryWrapper<BfBudgetPlanMonthlySummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("target_dept_code", dept.getDeptCode());
        queryWrapper.eq("target_dept_category", "1");
        queryWrapper.eq("year", jobYear);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("enable_flag","9");
        queryWrapper.eq("month", jobMonth);
        List<BfBudgetPlanMonthlySummary> monthBudgetList = bfBudgetPlanMonthlySummaryMapper.selectList(queryWrapper);


        BigDecimal weightedScore = null;
        String remark = null;


        if (monthBudgetList != null && monthBudgetList.size() > 0) {

            weightedScore = monthBudgetList.get(0).getAchievementRate();


        } else {
            logger.error("未查询到该月度的月度计划数据...");
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }

        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(jobYear, jobMonth, dept.getName(), dept.getDeptCode(),
                dept.getId(), dept.getDeptClassify(), weightedScore, null,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_MONTH, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}
