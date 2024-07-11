package com.gf.biz.ifsSync.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.ifsSync.entity.PurchaseM;
import org.apache.ibatis.annotations.Mapper;

@DS("biz_1")
public interface PurchaseApplyMapper extends BaseMapper<PurchaseM> {
}
