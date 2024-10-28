package com.gf.biz.totalDeptIndicatorScore.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.codewaveBizForm.mapper.BdDeptRankProportionMapper;
import com.gf.biz.codewaveBizForm.mapper.BfFoodSafetyInspectMapper;
import com.gf.biz.codewaveBizForm.mapper.BfPenaltyRecordMapper;
import com.gf.biz.codewaveBizForm.po.BdDeptRankProportion;
import com.gf.biz.codewaveBizForm.po.BfFoodSafetyInspect;
import com.gf.biz.codewaveBizForm.po.BfPenaltyRecord;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.gf.biz.totalDeptIndicatorScore.PerformanceIndocatorEnum;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalScoreDto;
import com.gf.biz.totalDeptIndicatorScore.mapper.BfIndicatorDeptTotalScoreMapper;
import com.gf.biz.totalDeptIndicatorScore.po.BfIndicatorDeptTotalScore;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 运营门店计算排名等级算法：
 * <p>
 * 1、季度或者半年度的，计入指定月，其他月不计分；
 * 2、将食安和通用扣分项的扣分扣分总数计入总分结果；
 * 3、按照等级比例，根据倒排好的分数，按照等级对应门店数，随机取门店
 * 4、根据食安和通用比例扣分项，进行等级降级
 * 5、生成表单，提供干预界面
 * <p>
 * <p>
 * 每个门店的加权总分= 主营业务总收入加权分+毛利率加权分+可控费用率加权分+平均人效加权分+食安检查加权分+神秘访客加权分+顾客反馈加权分+大众美团评分加权分+
 * 外卖指标加权分+门店QSC加权分+月度计划达成率加权分+读书量加权分+新员工单证获证率加权分
 * +（门店月扣分项表单扣分总分）
 */
public class CalcOptTotalDeptIndicatorScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CalcOptTotalDeptIndicatorScoreJobHandler.class);

    @Override
    public void execute() throws Exception {
        Date currentDate = new Date();

        //定时任务默认当前月跑上个月的数据
        Integer currentYear = TimeUtil.getYear(currentDate);
        Integer currentMonth = TimeUtil.getMonth(currentDate);

        Integer jobYear = null;
        Integer jobMonth = null;


        /*String jobParam = XxlJobHelper.getJobParam();


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


        } else {*/
            logger.info("执行所有职能部门绩效跑分...");


            if (currentMonth == 1) {
                jobYear = --currentYear;
                jobMonth = 12;
            } else {
                jobMonth = --currentMonth;
            }

            logger.info("年:{},月度:{}", jobYear, jobMonth);

            QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("dept_classify", jobDeptClassify);
            queryWrapper.eq("dept_classify", BizCommonConstant.DEPT_CLASSIFY_OPT);
            queryWrapper.isNotNull("dept_code");
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                List<BfIndicatorDeptTotalScoreDto> toCalcMetaDataList = new ArrayList<>();
                for (LcapDepartment4a79f3 dept : deptList) {
                    toCalcMetaDataList.add(this.getDeptMetaScoreData(dept, jobYear, jobMonth));
                }

                //进行算等级
                BdDeptRankProportionMapper bdDeptRankProportionMapper = SpringBeanUtil.getBean(BdDeptRankProportionMapper.class);
                QueryWrapper<BdDeptRankProportion> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq(CommonConstant.COLUMN_DEL_FLAG,CommonConstant.STATUS_UN_DEL);
                List<BdDeptRankProportion> bdDeptRankProportions = bdDeptRankProportionMapper.selectList(queryWrapper1);
                Map<String,Integer> rankMap = new HashMap<>();
                for(BdDeptRankProportion rankData:bdDeptRankProportions){
                    Integer rankAPlus = Integer.valueOf(String.valueOf(new BigDecimal
                            (deptList.size()).multiply(rankData.getProportionValue()).setScale(0, RoundingMode.CEILING)));

                    rankMap.put(rankData.getGradeCode(),rankAPlus);
                }

                for(BfIndicatorDeptTotalScoreDto toDeal:toCalcMetaDataList){
                    if(rankMap.containsKey("5")){
                        toDeal.setTransitionRank(Integer.valueOf("5"));
                        calcRankMap("5",rankMap);
                    }else if(rankMap.containsKey("4")){
                        toDeal.setTransitionRank(Integer.valueOf("4"));
                        calcRankMap("4",rankMap);
                    }else if(rankMap.containsKey("3")){
                        toDeal.setTransitionRank(Integer.valueOf("3"));
                        calcRankMap("3",rankMap);

                    }else if(rankMap.containsKey("2")){
                        toDeal.setTransitionRank(Integer.valueOf("2"));
                        calcRankMap("2",rankMap);
                    }else if(rankMap.containsKey("1")){//剩下的强制取D
                        toDeal.setTransitionRank(Integer.valueOf("1"));
                        //calcRankMap("5",rankMap);
                    }
                }


                BfIndicatorDeptTotalScoreMapper bfIndicatorDeptTotalScoreMapper = SpringBeanUtil.getBean(BfIndicatorDeptTotalScoreMapper.class);
                //再对一般扣分和食安扣分进行等级计算
                for(BfIndicatorDeptTotalScoreDto toDeal:toCalcMetaDataList){
                    int totalRankNum = toDeal.getTransitionRankNumber()+toDeal.getTransitionFoodRankNumber();
                    if(totalRankNum>4){//最多降四级
                        totalRankNum=4;
                    }

                    int finalRank = toDeal.getTransitionRank()-totalRankNum;

                    if(finalRank<=0){
                        toDeal.setFinalRank("1");
                    }else{
                        toDeal.setFinalRank(String.valueOf(finalRank));
                    }
                    try{
                        BfIndicatorDeptTotalScore toAdd = new BfIndicatorDeptTotalScore();
                        BeanUtils.copyProperties(toDeal,toAdd);
                        bfIndicatorDeptTotalScoreMapper.insert(toAdd);
                    }catch(Exception e){
                        logger.error("存储运营最终得分排名失败",e);
                    }

                }
            }
        //}
    }

    private void calcRankMap(String rank,Map<String,Integer> rankMap){

        int cnt = rankMap.get(rank);
        cnt--;
        if(cnt==0){
            rankMap.remove(rank);
        }else{
            rankMap.put(rank,cnt);
        }
    }

    private BfIndicatorDeptTotalScoreDto getDeptMetaScoreData(LcapDepartment4a79f3 dept, Integer jobYear, Integer jobMonth) {
        //先获取指标列表
        List<String> piList = new ArrayList<>();
        for (PerformanceIndocatorEnum piEnum : PerformanceIndocatorEnum.values()) {
            piList.add(piEnum.getPiCode());
        }

        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        List<BdIndicatorDeptScoreDto> deptInitialDatas = bdIndicatorDeptScoreMapper.getOptDeptSumInitialScore(dept.getDeptCode(), jobYear, jobMonth, piList);

        Integer totalFoodDowngradeLevel = new Integer(0);
        BigDecimal totalDeductionScore = new BigDecimal(0);
        Integer totalDowngradeLevel = 0;
        BigDecimal transitionScore = BigDecimal.ZERO;
        String remark = null;

        if (deptInitialDatas.isEmpty()) {
            remark = BizCommonConstant.PI_SCORE_EXCEPTION_REASON_1;
        } else {
            transitionScore = deptInitialDatas.get(0).getFinalScore();


            //查询当前部门的通用扣分项表的扣分和等级
            BfPenaltyRecordMapper bfPenaltyRecordMapper = SpringBeanUtil.getBean(BfPenaltyRecordMapper.class);
            QueryWrapper<BfPenaltyRecord> queryWrapper = new QueryWrapper<>();


            queryWrapper.select("sum(IFNULL(total_deduction_score,0)) as total_deduction_score," +
                    "sum(IFNULL(downgrade_level,0)) as total_downgrade_level");
            queryWrapper.eq("penalty_date", jobYear + "-" + String.format("%02d", jobMonth));
            queryWrapper.eq("enable_flag", "1");
            queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, "0");
            queryWrapper.eq("dept_id", dept.getDeptId());
            queryWrapper.groupBy("dept_id");

            List<Map<String, Object>> penaltyMap = bfPenaltyRecordMapper.selectMaps(queryWrapper);

            if (penaltyMap != null && penaltyMap.size() > 0) {
                totalDeductionScore = new BigDecimal(penaltyMap.get(0).get("total_deduction_score").toString());
                totalDowngradeLevel = new Integer(penaltyMap.get(0).get("total_downgrade_level").toString());
                if (totalDowngradeLevel > 4) {
                    totalDowngradeLevel = 4;
                }
            }


            //查询当前部门的食品安全表单的降级
            if (jobMonth == 3 || jobMonth == 6 || jobMonth == 9 || jobMonth == 12) {
                int quarter = TimeUtil.getQuarterByMonth(jobMonth);

                Integer[] months = TimeUtil.getSeasonMonths(quarter);
                BfFoodSafetyInspectMapper bfFoodSafetyInspectMapper = SpringBeanUtil.getBean(BfFoodSafetyInspectMapper.class);
                QueryWrapper<BfFoodSafetyInspect> queryWrapper1 = new QueryWrapper<>();
                queryWrapper.eq("year", jobYear);
                queryWrapper.eq("quarter", quarter);
                queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                queryWrapper.eq("status", "4");
                queryWrapper.eq("depart_id", dept.getId());

                queryWrapper.eq("enable_flag", "9");

                List<BfFoodSafetyInspect> foodInspectList = bfFoodSafetyInspectMapper.selectList(queryWrapper1);
                if (foodInspectList != null && foodInspectList.size() > 0) {
                    for (BfFoodSafetyInspect bf : foodInspectList) {
                        if (bf.getDownGrade() != null) {
                            totalFoodDowngradeLevel += Integer.parseInt(bf.getDownGrade().toString());
                        }
                    }
                }

                if (totalFoodDowngradeLevel > 4) {
                    totalFoodDowngradeLevel = 4;
                }


            }


        }
        BfIndicatorDeptTotalScoreDto toOpt = new BfIndicatorDeptTotalScoreDto(jobYear, jobMonth,
                dept.getName(), dept.getDeptCode(), dept.getId(),
                dept.getDeptClassify(), transitionScore, BizCommonConstant.DEPT_CLASSIFY_OPT,
                totalDeductionScore, totalDowngradeLevel, totalFoodDowngradeLevel, remark);
        return toOpt;
    }
}
