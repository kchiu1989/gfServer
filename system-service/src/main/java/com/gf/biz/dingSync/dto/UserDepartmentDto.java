package com.gf.biz.dingSync.dto;



import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gf
 * @since 2024-05-24 17:01:30
 */


public class UserDepartmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected String createdBy;

    protected String updatedBy;

    protected String deletedFlag;

    protected Date createdTime;

    protected Date updatedTime;


    private String status;


    private Long userId;


    private Long departmentId;


    private Long unitId;


    private Long groupId;

    private String ifDepartmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getIfDepartmentId() {
        return ifDepartmentId;
    }

    public void setIfDepartmentId(String ifDepartmentId) {
        this.ifDepartmentId = ifDepartmentId;
    }
}