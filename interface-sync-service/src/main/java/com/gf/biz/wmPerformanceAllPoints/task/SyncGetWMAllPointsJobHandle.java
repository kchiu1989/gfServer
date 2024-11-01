package com.gf.biz.wmPerformanceAllPoints.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.elemeData.entity.IfElmeShoppingRatingInfo;
import com.gf.biz.elemeData.mapper.IfElmeShoppingRatingInfoMapper;
import com.gf.biz.meituanwmData.entity.IfMtwmShopRatingData;
import com.gf.biz.meituanwmData.mapper.IfMtwmShopRatingDataMapper;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.gf.biz.wmPerformanceAllPoints.entity.BfTakeoutAssessSurvey;
import com.gf.biz.wmPerformanceAllPoints.entity.BfTakeoutDeliveryTime;
import com.gf.biz.wmPerformanceAllPoints.entity.IfTakeoutAssessSurvey;
import com.gf.biz.wmPerformanceAllPoints.entity.IfTakeoutShopMapping;
import com.gf.biz.wmPerformanceAllPoints.mapper.BfTakeoutAssessSurveyMapper;
import com.gf.biz.wmPerformanceAllPoints.mapper.BfTakeoutDeliveryTimeMapper;
import com.gf.biz.wmPerformanceAllPoints.mapper.IfTakeoutAssessSurveyMapper;
import com.gf.biz.wmPerformanceAllPoints.mapper.IfTakeoutShopMappingMapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

