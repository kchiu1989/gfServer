package com.gf.biz.dingSync.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.dingSync.po.UserInfo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:00:11
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    int batchUpdateDirectLeaderId();
}
