package com.gf.biz.dingSync.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.JacksonUtils;
import com.gf.biz.common.util.RedisUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.dingSync.entity.AccessToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Gf
 */
public class DingSyncUtil {

    private final static Logger log = LoggerFactory.getLogger(DingSyncUtil.class);

    private static final String AppId = "65320f02-821c-4146-ad37-94f7d86785a0";
    private static final String AppKey = "dingziwaoroobolpqb0m";
    private static final String AppSecret = "MXk0icwCtMYXtzTwgEPKA0GNtCG4AwgNWMcxyijC1vtVM2EdsXgHGNy4rM0WNMzD";

    public static final String DING_TOKEN_PREFIX ="DING_TOKEN_";

    //获取子部门ID列表  本接口只支持获取下一级所有部门ID列表
    public static final String GET_DEPT_LISTSUB = "https://oapi.dingtalk.com/topapi/v2/department/listsub?access_token=ACCESS_TOKEN";

    //获取部门详情 POST请求
    public static final String GET_DEPT_INFO = "https://oapi.dingtalk.com/topapi/v2/department/get?access_token=ACCESS_TOKEN";

    public static final String GET_USERINFO_BYCODE = "https://oapi.dingtalk.com/sns/getuserinfo_bycode?accessKey=ACCESS_KEY&timestamp=TIMESTAMP&signature=SIGNATURE";

    public static final String GET_USERINFO_BYUNIONID = "https://oapi.dingtalk.com/topapi/user/getbyunionid?access_token=ACCESS_TOKEN";

    public static final String POST_USERINFO_BYUSERID = "https://oapi.dingtalk.com/topapi/v2/user/get?access_token=ACCESS_TOKEN";

    public static final String GET_ACCESS_TOKEN = "https://oapi.dingtalk.com/gettoken?appkey=APPKEY&appsecret=APPSECRET";

    public static final String GET_ROLE_LIST = "https://oapi.dingtalk.com/topapi/role/list?access_token=ACCESS_TOKEN";

    public static final String GET_RESIGN_USER_LIST = "https://api.dingtalk.com/v1.0/contact/empLeaveRecords";


    //获取AccessToken
    public static final String DING_GETTOKEN = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
    //通过appKey和appSecret获取AccessToken
    public static final String DING_GETTOKEN_KEY = "https://oapi.dingtalk.com/gettoken?appkey=KEY&appsecret=SECRET";
    //通过CODE换取用户身份
    public static final String DING_GETUSERINFO = "https://oapi.dingtalk.com/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
    //获取jsapi_ticket
    public static final String DING_GET_JSAPI_TICKET = "https://oapi.dingtalk.com/get_jsapi_ticket?access_token=ACCESS_TOKEN";

    public static final String DING_MESSAGE_SEND = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=ACCESS_TOKEN";

    //获取部门列表(1.0)
    public static final String DING_DEPARTMENT_LIST = "https://oapi.dingtalk.com/department/list?access_token=ACCESS_TOKEN";
    //获取部门详情 (1.0)
    public static final String DING_DEPARTMENT_GET = "https://oapi.dingtalk.com/department/get?access_token=ACCESS_TOKEN&id=DEPTID";
    //创建部门
    public static final String DING_DEPARTMENT_CREATE = "https://oapi.dingtalk.com/department/create?access_token=ACCESS_TOKEN";
    //更新部门
    public static final String DING_DEPARTMENT_UPDATE = "https://oapi.dingtalk.com/department/update?access_token=ACCESS_TOKEN";
    //删除部门
    public static final String DING_DEPARTMENT_DEL = "https://oapi.dingtalk.com/department/delete?access_token=ACCESS_TOKEN&id=DEPTID";

