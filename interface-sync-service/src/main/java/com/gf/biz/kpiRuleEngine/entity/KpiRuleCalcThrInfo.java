package com.gf.biz.kpiRuleEngine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gf.biz.common.entity.BaseBizEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-06-07 17:27:10
 */
@TableName("kpi_rule_calc_thr_info")
    public class KpiRuleCalcThrInfo extends BaseBizEntity implements Serializable {

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
         * 关联运算表id
         */
    @TableField("rule_calc_id")
        private Long ruleCalcId;

        /**
         * 最终值
         */
    @TableField("final_value")
        private BigDecimal finalValue;

        /**
         * 是否默认值'0'默认值'1'对应最终值
         */
    @TableField("default_value_flag")
        private String defaultValueFlag;

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

    public Long getRuleCalcId() {
        return ruleCalcId;
    }

    public void setRuleCalcId(Long ruleCalcId) {
        this.ruleCalcId = ruleCalcId;
    }

    public BigDecimal getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(BigDecimal finalValue) {
        this.finalValue = finalValue;
    }

    public String getDefaultValueFlag() {
        return defaultValueFlag;
    }

    public void setDefaultValueFlag(String defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
    }
}