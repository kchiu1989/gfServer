package com.gf.biz.codewave.dto;

import com.gf.biz.codewave.po.BaseCodewaveBizEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户与部门关联实体。操作该表可完成为部门添加用户、移除部门用户等。默认生成的字段不允许改动，可新增自定义字段。
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:41:38
 */

public class LcapUserDeptMappingDto extends BaseCodewaveBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;


    private String createdBy;

    private String updatedBy;

    private Date createdTime;

    private Date updatedTime;

    /**
     * 用户ID
     */

    private Long userId;

    private String userAccount;

    private String userName;

    private Long ifUserId;

    /**
     * 部门标识
     */

    private Long deptId;

    private String deptCode;

    private String deptName;

    private String deptClassify;

    /**
     * 是否是部门主管
     */

    private Long isDeptLeader;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }



    public Long getIsDeptLeader() {
        return isDeptLeader;
    }

    public void setIsDeptLeader(Long isDeptLeader) {
        this.isDeptLeader = isDeptLeader;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getIfUserId() {
        return ifUserId;
    }

    public void setIfUserId(Long ifUserId) {
        this.ifUserId = ifUserId;
    }

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

    public String getDeptClassify() {
        return deptClassify;
    }

    public void setDeptClassify(String deptClassify) {
        this.deptClassify = deptClassify;
    }
}