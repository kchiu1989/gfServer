package com.gf.biz.codewave.task;

import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.dingSync.mapper.UserInfoMapper;
import com.xxl.job.core.handler.IJobHandler;

/**
 * 同步主数据系统的用户、部门、用户部门关系到codewave的表
 */
public class SyncMasterDataToCodewaveJob extends IJobHandler {
    @Override
    public void execute() throws Exception {
        //获取当天新增或者更新的数据
        UserInfoMapper userInfoMapper = SpringBeanUtil.getBean(UserInfoMapper.class);
        //userInfoMapper.
    }
}
