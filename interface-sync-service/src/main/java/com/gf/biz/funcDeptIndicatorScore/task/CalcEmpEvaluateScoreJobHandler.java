package com.gf.biz.funcDeptIndicatorScore.task;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.funcDeptIndicatorScore.entity.BfDeptServiceEvaluateDept;
import com.gf.biz.funcDeptIndicatorScore.entity.BfEmployeeSatisfySurvey;
import com.gf.biz.funcDeptIndicatorScore.mapper.BfDeptServiceEvaluateDeptMapper;
import com.gf.biz.funcDeptIndicatorScore.mapper.BfEmployeeSatisfySurveyMapper;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.service.BdIndicatorDeptScoreService;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CalcEmpEvaluateScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    private static final String PI_CODE = "PI0018";
    private static final String PI_NAME = "员工满意度";
    private static final BigDecimal dimensionWeight = new BigDecimal("0.15");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.30");
    private static final BigDecimal secondLevelIndicatorWeight = new BigDecimal("0.70");
    private static final String status = "2";//代表生效

    public void execute() throws Exception
    {
        Integer currentYear = TimeUtil.getNowYear();
        Integer currentQuarter = TimeUtil.getSeason(new Date());
        if (currentQuarter == 1) {
            currentYear--;
            currentQuarter = 4;
        } else {
            currentQuarter--;
        }
        String jobParam = XxlJobHelper.getJobParam();
        String jobDeptCode = null;
        String jobDeptClassify = "1";

        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        if (StringUtils.isNotBlank(jobParam)) {//动态传参
            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("quarter")) {
                currentYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                currentQuarter = Integer.parseInt(xxlJobJsonObj.getString("quarter"));
            }
            logger.info("使用动态传参");
            if (xxlJobJsonObj.containsKey("deptCode")) {
                jobDeptCode = xxlJobJsonObj.getString("deptCode");
            }
        }
        logger.info("年:{},季度:{},部门编码:{}", currentYear, currentQuarter, jobDeptCode);


        if (jobDeptCode != null) {
            logger.info("特定部门跑分");
            QueryWrapper<LcapDepartment4a79f3> Wrapper = new QueryWrapper<>();
            Wrapper.eq("dept_code", jobDeptCode);
            Wrapper.eq("dept_classify", jobDeptClassify);
            Wrapper.isNotNull("dept_code");
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(Wrapper);
            if(deptList != null && deptList.size()>0){
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.getEmpEvaluateScore(dept, currentYear, currentQuarter);
                }
            }
            return;
        }
        QueryWrapper<LcapDepartment4a79f3> Wrapper = new QueryWrapper<>();
        //Wrapper.eq("dept_code", jobDeptCode);
        Wrapper.eq("dept_classify", jobDeptClassify);
        Wrapper.isNotNull("dept_code");
        List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(Wrapper);
        if(deptList != null && deptList.size()>0){
            for (LcapDepartment4a79f3 dept : deptList) {
                this.getEmpEvaluateScore(dept, currentYear, currentQuarter);
            }
        }



    }
    public void getEmpEvaluateScore(LcapDepartment4a79f3 dept, Integer year, Integer quarter) throws Exception{
        BfEmployeeSatisfySurveyMapper bfEmployeeSatisfySurveyMapper = SpringBeanUtil.getBean(BfEmployeeSatisfySurveyMapper.class);
        QueryWrapper<BfEmployeeSatisfySurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", dept.getDeptCode());
        queryWrapper.eq("status", status);//已生效
        queryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        queryWrapper.eq("business_year", year);
        queryWrapper.eq("business_season", quarter);
        List<BfEmployeeSatisfySurvey> empList = bfEmployeeSatisfySurveyMapper.selectList(queryWrapper);
        BigDecimal finalScore = BigDecimal.ZERO;
        BigDecimal middleScore = BigDecimal.ZERO;
        String remark = "";
        int count = 0;
        if(empList != null && empList.size()>0){
            for (BfEmployeeSatisfySurvey emp : empList) {
                middleScore = middleScore.add(emp.getTotalScore());
                count++;
            }
            middleScore = middleScore.divide(new BigDecimal(empList.size()), 3, BigDecimal.ROUND_HALF_UP);
            finalScore = middleScore.multiply(dimensionWeight).multiply(firstLevelIndicatorWeight).multiply(secondLevelIndicatorWeight);
            remark = count+"人参与评价";
        }else{
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        }


        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(year, quarter, dept.getName(), dept.getDeptCode(),
                dept.getId(), "1", finalScore, middleScore,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);
    }

}
