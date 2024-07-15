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
 * @since 2024-05-24 17:01:58
 */

@TableName("md_group")
    public class MdGroup extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


        /**
         * '0'正常 '1'删除
         */
    @TableField("status")
        private String status;

    @TableField("group_name")
        private String groupName;

    @TableField("group_code")
        private String groupCode;

    @TableField("short_name")
        private String shortName;

    @TableField("parent_id")
        private Long parentId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}