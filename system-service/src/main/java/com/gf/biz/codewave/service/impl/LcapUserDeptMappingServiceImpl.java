package com.gf.biz.codewave.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gf.biz.codewave.mapper.LcapUserDeptMappingMapper;
import com.gf.biz.codewave.po.LcapUserDeptMapping;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gf.biz.codewave.service.LcapUserDeptMappingService;
import com.gf.biz.common.CommonConstant;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与部门关联实体。操作该表可完成为部门添加用户、移除部门用户等。默认生成的字段不允许改动，可新增自定义字段。 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:41:38
 */
@Service
@DS(CommonConstant.DATASOURCE_BIZ_1)
public class LcapUserDeptMappingServiceImpl extends ServiceImpl<LcapUserDeptMappingMapper, LcapUserDeptMapping> implements LcapUserDeptMappingService {

}
