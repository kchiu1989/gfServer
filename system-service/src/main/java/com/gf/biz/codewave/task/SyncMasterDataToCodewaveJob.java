package com.gf.biz.codewave.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.codewave.mapper.LcapDepartmentMapper;
import com.gf.biz.codewave.mapper.LcapUserDeptMappingMapper;
import com.gf.biz.codewave.mapper.LcapUserMapper;
import com.gf.biz.codewave.po.LcapDepartment;
import com.gf.biz.codewave.po.LcapUser;
import com.gf.biz.codewave.po.LcapUserDeptMapping;
import com.gf.biz.codewave.service.LcapDepartmentService;
import com.gf.biz.codewave.service.LcapUserDeptMappingService;
import com.gf.biz.codewave.service.LcapUserService;
import com.gf.biz.codewave.util.BCryptHelper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.dingSync.dto.UserDepartmentDto;
import com.gf.biz.dingSync.mapper.MdDepartmentMapper;
import com.gf.biz.dingSync.mapper.UserDepartmentMapper;
import com.gf.biz.dingSync.mapper.UserInfoMapper;
import com.gf.biz.dingSync.po.MdDepartment;
import com.gf.biz.dingSync.po.UserInfo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步主数据系统的用户、部门、用户部门关系到codewave的表
 */
public class SyncMasterDataToCodewaveJob extends IJobHandler {

    private final static Logger log = LoggerFactory.getLogger(SyncMasterDataToCodewaveJob.class);

