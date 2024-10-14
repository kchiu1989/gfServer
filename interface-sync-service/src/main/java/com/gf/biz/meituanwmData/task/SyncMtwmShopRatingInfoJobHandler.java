package com.gf.biz.meituanwmData.task;

import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.mapper.IfElemeShopInfoMapper;
import com.gf.biz.elemeData.mapper.IfElmeShoppingRatingInfoMapper;
import com.gf.biz.meituanwmData.entity.IfMtwmShopRatingData;
import com.gf.biz.meituanwmData.mapper.IfMtwmShopDataMapper;
import com.gf.biz.meituanwmData.mapper.IfMtwmShopRatingDataMapper;
import com.gf.biz.meituanwmData.po.IfMtwmshopEntity;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * 同步美团外卖门店评分作业
 */
public class SyncMtwmShopRatingInfoJobHandler extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(SyncMtwmShopRatingInfoJobHandler.class);
    private static final String appId="9690";
    private static final String appSecret="ae0e087857354177105fa5da6d3769db";
    private static final String jobUrl="https://waimaiopen.meituan.com/api/v1/comment/score";

    public static void main(String[] args){
        SyncMtwmShopRatingInfoJobHandler shhh = new SyncMtwmShopRatingInfoJobHandler();
        try{
            shhh.execute();
        }catch(Exception e){
            logger.error("shhh.execute error",e);
        }

    }


    @Override
    public void execute() throws Exception {

        logger.info("syncMtwmShopRatingData start");
        syncMtwmShopRatingData();
    }
    public void syncMtwmShopRatingData() throws Exception{
        IfMtwmShopDataMapper ifMtwmShopDataMapper = SpringBeanUtil.getBean(IfMtwmShopDataMapper.class);
        IfMtwmShopRatingDataMapper ifMtwmShopRatingDataMapper = SpringBeanUtil.getBean(IfMtwmShopRatingDataMapper.class);

        List<IfMtwmshopEntity> shopIdList=ifMtwmShopDataMapper.getAllShopTrdptyId();
        logger.info("shopIdList:{}",shopIdList);

        IfMtwmShopRatingData ifMtwmShopRatingData =null;

        for(IfMtwmshopEntity ifMtwmshopEntity:shopIdList) {
            ifMtwmShopRatingData = new IfMtwmShopRatingData();
            Map<String, Object> params = new HashMap<>();
            params.put("app_poi_code", ifMtwmshopEntity.getShopTrdptyId());
            String finalUrl = getFinalUrl(params);

            String rltStr = HttpClientUtil.doGet(finalUrl);
            logger.info("rltStr:{}", rltStr);
            JSONObject rltJson = JSONObject.parseObject(rltStr);
            logger.info("rltJson:{}", rltJson);
            logger.info("rltJson.getString(\"data\"):{}", rltJson.getString("data"));
            JSONObject dataJson = rltJson.getJSONObject("data");
            if (dataJson != null) {
                ifMtwmShopRatingData.setAvgDeliveryScore(dataJson.getString("avgDeliveryScore"));
                ifMtwmShopRatingData.setAvgPackingScore(dataJson.getString("avgPackingScore"));
                ifMtwmShopRatingData.setAvgPoiScore(dataJson.getString("avgPoiScore"));
                ifMtwmShopRatingData.setAvgTasteScore(dataJson.getString("avgTasteScore"));
                ifMtwmShopRatingData.setDeliverySatisfaction(dataJson.getString("deliverySatisfaction"));
                ifMtwmShopRatingData.setCreatedTime(new Date());
                ifMtwmShopRatingData.setAppPoiCode(ifMtwmshopEntity.getShopTrdptyId());
                ifMtwmShopRatingData.setShopId(ifMtwmshopEntity.getShopId());
                ifMtwmShopRatingData.setStatus("1");
                ifMtwmShopRatingData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                ifMtwmShopRatingData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifMtwmShopRatingDataMapper.insert(ifMtwmShopRatingData);
            }
        }
        logger.info("syncMtwmShopRatingData end");

    }

    private String getFinalUrl(Map<String,Object> appParams){
        JSONObject sysParams = new JSONObject();

        sysParams.put("app_id",appId);
        //1713517172275
        long timestamp = new Date().getTime();
        sysParams.put("timestamp",timestamp);

        Map<String,Object> innerMap=sysParams.getInnerMap();

        innerMap.putAll(appParams);

        Map<String,Object> sortMap = new TreeMap<>(innerMap);
        Iterator<Map.Entry<String,Object>> ist=sortMap.entrySet().iterator();

        Map.Entry<String,Object> single=null;
        String getStr="";
        while(ist.hasNext()){
            single=ist.next();
            getStr+=single.getKey()+"="+single.getValue()+"&";
        }
        getStr=getStr.substring(0,getStr.length()-1);
        getStr+=appSecret;

        String finalUrl=jobUrl+"?";
        getStr=finalUrl+getStr;

        logger.info("toGenSignStr:{}",getStr);
        String sig= DigestUtils.md5DigestAsHex(getStr.getBytes());
        logger.info("genSignStr end:{}",sig);
        Map<String, Object> formMap = new HashMap<>(sysParams.getInnerMap());
        formMap.put("sig",sig);

        ist=formMap.entrySet().iterator();
        Map.Entry<String,Object> fSingle=null;
        while(ist.hasNext()){
            fSingle=ist.next();
            finalUrl+=fSingle.getKey()+"="+fSingle.getValue()+"&";
        }

        finalUrl=finalUrl.substring(0,finalUrl.length()-1);
        return finalUrl;
    }

}
