package com.gf.biz.elemeData.entity;

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
 * @since 2024-10-10 16:15:20
 */
@Data
@TableName("if_elme_shopping_rating_info")
    public class IfElmeShoppingRatingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id" ,type = IdType.AUTO)
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
        private Date updatedBy;

        /**
         * ‘0’正常，‘1’删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

        /**
         * 店铺id
         */
    @TableField("shop_id")
        private Integer shopId;

        /**
         * 是否为新指标
         */
    @TableField("is_new_factor")
        private String isNewFactor;

        /**
         * 评分版本标识
         */
    @TableField("factor_version")
        private String factorVersion;

        /**
         * 店铺分数
         */
    @TableField("star_score")
        private String starScore;

        /**
         * 新指标，用户总评
         */
    @TableField("overall_score")
        private String overallScore;

        /**
         * 新指标，味道评分
         */
    @TableField("taste_score")
        private String tasteScore;

        /**
         * 新指标，包装评分
         */
    @TableField("package_score")
        private String packageScore;

        /**
         * 新指标，差评人工回复率
         */
    @TableField("bad_rate_reply_rate")
        private String badRateReplyRate;

        /**
         * 新指标，优质评价率
         */
    @TableField("high_quality_rate")
        private String highQualityRate;

        /**
         * 新指标，订单评价率
         */
    @TableField("order_comment_rate")
        private String orderCommentRate;

        /**
         * 状态
         */
    @TableField("status")
        private String status;
    @TableField("year")
    private String year;

    @TableField("month")
    private String month;

    @TableField("day")
    private String day;

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

    public Date getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Date updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getIsNewFactor() {
        return isNewFactor;
    }

    public void setIsNewFactor(String isNewFactor) {
        this.isNewFactor = isNewFactor;
    }

    public String getFactorVersion() {
        return factorVersion;
    }

    public void setFactorVersion(String factorVersion) {
        this.factorVersion = factorVersion;
    }

    public String getStarScore() {
        return starScore;
    }

    public void setStarScore(String starScore) {
        this.starScore = starScore;
    }

    public String getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(String overallScore) {
        this.overallScore = overallScore;
    }

    public String getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(String tasteScore) {
        this.tasteScore = tasteScore;
    }

    public String getPackageScore() {
        return packageScore;
    }

    public void setPackageScore(String packageScore) {
        this.packageScore = packageScore;
    }

    public String getBadRateReplyRate() {
        return badRateReplyRate;
    }

    public void setBadRateReplyRate(String badRateReplyRate) {
        this.badRateReplyRate = badRateReplyRate;
    }

    public String getHighQualityRate() {
        return highQualityRate;
    }

    public void setHighQualityRate(String highQualityRate) {
        this.highQualityRate = highQualityRate;
    }

    public String getOrderCommentRate() {
        return orderCommentRate;
    }

    public void setOrderCommentRate(String orderCommentRate) {
        this.orderCommentRate = orderCommentRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}