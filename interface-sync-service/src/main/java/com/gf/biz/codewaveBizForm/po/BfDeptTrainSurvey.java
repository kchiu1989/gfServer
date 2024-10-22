package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-22 13:44:31
 */
@TableName("bf_dept_train_survey")
public class BfDeptTrainSurvey implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 提交日期
     */
    @TableField("submit_date")
    private Date submitDate;

    /**
     * 品牌/中心名称
     */
    @TableField("brand_center_name")
    private String brandCenterName;

    /**
     * 品牌/中心编码
     */
    @TableField("brand_center_code")
    private String brandCenterCode;

    /**
     * 评价部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 评价部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 评价部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 审批人id
     */
    @TableField("appr_user_id")
    private Long apprUserId;

    /**
     * 审批人名称
     */
    @TableField("appr_user_name")
    private String apprUserName;

    /**
     * 审批状态标识
     */
    @TableField("status")
    private String status;

    /**
     * 审批状态名
     */
    @TableField("status_name")
    private String statusName;

    @TableField("weighting_score")
    private Double weightingScore;

    /**
     * 总分
     */
    @TableField("total_score")
    private BigDecimal totalScore;

    /**
     * 流程实例id
     */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
     * 表单单据类型id
     */
    @TableField("bd_form_type_id")
    private Long bdFormTypeId;

    /**
     * 表单单据类型代码
     */
    @TableField("bd_form_type_code")
    private String bdFormTypeCode;

    /**
     * 部门类型
     */
    @TableField("dept_type_name")
    private String deptTypeName;

    /**
     * 部门类型编码
     */
    @TableField("dept_type_code")
    private String deptTypeCode;

    /**
     * 创建者姓名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 业务日期
     */
    @TableField("business_date")
    private Date businessDate;

    /**
     * 业务年
     */
    @TableField("biz_year")
    private Integer bizYear;

    /**
     * 业务季度
     */
    @TableField("biz_quarter")
    private Integer bizQuarter;

    /**
     * 业务月
     */
    @TableField("biz_month")
    private Integer bizMonth;

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getBrandCenterName() {
        return brandCenterName;
    }

    public void setBrandCenterName(String brandCenterName) {
        this.brandCenterName = brandCenterName;
    }

    public String getBrandCenterCode() {
        return brandCenterCode;
    }

    public void setBrandCenterCode(String brandCenterCode) {
        this.brandCenterCode = brandCenterCode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public String getApprUserName() {
        return apprUserName;
    }

    public void setApprUserName(String apprUserName) {
        this.apprUserName = apprUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Double getWeightingScore() {
        return weightingScore;
    }

    public void setWeightingScore(Double weightingScore) {
        this.weightingScore = weightingScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getBdFormTypeId() {
        return bdFormTypeId;
    }

    public void setBdFormTypeId(Long bdFormTypeId) {
        this.bdFormTypeId = bdFormTypeId;
    }

    public String getBdFormTypeCode() {
        return bdFormTypeCode;
    }

    public void setBdFormTypeCode(String bdFormTypeCode) {
        this.bdFormTypeCode = bdFormTypeCode;
    }

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName;
    }

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getBizYear() {
        return bizYear;
    }

    public void setBizYear(Integer bizYear) {
        this.bizYear = bizYear;
    }

    public Integer getBizQuarter() {
        return bizQuarter;
    }

    public void setBizQuarter(Integer bizQuarter) {
        this.bizQuarter = bizQuarter;
    }

    public Integer getBizMonth() {
        return bizMonth;
    }

    public void setBizMonth(Integer bizMonth) {
        this.bizMonth = bizMonth;
    }
}