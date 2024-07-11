package com.gf.biz.dingSync.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.gf.biz.dingSync.dto.UserDepartmentDto;
import com.gf.biz.dingSync.po.UserDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:01:30
 */
public interface UserDepartmentMapper extends BaseMapper<UserDepartment> {

    List<UserDepartmentDto> selectAdditionalInfo(@Param("userId") Long userId);
    List<UserDepartmentDto> selectLeaderListByDeptId(@Param("deptId") Long deptId);

}
