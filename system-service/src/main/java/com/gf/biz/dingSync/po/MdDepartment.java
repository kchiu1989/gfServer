package com.gf.biz.dingSync.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.gf.biz.common.entity.BaseBizEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:02:35
 */

@TableName("md_department")
public class MdDepartment extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * '0'正常 '1'删除
     */
    @TableField("status")
    private String status;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("unit_id")
    private Long unitId;

    @TableField("group_id")
    private Long groupId;

    @TableField("parent_id")
    private Long parentId;

    @TableField("if_id")
    private String ifId;

    @TableField("manage_user_list")
    private String manageUserList;

    public String getManageUserList() {
        return manageUserList;
    }

    public void setManageUserList(String manageUserList) {
        this.manageUserList = manageUserList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIfId() {
        return ifId;
    }

    public void setIfId(String ifId) {
        this.ifId = ifId;
    }
}