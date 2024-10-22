package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BfFoodSafetyInspectMapper;
import com.gf.biz.codewaveBizForm.po.BfFoodSafetyInspect;
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
 * 业务发生为按照季度
 * 运营部门 食安检查指标 绩效算分
 * (1)实际得分成绩≥75分且无红线问题，按实际得分*权重计入“食安检查指标”得分；
 * (2)实际得分成绩＜75分，食安检查指标得分直接为0分。
 * 直接取食安二次打分表的最终得分（已加权）
 */
public class CalcFoodSafetyInspectionScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcFoodSafetyInspectionScoreJobHandler.class);
    private static final String PI_CODE = "PI0012";
    private static final String PI_NAME = "食安检查";


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

                queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
                queryWrapper.isNotNull("dept_code");
                List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
                if (deptList != null && deptList.size() > 0) {
                    for (LcapDepartment4a79f3 dept : deptList) {
                        this.calculateScore(dept, jobYear, jobQuarter);
                    }
                }

            } else {
                logger.error("任务参数缺失");
            }


        } else {
            logger.info("执行所有运营部门绩效跑分...");
            int quarterFirstMonth = TimeUtil.getFirstSeasonMonth(currentDate);
            if (currentMonth != quarterFirstMonth) {
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
            queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
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

        BfFoodSafetyInspectMapper bfFoodSafetyInspectMapper = SpringBeanUtil.getBean(BfFoodSafetyInspectMapper.class);
        QueryWrapper<BfFoodSafetyInspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", jobYear);
        queryWrapper.eq("quarter", jobQuarter);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status", "4");
        queryWrapper.eq("depart_id",dept.getId());

        queryWrapper.eq("enable_flag", "9");

        List<BfFoodSafetyInspect> monthBudgetList = bfFoodSafetyInspectMapper.selectList(queryWrapper);


        BigDecimal weightedScore = null;
        String remark = null;


        if (monthBudgetList != null && monthBudgetList.size() > 0) {

            weightedScore = monthBudgetList.get(0).getFinalScore();


        } else {
            logger.error("未查询到该季度的食安数据...");
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }

        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(jobYear, jobQuarter, dept.getName(), dept.getDeptCode(),
                dept.getId(), dept.getDeptClassify(), weightedScore, null,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}
