package com.gf.biz.codewave.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:24:20
 */
@TableName("lcap_department_4a79f3_delete")
public class LcapDepartmentDelete extends BaseCodewaveBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("if_id")
    private Long ifId;

    @TableField("if_parent_id")
    private Long ifParentId;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 部门标识
     */
    @TableField("dept_id")
    private String deptId;

    @TableField("dept_classify")
    private String deptClassify;

    @TableField("dept_code")
    private String deptCode;

    public Long getIfId() {
        return ifId;
    }

    public void setIfId(Long ifId) {
        this.ifId = ifId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public Long getIfParentId() {
        return ifParentId;
    }

    public void setIfParentId(Long ifParentId) {
        this.ifParentId = ifParentId;
    }

    /**
     * 父部门标识
     */
    @TableField("parent_dept_id")
    private String parentDeptId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptClassify() {
        return deptClassify;
    }

    public void setDeptClassify(String deptClassify) {
        this.deptClassify = deptClassify;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}