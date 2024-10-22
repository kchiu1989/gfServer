package com.gf.biz.codewaveBizForm.po;

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
 * @since 2024-10-22 16:56:26
 */
@TableName("bf_food_safety_inspect")
public class BfFoodSafetyInspect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
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
     * 年度
     */
    @TableField("year")
    private Long year;

    /**
     * 季度
     */
    @TableField("quarter")
    private Long quarter;

    /**
     * 部门ID
     */
    @TableField("depart_id")
    private Long departId;

    /**
     * 第一次成绩
     */
    @TableField("first_score")
    private BigDecimal firstScore;

    /**
     * 加权成绩
     */
    @TableField("weighted_score")
    private BigDecimal weightedScore;

    /**
     * 扣分
     */
    @TableField("minus_score")
    private BigDecimal minusScore;

    /**
     * 降级数
     */
    @TableField("down_grade")
    private Long downGrade;

    /**
     * 最终成绩
     */
    @TableField("final_score")
    private BigDecimal finalScore;

    /**
     * 季度首日
     */
    @TableField("quarter_y_m_d")
    private Date quarterYMD;

    /**
     * 第一次计算成绩
     */
    @TableField("first_compute_score")
    private BigDecimal firstComputeScore;

    /**
     * 第一次红线问题
     */
    @TableField("first_redline_issue")
    private String firstRedlineIssue;

    /**
     * 第二次成绩
     */
    @TableField("scd_score")
    private BigDecimal scdScore;

    /**
     * 第二次红线问题
     */
    @TableField("scd_redline_issue")
    private String scdRedlineIssue;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 第一次红线类型
     */
    @TableField("first_redline_type")
    private String firstRedlineType;

    /**
     * 第二次红线类型
     */
    @TableField("scd_redline_type")
    private String scdRedlineType;

    /**
     * 流程ID
     */
    @TableField("task_id")
    private String taskId;

    /**
     * 门店经理ID
     */
    @TableField("restaurant_manager")
    private String restaurantManager;

    /**
     * 删除标识：0未删除,1已删除
     */
    @TableField("deleted_flag")
    private String deletedFlag;

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

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getQuarter() {
        return quarter;
    }

    public void setQuarter(Long quarter) {
        this.quarter = quarter;
    }

    public Long getDepartId() {
        return departId;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public BigDecimal getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(BigDecimal firstScore) {
        this.firstScore = firstScore;
    }

    public BigDecimal getWeightedScore() {
        return weightedScore;
    }

    public void setWeightedScore(BigDecimal weightedScore) {
        this.weightedScore = weightedScore;
    }

    public BigDecimal getMinusScore() {
        return minusScore;
    }

    public void setMinusScore(BigDecimal minusScore) {
        this.minusScore = minusScore;
    }

    public Long getDownGrade() {
        return downGrade;
    }

    public void setDownGrade(Long downGrade) {
        this.downGrade = downGrade;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public Date getQuarterYMD() {
        return quarterYMD;
    }

    public void setQuarterYMD(Date quarterYMD) {
        this.quarterYMD = quarterYMD;
    }

    public BigDecimal getFirstComputeScore() {
        return firstComputeScore;
    }

    public void setFirstComputeScore(BigDecimal firstComputeScore) {
        this.firstComputeScore = firstComputeScore;
    }

    public String getFirstRedlineIssue() {
        return firstRedlineIssue;
    }

    public void setFirstRedlineIssue(String firstRedlineIssue) {
        this.firstRedlineIssue = firstRedlineIssue;
    }

    public BigDecimal getScdScore() {
        return scdScore;
    }

    public void setScdScore(BigDecimal scdScore) {
        this.scdScore = scdScore;
    }

    public String getScdRedlineIssue() {
        return scdRedlineIssue;
    }

    public void setScdRedlineIssue(String scdRedlineIssue) {
        this.scdRedlineIssue = scdRedlineIssue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstRedlineType() {
        return firstRedlineType;
    }

    public void setFirstRedlineType(String firstRedlineType) {
        this.firstRedlineType = firstRedlineType;
    }

    public String getScdRedlineType() {
        return scdRedlineType;
    }

    public void setScdRedlineType(String scdRedlineType) {
        this.scdRedlineType = scdRedlineType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRestaurantManager() {
        return restaurantManager;
    }

    public void setRestaurantManager(String restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
}