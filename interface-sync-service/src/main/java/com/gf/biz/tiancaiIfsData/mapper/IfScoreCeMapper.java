package com.gf.biz.tiancaiIfsData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.ifsSync.po.IfScoreEntity;
import com.gf.biz.tiancaiIfsData.entity.IfScoreCe;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IfScoreCeMapper extends BaseMapper<IfScoreCe> {
    @Select("SELECT mc_id,gc_name,mc_name ,AVG(star) as star,count(star) as ceCnt from if_score_ce WHERE name=#{str} GROUP BY mc_name Order by mc_name")
    List<IfScoreEntity> getAverageStar(@Param("str") String str);

}
