package com.gf.biz.ifsSync.job.tiancai;

import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用天财商龙顾客反馈
 */
public class IfScoreCeJob {

    private static final Logger logger = LoggerFactory.getLogger(IfScoreCeJob.class);
    private static final String secret="MQ0WINT60DXP7U7R09A70V6Z1SEDIGYH";
    private static final String systype="54547QDLVSCY485398";//商户标识
    private static final String groupId="140386";

    public static void main(String[] args) {
        //systype	是	string	商户标识
        //data	是	string	对data参数json化后的加密字符串

        //data参数：
        //mcid	否	int	门店ID（传入此值则只返回对应门店的数据）
        //beginTime	是	string	开始时间
        //endTime	是	string	结束时间
        //lastid	否	string	上一次的最后订单ID

        JSONObject jo= new JSONObject();
        jo.put("mcid", groupId);
        jo.put("beginTime","2024-03-01 00:00:00");
        jo.put("endTime","2024-04-23 00:00:00");

        SimpleStringCypher cypher = new SimpleStringCypher(secret);
        try{
            String secretData = cypher.encrypt(jo.toJSONString());
            Map<String,String> params = new HashMap<>();
            params.put("systype",systype);
            params.put("data",secretData);
            String resultContent= HttpClientUtil.postFormUrlEncoded("http://o2oapi.wuuxiang.com/external/evaluateinfo/GetEvaluateInfo.htm",null,params);
            //解密
            String finalContent =cypher.decrypt(resultContent);
            logger.info("finalContent:{}",finalContent);
        }catch(Exception e){
            logger.error("encrypt error",e);
        }


    }


/**
 * 加密方法
 */
private static class SimpleStringCypher {
    private byte[] linebreak = {};
    private SecretKey key;
    private Cipher cipher;
    private Base64 coder;

    public SimpleStringCypher(String secret) {
        try {
            coder = new Base64(32, linebreak, true);
            //secret为密钥 appkey
            byte[] secrets = coder.decode(secret);

            key = new SecretKeySpec(secrets, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        } catch (Exception e) {
            logger.error("SimpleStringCypher error",e);
        }
    }

    //对data进行加密
    public synchronized String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
        return new String(coder.encode(cipherText), "UTF-8");
    }

    //对返回结果进行解密
    public synchronized String decrypt(String codedText) throws Exception {
        byte[] encypted = coder.decode(codedText.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encypted);
        return new String(decrypted, "UTF-8");
    }
}
}
