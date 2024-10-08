package com.gf.biz.ifsSync.test;

import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * 调用樊登图书
 */
public class CallApiTest {
    private static final Logger logger = LoggerFactory.getLogger(CallApiTest.class);
    public static void main(String[] args) {
        //嗨探 接口
        //商户号100010
        //秘钥ed305a5d91d44ec2984575696b86f50d
        JSONObject params = new JSONObject();
        params.put("stime","2023-06-07");
        params.put("etime","2023-06-07");

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
        HttpClientUtil.doPostFormData("https://b.haitan.shop/djzchina-ops-b-api/reportApi/listOnlineReport",null,null,formMap);
        //0506391f4241d1b2b3fdc5ea0269598e
    }
}
