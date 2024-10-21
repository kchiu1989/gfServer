package com.gf.biz.operateIndicatorScore.dto;

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
 * @since 2024-10-16 16:53:16
 */

public class BdIndicatorDeptScoreDto extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 更新者
     */
    private String updatedBy;

    /**
     * '0'正常 '1'删除
     */
    private String deletedFlag;

    /**
     * 绩效年
     */
    private Integer year;

    /**
     * 绩效月/季度
     */
    private Integer monthQuarter;

    /**
     * 直属部门名称
     */
    private String deptName;

    /**
     * 直属部门编码
     */
    private String deptCode;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 0运营部门1职能部门
     */
    private String deptClassifyFlag;

    /**
     * 未加权得分
     */
    private BigDecimal finalScore;

    /**
     * 中间得分
     */
    private BigDecimal transitionScore;

    /**
     * 0月度1季度
     */
    private String dimensionFlag;

    /**
     * 绩效指标名称
     */
    private String indicatorName;

    /**
     * 绩效指标代码
     */
    private String indicatorCode;

    /**
     * 关联的季度(针对职能)
     */
    private Long relateId;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonthQuarter() {
        return monthQuarter;
    }

    public void setMonthQuarter(Integer monthQuarter) {
        this.monthQuarter = monthQuarter;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptClassifyFlag() {
        return deptClassifyFlag;
    }

    public void setDeptClassifyFlag(String deptClassifyFlag) {
        this.deptClassifyFlag = deptClassifyFlag;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public BigDecimal getTransitionScore() {
        return transitionScore;
    }

    public void setTransitionScore(BigDecimal transitionScore) {
        this.transitionScore = transitionScore;
    }

    public String getDimensionFlag() {
        return dimensionFlag;
    }

    public void setDimensionFlag(String dimensionFlag) {
        this.dimensionFlag = dimensionFlag;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }


    public BdIndicatorDeptScoreDto(Integer year, Integer monthQuarter, String deptName, String deptCode, Long deptId,
                                String deptClassifyFlag, BigDecimal finalScore, BigDecimal transitionScore,
                                String dimensionFlag, String indicatorName, String indicatorCode, String remark) {
        this.year = year;
        this.monthQuarter = monthQuarter;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptId = deptId;
        this.deptClassifyFlag = deptClassifyFlag;
        this.finalScore = finalScore;
        this.transitionScore = transitionScore;
        this.dimensionFlag = dimensionFlag;
        this.indicatorName = indicatorName;
        this.indicatorCode = indicatorCode;
        this.remark = remark;
    }

    public BdIndicatorDeptScoreDto() {
    }
}