package com.gf.biz.elemeData.task;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.JacksonUtils;

import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.entity.IfElemeShopInfo;
import com.gf.biz.elemeData.entity.IfElmeShoppingRatingInfo;
import com.gf.biz.elemeData.mapper.IfElemeShopInfoMapper;
import com.gf.biz.elemeData.mapper.IfElmeShoppingRatingInfoMapper;
import com.gf.biz.ifsSync.job.eleme.common.ResponsePayload;
import com.gf.biz.ifsSync.job.eleme.common.SignatureUtil;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.gf.biz.elemeData.task.SyncMerchantAccountInfoDtlJobHandler.getTokenInfo;


/**
 * 获取饿了么门店评分接口
 */
public class SyncElemeShopRatingInfoDtlJobHandler extends IJobHandler {
    /*private static final String test_key = "JMpPzLxXxF";
    private static final String test_secret = "7376a0368d893bd0bdceae2bf431eed1cfe63246";
    private static final String test_shopId1 = "516930903";
    private static final String test_token_url = "https://open-api-sandbox.shop.ele.me/token";
    private static final String test_url = "https://open-api-sandbox.shop.ele.me/api/v1/";
    private static final String test_token = "4d6dcf03d7862bc11b833db44577c35b";
    */


    private static final String prod_key = "JMKi2sJbGu";
    private static final String prod_secret = "509e6f1c3d74e809c38dd0a52f7a97b8df3729fa";
    private static final String prod_token_url = "https://open-api.shop.ele.me/token";
    private static final String prod_url = "https://open-api.shop.ele.me/api/v1/";


    private static final String action = "eleme.ugc.getShopFactorInfos";
    private static final Logger logger = LoggerFactory.getLogger(SyncElemeShopRatingInfoDtlJobHandler.class);


    @Override
    public void execute() throws Exception {

        //获取所有店铺数量，每40个店铺调用一次
        //IfElemeShopInfoService ifElemeShopInfoService= (IfElemeShopInfoService) SpringBeanUtil.getBean("ifElemeShopInfoServiceImpl");

        //ifElemeShopInfoService.getObj(new QueryWrapper<IfScoreMv>()
        // .eq(CommonConstant.DELETED_FLAG_COLUMN, CommonConstant.INT_STATUS_NORMAL);

        //先获取token
        /*Map<String,String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type","client_credentials");
        bodyParams.put("scope","all");



        String headerKey=test_key+":"+test_secret;
        String encodeKey=new String(Base64.getEncoder().encode(headerKey.getBytes()));

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Basic "+encodeKey);

        String tokenInfo=HttpClientUtil.postFormUrlEncoded(test_token_url,headerMap,bodyParams);
        logger.info("tokenInfo:{}",tokenInfo);*/

//        final long timestamp = System.currentTimeMillis();
//        final String appKey = prod_key;
//        String secret = prod_secret;
//        String accessToken = "4d6dcf03d7862bc11b833db44577c35b";
//        String requestId = getReqID();
//
//        logger.info("requestId:{}" + requestId);
//        Map<String, Object> requestPayload = new HashMap<String, Object>();
//        requestPayload.put("nop", "1.0.0");
//        requestPayload.put("id", requestId);
//        requestPayload.put("action", action);
//        requestPayload.put("token", accessToken);
//
//        Map<String, Object> metasHashMap = new HashMap<String, Object>();
//        metasHashMap.put("app_key", appKey);
//        metasHashMap.put("timestamp", timestamp);
//
//
//        requestPayload.put("metas", metasHashMap);
//
//        Map<String, Object> parameters = null;
//        requestPayload.put("params", parameters);
//        String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
//        requestPayload.put("signature", signature);
//
//        String requestJson = JacksonUtils.obj2json(requestPayload);
//        String responseJson=HttpClientUtil.postJsonUrl(prod_url,requestJson,null);
//        ResponsePayload responsePayload=JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
//
//        JSONObject jo = new JSONObject((LinkedHashMap<String,Object>)responsePayload.getResult());
//
//
//        logger.info("result:{}",responsePayload.getResult());
        logger.info("syncElemeShopRatingInfoData start");
        syncElemeShopRatingInfoData();
    }


