package com.gf.biz.codewaveBizForm.po;

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
 * @since 2024-10-28 17:15:31
 */
@TableName("bd_dept_rank_proportion")
public class BdDeptRankProportion extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 等级名称
     */
    @TableField("grade_name")
    private String gradeName;

    /**
     * 等级代码
     */
    @TableField("grade_code")
    private String gradeCode;

    /**
     * 更新者
     */
    @TableField("proportion_value")
    private BigDecimal proportionValue;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public BigDecimal getProportionValue() {
        return proportionValue;
    }

    public void setProportionValue(BigDecimal proportionValue) {
        this.proportionValue = proportionValue;
    }
}