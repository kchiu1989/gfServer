package com.gf.biz.meituanwmData.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.meituanwmData.entity.IfMtwmShopData;
import com.gf.biz.meituanwmData.po.IfMtwmshopEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-10-11 10:06:36
 */
    public interface IfMtwmShopDataMapper extends BaseMapper<IfMtwmShopData> {
        @Select("select shop_id,shop_trdpty_id from if_mtwm_shop_data")
        List<IfMtwmshopEntity> getAllShopTrdptyId();

}
