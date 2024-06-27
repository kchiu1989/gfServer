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
 * @since 2024-05-24 17:02:17
 */

@TableName("md_unit")
    public class MdUnit extends BaseBizEntity implements Serializable {

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

    @TableField("short_name")
        private String shortName;

    @TableField("group_id")
        private String groupId;

    @TableField("parent_id")
        private Long parentId;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}