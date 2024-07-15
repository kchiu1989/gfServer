package com.gf.biz.dingSync.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;

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

    @TableField("dept_name")
    private String deptName;

    @TableField("dept_code")
    private String deptCode;

    @TableField("unit_id")
    private Long unitId;

    @TableField("group_id")
    private Long groupId;

    @TableField("parent_id")
    private Long parentId;

    @TableField("if_id")
    private String ifId;

    @TableField("dept_identity")
    private String deptIdentity;

    @TableField("dept_classify")
    private String deptClassify;

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

    public String getDeptIdentity() {
        return deptIdentity;
    }

    public void setDeptIdentity(String deptIdentity) {
        this.deptIdentity = deptIdentity;
    }

    public String getDeptClassify() {
        return deptClassify;
    }

    public void setDeptClassify(String deptClassify) {
        this.deptClassify = deptClassify;
    }
}