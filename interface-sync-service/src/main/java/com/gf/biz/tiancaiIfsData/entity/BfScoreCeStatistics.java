package com.gf.biz.tiancaiIfsData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-09-25 16:06:27
 */
@Data
@TableName("bf_score_ce_statistics")
    public class BfScoreCeStatistics implements Serializable {

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
         * 集团ID
         */
    @TableField("unit_id")
        private String unitId;

        /**
         * 门店ID
         */
    @TableField("dept_id")
        private Long deptId;

        /**
         * 集团名称
         */
    @TableField("unit_name")
        private String unitName;

        /**
         * 门店名称
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 年
         */
    @TableField("year")
        private Integer year;

        /**
         * 月
         */
    @TableField("month")
        private Integer month;

        /**
         * 评价次数
         */
    @TableField("ce_cnt")
        private Integer ceCnt;

        /**
         * 评价项1平均分
         */
    @TableField("item_score1")
        private BigDecimal itemScore1;

        /**
         * 评价项2平均分
         */
    @TableField("item_score2")
        private BigDecimal itemScore2;

        /**
         * 评价项3平均分
         */
    @TableField("item_score3")
        private BigDecimal itemScore3;

        /**
         * 评价项4平均分
         */
    @TableField("item_score4")
        private BigDecimal itemScore4;

        /**
         * 评价项5平均分
         */
    @TableField("item_score5")
        private BigDecimal itemScore5;

        /**
         * 门店编码
         */
    @TableField("dept_code")
        private String deptCode;

        /**
         * 得分率
         */
    @TableField("get_point")
        private BigDecimal getPoint;

        /**
         * 关联底表id
         */
    @TableField("if_id")
        private Integer ifId;

    @TableField("status")
    private Integer status;


}