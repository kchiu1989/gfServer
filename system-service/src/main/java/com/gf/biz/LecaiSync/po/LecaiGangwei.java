package com.gf.biz.LecaiSync.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;


@TableName("if_lecai_gangwei_info")

public class LecaiGangwei extends BaseBizEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 本地乐才岗位表id
     */

    /**
     * 岗位id
     */
    @TableField("post_id")
    private String postId;

    /**
     * 上级岗位id
     */
    @TableField("parent_post_id")
    private String parentPostId;

    /**
     * 外部岗位编码
     */
    @TableField("out_gangwei_code")
    private Long outGangweiCode;

    /**
     * 组织name=角色组name
     */
    @TableField("organ_id")
    private String organId;

    /**
     * 岗位名称=角色名称
     */
    @TableField("post_name")
    private String postName;

    /**
     * 职位名称
     */
    @TableField("position_name")
    private String positionName;

    /**
     * 职级上限
     */
    @TableField("rank_top")
    private String rankTop;

    /**
     * 职级下限
     */
    @TableField("rank_lower")
    private String rankLower;

    /**
     * 部门人数
     */
    @TableField("member")
    private Integer member;



    /**
     * '0'代表推送完毕，'1'代表待新增推送，'2'代表待变更推送
     */
    @TableField("await_flag")
    private String awaitFlag;

    /**
     * '1'启用'0'停止
     */
    @TableField("status")
    private String status;

    /**
     * 钉钉角色roleId
     */
    @TableField("ding_role_id")
    private String dingRoleId;




    public String getPostId() {
        return postId;
    }

    public String getParentPostId() {
        return parentPostId;
    }

    public Long getOutGangweiCode() {
        return outGangweiCode;
    }

    public String getOrganId() {
        return organId;
    }

    public String getPostName() {
        return postName;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getRankTop() {
        return rankTop;
    }

    public String getRankLower() {
        return rankLower;
    }

    public Integer getMember() {
        return member;
    }

    public String getAwaitFlag() {
        return awaitFlag;
    }

    public String getStatus() {
        return status;
    }

    public String getDingRoleId() {
        return dingRoleId;
    }


    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setParentPostId(String parentPostId) {
        this.parentPostId = parentPostId;
    }

    public void setOutGangweiCode(Long outGangweiCode) {
        this.outGangweiCode = outGangweiCode;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setRankTop(String rankTop) {
        this.rankTop = rankTop;
    }

    public void setRankLower(String rankLower) {
        this.rankLower = rankLower;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public void setAwaitFlag(String awaitFlag) {
        this.awaitFlag = awaitFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDingRoleId(String dingRoleId) {
        this.dingRoleId = dingRoleId;
    }
}
