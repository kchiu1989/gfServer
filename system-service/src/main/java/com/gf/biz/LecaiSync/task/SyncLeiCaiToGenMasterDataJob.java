package com.gf.biz.LecaiSync.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.LecaiSync.dto.Lecai_gangwei_requestData;
import com.gf.biz.LecaiSync.mapper.LecaiHistoryGangweiMapper;
import com.gf.biz.LecaiSync.mapper.LecaiRolegroupInfoMapper;
import com.gf.biz.LecaiSync.mapper.LecaiUserInfoMapper;
import com.gf.biz.LecaiSync.po.LecaiHistoryGangwei;
import com.gf.biz.LecaiSync.po.LecaiRolegroupInfo;
import com.gf.biz.LecaiSync.po.LecaiUserInfo;
import com.gf.biz.codewave.po.LcapUser;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.LecaiSync.mapper.LecaiGangweiMapper;
import com.gf.biz.LecaiSync.po.LecaiGangwei;
import com.gf.biz.LecaiSync.util.lecaiSyncUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.dingSync.util.DingSyncUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import io.seata.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gf.biz.dingSync.util.DingSyncUtil.*;


public class SyncLeiCaiToGenMasterDataJob extends IJobHandler {

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
//        String jobParam=XxlJobHelper.getJobParam();
//        String startTime = TimeUtil.dateFormat(TimeUtil.getBeginDayOfYesterday()) ;
//        if(StringUtils.isNotBlank(jobParam)){
//            JSONObject jobj=JSONObject.parseObject(jobParam);
//            if(jobj.containsKey("startTime")){
//                startTime = jobj.getString("startTime");
//            }
//        }
//        log.info("前端指定时间:{}",startTime);

