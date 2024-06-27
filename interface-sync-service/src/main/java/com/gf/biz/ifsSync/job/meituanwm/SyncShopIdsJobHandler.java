package com.gf.biz.ifsSync.job.meituanwm;

import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.*;

public class SyncShopIdsJobHandler extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(SyncShopIdsJobHandler.class);
    private static final String appId="9690";
    private static final String appSecret="ae0e087857354177105fa5da6d3769db";
    private static final String jobUrl="https://waimaiopen.meituan.com/api/v1/poi/getids";

    @Override
    public void execute() throws Exception {

        JSONObject sysParams = new JSONObject();


        sysParams.put("app_id",appId);
        //1713517172275
        long timestamp = new Date().getTime();
        sysParams.put("timestamp",timestamp);

        Map<String,Object> innerMap=sysParams.getInnerMap();
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


        logger.info("sign:{}",getStr);
        String sig= DigestUtils.md5DigestAsHex(getStr.getBytes());
        logger.info("sign:{}",sig);
        Map<String, Object> formMap = new HashMap<>(sysParams.getInnerMap());
        formMap.put("sig",sig);




        ist=formMap.entrySet().iterator();
        Map.Entry<String,Object> fSingle=null;
        while(ist.hasNext()){
            fSingle=ist.next();
            finalUrl+=fSingle.getKey()+"="+fSingle.getValue()+"&";
        }

        finalUrl=finalUrl.substring(0,finalUrl.length()-1);

        HttpClientUtil.doGet(finalUrl);
    }
}
