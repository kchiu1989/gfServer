package com.gf.biz.ifsSync.job.eleme;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.JacksonUtils;
import com.gf.biz.common.util.RedisUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.elemeData.entity.IfElemeShopInfo;
import com.gf.biz.ifsSync.job.eleme.common.ResponsePayload;
import com.gf.biz.ifsSync.job.eleme.common.SignatureUtil;
import com.xxl.job.core.handler.IJobHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * 获取饿了么门店评分接口
 * <p>
 * 授权成功后得到的access_token正式环境有效期为30天，沙箱环境有效期是1天(expires_in的单位为秒)；
 * 开发者需要自行存储access_token，并且在每次接口访问前判断token是否失效(失效的判断逻辑是token已经过了expires_in的有效期或者接口调用返回的code为UNAUTHORIZED)；
 * 商家应用需要生成有效的access_token后，订单以及其它消息才会进行推送；
 * 商家应用没有授权同意的过程，token即将失效前，无需用refresh_token刷新，直接获取access_token即可；
 * 在有效期内，token可以不用每次接口调用前都授权访问一次，有效期内重复授权会导致上一次的token失效(缓存时间 10 分钟，也就是生成新 token 后，旧 token 10 分钟后才会失效)；
 * 创建商家应用的店铺账号需要绑定有手机号，若店铺未绑定手机号请联系对应的市场经理协助绑定；
 * 如果是为沙箱环境下的连锁店铺生成 access_token，入参中需要增加 sand_box_chain_id，具体代码可以参考 SDK OAuthClientDemo.java 中的 clientTokenChainSandBoxTest。
 */
public class SyncMerchantAccountInfoJobHandler extends IJobHandler {
    /*private static final String test_key = "JMpPzLxXxF";
    private static final String test_secret = "7376a0368d893bd0bdceae2bf431eed1cfe63246";
    private static final String test_shopId1 = "516930903";
    private static final String test_token_url = "https://open-api-sandbox.shop.ele.me/token";
    private static final String test_url = "https://open-api-sandbox.shop.ele.me/api/v1/";

    private static final String test_token = "4d6dcf03d7862bc11b833db44577c35b";*/
    private static final String action = "eleme.user.getUser";
    private static final Logger logger = LoggerFactory.getLogger(SyncMerchantAccountInfoJobHandler.class);


    private static final String prod_key = "JMKi2sJbGu";
    private static final String prod_secret = "509e6f1c3d74e809c38dd0a52f7a97b8df3729fa";
    private static final String prod_token_url = "https://open-api.shop.ele.me/token";
    private static final String prod_url = "https://open-api.shop.ele.me/api/v1/";


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
        final String appKey = prod_key;
        String secret = prod_secret;
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
        String responseJson = HttpClientUtil.postJsonUrl(prod_url, requestJson, null);
        ResponsePayload responsePayload = JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
        logger.info("result:{}", responsePayload.getResult());
    }

    private static final String getTokenInfo() throws Exception {

        //todo token存入redis

        //先获取token
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type", "client_credentials");
        bodyParams.put("scope", "all");

        String headerKey = prod_key + ":" + prod_secret;
        String encodeKey = new String(Base64.getEncoder().encode(headerKey.getBytes()));

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodeKey);


        String tokenInfo = HttpClientUtil.postFormUrlEncoded(prod_token_url, headerMap, bodyParams);
        logger.info("tokenInfo:{}", tokenInfo);
        return tokenInfo;
    }

    public static void main(String[] args) throws Exception {

        final long timestamp = System.currentTimeMillis();
        final String appKey = prod_key;
        String secret = prod_secret;
        String accessToken = JSONObject.parseObject(getTokenInfo()).getString("access_token");
       /* {
            "access_token": "c199e3dc2705388f927e27d5ea6833d4",
                "trace_id": "fe.sopush_service^^5D77063E0EAC493A89780BBC3AEDB332|1703038197150",
                "token_type": "Bearer",
                "expires_in": 2592000
        }*/ //实际存入系统 -30秒
        String requestId = getReqID();

        //logger.info("requestId:{}" + requestId);
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("nop", "1.0.0");
        requestPayload.put("id", requestId);
        requestPayload.put("action", action);
        requestPayload.put("token", accessToken);


        Map<String, Object> metasHashMap = new HashMap<String, Object>();
        metasHashMap.put("app_key", appKey);
        metasHashMap.put("timestamp", timestamp);


        requestPayload.put("metas", metasHashMap);

        Map<String, Object> parameters = new HashMap<String, Object>();
        requestPayload.put("params", parameters);
        String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
        requestPayload.put("signature", signature);

        String requestJson = JacksonUtils.obj2json(requestPayload);
        String responseJson = HttpClientUtil.postJsonUrl(prod_url, requestJson, null);
        //ResponsePayload responsePayload=JacksonUtils.json2pojo(responseJson, ResponsePayload.class);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = result.getJSONArray("authorizedShops");
        //logger.info("result:{}", jsonArray);

//        for (int i = 0; i < jsonArray.size(); i++) {
//            //logger.info("shop-id:{}", jsonArray.getJSONObject(i).get("id"));
//            logger.info("shop-name:{}", jsonArray.getJSONObject(i).get("name"));
//            logger.info("shop-leaf:{}", jsonArray.getJSONObject(i).get("leaf"));
//        }

        //logger.info("result:{}",responsePayload.getResult().getClass());


    }

    //生成requestId

    private static String getReqID() {
        String reqId = generateUUID() + "|" + System.currentTimeMillis();
        return reqId;
    }

    //生成uuid
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