        //String accessTokenMap=lecaiSyncUtil.getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        //log.info("accessTokenMap:{}",accessTokenMap);//测试token
        //Map gangweiInfoMap=lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId,1,100);
        //log.info("gangweiInfoMap:{}",gangweiInfoMap.get("data"));
//        log.info("开始同步乐才岗位信息");
//        syncLecaiGangweiInfo();//获取乐才岗位信息并插入到本地的库中
//        log.info("开始创建角色组！");
//        syncDingDingRoleGroup();//创建角色组
//        log.info("开始创建角色");
//        syncCreateDingDingRole();
//        log.info("开始同步乐才人员信息");
//        syncLecaiUserInfo();
        log.info("开始向角色中添加人员信息");
        syncCreateDingDingRoleUser();
//        log.info("开始进行单元测试");
//        //ceshi1();
//        ceshi2();

    }

    /**
     * 开始向库中插入乐才岗位信息数据
     *
     * @param     * @return
     */
    private void syncLecaiGangweiInfo() {
        Map gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId, pageNo, pageSize);//获取乐才岗位信息
        JSONArray gangweiInfo = (JSONArray) gangweiInfoMap.get("data");//获取data对应的数据
        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);//Mapper注入
        LecaiHistoryGangweiMapper lecaiHistoryGangweiMapper = SpringBeanUtil.getBean(LecaiHistoryGangweiMapper.class);//Mapper注入

        LecaiGangwei gangweiData;
        LecaiHistoryGangwei historyGangweiData;
        if (gangweiInfo != null) {//判定是否获取到数据
            try {
                Integer total = Integer.parseInt(gangweiInfoMap.get("total").toString());//获取总条数
                Integer totalPage = Integer.parseInt(gangweiInfoMap.get("totalpage").toString());//获取总页数
                log.info("总条数：{}，总页数{}", total, totalPage);
                for (int i = 1; i <= totalPage; i++) {//遍历所有页数
                    if (i != 1) {//如果是第一页不需要获取数据
                        gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId, i, pageSize);//再次获取乐才岗位信息
                        gangweiInfo = (JSONArray) gangweiInfoMap.get("data");//再次获取data对应的数据
                    }
                    for (int j = 0; j < gangweiInfo.size(); j++) {//开始逐条数据的插入
                        JSONObject single = (JSONObject) gangweiInfo.get(j);//获取单个数据
                            /*String postPid = single.getString("id");//获取当前数据的id
                            String postName=single.getString("postName");//获取当前数据的岗位名称
                            String post_name=lecaiGangweiMapper.selectByPostId(postPid);//根据postPid查询本地表中岗位名称*/
                        if (!single.isEmpty() && !StringUtils.isBlank(single.getString("postName"))) {
                            QueryWrapper<LecaiGangwei> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("post_id", single.getString("id"));
                            List<LecaiGangwei> dbGangweiList = lecaiGangweiMapper.selectList(queryWrapper);//根据postPid查询本地表中岗位名称
                            QueryWrapper<LecaiGangwei> queryWrapperList = new QueryWrapper<>();
                            queryWrapperList.eq("post_name", single.getString("postName"));//根据postName查询本地表中岗位名称
                            queryWrapperList.eq("sync_flag", "1");
                            List<LecaiGangwei> dbGangweiList1 = lecaiGangweiMapper.selectList(queryWrapperList);
                            LecaiGangwei postNameData = null;
                            if (!dbGangweiList1.isEmpty()) {
                                postNameData = dbGangweiList1.get(0);
                            }
                            if (dbGangweiList.isEmpty()) {
                                //获取数据为空即没有查到当前数据插入
                                gangweiData = new LecaiGangwei();
                                gangweiData.setPostId(single.getString("id"));
                                gangweiData.setParentPostId(single.getString("postPId"));
                                gangweiData.setPostName(single.getString("postName"));
                                gangweiData.setPositionName(single.getString("positionName"));
                                gangweiData.setRankTop(single.getString("rankTop"));
                                gangweiData.setRankLower(single.getString("rankLower"));
                                gangweiData.setMember(single.getInteger("member"));
                                // gangweiData.setOrganName(single.getString("organName"));
                                gangweiData.setOutGangweiCode(single.getLong("gangweiCode"));
                                gangweiData.setStatus(single.getString("status"));
                                gangweiData.setOrganId(single.getString("organId"));
                                gangweiData.setCreatedTime(new Date());
                                gangweiData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                                gangweiData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                                if (postNameData != null) {//如果原来库中有这个post_name，则直接插入
                                    gangweiData.setDingRoleId(postNameData.getDingRoleId());
                                    gangweiData.setSyncFlag("1");
                                } else {
                                    gangweiData.setSyncFlag("0");//默认为0,即还没有插入到钉钉的角色组
                                }
                                lecaiGangweiMapper.insert(gangweiData);//插入数据库
                                log.info("插入成功:", gangweiData);
                            } else {
                                LecaiGangwei dnGamgwei = dbGangweiList.get(0);//获取当前数据
                                if (dnGamgwei.getPostName().equals(single.getString("postName"))) {//获取数据不为空且查到当前数据相同，不作操作
                                    log.info("岗位名称未变化不做处理！");
                                } else {//获取数据不为空且查到当前数据不同，更新
                                    //String s = null;
                                    UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();
                                    //LecaiGangwei toUpd = new LecaiGangwei();
                                    //todo，因为如果更新为空的时候，不会 更新
                                    updateWrapper.set("post_name", single.getString("postName"));
                                    if (postNameData != null) {//如果原来库中有这个post_name，则直接插入
                                        updateWrapper.set("ding_role_id", postNameData.getDingRoleId());
                                    } else {
                                        historyGangweiData = new LecaiHistoryGangwei();//测试
                                        BeanUtils.copyProperties(dnGamgwei, historyGangweiData);
                                        lecaiHistoryGangweiMapper.insert(historyGangweiData);//插入数据库
                                        log.info("插入成功:", historyGangweiData);
                                        updateWrapper.set("ding_role_id", null);
                                        updateWrapper.set("sync_flag", "0");
                                    }
                                    updateWrapper.eq("id", dnGamgwei.getId());
                                    lecaiGangweiMapper.update(null, updateWrapper);
                                }
                            }
                        } else {
                            log.error("接口数据为空");
                        }
                    }
                }
                log.info("成功插入全部数据");
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);//出错日志
            }
        } else {
            log.info("没有数据");
        }
    }

    private void ceshi1() {

        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);//Mapper注入
        LecaiHistoryGangweiMapper lecaiHistoryGangweiMapper = SpringBeanUtil.getBean(LecaiHistoryGangweiMapper.class);//Mapper注入
        LecaiHistoryGangwei historyGangweiData;
        QueryWrapper<LecaiGangwei> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", "dong1125");
        List<LecaiGangwei> dbGangweiList = lecaiGangweiMapper.selectList(queryWrapper);//根据postPid查询本地表中岗位名称
        QueryWrapper<LecaiGangwei> queryWrapperList = new QueryWrapper<>();
        queryWrapperList.eq("post_name", "吃鸡高手");//根据postName查询本地表中岗位名称
        queryWrapperList.eq("sync_flag", "1");
        List<LecaiGangwei> dbGangweiList1 = lecaiGangweiMapper.selectList(queryWrapperList);
        LecaiGangwei postNameData = null;
        if (!dbGangweiList1.isEmpty()) {
            postNameData = dbGangweiList1.get(0);
        }
        if (dbGangweiList.isEmpty()) {
            //获取数据为空即没有查到当前数据插入
//            gangweiData = new LecaiGangwei();
//            gangweiData.setPostId(single.getString("id"));
//            gangweiData.setParentPostId(single.getString("postPId"));
//            gangweiData.setPostName(single.getString("postName"));
//            gangweiData.setPositionName(single.getString("positionName"));
//            gangweiData.setRankTop(single.getString("rankTop"));
//            gangweiData.setRankLower(single.getString("rankLower"));
//            gangweiData.setMember(single.getInteger("member"));
//            // gangweiData.setOrganName(single.getString("organName"));
//            gangweiData.setOutGangweiCode(single.getLong("gangweiCode"));
//            gangweiData.setStatus(single.getString("status"));
//            gangweiData.setOrganId(single.getString("organId"));
//            gangweiData.setCreatedTime(new Date());
//            gangweiData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
//            gangweiData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
//            if (postNameData != null) {//如果原来库中有这个post_name，则直接插入
//                gangweiData.setDingRoleId(postNameData.getDingRoleId());
//                gangweiData.setSyncFlag("1");
//            } else {
//                gangweiData.setSyncFlag("0");//默认为0,即还没有插入到钉钉的角色组
//            }
//            lecaiGangweiMapper.insert(gangweiData);//插入数据库
//            log.info("插入成功:", gangweiData);
        } else {
            LecaiGangwei dnGamgwei = dbGangweiList.get(0);//获取当前数据
            if (dnGamgwei.getPostName().equals("1")) {//获取数据不为空且查到当前数据相同，不作操作
                log.info("岗位名称未变化不做处理！");
            } else {//获取数据不为空且查到当前数据不同，更新
                //String s = null;
                UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();
                //LecaiGangwei toUpd = new LecaiGangwei();
                //todo，因为如果更新为空的时候，不会 更新
                updateWrapper.set("post_name", "吃鸡高手");
                if (postNameData != null) {//如果原来库中有这个post_name，则直接插入
                    updateWrapper.set("ding_role_id", postNameData.getDingRoleId());
                } else {
                    historyGangweiData = new LecaiHistoryGangwei();//测试
                    BeanUtils.copyProperties(dnGamgwei, historyGangweiData);
                    lecaiHistoryGangweiMapper.insert(historyGangweiData);//插入数据库
                    log.info("插入成功:", historyGangweiData);
                    updateWrapper.set("ding_role_id", null);
                    updateWrapper.set("sync_flag", "0");
                }
                updateWrapper.eq("id", dnGamgwei.getId());
                lecaiGangweiMapper.update(null, updateWrapper);
            }
        }
    }


    /**
     * 开始向库中插入乐才人员信息数据
     *
     * @param
     * @return
     * @throws Exception
     */
    private void syncLecaiUserInfo() {
        Map userInfoMap = lecaiSyncUtil.getLecaiUserInfo(lecai_corpId, pageNo, pageSize);//调用接口获取乐才人员信息数据
        JSONArray userInfo = (JSONArray) userInfoMap.get("data");
        LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);
        LecaiUserInfo userData;
        if (!userInfo.isEmpty()) {
            Integer total = (Integer) userInfoMap.get("total");
            Integer totalPage = (Integer) userInfoMap.get("totalpage");
            log.info("总条数：" + total + "总页数：" + totalPage);
            for (int i = 1; i <= totalPage; i++) {
                if (i != 1) {
                    userInfoMap = lecaiSyncUtil.getLecaiUserInfo(lecai_corpId, i, pageSize);//调用接口获取乐才人员信息数据
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
                            if (single.getString("gangweiName") != null && !single.getString("gangweiName").isEmpty()) {
                                if (single.getString("gangweiName").equals(userInfo1.getGangweiName()) && single.getString("phone").equals(userInfo1.getPhone())&& single.getString("userStatus").equals(userInfo1.getUserStatus())) {
                                    log.info("岗位、员工状态和手机号未变化，无需更新！");
                                } else {
                                    if (!single.getString("phone").equals(userInfo1.getPhone())) {
                                        log.info("手机号发生变化，需要进行更新！");//不需要再次推送
                                        UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                        LecaiUserInfo toUpd = new LecaiUserInfo();
                                        toUpd.setPhone(single.getString("phone"));
                                        updateWrapper.eq("id", userInfo1.getId());
                                        lecaiUserInfoMapper.update(toUpd, updateWrapper);
                                    } else if (!single.getString("gangweiName").equals(userInfo1.getGangweiName())) {
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
                                    } else if (!single.getString("userStatus").equals(userInfo1.getUserStatus())) {
                                        log.info("员工状态发生变化，需要进行更新！");
                                        UpdateWrapper<LecaiUserInfo> updateWrapper = new UpdateWrapper<>();
                                        LecaiUserInfo toUpd = new LecaiUserInfo();
                                        toUpd.setGangweiId(single.getString("gangweiId"));
                                        toUpd.setUserStatus(single.getString("userStatus"));
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
                                        toUpd.setUserStatus(single.getString("userStatus"));//更新岗位状态
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
     * 创建钉钉角色组
     *
     * @param
     * @return
     */
    public void syncDingDingRoleGroup() {
        String url = "https://oapi.dingtalk.com/role/add_role_group?access_token=ACCESS_TOKEN";
        //获取钉钉token
        String accessToken = DingSyncUtil.getDingAccessToken(AppKey, AppSecret);
        log.info("获取钉钉token成功,{}", accessToken);
        LecaiRolegroupInfoMapper lecaiRolegroupInfoMapper = SpringBeanUtil.getBean(LecaiRolegroupInfoMapper.class);//Mapper注入
        LecaiRolegroupInfo roleGroupData;
        url = url.replace("ACCESS_TOKEN", accessToken);
        JSONObject postBody = new JSONObject();//封装参数
        postBody.put("name", "乐才角色组3");
        if (accessToken != null) {
            try {
                String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
                if (resultString != null) {
                    JSONObject jsonObject = JSONObject.parseObject(resultString);
                    if (jsonObject.getString("errcode").equals("0")) {
                        log.info("创建角色组成功");
                        roleGroupData = new LecaiRolegroupInfo();
                        roleGroupData.setDingGroupId(jsonObject.getString("groupId"));
                        roleGroupData.setName("乐才角色组3");
                        lecaiRolegroupInfoMapper.insert(roleGroupData);
                        log.info("创建的角色组Id:{}", jsonObject.getString("groupId"));
                    } else {
                        log.error("创建角色组失败");
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 开始创建角色
     *
     * @param
     * @return
     */
    private void syncCreateDingDingRole() {
        String url = DING_USER_CREATE_ROLE;
        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);//Mapper注入
        // 获取钉钉token
        String accessToken = DingSyncUtil.getDingAccessToken(AppKey, AppSecret);
        QueryWrapper<LecaiGangwei> wrapper = new QueryWrapper<>();
        wrapper.eq("sync_flag", "0");//查询未同步的岗位
        wrapper.eq("status", "0");//正在使用的岗位
        wrapper.orderByAsc("post_name");//根据post_name升序排列
        List<LecaiGangwei> localGangweiList = lecaiGangweiMapper.selectList(wrapper);
        Map<String, String> hasSyncCache = new HashMap<>();
        LecaiGangwei lecaiGangwei;
        for (int i = 0; i < localGangweiList.size(); i++) {
            lecaiGangwei = localGangweiList.get(i);
            String dingRoleId = null;//避免出现空指针异常
            if (hasSyncCache.containsKey(lecaiGangwei.getPostName())) {//如果包含这个PostName，就取出roleId
                //取出roleId
                dingRoleId = hasSyncCache.get(lecaiGangwei.getPostName());//从Map中获取数据
                UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();//更新操作
                LecaiGangwei toUpd = new LecaiGangwei();
                toUpd.setDingRoleId(dingRoleId);//更新的DingRoleId
                toUpd.setSyncFlag("1");//更新SyncFlag为1
                updateWrapper.eq("id", lecaiGangwei.getId());//根据ID更新的地方
                log.info("已有roleId，直接更新！");
                lecaiGangweiMapper.update(toUpd, updateWrapper);
            } else {
                url = url.replace("ACCESS_TOKEN", accessToken);
                JSONObject postBody = new JSONObject();//封装参数
                postBody.put("roleName", lecaiGangwei.getPostName());
                postBody.put("groupId", groupId);
                try {
                    String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
                    if (resultString != null) {
                        JSONObject jsonObject = JSONObject.parseObject(resultString);
                        if (jsonObject.getString("errcode").equals("0")) {
                            log.info("创建角色成功");
                            UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();
                            LecaiGangwei toUpd = new LecaiGangwei();
                            toUpd.setDingRoleId(jsonObject.getString("roleId"));//更新的DingRoleId
                            toUpd.setSyncFlag("1");//更新SyncFlag为1
                            updateWrapper.eq("id", lecaiGangwei.getId());//根据ID更新的地方
                            lecaiGangweiMapper.update(toUpd, updateWrapper);
                            dingRoleId = jsonObject.getString("roleId");//调用接口;
                            hasSyncCache.put(lecaiGangwei.getPostName(), dingRoleId);//岗位名称可能为空
                        } else {
                            log.error("创建角色失败" + jsonObject.getString("errmsg"));
                        }
                    } else {
                        log.error("创建角色失败" + resultString);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            }
//            lecaiGangwei=new LecaiGangwei();
//            lecaiGangwei=lecaiGangweiMapper.selectById(i);
//            if("1".equals(lecaiGangwei.getSyncFlag())){//当前岗位未更新且DingRoleId为空
//                QueryWrapper<LecaiGangwei> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("post_Name",lecaiGangwei.getPostName() );
//                List<LecaiGangwei> allGangweiNameExistList = lecaiGangweiMapper.selectList(queryWrapper);//allGangweiNameExistList至少有一个数据
//                LecaiGangwei firstGetData = allGangweiNameExistList.get(0);
//                if(firstGetData.getDingRoleId().isEmpty()&&!accessToken.isEmpty()){//如果获取的roleId为空且access_token不为空，则再次调用创建钉钉角色的接口，获取DingRoleId
//                    url = url.replace("ACCESS_TOKEN", accessToken);
//                    JSONObject postBody = new JSONObject();//封装参数
//                    postBody.put("roleName",lecaiGangwei.getPostName());
//                    postBody.put("groupId",groupId);
//                    try{
//                        String resultString = HttpClientUtil.postJsonUrl(url, postBody.toJSONString(), null);
//                        if(resultString!=null){
//                            JSONObject jsonObject = JSONObject.parseObject(resultString);
//                            if(jsonObject.getString("errcode").equals("0")){
//                                log.info("创建角色成功");
//                                UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();
//                                LecaiGangwei toUpd = new LecaiGangwei();
//                                toUpd.setDingRoleId(jsonObject.getString("roleId"));//更新的DingRoleId
//                                updateWrapper.eq("id", lecaiGangwei.getId());//根据ID更新的地方
//                                lecaiGangweiMapper.update(toUpd, updateWrapper);
//                            }
//                        }
//                    }catch (Exception e){
//                        log.error(e.getMessage(), e);
//                    }
//                }else{//如果获取到的roleId不为空，则更新为库中相同岗位名称的roleId
//                    UpdateWrapper<LecaiGangwei> updateWrapper = new UpdateWrapper<>();
//                    LecaiGangwei toUpd = new LecaiGangwei();
//                    toUpd.setDingRoleId(firstGetData.getDingRoleId());//更新的DingRoleId
//                    updateWrapper.eq("id", lecaiGangwei.getId());//根据ID更新的地方
//                    lecaiGangweiMapper.update(toUpd, updateWrapper);
//                }
//
//            }else{
//                log.info("该数据已经有roleId");
//            }

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
        //wrapper.eq("sync_flag", "2");//查询待变更的人员
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
                                                    log.info("过去岗位名称为空，无需删除用户");
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
    private void ceshi2(){
        LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);
        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);
        LecaiHistoryGangweiMapper lecaiHistoryGangweiMapper = SpringBeanUtil.getBean(LecaiHistoryGangweiMapper.class);
        QueryWrapper<LecaiGangwei> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("post_name", "吃鸡低手");//之前岗位名称查询
        //wrapper2.eq("status", "0");//正在使用的岗位
        wrapper2.orderByAsc("post_name");//根据post_name升序排列
        List<LecaiGangwei> localGangweiList1 = lecaiGangweiMapper.selectList(wrapper2);
        if(localGangweiList1 != null&&localGangweiList1.size()>0) {
            LecaiGangwei leCaiGangweiInfo1 = localGangweiList1.get(0);
            log.info("当前岗位roleId" + leCaiGangweiInfo1.getDingRoleId());
        }else{
            QueryWrapper<LecaiHistoryGangwei> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("post_name", "吃鸡低手");//之前岗位名称查询
            //wrapper2.eq("status", "0");//正在使用的岗位
            wrapper3.orderByAsc("post_name");//根据post_name升序排列
            List<LecaiHistoryGangwei> localHistoryGangweiList = lecaiHistoryGangweiMapper.selectList(wrapper3);
            //LecaiGangwei leCaiGangweiInfo = localGangweiList1.get(0);
            LecaiHistoryGangwei leCaiHistoryGangweiInfo = localHistoryGangweiList.get(0);
            if (leCaiHistoryGangweiInfo != null) {
                log.info("历史岗位roleId" + leCaiHistoryGangweiInfo.getDingRoleId());
                //postBodyDeleteRoleUser.put("roleIds", leCaiHistoryGangweiInfo.getDingRoleId());
            } else {
                log.info("无法查询roleId");
            }
        }
    }

}