package com.gf.biz.kpiRuleEngine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.gf.biz.common.entity.BaseBizEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-06-07 17:26:02
 */

@TableName("kpi_rule_calc_info")
    public class KpiRuleCalcInfo extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;
        /**
         * 规则名称
         */
    @TableField("rule_name")
        private String ruleName;

        /**
         * 关联规则id
         */
    @TableField("rule_id")
        private Long ruleId;

        /**
         * '0'停用 '1'正常
         */
    @TableField("status")
        private String status;

        /**
         * 分支条件id
         */
    @TableField("condition_id")
        private Long conditionId;

        /**
         * 运算公式
         */
    @TableField("expression_str")
        private String expressionStr;

        /**
         * '0'无阈值'1有阈值'
         */
    @TableField("threshold_flag")
        private String thresholdFlag;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getExpressionStr() {
        return expressionStr;
    }

    public void setExpressionStr(String expressionStr) {
        this.expressionStr = expressionStr;
    }

    public String getThresholdFlag() {
        return thresholdFlag;
    }

    public void setThresholdFlag(String thresholdFlag) {
        this.thresholdFlag = thresholdFlag;
    }
}