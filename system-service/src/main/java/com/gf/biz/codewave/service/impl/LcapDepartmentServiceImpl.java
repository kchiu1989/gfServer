package com.gf.biz.codewave.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gf.biz.codewave.mapper.LcapDepartmentMapper;
import com.gf.biz.codewave.po.LcapDepartment;
import com.gf.biz.codewave.service.LcapDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:24:20
 */
@Service
@DS("biz_1")
public class LcapDepartmentServiceImpl extends ServiceImpl<LcapDepartmentMapper, LcapDepartment> implements LcapDepartmentService {

}
