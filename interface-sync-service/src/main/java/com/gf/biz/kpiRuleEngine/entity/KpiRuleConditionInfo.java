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
 * @since 2024-06-07 17:20:08
 */
@TableName("kpi_rule_condition_info")
    public class KpiRuleConditionInfo extends BaseBizEntity implements Serializable {

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
         * 条件组id
         */
    @TableField("condition_group_id")
        private Long conditionGroupId;

        /**
         * 分支判断条件
         */
    @TableField("condition_expression")
        private String conditionExpression;

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

    public Long getConditionGroupId() {
        return conditionGroupId;
    }

    public void setConditionGroupId(Long conditionGroupId) {
        this.conditionGroupId = conditionGroupId;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
}