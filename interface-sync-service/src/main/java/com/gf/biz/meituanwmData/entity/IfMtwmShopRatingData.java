package com.gf.biz.meituanwmData.entity;

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
@Data
@TableName("if_mtwm_shop_rating_data")
    public class IfMtwmShopRatingData implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 主键id
         */
                @TableId("id")
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


}