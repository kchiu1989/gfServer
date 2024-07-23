package com.gf.biz.LecaiSync.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.RedisUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.LecaiSync.dto.Lecai_gangwei_requestData;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class lecaiSyncUtil {

    private static final String lecai_corpId="56f837e57cbed68e607737b6e18f9a3b";
    private static final String lecai_secret="55d39d743443229547c10ac92e817aab";
    private static final String lecai_agent="joyhr";
//    private static final Integer pageNo=1;
//    private static final Integer pageSize=100;
    public static final String lecai_GETTOKEN_KEY="https://oapi.xinlecai.cn/token";
    public static final String lecai_GangWeiInfo_KEY="https://oapi.xinlecai.cn/openapi/joyhr/web/xlcapi/post";
    public static final String lecai_UserInfo_KEY="https://oapi.xinlecai.cn/openapi/joyhr/web/xlcapi/userList";
    /**
     * 获取lecaiAssessToken
     * @param lecai_corpId,lecai_agent,lecai_secret
     * @return
     */
    public static final String getLecaiAccessToken(String lecai_corpId,String lecai_agent,String lecai_secret){

        String accessTokenRtn = null;//获取乐才AccessToken
        RedisUtil redisUtils = (RedisUtil) SpringBeanUtil.getBean("redisUtil");//获取redis工具类
        try{
            accessTokenRtn = (String) redisUtils.get("LECAI_TOKEN_" + lecai_corpId);//获取redis中的token
        }catch(Exception e){
            log.error("redisUtils.get error",e);//获取redis失败
            return null;
        }
        JSONObject postBody = new JSONObject();
        postBody.put("corpId", lecai_corpId);
        postBody.put("agent", lecai_agent);
        postBody.put("secret", lecai_secret);
        if(accessTokenRtn == null){
            String url=lecai_GETTOKEN_KEY;
            try{
                String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
                Map result = JSONObject.parseObject(resultString).getInnerMap();
                if (!"0".equals(result.get("code").toString())) {
                    log.error("获取token失败:" + result.get("msg").toString());
                } else {
                    log.info("获取token:" +  "MS ,同步返回结果：" + result);
                    redisUtils.set("LECAI_TOKEN_" + lecai_corpId, result.get("result").toString(), 2*60*60-1*60);
                }
                return result.get("result").toString();
            }catch(Exception e){
                log.error("getLecaiAccessToken error",e);
            }
        }
        return accessTokenRtn;
    }
    /**
     * 获取乐才岗位信息
     * @param corpId,pageNo,pageSize
     * @return
     */
    public static Map getLecaiGangweiInfo1(String corpId,Integer pageNo,Integer pageSize,String startTime){
        String url = lecai_GangWeiInfo_KEY;
        String tokenStr = getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        log.info("getLecaiGangweiInfo start:{}", tokenStr);
        if(StringUtils.isBlank(tokenStr)){
            log.info("获取乐才岗位信息失败，无法获取token");
            return null;
        }
        Map tokenReturn = new HashMap<>();//角色信息
        tokenReturn.put("access_token", tokenStr);
        JSONObject postBody = new JSONObject();//封装参数
        Lecai_gangwei_requestData data = new Lecai_gangwei_requestData();
        data.setCorpId(corpId);
        data.setLastDate(startTime);
        postBody.put("data", data);
        postBody.put("pageNo", pageNo);
        postBody.put("pageSize", pageSize);
        try {
            log.info("getDeptInfoNewVersion start:{}", url);
            String resultString = HttpClientUtil.postJsonUrlWithHeader(url,tokenReturn, postBody.toJSONString(), null);
            log.info("getDeptInfoNewVersion end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();//解析返回结果

            if (!"0".equals(result.get("code").toString())) {
                log.error("获取岗位信息失败:" + result.get("msg").toString());
            } else {
                log.info("获取岗位信息:"  + "MS ,同步返回结果：" + result);
            }

        return result;
        } catch (Exception e) {
            log.error("getGangWeiInfoNewVersion error", e);
        }
        return null;
    }
    /**
     * 获取乐才人员信息
     * @return
     * @param
     */
    public static Map getLecaiUserInfo1(String corpId,Integer pageNo,Integer pageSize,String startTime){
        String url = lecai_UserInfo_KEY;
        String tokenStr = getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        log.info("getLecaiUserInfo start:{}", tokenStr);
        if(StringUtils.isBlank(tokenStr)){
            log.info("获取乐才人员信息失败，无法获取token");
            return null;
        }
        Map tokenReturn = new HashMap<>();//角色信息
        tokenReturn.put("access_token", tokenStr);
        JSONObject postBody = new JSONObject();//封装参数
        Lecai_gangwei_requestData data = new Lecai_gangwei_requestData();
        data.setCorpId(corpId);
        data.setLastDate(startTime);
        postBody.put("data", data);
        postBody.put("pageNo", pageNo);
        postBody.put("pageSize", pageSize);
        try {
            log.info("getLecaiUserInfo start:{}", url);
            String resultString = HttpClientUtil.postJsonUrlWithHeader(url,tokenReturn, postBody.toJSONString(), null);
            log.info("getLecaiUserInfo end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();//解析返回结果
            if (!"0".equals(result.get("code").toString())) {
                log.error("获取人员信息失败:" + result.get("msg").toString());
            } else {
                log.info("获取岗位信息:"+ "MS ,同步返回结果：" + result);
            }
            return result;
        }catch (Exception e){
            log.error("getLecaiUserInfo error", e);
        }
        return null;

    }

    public static Map getLecaiGangweiInfo(String corpId,Integer pageNo,Integer pageSize){
        String url = lecai_GangWeiInfo_KEY;
        String tokenStr = getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        log.info("getLecaiGangweiInfo start:{}", tokenStr);
        if(StringUtils.isBlank(tokenStr)){
            log.info("获取乐才岗位信息失败，无法获取token");
            return null;
        }
        Map tokenReturn = new HashMap<>();//角色信息
        tokenReturn.put("access_token", tokenStr);
        JSONObject postBody = new JSONObject();//封装参数
        Lecai_gangwei_requestData data = new Lecai_gangwei_requestData();
        data.setCorpId(corpId);
        postBody.put("data", data);
        postBody.put("pageNo", pageNo);
        postBody.put("pageSize", pageSize);
        try {
            log.info("getDeptInfoNewVersion start:{}", url);
            String resultString = HttpClientUtil.postJsonUrlWithHeader(url,tokenReturn, postBody.toJSONString(), null);
            log.info("getDeptInfoNewVersion end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();//解析返回结果

            if (!"0".equals(result.get("code").toString())) {
                log.error("获取岗位信息失败:" + result.get("msg").toString());
            } else {
                log.info("获取岗位信息:"  + "MS ,同步返回结果：" + result);
            }

            return result;
        } catch (Exception e) {
            log.error("getGangWeiInfoNewVersion error", e);
        }
        return null;
    }
    public static Map getLecaiUserInfo(String corpId,Integer pageNo,Integer pageSize){
        String url = lecai_UserInfo_KEY;
        String tokenStr = getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        log.info("getLecaiUserInfo start:{}", tokenStr);
        if(StringUtils.isBlank(tokenStr)){
            log.info("获取乐才人员信息失败，无法获取token");
            return null;
        }
        Map tokenReturn = new HashMap<>();//角色信息
        tokenReturn.put("access_token", tokenStr);
        JSONObject postBody = new JSONObject();//封装参数
        Lecai_gangwei_requestData data = new Lecai_gangwei_requestData();
        data.setCorpId(corpId);
        postBody.put("data", data);
        postBody.put("pageNo", pageNo);
        postBody.put("pageSize", pageSize);
        try {
            log.info("getLecaiUserInfo start:{}", url);
            String resultString = HttpClientUtil.postJsonUrlWithHeader(url,tokenReturn, postBody.toJSONString(), null);
            log.info("getLecaiUserInfo end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();//解析返回结果
            if (!"0".equals(result.get("code").toString())) {
                log.error("获取人员信息失败:" + result.get("msg").toString());
            } else {
                log.info("获取岗位信息:"+ "MS ,同步返回结果：" + result);
            }
            return result;
        }catch (Exception e){
            log.error("getLecaiUserInfo error", e);
        }
        return null;

    }
}
