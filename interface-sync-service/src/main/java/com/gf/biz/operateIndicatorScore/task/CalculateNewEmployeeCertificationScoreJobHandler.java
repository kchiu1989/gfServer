package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BfCertificateAccessSurveyMapper;
import com.gf.biz.codewaveBizForm.po.BfCertificateAccessSurvey;
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
import java.util.Date;
import java.util.List;

/**
 * 计算新员工一证获取率
 * 数据提取：
 * Q1新员工单证获证率=(12月1日-2月28日)入职员工单证获证人数/(12月1日-2月28日)入职员工入职人数*100%，4月初出Q1数据，数据纳入3月绩效
 * Q2新员工单证获证率=(3月1日-5月31日)入职员工单证获证人数/(3月1日-5月31日)入职员工入职人数*100%，7月初出Q2数据，数据纳入6月绩效
 * Q3新员工单证获证率=(6月1日-8月31日)入职员工单证获证人数/(6月1日-8月31日)入职员工入职人数*100%，10月初出Q3数据，数据纳入9月绩效
 * Q4新员工单证获证率=(9月1日-11月30日)入职员工单证获证人数/(9月1日-11月30日)入职员工入职人数*100%，下年1月初出Q4数据，数据纳入12月绩效
 * 考核公式：
 * 考核周期内，门店单证获证率
 * ①90% ≦M          得100分
 * ①80%≦M<90%       得90分
 * ②70%≦M<80%       得80分
 * ③60%≦M<70%       得70分
 * ③M<60%            得50分
 *
 * IDP 维度权重 6% 一级指标权重 50%
 */
public class CalculateNewEmployeeCertificationScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalculateNewEmployeeCertificationScoreJobHandler.class);

    private static final String PI_CODE="PI0004";
    private static final String PI_NAME="新员工一证获取率";
    private static final BigDecimal[] rateArray = new BigDecimal[]{new BigDecimal("0.9"),
            new BigDecimal("0.8"),new BigDecimal("0.7"),new BigDecimal("0.6")};
    private static final BigDecimal[] middleScoreArray = new BigDecimal[]{new BigDecimal("100"),
            new BigDecimal("90"),new BigDecimal("80"),new BigDecimal("70"),new BigDecimal("50")};
    private static final BigDecimal dimensionWeight = new BigDecimal("0.06");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.5");

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

            }else{
                logger.error("任务参数缺失");
            }


        } else {
            logger.info("执行所有运营部门绩效跑分...");
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
            queryWrapper.eq("dept_classify",BizCommonConstant.DEPT_CLASSIFY_OPT);
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

    //跑季度
    private void calculateScore(LcapDepartment4a79f3 dept, Integer currentYear, Integer jobQuarter) {
        //查询一证获取率业务表单
        //根据月计算季度

        BfCertificateAccessSurveyMapper bfCertificateAccessSurveyMapper = SpringBeanUtil.getBean(BfCertificateAccessSurveyMapper.class);
        QueryWrapper<BfCertificateAccessSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", dept.getDeptCode());
        queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
        queryWrapper.eq("year",currentYear);
        queryWrapper.eq("quarter",jobQuarter);
        queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG,CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("status","9");

        BfCertificateAccessSurvey dbData = bfCertificateAccessSurveyMapper.selectOne(queryWrapper);
        String remark = null;
        BigDecimal middleScore =null;
        BigDecimal finalScore=null;

        if(dbData!=null && dbData.getAchieveRate()!=null){
            BigDecimal achieveRate = dbData.getAchieveRate();
            middleScore = BigDecimal.ZERO;
            finalScore = middleScore;
            if(achieveRate.compareTo(rateArray[0])>=0){
                middleScore=middleScoreArray[0];
            }else if(achieveRate.compareTo(rateArray[1])>=0 && achieveRate.compareTo(rateArray[0])<0){
                middleScore=middleScoreArray[1];
            }else if(achieveRate.compareTo(rateArray[2])>=0 && achieveRate.compareTo(rateArray[1])<0){
                middleScore=middleScoreArray[2];
            }else if(achieveRate.compareTo(rateArray[3])>=0 && achieveRate.compareTo(rateArray[2])<0){
                middleScore=middleScoreArray[3];
            }else{
                middleScore=middleScoreArray[4];
            }

            finalScore = middleScore.multiply(dimensionWeight).multiply(firstLevelIndicatorWeight)
                    .setScale(3, RoundingMode.HALF_UP);
        }else{
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }

        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(currentYear,jobQuarter,dept.getName(),dept.getDeptCode(),
                dept.getId(),dept.getDeptClassify(),finalScore,middleScore,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);
    }
}
