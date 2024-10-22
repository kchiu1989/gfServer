package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 月度总结提交
 * </p>
 *
 * @author Gf
 * @since 2024-10-21 16:33:22
 */
@TableName("bf_budget_plan_monthly_summary")
    public class BfBudgetPlanMonthlySummary extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;
        /**
         * 名称
         */
    @TableField("title")
        private String title;

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
         * 提交人所属部门ID
         */
    @TableField("dept_id")
        private Integer deptId;

        /**
         * 提交人所属部门名称
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 提交人所属部门编码
         */
    @TableField("dept_code")
        private String deptCode;

        /**
         * 对象部门ID
         */
    @TableField("target_dept_id")
        private Integer targetDeptId;

        /**
         * 对象部门名称
         */
    @TableField("target_dept_name")
        private String targetDeptName;

        /**
         * 对象部门编码
         */
    @TableField("target_dept_code")
        private String targetDeptCode;

        /**
         * 对象部门类别
         */
    @TableField("target_dept_category")
        private String targetDeptCategory;

        /**
         * 对象部门类别编码
         */
    @TableField("target_dept_category_code")
        private String targetDeptCategoryCode;

        /**
         * 品牌/中心
         */
    @TableField("brand_center_name")
        private String brandCenterName;

        /**
         * 区域
         */
    @TableField("region_name")
        private String regionName;

        /**
         * 创建时间
         */


        /**
         * 单据状态
         */
    @TableField("enable_flag")
        private String enableFlag;

        /**
         * 创建人用户ID
         */
    @TableField("created_user_id")
        private String createdUserId;

        /**
         * 更新人用户ID
         */
    @TableField("updated_user_id")
        private String updatedUserId;

        /**
         * 月计划达成率
         */
    @TableField("achievement_rate")
        private BigDecimal achievementRate;

        /**
         * 填表人职位确认
         */
    @TableField("filler_Job_title")
        private String fillerJobTitle;

        /**
         * 厨师长姓名
         */
    @TableField("head_chef_name")
        private String headChefName;

        /**
         * 厨师长用户ID
         */
    @TableField("head_chef_user_id")
        private String headChefUserId;

        /**
         * 前厅经理姓名
         */
    @TableField("front_office_manager_name")
        private String frontOfficeManagerName;

        /**
         * 前厅经理用户ID
         */
    @TableField("front_office_manager_user_id")
        private String frontOfficeManagerUserId;

        /**
         * 工作流实例ID
         */
    @TableField("process_instance_id")
        private String processInstanceId;

        /**
         * 工作流提交时间
         */
    @TableField("process_submit_time")
        private Date processSubmitTime;

        /**
         * 审批人用户ID
         */
    @TableField("approver_user_ids")
        private String approverUserIds;

        /**
         * 审批人真实姓名
         */
    @TableField("approver_display_names")
        private String approverDisplayNames;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public Integer getTargetDeptId() {
        return targetDeptId;
    }

    public void setTargetDeptId(Integer targetDeptId) {
        this.targetDeptId = targetDeptId;
    }

    public String getTargetDeptName() {
        return targetDeptName;
    }

    public void setTargetDeptName(String targetDeptName) {
        this.targetDeptName = targetDeptName;
    }

    public String getTargetDeptCode() {
        return targetDeptCode;
    }

    public void setTargetDeptCode(String targetDeptCode) {
        this.targetDeptCode = targetDeptCode;
    }

    public String getTargetDeptCategory() {
        return targetDeptCategory;
    }

    public void setTargetDeptCategory(String targetDeptCategory) {
        this.targetDeptCategory = targetDeptCategory;
    }

    public String getTargetDeptCategoryCode() {
        return targetDeptCategoryCode;
    }

    public void setTargetDeptCategoryCode(String targetDeptCategoryCode) {
        this.targetDeptCategoryCode = targetDeptCategoryCode;
    }

    public String getBrandCenterName() {
        return brandCenterName;
    }

    public void setBrandCenterName(String brandCenterName) {
        this.brandCenterName = brandCenterName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public BigDecimal getAchievementRate() {
        return achievementRate;
    }

    public void setAchievementRate(BigDecimal achievementRate) {
        this.achievementRate = achievementRate;
    }

    public String getFillerJobTitle() {
        return fillerJobTitle;
    }

    public void setFillerJobTitle(String fillerJobTitle) {
        this.fillerJobTitle = fillerJobTitle;
    }

    public String getHeadChefName() {
        return headChefName;
    }

    public void setHeadChefName(String headChefName) {
        this.headChefName = headChefName;
    }

    public String getHeadChefUserId() {
        return headChefUserId;
    }

    public void setHeadChefUserId(String headChefUserId) {
        this.headChefUserId = headChefUserId;
    }

    public String getFrontOfficeManagerName() {
        return frontOfficeManagerName;
    }

    public void setFrontOfficeManagerName(String frontOfficeManagerName) {
        this.frontOfficeManagerName = frontOfficeManagerName;
    }

    public String getFrontOfficeManagerUserId() {
        return frontOfficeManagerUserId;
    }

    public void setFrontOfficeManagerUserId(String frontOfficeManagerUserId) {
        this.frontOfficeManagerUserId = frontOfficeManagerUserId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getProcessSubmitTime() {
        return processSubmitTime;
    }

    public void setProcessSubmitTime(Date processSubmitTime) {
        this.processSubmitTime = processSubmitTime;
    }

    public String getApproverUserIds() {
        return approverUserIds;
    }

    public void setApproverUserIds(String approverUserIds) {
        this.approverUserIds = approverUserIds;
    }

    public String getApproverDisplayNames() {
        return approverDisplayNames;
    }

    public void setApproverDisplayNames(String approverDisplayNames) {
        this.approverDisplayNames = approverDisplayNames;
    }
}