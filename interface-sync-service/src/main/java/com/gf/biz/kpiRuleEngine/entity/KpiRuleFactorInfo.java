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
 * @since 2024-06-07 17:26:13
 */
@TableName("kpi_rule_factor_info")
    public class KpiRuleFactorInfo extends BaseBizEntity implements Serializable {

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
    @TableField("factor_name")
        private String factorName;

        /**
         * 分支判断条件
         */
    @TableField("factor_desc")
        private String factorDesc;

        /**
         * '0'文本'1'金额
         */
    @TableField("data_type")
        private String dataType;

        /**
         * ‘0’直接取值'1'sql取值'2'函数取值'3'表达式取值
         */
    @TableField("data_method_flag")
        private String dataMethodFlag;

        /**
         * 映射字段
         */
    @TableField("mapping_field")
        private String mappingField;

        /**
         * 数据源id
         */
    @TableField("data_source_id")
        private Long dataSourceId;

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

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getFactorDesc() {
        return factorDesc;
    }

    public void setFactorDesc(String factorDesc) {
        this.factorDesc = factorDesc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataMethodFlag() {
        return dataMethodFlag;
    }

    public void setDataMethodFlag(String dataMethodFlag) {
        this.dataMethodFlag = dataMethodFlag;
    }

    public String getMappingField() {
        return mappingField;
    }

    public void setMappingField(String mappingField) {
        this.mappingField = mappingField;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}