package com.gf.biz.ifsSync.job.eleme.common;


import com.gf.biz.common.util.JacksonUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * 饿了么签名方法
 */
public class SignatureUtil {
    public static String generateSignature(String appKey, String secret, long timestamp, String action, String token, Map<String, Object> parameters) throws Exception {
        final Map<String, Object> sorted = new TreeMap();

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                sorted.put(entry.getKey(), entry.getValue());
            }
        }

        sorted.put("app_key", appKey);
        sorted.put("timestamp", timestamp);
        StringBuffer string = new StringBuffer();
        for (Map.Entry<String, Object> entry : sorted.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            string.append(entry.getKey()).append("=").append(JacksonUtils.obj2json(entry.getValue()));
        }
        String splice = String.format("%s%s%s%s", action, token, string, secret);
        //System.out.println(splice + "\n\n");
        String calculatedSignature = md5(splice);
        return calculatedSignature.toUpperCase();
    }

    public static String md5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
        }

        byte byteData[] = md.digest();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < byteData.length; i++)
            buffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

        return buffer.toString();
    }
}
