package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-18 17:08:44
 */
@TableName("bd_month_budget")
public class BdMonthBudget extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 年份-月份
     */
    @TableField("date")
    private String date;

    /**
     * 预算年
     */
    @TableField("year")
    private Integer year;

    /**
     * 预算月
     */
    @TableField("month")
    private Integer month;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 部门类别编码
     */
    @TableField("dept_classify_code")
    private String deptClassifyCode;

    /**
     * 部门类型
     */
    @TableField("dept_classify_name")
    private String deptClassifyName;


    /**
     * 单据状态
     */
    @TableField("status")
    private String status;

    /**
     * 预算项目名称
     */
    @TableField("bd_item_name")
    private String bdItemName;

    /**
     * 预算项目ID
     */
    @TableField("bd_item_id")
    private Long bdItemId;

    /**
     * 预算项目编码
     */
    @TableField("bd_item_code")
    private String bdItemCode;

    /**
     * 预算方案Id
     */
    @TableField("schema_id")
    private Integer schemaId;

    /**
     * 预算方案编码
     */
    @TableField("schema_code")
    private String schemaCode;

    /**
     * 计划值
     */
    @TableField("plan_value")
    private BigDecimal planValue;

    /**
     * 实际值
     */
    @TableField("actual_value")
    private BigDecimal actualValue;

    /**
     * 预算项目分类
     */
    @TableField("bd_item_classify")
    private String bdItemClassify;

    /**
     * 实际值的调整值
     */
    @TableField("actual_adjustment_value")
    private BigDecimal actualAdjustmentValue;

    /**
     * 调整后的达成值
     */
    @TableField("adjusted_actual_value")
    private BigDecimal adjustedActualValue;

    /**
     * 计划值的调整值
     */
    @TableField("plan_adjustment_value")
    private BigDecimal planAdjustmentValue;

    /**
     * 调整后的计划值
     */
    @TableField("adjusted_plan_value")
    private BigDecimal adjustedPlanValue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptClassifyCode() {
        return deptClassifyCode;
    }

    public void setDeptClassifyCode(String deptClassifyCode) {
        this.deptClassifyCode = deptClassifyCode;
    }

    public String getDeptClassifyName() {
        return deptClassifyName;
    }

    public void setDeptClassifyName(String deptClassifyName) {
        this.deptClassifyName = deptClassifyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBdItemName() {
        return bdItemName;
    }

    public void setBdItemName(String bdItemName) {
        this.bdItemName = bdItemName;
    }

    public Long getBdItemId() {
        return bdItemId;
    }

    public void setBdItemId(Long bdItemId) {
        this.bdItemId = bdItemId;
    }

    public String getBdItemCode() {
        return bdItemCode;
    }

    public void setBdItemCode(String bdItemCode) {
        this.bdItemCode = bdItemCode;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
    }

    public BigDecimal getPlanValue() {
        return planValue;
    }

    public void setPlanValue(BigDecimal planValue) {
        this.planValue = planValue;
    }

    public BigDecimal getActualValue() {
        return actualValue;
    }

    public void setActualValue(BigDecimal actualValue) {
        this.actualValue = actualValue;
    }

    public String getBdItemClassify() {
        return bdItemClassify;
    }

    public void setBdItemClassify(String bdItemClassify) {
        this.bdItemClassify = bdItemClassify;
    }

    public BigDecimal getActualAdjustmentValue() {
        return actualAdjustmentValue;
    }

    public void setActualAdjustmentValue(BigDecimal actualAdjustmentValue) {
        this.actualAdjustmentValue = actualAdjustmentValue;
    }

    public BigDecimal getAdjustedActualValue() {
        return adjustedActualValue;
    }

    public void setAdjustedActualValue(BigDecimal adjustedActualValue) {
        this.adjustedActualValue = adjustedActualValue;
    }

    public BigDecimal getPlanAdjustmentValue() {
        return planAdjustmentValue;
    }

    public void setPlanAdjustmentValue(BigDecimal planAdjustmentValue) {
        this.planAdjustmentValue = planAdjustmentValue;
    }

    public BigDecimal getAdjustedPlanValue() {
        return adjustedPlanValue;
    }

    public void setAdjustedPlanValue(BigDecimal adjustedPlanValue) {
        this.adjustedPlanValue = adjustedPlanValue;
    }
}