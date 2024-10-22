package com.gf.biz.operateIndicatorScore.task;

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
import java.util.Date;
import java.util.List;

/**
 * 数据源：樊登读书部门得分表
 * 职能部门
 * 考核公式：
 * 三个季度取平均
 */
public class CalcOptFdReadScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcOptFdReadScoreJobHandler.class);
    private static final String PI_CODE = "PI0011";
    private static final String PI_NAME = "樊登读书量";


    private static final BigDecimal dimensionWeight = new BigDecimal("0.06");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.5");

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

            } else {
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
            queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
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
        BfRecordFdStatisticMapper bfRecordFdStatisticMapper = SpringBeanUtil.getBean(BfRecordFdStatisticMapper.class);
        QueryWrapper<BfRecordFdStatistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", dept.getDeptCode());
        //queryWrapper.eq("dept_type_code", BizCommonConstant.DEPT_CLASSIFY_FUNC);
        queryWrapper.eq("year", jobYear);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status", "2");
        queryWrapper.eq("month", jobMonth);
        List<BfRecordFdStatistic> bfList = bfRecordFdStatisticMapper.selectList(queryWrapper);

        String remark = null;
        BigDecimal weightedScore = null;


        if (bfList != null && bfList.size() > 0) {
            BigDecimal middleScore = BigDecimal.ZERO;

            for (BfRecordFdStatistic single : bfList) {
                middleScore = middleScore.add(single.getFinalScore() == null ? BigDecimal.ZERO : single.getFinalScore());
            }

            weightedScore = middleScore.multiply(dimensionWeight).multiply(firstLevelIndicatorWeight)
                    .setScale(3, RoundingMode.HALF_UP);

        } else {
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }


        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(jobYear, jobMonth, dept.getName(), dept.getDeptCode(),
                dept.getId(), dept.getDeptClassify(), weightedScore, null,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_MONTH, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}
