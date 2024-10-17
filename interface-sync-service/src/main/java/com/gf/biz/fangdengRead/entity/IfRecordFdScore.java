package com.gf.biz.fangdengRead.entity;

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
 * @since 2024-10-16 16:36:48
 */

@TableName("if_record_fd_score")
public class IfRecordFdScore extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


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

    @TableField("remark")
    private String remark;
    /**
     * 未加权得分
     */
    @TableField("final_score")
    private BigDecimal finalScore;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IfRecordFdScore(Integer year, Integer month, String deptName, String deptCode, Long deptId, BigDecimal finalScore, String deptClassifyFlag,String remark) {
        this.year = year;
        this.month = month;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptId = deptId;
        this.finalScore = finalScore;
        this.deptClassifyFlag = deptClassifyFlag;
        this.remark = remark;
    }

    public IfRecordFdScore() {
    }
}