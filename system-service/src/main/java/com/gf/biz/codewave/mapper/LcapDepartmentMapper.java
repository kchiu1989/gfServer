package com.gf.biz.codewave.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.codewave.po.LcapDepartment;

/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。 Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:24:20
 */
@DS("biz_1")
    public interface LcapDepartmentMapper extends BaseMapper<LcapDepartment> {

}
