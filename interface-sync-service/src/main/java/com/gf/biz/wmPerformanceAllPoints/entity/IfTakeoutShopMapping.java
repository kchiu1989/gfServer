package com.gf.biz.wmPerformanceAllPoints.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-10-14 16:22:42
 */

@TableName("if_takeout_shop_mapping")
    public class IfTakeoutShopMapping implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 店面编号
         */
    @TableField( "id")
        private Integer id;

        /**
         * 店面名称
         */
    @TableField("name")
        private String name;

        /**
         * 门店id-美团
         */
    @TableField("meituan_shopping_id")
        private String meituanShoppingId;

        /**
         * 门店id-饿了么
         */
    @TableField("eleme_shopping_id")
        private String elemeShoppingId;

        /**
         * 门店电话
         */
    @TableField("shopping_mobile")
        private String shoppingMobile;

        /**
         * 店经理
         */
    @TableField("shopping_manager")
        private String shoppingManager;

        /**
         * 店经理电话
         */
    @TableField("shopping_manager_mobile")
        private String shoppingManagerMobile;

        /**
         * 厨师长
         */
    @TableField("head_cook")
        private String headCook;

        /**
         * 厨师长电话
         */
    @TableField("head_cook_mobile")
        private String headCookMobile;

        /**
         * 门店地址
         */
    @TableField("shopping_address")
        private String shoppingAddress;

        /**
         * 开业时间
         */
    @TableField("created_time")
        private String createdTime;

        /**
         * 重装开业时间
         */
    @TableField("recreated_time")
        private String recreatedTime;

        /**
         * 区域
         */
    @TableField("region")
        private String region;

        /**
         * 区域经理
         */
    @TableField("region_manager")
        private String regionManager;

        /**
         * 区域技术督导
         */
    @TableField("region_technique_manager")
        private String regionTechniqueManager;

        /**
         * 品牌总监
         */
    @TableField("brand_director")
        private String brandDirector;

        /**
         * 品牌技术督导
         */
    @TableField("brand_technique_manager")
        private String brandTechniqueManager;

        /**
         * 品牌中心
         */
    @TableField("brand")
        private String brand;

        /**
         * 省份
         */
    @TableField("province")
        private String province;

        /**
         * 城市
         */
    @TableField("city")
        private String city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeituanShoppingId() {
        return meituanShoppingId;
    }

    public void setMeituanShoppingId(String meituanShoppingId) {
        this.meituanShoppingId = meituanShoppingId;
    }

    public String getElemeShoppingId() {
        return elemeShoppingId;
    }

    public void setElemeShoppingId(String elemeShoppingId) {
        this.elemeShoppingId = elemeShoppingId;
    }

    public String getShoppingMobile() {
        return shoppingMobile;
    }

    public void setShoppingMobile(String shoppingMobile) {
        this.shoppingMobile = shoppingMobile;
    }

    public String getShoppingManager() {
        return shoppingManager;
    }

    public void setShoppingManager(String shoppingManager) {
        this.shoppingManager = shoppingManager;
    }

    public String getShoppingManagerMobile() {
        return shoppingManagerMobile;
    }

    public void setShoppingManagerMobile(String shoppingManagerMobile) {
        this.shoppingManagerMobile = shoppingManagerMobile;
    }

    public String getHeadCook() {
        return headCook;
    }

    public void setHeadCook(String headCook) {
        this.headCook = headCook;
    }

    public String getHeadCookMobile() {
        return headCookMobile;
    }

    public void setHeadCookMobile(String headCookMobile) {
        this.headCookMobile = headCookMobile;
    }

    public String getShoppingAddress() {
        return shoppingAddress;
    }

    public void setShoppingAddress(String shoppingAddress) {
        this.shoppingAddress = shoppingAddress;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getRecreatedTime() {
        return recreatedTime;
    }

    public void setRecreatedTime(String recreatedTime) {
        this.recreatedTime = recreatedTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionManager() {
        return regionManager;
    }

    public void setRegionManager(String regionManager) {
        this.regionManager = regionManager;
    }

    public String getRegionTechniqueManager() {
        return regionTechniqueManager;
    }

    public void setRegionTechniqueManager(String regionTechniqueManager) {
        this.regionTechniqueManager = regionTechniqueManager;
    }

    public String getBrandDirector() {
        return brandDirector;
    }

    public void setBrandDirector(String brandDirector) {
        this.brandDirector = brandDirector;
    }

    public String getBrandTechniqueManager() {
        return brandTechniqueManager;
    }

    public void setBrandTechniqueManager(String brandTechniqueManager) {
        this.brandTechniqueManager = brandTechniqueManager;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
}