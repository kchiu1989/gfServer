package com.gf.biz.fangdengRead.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 樊登完读记录表
 * </p>
 *
 * @author Gf
 * @since 2024-10-14 11:35:54
 */
@TableName("if_record_fd_cr")
public class IfRecordFdCr extends BaseBizEntity implements Serializable {


    private static final long serialVersionUID = 181516090944655172L;

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
     * 员工姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 一级部门
     */
    @TableField("first_level_dept_name")
    private String firstLevelDeptName;

    /**
     * 二级部门
     */
    @TableField("second_level_dept_name")
    private String secondLevelDeptName;

    /**
     * 三级部门
     */
    @TableField("third_level_dept_name")
    private String thirdLevelDeptName;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 学习内容（书名）
     */
    @TableField("book_name")
    private String bookName;

    /**
     * 学习完成时间
     */
    @TableField("finish_time")
    private Date finishTime;

    /**
     * 员工编码
     */
    @TableField("user_account")
    private String userAccount;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstLevelDeptName() {
        return firstLevelDeptName;
    }

    public void setFirstLevelDeptName(String firstLevelDeptName) {
        this.firstLevelDeptName = firstLevelDeptName;
    }

    public String getSecondLevelDeptName() {
        return secondLevelDeptName;
    }

    public void setSecondLevelDeptName(String secondLevelDeptName) {
        this.secondLevelDeptName = secondLevelDeptName;
    }

    public String getThirdLevelDeptName() {
        return thirdLevelDeptName;
    }

    public void setThirdLevelDeptName(String thirdLevelDeptName) {
        this.thirdLevelDeptName = thirdLevelDeptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}