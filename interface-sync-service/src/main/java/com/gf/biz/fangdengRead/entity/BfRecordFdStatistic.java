package com.gf.biz.fangdengRead.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 樊登人员读书统计表
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 17:57:03
 */
@TableName("bf_record_fd_statistic")
    public class BfRecordFdStatistic extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;



        /**
         * 完成年
         */
    @TableField("year")
        private Integer year;

        /**
         * 完成月
         */
    @TableField("month")
        private Integer month;

        /**
         * 读书数量
         */
    @TableField("read_cnt")
        private Integer readCnt;

        /**
         * 笔记分享数量
         */
    @TableField("note_share_cnt")
        private Integer noteShareCnt;

        /**
         * 员工姓名
         */
    @TableField("user_name")
        private String userName;

        /**
         * 员工编码
         */
    @TableField("user_account")
        private String userAccount;

        /**
         * 岗位名称
         */
    @TableField("position_name")
        private String positionName;

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
         * 部门Id
         */
    @TableField("dept_id")
        private Long deptId;

        /**
         * 用户id
         */
    @TableField("user_id")
        private Long userId;

        /**
         * 关联底表主键
         */
    @TableField("if_id")
        private Long ifId;

        /**
         * 业务日期
         */
    @TableField("business_date")
        private Date businessDate;

        /**
         * 未加权得分
         */
    @TableField("final_score")
        private BigDecimal finalScore;

    @TableField("status")
        private String status;

        /**
         * 0运营部门1职能部门
         */
    @TableField("dept_classify_flag")
        private String deptClassifyFlag;

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

    public Integer getReadCnt() {
        return readCnt;
    }

    public void setReadCnt(Integer readCnt) {
        this.readCnt = readCnt;
    }

    public Integer getNoteShareCnt() {
        return noteShareCnt;
    }

    public void setNoteShareCnt(Integer noteShareCnt) {
        this.noteShareCnt = noteShareCnt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIfId() {
        return ifId;
    }

    public void setIfId(Long ifId) {
        this.ifId = ifId;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeptClassifyFlag() {
        return deptClassifyFlag;
    }

    public void setDeptClassifyFlag(String deptClassifyFlag) {
        this.deptClassifyFlag = deptClassifyFlag;
    }

    public BfRecordFdStatistic(Integer year, Integer month, String deptName, String deptCode, Long deptId, BigDecimal finalScore, String deptClassifyFlag) {
        this.year = year;
        this.month = month;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptId = deptId;
        this.finalScore = finalScore;
        this.deptClassifyFlag = deptClassifyFlag;
    }


    public BfRecordFdStatistic() {
    }
}