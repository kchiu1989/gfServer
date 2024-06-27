package com.gf.biz.kpiRuleEngine.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.mapper.CommonMapper;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.expressionUtils.ExpressionUtil;
import com.gf.biz.kpiRuleEngine.dto.KpiRuleFactorInfoDto;
import com.gf.biz.kpiRuleEngine.entity.*;
import com.gf.biz.kpiRuleEngine.mapper.*;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestExecuteExpressionJob extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(TestExecuteExpressionJob.class);

    @Override
    public void execute() throws Exception {
        KpiRuleCalcInfoMapper kpiRuleCalcInfoMapper = SpringBeanUtil.getBean(KpiRuleCalcInfoMapper.class);

        KpiRuleCalcThrInfoMapper kpiRuleCalcThrInfoMapper = SpringBeanUtil.getBean(KpiRuleCalcThrInfoMapper.class);

        KpiRuleCalcThrDetailInfoMapper kpiRuleCalcThrDetailInfoMapper = SpringBeanUtil.getBean(KpiRuleCalcThrDetailInfoMapper.class);

        KpiRuleConditionGroupInfoMapper kpiRuleConditionGroupInfoMapper = SpringBeanUtil.getBean(KpiRuleConditionGroupInfoMapper.class);

        KpiRuleConditionInfoMapper kpiRuleConditionInfoMapper = SpringBeanUtil.getBean(KpiRuleConditionInfoMapper.class);

        KpiRuleDataSourceInfoMapper kpiRuleDataSourceInfoMapper = SpringBeanUtil.getBean(KpiRuleDataSourceInfoMapper.class);

        KpiRuleFactorInfoMapper kpiRuleFactorInfoMapper = SpringBeanUtil.getBean(KpiRuleFactorInfoMapper.class);

        KpiRuleInfoMapper kpiRuleInfoMapper = SpringBeanUtil.getBean(KpiRuleInfoMapper.class);

        KpiRuleInfo kpiRuleInfo = kpiRuleInfoMapper.selectById(1);

        if("0".equals(kpiRuleInfo.getConditionFlag())){//无判断分支，查询公式表
            QueryWrapper<KpiRuleCalcInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rule_id", 1);
            List<KpiRuleCalcInfo> kpiRuleCalcInfoList = kpiRuleCalcInfoMapper.selectList(queryWrapper);


            //运行之前 先查询sql
            QueryWrapper<KpiRuleDataSourceInfo> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("rule_id", 1);
            List<KpiRuleDataSourceInfo> kpiRuleDataSourceInfoList = kpiRuleDataSourceInfoMapper.selectList(queryWrapper2);
            KpiRuleDataSourceInfo kpiRuleDataSourceInfo = kpiRuleDataSourceInfoList.get(0);

            String querySql = kpiRuleDataSourceInfo.getQuerySql();
            CommonMapper commonMapper = SpringBeanUtil.getBean(CommonMapper.class);
            List<Map<String,Object>> queryRlt = commonMapper.queryByDynamicSql(querySql);

            //查询规则因子，匹配上面查询到的结果集
            QueryWrapper<KpiRuleFactorInfo> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("rule_id", 1);
            List<KpiRuleFactorInfo> kpiRuleFactorInfoList = kpiRuleFactorInfoMapper.selectList(queryWrapper3);


            //解析规则因子
            List<Map<String, KpiRuleFactorInfoDto>> ruleFactorMapList = new ArrayList<>();
            Map<String,KpiRuleFactorInfoDto> factorMap;
            for(Map<String,Object> singleMap:queryRlt){
                factorMap= new HashMap<String,KpiRuleFactorInfoDto>();
                for(KpiRuleFactorInfo ruleFactorInfo:kpiRuleFactorInfoList){
                    KpiRuleFactorInfoDto  kpiRuleFactorInfoDto = new KpiRuleFactorInfoDto();
                    BeanUtils.copyProperties(ruleFactorInfo,kpiRuleFactorInfoDto);
                    kpiRuleFactorInfoDto.setValue(singleMap.get(ruleFactorInfo.getMappingField()));
                    factorMap.put(ruleFactorInfo.getFactorName(),kpiRuleFactorInfoDto);
                }
                ruleFactorMapList.add(factorMap);
            }

            //编译公式并解析公式
            //expressionStr = "{actualValue}/{plannedValue}";
            String expressionStr = kpiRuleCalcInfoList.get(0).getExpressionStr();
            String expressionRegex = "\\{[^\\}]+\\}";

            //2.创建模式对象
            Pattern pattern = Pattern.compile(expressionRegex);

            //3.获取对应的匹配器(传入要检索的内容)

            Map<String,Object> expressionParams = new HashMap<>();

            Matcher matcher = pattern.matcher(expressionStr);
            while (matcher.find()) {
                String orifactorName = matcher.group(0);
                logger.info("factorName:{}" , orifactorName);
                //处理因子
                String factorName = orifactorName.replace("{","").replace("}","");

                if(!ruleFactorMapList.get(0).containsKey(factorName)){
                    logger.error("not contains the factor:{}",factorName);
                    break;
                }
                expressionParams.put(factorName,ruleFactorMapList.get(0).get(factorName).getValue());
            }
            expressionStr = expressionStr.replace("{","").replace("}","");
            Object calculateResult = ExpressionUtil.formulaOperation(expressionStr,expressionParams);



        }

    }

    public static void main(String[] args) {

        String expressionStr = "{actualValue}/{plannedValue}";
        String expressionRegex = "\\{[^\\}]+\\}";

        //2.创建模式对象
        Pattern pattern = Pattern.compile(expressionRegex);

        //3.获取对应的匹配器(传入要检索的内容)

        Map<String,Object> expressionParams = new HashMap<>();

        Matcher matcher = pattern.matcher(expressionStr);
        while (matcher.find()) {
            String orifactorName = matcher.group(0);
            logger.info("factorName:{}" , orifactorName);
            //处理因子
            String factorName = orifactorName.replace("{","").replace("}","");
            //expressionParams.put(factorName,);
        }


    }
}
