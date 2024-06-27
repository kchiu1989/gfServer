package com.gf.biz.ifsSync.entity;


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
 * @since 2024-04-22 17:29:02
 */

@TableName("if_score_mv")
public class IfScoreMv extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 报告对应唯一 key
     */
    @TableField("if_key")
    private String ifKey;

    /**
     * 检查日期（时间戳）
     */
    @TableField("check_time")
    private Date checkTime;

    /**
     * 交付日期（时间戳）
     */
    @TableField("delivery_time")
    private Date deliveryTime;

    /**
     * 门店所在省
     */
    @TableField("province")
    private String province;

    /**
     * 门店所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 品牌名称
     */
    @TableField("brand")
    private String brand;

    /**
     * 门店名称
     */
    @TableField("store")
    private String store;

    @TableField("store_id")
    private String storeId;

    @TableField("store_code")
    private String storeCode;

    /**
     * 报告得分
     */
    @TableField("total_score")
    private BigDecimal totalScore;

    /**
     * 报告评级
     */
    @TableField("total_rate")
    private String totalRate;

    /**
     * 报告类型（1-标准	式，2-流程式，3- 开放式，4-问卷式）	详见文档第 7 项注	意事项
     */
    @TableField("type")
    private Integer type;

    @TableField("pay_money")
    private BigDecimal payMoney;

    @TableField("desk_number")
    private String deskNumber;


    public String getIfKey() {
        return ifKey;
    }

    public void setIfKey(String ifKey) {
        this.ifKey = ifKey;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(String deskNumber) {
        this.deskNumber = deskNumber;
    }
}