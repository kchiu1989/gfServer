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
 * @since 2024-10-09 14:15:02
 */
@Data
@TableName("if_eleme_shop_info")
public class IfElemeShopInfo implements Serializable {

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
     * 是否叶子节点店铺 '1'是 '0'不是
     */
    @TableField("leaf_flag")
    private String leafFlag;

    /**
     * '0'连锁'1'店铺
     */
    @TableField("level_flag")
    private String levelFlag;

    /**
     * 父级id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 店铺/连锁名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 店铺/连锁id
     */
    @TableField("shop_id")
    private String shopId;
    /**
     * 店铺/连锁appid
     */
    @TableField("app_id")
    private String appId;


}