    @Override
    public void execute() throws Exception {
        //获取当天新增或者更新的数据


        UserInfoMapper userInfoMapper = SpringBeanUtil.getBean(UserInfoMapper.class);

        List<UserInfo> toOptUserList = userInfoMapper.selectUserInfoToSyncCodewave();

        LcapUserMapper lcapUserMapper = SpringBeanUtil.getBean(LcapUserMapper.class);

        if (!toOptUserList.isEmpty()) {

            LcapUserService lcapUserService = SpringBeanUtil.getBean(LcapUserService.class);

            XxlJobHelper.log("开始同步用户主数据到Codewave...");

            QueryWrapper<LcapUser> queryLcapUserWrapper;
            UpdateWrapper<LcapUser> updateLcapUserWrapper;
            LcapUser lcapUserToAdd;

            Date currentDate = new Date();

            for (UserInfo userInfo : toOptUserList) {
                try {
                    XxlJobHelper.log("userId:{},userAccount:{}", userInfo.getId(), userInfo.getUserAccount());
                    //先根据ifId查询lcap_user是否存在，若不存在，新增数据；否则更新数据
                    queryLcapUserWrapper = new QueryWrapper<>();
                    queryLcapUserWrapper.eq("if_id", userInfo.getId());
                    List<LcapUser> lcapUserList = lcapUserMapper.selectList(queryLcapUserWrapper);

                    if (!lcapUserList.isEmpty()) {
                        //删除直接物理删除,迁移到历史表
                        LcapUser dbLcapUser = lcapUserList.get(0);
                        if (CommonConstant.STATUS_DEL.equals(userInfo.getDeletedFlag())) {
                            XxlJobHelper.log("userId:{},userAccount:{},物理删除", userInfo.getId(), userInfo.getUserAccount());
                            lcapUserService.deleteAndSaveHistory(dbLcapUser);
                            continue;
                        }
                        //直属领导变更，账号、手机号不做调整
                        if (dbLcapUser.getIfDirectLeaderId() != null && userInfo.getDirectLeaderId() != null
                                && dbLcapUser.getIfDirectLeaderId().compareTo(userInfo.getDirectLeaderId()) != 0) {
                            XxlJobHelper.log("userId:{},userAccount:{},更改直属领导", userInfo.getId(), userInfo.getUserAccount());
                            updateLcapUserWrapper = new UpdateWrapper<>();
                            updateLcapUserWrapper.set("if_direct_leader_id", userInfo.getDirectLeaderId());
                            updateLcapUserWrapper.set("direct_leader_id", null);
                            updateLcapUserWrapper.set("updated_time", currentDate);
                            updateLcapUserWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                            updateLcapUserWrapper.eq(CommonConstant.COLUMN_ID, dbLcapUser.getId());
                            lcapUserMapper.update(null, updateLcapUserWrapper);

                        }

                    } else {
                        if (CommonConstant.STATUS_DEL.equals(userInfo.getDeletedFlag())) {
                            XxlJobHelper.log("userId:{},userAccount:{},主数据已删除，无需新增", userInfo.getId(), userInfo.getUserAccount());
                            continue;
                        }

                        //新增用户
                        XxlJobHelper.log("userId:{},userAccount:{},新增用户", userInfo.getId(), userInfo.getUserAccount());
                        lcapUserToAdd = new LcapUser();
                        lcapUserToAdd.setIfId(userInfo.getId());
                        lcapUserToAdd.setCreatedTime(currentDate);
                        lcapUserToAdd.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                        lcapUserToAdd.setUserName(userInfo.getUserAccount());

                        lcapUserToAdd.setPhone(userInfo.getTelephone());
                        //lcapUserToAdd.setEmail();
                        lcapUserToAdd.setDisplayName(userInfo.getUserName());
                        lcapUserToAdd.setStatus("Normal");
                        lcapUserToAdd.setSource("Normal");
                        lcapUserToAdd.setUserId(genCodewaveUserId(lcapUserToAdd.getUserName(), lcapUserToAdd.getSource()));
                        lcapUserToAdd.setPassword(BCryptHelper.hashpw(lcapUserToAdd.getUserName() + "123", BCryptHelper.gensalt()));
                        lcapUserToAdd.setIfDirectLeaderId(userInfo.getDirectLeaderId());
                        lcapUserMapper.insert(lcapUserToAdd);

                    }


                } catch (Exception e) {
                    log.error("处理用户信息异常", e);
                    XxlJobHelper.log(e);
                }
            }

            //进行一次同步直属领导的批量更新
            lcapUserMapper.updateDirectUser();
        }


        //获取当天新增或者更新的数据
        MdDepartmentMapper mdDepartmentMapper = SpringBeanUtil.getBean(MdDepartmentMapper.class);


        //处理部门
        List<MdDepartment> toOptDeptList = mdDepartmentMapper.selectDeptInfoToSyncCodewave();

        LcapDepartmentMapper lcapDepartmentMapper = SpringBeanUtil.getBean(LcapDepartmentMapper.class);

        if (!toOptDeptList.isEmpty()) {


            LcapDepartmentService lcapDepartmentService = SpringBeanUtil.getBean(LcapDepartmentService.class);

            XxlJobHelper.log("开始同步部门主数据到Codewave...");

            QueryWrapper<LcapDepartment> queryLcapDeptWrapper;
            UpdateWrapper<LcapDepartment> updateLcapDeptWrapper;
            LcapDepartment lcapDeptToAdd;

            Date currentDate = new Date();

            for (MdDepartment deptInfo : toOptDeptList) {
                try {
                    XxlJobHelper.log("deptId:{},deptIdentity:{}", deptInfo.getId(), deptInfo.getDeptIdentity());
                    //先根据ifId查询lcap_user是否存在，若不存在，新增数据；否则更新数据
                    queryLcapDeptWrapper = new QueryWrapper<>();
                    queryLcapDeptWrapper.eq("if_id", deptInfo.getId());
                    List<LcapDepartment> lcapDeptList = lcapDepartmentMapper.selectList(queryLcapDeptWrapper);

                    if (!lcapDeptList.isEmpty()) {
                        //删除直接物理删除,迁移到历史表
                        LcapDepartment dbLcapDept = lcapDeptList.get(0);
                        if (CommonConstant.STATUS_DEL.equals(deptInfo.getDeletedFlag())) {
                            XxlJobHelper.log("deptId:{},deptIdentity:{},物理删除", deptInfo.getId(), deptInfo.getDeptIdentity());
                            lcapDepartmentService.deleteAndSaveHistory(dbLcapDept);
                            continue;
                        }
                        //更改部门所属上级关系
                        if (dbLcapDept.getIfParentId() != null && deptInfo.getParentId() != null
                                && dbLcapDept.getIfParentId().compareTo(deptInfo.getParentId()) != 0) {
                            XxlJobHelper.log("deptId:{},deptIdentity:{},更改上级部门关系", deptInfo.getId(), deptInfo.getDeptIdentity());
                            updateLcapDeptWrapper = new UpdateWrapper<>();
                            updateLcapDeptWrapper.set("if_parent_id", deptInfo.getParentId());
                            updateLcapDeptWrapper.set("parent_id", null);
                            updateLcapDeptWrapper.set("updated_time", currentDate);
                            updateLcapDeptWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                            updateLcapDeptWrapper.eq(CommonConstant.COLUMN_ID, dbLcapDept.getId());
                            lcapDepartmentMapper.update(null, updateLcapDeptWrapper);

                        }

                        //更改部门的名称
                        if (dbLcapDept.getName() != null && deptInfo.getName() != null
                                && !dbLcapDept.getName().equals(deptInfo.getName())) {
                            XxlJobHelper.log("deptId:{},deptIdentity:{},更改部门名称，标识不改变", deptInfo.getId(), deptInfo.getDeptIdentity());
                            updateLcapDeptWrapper = new UpdateWrapper<>();
                            updateLcapDeptWrapper.set("name", deptInfo.getName());
                            updateLcapDeptWrapper.set("updated_time", currentDate);
                            updateLcapDeptWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                            updateLcapDeptWrapper.eq(CommonConstant.COLUMN_ID, dbLcapDept.getId());
                            lcapDepartmentMapper.update(null, updateLcapDeptWrapper);
                        }

                    } else {
                        //新增用户
                        XxlJobHelper.log("deptId:{},deptIdentity:{},新增用户", deptInfo.getId(), deptInfo.getDeptIdentity());

                        if (CommonConstant.STATUS_DEL.equals(deptInfo.getDeletedFlag())) {
                            XxlJobHelper.log("deptId:{},deptIdentity:{},主数据已删除，无需新增", deptInfo.getId(), deptInfo.getDeptIdentity());
                            continue;
                        }


                        lcapDeptToAdd = new LcapDepartment();
                        lcapDeptToAdd.setCreatedTime(currentDate);
                        lcapDeptToAdd.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                        lcapDeptToAdd.setIfId(deptInfo.getId());
                        lcapDeptToAdd.setName(deptInfo.getName());

                        lcapDeptToAdd.setDeptId(deptInfo.getDeptIdentity());
                        lcapDeptToAdd.setIfParentId(deptInfo.getParentId());

                        lcapDepartmentMapper.insert(lcapDeptToAdd);

                    }


                } catch (Exception e) {
                    log.error("处理部门信息异常", e);
                    XxlJobHelper.log(e);
                }
            }

            //进行一次同步直属领导的批量更新
            lcapDepartmentMapper.updateParentDept();
        }

        //只处理主数据的部门和人员关系，其他不动；且部门主管按照主数据系统的来
        UserDepartmentMapper userDepartmentMapper = SpringBeanUtil.getBean(UserDepartmentMapper.class);

        List<UserDepartmentDto> toOptUserDeptList = userDepartmentMapper.selectUserDeptInfoToSyncCodewave();
        if (!toOptUserDeptList.isEmpty()) {

            LcapUserDeptMappingMapper lcapUserDeptMappingMapper = SpringBeanUtil.getBean(LcapUserDeptMappingMapper.class);

            LcapUserDeptMappingService lcapUserDeptMappingService = SpringBeanUtil.getBean(LcapUserDeptMappingService.class);

            XxlJobHelper.log("开始同步用户部门关系主数据到Codewave...");

            Date currentDate = new Date();

            for (UserDepartmentDto userDeptInfo : toOptUserDeptList) {
                try {
                    XxlJobHelper.log("deptId:{},userId:{}", userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                    //先根据ifId查询lcap_user是否存在，若不存在，新增数据；否则更新数据

                    List<LcapUserDeptMapping> lcapUserDeptList = lcapUserDeptMappingMapper.selectByIfUserIdAndIfDeptId(userDeptInfo.getUserId(), userDeptInfo.getDepartmentId());

                    if (!lcapUserDeptList.isEmpty()) {
                        //删除直接物理删除,迁移到历史表
                        LcapUserDeptMapping dbLcapUserDept = lcapUserDeptList.get(0);
                        if (CommonConstant.STATUS_DEL.equals(userDeptInfo.getDeletedFlag())) {
                            XxlJobHelper.log("deptId:{},userId:{},物理删除", userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                            lcapUserDeptMappingService.deleteAndSaveHistory(dbLcapUserDept);
                            continue;
                        }

                        if (userDeptInfo.getDeptLeaderFlag() == null && dbLcapUserDept.getIsDeptLeader() != null ||
                                userDeptInfo.getDeptLeaderFlag() != null && dbLcapUserDept.getIsDeptLeader() != null
                                        && !userDeptInfo.getDeptLeaderFlag().equals(dbLcapUserDept.getIsDeptLeader().toString())
                                || userDeptInfo.getDeptLeaderFlag() != null && dbLcapUserDept.getIsDeptLeader() == null) {
                            UpdateWrapper<LcapUserDeptMapping> updateWrapper = new UpdateWrapper<>();
                            updateWrapper.set("is_dept_leader", userDeptInfo.getDeptLeaderFlag() == null ? null : Long.valueOf(userDeptInfo.getDeptLeaderFlag()));
                            updateWrapper.set("updated_time", currentDate);
                            updateWrapper.set("updated_by", CommonConstant.DEFAULT_OPT_USER);
                            updateWrapper.eq(CommonConstant.COLUMN_ID, dbLcapUserDept.getId());
                            lcapUserDeptMappingMapper.update(null, updateWrapper);
                            continue;
                        }

                        XxlJobHelper.log("deptId:{},userId:{},不做更新", userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                    } else {

                        if (CommonConstant.STATUS_DEL.equals(userDeptInfo.getDeletedFlag())) {
                            XxlJobHelper.log("deptId:{},userId:{},主数据已删除，不做新增", userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                            continue;
                        }

                        //新增用户
                        XxlJobHelper.log("deptId:{},userId:{},新增关系", userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                        // lcapUserDeptList

                        //先根据ifUserId查询lcapUser的userId
                        QueryWrapper<LcapUser> queryLcapUserWrapper = new QueryWrapper<>();
                        queryLcapUserWrapper.select("user_id");
                        queryLcapUserWrapper.eq("if_id", userDeptInfo.getUserId());
                        List<Object> userIdList = lcapUserMapper.selectObjs(queryLcapUserWrapper);
                        if (userIdList == null || userIdList.isEmpty()) {
                            XxlJobHelper.log("deptId:{},userId:{},查询不到用户信息的userId，不做新增用户部门关系",
                                    userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                            continue;
                        }

                        //先根据ifDeptId查询lcapDepartment的deptId
                        QueryWrapper<LcapDepartment> queryLcapDeptWrapper = new QueryWrapper<>();
                        queryLcapDeptWrapper.select("dept_id");
                        queryLcapDeptWrapper.eq("if_id", userDeptInfo.getDepartmentId());
                        List<Object> deptIdList = lcapDepartmentMapper.selectObjs(queryLcapDeptWrapper);


                        if (deptIdList == null || deptIdList.isEmpty()) {
                            XxlJobHelper.log("deptId:{},userId:{},查询不到部门信息的deptId，不做新增用户部门关系",
                                    userDeptInfo.getDepartmentId(), userDeptInfo.getUserId());
                            continue;
                        }


                        Map<String, Object> insertParams = new HashMap<>();
                        insertParams.put("userId", String.valueOf(userIdList.get(0)));
                        insertParams.put("deptId", String.valueOf(deptIdList.get(0)));
                        insertParams.put("isDeptLeader", userDeptInfo.getDeptLeaderFlag() == null ? null : Long.valueOf(userDeptInfo.getDeptLeaderFlag()));
                        lcapUserDeptMappingMapper.insertLcapUserDeptNative(insertParams);
                    }

                } catch (Exception e) {
                    log.error("处理部门信息异常", e);
                    XxlJobHelper.log(e);
                }

            }
        }
    }


    /**
     * 生成codewave用户唯一标识
     *
     * @param userAccount 账号名
     * @param source      认证源  Normal
     * @return
     */
    private String genCodewaveUserId(String userAccount, String source) {
        return DigestUtils.md5DigestAsHex((userAccount + source).getBytes());

    }
}
