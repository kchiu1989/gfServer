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
 * @since 2024-05-24 17:00:48
 */

@TableName("user_info_ext")
    public class UserInfoExt extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;



        /**
         * '0'正常 '1'删除
         */
    @TableField("status")
        private String status;

    @TableField("user_id")
        private Long userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}