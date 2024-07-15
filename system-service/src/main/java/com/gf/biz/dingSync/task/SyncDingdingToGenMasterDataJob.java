package com.gf.biz.dingSync.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.exception.BusinessException;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.dingSync.dto.UserDepartmentDto;
import com.gf.biz.dingSync.mapper.MdDepartmentMapper;
import com.gf.biz.dingSync.mapper.UserDepartmentMapper;


import com.gf.biz.dingSync.mapper.UserInfoMapper;
import com.gf.biz.dingSync.po.MdDepartment;
import com.gf.biz.dingSync.po.UserDepartment;
import com.gf.biz.dingSync.po.UserInfo;


import com.gf.biz.dingSync.service.UserDepartmentService;

import com.gf.biz.dingSync.util.DingSyncUtil;
import com.xxl.job.core.handler.IJobHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SyncDingdingToGenMasterDataJob extends IJobHandler {

    private final static Logger log = LoggerFactory.getLogger(SyncDingdingToGenMasterDataJob.class);
    private final static String rootDeptId = "1";
    private final static Long unitId = 1L;
    private final static Long groupId = 1L;
    private final static String AppKey = "dingziwaoroobolpqb0m";
    private final static String AppSecret = "MXk0icwCtMYXtzTwgEPKA0GNtCG4AwgNWMcxyijC1vtVM2EdsXgHGNy4rM0WNMzD";

//	private final static String AppKey =  "ding51bq1zdwmpbshv3b";
    // AppContext.getSystemProperty("dingSync.appKey");
//	private final static String AppSecret = "moePmcZ6cz5jes5ZSYX2QSEuw0CjGMlni-mJ7RuM3a-dalMJBnDpaS3g1zQxtP61";
    // AppContext.getSystemProperty("dingSync.appSecret");

   /* private static List<MidOrgPersonBaseEntity> createPersonBases = new ArrayList<>();
    private static List<MidOrgPersonBaseEntity> updatePersonBases = new ArrayList<>();
    private static List<MidOrgJobEntity> createJobBases = new ArrayList<>();
    private static List<MidOrgJobEntity> updateJobBases = new ArrayList<>();
    private static List<MidOrgPostEntity> createPostBases = new ArrayList<>();
    private static List<MidOrgPostEntity> updatePostBases = new ArrayList<>();

    private DingSyncDao dingSyncDao;

    public void setDingSyncDao (DingSyncDao dingSyncDao){
        this.dingSyncDao = dingSyncDao;


    }*/


    @Override
    public void execute() throws Exception {

        log.info("开始同步钉钉部门和人员...");

        //key为ifId value为部门信息
        Map<String,MdDepartment> deptCache = new HashMap<>();

        Set<String> hasSyncDeptIds = new HashSet<>();
        //同步所有部门信息
        syncDepts(hasSyncDeptIds,deptCache);


        //本次已经同步过的用户
        Set<String> hasSyncDingUserIds = new HashSet<>();

        //根据本次成功同步到的部门Id全量，对人员进行处理
        Iterator<String> deptItr = hasSyncDeptIds.iterator();
        while (deptItr.hasNext()) {
            //处理人员相关数据
            dealWithPersonInfo(deptItr.next(), deptCache,hasSyncDingUserIds);
        }

        //需要当前数据库中存在的部门去查询接口是否存在来判断
        //先根据数据库中的和本次同步接口的差集，再和接口重新进行一次一一匹配，本次同步接口可能出现部分部门同步失败的情况

        MdDepartmentMapper mdDepartmentMapper = SpringBeanUtil.getBean(MdDepartmentMapper.class);

        QueryWrapper<MdDepartment> queryWrapper =new QueryWrapper<>();
        queryWrapper.select("if_id").eq(CommonConstant.COLUMN_DEL_FLAG,CommonConstant.STATUS_UN_DEL);

        List<Object> allExistsDbDeptIdObjList = mdDepartmentMapper.selectObjs(queryWrapper);
        List<String> allExistsDbDeptIdList = new ArrayList<>();

        for(Object dbIfDeptIdObj:allExistsDbDeptIdObjList){
            allExistsDbDeptIdList.add(String.valueOf(dbIfDeptIdObj));
        }


        boolean removeFlag = allExistsDbDeptIdList.removeAll(hasSyncDeptIds);
        if (removeFlag) {

            List<String> toDeleteIfDeptIds = new ArrayList<>();

            for (String dbIfDeptId : allExistsDbDeptIdList) {
                //逻辑删除部门表
                Map deptInfoMap = DingSyncUtil.getDeptInfoNewVersion(dbIfDeptId);
                if (deptInfoMap.isEmpty() || deptInfoMap == null) {
                    log.error("deptId=" + dbIfDeptId + " 部门接口详情未成功获取到，停止删除");
                    continue;
                }
                if (deptInfoMap.get("errcode") != null ) {

                    if(!"0".equals(String.valueOf(deptInfoMap.get("errcode")))){
                        log.error("获取部门详情存在错误码,eptId=" + dbIfDeptId + ",报错信息：" + deptInfoMap.get("errmsg"));
                        if( "40009".equals(String.valueOf(deptInfoMap.get("errcode")))){
                            toDeleteIfDeptIds.add(dbIfDeptId);
                        }
                    }
                }
            }

            if(!toDeleteIfDeptIds.isEmpty()){

                MdDepartment toUpd = new MdDepartment();
                toUpd.setDeletedFlag(CommonConstant.STATUS_DEL);
                toUpd.setUpdatedTime(new Date());
                toUpd.setUpdatedBy(CommonConstant.DEFAULT_OPT_USER);
                UpdateWrapper<MdDepartment> updWrapper = new UpdateWrapper<>();
                updWrapper.in("if_id", toDeleteIfDeptIds);
                mdDepartmentMapper.update(toUpd,updWrapper);
            }

        }

        //调用接口 职能人事 离职员工列表
        List<String> resignUserList =DingSyncUtil.getResignUserList(null);

        if(!resignUserList.isEmpty()) {
            UserInfoMapper userInfoMapper = SpringBeanUtil.getBean(UserInfoMapper.class);

            UserDepartmentMapper userDepartmentMapper = SpringBeanUtil.getBean(UserDepartmentMapper.class);

            for (String resignUserId : resignUserList) {
                log.info("resignUserId:{}", resignUserId);


                QueryWrapper<UserInfo> qw1 = new QueryWrapper<>();
                qw1.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                qw1.eq("if_id", resignUserId);
                UserInfo user = userInfoMapper.selectOne(qw1);

                if(user!=null){
                    user.setDeletedFlag(CommonConstant.STATUS_DEL);
                    int updUserCnt = userInfoMapper.updateById(user);

                    UserDepartment toUpdUserDept = new UserDepartment();
                    toUpdUserDept.setDeletedFlag(CommonConstant.STATUS_DEL);
                    UpdateWrapper<UserDepartment> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("user_id",user.getId());
                    int updUserDeptCnt = userDepartmentMapper.update(toUpdUserDept,updateWrapper);
                }
            }
        }
    }


    /**
     * 同步部门信息
     *
     * @param midOrgPersonPartMap 数据库已存在的任职信息
     * @param midOrgPersonBaseMap 数据库已存在的人员基础信息
     * @param midOrgPersonMap     数据库已存在的人员信息
     * @param midOrgPostMap       数据库已存在的岗位信息
     * @param userIds
     */
   /* private void syncDeptsWithQueueMap(Map<String, MidOrgDepartmentEntity> midDeptsMap, List<String> unitIds, String unitId
            , List<MidOrgDepartmentEntity> createMidDepts, List<MidOrgDepartmentEntity> updateMidDepts, List<String> deptIds, Map<String, MidOrgPostEntity> midOrgPostMap, Map<String, MidOrgPersonEntity> midOrgPersonMap, Map<String, MidOrgPersonBaseEntity> midOrgPersonBaseMap,
                                       Map<String, List<MidOrgPersonPartEntity>> midOrgPersonPartMap, List<String> userIds) throws BusinessException {


        Map deptMap = DingSyncUtil.getSubDeptListByOption(rootDeptId);

        if (deptMap.isEmpty() || deptMap == null) {
            log.info("unitId=" + rootDeptId + "的单位/部门下无子部门信息，跳过");
            return;
        }
        if (deptMap.get("errcode") != null && (Integer) deptMap.get("errcode") != 0) {//0是成功
            log.error("获取部门信息报错,unitId=" + rootDeptId + ",报错信息：" + deptMap.get("errmsg"));
            return;
        }

        if (deptMap.get("result") != null) {
            Queue<Map> deptQueue = new LinkedList<>();
            List<Map<String, Object>> dingDeptlist = (List<Map<String, Object>>) deptMap.get("result");
            for (Map<String, Object> dingDeptMap : dingDeptlist) {
                deptQueue.offer(dingDeptMap);
            }

            while (!deptQueue.isEmpty()) {
                //每次循环出列一个对象 一直到队列为空
                Map<String, Object> deptIfMap = deptQueue.poll();
                syncDeptAndPersonCore(deptIfMap, midDeptsMap, unitIds, unitId
                        , createMidDepts, updateMidDepts, deptIds, midOrgPostMap, midOrgPersonMap, midOrgPersonBaseMap,
                        midOrgPersonPartMap, userIds);

                String subDeptParentId = String.valueOf(deptIfMap.get("id"))
                Map subDeptMap = DingSyncUtil.getSubDeptListByOption(subDeptParentId);

                if (subDeptMap.isEmpty() || subDeptMap == null) {
                    log.info("deptId=" + subDeptParentId + "的单位/部门下无子部门信息，跳过");
                    return;
                }
                if (subDeptMap.get("errcode") != null && (Integer) subDeptMap.get("errcode") != 0) {//0是成功
                    log.error("获取部门信息报错,unitId=" + rootDeptId + ",报错信息：" + subDeptMap.get("errmsg"));
                    return;
                }

                if (subDeptMap.get("result") != null) {
                    List<Map<String, Object>> subDingDeptlist = (List<Map<String, Object>>) subDeptMap.get("result");
                    for (Map<String, Object> subDingDeptMap : subDingDeptlist) {
                        deptQueue.offer(subDingDeptMap);
                    }
                }
            }

        }
    }*/

    /*private void syncDeptAndPersonCore(Map<String, Object> dingDept, Map<String, MidOrgDepartmentEntity> midDeptsMap, List<String> unitIds, String unitId
            , List<MidOrgDepartmentEntity> createMidDepts, List<MidOrgDepartmentEntity> updateMidDepts, List<String> deptIds, Map<String, MidOrgPostEntity> midOrgPostMap, Map<String, MidOrgPersonEntity> midOrgPersonMap, Map<String, MidOrgPersonBaseEntity> midOrgPersonBaseMap,
                                       Map<String, List<MidOrgPersonPartEntity>> midOrgPersonPartMap, List<String> userIds) {
        String dingDeptId = dingDept.get("dept_id").toString();

        boolean isCreate = true;
        MidOrgDepartmentEntity midOrgDept = new MidOrgDepartmentEntity();
        MidOrgDepartmentEntity midOrgDeptOld = new MidOrgDepartmentEntity();//添加旧数据对象用于对比数据
        if (midDeptsMap.get(dingDeptId) == null) {
            midOrgDept.setId(dingDeptId);
        } else {
            midOrgDept = midDeptsMap.get(dingDeptId);
            midOrgDeptOld = midDeptsMap.get(dingDeptId);
            isCreate = false;
        }

        //获取部门详情
        Map deptInfoMap = DingSyncUtil.getDeptInfoNewVersion(dingDeptId);
        if (deptInfoMap.isEmpty() || deptInfoMap == null) {
            log.error("deptId=" + dingDeptId + " 部门信息未获取到，停止同步");
            return;
        }
        if (deptInfoMap.get("errcode") != null && (Integer) deptInfoMap.get("errcode") != 0) {//0是成功
            log.error("获取部门信息报错,eptId=" + dingDeptId + ",报错信息：" + deptInfoMap.get("errmsg"));
            return;
        }
        //过滤将三个部门添加到子单位 ???不太清晰
        if ("1".equals(deptInfoMap.get("parent_id").toString())) {
            Map<String, MidOrgUnitEntity> unitMap = getUnitFromDB();
            MidOrgUnitEntity unit = new MidOrgUnitEntity();
            MidOrgUnitEntity unitOld = new MidOrgUnitEntity();
            String id = deptInfoMap.get("id").toString();
            unitIds.add(id);
            boolean isUnitCreate = true;
            if (unitMap.get(id) == null) {
                unit.setId(deptInfoMap.get("id").toString());
            } else {
                unit = unitMap.get(id);
                unitOld = unitMap.get(id);
                isUnitCreate = false;
            }
            unit.setName(deptInfoMap.get("name").toString());
            unit.setShortName(unit.getName());
            unit.setCode(deptInfoMap.get("id").toString());
            unit.setParentid(deptInfoMap.get("parent_id").toString());
            unit.setIsDeleted(disableTag);
            unit.setGroupid("1");
            unit.setIsEnable(enableTag);
            //比较数据是否被修改
            boolean equals = unitOld.equals(unit);
            if (equals) {
                unit.setUpdateTime(new Date());
                log.info("未检测到更新数据：" + unit.getName());
            } else {
                unit.setUpdateTime(new Date());
            }
            if (isUnitCreate) {
                dingSyncDao.saveMidOrgUnit(unit);
            } else {
                dingSyncDao.updateMidOrgUnit(unit);
            }
            //将人员id以及子部门id使用该单位ID
            unitId = id;
        } else {
            //获取部门详情
            midOrgDept.setName(deptInfoMap.get("name").toString());
            midOrgDept.setCode(deptInfoMap.get("id").toString());
            midOrgDept.setDepartmanagers(deptInfoMap.get("dept_manager_userid_list").toString().replace("|", ","));
            midOrgDept.setGroupid("1");//客开修改
            midOrgDept.setIsDeleted(disableTag);
            midOrgDept.setIsEnable(enableTag);
            //过滤掉部门id
            if (!deptInfoMap.get("parent_id").toString().equals(unitId)) {
                midOrgDept.setParentid(deptInfoMap.get("parent_id").toString());
            }
            midOrgDept.setUnitid(unitId);
            boolean equals = midOrgDeptOld.equals(midOrgDept);
            if (equals) {
                midOrgDept.setUpdateTime(new Date());
                log.info("未检测到更新数据" + midOrgDept.getName());
            } else {
                midOrgDept.setUpdateTime(new Date());
            }
            if (isCreate) {
                createMidDepts.add(midOrgDept);
            } else {
                updateMidDepts.add(midOrgDept);
            }
            deptIds.add(dingDeptId);
        }

        List<String> deptIdss = new ArrayList<>();
        deptIdss.add(dingDeptId);
        //处理人员相关数据
        this.dealWithPersonInfo(unitId, dingDeptId, midOrgPostMap, midOrgPersonBaseMap, midOrgPersonPartMap, userIds);
        //dealWithDept(unitId, midDeptsMap, updateMidDepts, createMidDepts, deptIds, dingDeptId, midOrgPostMap, midOrgPersonMap, midOrgPersonBaseMap, midOrgPersonPartMap, userIds, unitIds);
    }*/


    /**
     * 同步部门信息
     *
     * @param dingDeptIds
     */
    private void syncDepts(Set<String> dingDeptIds,Map<String,MdDepartment> deptCache) throws BusinessException {
            /*List<String> deptIds = new ArrayList<>();
            Map<String, MidOrgDepartmentEntity> midDeptsMap = this.getDeptsFromDB();*/
        //List<MidOrgDepartmentEntity> updateMidDepts = new ArrayList<>();
        //List<MidOrgDepartmentEntity> createMidDepts = new ArrayList<>();

        MdDepartmentMapper mdDepartmentMapper = SpringBeanUtil.getBean(MdDepartmentMapper.class);

        MdDepartment rootDeptInfo = mdDepartmentMapper.selectById(Long.valueOf(rootDeptId));
        deptCache.put(rootDeptInfo.getIfId(),rootDeptInfo);

        dingDeptIds.add(rootDeptId);
        dealWithDept(Long.valueOf(rootDeptId), rootDeptId, unitId,dingDeptIds,deptCache);
        //删除被删除的部门
            /*if (deptIds.size() > 0) {
                for (String deptId : deptIds) {
                    midDeptsMap.remove(deptId);
                }
            }*/
//		if(!midDeptsMap.isEmpty()&&deptIds.size()>0) {//deptIds 必须同步到部门，否则不更新
//			List<MidOrgDepartmentEntity> deletedDepts= new ArrayList<>();
//			for (MidOrgDepartmentEntity Dept : midDeptsMap.values()) {
//				Dept.setIsDeleted(enableTag);
//				deletedDepts.add(Dept);
//				log.info("被删除的部门信息："+JSONUtil.toJSONString(Dept));
//			}
//			if(!deletedDepts.isEmpty()) {
//				dingSyncDao.updateAllMidOrgDepartment(deletedDepts);
//			}
//		}

        //dingSyncDao.saveAllMidOrgDepartment(createMidDepts);
        //dingSyncDao.updateAllMidOrgDepartment(updateMidDepts);

    }


    /**
     * 处理部门信息
     */
    private void dealWithDept(Long parentId, String parentIfId, Long unitId,
                              Set<String> dingDeptIds,Map<String,MdDepartment> deptCache) throws BusinessException {
        Map ifDeptsMap = DingSyncUtil.getSubDeptListByOption(parentIfId);
        if (ifDeptsMap.isEmpty() || ifDeptsMap == null) {
            log.info("deptId=" + parentIfId + "返回的部门列表接口信息为空，跳过");
            return;
        }
        if (ifDeptsMap.get("errcode") != null && (Integer) ifDeptsMap.get("errcode") != 0) {//0是成功
            log.error("获取部门列表失败,deptId=" + parentIfId + ",报错信息：" + ifDeptsMap.get("errmsg"));
            return;
        }

        if (ifDeptsMap.get("result") != null) {
            List<Map<String, Object>> ifDingDeptlist = (List<Map<String, Object>>) ifDeptsMap.get("result");
            //根据上级部门id遍历当前循环到的直属部门列表

            if(ifDingDeptlist==null || ifDingDeptlist.isEmpty()){
                log.info("deptId=" + parentIfId + "部门列表信息为空，跳过");
                return;
            }

            MdDepartmentMapper mdDepartmentMapper = SpringBeanUtil.getBean(MdDepartmentMapper.class);
            MdDepartment toOpt;
            Long recursionParentId;
            String dingDeptId;
            for (Map<String, Object> ifDingDept : ifDingDeptlist) {

                toOpt = null;
                dingDeptId = ifDingDept.get("dept_id").toString();

                dingDeptIds.add(dingDeptId);
                deptCache.put(dingDeptId,toOpt);

                try {

                    Map deptInfoMap = DingSyncUtil.getDeptInfoNewVersion(dingDeptId);
                    if (deptInfoMap.isEmpty() || deptInfoMap == null) {
                        log.error("deptId=" + dingDeptId + " 部门详情未获取到，停止同步");
                        return;
                    }
                    if (deptInfoMap.get("errcode") != null && (Integer) deptInfoMap.get("errcode") != 0) {
                        log.error("获取部门详情报错,eptId=" + dingDeptId + ",报错信息：" + deptInfoMap.get("errmsg"));
                        return;
                    }

                    deptInfoMap = ((JSONObject)deptInfoMap.get("result")).getInnerMap();

                    QueryWrapper<MdDepartment> qw1 = new QueryWrapper<>();
                    qw1.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                    qw1.eq("if_id", dingDeptId);
                    List<MdDepartment> deptList = mdDepartmentMapper.selectList(qw1);


                    if (deptList.size() == 0) {

                        toOpt = new MdDepartment();
                        toOpt.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                        toOpt.setStatus(CommonConstant.STATUS_NORMAL);
                        toOpt.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                        toOpt.setCreatedTime(new Date());
                        toOpt.setIfId(dingDeptId);
                        toOpt.setDeptName(String.valueOf(ifDingDept.get("name")));
                        toOpt.setManageUserList(deptInfoMap.get("dept_manager_userid_list")==null?null:deptInfoMap.get("dept_manager_userid_list").toString());
                        toOpt.setGroupId(groupId);//客开修改
                        toOpt.setUnitId(unitId);
                        toOpt.setParentId(parentId);

                        mdDepartmentMapper.insert(toOpt);
                    } else {
                        toOpt = deptList.get(0);
                        toOpt.setParentId(parentId);
                        toOpt.setDeptName(String.valueOf(ifDingDept.get("name")));
                        toOpt.setManageUserList(deptInfoMap.get("dept_manager_userid_list")==null?null:deptInfoMap.get("dept_manager_userid_list").toString());
                        toOpt.setUpdatedBy(CommonConstant.DEFAULT_OPT_USER);
                        toOpt.setUpdatedTime(new Date());
                        mdDepartmentMapper.updateById(toOpt);
                    }

                    recursionParentId = toOpt.getId();
                    if (recursionParentId.compareTo(-1L) != 0) {
                        deptCache.put(dingDeptId,toOpt);
                        dealWithDept(recursionParentId, toOpt.getIfId(), unitId, dingDeptIds,deptCache);
                    }


                } catch (Exception e) {
                    log.error("dealWithDept error", e);
                }


            }
        }
    }

    private void dealWithPersonInfo(String dingDeptId,Map<String,MdDepartment> deptCache,
                                    Set<String> hasSyncDingUserIds) throws BusinessException {

        if(deptCache.get(dingDeptId)!=null){
            Map ifUserListMap = DingSyncUtil.getUserListByDeptId(dingDeptId);
            if (ifUserListMap.isEmpty() || ifUserListMap == null) {
                log.error("未获取当前部门下的人员列表，deptId=" + dingDeptId);
                return;
            }
            if (ifUserListMap.get("errcode") != null && (Integer) ifUserListMap.get("errcode") != 0) {//0是成功
                log.error("取出部门下人员列表失败：" + ifUserListMap.get("errmsg"));
                return;
            }


            if (ifUserListMap.get("userlist") != null) {
                List<Map<String, Object>> userlist = (List<Map<String, Object>>) ifUserListMap.get("userlist");

                UserInfo toOpt;

                UserInfoMapper userInfoMapper = SpringBeanUtil.getBean(UserInfoMapper.class);

                for (Map<String, Object> user : userlist) {
                    toOpt = null;

                    String ifUserId = user.get("userid").toString();
                    if (hasSyncDingUserIds.contains(ifUserId)) {
                        log.info("已处理过该用户数据，ifUserId:{}，跳过", ifUserId);
                        continue;
                    }
                    hasSyncDingUserIds.add(ifUserId);


                    Map ifUserInfoMap = DingSyncUtil.getUserInfo(ifUserId);
                    if (ifUserInfoMap.isEmpty() || ifUserInfoMap == null) {
                        log.error("未获取人到人员详细数据，userId：{}，deptId:{}", ifUserId, dingDeptId);
                        continue;
                    }

                    try{
                        QueryWrapper<UserInfo> qw1 = new QueryWrapper<>();
                        qw1.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                        qw1.eq("if_id", ifUserId);
                        List<UserInfo> userList = userInfoMapper.selectList(qw1);


                        if (userList.size() == 0) {
                            toOpt = new UserInfo();
                            toOpt.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                            toOpt.setCreatedTime(new Date());
                            toOpt.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                            toOpt.setStatus(CommonConstant.STATUS_NORMAL);
                            toOpt.setIfId(ifUserId);


                        } else {
                            toOpt=userList.get(0);
                            toOpt.setUpdatedTime(new Date());
                            toOpt.setUpdatedBy(CommonConstant.DEFAULT_OPT_USER);
                        }

                        if (ifUserInfoMap.get("position") != null) {
                            toOpt.setPosition(String.valueOf(ifUserInfoMap.get("position")));
                        }

                        toOpt.setUserName(String.valueOf(ifUserInfoMap.get("name")));

                        List roles = (List) ifUserInfoMap.get("roles");
                        log.info("userName:{}.roles:{}", toOpt.getUserName(), roles);

                        if (ifUserInfoMap.get("mobile") != null) {
                            toOpt.setTelephone(String.valueOf(ifUserInfoMap.get("mobile").toString()));
                        }


                        try{
                            if(toOpt.getId()!=null){
                                userInfoMapper.updateById(toOpt);
                            }else{
                                userInfoMapper.insert(toOpt);
                            }
                        }catch (Exception e){
                            log.error("处理用户信息出错,ifUserId:{},userName:{}",e);
                            continue;
                        }



                        //人员部门关系信息处理
                        dealWithPersonDeptInfo(toOpt.getId(), ifUserInfoMap,deptCache);
                    }catch(Exception e){
                        log.error("dealWithPersonInfo error", e);
                    }


                }

            } else {
                log.error("未获取到部门下人员,deptId:{}", dingDeptId);
            }
        }else{
            log.error("同步部门没有成功同步，不对该部门关联用户进行处理");
        }


        
    }

    /**
     * 人员对应部门数据处理
     *
     * @param userId
     * @param ifUserInfoMap
     * @param deptCache
     */
    private void dealWithPersonDeptInfo(Long userId,Map ifUserInfoMap, Map<String,MdDepartment> deptCache) {
        log.info("处理人员部门关系数据开始...");
        if (ifUserInfoMap.get("department") != null) {

            //在对应的部门中的排序，Map结构的json字符串。Key是部门的ID，Value是人员在这个部门的排序值。
            JSONArray ifUserDeptListJa = JSONObject.parseArray(ifUserInfoMap.get("department").toString());


            UserDepartmentMapper userDepartmentMapper = SpringBeanUtil.getBean(UserDepartmentMapper.class);

            List<UserDepartmentDto> dbUserDeptlist = userDepartmentMapper.selectAdditionalInfo(userId);


            //来源数据如果没有数据了，而数据库仍然存在，则删除或变更状态
            Map<String, UserDepartmentDto> existsDbUserDeptToDelete = new HashMap<>();
            if (!dbUserDeptlist.isEmpty()) {
                for (UserDepartmentDto userDepartment : dbUserDeptlist) {

                    existsDbUserDeptToDelete.put(userDepartment.getIfDepartmentId(), userDepartment);
//					if(!orderInDepts.containsKey(midOrgPersonPartEntity.getDepartmentid())) {
//						midOrgPersonPartEntity.setIsDeleted(enableTag);
//						midOrgPersonPartEntity.setIsFinish(enableTag);
//						midOrgPersonPartEntity.setUpdateTime(new Date());
//						updateParts.add(midOrgPersonPartEntity);
//					}
                }
            }


            List<String> ifUserDeptList = ifUserDeptListJa.toJavaList(String.class);

            Set<String> UserDeptIntersectionSet = new HashSet<>();

            for (String deptId : ifUserDeptList) {

                if (existsDbUserDeptToDelete.get(deptId) != null) {
                    existsDbUserDeptToDelete.remove(deptId);//已存在，不需要处理.剩下的需要删除

                    UserDeptIntersectionSet.add(deptId);
                    continue;
                }
                /*if (person.getDepartmentid().equals(deptId)) {
                    continue;
                }*/

            }

            List<Long> toDeleteList = new ArrayList<>();
            for (Map.Entry<String, UserDepartmentDto> toDeleteSingle : existsDbUserDeptToDelete.entrySet()) {
                //删除
                toDeleteList.add(toDeleteSingle.getValue().getId());
            }

            if (!toDeleteList.isEmpty()) {
                userDepartmentMapper.deleteBatchIds(toDeleteList);
            }


            //差集需要新增, 且部门信息存在的
            Set<String> differenceSet = new HashSet<>(ifUserDeptList);
            differenceSet.removeAll(UserDeptIntersectionSet);
            List<UserDepartment> toAddList = new ArrayList<>();
            UserDepartment toAdd;
            for (String ifDeptId : differenceSet) {

                if (deptCache.get(ifDeptId) != null) {
                    toAdd = new UserDepartment();
                    toAdd.setUserId(userId);
                    toAdd.setDepartmentId(deptCache.get(ifDeptId).getId());
                    toAdd.setGroupId(deptCache.get(ifDeptId).getGroupId());
                    toAdd.setUnitId(deptCache.get(ifDeptId).getUnitId());
                    toAdd.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                    toAdd.setStatus(CommonConstant.STATUS_NORMAL);
                    toAdd.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                    toAdd.setCreatedTime(new Date());
                    toAddList.add(toAdd);
                } else {
                    log.error("部门没有顺利同步，或者没有部门信息，不做该用户和部门的关系新增,ifDeptId:{},userId:{}", ifDeptId, userId);
                }

                //createParts.add(midOrgPersonPartEntity);
            }

            if (!toAddList.isEmpty()) {
                try {
                    UserDepartmentService userDepartmentService = SpringBeanUtil.getBean(UserDepartmentService.class);
                    userDepartmentService.saveBatch(toAddList);
                } catch (Exception e) {
                    log.error("saveBatch error", e);
                }
            }


            // dingSyncDao.saveAllMidOrgPersonParties(createParts);
            // dingSyncDao.updateAllMidOrgPersonPart(updateParts);
        }
    }

}
