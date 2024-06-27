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
 * @since 2024-05-24 17:00:11
 */

@TableName("user_info")
    public class UserInfo extends BaseBizEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("position")
    private String position;
        /**
         * '1'正常 '0'         */
    @TableField("status")
        private String status;

    @TableField("user_name")
        private String userName;

    @TableField("user_account")
        private String userAccount;

    @TableField("age")
        private Integer age;

    @TableField("gender")
        private Integer gender;

    @TableField("birthday")
        private Date birthday;

    @TableField("nation")
        private String nation;

    @TableField("birth_place")
        private String birthPlace;

    @TableField("id_card")
        private String idCard;

    @TableField("marriage")
        private Integer marriage;

    @TableField("work_start_date")
        private Date workStartDate;

    @TableField("working_time")
        private Integer workingTime;

    @TableField("email")
        private String email;

    @TableField("telephone")
        private String telephone;

    @TableField("if_id")
        private String ifId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Integer getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Integer workingTime) {
        this.workingTime = workingTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIfId() {
        return ifId;
    }

    public void setIfId(String ifId) {
        this.ifId = ifId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}