package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-17 16:48:16
 */
@TableName("bf_certificate_access_survey")
public class BfCertificateAccessSurvey extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 提交日期
     */
    @TableField("submit_date")
    private Date submitDate;

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
     * 区域
     */
    @TableField("region_name")
    private String regionName;

    /**
     * 区域名称
     */
    @TableField("region_code")
    private String regionCode;

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
     * 得分
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
     * 达成率
     */
    @TableField("achieve_rate")
    private BigDecimal achieveRate;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 加权得分
     */
    @TableField("weighted_score")
    private BigDecimal weightedScore;

    /**
     * 业务日期
     */
    @TableField("business_date")
    private Date businessDate;

    /**
     * 业务年
     */
    @TableField("business_year")
    private String businessYear;

    /**
     * 业务季度
     */
    @TableField("business_quarter")
    private String businessQuarter;

    /**
     * 创建者名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 流程任务id
     */
    @TableField("process_task_id")
    private String processTaskId;

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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

    public BigDecimal getAchieveRate() {
        return achieveRate;
    }

    public void setAchieveRate(BigDecimal achieveRate) {
        this.achieveRate = achieveRate;
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

    public BigDecimal getWeightedScore() {
        return weightedScore;
    }

    public void setWeightedScore(BigDecimal weightedScore) {
        this.weightedScore = weightedScore;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getBusinessYear() {
        return businessYear;
    }

    public void setBusinessYear(String businessYear) {
        this.businessYear = businessYear;
    }

    public String getBusinessQuarter() {
        return businessQuarter;
    }

    public void setBusinessQuarter(String businessQuarter) {
        this.businessQuarter = businessQuarter;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }
}