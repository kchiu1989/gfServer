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
 * @since 2024-05-24 17:01:30
 */

@TableName("user_department")
public class UserDepartment extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * '0'正常 '1'删除
     */
    @TableField("status")
    private String status;

    @TableField("user_id")
    private Long userId;

    @TableField("department_id")
    private Long departmentId;

    @TableField("unit_id")
    private Long unitId;

    @TableField("group_id")
    private Long groupId;

    @TableField("dept_leader_flag")
    private String deptLeaderFlag;


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

    public String getDeptLeaderFlag() {
        return deptLeaderFlag;
    }

    public void setDeptLeaderFlag(String deptLeaderFlag) {
        this.deptLeaderFlag = deptLeaderFlag;
    }
}