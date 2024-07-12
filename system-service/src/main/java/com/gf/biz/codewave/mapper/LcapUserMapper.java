package com.gf.biz.codewave.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.codewave.po.LcapUser;
import com.gf.biz.common.CommonConstant;

/**
 * <p>
 * 制品应用的用户实体。
 * 1 实体名称不允许改动
 * 2 默认生成的字段不允许改动
 * 3 可新增自定义字段（避免设置为非空且无默认值） Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:12:16
 */
@DS(CommonConstant.DATASOURCE_BIZ_1)
public interface LcapUserMapper extends BaseMapper<LcapUser> {

}
