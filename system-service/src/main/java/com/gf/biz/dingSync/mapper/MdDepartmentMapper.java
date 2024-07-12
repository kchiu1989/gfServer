package com.gf.biz.dingSync.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.dingSync.po.MdDepartment;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:02:35
 */
public interface MdDepartmentMapper extends BaseMapper<MdDepartment> {
    List<MdDepartment> getDepartmentListOfChange();
    List<MdDepartment> selectDeptInfoToSyncCodewave();
}
