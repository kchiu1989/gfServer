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
}
