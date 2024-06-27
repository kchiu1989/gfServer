package com.gf.biz.dingSync.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.dingSync.mapper.UserDepartmentMapper;
import com.gf.biz.dingSync.po.UserDepartment;
import com.gf.biz.dingSync.service.UserDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:01:30
 */
@Slf4j
@Service
@DS("biz_1")
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements UserDepartmentService {

}
