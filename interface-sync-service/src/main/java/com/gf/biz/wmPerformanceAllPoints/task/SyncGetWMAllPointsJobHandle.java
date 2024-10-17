package com.gf.biz.wmPerformanceAllPoints.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.entity.IfElmeShoppingRatingInfo;
import com.gf.biz.elemeData.mapper.IfElmeShoppingRatingInfoMapper;
import com.gf.biz.meituanwmData.entity.IfMtwmShopRatingData;
import com.gf.biz.meituanwmData.mapper.IfMtwmShopRatingDataMapper;
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
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

public class SyncGetWMAllPointsJobHandle extends IJobHandler {
    private static final Logger log = LoggerFactory.getLogger(SyncGetWMAllPointsJobHandle.class);
    @Override
    public void execute() throws Exception {
        log.info("开始执行外卖算分");
        syncGetWMAllPoints();

    }
    public void syncGetWMAllPoints() throws Exception {

        IfTakeoutAssessSurveyMapper ifTakeoutAssessSurveyMapper = SpringBeanUtil.getBean(IfTakeoutAssessSurveyMapper.class);
        IfTakeoutShopMappingMapper ifTakeoutShopMappingMapper = SpringBeanUtil.getBean(IfTakeoutShopMappingMapper.class);
        IfMtwmShopRatingDataMapper ifMtwmShopRatingDataMapper = SpringBeanUtil.getBean(IfMtwmShopRatingDataMapper.class);
        BfTakeoutAssessSurveyMapper bfTakeoutAssessSurveyMapper = SpringBeanUtil.getBean(BfTakeoutAssessSurveyMapper.class);
        BfTakeoutDeliveryTimeMapper bfTakeoutDeliveryTimeMapper = SpringBeanUtil.getBean(BfTakeoutDeliveryTimeMapper.class);
        IfElmeShoppingRatingInfoMapper ifElmeShoppingRatingInfoMapper = SpringBeanUtil.getBean(IfElmeShoppingRatingInfoMapper.class);
        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
        List<LcapDepartment4a79f3> lcapDepartment4a79f3sList = lcapDepartment4a79f3Mapper.getAllDeptInfo("0");
        //List<IfTakeoutShopMapping> ifTakeoutShopMappingList = ifTakeoutShopMappingMapper.getAllInfo();
        IfTakeoutAssessSurvey ifTakeoutAssessSurvey = null;
        BfTakeoutAssessSurvey bfTakeoutAssessSurvey =null;
        for (LcapDepartment4a79f3 lcapDepartment : lcapDepartment4a79f3sList) {
            IfTakeoutShopMapping ifTakeoutShopMapping = new IfTakeoutShopMapping();
            QueryWrapper<IfTakeoutShopMapping> wrapper = new QueryWrapper<IfTakeoutShopMapping>();
            wrapper.eq("id", lcapDepartment.getDeptCode());
            ifTakeoutShopMapping =ifTakeoutShopMappingMapper.selectOne(wrapper);
            ifTakeoutAssessSurvey = new IfTakeoutAssessSurvey();
            bfTakeoutAssessSurvey = new BfTakeoutAssessSurvey();
            if (ifTakeoutShopMapping!= null) {
                BigDecimal ifElmeShoppingRatingInfoSumPoints =BigDecimal.ZERO;
                BigDecimal ifMtwmShopRatingDataSumPoints = BigDecimal.ZERO;
                BigDecimal bfTakeoutDeliveryTimeSumTime = BigDecimal.ZERO;
                QueryWrapper<IfElmeShoppingRatingInfo>  ifElmeShoppingRatingInfoQueryWrapper= new QueryWrapper();
                ifElmeShoppingRatingInfoQueryWrapper.eq("shop_id", ifTakeoutShopMapping.getElemeShoppingId());
                ifElmeShoppingRatingInfoQueryWrapper.eq("year", 2024);
                ifElmeShoppingRatingInfoQueryWrapper.eq("month", 10);
                List<IfElmeShoppingRatingInfo> ifElmeShoppingRatingInfoList = ifElmeShoppingRatingInfoMapper.selectList(ifElmeShoppingRatingInfoQueryWrapper);

                if(ifElmeShoppingRatingInfoList.size()>0) {
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
                }else {
                    ifTakeoutAssessSurvey.setElemePoints(null);
                }
                QueryWrapper<IfMtwmShopRatingData> ifMtwmShopRatingDataQueryWrapper= new QueryWrapper();
                ifMtwmShopRatingDataQueryWrapper.eq("shop_id", ifTakeoutShopMapping.getMeituanShoppingId());
                ifMtwmShopRatingDataQueryWrapper.eq("year", 2024);
                ifMtwmShopRatingDataQueryWrapper.eq("month", 10);
                List<IfMtwmShopRatingData> ifMtwmShopRatingDataList = ifMtwmShopRatingDataMapper.selectList(ifMtwmShopRatingDataQueryWrapper);
                if(ifMtwmShopRatingDataList.size()>0) {
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
                }else{
                    ifTakeoutAssessSurvey.setMeituanPoints(null);
                }
                QueryWrapper<BfTakeoutDeliveryTime> bfTakeoutDeliveryTimeQueryWrapper= new QueryWrapper();
                bfTakeoutDeliveryTimeQueryWrapper.eq("dept_type_code", "0");
                bfTakeoutDeliveryTimeQueryWrapper.eq("dept_code", lcapDepartment.getDeptCode());
                bfTakeoutDeliveryTimeQueryWrapper.eq("business_year", 2024);
                bfTakeoutDeliveryTimeQueryWrapper.eq("business_month", 10);
                List<BfTakeoutDeliveryTime> bfTakeoutDeliveryTimeList = bfTakeoutDeliveryTimeMapper.selectList(bfTakeoutDeliveryTimeQueryWrapper);
                if(bfTakeoutDeliveryTimeList.size()>0) {
                    for (BfTakeoutDeliveryTime bfTakeoutDeliveryTime : bfTakeoutDeliveryTimeList) {
                        //bfTakeoutDeliveryTimeSumTime=bfTakeoutDeliveryTimeSumTime+Double.valueOf(String.valueOf(bfTakeoutDeliveryTime.getDeliveryTime()));
                        bfTakeoutDeliveryTimeSumTime = bfTakeoutDeliveryTimeSumTime.add(new BigDecimal(String.valueOf(bfTakeoutDeliveryTime.getDeliveryTime())));
                    }
                    //ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime(String.valueOf(bfTakeoutDeliveryTimeSumTime/bfTakeoutDeliveryTimeList.size()));
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime(String.valueOf(bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size()))));
                    if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(17)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(100);
                    } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(18)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(90);
                    } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(19)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(80);
                    } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(20)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(70);
                    } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(22)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(60);
                    } else if (bfTakeoutDeliveryTimeSumTime.divide(new BigDecimal(bfTakeoutDeliveryTimeList.size())).compareTo(BigDecimal.valueOf(10086)) <= 0) {
                        ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(50);
                    }
                }else{
                    ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(null);
                }
                ifTakeoutAssessSurvey.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                ifTakeoutAssessSurvey.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifTakeoutAssessSurvey.setMonth("10");
                ifTakeoutAssessSurvey.setBrandCenterName(lcapDepartment.getBrandCenterName());
                ifTakeoutAssessSurvey.setRegionName(lcapDepartment.getRegionName());
                ifTakeoutAssessSurvey.setDeptCode(lcapDepartment.getDeptCode());
                ifTakeoutAssessSurvey.setDeptId(Math.toIntExact(lcapDepartment.getId()));
                ifTakeoutAssessSurvey.setDeptName(lcapDepartment.getName());
                ifTakeoutAssessSurvey.setCreatedTime(new Date());
                log.info("if表id：{}", ifTakeoutAssessSurvey.getId());
                ifTakeoutAssessSurveyMapper.insert(ifTakeoutAssessSurvey);

                if(ifTakeoutAssessSurvey.getId()!=null){
                    copyProperties(ifTakeoutAssessSurvey,bfTakeoutAssessSurvey,"id");
                    bfTakeoutAssessSurvey.setStatus("0");
                    bfTakeoutAssessSurvey.setIfId(ifTakeoutAssessSurvey.getId());
                    bfTakeoutAssessSurveyMapper.insert(bfTakeoutAssessSurvey);
                }

                log.info("同步外卖评分数据成功");

            } else{
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTime("0");
                ifTakeoutAssessSurvey.setMeituanTakeoutDeliveryTimePoints(0);
                ifTakeoutAssessSurvey.setMeituanScore("0");
                ifTakeoutAssessSurvey.setMeituanPoints("0");
                ifTakeoutAssessSurvey.setElemeScore("0");
                ifTakeoutAssessSurvey.setElemePoints("0");
                ifTakeoutAssessSurvey.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                ifTakeoutAssessSurvey.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifTakeoutAssessSurvey.setMonth("10");
                ifTakeoutAssessSurvey.setBrandCenterName(lcapDepartment.getBrandCenterName());
                ifTakeoutAssessSurvey.setRegionName(lcapDepartment.getRegionName());
                ifTakeoutAssessSurvey.setDeptCode(lcapDepartment.getDeptCode());
                ifTakeoutAssessSurvey.setDeptId(Math.toIntExact(lcapDepartment.getId()));
                ifTakeoutAssessSurvey.setDeptName(lcapDepartment.getName());
                ifTakeoutAssessSurvey.setCreatedTime(new Date());
                ifTakeoutAssessSurveyMapper.insert(ifTakeoutAssessSurvey);
                if(ifTakeoutAssessSurvey.getId()!=null){
                    copyProperties(ifTakeoutAssessSurvey,bfTakeoutAssessSurvey,"id");
                    bfTakeoutAssessSurvey.setStatus("0");
                    bfTakeoutAssessSurvey.setIfId(ifTakeoutAssessSurvey.getId());
                    bfTakeoutAssessSurveyMapper.insert(bfTakeoutAssessSurvey);
                }
                log.info("同步外卖评分数据失败");
            }

        }
        log.info("同步外卖评分数据结束");


    }

}
