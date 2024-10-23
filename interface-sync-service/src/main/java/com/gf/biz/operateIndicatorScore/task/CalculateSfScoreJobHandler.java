package com.gf.biz.operateIndicatorScore.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.shenfangData.entity.BfScoreMv;
import com.gf.biz.shenfangData.mapper.BfScoreMvMapper;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
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

public class CalculateSfScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);

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
            queryWrapper.isNotNull("dept_code");
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.syncSfGetTargetRunningPointsData(dept, currentYear, currentMonth);
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
                this.syncSfGetTargetRunningPointsData(dbDept, currentYear, currentMonth);
            }
        }
//        logger.info("开始执行神访目标分值计算");
//        syncSfGetTargetRunningPointsData();


    }

    public void syncSfGetTargetRunningPointsData(LcapDepartment4a79f3 currentDept, Integer currentYear, Integer currentMonth) throws Exception {
        //LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);//Mapper注入
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        BfScoreMvMapper bfScoreMvMapper = SpringBeanUtil.getBean(BfScoreMvMapper.class);
//        QueryWrapper<LcapDepartment4a79f3> wrapper = new QueryWrapper<>();
//        wrapper.eq("dept_classify","0");
//        List<LcapDepartment4a79f3> lcapDepartment4a79f3List = lcapDepartment4a79f3Mapper.selectList(wrapper);
        //for(LcapDepartment4a79f3 lcapDepartment4a79f3:lcapDepartment4a79f3List){
        QueryWrapper<BfScoreMv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_code", currentDept.getDeptCode());
        queryWrapper.eq("status","1");//已确认
        queryWrapper.eq("year",currentYear);//业务年
        queryWrapper.eq("month",currentMonth);//业务月
        List<BfScoreMv> bfScoreMvList = bfScoreMvMapper.selectList(queryWrapper);
        QueryWrapper<BdIndicatorDeptScore> bdIndicatorDeptScoreQueryWrapper = new QueryWrapper<>();
        bdIndicatorDeptScoreQueryWrapper.eq("dept_code", currentDept.getDeptCode());
        bdIndicatorDeptScoreQueryWrapper.eq("indicator_code", "PI0003");
        bdIndicatorDeptScoreQueryWrapper.eq("year",currentYear);
        bdIndicatorDeptScoreQueryWrapper.eq("month_quarter",currentMonth);
        bdIndicatorDeptScoreQueryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
        List<BdIndicatorDeptScore> bdIndicatorDeptScoreList = bdIndicatorDeptScoreMapper.selectList(bdIndicatorDeptScoreQueryWrapper);
        BdIndicatorDeptScore bdIndicatorDeptScore = new BdIndicatorDeptScore();
        BigDecimal finalScore = BigDecimal.ZERO;
        if (bfScoreMvList.size() > 0) {
            for (BfScoreMv bfScoreMv : bfScoreMvList) {
                finalScore = finalScore.add(bfScoreMv.getTotalScore());
            }
            if (finalScore.compareTo(BigDecimal.ZERO) > 0) {
                finalScore = finalScore.divide(new BigDecimal(bfScoreMvList.size()), 3, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(0.23)).multiply(new BigDecimal(49)).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP);
                bdIndicatorDeptScore.setFinalScore(finalScore);
            } else {
                bdIndicatorDeptScore.setRemark("该数据为0，请查询相应表单");
            }
        } else {
            bdIndicatorDeptScore.setRemark("该数据未查询到或该数据未确认，请查询相应表单");
        }
        if (bdIndicatorDeptScoreList.size() > 0) {
            BdIndicatorDeptScore bdIndicatorDeptScoreListData = bdIndicatorDeptScoreList.get(0);
            if (bdIndicatorDeptScoreListData.getFinalScore() != bdIndicatorDeptScore.getFinalScore()) {
                UpdateWrapper<BdIndicatorDeptScore> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", bdIndicatorDeptScoreListData.getId());
                updateWrapper.set("remark", null);
                updateWrapper.set("finalScore", bdIndicatorDeptScore.getFinalScore());
                updateWrapper.set("update_time", new Date());
                updateWrapper.set("update_by", CommonConstant.DEFAULT_OPT_USER);
                bdIndicatorDeptScoreMapper.update(null, updateWrapper);
            } else {
                logger.info("数据无变化，无需更新");
            }
        } else {
            bdIndicatorDeptScore.setDeptCode(currentDept.getDeptCode());
            bdIndicatorDeptScore.setDeptName(currentDept.getName());
            bdIndicatorDeptScore.setDeptId(currentDept.getId());
            bdIndicatorDeptScore.setDeptClassifyFlag(currentDept.getDeptClassify());
            bdIndicatorDeptScore.setYear(2024);//预留年份字段
            bdIndicatorDeptScore.setMonthQuarter(1);//预留月份字段
            bdIndicatorDeptScore.setRelateId(1L);//预留季度字段
            bdIndicatorDeptScore.setDimensionFlag("0");//月份flag
            bdIndicatorDeptScore.setIndicatorCode("PI0003");//预留指标字段
            bdIndicatorDeptScore.setIndicatorName("神秘访客");//预留指标名称字段
            bdIndicatorDeptScore.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            bdIndicatorDeptScore.setCreatedTime(new Date());
            bdIndicatorDeptScore.setDeletedFlag(CommonConstant.STATUS_DEL);
            bdIndicatorDeptScoreMapper.insert(bdIndicatorDeptScore);
        }
        logger.info("神访目标分值计算完成");

    }
}
