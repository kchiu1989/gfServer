package com.gf.biz.fangdengRead.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 樊登笔记管理表
 * </p>
 *
 * @author Gf
 * @since 2024-10-14 12:00:49
 */
@TableName("if_record_fd_nm")
    public class IfRecordFdNm extends BaseBizEntity implements Serializable {

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
         * 笔记编号
         */
    @TableField("note_number")
        private String noteNumber;

        /**
         * 笔记内容
         */
    @TableField("note_content")
        private String noteContent;

        /**
         * 内容类型（1.笔记墙2.樊登讲书）
         */
    @TableField("content_type")
        private String contentType;

        /**
         * 关联内容(书名)
         */
    @TableField("book_name")
        private String bookName;

        /**
         * 发布者
         */
    @TableField("publisher")
        private String publisher;

        /**
         * 所在部门
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 发布时间
         */
    @TableField("publish_time")
        private Date publishTime;

        /**
         * 发布状态
         */
    @TableField("publish_status")
        private String publishStatus;

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

    public String getNoteNumber() {
        return noteNumber;
    }

    public void setNoteNumber(String noteNumber) {
        this.noteNumber = noteNumber;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}