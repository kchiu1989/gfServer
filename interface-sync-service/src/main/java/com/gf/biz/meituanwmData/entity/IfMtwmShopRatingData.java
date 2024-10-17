package com.gf.biz.meituanwmData.entity;

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
 * @since 2024-10-11 09:32:26
 */

@TableName("if_mtwm_shop_rating_data")
    public class IfMtwmShopRatingData implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 主键id
         */
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
         * 删除标识，0正常，1删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

        /**
         * 商家评分
         */
    @TableField("avg_poi_score")
        private String avgPoiScore;

        /**
         * 口味
         */
    @TableField("avg_taste_score")
        private String avgTasteScore;

        /**
         * 包装
         */
    @TableField("avg_packing_score")
        private String avgPackingScore;

        /**
         * 配送评分
         */
    @TableField("avg_delivery_score")
        private String avgDeliveryScore;

        /**
         * 配送满意度
         */
    @TableField("delivery_satisfaction")
        private String deliverySatisfaction;

        /**
         * 状态1启用2停用
         */
    @TableField("status")
        private String status;

    @TableField("app_poi_code")
    private String appPoiCode;

    @TableField("shop_id")
    private String shopId;

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

    public String getAvgPoiScore() {
        return avgPoiScore;
    }

    public void setAvgPoiScore(String avgPoiScore) {
        this.avgPoiScore = avgPoiScore;
    }

    public String getAvgTasteScore() {
        return avgTasteScore;
    }

    public void setAvgTasteScore(String avgTasteScore) {
        this.avgTasteScore = avgTasteScore;
    }

    public String getAvgPackingScore() {
        return avgPackingScore;
    }

    public void setAvgPackingScore(String avgPackingScore) {
        this.avgPackingScore = avgPackingScore;
    }

    public String getAvgDeliveryScore() {
        return avgDeliveryScore;
    }

    public void setAvgDeliveryScore(String avgDeliveryScore) {
        this.avgDeliveryScore = avgDeliveryScore;
    }

    public String getDeliverySatisfaction() {
        return deliverySatisfaction;
    }

    public void setDeliverySatisfaction(String deliverySatisfaction) {
        this.deliverySatisfaction = deliverySatisfaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppPoiCode() {
        return appPoiCode;
    }

    public void setAppPoiCode(String appPoiCode) {
        this.appPoiCode = appPoiCode;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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