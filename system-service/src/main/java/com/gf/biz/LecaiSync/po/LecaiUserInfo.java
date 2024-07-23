package com.gf.biz.LecaiSync.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gf.biz.common.entity.BaseBizEntity;

import java.io.Serializable;
import java.util.Date;

@TableName("if_lecai_user_info")
public class LecaiUserInfo extends BaseBizEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 本地乐才人员表id
     */

    /**
     * 入职id
     */
    @TableField("resume_id")
    private String resumeId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;


//    /**
//     * '1'启用'0'停用
//     */
//    @TableField("status")
//    private String status;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 考勤状态：'0'不考勤'1'考勤不打卡'2'考勤打卡
     */
    @TableField("attend_status")
    private String attendStatus;

    /**
     * 银行卡
     */
    @TableField("bank_cardnum")
    private String bankCardnum;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 证件号码
     */
    @TableField("card_num")
    private String cardNum;

    /**
     * 证件类型'0'身份证'1'台胞证'2'海外证件
     */
    @TableField("card_type")
    private String cardType;

    /**
     * 部门名
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 工号
     */
    @TableField("emp_no")
    private String empNo;

    /**
     * 入职时间
     */
    @TableField("entry_date")
    private String entryDate;

    /**
     * 进店时间
     */
    @TableField("entry_shop_date")
    private String entryShopDate;

    /**
     * 参加工作时间
     */
    @TableField("first_work_date")
    private String firstWorkDate;

    /**
     * 岗位Id
     */
    @TableField("gangwei_id")
    private String gangweiId;
    /**
     * 未变更前的岗位名称
     */
    @TableField("past_gangwei_name")
    private String pastGangweiName;

    /**
     * 岗位名称
     */
    @TableField("gangwei_name")
    private String gangweiName;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 户口类型
     */
    @TableField("residence_type")
    private String residenceType;

    /**
     * 户籍省id
     */
    @TableField("residence_province_id")
    private Integer residenceProvinceId;

    /**
     * 户籍市id
     */
    @TableField("residence_city_id")
    private Integer residenceCityId;

    /**
     * 户籍区id
     */
    @TableField("residence_district_id")
    private Integer residenceDistrictId;

    /**
     * 户籍地址
     */
    @TableField("residence_addr")
    private String residenceAddr;

    /**
     * 试用期'0'正在试用'1'试用结束
     */
    @TableField("trial_limit")
    private Integer trialLimit;

    /**
     * 姓名
     */
    @TableField("username")
    private String username;

    /**
     * 工作类型：'0'全职工'1'小时工
     */
    @TableField("work_type")
    private String workType;

    /**
     * 工作状态：'0'合同工'1'小时工'2'外包工'3'派遣工
     */
    @TableField("work_status")
    private String workStatus;

    /**
     * 职级id
     */
    @TableField("zhiji_id")
    private String zhijiId;

    /**
     * 职级名称
     */
    @TableField("zhiji_name")
    private String zhijiName;

    /**
     * 职位id
     */
    @TableField("zhiwei_id")
    private String zhiweiId;

    /**
     * 职位名称
     */
    @TableField("zhiwei_name")
    private String zhiweiName;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private String updateDate;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 员工状态：10：实习20：试用30：正式40：离职50：退休60：返聘
     */
    @TableField("user_status")
    private String userStatus;

    /**
     * 部门负责人id
     */
    @TableField("charge_user_id")
    private String chargeUserId;

    /**
     * 部门负责人姓名
     */
    @TableField("charge_user_name")
    private String chargeUserName;

    /**
     * 法人公司code
     */
    @TableField("corporation_code")
    private String corporationCode;

    /**
     * 合同主体变更日期
     */
    @TableField("contract_change_date")
    private String contractChangeDate;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 岗位编码
     */
    @TableField("role_code")
    private String roleCode;
    /**
     * 外部岗位编码
     */
    @TableField("out_role_code")
    private String outRoleCode;

    /**
     * 健康证过期时间
     */
    @TableField("health_over_time")
    private String healthOverTime;

    /**
     * 是否开通OA账号
     */
    @TableField("oa_open_flag")
    private String oaOpenFlag;

    /**
     * 开户银行
     */
    @TableField("bank_name_desc")
    private String bankNameDesc;

    /**
     * 银行名
     */
    @TableField("bank_head")
    private String bankHead;

    /**
     * 银行支行
     */
    @TableField("bank_branch")
    private String bankBranch;

    /**
     * 联行号
     */
    @TableField("bank_line_no")
    private String bankLineNo;

    /**
     * 发薪单位
     */
    @TableField("pay_unit")
    private String payUnit;

    /**
     * 社保缴纳账户
     */
    @TableField("social_account")
    private String socialAccount;

    /**
     * 所属公司
     */
    @TableField("belong_company")
    private String belongCompany;

    /**
     * 职务级别
     */
    @TableField("job_level")
    private String jobLevel;

    /**
     * 自定义开户行名称
     */
    @TableField("custom_bank_name")
    private String customBankName;

    /**
     * 现居地址
     */
    @TableField("address")
    private String address;

    /**
     * 入职途径
     */
    @TableField("entry_way")
    private String entryWay;

    /**
     * 入职状态：0:入职完成1:入职中2:入职失败3:入职驳回
     */
    @TableField("entry_status")
    private String entryStatus;

    /**
     * 学历
     */
    @TableField("edu_level")
    private String eduLevel;

    /**
     * 职位分类全路径
     */
    @TableField("zhiwei_class_name")
    private String zhiweiClassName;

    /**
     * 入职次数
     */
    @TableField("entry_times")
    private String entryTimes;

    /**
     * 系统外入职次数
     */
    @TableField("other_entry_times")
    private String otherEntryTimes;


    /**
     * '0'代表推送完毕，'1'代表待新增推送，'2'代表待变更推送
     */
    @TableField("sync_flag")
    private String syncFlag;

    public String getPastGangweiName() {
        return pastGangweiName;
    }

    public void setPastGangweiName(String pastGangweiName) {
        this.pastGangweiName = pastGangweiName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAttendStatus(String attendStatus) {
        this.attendStatus = attendStatus;
    }

    public void setBankCardnum(String bankCardnum) {
        this.bankCardnum = bankCardnum;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBankHead(String bankHead) {
        this.bankHead = bankHead;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public void setBankLineNo(String bankLineNo) {
        this.bankLineNo = bankLineNo;
    }

    public void setBelongCompany(String belongCompany) {
        this.belongCompany = belongCompany;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setEntryShopDate(String entryShopDate) {
        this.entryShopDate = entryShopDate;
    }

    public void setFirstWorkDate(String firstWorkDate) {
        this.firstWorkDate = firstWorkDate;
    }

    public void setGangweiId(String gangweiId) {
        this.gangweiId = gangweiId;
    }

    public void setGangweiName(String gangweiName) {
        this.gangweiName = gangweiName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public void setResidenceProvinceId(Integer residenceProvinceId) {
        this.residenceProvinceId = residenceProvinceId;
    }

    public void setResidenceCityId(Integer residenceCityId) {
        this.residenceCityId = residenceCityId;
    }

    public void setResidenceDistrictId(Integer residenceDistrictId) {
        this.residenceDistrictId = residenceDistrictId;
    }

    public void setResidenceAddr(String residenceAddr) {
        this.residenceAddr = residenceAddr;
    }

    public void setTrialLimit(Integer trialLimit) {
        this.trialLimit = trialLimit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public void setZhijiId(String zhijiId) {
        this.zhijiId = zhijiId;
    }

    public void setZhijiName(String zhijiName) {
        this.zhijiName = zhijiName;
    }

    public void setZhiweiId(String zhiweiId) {
        this.zhiweiId = zhiweiId;
    }

    public void setZhiweiName(String zhiweiName) {
        this.zhiweiName = zhiweiName;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setChargeUserId(String chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public void setCorporationCode(String corporationCode) {
        this.corporationCode = corporationCode;
    }

    public void setContractChangeDate(String contractChangeDate) {
        this.contractChangeDate = contractChangeDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public void setOutRoleCode(String outRoleCode) {
        this.outRoleCode = outRoleCode;
    }

    public void setHealthOverTime(String healthOverTime) {
        this.healthOverTime = healthOverTime;
    }

    public void setOaOpenFlag(String oaOpenFlag) {
        this.oaOpenFlag = oaOpenFlag;
    }

    public void setBankNameDesc(String bankNameDesc) {
        this.bankNameDesc = bankNameDesc;
    }

    public void setPayUnit(String payUnit) {
        this.payUnit = payUnit;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public void setCustomBankName(String customBankName) {
        this.customBankName = customBankName;
    }

    public void setEntryWay(String entryWay) {
        this.entryWay = entryWay;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public void setZhiweiClassName(String zhiweiClassName) {
        this.zhiweiClassName = zhiweiClassName;
    }

    public void setEntryTimes(String entryTimes) {
        this.entryTimes = entryTimes;
    }

    public void setOtherEntryTimes(String otherEntryTimes) {
        this.otherEntryTimes = otherEntryTimes;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getResumeId() {
        return resumeId;
    }

    public String getUserId() {
        return userId;
    }

//    public String getStatus() {
//        return status;
//    }

    public Integer getAge() {
        return age;
    }

    public String getAttendStatus() {
        return attendStatus;
    }

    public String getBankCardnum() {
        return bankCardnum;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getCardType() {
        return cardType;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getEntryShopDate() {
        return entryShopDate;
    }

    public String getFirstWorkDate() {
        return firstWorkDate;
    }

    public String getGangweiId() {
        return gangweiId;
    }

    public String getGangweiName() {
        return gangweiName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public Integer getResidenceProvinceId() {
        return residenceProvinceId;
    }

    public Integer getResidenceCityId() {
        return residenceCityId;
    }

    public Integer getResidenceDistrictId() {
        return residenceDistrictId;
    }

    public String getResidenceAddr() {
        return residenceAddr;
    }

    public Integer getTrialLimit() {
        return trialLimit;
    }

    public String getUsername() {
        return username;
    }

    public String getWorkType() {
        return workType;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public String getZhijiId() {
        return zhijiId;
    }

    public String getZhijiName() {
        return zhijiName;
    }

    public String getZhiweiId() {
        return zhiweiId;
    }

    public String getZhiweiName() {
        return zhiweiName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getNickName() {
        return nickName;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getChargeUserId() {
        return chargeUserId;
    }

    public String getChargeUserName() {
        return chargeUserName;
    }

    public String getCorporationCode() {
        return corporationCode;
    }

    public String getContractChangeDate() {
        return contractChangeDate;
    }

    public String getEmail() {
        return email;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getOutRoleCode() {
        return outRoleCode;
    }

    public String getHealthOverTime() {
        return healthOverTime;
    }

    public String getOaOpenFlag() {
        return oaOpenFlag;
    }

    public String getBankNameDesc() {
        return bankNameDesc;
    }

    public String getBankHead() {
        return bankHead;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getBankLineNo() {
        return bankLineNo;
    }

    public String getPayUnit() {
        return payUnit;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public String getBelongCompany() {
        return belongCompany;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public String getCustomBankName() {
        return customBankName;
    }

    public String getAddress() {
        return address;
    }

    public String getEntryWay() {
        return entryWay;
    }

    public String getEntryStatus() {
        return entryStatus;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public String getZhiweiClassName() {
        return zhiweiClassName;
    }

    public String getEntryTimes() {
        return entryTimes;
    }

    public String getOtherEntryTimes() {
        return otherEntryTimes;
    }

    public String getSyncFlag() {
        return syncFlag;
    }
}
