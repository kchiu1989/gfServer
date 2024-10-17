package com.gf.biz.fangdengRead.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.fangdengRead.entity.IfRecordFdNm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 樊登笔记管理表 Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-10-14 12:00:49
 */
public interface IfRecordFdNmMapper extends BaseMapper<IfRecordFdNm> {
    List<Map<String,Object>> selectNmDataMapListByMonth(@Param("year") Integer year, @Param("month") Integer month);
}
