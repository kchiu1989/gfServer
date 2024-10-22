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
 * @since 2024-10-22 10:45:45
 */
@TableName("bf_work_collaborate")
public class BfWorkCollaborate extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 提交日期
     */
    @TableField("submit_date")
    private Date submitDate;

    /**
     * 协作部门类型
     */
    @TableField("co_dept_type_name")
    private String coDeptTypeName;

    /**
     * 协作部门类型编码
     */
    @TableField("co_dept_type_code")
    private String coDeptTypeCode;

    /**
     * 协作品牌/中心名称
     */
    @TableField("co_brand_center_name")
    private String coBrandCenterName;

    /**
     * 品协作牌/中心编码
     */
    @TableField("co_brand_center_code")
    private String coBrandCenterCode;

    /**
     * 协作区域
     */
    @TableField("co_region_name")
    private String coRegionName;

    /**
     * 协作区域名称
     */
    @TableField("co_region_code")
    private String coRegionCode;

    /**
     * 发起部门id
     */
    @TableField("apply_dept_id")
    private Long applyDeptId;

    /**
     * 发起部门编码
     */
    @TableField("apply_dept_code")
    private String applyDeptCode;

    /**
     * 发起部门名称
     */
    @TableField("apply_dept_name")
    private String applyDeptName;

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

    /**
     * 协作部门id
     */
    @TableField("co_dept_id")
    private Long coDeptId;

    /**
     * 协作部门编码
     */
    @TableField("co_dept_code")
    private String coDeptCode;

    /**
     * 协作部门名称
     */
    @TableField("co_dept_name")
    private String coDeptName;

    /**
     * 协作人id
     */
    @TableField("co_user_id")
    private Long coUserId;

    /**
     * 协作人名称
     */
    @TableField("co_user_name")
    private String coUserName;

    /**
     * 总分
     */
    @TableField("total_score")
    private BigDecimal totalScore;

    /**
     * 业务日期
     */
    @TableField("business_date")
    private Date businessDate;

    /**
     * 督办人id
     */
    @TableField("supervise_user_id")
    private Long superviseUserId;

    /**
     * 督办人名称
     */
    @TableField("supervise_user_name")
    private String superviseUserName;

    /**
     * 0否1是,协作是否完成
     */
    @TableField("co_finish_flag")
    private String coFinishFlag;

    /**
     * 效率评分
     */
    @TableField("co_efficiency_score")
    private BigDecimal coEfficiencyScore;

    /**
     * 态度评分
     */
    @TableField("co_attitude_score")
    private BigDecimal coAttitudeScore;

    /**
     * 质量评分
     */
    @TableField("co_quality_score")
    private BigDecimal coQualityScore;

    /**
     * 创建者姓名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 流程实例Id
     */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
     * 是否进行营销企划
     */
    @TableField("plan_flag")
    private String planFlag;

    /**
     * 协作人账号
     */
    @TableField("co_user_account")
    private String coUserAccount;

    /**
     * 督办人账号
     */
    @TableField("supervise_user_account")
    private String superviseUserAccount;

    /**
     * 发起人账号
     */
    @TableField("create_user_account")
    private String createUserAccount;

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

    public String getCoDeptTypeName() {
        return coDeptTypeName;
    }

    public void setCoDeptTypeName(String coDeptTypeName) {
        this.coDeptTypeName = coDeptTypeName;
    }

    public String getCoDeptTypeCode() {
        return coDeptTypeCode;
    }

    public void setCoDeptTypeCode(String coDeptTypeCode) {
        this.coDeptTypeCode = coDeptTypeCode;
    }

    public String getCoBrandCenterName() {
        return coBrandCenterName;
    }

    public void setCoBrandCenterName(String coBrandCenterName) {
        this.coBrandCenterName = coBrandCenterName;
    }

    public String getCoBrandCenterCode() {
        return coBrandCenterCode;
    }

    public void setCoBrandCenterCode(String coBrandCenterCode) {
        this.coBrandCenterCode = coBrandCenterCode;
    }

    public String getCoRegionName() {
        return coRegionName;
    }

    public void setCoRegionName(String coRegionName) {
        this.coRegionName = coRegionName;
    }

    public String getCoRegionCode() {
        return coRegionCode;
    }

    public void setCoRegionCode(String coRegionCode) {
        this.coRegionCode = coRegionCode;
    }

    public Long getApplyDeptId() {
        return applyDeptId;
    }

    public void setApplyDeptId(Long applyDeptId) {
        this.applyDeptId = applyDeptId;
    }

    public String getApplyDeptCode() {
        return applyDeptCode;
    }

    public void setApplyDeptCode(String applyDeptCode) {
        this.applyDeptCode = applyDeptCode;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
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

    public Long getCoDeptId() {
        return coDeptId;
    }

    public void setCoDeptId(Long coDeptId) {
        this.coDeptId = coDeptId;
    }

    public String getCoDeptCode() {
        return coDeptCode;
    }

    public void setCoDeptCode(String coDeptCode) {
        this.coDeptCode = coDeptCode;
    }

    public String getCoDeptName() {
        return coDeptName;
    }

    public void setCoDeptName(String coDeptName) {
        this.coDeptName = coDeptName;
    }

    public Long getCoUserId() {
        return coUserId;
    }

    public void setCoUserId(Long coUserId) {
        this.coUserId = coUserId;
    }

    public String getCoUserName() {
        return coUserName;
    }

    public void setCoUserName(String coUserName) {
        this.coUserName = coUserName;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Long getSuperviseUserId() {
        return superviseUserId;
    }

    public void setSuperviseUserId(Long superviseUserId) {
        this.superviseUserId = superviseUserId;
    }

    public String getSuperviseUserName() {
        return superviseUserName;
    }

    public void setSuperviseUserName(String superviseUserName) {
        this.superviseUserName = superviseUserName;
    }

    public String getCoFinishFlag() {
        return coFinishFlag;
    }

    public void setCoFinishFlag(String coFinishFlag) {
        this.coFinishFlag = coFinishFlag;
    }

    public BigDecimal getCoEfficiencyScore() {
        return coEfficiencyScore;
    }

    public void setCoEfficiencyScore(BigDecimal coEfficiencyScore) {
        this.coEfficiencyScore = coEfficiencyScore;
    }

    public BigDecimal getCoAttitudeScore() {
        return coAttitudeScore;
    }

    public void setCoAttitudeScore(BigDecimal coAttitudeScore) {
        this.coAttitudeScore = coAttitudeScore;
    }

    public BigDecimal getCoQualityScore() {
        return coQualityScore;
    }

    public void setCoQualityScore(BigDecimal coQualityScore) {
        this.coQualityScore = coQualityScore;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(String planFlag) {
        this.planFlag = planFlag;
    }

    public String getCoUserAccount() {
        return coUserAccount;
    }

    public void setCoUserAccount(String coUserAccount) {
        this.coUserAccount = coUserAccount;
    }

    public String getSuperviseUserAccount() {
        return superviseUserAccount;
    }

    public void setSuperviseUserAccount(String superviseUserAccount) {
        this.superviseUserAccount = superviseUserAccount;
    }

    public String getCreateUserAccount() {
        return createUserAccount;
    }

    public void setCreateUserAccount(String createUserAccount) {
        this.createUserAccount = createUserAccount;
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