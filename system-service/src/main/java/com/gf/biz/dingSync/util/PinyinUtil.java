package com.gf.biz.dingSync.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinyinUtil {

    private final static Logger log = LoggerFactory.getLogger(PinyinUtil.class);

    /**
     * 中英文转字母
     *
     * @param input 中英文
     * @return 中文转拼音，英文转小写
     */

    public static String pinyinChange(String input) {
        if (StringUtils.isBlank(input)) {
            log.error("待处理字符串为空");
            return "";
        }
        // 去除中文符号
        input = input.replaceAll("[——，。；！？：、’‘“”（）【】《》 ]", "");
        char[] inputChars = input.toCharArray();
        // 设置转换格式
        HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
        // LOWERCASE小写,UPPERCASE大写
        pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 无声调，WITH_TONE_NUMBER表示用数字表示声调, WITH_TONE_MARK表示用声调符号表示
        pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // WITH_V：用v表示ü  (nv) ,WITH_U_AND_COLON：用"u:"表示ü  (nu:),WITH_U_UNICODE：直接用ü (nü)
        pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder pinyinText = new StringBuilder();
        try {
            for (char charToTranslate : inputChars) {
                // 通过正则判断是否为汉字字符
                if (String.valueOf(charToTranslate).matches("[\\u4E00-\\u9FA5]")) {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(charToTranslate, pinyinFormat);
                    pinyinText.append(pinyinArray[0]);
                } else {
                    pinyinText.append(Character.toLowerCase(charToTranslate));
                }
            }
            return pinyinText.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            log.error("pinyinChange error",e1);
            return "";
        }
    }
}
