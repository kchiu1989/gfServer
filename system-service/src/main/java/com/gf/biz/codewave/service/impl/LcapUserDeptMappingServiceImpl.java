package com.gf.biz.codewave.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gf.biz.codewave.dto.LcapUserDeptMappingDto;
import com.gf.biz.codewave.mapper.LcapUserDeptMappingDeleteMapper;
import com.gf.biz.codewave.mapper.LcapUserDeptMappingMapper;
import com.gf.biz.codewave.po.LcapUserDelete;
import com.gf.biz.codewave.po.LcapUserDeptMapping;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gf.biz.codewave.po.LcapUserDeptMappingDelete;
import com.gf.biz.codewave.service.LcapUserDeptMappingService;
import com.gf.biz.common.CommonConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户与部门关联实体。操作该表可完成为部门添加用户、移除部门用户等。默认生成的字段不允许改动，可新增自定义字段。 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-07-11 22:41:38
 */
@Service
@DS(CommonConstant.DATASOURCE_BIZ_1)
public class LcapUserDeptMappingServiceImpl extends ServiceImpl<LcapUserDeptMappingMapper, LcapUserDeptMapping> implements LcapUserDeptMappingService {

    private LcapUserDeptMappingMapper lcapUserDeptMappingMapper;
    private LcapUserDeptMappingDeleteMapper lcapUserDeptMappingDeleteMapper;

    public void setLcapUserDeptMappingMapper(LcapUserDeptMappingMapper lcapUserDeptMappingMapper) {
        this.lcapUserDeptMappingMapper = lcapUserDeptMappingMapper;
    }

    public void setLcapUserDeptMappingDeleteMapper(LcapUserDeptMappingDeleteMapper lcapUserDeptMappingDeleteMapper) {
        this.lcapUserDeptMappingDeleteMapper = lcapUserDeptMappingDeleteMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAndSaveHistory(LcapUserDeptMapping lcapUserDeptMapping) {
        LcapUserDeptMappingDelete history = new LcapUserDeptMappingDelete();
        BeanUtils.copyProperties(lcapUserDeptMapping,history);
        lcapUserDeptMappingDeleteMapper.insert(history);
        lcapUserDeptMappingMapper.deleteById(lcapUserDeptMapping.getId());
    }
}
