package com.gf.biz.codewave.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 用户与部门关联实体。操作该表可完成为部门添加用户、移除部门用户等。默认生成的字段不允许改动，可新增自定义字段。
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:41:38
 */
@TableName("lcap_user_dept_mapping_4a79f3_delete")
public class LcapUserDeptMappingDelete extends BaseCodewaveBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 部门标识
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 是否是部门主管
     */
    @TableField("is_dept_leader")
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
}