package com.gf.biz.tiancaiIfsData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-09-11 16:36:12
 */

@TableName("if_score_ce_statistics")
public class IfScoreCeStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private String itemScore1;

    /**
     * 评价项2平均分
     */
    @TableField("item_score2")
    private String itemScore2;

    /**
     * 评价项3平均分
     */
    @TableField("item_score3")
    private String itemScore3;

    /**
     * 评价项4平均分
     */
    @TableField("item_score4")
    private String itemScore4;

    /**
     * 评价项5平均分
     */
    @TableField("item_score5")
    private String itemScore5;

    /**
     * 门店编码
     */
    @TableField("dept_code")
    private String deptCode;
    @TableField("get_point")
    private BigDecimal getPoint;

    public BigDecimal getGetPoint() {
        return getPoint;
    }

    public void setGetPoint(BigDecimal getPoint) {
        this.getPoint = getPoint;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public String getUnitId() {
        return unitId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getDeptName() {
        return deptName;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getCeCnt() {
        return ceCnt;
    }

    public String getItemScore1() {
        return itemScore1;
    }

    public String getItemScore2() {
        return itemScore2;
    }

    public String getItemScore3() {
        return itemScore3;
    }

    public String getItemScore4() {
        return itemScore4;
    }

    public String getItemScore5() {
        return itemScore5;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setCeCnt(Integer ceCnt) {
        this.ceCnt = ceCnt;
    }

    public void setItemScore1(String itemScore1) {
        this.itemScore1 = itemScore1;
    }

    public void setItemScore2(String itemScore2) {
        this.itemScore2 = itemScore2;
    }

    public void setItemScore3(String itemScore3) {
        this.itemScore3 = itemScore3;
    }

    public void setItemScore4(String itemScore4) {
        this.itemScore4 = itemScore4;
    }

    public void setItemScore5(String itemScore5) {
        this.itemScore5 = itemScore5;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}
