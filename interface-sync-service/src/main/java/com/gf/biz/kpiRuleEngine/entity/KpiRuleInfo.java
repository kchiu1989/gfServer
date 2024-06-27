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
 * @since 2024-06-07 17:18:48
 */
@TableName("kpi_rule_info")
    public class KpiRuleInfo extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 规则名称
         */
    @TableField("rule_name")
        private String ruleName;

        /**
         * 规则描述
         */
    @TableField("rule_desc")
        private String ruleDesc;

        /**
         * '0'停用 '1'正常
         */
    @TableField("status")
        private String status;

        /**
         * '0'无条件分支'1'有条件分支
         */
    @TableField("condition_flag")
        private String conditionFlag;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConditionFlag() {
        return conditionFlag;
    }

    public void setConditionFlag(String conditionFlag) {
        this.conditionFlag = conditionFlag;
    }
}