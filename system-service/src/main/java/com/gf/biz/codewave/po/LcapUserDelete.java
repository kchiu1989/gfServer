package com.gf.biz.codewave.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 制品应用的用户实体。
 * 1 实体名称不允许改动
 * 2 默认生成的字段不允许改动
 * 3 可新增自定义字段（避免设置为非空且无默认值）
 * </p>
 *
 * @author Gf
 * @since 2024-07-09 15:45:56
 */

@TableName("lcap_user_4a79f3_delete")
public class LcapUserDelete extends BaseCodewaveBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("if_id")
    private Long ifId;

    @TableField("if_direct_leader_id")
    private Long ifDirectLeaderId;


    /**
     * 第三方登录方式唯一id；普通登录使用userName+source作为userId
     */
    @TableField("user_id")
    private String userId;

    /**
     * 普通登录用户名，类似账号的概念
     */
    @TableField("user_name")
    private String userName;

    /**
     * 普通登录密码，密码建议加密存储。第三方登录不会存储密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 展示的名称
     */
    @TableField("display_name")
    private String displayName;

    /**
     * 状态，标识当前用户的状态是什么
     */
    @TableField("status")
    private String status;

    /**
     * 当前条用户数据来自哪个用户源，如普通登录、微信登录
     */
    @TableField("source")
    private String source;

    /**
     * 上级领导
     */
    @TableField("direct_leader_id")
    private String directLeaderId;

    public void setIfId(Long ifId) {
        this.ifId = ifId;
    }

    public Long getIfId() {
        return ifId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public Long getIfDirectLeaderId() {
        return ifDirectLeaderId;
    }

    public void setIfDirectLeaderId(Long ifDirectLeaderId) {
        this.ifDirectLeaderId = ifDirectLeaderId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}