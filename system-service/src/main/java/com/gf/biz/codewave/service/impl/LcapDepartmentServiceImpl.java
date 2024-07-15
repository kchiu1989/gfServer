package com.gf.biz.codewave.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.codewave.mapper.LcapDepartmentDeleteMapper;
import com.gf.biz.codewave.mapper.LcapDepartmentMapper;
import com.gf.biz.codewave.po.LcapDepartment;
import com.gf.biz.codewave.po.LcapDepartmentDelete;
import com.gf.biz.codewave.service.LcapDepartmentService;
import com.gf.biz.common.CommonConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:24:20
 */
@Service
@DS(CommonConstant.DATASOURCE_BIZ_1)
public class LcapDepartmentServiceImpl extends ServiceImpl<LcapDepartmentMapper, LcapDepartment> implements LcapDepartmentService {
    private LcapDepartmentMapper lcapDepartmentMapper;
    private LcapDepartmentDeleteMapper lcapDepartmentDeleteMapper;
    @Autowired
    public void setLcapDepartmentMapper(LcapDepartmentMapper lcapDepartmentMapper) {
        this.lcapDepartmentMapper = lcapDepartmentMapper;
    }
    @Autowired
    public void setLcapDepartmentDeleteMapper(LcapDepartmentDeleteMapper lcapDepartmentDeleteMapper) {
        this.lcapDepartmentDeleteMapper = lcapDepartmentDeleteMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAndSaveHistory(LcapDepartment dbDept) {
        LcapDepartmentDelete history = new LcapDepartmentDelete();
        BeanUtils.copyProperties(dbDept,history);
        lcapDepartmentDeleteMapper.insert(history);
        lcapDepartmentMapper.deleteById(dbDept.getId());
    }



}
