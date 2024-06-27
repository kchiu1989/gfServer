package com.gf.biz.dingSync.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.dingSync.mapper.UserInfoMapper;
import com.gf.biz.dingSync.po.UserInfo;
import com.gf.biz.dingSync.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:00:11
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