public class SyncGetWMAllPointsJobHandle extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncGetWMAllPointsJobHandle.class);

    @Override
    public void execute() throws Exception {
//        log.info("开始执行外卖算分");
//        syncGetWMAllPoints();
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
                    this.syncGetWMAllPoints(dept, currentYear, currentMonth);
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
                this.syncGetWMAllPoints(dbDept, currentYear, currentMonth);
            }
        }
    }

    public void syncGetWMAllPoints(LcapDepartment4a79f3 currentDept, Integer currentYear, Integer currentMonth) throws Exception {

        IfTakeoutAssessSurveyMapper ifTakeoutAssessSurveyMapper = SpringBeanUtil.getBean(IfTakeoutAssessSurveyMapper.class);
        IfTakeoutShopMappingMapper ifTakeoutShopMappingMapper = SpringBeanUtil.getBean(IfTakeoutShopMappingMapper.class);
        IfMtwmShopRatingDataMapper ifMtwmShopRatingDataMapper = SpringBeanUtil.getBean(IfMtwmShopRatingDataMapper.class);
        BfTakeoutAssessSurveyMapper bfTakeoutAssessSurveyMapper = SpringBeanUtil.getBean(BfTakeoutAssessSurveyMapper.class);
        BfTakeoutDeliveryTimeMapper bfTakeoutDeliveryTimeMapper = SpringBeanUtil.getBean(BfTakeoutDeliveryTimeMapper.class);
        IfElmeShoppingRatingInfoMapper ifElmeShoppingRatingInfoMapper = SpringBeanUtil.getBean(IfElmeShoppingRatingInfoMapper.class);
        //LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        //List<LcapDepartment4a79f3> lcapDepartment4a79f3sList = lcapDepartment4a79f3Mapper.getAllDeptInfo("0");
        //List<IfTakeoutShopMapping> ifTakeoutShopMappingList = ifTakeoutShopMappingMapper.getAllInfo();
        IfTakeoutAssessSurvey ifTakeoutAssessSurvey = new IfTakeoutAssessSurvey();
        BfTakeoutAssessSurvey bfTakeoutAssessSurvey = new BfTakeoutAssessSurvey();
        //for (LcapDepartment4a79f3 lcapDepartment : lcapDepartment4a79f3sList) {
        IfTakeoutShopMapping ifTakeoutShopMapping = new IfTakeoutShopMapping();
        QueryWrapper<IfTakeoutShopMapping> wrapper = new QueryWrapper<IfTakeoutShopMapping>();
        wrapper.eq("id", currentDept.getDeptCode());
        ifTakeoutShopMapping = ifTakeoutShopMappingMapper.selectOne(wrapper);
        ifTakeoutAssessSurvey = new IfTakeoutAssessSurvey();
        bfTakeoutAssessSurvey = new BfTakeoutAssessSurvey();
        if (ifTakeoutShopMapping != null) {
            QueryWrapper<BfTakeoutAssessSurvey> bfTakeoutAssessSurveyQueryWrapper = new QueryWrapper();
            bfTakeoutAssessSurveyQueryWrapper.eq("dept_code", currentDept.getDeptCode());
            bfTakeoutAssessSurveyQueryWrapper.eq("year", currentYear);
            bfTakeoutAssessSurveyQueryWrapper.eq("month", currentMonth);
            bfTakeoutAssessSurveyQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
            //bfTakeoutAssessSurveyQueryWrapper.eq("status", "1");
            BfTakeoutAssessSurvey bfTakeoutAssessSurveyUpdate = bfTakeoutAssessSurveyMapper.selectOne(bfTakeoutAssessSurveyQueryWrapper);
            BigDecimal ifElmeShoppingRatingInfoSumPoints = BigDecimal.ZERO;
            BigDecimal ifMtwmShopRatingDataSumPoints = BigDecimal.ZERO;
            BigDecimal bfTakeoutDeliveryTimeSumTime = BigDecimal.ZERO;
            QueryWrapper<IfElmeShoppingRatingInfo> ifElmeShoppingRatingInfoQueryWrapper = new QueryWrapper();
            ifElmeShoppingRatingInfoQueryWrapper.eq("shop_id", ifTakeoutShopMapping.getElemeShoppingId());
            ifElmeShoppingRatingInfoQueryWrapper.eq("year", currentYear);
            ifElmeShoppingRatingInfoQueryWrapper.eq("deleted_flag",CommonConstant.STATUS_UN_DEL);
            ifElmeShoppingRatingInfoQueryWrapper.eq("month", currentMonth);
            List<IfElmeShoppingRatingInfo> ifElmeShoppingRatingInfoList = ifElmeShoppingRatingInfoMapper.selectList(ifElmeShoppingRatingInfoQueryWrapper);
            if (ifElmeShoppingRatingInfoList.size() > 0) {
                for (IfElmeShoppingRatingInfo ifElmeShoppingRatingInfo : ifElmeShoppingRatingInfoList) {
                    //ifElmeShoppingRatingInfoSumPoints=ifElmeShoppingRatingInfoSumPoints+BigDecimal.valueOf(Long.valueOf(ifElmeShoppingRatingInfo.getStarScore()));

                    ifElmeShoppingRatingInfoSumPoints = ifElmeShoppingRatingInfoSumPoints.add(new BigDecimal(ifElmeShoppingRatingInfo.getStarScore()));
                }
                //ifTakeoutAssessSurvey.setElemeScore(String.valueOf(Double.valueOf(ifElmeShoppingRatingInfoSumPoints/ifElmeShoppingRatingInfoList.size())));
                ifTakeoutAssessSurvey.setElemeScore(String.valueOf(ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size()))));
                if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(5.0)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("110");
                } else if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(4.9)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("100");
                } else if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(4.8)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("90");
                } else if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(4.7)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("80");
                } else if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(4.6)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("70");
                } else if (ifElmeShoppingRatingInfoSumPoints.divide(new BigDecimal(ifElmeShoppingRatingInfoList.size())).compareTo(BigDecimal.valueOf(0)) >= 0) {
                    ifTakeoutAssessSurvey.setElemePoints("60");
                }
            } else {
                ifTakeoutAssessSurvey.setElemePoints(null);
            }
            QueryWrapper<IfMtwmShopRatingData> ifMtwmShopRatingDataQueryWrapper = new QueryWrapper();
            ifMtwmShopRatingDataQueryWrapper.eq("shop_id", ifTakeoutShopMapping.getMeituanShoppingId());
            ifMtwmShopRatingDataQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
            ifMtwmShopRatingDataQueryWrapper.eq("year", currentYear);
            ifMtwmShopRatingDataQueryWrapper.eq("month", currentMonth);
            List<IfMtwmShopRatingData> ifMtwmShopRatingDataList = ifMtwmShopRatingDataMapper.selectList(ifMtwmShopRatingDataQueryWrapper);
            if (ifMtwmShopRatingDataList.size() > 0) {
                for (IfMtwmShopRatingData ifMtwmShopRatingData : ifMtwmShopRatingDataList) {
                    //ifMtwmShopRatingDataSumPoints=ifMtwmShopRatingDataSumPoints+Double.valueOf(ifMtwmShopRatingData.getAvgPoiScore());

                    ifMtwmShopRatingDataSumPoints = ifMtwmShopRatingDataSumPoints.add(new BigDecimal(ifMtwmShopRatingData.getAvgPoiScore()));
                }
                //ifTakeoutAssessSurvey.setMeituanScore(String.valueOf(Double.valueOf(ifMtwmShopRatingDataSumPoints/ifMtwmShopRatingDataList.size())));
                ifTakeoutAssessSurvey.setMeituanScore(String.valueOf(ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size()))));
                if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(5.0)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("110");
                } else if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(4.9)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("100");
                } else if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(4.8)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("90");
                } else if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(4.7)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("80");
                } else if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(4.6)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("70");
                } else if (ifMtwmShopRatingDataSumPoints.divide(new BigDecimal(ifMtwmShopRatingDataList.size())).compareTo(BigDecimal.valueOf(0)) >= 0) {
                    ifTakeoutAssessSurvey.setMeituanPoints("60");
                }
            } else {
                ifTakeoutAssessSurvey.setMeituanPoints(null);
            }
            QueryWrapper<BfTakeoutDeliveryTime> bfTakeoutDeliveryTimeQueryWrapper = new QueryWrapper();
            bfTakeoutDeliveryTimeQueryWrapper.eq("dept_type_code", "0");
            bfTakeoutDeliveryTimeQueryWrapper.eq("dept_code", currentDept.getDeptCode());
            //bfTakeoutDeliveryTimeQueryWrapper.eq("status", "9");
            bfTakeoutDeliveryTimeQueryWrapper.eq("deleted_flag", "0");
            bfTakeoutDeliveryTimeQueryWrapper.eq("business_year", currentYear);
            bfTakeoutDeliveryTimeQueryWrapper.eq("business_month", currentMonth);
            List<BfTakeoutDeliveryTime> bfTakeoutDeliveryTimeList = bfTakeoutDeliveryTimeMapper.selectList(bfTakeoutDeliveryTimeQueryWrapper);
            if (bfTakeoutDeliveryTimeList.size() > 0) {
                for (BfTakeoutDeliveryTime bfTakeoutDeliveryTime : bfTakeoutDeliveryTimeList) {
                    //bfTakeoutDeliveryTimeSumTime=bfTakeoutDeliveryTimeSumTime+Double.valueOf(String.valueOf(bfTakeoutDeliveryTime.getDeliveryTime()));
                    bfTakeoutDeliveryTimeSumTime = bfTakeoutDeliveryTimeSumTime.add(new BigDecimal(String.valueOf(bfTakeoutDeliveryTime.getDeliveryTime())));
                }
                //ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime(String.valueOf(bfTakeoutDeliveryTimeSumTime/bfTakeoutDeliveryTimeList.size()));
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime(String.valueOf(bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size()))));
                if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(15)) <= 0) {
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints("100");

                } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(16)) <= 0) {
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints("90");
                } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(17)) <= 0) {
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints("80");
                } else  {
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints("70");
                }
            } else {
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(null);
            }
            if(bfTakeoutAssessSurveyUpdate!=null){
                bfTakeoutAssessSurvey.setMeituanPoints(ifTakeoutAssessSurvey.getMeituanPoints());
                bfTakeoutAssessSurvey.setMeituanScore(ifTakeoutAssessSurvey.getMeituanScore());
                bfTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(ifTakeoutAssessSurvey.getMeituanTakeoutDeliveryTimePoints());
                bfTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime(ifTakeoutAssessSurvey.getMeituanTakeoutDeliveryTime());
                bfTakeoutAssessSurvey.setElemePoints(ifTakeoutAssessSurvey.getElemePoints());
                bfTakeoutAssessSurvey.setElemeScore(ifTakeoutAssessSurvey.getElemeScore());
                if(bfTakeoutAssessSurvey.getElemeScore()!=bfTakeoutAssessSurveyUpdate.getElemeScore()){
                    UpdateWrapper<BfTakeoutAssessSurvey> elemeUpdateWrapper = new UpdateWrapper<>();
                    elemeUpdateWrapper.eq("id", bfTakeoutAssessSurveyUpdate.getId());
                    elemeUpdateWrapper.set("eleme_score", bfTakeoutAssessSurvey.getElemeScore());
                    elemeUpdateWrapper.set("eleme_points", bfTakeoutAssessSurvey.getElemePoints());
                    elemeUpdateWrapper.set("updated_time", new Date());
                    elemeUpdateWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                    bfTakeoutAssessSurveyMapper.update(null, elemeUpdateWrapper);
                }else{
                    logger.info("eleme数据无变化，无需更新");
                }
                if(bfTakeoutAssessSurvey.getMeituanScore()!=bfTakeoutAssessSurveyUpdate.getMeituanScore()){
                    UpdateWrapper<BfTakeoutAssessSurvey> MtUpdateWrapper = new UpdateWrapper<>();
                    MtUpdateWrapper.eq("id", bfTakeoutAssessSurveyUpdate.getId());
                    MtUpdateWrapper.set("meituan_score", bfTakeoutAssessSurvey.getMeituanScore());
                    MtUpdateWrapper.set("meituan_points", bfTakeoutAssessSurvey.getMeituanPoints());
                    MtUpdateWrapper.set("updated_time", new Date());
                    MtUpdateWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                    bfTakeoutAssessSurveyMapper.update(null, MtUpdateWrapper);
                }else{
                    logger.info("meituan数据无变化，无需更新");
                }
                if(bfTakeoutAssessSurvey.getMeituanTakeoutDeliveryTime()!=bfTakeoutAssessSurveyUpdate.getMeituanTakeoutDeliveryTime()){
                    UpdateWrapper<BfTakeoutAssessSurvey> MtTimeUpdateWrapper = new UpdateWrapper<>();
                    MtTimeUpdateWrapper.eq("id", bfTakeoutAssessSurveyUpdate.getId());
                    MtTimeUpdateWrapper.set("meituan_takeout_delivery_time", bfTakeoutAssessSurvey.getMeituanTakeoutDeliveryTime());
                    MtTimeUpdateWrapper.set("meituan_takeout_delivery_time_points", bfTakeoutAssessSurvey.getMeituanTakeoutDeliveryTimePoints());
                    MtTimeUpdateWrapper.set("updated_time", new Date());
                    MtTimeUpdateWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                    bfTakeoutAssessSurveyMapper.update(null, MtTimeUpdateWrapper);
                }else{
                    logger.info("meituan配送时间无变化，无需更新");
                }
            }else {
                ifTakeoutAssessSurvey.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                ifTakeoutAssessSurvey.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifTakeoutAssessSurvey.setMonth(String.valueOf(currentMonth));
                ifTakeoutAssessSurvey.setYear(currentYear);
                ifTakeoutAssessSurvey.setBrandCenterName(currentDept.getBrandCenterName());
                ifTakeoutAssessSurvey.setRegionName(currentDept.getRegionName());
                ifTakeoutAssessSurvey.setDeptCode(currentDept.getDeptCode());
                ifTakeoutAssessSurvey.setDeptId(Math.toIntExact(currentDept.getId()));
                ifTakeoutAssessSurvey.setDeptName(currentDept.getName());
                ifTakeoutAssessSurvey.setCreatedTime(new Date());
                logger.info("if表id：{}", ifTakeoutAssessSurvey.getId());
                ifTakeoutAssessSurveyMapper.insert(ifTakeoutAssessSurvey);
                if (ifTakeoutAssessSurvey.getId() != null) {
                    copyProperties(ifTakeoutAssessSurvey, bfTakeoutAssessSurvey, "id");
                    bfTakeoutAssessSurvey.setStatus("0");
                    bfTakeoutAssessSurvey.setIfId(ifTakeoutAssessSurvey.getId());
                    bfTakeoutAssessSurveyMapper.insert(bfTakeoutAssessSurvey);
                }
            }

            logger.info("同步外卖评分数据成功");

        } else {
            QueryWrapper<IfTakeoutAssessSurvey> ifTakeoutAssessSurveyQueryWrapper = new QueryWrapper<>();
            ifTakeoutAssessSurveyQueryWrapper.eq("deleted_flag", CommonConstant.STATUS_UN_DEL);
            ifTakeoutAssessSurveyQueryWrapper.eq("dept_code", currentDept.getDeptCode());
            ifTakeoutAssessSurveyQueryWrapper.eq("year", currentYear);
            ifTakeoutAssessSurveyQueryWrapper.eq("month", currentMonth);
            List<IfTakeoutAssessSurvey> ifTakeoutAssessSurveyList = ifTakeoutAssessSurveyMapper.selectList(ifTakeoutAssessSurveyQueryWrapper);

            if(ifTakeoutAssessSurveyList.size()>0){
                logger.info("该部门已存在该月数据，无需更新");
            }else {
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime("0");
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints("0");
                ifTakeoutAssessSurvey.setMeituanScore("0");
                ifTakeoutAssessSurvey.setMeituanPoints("0");
                ifTakeoutAssessSurvey.setElemeScore("0");
                ifTakeoutAssessSurvey.setElemePoints("0");
                ifTakeoutAssessSurvey.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                ifTakeoutAssessSurvey.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifTakeoutAssessSurvey.setMonth(String.valueOf(currentMonth));
                ifTakeoutAssessSurvey.setYear(currentYear);
                ifTakeoutAssessSurvey.setBrandCenterName(currentDept.getBrandCenterName());
                ifTakeoutAssessSurvey.setRegionName(currentDept.getRegionName());
                ifTakeoutAssessSurvey.setDeptCode(currentDept.getDeptCode());
                ifTakeoutAssessSurvey.setDeptId(Math.toIntExact(currentDept.getId()));
                ifTakeoutAssessSurvey.setDeptName(currentDept.getName());
                ifTakeoutAssessSurvey.setCreatedTime(new Date());
                ifTakeoutAssessSurveyMapper.insert(ifTakeoutAssessSurvey);
                if (ifTakeoutAssessSurvey.getId() != null) {
                    copyProperties(ifTakeoutAssessSurvey, bfTakeoutAssessSurvey, "id");
                    bfTakeoutAssessSurvey.setStatus("0");
                    bfTakeoutAssessSurvey.setIfId(ifTakeoutAssessSurvey.getId());
                    bfTakeoutAssessSurveyMapper.insert(bfTakeoutAssessSurvey);
                }
                logger.info("同步外卖评分数据失败");
            }


        }
        logger.info("同步外卖评分数据结束");


    }

}
