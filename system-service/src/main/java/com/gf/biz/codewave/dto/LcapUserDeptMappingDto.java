package com.gf.biz.codewave.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private String userId;

    /**
     * 部门标识
     */

    private String deptId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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
}