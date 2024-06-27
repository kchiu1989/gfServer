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
 * @since 2024-06-07 17:20:29
 */
@TableName("kpi_rule_condition_group_info")
    public class KpiRuleConditionGroupInfo extends BaseBizEntity implements Serializable {

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
         * 条件组'1'逻辑与'2'逻辑或
         */
    @TableField("condition_group_relation")
        private String conditionGroupRelation;

        /**
         * '0'无分支条件'1'有分支条件
         */
    @TableField("condition_flag")
        private String conditionFlag;

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

    public String getConditionGroupRelation() {
        return conditionGroupRelation;
    }

    public void setConditionGroupRelation(String conditionGroupRelation) {
        this.conditionGroupRelation = conditionGroupRelation;
    }

    public String getConditionFlag() {
        return conditionFlag;
    }

    public void setConditionFlag(String conditionFlag) {
        this.conditionFlag = conditionFlag;
    }
}