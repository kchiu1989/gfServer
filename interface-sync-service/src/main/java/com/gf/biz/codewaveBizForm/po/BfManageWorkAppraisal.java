package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 管理人员工作表现鉴定表
 * </p>
 *
 * @author Gf
 * @since 2024-10-25 17:35:32
 */
@TableName("bf_manage_work_appraisal")
    public class BfManageWorkAppraisal extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;
        /**
         * 提交日期
         */
    @TableField("submit_date")
        private Date submitDate;

        /**
         * 评价部门id
         */
    @TableField("evaluated_dept_id")
        private Long evaluatedDeptId;

        /**
         * 评价部门编码
         */
    @TableField("evaluated_dept_code")
        private String evaluatedDeptCode;

        /**
         * 评价部门名称
         */
    @TableField("evaluated_dept_name")
        private String evaluatedDeptName;

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
         * 评价人id
         */
    @TableField("evaluate_user_id")
        private Long evaluateUserId;

        /**
         * 评价人名
         */
    @TableField("evaluate_user_name")
        private String evaluateUserName;

        /**
         * 总分
         */
    @TableField("total_score")
        private BigDecimal totalScore;

        /**
         * 被评价人id
         */
    @TableField("evaluated_user_id")
        private Long evaluatedUserId;

        /**
         * 被评价人名
         */
    @TableField("evaluated_user_name")
        private String evaluatedUserName;

        /**
         * '0'不属于经理'1'属于经理
         */
    @TableField("manager_flag")
        private String managerFlag;

        /**
         * 基本考评得分
         */
    @TableField("score1")
        private BigDecimal score1;

        /**
         * 企业价值观得分
         */
    @TableField("score2")
        private BigDecimal score2;

        /**
         * 工作态度得分
         */
    @TableField("score3")
        private BigDecimal score3;

        /**
         * 管理能力得分
         */
    @TableField("score4")
        private BigDecimal score4;

        /**
         * 工作表现得分
         */
    @TableField("score5")
        private BigDecimal score5;

        /**
         * 评语
         */
    @TableField("comment")
        private String comment;

        /**
         * 业务日期
         */
    @TableField("business_date")
        private Date businessDate;

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
         * 企业活动参与

         */
    @TableField("score6")
        private BigDecimal score6;

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

    public Long getEvaluatedUserId() {
        return evaluatedUserId;
    }

    public void setEvaluatedUserId(Long evaluatedUserId) {
        this.evaluatedUserId = evaluatedUserId;
    }

    public String getEvaluatedUserName() {
        return evaluatedUserName;
    }

    public void setEvaluatedUserName(String evaluatedUserName) {
        this.evaluatedUserName = evaluatedUserName;
    }

    public String getManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(String managerFlag) {
        this.managerFlag = managerFlag;
    }

    public BigDecimal getScore1() {
        return score1;
    }

    public void setScore1(BigDecimal score1) {
        this.score1 = score1;
    }

    public BigDecimal getScore2() {
        return score2;
    }

    public void setScore2(BigDecimal score2) {
        this.score2 = score2;
    }

    public BigDecimal getScore3() {
        return score3;
    }

    public void setScore3(BigDecimal score3) {
        this.score3 = score3;
    }

    public BigDecimal getScore4() {
        return score4;
    }

    public void setScore4(BigDecimal score4) {
        this.score4 = score4;
    }

    public BigDecimal getScore5() {
        return score5;
    }

    public void setScore5(BigDecimal score5) {
        this.score5 = score5;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
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

    public BigDecimal getScore6() {
        return score6;
    }

    public void setScore6(BigDecimal score6) {
        this.score6 = score6;
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