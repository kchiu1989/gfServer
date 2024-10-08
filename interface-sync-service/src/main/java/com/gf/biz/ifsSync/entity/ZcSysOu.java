package com.gf.biz.ifsSync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("zc_sys_ou")
public class ZcSysOu implements Serializable {


    private static final long serialVersionUID = -5391885623703926920L;
    @TableId(type= IdType.AUTO)
    private Long id;
    @TableField(value="ou_code")
    private String ouCode;
    @TableField(value="ou_name")
    private String ouName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }
}
