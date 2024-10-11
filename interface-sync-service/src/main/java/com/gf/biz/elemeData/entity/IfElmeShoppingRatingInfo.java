package com.gf.biz.elemeData.entity;

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


}