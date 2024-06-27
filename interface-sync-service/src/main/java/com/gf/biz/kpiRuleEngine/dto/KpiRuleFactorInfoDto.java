package com.gf.biz.kpiRuleEngine.dto;


import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-06-07 17:26:13
 */

public class KpiRuleFactorInfoDto extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */

    private String ruleName;

    /**
     * 关联规则id
     */

    private Long ruleId;


    /**
     * 条件组id
     */

    private String factorName;

    private Long datasourceId;

    private Object value;

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

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}