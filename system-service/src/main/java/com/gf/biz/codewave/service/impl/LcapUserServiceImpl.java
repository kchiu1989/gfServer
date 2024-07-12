package com.gf.biz.codewave.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.codewave.mapper.LcapUserMapper;
import com.gf.biz.codewave.po.LcapUser;
import com.gf.biz.codewave.service.LcapUserService;
import com.gf.biz.common.CommonConstant;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 制品应用的用户实体。
 * 1 实体名称不允许改动
 * 2 默认生成的字段不允许改动
 * 3 可新增自定义字段（避免设置为非空且无默认值） 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:12:16
 */
@Service
@DS(CommonConstant.DATASOURCE_BIZ_1)
public class LcapUserServiceImpl extends ServiceImpl<LcapUserMapper, LcapUser> implements LcapUserService {

}
