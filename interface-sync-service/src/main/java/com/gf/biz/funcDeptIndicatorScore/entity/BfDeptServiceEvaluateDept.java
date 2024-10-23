package com.gf.biz.funcDeptIndicatorScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-10-21 10:16:11
 */

@TableName("bf_dept_service_evaluate_dept")
    public class BfDeptServiceEvaluateDept implements Serializable {

    private static final long serialVersionUID = 1L;

                @TableId(value = "id", type = IdType.AUTO)
                private Long id;

        /**
         * 创建时间
         */
    @TableField("created_time")
        private Date createdTime;

        /**
         * 更新时间
         */
    @TableField("updated_time")
        private Date updatedTime;

        /**
         * 创建者
         */
    @TableField("created_by")
        private String createdBy;

        /**
         * 更新者
         */
    @TableField("updated_by")
        private String updatedBy;

        /**
         * 部门名称
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 部门标识
         */
    @TableField("dept_id")
        private Long deptId;

        /**
         * 1门店2高管
         */
    @TableField("evaluate_port")
        private String evaluatePort;

        /**
         * 1启用 0 停用
         */
    @TableField("status")
        private String status;

        /**
         * 1正常 0 删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;
    @TableField("dept_code")
    private String deptCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getEvaluatePort() {
        return evaluatePort;
    }

    public void setEvaluatePort(String evaluatePort) {
        this.evaluatePort = evaluatePort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}