package com.gf.biz.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CommonMapper{
    List<Map<String,Object>> queryByDynamicSql(@Param("dynamicSql") String dynamicSql);
}
