package com.gf.biz.totalDeptIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BfPenaltyRecordMapper;
import com.gf.biz.codewaveBizForm.po.BfPenaltyRecord;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.gf.biz.totalDeptIndicatorScore.OptPerformanceIndocatorEnum;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalDto;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalScoreDto;
import com.gf.biz.totalDeptIndicatorScore.service.BfIndicatorDeptTotalService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 职能部门计算排名等级算法：
 * <p>
 * 1、直接对部门的季度指标各项进行加和 （包含部门的通用扣分项的扣分总数）
 * 2、生成表单，提供干预表单
 * <p>
 * 每个部门的加权总分=部门服务满意度+3-5项核心指标+部门员工满意度+部门办公室检查+管理费用合计+管理鉴定表+月度计划达成率
 * +协作数量+协作质量+部门培训计划+读书量
 * +（部门季度扣分项表单扣分总分）
 */
public class CalcFuncTotalDeptIndicatorScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcFuncTotalDeptIndicatorScoreJobHandler.class);

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


            } else {
                logger.error("任务参数缺失");
                return;
            }


        } else {
            logger.info("执行所有职能部门绩效跑分...");
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


        }

        QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("dept_classify", jobDeptClassify);
        queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_FUNC);
        queryWrapper.isNotNull("dept_code");
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);

        if (deptList != null && deptList.size() > 0) {
            List<BfIndicatorDeptTotalScoreDto> toCalcMetaDataList = new ArrayList<>();
            for (LcapDepartment4a79f3 dept : deptList) {
                toCalcMetaDataList.add(this.getDeptMetaScoreData(dept, jobYear, jobQuarter));
            }

            BfIndicatorDeptTotalDto toDeal = new BfIndicatorDeptTotalDto();
            toDeal.setYear(jobYear);
            toDeal.setMonthQuarter(jobQuarter);
            toDeal.setDeptClassifyFlag(BizCommonConstant.DEPT_CLASSIFY_FUNC);
            toDeal.setDimensionFlag("1");
            toDeal.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
            toDeal.setItemList(toCalcMetaDataList);

            BfIndicatorDeptTotalService bfIndicatorDeptTotalService = SpringBeanUtil.getBean(BfIndicatorDeptTotalService.class);
            try{
                bfIndicatorDeptTotalService.createOrUpdateData(toDeal);
            }catch (Exception e){
                logger.error("存储职能总分数据异常",e);
            }


        }

    }


    private BfIndicatorDeptTotalScoreDto getDeptMetaScoreData(LcapDepartment4a79f3 dept, Integer jobYear, Integer jobQuarter) {
        //先获取指标列表

        List<String> piList = OptPerformanceIndocatorEnum.getPiCodeList();

        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        List<BdIndicatorDeptScoreDto> deptInitialDatas = bdIndicatorDeptScoreMapper.getFuncDeptSumInitialScore(dept.getDeptCode(), jobYear,
                jobQuarter, piList);

        //Integer totalFoodDowngradeLevel = new Integer(0);
        BigDecimal totalDeductionScore = new BigDecimal(0);
        //Integer totalDowngradeLevel = 0;
        BigDecimal transitionScore = BigDecimal.ZERO;
        String remark = null;

        if (deptInitialDatas.isEmpty()) {
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        } else {
            transitionScore = deptInitialDatas.get(0).getFinalScore();

            Integer[] months = TimeUtil.getSeasonMonths(jobQuarter);


            //查询当前部门的通用扣分项表的扣分和等级
            BfPenaltyRecordMapper bfPenaltyRecordMapper = SpringBeanUtil.getBean(BfPenaltyRecordMapper.class);
            QueryWrapper<BfPenaltyRecord> queryWrapper = new QueryWrapper<>();


            queryWrapper.select("sum(IFNULL(total_deduction_score,0)) as total_deduction_score," +
                    "sum(IFNULL(downgrade_level,0)) as total_downgrade_level");
            queryWrapper.and(Wrapper -> Wrapper.eq("penalty_date", jobYear + "-" + String.format("%02d", months[0]))
                    .or().eq("penalty_date", jobYear + "-" + String.format("%02d", months[1]))
                    .or().eq("penalty_date", jobYear + "-" + String.format("%02d", months[2])));

            queryWrapper.eq("enable_flag", "1");
            queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, "0");
            queryWrapper.eq("dept_id", dept.getDeptId());
            queryWrapper.groupBy("dept_id");

            List<Map<String, Object>> penaltyMap = bfPenaltyRecordMapper.selectMaps(queryWrapper);

            if (penaltyMap != null && !penaltyMap.isEmpty()) {
                totalDeductionScore = new BigDecimal(penaltyMap.get(0).get("total_deduction_score").toString());
            }
        }

        return new BfIndicatorDeptTotalScoreDto(jobYear, jobQuarter,
                dept.getName(), dept.getDeptCode(), dept.getId(),
                dept.getDeptClassify(), transitionScore, BizCommonConstant.DEPT_CLASSIFY_OPT,
                totalDeductionScore, null, null, remark);

    }
}
