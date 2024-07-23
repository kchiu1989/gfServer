package com.gf.biz.LecaiSync.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

@TableName("if_lecai_roleGroup_info")
public class LecaiRolegroupInfo extends BaseBizEntity {
    @TableField("name")
    private String name;

    @TableField("ding_group_id")
    private String dingGroupId;

    public String getName() {
        return name;
    }

    public String getDingGroupId() {
        return dingGroupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDingGroupId(String dingGroupId) {
        this.dingGroupId = dingGroupId;
    }
}