    public  void syncElemeShopRatingInfoData() throws Exception {

        IfElemeShopInfoMapper ifElemeShopInfoMapper = SpringBeanUtil.getBean(IfElemeShopInfoMapper.class);
        IfElmeShoppingRatingInfoMapper ifElmeShoppingRatingInfoMapper = SpringBeanUtil.getBean(IfElmeShoppingRatingInfoMapper.class);
        final long timestamp = System.currentTimeMillis();
        final String appKey = prod_key;
        String secret = prod_secret;
        String accessToken = getTokenInfo();
        String requestId = getReqID();

        logger.info("requestId:{}" + requestId);
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("nop", "1.0.0");
        requestPayload.put("id", requestId);
        requestPayload.put("action", action);
        requestPayload.put("token", accessToken);

        Map<String, Object> metasHashMap = new HashMap<>();
        metasHashMap.put("app_key", appKey);
        metasHashMap.put("timestamp", timestamp);


        requestPayload.put("metas", metasHashMap);


        List<String> shopIds =ifElemeShopInfoMapper.getAllShopId("1");

        logger.info("shopIds:{}",shopIds);
        // 分割列表
        List<List<String>> splitShopIds = splitIntoChunks(shopIds, 20);

        // 打印结果
        for (int i = 0; i < splitShopIds.size(); i++) {
            logger.info("Group " + (i + 1) + ": " + splitShopIds.get(i));
            //实际业务参数 可以修改
            Map<String, Object> parameters = new HashMap<>();
            //todo shopids
            //parameters.put("supplierId","94854117");
            parameters.put("shopIds",  splitShopIds.get(i));
            parameters.put("offset", "0");
            parameters.put("limit", "20");

            requestPayload.put("params", parameters);

            String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
            requestPayload.put("signature", signature);

            String requestJson = JacksonUtils.obj2json(requestPayload);
            String responseJson = HttpClientUtil.postJsonUrl(prod_url, requestJson, null);
            JSONObject jsonObject = JSONObject.parseObject(responseJson);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            IfElmeShoppingRatingInfo ifElmeShoppingRatingInfo = null;
            for (int j = 0; j < jsonArray.size(); j++) {
                ifElmeShoppingRatingInfo =new IfElmeShoppingRatingInfo();
                JSONObject ShoppingRatingInfo = jsonArray.getJSONObject(j);
                try {
                    ifElmeShoppingRatingInfo.setShopId(Integer.valueOf(ShoppingRatingInfo.getString("shopId")));
                    ifElmeShoppingRatingInfo.setStarScore(ShoppingRatingInfo.getString("starScore"));
                    ifElmeShoppingRatingInfo.setOverallScore(ShoppingRatingInfo.getString("overallScore"));
                    ifElmeShoppingRatingInfo.setTasteScore(ShoppingRatingInfo.getString("tasteScore"));
                    ifElmeShoppingRatingInfo.setPackageScore(ShoppingRatingInfo.getString("packageScore"));
                    ifElmeShoppingRatingInfo.setBadRateReplyRate(ShoppingRatingInfo.getString("badRateReplyRate"));
                    ifElmeShoppingRatingInfo.setHighQualityRate(ShoppingRatingInfo.getString("highQualityRate"));
                    ifElmeShoppingRatingInfo.setOrderCommentRate(ShoppingRatingInfo.getString("orderCommentRate"));
                    ifElmeShoppingRatingInfo.setStatus("1");
                    ifElmeShoppingRatingInfo.setCreatedTime(new Date());
                    ifElmeShoppingRatingInfo.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                    ifElmeShoppingRatingInfo.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                    ifElmeShoppingRatingInfoMapper.insert(ifElmeShoppingRatingInfo);

                }catch(Exception e){
                    logger.error("error:{}",e);

                }

            }

            //ResponsePayload responsePayload = JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
            logger.info("result:{}", requestJson);
            logger.info("responseJson:{}", responseJson);
            logger.info("jsonObject:{}", jsonObject);
            logger.info("jsonArray:{}", jsonArray);
        }
        logger.info("syncElemeShopRatingInfoData end");

    }


    private static String getReqID() {
        String reqId = generateUUID() + "|" + System.currentTimeMillis();
        return reqId;
    }

    private static String generateUUID() {
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            if (uuid.length() > 32) {
                uuid = uuid.substring(0, 32);
            }
            return uuid.toUpperCase();
        } catch (Exception e) {
            return "00112233445566778899AABBCCDDEEFF";
        }
    }
    public static List<List<String>> splitIntoChunks(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            // 创建子列表并添加到chunks中
            chunks.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return chunks;
    }


}
