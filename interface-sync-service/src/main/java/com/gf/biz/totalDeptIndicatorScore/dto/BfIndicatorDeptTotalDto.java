package com.gf.biz.totalDeptIndicatorScore.dto;

import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-10-29 16:29:41
 */
public class BfIndicatorDeptTotalDto extends BaseBizEntity implements Serializable {

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
     * 0运营部门1职能部门
     */
    private String deptClassifyFlag;

    private Long masterId;

    /**
     * 0月度1季度
     */
    private String dimensionFlag;

    private List<BfIndicatorDeptTotalScoreDto> itemList;

    public List<BfIndicatorDeptTotalScoreDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<BfIndicatorDeptTotalScoreDto> itemList) {
        this.itemList = itemList;
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

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }
}