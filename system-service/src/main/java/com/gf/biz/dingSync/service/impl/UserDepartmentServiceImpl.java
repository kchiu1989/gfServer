package com.gf.biz.dingSync.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.dingSync.dto.UserDepartmentDto;
import com.gf.biz.dingSync.mapper.UserDepartmentMapper;
import com.gf.biz.dingSync.mapper.UserInfoMapper;
import com.gf.biz.dingSync.po.MdDepartment;
import com.gf.biz.dingSync.po.UserDepartment;
import com.gf.biz.dingSync.po.UserInfo;
import com.gf.biz.dingSync.service.UserDepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:01:30
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements UserDepartmentService {
    private final static Logger log = LoggerFactory.getLogger(UserDepartmentServiceImpl.class);

    private UserInfoMapper userInfoMapper;

    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateManageUser(MdDepartment toOptDept, List<String> manageIfUserList) {

        //将该部门的部门领导人查询出来
        List<UserDepartmentDto> leaderFlagList=this.getBaseMapper().selectLeaderListByDeptId(toOptDept.getId());

        if(!leaderFlagList.isEmpty()){
            Iterator<UserDepartmentDto> leaderFlagListItr=leaderFlagList.iterator();
            Iterator<String> manageIfUserListItr=manageIfUserList.iterator();
            while (leaderFlagListItr.hasNext()) {
                UserDepartmentDto leaderFlagData = leaderFlagListItr.next();
                while (manageIfUserListItr.hasNext()) {
                    String ifUserId = manageIfUserListItr.next();
                    if(ifUserId.equals(leaderFlagData.getIfUserId())) {
                        log.info("ifUserId:{}，部门Id:{},不需要更新主管标识",leaderFlagData.getIfUserId(), leaderFlagData.getDepartmentId());
                        leaderFlagListItr.remove();
                        manageIfUserListItr.remove();
                    }
                }
            }
        }

        if(!leaderFlagList.isEmpty()){

            List<Long> idList = new ArrayList<>();
            for(UserDepartmentDto leaderFlagData : leaderFlagList){
                idList.add(leaderFlagData.getId());
            }

            //更新为非主管标识
            UpdateWrapper<UserDepartment> updateWrapper=new UpdateWrapper<>();
            updateWrapper.in("id",idList);
            UserDepartment toUpd = new  UserDepartment();
            toUpd.setUpdatedTime(new Date());
            toUpd.setUpdatedBy("SYS");
            toUpd.setDeptLeaderFlag("0");
            getBaseMapper().update(toUpd,updateWrapper);
        }

        //更新为主管
        if(!manageIfUserList.isEmpty()){
            //根据ifUserId查询出user_info的id
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.eq(CommonConstant.COLUMN_DEL_FLAG,'0');
            userInfoQueryWrapper.in("if_id",manageIfUserList);
            List<UserInfo> userList = userInfoMapper.selectList(userInfoQueryWrapper);
            if(!userList.isEmpty()){
                List<Long> userIdList = new ArrayList<>();
                for(UserInfo userInfo : userList){
                    userIdList.add(userInfo.getId());
                }

                UpdateWrapper<UserDepartment> updateWrapper=new UpdateWrapper<>();
                updateWrapper.in("user_id",userIdList);
                updateWrapper.eq("department_id",toOptDept.getId());
                UserDepartment toUpd = new UserDepartment();
                toUpd.setUpdatedTime(new Date());
                toUpd.setUpdatedBy("SYS");
                toUpd.setDeptLeaderFlag("1");
                getBaseMapper().update(toUpd,updateWrapper);
            }
        }

    }
}
