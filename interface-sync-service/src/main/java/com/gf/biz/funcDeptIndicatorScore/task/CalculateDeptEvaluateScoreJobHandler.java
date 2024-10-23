package com.gf.biz.funcDeptIndicatorScore.task;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.bizCommon.BizCommonConstant;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.funcDeptIndicatorScore.entity.BfDeptServiceEvaluate;
import com.gf.biz.funcDeptIndicatorScore.entity.BfDeptServiceEvaluateDept;
import com.gf.biz.funcDeptIndicatorScore.mapper.BfDeptServiceEvaluateDeptMapper;
import com.gf.biz.funcDeptIndicatorScore.mapper.BfDeptServiceEvaluateMapper;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.operateIndicatorScore.service.BdIndicatorDeptScoreService;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static com.gf.biz.bizCommon.BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER;

public class CalculateDeptEvaluateScoreJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);
    private static final String PI_CODE = "PI0006";
    private static final String PI_NAME = "部门服务满意度";
    private static final String[] managerUsernameArray = new String[]{"liuchangming", "liuyongxiu", "xuzhengyun", "lixiaotao"};
    private static final String[] brandUsernameArray = new String[]{"fushengli", "wangchunwei", "dengxaioliang", "liyicheng", "liulianji", "taoshunan", "wangxueliang", "huangqunbo", "xuenqi", "matao"};
    private static final BigDecimal dimensionWeight = new BigDecimal("0.30");
    private static final BigDecimal firstLevelIndicatorWeight = new BigDecimal("0.65");
    private static final String managerCode = "2";
    private static final String brandCode = "1";

    public void execute() throws Exception {
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
        String jobDeptClassify = "0";
        String status = "1";
        BfDeptServiceEvaluateDeptMapper bfDeptServiceEvaluateDeptMapper = SpringBeanUtil.getBean(BfDeptServiceEvaluateDeptMapper.class);
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
            QueryWrapper<BfDeptServiceEvaluateDept> brandQueryWrapper = new QueryWrapper<>();
            brandQueryWrapper.eq("dept_code", jobDeptCode);
            brandQueryWrapper.eq("status", status);
            brandQueryWrapper.eq("evaluate_port", brandCode);
            brandQueryWrapper.isNotNull("dept_code");
            brandQueryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
            List<BfDeptServiceEvaluateDept> deptBrandList = bfDeptServiceEvaluateDeptMapper.selectList(brandQueryWrapper);
            if (deptBrandList != null && deptBrandList.size() > 0) {
                for (BfDeptServiceEvaluateDept dept : deptBrandList) {
                    this.getDeptEvaluateByBrandScore(dept, currentYear, currentQuarter, jobDeptClassify);
                }
            }
            QueryWrapper<BfDeptServiceEvaluateDept> managerQeryWrapper = new QueryWrapper<>();
            managerQeryWrapper.eq("dept_code", jobDeptCode);
            managerQeryWrapper.eq("status", status);
            managerQeryWrapper.eq("evaluate_port", managerCode);
            managerQeryWrapper.isNotNull("dept_code");
            managerQeryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
            List<BfDeptServiceEvaluateDept> deptManagerList = bfDeptServiceEvaluateDeptMapper.selectList(managerQeryWrapper);
            if (deptManagerList != null && deptManagerList.size() > 0) {
                for (BfDeptServiceEvaluateDept dept : deptManagerList) {
                    this.getDeptEvaluateByManagerScore(dept, currentYear, currentQuarter);
                }
            }
            return;

        }
        //获取门店部门满意度评分

        QueryWrapper<BfDeptServiceEvaluateDept> brandQueryWrapper = new QueryWrapper<>();
        //brandQueryWrapper.eq("dept_code", jobDeptCode);
        brandQueryWrapper.eq("status", status);
        brandQueryWrapper.eq("evaluate_port", brandCode);
        brandQueryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
        //brandQueryWrapper.isNotNull("dept_code");
        List<BfDeptServiceEvaluateDept> deptBrandList = bfDeptServiceEvaluateDeptMapper.selectList(brandQueryWrapper);
        if (deptBrandList != null && deptBrandList.size() > 0) {
            for (BfDeptServiceEvaluateDept dept : deptBrandList) {
                this.getDeptEvaluateByBrandScore(dept, currentYear, currentQuarter, jobDeptClassify);
            }
        }
        //获取经理部门满意度评分
        QueryWrapper<BfDeptServiceEvaluateDept> managerQeryWrapper = new QueryWrapper<>();
        //managerQeryWrapper.eq("dept_code", jobDeptCode);
        managerQeryWrapper.eq("status", status);
        managerQeryWrapper.eq("evaluate_port", managerCode);
        managerQeryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
        //managerQeryWrapper.isNotNull("dept_code");
        List<BfDeptServiceEvaluateDept> deptManagerList = bfDeptServiceEvaluateDeptMapper.selectList(managerQeryWrapper);
        if (deptManagerList != null && deptManagerList.size() > 0) {
            for (BfDeptServiceEvaluateDept dept : deptManagerList) {
                this.getDeptEvaluateByManagerScore(dept, currentYear, currentQuarter);
            }
        }

    }

    public void getDeptEvaluateByBrandScore(BfDeptServiceEvaluateDept dept, Integer year, Integer quarter, String jobDeptClassify) throws Exception {
        BfDeptServiceEvaluateMapper bfDeptServiceEvaluateMapper = SpringBeanUtil.getBean(BfDeptServiceEvaluateMapper.class);
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        QueryWrapper<LcapDepartment4a79f3> lcapDepartment4a79f3QueryWrapper = new QueryWrapper<>();
        lcapDepartment4a79f3QueryWrapper.eq("dept_classify", jobDeptClassify);
        List<LcapDepartment4a79f3> deptList = lcapDepartment4a79f3Mapper.selectList(lcapDepartment4a79f3QueryWrapper);
        BigDecimal finalScore = BigDecimal.ZERO;
        BigDecimal middleScore = BigDecimal.ZERO;
        String remark = "";
        int count = 0;
        for (LcapDepartment4a79f3 brandDept : deptList) {
            QueryWrapper<BfDeptServiceEvaluate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("evaluated_dept_code", dept.getDeptCode());
            queryWrapper.eq("evaluate_port_code", brandCode);
            queryWrapper.eq("biz_year", year);
            queryWrapper.eq("dept_code", brandDept.getDeptCode());
            queryWrapper.eq("dept_type_code", brandDept.getDeptClassify());
            queryWrapper.eq("biz_quarter", quarter);
            queryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
            queryWrapper.eq("status", "9");
            List<BfDeptServiceEvaluate> dbDataList = bfDeptServiceEvaluateMapper.selectList(queryWrapper);
            if (dbDataList != null && dbDataList.size() > 0) {
                //todo 每个门店只取一条数据，要做判断；最好再写一个lcap_department表，记录每个部门是否已经跑分
//            for (BfDeptServiceEvaluate dbData : dbDataList) {
//                middleScore = middleScore.add(dbData.getTotalScore());
//            }
                middleScore = middleScore.add(dbDataList.get(0).getTotalScore());
                //BfDeptServiceEvaluate dbData = dbDataList.get(0);
                //middleScore = middleScore.add(dbData.getTotalScore());
//            middleScore = middleScore.add(new BigDecimal((deptSize - dbDataList.size()) * 100));
//            middleScore = middleScore.divide(new BigDecimal(deptSize), 3, BigDecimal.ROUND_HALF_UP);
            } else {
                middleScore = middleScore.add(new BigDecimal(100));
                count++;

            }
        }
//            if(middleScore.compareTo(BigDecimalll.ZERO)==0){
//                remark = "该部门得分为0，请查询相应表单";
//            }
//            }
        remark=count+"家没有参与评价";
        middleScore = middleScore.divide(new BigDecimal(deptList.size()), 3, BigDecimal.ROUND_HALF_UP);
        finalScore = middleScore.multiply(firstLevelIndicatorWeight).multiply(dimensionWeight).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP);
        QueryWrapper<BdIndicatorDeptScore> bdIndicatorDeptScoreQueryWrapper = new QueryWrapper<>();
        bdIndicatorDeptScoreQueryWrapper.eq("dept_code", dept.getDeptCode());
        bdIndicatorDeptScoreQueryWrapper.eq("indicator_code", PI_CODE);
        bdIndicatorDeptScoreQueryWrapper.eq("year", year);
        bdIndicatorDeptScoreQueryWrapper.eq("dimension_flag", BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER);
        bdIndicatorDeptScoreQueryWrapper.eq("month_quarter", quarter);
        bdIndicatorDeptScoreQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
        BdIndicatorDeptScore bdIndicatorDeptScoreUpdate = bdIndicatorDeptScoreMapper.selectOne(bdIndicatorDeptScoreQueryWrapper);
        if (bdIndicatorDeptScoreUpdate != null) {
            if (bdIndicatorDeptScoreUpdate.getFinalScore() != finalScore) {
                UpdateWrapper<BdIndicatorDeptScore> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", bdIndicatorDeptScoreUpdate.getId());
                updateWrapper.set("remark", null);
                updateWrapper.set("final_score", finalScore);
                updateWrapper.set("transition_score", middleScore);
                updateWrapper.set("updated_time", new Date());
                updateWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                bdIndicatorDeptScoreMapper.update(null, updateWrapper);
            } else {
                logger.info("数据无变化，无需更新");
            }
        } else {
            BdIndicatorDeptScore bdIndicatorDeptScore = new BdIndicatorDeptScore();
            bdIndicatorDeptScore.setDeptCode(dept.getDeptCode());
            bdIndicatorDeptScore.setDeptName(dept.getDeptName());
            bdIndicatorDeptScore.setDeptId(dept.getId());
            bdIndicatorDeptScore.setIndicatorCode(PI_CODE);
            bdIndicatorDeptScore.setIndicatorName(PI_NAME);
            bdIndicatorDeptScore.setDimensionFlag(PI_SCORE_DIMENSION_FLAG_QUARTER);
            bdIndicatorDeptScore.setYear(year);
            bdIndicatorDeptScore.setRemark(remark);
            bdIndicatorDeptScore.setMonthQuarter(quarter);
            bdIndicatorDeptScore.setTransitionScore(middleScore);
            bdIndicatorDeptScore.setFinalScore(finalScore);
            bdIndicatorDeptScore.setCreatedTime(new Date());
            bdIndicatorDeptScore.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
            bdIndicatorDeptScore.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
            bdIndicatorDeptScore.setDeptClassifyFlag("1");
            bdIndicatorDeptScoreMapper.insert(bdIndicatorDeptScore);

        }

        logger.info("部门PI得分计算完成");

    }


    private void getDeptEvaluateByManagerScore(BfDeptServiceEvaluateDept dept, Integer year, Integer quarter) {
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        BfDeptServiceEvaluateMapper bfDeptServiceEvaluateMapper = SpringBeanUtil.getBean(BfDeptServiceEvaluateMapper.class);
        BigDecimal managerMiddleScore = new BigDecimal(0);
        BigDecimal brandMiddleScore = new BigDecimal(0);
        BigDecimal managerFinalScore = new BigDecimal(0);
        BigDecimal brandFinalScore = new BigDecimal(0);
        BigDecimal finalScore = new BigDecimal(0);
        BigDecimal middleScore = new BigDecimal(0);
        String remark = "";
        int count = 0;

        for (String brandUsername : brandUsernameArray) {
            QueryWrapper<BfDeptServiceEvaluate> bfDeptServiceEvaluateQueryWrapper = new QueryWrapper<>();
            bfDeptServiceEvaluateQueryWrapper.eq("evaluated_dept_code", dept.getDeptCode());
            bfDeptServiceEvaluateQueryWrapper.eq("evaluate_port_code", brandCode);
            bfDeptServiceEvaluateQueryWrapper.eq("biz_year", year);
            bfDeptServiceEvaluateQueryWrapper.eq("evaluate_user_account", brandUsername);//需要修改
            bfDeptServiceEvaluateQueryWrapper.eq("biz_quarter", quarter);
            bfDeptServiceEvaluateQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
            bfDeptServiceEvaluateQueryWrapper.eq("status", "9");
            List<BfDeptServiceEvaluate> dbDataList = bfDeptServiceEvaluateMapper.selectList(bfDeptServiceEvaluateQueryWrapper);
            if (dbDataList != null && dbDataList.size() > 0) {
                brandMiddleScore = brandMiddleScore.add(dbDataList.get(0).getTotalScore());
            } else {
                brandMiddleScore = brandMiddleScore.add(new BigDecimal(100));
                count++;
            }
        }
        brandMiddleScore = brandMiddleScore.divide(new BigDecimal(brandUsernameArray.length), 3, BigDecimal.ROUND_HALF_UP);
        brandFinalScore = brandMiddleScore.multiply(firstLevelIndicatorWeight).multiply(dimensionWeight).multiply(new BigDecimal(0.6)).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP);
        for(String managerUsername:managerUsernameArray){
            QueryWrapper<BfDeptServiceEvaluate> bfDeptServiceEvaluateQueryWrapper = new QueryWrapper<>();
            bfDeptServiceEvaluateQueryWrapper.eq("evaluated_dept_code", dept.getDeptCode());
            bfDeptServiceEvaluateQueryWrapper.eq("evaluate_port_code", brandCode);
            bfDeptServiceEvaluateQueryWrapper.eq("biz_year", year);
            bfDeptServiceEvaluateQueryWrapper.eq("evaluate_user_account", managerUsername);//需要修改
            bfDeptServiceEvaluateQueryWrapper.eq("biz_quarter", quarter);
            bfDeptServiceEvaluateQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
            bfDeptServiceEvaluateQueryWrapper.eq("status", "9");
            List<BfDeptServiceEvaluate> dbDataList = bfDeptServiceEvaluateMapper.selectList(bfDeptServiceEvaluateQueryWrapper);
            if (dbDataList != null && dbDataList.size() > 0) {
                managerMiddleScore = managerMiddleScore.add(dbDataList.get(0).getTotalScore());
            } else {
                managerMiddleScore = managerMiddleScore.add(new BigDecimal(100));
                count++;

            }

        }
        remark = count+ "ren未参与评分，请查询相应表单;";
        managerMiddleScore = managerMiddleScore.divide(new BigDecimal(managerUsernameArray.length), 3, BigDecimal.ROUND_HALF_UP);
        managerFinalScore = managerMiddleScore.multiply(firstLevelIndicatorWeight).multiply(dimensionWeight).multiply(new BigDecimal(0.4)).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP);
        middleScore=managerMiddleScore.multiply(new BigDecimal(0.6)).add(brandMiddleScore.multiply(new BigDecimal(0.4)));
        finalScore=managerFinalScore.add(brandFinalScore);
        BdIndicatorDeptScoreDto toOpt = new BdIndicatorDeptScoreDto(year, quarter, dept.getDeptName(), dept.getDeptCode(),
                dept.getId(), "1", finalScore, middleScore,
                BizCommonConstant.PI_SCORE_DIMENSION_FLAG_QUARTER, PI_NAME, PI_CODE, remark);

        BdIndicatorDeptScoreService bdIndicatorDeptScoreService = SpringBeanUtil.getBean(BdIndicatorDeptScoreService.class);
        bdIndicatorDeptScoreService.createOrAddBdIndicatorDeptScore(toOpt);

    }
}
