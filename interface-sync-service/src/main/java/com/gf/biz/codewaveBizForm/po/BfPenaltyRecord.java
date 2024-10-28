package com.gf.biz.codewaveBizForm.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;

/**
 * <p>
 * 扣分项记录表
 * </p>
 *
 * @author Gf
 * @since 2024-10-28 15:44:59
 */
@TableName("bf_penalty_record")
    public class BfPenaltyRecord extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;
        /**
         * 部门ID
         */
    @TableField("dept_id")
        private Long deptId;

        /**
         * 部门名称
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 创建人ID
         */
    @TableField("created_user_id")
        private String createdUserId;

        /**
         * 更新人ID
         */
    @TableField("updated_user_id")
        private String updatedUserId;

        /**
         * 扣分期间
         */
    @TableField("penalty_date")
        private String penaltyDate;

        /**
         * 扣分项数量
         */
    @TableField("penalty_item_count")
        private Integer penaltyItemCount;

        /**
         * 降级级数
         */
    @TableField("downgrade_level")
        private Integer downgradeLevel;

        /**
         * 总扣分
         */
    @TableField("total_deduction_score")
        private Double totalDeductionScore;

        /**
         * 生效状态
         */
    @TableField("enable_flag")
        private String enableFlag;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getPenaltyDate() {
        return penaltyDate;
    }

    public void setPenaltyDate(String penaltyDate) {
        this.penaltyDate = penaltyDate;
    }

    public Integer getPenaltyItemCount() {
        return penaltyItemCount;
    }

    public void setPenaltyItemCount(Integer penaltyItemCount) {
        this.penaltyItemCount = penaltyItemCount;
    }

    public Integer getDowngradeLevel() {
        return downgradeLevel;
    }

    public void setDowngradeLevel(Integer downgradeLevel) {
        this.downgradeLevel = downgradeLevel;
    }

    public Double getTotalDeductionScore() {
        return totalDeductionScore;
    }

    public void setTotalDeductionScore(Double totalDeductionScore) {
        this.totalDeductionScore = totalDeductionScore;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}