package com.gf.biz.crossdb.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.crossdb.entity.UserInfo;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:00:11
 */
@DS(CommonConstant.DATASOURCE_BIZ_1)
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
