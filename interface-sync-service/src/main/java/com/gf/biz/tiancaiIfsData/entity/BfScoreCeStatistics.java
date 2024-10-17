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
    @TableField("business_date")
    private Date businessDate;

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getCeCnt() {
        return ceCnt;
    }

    public void setCeCnt(Integer ceCnt) {
        this.ceCnt = ceCnt;
    }

    public BigDecimal getItemScore1() {
        return itemScore1;
    }

    public void setItemScore1(BigDecimal itemScore1) {
        this.itemScore1 = itemScore1;
    }

    public BigDecimal getItemScore2() {
        return itemScore2;
    }

    public void setItemScore2(BigDecimal itemScore2) {
        this.itemScore2 = itemScore2;
    }

    public BigDecimal getItemScore3() {
        return itemScore3;
    }

    public void setItemScore3(BigDecimal itemScore3) {
        this.itemScore3 = itemScore3;
    }

    public BigDecimal getItemScore4() {
        return itemScore4;
    }

    public void setItemScore4(BigDecimal itemScore4) {
        this.itemScore4 = itemScore4;
    }

    public BigDecimal getItemScore5() {
        return itemScore5;
    }

    public void setItemScore5(BigDecimal itemScore5) {
        this.itemScore5 = itemScore5;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public BigDecimal getGetPoint() {
        return getPoint;
    }

    public void setGetPoint(BigDecimal getPoint) {
        this.getPoint = getPoint;
    }

    public Integer getIfId() {
        return ifId;
    }

    public void setIfId(Integer ifId) {
        this.ifId = ifId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}