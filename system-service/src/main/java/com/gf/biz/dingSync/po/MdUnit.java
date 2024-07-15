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

    @TableField("unit_name")
        private String unitName;

    @TableField("unit_code")
        private String unitCode;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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