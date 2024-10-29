package com.gf.biz.totalDeptIndicatorScore.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-29 16:29:41
 */
@TableName("bf_indicator_dept_total")
public class BfIndicatorDeptTotal extends BaseBizEntity implements Serializable {

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
     * 0运营部门1职能部门
     */
    @TableField("dept_classify_flag")
    private String deptClassifyFlag;

    @TableField("dimension_flag")
    private String dimensionFlag;

    @TableField("business_date")
    private Date businessDate;

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

    public String getDeptClassifyFlag() {
        return deptClassifyFlag;
    }

    public void setDeptClassifyFlag(String deptClassifyFlag) {
        this.deptClassifyFlag = deptClassifyFlag;
    }

    public String getDimensionFlag() {
        return dimensionFlag;
    }

    public void setDimensionFlag(String dimensionFlag) {
        this.dimensionFlag = dimensionFlag;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }
}