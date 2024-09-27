package com.gf.biz.ifsSync.job.eleme;


import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.JacksonUtils;
import com.gf.biz.ifsSync.job.eleme.common.ResponsePayload;
import com.gf.biz.ifsSync.job.eleme.common.SignatureUtil;
import com.xxl.job.core.handler.IJobHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * 获取饿了么门店评分接口
 */
public class SyncMerchantAccountInfoJobHandler extends IJobHandler {
    private static final String test_key = "JMpPzLxXxF";
    private static final String test_secret = "7376a0368d893bd0bdceae2bf431eed1cfe63246";
    private static final String test_shopId1 = "516930903";
    private static final String test_token_url = "https://open-api-sandbox.shop.ele.me/token";
    private static final String test_url = "https://open-api-sandbox.shop.ele.me/api/v1/";

    private static final String test_token = "4d6dcf03d7862bc11b833db44577c35b";
    private static final String action="eleme.user.getUser";
    private static final Logger logger = LoggerFactory.getLogger(SyncMerchantAccountInfoJobHandler.class);


    @Override
    public void execute() throws Exception {
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

        final long timestamp = System.currentTimeMillis();
        final String appKey = test_key;
        String secret = test_secret;
        String accessToken = "4d6dcf03d7862bc11b833db44577c35b";
        String requestId = getReqID();

        logger.info("requestId:{}" + requestId);
        Map<String, Object> requestPayload = new HashMap<String, Object>();
        requestPayload.put("nop", "1.0.0");
        requestPayload.put("id", requestId);
        requestPayload.put("action", action);
        requestPayload.put("token", accessToken);

        Map<String, Object> metasHashMap = new HashMap<String, Object>();
        metasHashMap.put("app_key", appKey);
        metasHashMap.put("timestamp", timestamp);


        requestPayload.put("metas", metasHashMap);

        Map<String, Object> parameters = null;
        requestPayload.put("params", parameters);
        String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
        requestPayload.put("signature", signature);

        String requestJson = JacksonUtils.obj2json(requestPayload);
        String responseJson=HttpClientUtil.postJsonUrl(test_url,requestJson,null);
        ResponsePayload responsePayload=JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
        logger.info("result:{}",responsePayload.getResult());
    }

    private static final String getTokenInfo() throws Exception {
        //先获取token
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type", "client_credentials");
        bodyParams.put("scope", "all");


        String headerKey = test_key + ":" + test_secret;
        String encodeKey = new String(Base64.getEncoder().encode(headerKey.getBytes()));

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodeKey);

        String tokenInfo = HttpClientUtil.postFormUrlEncoded(test_token_url, headerMap, bodyParams);
        logger.info("tokenInfo:{}", tokenInfo);
        return tokenInfo;
    }

    public static void main(String[] args) throws Exception {

        final long timestamp = System.currentTimeMillis();
        final String appKey = test_key;
        String secret = test_secret;
        String accessToken = "4d6dcf03d7862bc11b833db44577c35b";
        String requestId = getReqID();

        logger.info("requestId:{}" + requestId);
        Map<String, Object> requestPayload = new HashMap<String, Object>();
        requestPayload.put("nop", "1.0.0");
        requestPayload.put("id", requestId);
        requestPayload.put("action", action);
        requestPayload.put("token", accessToken);

        Map<String, Object> metasHashMap = new HashMap<String, Object>();
        metasHashMap.put("app_key", appKey);
        metasHashMap.put("timestamp", timestamp);


        requestPayload.put("metas", metasHashMap);

        Map<String, Object> parameters =  new HashMap<String, Object>();
        requestPayload.put("params", parameters);
        String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
        requestPayload.put("signature", signature);

        String requestJson = JacksonUtils.obj2json(requestPayload);
        String responseJson=HttpClientUtil.postJsonUrl(test_url,requestJson,null);
        ResponsePayload responsePayload=JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
        logger.info("result:{}",responsePayload.getResult());
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


}
