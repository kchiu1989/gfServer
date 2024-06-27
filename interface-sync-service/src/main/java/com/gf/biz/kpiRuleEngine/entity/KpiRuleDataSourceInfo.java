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
 * @since 2024-06-07 17:26:33
 */
@TableName("kpi_rule_data_source_info")
    public class KpiRuleDataSourceInfo extends BaseBizEntity implements Serializable {

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
         * 关联表
         */
    @TableField("ref_table")
        private String refTable;

        /**
         * '0'按照表名和查询条件 '1'直接按照sql查询
         */
    @TableField("define_type")
        private String defineType;

        /**
         * 查询语句
         */
    @TableField("query_sql")
        private String querySql;

        /**
         * 关联表名称
         */
    @TableField("ref_table_name")
        private String refTableName;

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

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getDefineType() {
        return defineType;
    }

    public void setDefineType(String defineType) {
        this.defineType = defineType;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }
}