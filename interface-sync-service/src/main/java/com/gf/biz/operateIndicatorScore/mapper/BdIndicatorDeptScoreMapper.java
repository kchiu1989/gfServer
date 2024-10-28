package com.gf.biz.operateIndicatorScore.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 16:53:16
 */
    public interface BdIndicatorDeptScoreMapper extends BaseMapper<BdIndicatorDeptScore> {
    List<BdIndicatorDeptScoreDto> getOptDeptSumInitialScore(@Param("deptCode") String deptCode, @Param("jobYear") Integer jobYear
            , @Param("jobMonth") Integer jobMonth, @Param("piList")  List<String> piList);
}
