package com.gf.biz.elemeData.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.elemeData.entity.IfElemeShopInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-10-09 14:15:02
 */
public interface IfElemeShopInfoMapper extends BaseMapper<IfElemeShopInfo> {
    @Select("SELECT shop_id from if_eleme_shop_info where leaf_flag=#{leafFlag}")
    List<String> getAllShopId(@Param("leafFlag") String leafFlag);

}
