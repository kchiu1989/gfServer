package com.gf.biz.totalDeptIndicatorScore.dto;

import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-28 11:41:04
 */

public class BfIndicatorDeptTotalScoreDto extends BaseBizEntity implements Serializable,Comparable<BfIndicatorDeptTotalScoreDto> {

    private static final long serialVersionUID = 1L;


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
     * 最终得分
     */
    private BigDecimal finalScore;

    /**
     * 过程得分
     */
    private BigDecimal transitionScore;

    /**
     * 0月度1季度
     */
    private String dimensionFlag;

    /**
     * 关联的季度(针对职能)
     */
    private Long relateId;

    /**
     * 干预后得分
     */
    private BigDecimal interveneScore;

    /**
     * 等级
     */
    private String finalRank;

    /**
     * 干预后等级
     */
    private String interveneRank;

    /**
     * 扣分总数
     */
    private BigDecimal transitionDeductScore;

    /**
     * 降级总数
     */
    private Integer transitionRankNumber;


    private Integer transitionFoodRankNumber;

    private String remark;

    /**
     * 过程排名
     */
    private Integer transitionRank;

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

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public BigDecimal getInterveneScore() {
        return interveneScore;
    }

    public void setInterveneScore(BigDecimal interveneScore) {
        this.interveneScore = interveneScore;
    }

    public String getFinalRank() {
        return finalRank;
    }

    public void setFinalRank(String finalRank) {
        this.finalRank = finalRank;
    }

    public String getInterveneRank() {
        return interveneRank;
    }

    public void setInterveneRank(String interveneRank) {
        this.interveneRank = interveneRank;
    }

    public BigDecimal getTransitionDeductScore() {
        return transitionDeductScore;
    }

    public void setTransitionDeductScore(BigDecimal transitionDeductScore) {
        this.transitionDeductScore = transitionDeductScore;
    }

    public Integer getTransitionRankNumber() {
        return transitionRankNumber;
    }

    public void setTransitionRankNumber(Integer transitionRankNumber) {
        this.transitionRankNumber = transitionRankNumber;
    }

    public Integer getTransitionRank() {
        return transitionRank;
    }

    public void setTransitionRank(Integer transitionRank) {
        this.transitionRank = transitionRank;
    }

    public Integer getTransitionFoodRankNumber() {
        return transitionFoodRankNumber;
    }

    public void setTransitionFoodRankNumber(Integer transitionFoodRankNumber) {
        this.transitionFoodRankNumber = transitionFoodRankNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BfIndicatorDeptTotalScoreDto(Integer year, Integer monthQuarter, String deptName, String deptCode, Long deptId,
                                        String deptClassifyFlag, BigDecimal transitionScore, String dimensionFlag,
                                        BigDecimal transitionDeductScore, Integer transitionRankNumber,
                                        Integer transitionFoodRankNumber, String remark) {
        this.year = year;
        this.monthQuarter = monthQuarter;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptId = deptId;
        this.deptClassifyFlag = deptClassifyFlag;
        this.transitionScore = transitionScore;
        this.dimensionFlag = dimensionFlag;
        this.transitionDeductScore = transitionDeductScore;
        this.finalScore = transitionScore.subtract(transitionDeductScore);
        this.transitionRankNumber = transitionRankNumber;
        this.transitionFoodRankNumber = transitionFoodRankNumber;
        this.remark = remark;
    }

    @Override
    public int compareTo(BfIndicatorDeptTotalScoreDto o) {
        return this.finalScore.compareTo(o.getFinalScore());
    }
}