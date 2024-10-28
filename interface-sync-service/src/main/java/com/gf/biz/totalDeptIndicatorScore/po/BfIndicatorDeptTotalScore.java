package com.gf.biz.totalDeptIndicatorScore.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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

@TableName("bf_indicator_dept_total_score")
public class BfIndicatorDeptTotalScore extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 绩效年
     */
    @TableField("year")
    private Integer year;

    /**
     * 绩效月/季度
     */
    @TableField("month_quarter")
    private Integer monthQuarter;

    /**
     * 直属部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 直属部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 0运营部门1职能部门
     */
    @TableField("dept_classify_flag")
    private String deptClassifyFlag;

    /**
     * 最终得分
     */
    @TableField("final_score")
    private BigDecimal finalScore;

    /**
     * 过程得分
     */
    @TableField("transition_score")
    private BigDecimal transitionScore;

    /**
     * 0月度1季度
     */
    @TableField("dimension_flag")
    private String dimensionFlag;

    /**
     * 绩效指标名称
     */
    @TableField("indicator_name")
    private String indicatorName;

    /**
     * 绩效指标代码
     */
    @TableField("indicator_code")
    private String indicatorCode;

    /**
     * 关联的季度(针对职能)
     */
    @TableField("relate_id")
    private Long relateId;

    /**
     * 干预后得分
     */
    @TableField("intervene_score")
    private BigDecimal interveneScore;

    /**
     * 等级
     */
    @TableField("final_rank")
    private Integer finalRank;

    /**
     * 干预后等级
     */
    @TableField("intervene_rank")
    private BigDecimal interveneRank;

    /**
     * 扣分总数
     */
    @TableField("transition_deduct_score")
    private BigDecimal transitionDeductScore;

    /**
     * 降级总数
     */
    @TableField("transition_rank_number")
    private Integer transitionRankNumber;

    /**
     * 过程排名
     */
    @TableField("transition_rank")
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

    public BigDecimal getInterveneScore() {
        return interveneScore;
    }

    public void setInterveneScore(BigDecimal interveneScore) {
        this.interveneScore = interveneScore;
    }

    public Integer getFinalRank() {
        return finalRank;
    }

    public void setFinalRank(Integer finalRank) {
        this.finalRank = finalRank;
    }

    public BigDecimal getInterveneRank() {
        return interveneRank;
    }

    public void setInterveneRank(BigDecimal interveneRank) {
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
}