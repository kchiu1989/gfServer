package com.gf.biz.fangdengRead.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;

/**
 * <p>
 * 樊登人员读书统计表
 * </p>
 *
 * @author Gf
 * @since 2024-10-15 09:34:18
 */
@TableName("if_record_fd_statistic")
public class IfRecordFdStatistic extends BaseBizEntity implements Serializable {

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
     * dingUserId
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

    @TableField("dept_classify")
    private String deptClassify;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 主数据用户id
     */
    @TableField("if_user_id")
    private Long ifUserId;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getIfUserId() {
        return ifUserId;
    }

    public void setIfUserId(Long ifUserId) {
        this.ifUserId = ifUserId;
    }

    public String getDeptClassify() {
        return deptClassify;
    }

    public void setDeptClassify(String deptClassify) {
        this.deptClassify = deptClassify;
    }
}