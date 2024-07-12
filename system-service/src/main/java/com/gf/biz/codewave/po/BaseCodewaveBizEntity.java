package com.gf.biz.codewave.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gf.biz.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Gf
 */
public abstract class BaseCodewaveBizEntity extends BaseEntity<Long,String> implements Serializable {
    @TableField(value = "created_by")
    protected String createdBy;
    @TableField(value = "updated_by")
    protected String updatedBy;
    @TableField("created_time")
    protected Date createdTime;
    @TableField("updated_time")
    protected Date updatedTime;


    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

}
