package com.gf.biz.wmPerformanceAllPoints.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-10-15 11:39:18
 */

@TableName("bf_takeout_assess_survey")
    public class BfTakeoutAssessSurvey implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 主键id
         */
                @TableId(value = "id",type = IdType.AUTO)
                private Integer id;

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
         * 0正常1删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

        /**
         * 月份
         */
    @TableField("month")
        private String month;

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
         * 区域名称
         */
    @TableField("region_name")
        private String regionName;

        /**
         * 区域编码
         */
    @TableField("region_code")
        private String regionCode;

        /**
         * 部门id
         */
    @TableField("dept_id")
        private Integer deptId;

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
         * 提交时间
         */
    @TableField("submit_time")
        private Date submitTime;

        /**
         * 饿了么得分
         */
    @TableField("eleme_points")
        private String elemePoints;

        /**
         * 饿了么评分
         */
    @TableField("eleme_score")
        private String elemeScore;

        /**
         * 美团得分
         */
    @TableField("meituan_points")
        private String meituanPoints;

        /**
         * 美团评分
         */
    @TableField("meituan_score")
        private String meituanScore;

        /**
         * 美团出餐时长
         */
    @TableField("meituan_takeout_delivery_time")
        private String meituanTakeoutDeliveryTime;

        /**
         * 出餐时长得分
         */
    @TableField("meituan_takeout_delivery_time_points")
        private String meituanTakeoutDeliveryTimePoints;

        /**
         * 1确认0未确认
         */
    @TableField("status")
        private String status;


    @TableField("if_id")
    private Long ifId;
    @TableField("business_date")
    private Date businessDate;
    @TableField("year")
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getElemePoints() {
        return elemePoints;
    }

    public void setElemePoints(String elemePoints) {
        this.elemePoints = elemePoints;
    }

    public String getElemeScore() {
        return elemeScore;
    }

    public void setElemeScore(String elemeScore) {
        this.elemeScore = elemeScore;
    }

    public String getMeituanPoints() {
        return meituanPoints;
    }

    public void setMeituanPoints(String meituanPoints) {
        this.meituanPoints = meituanPoints;
    }

    public String getMeituanScore() {
        return meituanScore;
    }

    public void setMeituanScore(String meituanScore) {
        this.meituanScore = meituanScore;
    }

    public String getMeituanTakeoutDeliveryTime() {
        return meituanTakeoutDeliveryTime;
    }

    public void setMeituanTakeoutDeliveryTime(String meituanTakeoutDeliveryTime) {
        this.meituanTakeoutDeliveryTime = meituanTakeoutDeliveryTime;
    }

    public String getMeituanTakeoutDeliveryTimePoints() {
        return meituanTakeoutDeliveryTimePoints;
    }

    public void setMeituanTakeoutDeliveryTimePoints(String meituanTakeoutDeliveryTimePoints) {
        this.meituanTakeoutDeliveryTimePoints = meituanTakeoutDeliveryTimePoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIfId() {
        return ifId;
    }

    public void setIfId(Long ifId) {
        this.ifId = ifId;
    }
}

