package com.gf.biz.shenfangData.task;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.shenfangData.entity.BfScoreMv;
import com.gf.biz.shenfangData.entity.BfScoreMvDetail;
import com.gf.biz.shenfangData.entity.IfScoreMv;
import com.gf.biz.shenfangData.entity.IfScoreMvDetail;
import com.gf.biz.shenfangData.mapper.BfScoreMvDetailMapper;
import com.gf.biz.shenfangData.mapper.BfScoreMvMapper;
import com.gf.biz.shenfangData.mapper.IfScoreMvDetailMapper;
import com.gf.biz.shenfangData.mapper.IfScoreMvMapper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

/**
 * 调用神秘访客
 */
//@Component("ifScoreMvJob")
public class SyncIfScoreMvDataJobHandler extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncIfScoreMvDataJobHandler.class);

//    public static void main(String[] args) {
//        //嗨探 接口
//        //商户号100010
//        //秘钥ed305a5d91d44ec2984575696b86f50d
//        JSONObject params = new JSONObject();
//        params.put("stime","2023-06-07");
//        params.put("etime","2023-06-07");
//
//        params.put("companyId",100010);
//        //1713517172275
//        long timestamp = new Date().getTime();
//        params.put("timestamp",timestamp);
//
//        Map<String,Object> innerMap=params.getInnerMap();
//        Map<String,Object> sortMap = new TreeMap<>(innerMap);
//        Iterator<Map.Entry<String,Object>> ist=sortMap.entrySet().iterator();
//
//        Map.Entry<String,Object> single=null;
//        String getStr="";
//        while(ist.hasNext()){
//            single=ist.next();
//            getStr+=single.getKey()+"="+single.getValue()+"&";
//        }
//        getStr=getStr.substring(0,getStr.length()-1);
//        getStr+="ed305a5d91d44ec2984575696b86f50d";
//
//        logger.info("sign:{}",getStr);
//        String sign=DigestUtils.md5DigestAsHex(getStr.getBytes());
//        logger.info("sign:{}",sign);
//        Map<String,String> formMap = new HashMap<>();
//        formMap.put("stime",params.getString("stime"));
//        formMap.put("etime",params.getString("etime"));
//        formMap.put("companyId",String.valueOf(params.getInteger("companyId")));
//        formMap.put("timestamp",String.valueOf(params.getLongValue("timestamp")));
//        formMap.put("sign",sign);
//        String result =HttpClientUtil.doPostFormData("https://b.haitan.shop/djzchina-ops-b-api/reportApi/listOnlineReport",null,null,formMap);
//        logger.info("result:{}",result);
//        //0506391f4241d1b2b3fdc5ea0269598e
//    }

    @Override
    public void execute() throws Exception {
        logger.info("开始获取神秘访客数据");
        SyncGetScoreMvData();



        //0506391f4241d1b2b3fdc5ea0269598e
    }
    public void SyncGetScoreMvData() throws Exception{
        //嗨探 接口
        //商户号100010
        //秘钥ed305a5d91d44ec2984575696b86f50d
        IfScoreMvMapper ifScoreMvMapper = SpringBeanUtil.getBean(IfScoreMvMapper.class);
        IfScoreMvDetailMapper ifScoreMvDetailMapper = SpringBeanUtil.getBean(IfScoreMvDetailMapper.class);
        BfScoreMvMapper bfScoreMvMapper = SpringBeanUtil.getBean(BfScoreMvMapper.class);
        BfScoreMvDetailMapper bfScoreMvDetailMapper = SpringBeanUtil.getBean(BfScoreMvDetailMapper.class);

        JSONObject params = new JSONObject();
        params.put("stime","2023-12-01");
        params.put("etime","2023-12-30");

        params.put("companyId",100010);
        //1713517172275
        long timestamp = new Date().getTime();
        params.put("timestamp",timestamp);

        Map<String,Object> innerMap=params.getInnerMap();
        Map<String,Object> sortMap = new TreeMap<>(innerMap);
        Iterator<Map.Entry<String,Object>> ist=sortMap.entrySet().iterator();

        Map.Entry<String,Object> single=null;
        String getStr="";
        while(ist.hasNext()){
            single=ist.next();
            getStr+=single.getKey()+"="+single.getValue()+"&";
        }
        getStr=getStr.substring(0,getStr.length()-1);
        getStr+="ed305a5d91d44ec2984575696b86f50d";

        logger.info("sign:{}",getStr);
        String sign=DigestUtils.md5DigestAsHex(getStr.getBytes());
        logger.info("sign:{}",sign);
        Map<String,String> formMap = new HashMap<>();
        formMap.put("stime",params.getString("stime"));
        formMap.put("etime",params.getString("etime"));
        formMap.put("companyId",String.valueOf(params.getInteger("companyId")));
        formMap.put("timestamp",String.valueOf(params.getLongValue("timestamp")));
        formMap.put("sign",sign);
        String result =HttpClientUtil.doPostFormData("https://b.haitan.shop/djzchina-ops-b-api/reportApi/listOnlineReport",null,null,formMap);
        logger.info("result:{}",result);
        cn.hutool.json.JSONObject resultJson = JSONUtil.parseObj(result);


        if(!resultJson.get("total").equals(0)){
            cn.hutool.json.JSONArray resultData = resultJson.getJSONArray("result");
            for(int i=0;i<resultData.size();i++) {
               // try {
                    IfScoreMv ifScoreMv = new IfScoreMv();
                    cn.hutool.json.JSONObject resultDataObj = resultData.getJSONObject(i);
                    ifScoreMv.setIfKey(resultDataObj.getStr("key"));
                    ifScoreMv.setCheckTime(stampToDate(resultDataObj.getLong("checkTime")));
                    ifScoreMv.setDeliveryTime(stampToDate(resultDataObj.getLong("deliveryTime")));
                    ifScoreMv.setProvince(resultDataObj.getStr("province"));
                    ifScoreMv.setCity(resultDataObj.getStr("city"));
                    ifScoreMv.setBrand(resultDataObj.getStr("brand"));
                    ifScoreMv.setStore(resultDataObj.getStr("store"));
                    ifScoreMv.setStoreId(resultDataObj.getStr("storeId"));
                    ifScoreMv.setStoreCode(resultDataObj.getStr("storeCode"));
                    ifScoreMv.setTotalScore(resultDataObj.getBigDecimal("totalScore"));
                    ifScoreMv.setTotalRate(resultDataObj.getStr("totalRate"));
                    ifScoreMv.setType(resultDataObj.getInt("type"));
                    ifScoreMv.setPayMoney(resultDataObj.getBigDecimal("payMoney"));
                    ifScoreMv.setDeskNumber(resultDataObj.getStr("deskNumber"));
                    ifScoreMv.setYear("year");
                    ifScoreMv.setMonth("month");
                    ifScoreMv.setCreatedTime(new Date());
                    ifScoreMv.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                    ifScoreMv.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                    ifScoreMvMapper.insert(ifScoreMv);
                    BfScoreMv bfScoreMv = new BfScoreMv();
                    copyProperties(ifScoreMv,bfScoreMv,"id");
                    bfScoreMv.setIfId(ifScoreMv.getId());
                    bfScoreMv.setStatus("0");
                    bfScoreMvMapper.insert(bfScoreMv);

                    cn.hutool.json.JSONArray reportInfoList = resultDataObj.getJSONArray("reportInfoList");
                    for (int j = 0; j < reportInfoList.size(); j++) {
                        IfScoreMvDetail ifScoreMvDetail = new IfScoreMvDetail();
                        ifScoreMvDetail.setMasterId(ifScoreMv.getId());
                        ifScoreMvDetail.setFirstClassify(reportInfoList.getJSONObject(j).getStr("firstClassify"));
                        ifScoreMvDetail.setSecondClassify(reportInfoList.getJSONObject(j).getStr("secondClassify"));

                        ifScoreMvDetail.setThirdlyClassify(reportInfoList.getJSONObject(j).getStr("thirdlyClassify"));
                        ifScoreMvDetail.setFourthlyClassfy(reportInfoList.getJSONObject(j).getStr("fourthlyClassfy"));
                        ifScoreMvDetail.setOptionQuestion(reportInfoList.getJSONObject(j).getStr("optionQuestion"));
                        ifScoreMvDetail.setOptionList(reportInfoList.getJSONObject(j).getStr("optionList"));
                        ifScoreMvDetail.setExperienceComment(reportInfoList.getJSONObject(j).getStr("experienceComment"));
                        //ifScoreMvDetail.setExperienceCommentList(reportInfoList.getJSONObject(i).getStr("experienceCommentList"));
                        ifScoreMvDetail.setExperienceScore(reportInfoList.getJSONObject(j).getBigDecimal("experienceScore"));
                        ifScoreMvDetail.setExperienceScoreValid(reportInfoList.getJSONObject(j).getInt("experienceScoreValid"));
                        //ifScoreMvDetail.setFileList(reportInfoList.getJSONObject(j).getStr("fileList"));
                        ifScoreMvDetail.setScore(reportInfoList.getJSONObject(j).getBigDecimal("score"));
                        ifScoreMvDetail.setScoreSort(reportInfoList.getJSONObject(j).getStr("scoreSort"));
                        ifScoreMvDetail.setExamine(reportInfoList.getJSONObject(j).getStr("examine"));
                        ifScoreMvDetail.setExamineId(reportInfoList.getJSONObject(j).getStr("examineId"));
                        ifScoreMvDetail.setCreatedTime(new Date());
                        ifScoreMvDetail.setYear("year");
                        ifScoreMvDetail.setMonth("month");
                        ifScoreMvDetail.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                        ifScoreMvDetail.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                        ifScoreMvDetailMapper.insert(ifScoreMvDetail);
                        BfScoreMvDetail bfScoreMvDetail = new BfScoreMvDetail();
                        copyProperties(ifScoreMvDetail,bfScoreMvDetail,"id");
                        bfScoreMvDetail.setIfId(ifScoreMvDetail.getId());
                        bfScoreMvDetail.setStatus("0");
                        if(Integer.valueOf(ifScoreMv.getMonth())>=10){
                            bfScoreMvDetail.setBusinessDate(new Date(Long.valueOf(ifScoreMv.getYear()+"-"+ifScoreMv.getMonth()+"-01")));
                        }else{
                            bfScoreMvDetail.setBusinessDate(new Date(Long.valueOf(ifScoreMv.getYear()+"-0"+ifScoreMv.getMonth()+"-01")));
                        }
                        bfScoreMvDetailMapper.insert(bfScoreMvDetail);
                    }
                //}catch (Exception e){
               //     logger.error("获取商家评分异常:{}",e);
                //}
            }
        }
        logger.info("结束获取商家评分");

        //logger.info("resultJson:{}",resultJson);
    }
    //时间戳转化
    public static String stampToDate(long timeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeMillis));
    }
}
