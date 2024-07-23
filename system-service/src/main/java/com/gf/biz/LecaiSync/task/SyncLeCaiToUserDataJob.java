package com.gf.biz.LecaiSync.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.LecaiSync.mapper.LecaiGangweiMapper;
import com.gf.biz.LecaiSync.mapper.LecaiHistoryGangweiMapper;
import com.gf.biz.LecaiSync.mapper.LecaiUserInfoMapper;
import com.gf.biz.LecaiSync.po.LecaiGangwei;
import com.gf.biz.LecaiSync.po.LecaiHistoryGangwei;
import com.gf.biz.LecaiSync.po.LecaiUserInfo;
import com.gf.biz.LecaiSync.util.lecaiSyncUtil;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.dingSync.util.DingSyncUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import io.seata.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.gf.biz.dingSync.util.DingSyncUtil.*;

public class SyncLeCaiToUserDataJob extends IJobHandler {
    private final static Logger log = LoggerFactory.getLogger(SyncLeiCaiToGenMasterDataJob.class);
    private static final String AppId = "65320f02-821c-4146-ad37-94f7d86785a0";
    private static final String AppKey = "dingziwaoroobolpqb0m";
    private static final String AppSecret = "MXk0icwCtMYXtzTwgEPKA0GNtCG4AwgNWMcxyijC1vtVM2EdsXgHGNy4rM0WNMzD";
    private static final String lecai_corpId = "56f837e57cbed68e607737b6e18f9a3b";
    private static final String lecai_secret = "55d39d743443229547c10ac92e817aab";
    private static final String lecai_agent = "joyhr";
    private static final Integer pageNo = 1;
    private static final Integer pageSize = 200;
    private static final Number groupId = 4281908459L;
    @Override
    public void execute() throws Exception {
        String jobParam= XxlJobHelper.getJobParam();
        String startTime = TimeUtil.dateFormat(TimeUtil.getBeginDayOfYesterday()) ;
        if(StringUtils.isNotBlank(jobParam)){
            JSONObject jobj=JSONObject.parseObject(jobParam);
            if(jobj.containsKey("startTime")){
                startTime = jobj.getString("startTime");
            }
        }
        log.info("前端指定时间:{}",startTime);
        log.info("开始同步乐才人员信息");
        syncLecaiUserInfo(startTime);
        log.info("开始向角色中添加人员信息");
        syncCreateDingDingRoleUser();
    }
    /**
     * 开始向库中插入乐才人员信息数据
     *
     * @param
     * @return
     * @throws Exception
     */
    private void syncLecaiUserInfo(String startTime) {
        Map userInfoMap = lecaiSyncUtil.getLecaiUserInfo1(lecai_corpId, pageNo, pageSize, startTime);//调用接口获取乐才人员信息数据
        JSONArray userInfo = (JSONArray) userInfoMap.get("data");
        LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);
        LecaiUserInfo userData;
        if (!userInfo.isEmpty()) {
            Integer total = (Integer) userInfoMap.get("total");
            Integer totalPage = (Integer) userInfoMap.get("totalpage");
            log.info("总条数：" + total + "总页数：" + totalPage);
            for (int i = 1; i <= totalPage; i++) {
                if (i != 1) {
                    userInfoMap = lecaiSyncUtil.getLecaiUserInfo1(lecai_corpId, i, pageSize,startTime);//调用接口获取乐才人员信息数据
                    userInfo = (JSONArray) userInfoMap.get("data");
                }
                for (int j = 0; j < userInfo.size(); j++) {//开始逐条数据的插入
                    JSONObject single = (JSONObject) userInfo.get(j);//获取单个数据
                    if (!single.isEmpty()) {
                        QueryWrapper<LecaiUserInfo> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("emp_no", single.getString("empNo"));
                        List<LecaiUserInfo> dbUserList = lecaiUserInfoMapper.selectList(queryWrapper);//根据empNo查询本地表中人员信息
                        if (dbUserList.isEmpty()) {//获取数据为空即没有查到当前数据插入
                            userData = new LecaiUserInfo();
                            userData.setUserId(single.getString("userId"));
                            userData.setResumeId(single.getString("resumeId"));
                            userData.setAge(single.getInteger("age"));
                            userData.setAttendStatus(single.getString("attendStatus"));
                            userData.setBankCardnum(single.getString("bankCardNum"));
                            userData.setBirthday(single.getString("birthday"));
                            userData.setCardNum(single.getString("cardNum"));
                            userData.setCardType(single.getString("cardType"));
                            userData.setDeptName(single.getString("deptName"));
                            userData.setEmpNo(single.getString("empNo"));
                            userData.setEntryDate(single.getString("entryDate"));
                            userData.setEntryShopDate(single.getString("entryShopDate"));
                            userData.setFirstWorkDate(single.getString("firstWorkDate"));
                            userData.setGangweiId(single.getString("gangweiId"));
                            userData.setGangweiName(single.getString("gangweiName"));
                            userData.setGender(single.getString("gender"));
                            userData.setPhone(single.getString("phone"));
                            userData.setResidenceType(single.getString("residenceType"));
                            userData.setResidenceProvinceId(single.getInteger("residenceProvinceId"));
                            userData.setResidenceCityId(single.getInteger("residenceCityId"));
                            userData.setResidenceDistrictId(single.getInteger("residenceDistrictId"));
                            userData.setResidenceAddr(single.getString("residenceAddress"));
                            userData.setTrialLimit(single.getInteger("trialLimit"));
                            userData.setUsername(single.getString("userName"));
                            userData.setWorkType(single.getString("workType"));
                            userData.setWorkStatus(single.getString("workStatus"));
                            userData.setZhijiId(single.getString("zhiji"));
                            userData.setZhijiName(single.getString("zhijiName"));
                            userData.setZhiweiId(single.getString("zhiweiId"));
                            userData.setZhiweiName(single.getString("zhiweiName"));
                            userData.setCreateDate(single.getString("createDate"));
                            userData.setUpdateDate(single.getString("updateDate"));
                            userData.setNickName(single.getString("nickName"));
                            userData.setDeptId(single.getString("deptId"));
                            userData.setUserStatus(single.getString("userStatus"));
                            userData.setChargeUserId(single.getString("chargeUserId"));
                            userData.setChargeUserName(single.getString("chargeUserName"));
                            userData.setCorporationCode(single.getString("corporationCode"));
                            userData.setContractChangeDate(single.getString("contractChangeDate"));
                            userData.setEmail(single.getString("email"));
                            userData.setDeptCode(single.getString("deptCode"));
                            userData.setRoleCode(single.getString("gangweiCode"));
                            userData.setOutRoleCode(single.getString("outGangweiCode"));
                            userData.setHealthOverTime(single.getString("healthOverTime"));
                            userData.setOaOpenFlag(single.getString("oaOpenFlag"));
                            userData.setBankNameDesc(single.getString("bankNameDesc"));
                            userData.setBankHead(single.getString("bankHead"));
                            userData.setBankBranch(single.getString("bankBranch"));
                            userData.setBankLineNo(single.getString("bankLineNo"));
                            userData.setPayUnit(single.getString("payUnit"));
                            userData.setSocialAccount(single.getString("socialAccount"));
                            userData.setBelongCompany(single.getString("belongCompany"));
                            userData.setJobLevel(single.getString("jobLevel"));
                            userData.setCustomBankName(single.getString("customBankName"));
                            userData.setAddress(single.getString("address"));
                            userData.setEntryWay(single.getString("entryWay"));
                            userData.setEntryStatus(single.getString("entryStatus"));
                            userData.setEduLevel(single.getString("eduLevel"));
                            userData.setZhiweiClassName(single.getString("zhiweiClassName"));
                            userData.setEntryTimes(single.getString("entryTimes"));
                            userData.setOtherEntryTimes(single.getString("otherEntryTimes"));
                            userData.setCreatedTime(new Date());
                            userData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                            userData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                            userData.setSyncFlag("0");//默认为0,即还没有插入到钉钉的角色组
                            lecaiUserInfoMapper.insert(userData);
                            log.info("数据插入成功");
                        } else {
                            log.info("数据已存在");
                            LecaiUserInfo userInfo1 = dbUserList.get(0);
                            if (single.getString("ganwgeiName") != null && !single.getString("ganwgeiName").isEmpty()) {
                                if (single.getString("ganwgeiName").equals(userInfo1.getGangweiName()) && single.getString("phone").equals(userInfo1.getPhone())) {
                                    log.info("岗位和手机号未变化，无需更新！");
                                } else {
                                    if (!single.getString("phone").equals(userInfo1.getPhone())) {
                                        log.info("手机号发生变化，需要进行更新！");//不需要再次推送
                                        UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                        LecaiUserInfo toUpd = new LecaiUserInfo();
                                        toUpd.setPhone(single.getString("phone"));
                                        updateWrapper.eq("id", userInfo1.getId());
                                        lecaiUserInfoMapper.update(toUpd, updateWrapper);
                                    } else if (!single.getString("ganwgeiName").equals(userInfo1.getGangweiName())) {
                                        log.info("岗位发生变化，需要进行更新！");
                                        UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                        LecaiUserInfo toUpd = new LecaiUserInfo();
                                        toUpd.setGangweiId(single.getString("gangweiId"));
                                        toUpd.setGangweiName(single.getString("gangweiName"));//更新岗位名称
                                        toUpd.setPastGangweiName(userInfo1.getGangweiName());
                                        //todo 删除岗位再重新同步钉钉
                                        toUpd.setSyncFlag("0");//状态由已推送改为待变更推送
                                        updateWrapper.eq("id", userInfo1.getId());
                                        lecaiUserInfoMapper.update(toUpd, updateWrapper);
                                    } else {
                                        log.info("岗位和手机号都发生变化，需要进行更新！");
                                        UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                        LecaiUserInfo toUpd = new LecaiUserInfo();
                                        toUpd.setGangweiId(single.getString("gangweiId"));
                                        toUpd.setGangweiName(single.getString("gangweiName"));//更新岗位名称
                                        toUpd.setPastGangweiName(userInfo1.getGangweiName());
                                        toUpd.setPhone(single.getString("phone"));
                                        toUpd.setSyncFlag("0");//状态由已推送改为待变更推送
                                        updateWrapper.eq("id", userInfo1.getId());
                                        lecaiUserInfoMapper.update(toUpd, updateWrapper);
                                    }
                                }
                            } else {
                                log.info("岗位名称为空，不做处理");
                            }
                        }
                    } else {
                        log.info("获取数据为空");
                    }
                }
            }
            log.info("数据同步完成");
        }
    }
    /**
     * 开始向角色中插入用户
     *
     * @param
     * @return
     */
    public void syncCreateDingDingRoleUser() {
        String url = DING_USER_GETBYMOBILE;
        LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);
        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);
        LecaiHistoryGangweiMapper lecaiHistoryGangweiMapper = SpringBeanUtil.getBean(LecaiHistoryGangweiMapper.class);
        String accessToken = DingSyncUtil.getDingAccessToken(AppKey, AppSecret);
        QueryWrapper<LecaiUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("sync_flag", "0");//查询未同步的人员
        wrapper.eq("sync_flag", "2");//查询待变更的人员
        wrapper.ne("user_status", "40");//离职
        wrapper.ne("user_status", "50");//退休
        wrapper.orderByAsc("gangwei_name");//根据gangwei_name升序排列
        List<LecaiUserInfo> localUserInfoList = lecaiUserInfoMapper.selectList(wrapper);
        LecaiUserInfo lecaiUserInfo;
        for (int i = 0; i < localUserInfoList.size(); i++) {
            lecaiUserInfo = localUserInfoList.get(i);
            url = url.replace("ACCESS_TOKEN", accessToken);
            if (lecaiUserInfo.getSyncFlag().equals("0")) {//如果未同步或者待变更推送，则调用接口
                JSONObject postBody = new JSONObject();
                postBody.put("mobile", lecaiUserInfo.getPhone());
                try {
                    String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);//根据手机号获取userID
                    if (resultString != null) {
                        JSONObject jsonObject = JSONObject.parseObject(resultString);//将一个JSON格式的字符串（即resultString）转换成一个JSONObject对象。
                        if (jsonObject.getString("errcode").equals("0")) {
                            if (jsonObject.getJSONObject("result").getString("userid") != null) {//获取的userId不为空
                                log.info("获取userId成功");
                                QueryWrapper<LecaiGangwei> wrapper1 = new QueryWrapper<>();
                                wrapper1.eq("post_name", lecaiUserInfo.getGangweiName());//查询岗位名称一样的人
                                wrapper1.eq("status", "0");//正在使用的岗位
                                wrapper1.orderByAsc("post_name");//根据post_name升序排列
                                List<LecaiGangwei> localGangweiList = lecaiGangweiMapper.selectList(wrapper1);
                                LecaiGangwei leCaiGangweiInfo = localGangweiList.get(0);
                                String urlAddUser = DING_USER_BATCHADDROLE;//批量增加员工角色
                                urlAddUser = urlAddUser.replace("ACCESS_TOKEN", accessToken);
                                if (!leCaiGangweiInfo.getDingRoleId().isEmpty()) {//获取的Ding——id不为空
                                    JSONObject postBodyAddUser = new JSONObject();
                                    postBodyAddUser.put("roleIds", leCaiGangweiInfo.getDingRoleId());
                                    postBodyAddUser.put("userIds", jsonObject.getJSONObject("result").getString("userid"));
                                    try {
                                        String resultStringAddUser = HttpClientUtil.postJsonUrl(urlAddUser, postBodyAddUser.toJSONString(), null);//调用批量增加这个接口
                                        if (resultStringAddUser != null) {
                                            JSONObject jsonObjectAddUser = JSONObject.parseObject(resultStringAddUser);
                                            if (jsonObjectAddUser.getString("errcode").equals("0")) {//成功添加用户
                                                log.info("添加用户成功" + resultStringAddUser);
                                                UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                                LecaiUserInfo toUpd = new LecaiUserInfo();
                                                toUpd.setSyncFlag("1");//更新的同步标志
                                                updateWrapper.eq("id", lecaiUserInfo.getId());//根据ID更新的地方
                                                lecaiUserInfoMapper.update(toUpd, updateWrapper);
                                                if (lecaiUserInfo.getPastGangweiName() != null) {//如果过去名称不为空，然后走删除角色
                                                    QueryWrapper<LecaiGangwei> wrapper2 = new QueryWrapper<>();
                                                    wrapper2.eq("post_name", lecaiUserInfo.getPastGangweiName());//之前岗位名称查询
                                                    wrapper2.eq("status", "0");//正在使用的岗位
                                                    wrapper2.orderByAsc("post_name");//根据post_name升序排列
                                                    List<LecaiGangwei> localGangweiList1 = lecaiGangweiMapper.selectList(wrapper2);
                                                    String urlDeleteRoleUser = DING_USER_BATCHDELETEROLE;//批量删除员工角色关联的接口
                                                    urlDeleteRoleUser = urlDeleteRoleUser.replace("ACCESS_TOKEN", accessToken);
                                                    JSONObject postBodyDeleteRoleUser = new JSONObject();
                                                    postBodyDeleteRoleUser.put("userIds", jsonObject.getJSONObject("result").getString("userid"));
                                                    //LecaiGangwei leCaiGangweiInfo1 = localGangweiList1.get(0);
                                                    if (localGangweiList1!=null&&localGangweiList1.size()>0) {//避免出现空指针异常
                                                        LecaiGangwei leCaiGangweiInfo1 = localGangweiList1.get(0);
                                                        postBodyDeleteRoleUser.put("roleIds", leCaiGangweiInfo1.getDingRoleId());
                                                    } else {
                                                        QueryWrapper<LecaiHistoryGangwei> wrapper3 = new QueryWrapper<>();
                                                        wrapper3.eq("post_name", lecaiUserInfo.getPastGangweiName());//之前岗位名称查询
                                                        //wrapper2.eq("status", "0");//正在使用的岗位
                                                        wrapper3.orderByAsc("post_name");//根据post_name升序排列
                                                        List<LecaiHistoryGangwei> localHistoryGangweiList = lecaiHistoryGangweiMapper.selectList(wrapper3);
                                                        //LecaiGangwei leCaiGangweiInfo = localGangweiList1.get(0);
                                                        LecaiHistoryGangwei leCaiHistoryGangweiInfo = localHistoryGangweiList.get(0);
                                                        if (leCaiHistoryGangweiInfo != null) {
                                                            postBodyDeleteRoleUser.put("roleIds", leCaiHistoryGangweiInfo.getDingRoleId());
                                                        } else {
                                                            log.info("无法查询roleId");
                                                        }
                                                    }
//                                                    String urlDeleteRoleUser=DING_USER_BATCHDELETEROLE;//批量删除员工角色关联的接口
//                                                    urlDeleteRoleUser=urlDeleteRoleUser.replace("ACCESS_TOKEN",accessToken);
//                                                    JSONObject postBodyDeleteRoleUser=new JSONObject();
//                                                    postBodyDeleteRoleUser.put("roleIds",lecaiUserInfo.getPastDingdingRoleId());
//                                                    postBodyDeleteRoleUser.put("userIds",jsonObject.getJSONObject("result").getString("userid"));
                                                    try {
                                                        String resultStringDeleteRoleUser = HttpClientUtil.postJsonUrl(urlDeleteRoleUser, postBodyDeleteRoleUser.toJSONString(), null);//调用批量删除这个接口
                                                        if (resultStringDeleteRoleUser != null) {//返回数据不为空
                                                            JSONObject jsonObjectDeleteRoleUser = JSONObject.parseObject(resultStringDeleteRoleUser);
                                                            if (jsonObjectDeleteRoleUser.getString("errcode").equals("0")) {
                                                                log.info("删除用户成功" + resultStringDeleteRoleUser);
                                                            } else {
                                                                log.error("删除用户失败" + resultStringDeleteRoleUser);
                                                            }
                                                        } else {
                                                            log.error("返回数据为空" + resultStringDeleteRoleUser);
                                                        }
                                                    } catch (Exception e) {
                                                        log.error(e.getMessage(), e);
                                                    }
                                                } else {
                                                    log.error("过去岗位名称为空，无需删除用户");
                                                }
                                            } else {
                                                log.error("添加用户失败" + resultStringAddUser);
                                            }
                                        } else {
                                            log.error("返回数据为空" + resultStringAddUser);
                                        }
                                    } catch (Exception e) {
                                        log.error(e.getMessage(), e);
                                    }
                                } else {
                                    log.error("岗位无roleId,此条数据未同步！");
                                    //syncLecaiGangweiInfo();
                                    //syncCreateDingDingRole();
                                    //syncCreateDingDingRoleUser();
                                }
                            }
                        }
                    } else {
                        log.error("获取userId为空" + resultString);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                log.info("该用户已同步");
            }
        }
        log.info("钉钉角色用户信息同步完成");
    }
}
