package com.gf.biz.codewave.po;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("lcap_department_4a79f3")
public class LcapDepartment extends BaseCodewaveBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("if_id")
    private Long ifId;

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

    /**
     * 父部门标识
     */
    @TableField("parent_dept_id")
    private String parentDeptId;




}