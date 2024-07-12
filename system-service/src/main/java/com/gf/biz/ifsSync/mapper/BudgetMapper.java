package com.gf.biz.ifsSync.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.ifsSync.entity.BudgetM;
import org.apache.ibatis.annotations.Mapper;

@DS(CommonConstant.DATASOURCE_BIZ_1)
public interface BudgetMapper extends BaseMapper<BudgetM> {
}
