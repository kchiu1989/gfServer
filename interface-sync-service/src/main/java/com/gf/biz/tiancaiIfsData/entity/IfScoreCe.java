package com.gf.biz.tiancaiIfsData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-09-11 16:24:32
 */

@Data
@TableName("if_score_ce")
public class IfScoreCe implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 有效标志
     */
    @TableField("valid_flag")
        private String validFlag;

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
     * 集团ID
     */
    @TableField("gc_id")
    private String gcId;

    /**
     * 门店ID
     */
    @TableField("mc_id")
    private String mcId;

    /**
     * 区域
     */
    @TableField("region")
    private String region;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 门店名称
     */
    @TableField("mc_name")
    private String mcName;

    /**
     * 集团名称
     */
    @TableField("gc_name")
    private String gcName;

    /**
     * 评价模板ID
     */
    @TableField("template_id")
    private String templateId;

    /**
     * 评价模板名称
     */
    @TableField("template_name")
    private String templateName;

    /**
     * if_id,name,value,isMultiSelect唯一主键
     */
    @TableField("if_key")
    private String ifKey;

    /**
     * 评价人昵称
     */
    @TableField("send_nick_name")
    private String sendNickName;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 问题名称
     */
    @TableField("name")
    private String name;

    /**
     * 问题内容
     */
    @TableField("value")
    private String value;

    /**
     * 问题备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 是否是多个问题内容（0：单个，1：多个）
     */
    @TableField("is_multi_select")
    private String isMultiSelect;

    /**
     * 评价订单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 评价业务来源（点餐、堂食、桌边付、门店评价、CRM点餐、自提、外卖、扫码付、未知点餐
     */
    @TableField("business_no")
    private String businessNo;

    /**
     * 问题类型（1：自定义 2：单选 3：多选 4：菜品评价 5：问答题
     */
    @TableField("select_type")
    private String selectType;

    /**
     * 评价记录ID
     */
    @TableField("if_id")
    private String ifId;

    /**
     * 评星
     */
    @TableField("star")
    private String star;

    /**
     * 评价人openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 标签
     */
    @TableField("tips")
    private String tips;

    /**
     * 填空/备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 评价记录创建时间
     */
    @TableField("time")
    private String time;



}