    //获取部门用户基础信息 用户管理1.0(不推荐)
    public static final String DING_USER_GET_BY_DEPT = "https://oapi.dingtalk.com/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPT_ID";
    //用户管理1.0(不推荐) 查询用户详情
    public static final String DING_USER_GET = "https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=MEMBERID";
    //创建成员
    public static final String DING_USER_CREATE = "https://oapi.dingtalk.com/user/create?access_token=ACCESS_TOKEN";
    //更新成员
    public static final String DING_USER_UPDATE = "https://oapi.dingtalk.com/user/update?access_token=ACCESS_TOKEN";
    //删除人员
    public static final String DING_USER_DELETE = "https://oapi.dingtalk.com/user/delete?access_token=ACCESS_TOKEN&userid=MEMBERID";
    //批量删除人员
    public static final String DING_USER_BATCHDELETE = "https://oapi.dingtalk.com/user/batchdelete?access_token=ACCESS_TOKEN";
    //根据手机号查userId
    public static final String DING_USER_GETBYMOBILE = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile?access_token=ACCESS_TOKEN";
    //批量增加员工角色
    public static final String DING_USER_BATCHADDROLE = "https://oapi.dingtalk.com/topapi/role/addrolesforemps?access_token=ACCESS_TOKEN";
    //批量删除员工角色
    public static final String DING_USER_BATCHDELETEROLE = "https://oapi.dingtalk.com/topapi/role/removerolesforemps?access_token=ACCESS_TOKEN";
    //开始创建角色
    public static final String DING_USER_CREATE_ROLE = "https://oapi.dingtalk.com/role/add_role?access_token=ACCESS_TOKEN";
    /**
     * 获取员工离职记录列表
     * 时间默认获取当月1号到当前时间的 时间格式：YYYY-MM-DDTHH:mm:ssZ
     *
     * @param params
     * @throws IOException
     */
    public static List<String> getResignUserList(Map<String, Object> params) throws IOException {

        Set<String> resignUserList = new HashSet<>();

        Map<String, String> headerMap = new HashMap<>();


        String startTime = "2024-01-01T00:00:00Z";

        String nextToken = "0";

        final String url = GET_RESIGN_USER_LIST + "?startTime=" + startTime;

        String dynamicUrl;

        while (StringUtils.isNotBlank(nextToken)) {

            String accessToken = getDingAccessToken(AppKey, AppSecret);

            if (StringUtils.isBlank(accessToken)) {
                log.error("获取TOKEN失败");
                return new ArrayList<>(resignUserList);
            }
            headerMap.put("x-acs-dingtalk-access-token", accessToken);

            headerMap.put("Content-Type", "application/json");

            dynamicUrl = url + "&nextToken=" + nextToken + "&maxResults=50";
            String responseStr = HttpClientUtil.doGetWithHeader(dynamicUrl, headerMap);

            JSONObject responseJson = JSONObject.parseObject(responseStr);

            //先处理离职人员列表的新增
            JSONArray userArray = responseJson.getJSONArray("records");

            if (userArray.size() > 0) {
                for (int i = 0; i < userArray.size(); i++) {
                    JSONObject userObject = userArray.getJSONObject(i);
                    resignUserList.add(userObject.getString("userId"));
                }
            }

            if (!responseJson.containsKey("nextToken")) {
                break;
            }
            nextToken = responseJson.getString("nextToken");

        }
        return new ArrayList<>(resignUserList);
    }

