package com.gf.biz.LecaiSync.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.LecaiSync.mapper.LecaiGangweiMapper;
import com.gf.biz.LecaiSync.mapper.LecaiHistoryGangweiMapper;
import com.gf.biz.LecaiSync.po.LecaiGangwei;
import com.gf.biz.LecaiSync.po.LecaiHistoryGangwei;
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
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gf.biz.dingSync.util.DingSyncUtil.DING_USER_CREATE_ROLE;

public class SyncLeCaiToGangweiDataJob extends IJobHandler {
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
        log.info("开始同步乐才岗位信息");
        syncLecaiGangweiInfo(startTime);//获取乐才岗位信息并插入到本地的库中
        log.info("开始创建角色");
        syncCreateDingDingRole();


    }
    /**
     * 开始向库中插入乐才岗位信息数据
     *
     * @param     * @return
     */
    private void syncLecaiGangweiInfo(String startTime) {
        Map gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo1(lecai_corpId, pageNo, pageSize,startTime);//获取乐才岗位信息
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
                        gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo1(lecai_corpId, i, pageSize,startTime);//再次获取乐才岗位信息
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
}
