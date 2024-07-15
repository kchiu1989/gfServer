package com.gf.biz.codewave.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.codewave.dto.LcapUserDeptMappingDto;
import com.gf.biz.codewave.po.LcapUserDeptMapping;
import com.gf.biz.common.CommonConstant;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户与部门关联实体。操作该表可完成为部门添加用户、移除部门用户等。默认生成的字段不允许改动，可新增自定义字段。 Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:41:38
 */
@DS(CommonConstant.DATASOURCE_BIZ_1)
public interface LcapUserDeptMappingMapper extends BaseMapper<LcapUserDeptMapping> {
    List<LcapUserDeptMapping> selectByIfUserIdAndIfDeptId(@Param("ifUserId") Long ifUserId, @Param("ifDeptId") Long ifDeptId);
    int insertLcapUserDeptNative(@Param("insertParams") Map<String,Object> insertParams);
}