    private static final String getSignature(String appSecret, String timestamp) {
        String urlEncodeSignature = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(timestamp.getBytes("UTF-8"));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            if ("".equals(signature)) {
                return "";
            }
            String encoded = URLEncoder.encode(signature, "UTF-8");
            log.info("encoded:" + encoded);
            urlEncodeSignature = encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
            log.info("urlEncodeSignature:" + urlEncodeSignature);
        } catch (Exception e) {
            log.error("计算签名失败", e);
        }
        return urlEncodeSignature;
    }

    /**
     * 获取钉钉部门信息
     *
     * @param deptId
     * @return
     */
    public static Map getDeptInfo(String deptId) {
        Map result = new HashMap<>();
        long start = System.currentTimeMillis();
        String url = DING_DEPARTMENT_GET;
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.error("获取钉钉token失败");
            return result;
        }

        url = url.replace("ACCESS_TOKEN", tokenStr);
        url = url.replace("DEPTID", deptId);

        try {
            log.info("getDeptInfo start ==============================:{}", url);
            String resultJson = HttpClientUtil.doGet(url);
            log.info("getDeptInfo end ==============================:{}", resultJson);
            result = JSONObject.parseObject(resultJson).getInnerMap();

            result.put("id", deptId);
            log.info("get unitInfo ：" + deptId + " " + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + result);
            return result;
        } catch (Exception e) {
            log.error("getDeptInfo error,deptId: {}", deptId, e);
        }
        return result;
    }


    /**
     * 获取钉钉角色信息
     *
     * @return
     */
    public static Map getPostInfo() {
        Map result = new HashMap<>();
        long start = System.currentTimeMillis();
        String url = GET_ROLE_LIST;
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.info("获取钉钉角色信息失败，无法获取token");
            return result;
        }
        url = url.replace("ACCESS_TOKEN", tokenStr);

        try {
            log.info(url);
            String resultStr = HttpClientUtil.doGet(url);
            log.info("get postInfo ：  " + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + result);
            result = JSONObject.parseObject(resultStr).getInnerMap();
            return result;
        } catch (Exception e) {
            log.error("get postInfo error", e);
        }
        return result;
    }


    /**
     * 获取子部门信息
     *
     * @param deptId
     * @return
     */
    public static Map getDeptInfoNewVersion(String deptId) {
        long start = System.currentTimeMillis();
        String url = GET_DEPT_INFO;
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.error("无法获取token");
            return null;
        }

        url = url.replace("ACCESS_TOKEN", tokenStr);
        JSONObject postBody = new JSONObject();
        postBody.put("dept_id", deptId);

        try {
            log.info("getDeptInfoNewVersion start:{}", url);
            String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
            log.info("getDeptInfoNewVersion end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();

            if (!"0".equals(result.get("errcode").toString())) {
                log.error("取子部门失败:" + result.get("errmsg").toString());
            } else {
                log.info("取子部门:" + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + result);
            }
            return result;
        } catch (Exception e) {
            log.error("getDeptInfoNewVersion error", e);
        }
        return null;
    }


    /**
     * 获取子部门信息
     *
     * @param deptId
     * @return
     */
    public static Map getSubDeptListByOption(String deptId) {
        long start = System.currentTimeMillis();
        String url = GET_DEPT_LISTSUB;
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.error("无法获取token");
            return null;
        }

        url = url.replace("ACCESS_TOKEN", tokenStr);
        JSONObject postBody = new JSONObject();
        postBody.put("dept_id", deptId);

        try {
            log.info("getSubDeptListByOption start:{}", url);
            String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
            log.info("getSubDeptListByOption end:{}", resultString);
            Map result = JSONObject.parseObject(resultString).getInnerMap();

            if (!"0".equals(result.get("errcode").toString())) {
                log.error("获取子部门失败:" + result.get("errmsg").toString());
            } else {
                log.info("获取子部门:" + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + result);
            }
            return result;
        } catch (Exception e) {
            log.error("getSubDeptListByOption error", e);
        }
        return null;
    }

    /**
     * 获取部门下人员列表数据
     *
     * @param dingdingDeptId
     * @return
     */
    public static Map getUserListByDeptId(String dingdingDeptId) {
        long start = System.currentTimeMillis();

        Map result = new HashMap<>();
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.info("无法获取token");
            return result;
        }
        String url = DING_USER_GET_BY_DEPT;
        url = url.replace("ACCESS_TOKEN", tokenStr);
        url = url.replace("DEPT_ID", dingdingDeptId);

        try {
            log.info("getUserListByDeptId start:{}", url);
            String resultStr = HttpClientUtil.doGet(url);
            log.info("getUserListByDeptId end:{}", resultStr);
            result = JSONObject.parseObject(resultStr).getInnerMap();
            log.info("getUserListByDeptId ：" + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + result);
            return result;
        } catch (Exception e) {
            log.error("getUserListByDeptId error ", e);
        }
        return result;
    }

    /**
     * 获取人员详情
     *
     * @param userId
     * @return
     */
    public static Map getUserInfo(String userId) {
        Map result = new HashMap<>();
        long start = System.currentTimeMillis();
        String url = DING_USER_GET;
        String tokenStr = getDingAccessToken(AppKey, AppSecret);
        if (StringUtils.isBlank(tokenStr)) {
            log.error("无法获取token");
            return result;
        }

        url = url.replace("ACCESS_TOKEN", tokenStr);
        url = url.replace("MEMBERID", userId);

        try {
            log.info("获取人员详情开始 ：" + url);
            String resultStr = HttpClientUtil.doGet(url);
            log.info("获取人员详情结束 ：" + userId + " " + (System.currentTimeMillis() - start) + "MS ,同步返回结果：" + resultStr);
            result = JSONObject.parseObject(resultStr).getInnerMap();
            return result;
        } catch (Exception e) {
            log.error("getUserInfo error", e);
        }
        return result;
    }

    /**
     * 获取钉钉accessToken
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    public static final String getDingAccessToken(String appKey, String appSecret) {
        String accessTokenRtn = null;
        RedisUtil redisUtils = (RedisUtil) SpringBeanUtil.getBean("redisUtil");

        try{
            accessTokenRtn = (String) redisUtils.get(DING_TOKEN_PREFIX+ AppId);
        }catch(Exception e){
            log.error("redisUtils.get error",e);
            return null;
        }

        //说明过期了需要重新请求
        if (accessTokenRtn == null) {
            //重新调用接口获取TOKEN
            String url = DING_GETTOKEN_KEY.replace("KEY", appKey).replace("SECRET", appSecret);
            String access_token_str = HttpClientUtil.doGet(url);
            AccessToken accessToken = null;
            try {
                accessToken = JacksonUtils.json2pojo(access_token_str, AccessToken.class);
            } catch (Exception e) {
                log.error("json2pojo error", e);
                return null;
            }
            long expireTime = accessToken.getExpires_in();
            redisUtils.set(DING_TOKEN_PREFIX+ AppId, accessToken.getAccess_token(), expireTime - 5 * 60);
            return accessToken.getAccess_token();
        }

        return accessTokenRtn;
    }


}
