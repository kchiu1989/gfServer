package com.gf.biz.funcDeptIndicatorScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 部门服务满意度评分表
 * </p>
 *
 * @author Gf
 * @since 2024-10-18 15:42:54
 */
@TableName("bf_dept_service_evaluate")
    public class BfDeptServiceEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

                @TableId(value = "id", type = IdType.AUTO)
                private Long id;

        /**
         * 创建时间
         */
    @TableField("created_time")
        private Date createdTime;

        /**
         * 更新时间
         */
    @TableField("updated_time")
        private Date updatedTime;

        /**
         * 创建者
         */
    @TableField("created_by")
        private String createdBy;

        /**
         * 更新者
         */
    @TableField("updated_by")
        private String updatedBy;

        /**
         * '0'正常 '1'删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

        /**
         * 评价端口
         */
    @TableField("evaluate_port_name")
        private String evaluatePortName;

        /**
         * 评价端口字典代码
         */
    @TableField("evaluate_port_code")
        private String evaluatePortCode;

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

        /**
         * 被评价部门id
         */
    @TableField("evaluated_dept_id")
        private Long evaluatedDeptId;

        /**
         * 被评价部门编码
         */
    @TableField("evaluated_dept_code")
        private String evaluatedDeptCode;

        /**
         * 被评价部门名称
         */
    @TableField("evaluated_dept_name")
        private String evaluatedDeptName;

        /**
         * 评价人id
         */
    @TableField("evaluate_user_id")
        private Long evaluateUserId;

        /**
         * 评价人名称
         */
    @TableField("evaluate_user_name")
        private String evaluateUserName;

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
         * 业务发生年
         */
    @TableField("business_year")
        private Integer businessYear;

        /**
         * 业务发生月
         */
    @TableField("business_month")
        private Integer businessMonth;

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
         * 创建者姓名
         */
    @TableField("create_user_name")
        private String createUserName;

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

        /**
         * 流程任务id
         */
    @TableField("process_task_id")
        private String processTaskId;
    @TableField("evaluate_user_account")
    private String evaluateUserAccount;

    public String getEvaluateUserAccount() {
        return evaluateUserAccount;
    }

    public void setEvaluateUserAccount(String evaluateUserAccount) {
        this.evaluateUserAccount = evaluateUserAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public String getEvaluatePortName() {
        return evaluatePortName;
    }

    public void setEvaluatePortName(String evaluatePortName) {
        this.evaluatePortName = evaluatePortName;
    }

    public String getEvaluatePortCode() {
        return evaluatePortCode;
    }

    public void setEvaluatePortCode(String evaluatePortCode) {
        this.evaluatePortCode = evaluatePortCode;
    }

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

    public Long getEvaluatedDeptId() {
        return evaluatedDeptId;
    }

    public void setEvaluatedDeptId(Long evaluatedDeptId) {
        this.evaluatedDeptId = evaluatedDeptId;
    }

    public String getEvaluatedDeptCode() {
        return evaluatedDeptCode;
    }

    public void setEvaluatedDeptCode(String evaluatedDeptCode) {
        this.evaluatedDeptCode = evaluatedDeptCode;
    }

    public String getEvaluatedDeptName() {
        return evaluatedDeptName;
    }

    public void setEvaluatedDeptName(String evaluatedDeptName) {
        this.evaluatedDeptName = evaluatedDeptName;
    }

    public Long getEvaluateUserId() {
        return evaluateUserId;
    }

    public void setEvaluateUserId(Long evaluateUserId) {
        this.evaluateUserId = evaluateUserId;
    }

    public String getEvaluateUserName() {
        return evaluateUserName;
    }

    public void setEvaluateUserName(String evaluateUserName) {
        this.evaluateUserName = evaluateUserName;
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

    public Integer getBusinessYear() {
        return businessYear;
    }

    public void setBusinessYear(Integer businessYear) {
        this.businessYear = businessYear;
    }

    public Integer getBusinessMonth() {
        return businessMonth;
    }

    public void setBusinessMonth(Integer businessMonth) {
        this.businessMonth = businessMonth;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }
}