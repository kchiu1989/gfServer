package com.gf.biz.expressionUtils;


import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ExpressionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExpressionUtil.class);

    public static Object formulaOperation(String expression,Map<String,Object> params) {
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Object calculateResult = compiledExp.execute(params);
        logger.error("calculate result:{}",calculateResult.toString());
        return calculateResult;
    }

    /**
     *
     * cpi-美团/大众点评 细项配置 （属性：）
     *
     * 解析：
     *
     * 0、美团/大众点评  1:1
     *
     * 1、M=实际达成值（同步接口获得）-目标值（计划填写）
     *
     * 2、规则表达式区间
     * 0≦M      得100分
     * -0.1≦M< 0   得80分
     * -0.2≦M< 0   得70分
     * M< -0.2      得50分
     * 美团/大众评分：1：1
     *
     * 思路：首先M可以作为规则因子
     * 数据库维护一个
     *
     */

    public static void main(String[] args) {
        // 定义表达式
        /*String expression = "a > 0.8 && a < 0.85";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String,Object> vv = new HashMap<>();
        vv.put("a",0.84D);

        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(vv);
        System.out.println(result);*/

        String calcExp="(total * double(num) + completedNum * 0.5 - unFinishedNum * 0.5) * 10";
        Map<String,Object> vv = new HashMap<>();
        vv.put("total",4);
        vv.put("num",1.52);
        vv.put("completedNum",2);
        vv.put("unFinishedNum",4);
        Expression compiledExp = AviatorEvaluator.compile(calcExp);
        Object result = compiledExp.execute(vv);
        System.out.println(result);
    }
}
