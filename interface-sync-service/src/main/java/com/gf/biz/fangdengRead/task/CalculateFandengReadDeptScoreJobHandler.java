package com.gf.biz.fangdengRead.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.fangdengRead.entity.IfRecordFdScore;
import com.gf.biz.fangdengRead.entity.IfRecordFdStatistic;
import com.gf.biz.fangdengRead.mapper.IfRecordFdStatisticMapper;
import com.gf.biz.fangdengRead.service.BfRecordFdStatisticService;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 门店 --部门
 * 店经理和厨师长 满分共三分，一人1.5分(按照绩效表 1:1各50分)，读书两本，分享两本，有一项未达标就是0分
 * <p>
 * 职能部门 （季度绩效奖金）
 * 按部门（部门经理 一人） 特殊情况 ：拓展部两个经理 采购部三个经理（有一个未达标就为0分）  2.5分
 */
public class CalculateFandengReadDeptScoreJobHandler extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(CalculateFandengReadDeptScoreJobHandler.class);
    private static final Integer fix_opt_dept_participants = 2;

    @Override
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
        String jobDeptClassify = null;


        if (StringUtils.isNotBlank(jobParam)) {
            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("month")) {
                currentYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                currentMonth = Integer.parseInt(xxlJobJsonObj.getString("month"));
            }

            logger.info("使用动态传参");

            if (xxlJobJsonObj.containsKey("deptCode") && xxlJobJsonObj.containsKey("deptClassify")) {
                jobDeptCode = xxlJobJsonObj.getString("deptCode");
                jobDeptClassify = xxlJobJsonObj.getString("deptClassify");
            }
        }

        logger.info("年:{},月:{},部门编码:{},部门分类", currentYear, currentMonth, jobDeptCode, jobDeptClassify);

        if (jobDeptCode != null && jobDeptClassify != null) {
            logger.info("特定部门跑分");
            //先跑运营部门
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            QueryWrapper<LcapDepartment4a79f3> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_classify", jobDeptClassify);
            queryWrapper.eq("dept_code", jobDeptCode);
            queryWrapper.isNotNull("dept_code");
            List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                for (LcapDepartment4a79f3 dept : deptList) {
                    this.calculateScore(jobDeptClassify, dept, currentYear, currentMonth);
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
                this.calculateScore(dbDept.getDeptClassify(), dbDept, currentYear, currentMonth);
            }
        }

        //跑职能部门

        queryWrapper.clear();
        queryWrapper.eq("dept_classify", "1");
        queryWrapper.isNotNull("dept_code");
        List<LcapDepartment4a79f3> functionalDeptList = lcapDepartment4a79f3Mapper.selectList(queryWrapper);
        if (functionalDeptList != null && functionalDeptList.size() > 0) {


            for (LcapDepartment4a79f3 dbDept : functionalDeptList) {
                logger.info("开始进行对部门:{},部门编码:{},的处理", dbDept.getName(), dbDept.getDeptCode());
                this.calculateScore(dbDept.getDeptClassify(), dbDept, currentYear, currentMonth);
            }
        }


    }

    public void calculateScore(String deptClassify, LcapDepartment4a79f3 currentDept, Integer currentYear, Integer currentMonth) {

        if ("0".equals(deptClassify) || "1".equals(deptClassify)) {
            String errorRemark = null;

            IfRecordFdStatisticMapper ifRecordFdStatisticMapper = SpringBeanUtil.getBean(IfRecordFdStatisticMapper.class);
            QueryWrapper<IfRecordFdStatistic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_id", currentDept.getId());
            queryWrapper.eq("year", currentYear);
            queryWrapper.eq("month", currentMonth);
            queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
            List<IfRecordFdStatistic> dbList = ifRecordFdStatisticMapper.selectList(queryWrapper);
            BigDecimal finalScore = null;
            if (dbList != null && dbList.size() > 0) {

                if ("0".equals(deptClassify)) {
                    if (dbList.size() > fix_opt_dept_participants) {
                        logger.error("统计数据异常，数量大于{}，不做处理", fix_opt_dept_participants);
                        errorRemark="统计数据异常，数量大于"+fix_opt_dept_participants+"，不做处理";
                    } else {
                        BigDecimal processScore = BigDecimal.ZERO;
                        for (IfRecordFdStatistic ifRecordFdStatistic : dbList) {
                            if (ifRecordFdStatistic.getReadCnt()!=null && ifRecordFdStatistic.getNoteShareCnt()!=null
                                    && ifRecordFdStatistic.getReadCnt() >= 2 && ifRecordFdStatistic.getNoteShareCnt() >= 2) {
                                processScore = processScore.add(new BigDecimal("100").
                                        multiply(BigDecimal.ONE).divide(new BigDecimal("2"), 3, RoundingMode.HALF_UP));
                            }
                        }
                        finalScore = processScore;
                    }
                } else {
                    BigDecimal processScore = new BigDecimal("100");
                    for (IfRecordFdStatistic ifRecordFdStatistic : dbList) {
                        if (ifRecordFdStatistic.getReadCnt() == null || ifRecordFdStatistic.getNoteShareCnt() ==null ||
                                ifRecordFdStatistic.getReadCnt() < 2 || ifRecordFdStatistic.getNoteShareCnt() < 2) {
                            processScore = BigDecimal.ZERO;
                            finalScore = processScore;
                            break;
                        }
                        finalScore = processScore;
                    }
                }


            } else {
                logger.error("未查询出部门统计数据,部门编码:{}", currentDept.getDeptCode());
                errorRemark="未查询出部门统计数据,部门编码:"+currentDept.getDeptCode();
            }

            try {

                BfRecordFdStatisticService bfRecordFdStatisticService = SpringBeanUtil.getBean(BfRecordFdStatisticService.class);
                IfRecordFdScore toDeal = new IfRecordFdScore(currentYear, currentMonth, currentDept.getName(),
                        currentDept.getDeptCode(), currentDept.getId(), finalScore, currentDept.getDeptClassify(),errorRemark);
                bfRecordFdStatisticService.generateOrUpdateBizForm(toDeal);

            } catch (Exception e) {
                logger.error("generateOrUpdateBizForm error", e);
            }
        } else {
            logger.error("数据异常，deptClassify:{},deptCode:{}", deptClassify, currentDept.getDeptCode());
        }


    }
}
