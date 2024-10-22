package com.gf.biz.operateIndicatorScore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.GfResult;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.operateIndicatorScore.dto.BdIndicatorDeptScoreDto;
import com.gf.biz.operateIndicatorScore.entity.BdIndicatorDeptScore;
import com.gf.biz.operateIndicatorScore.mapper.BdIndicatorDeptScoreMapper;
import com.gf.biz.operateIndicatorScore.service.BdIndicatorDeptScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-10-16 16:53:16
 */
@Service
public class BdIndicatorDeptScoreServiceImpl extends ServiceImpl<BdIndicatorDeptScoreMapper, BdIndicatorDeptScore> implements BdIndicatorDeptScoreService {

    private static final Logger logger = LoggerFactory.getLogger(BdIndicatorDeptScoreServiceImpl.class);
    @Autowired
    private BdIndicatorDeptScoreMapper BdIndicatorDeptScoreMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GfResult<String> createOrAddBdIndicatorDeptScore(BdIndicatorDeptScoreDto bdIndicatorDeptScoreDto) {
        BdIndicatorDeptScoreMapper bdIndicatorDeptScoreMapper = SpringBeanUtil.getBean(BdIndicatorDeptScoreMapper.class);
        QueryWrapper<BdIndicatorDeptScore> queryScoreWrapper = new QueryWrapper<>();
        queryScoreWrapper.eq("dept_id", bdIndicatorDeptScoreDto.getDeptId());
        queryScoreWrapper.eq("year", bdIndicatorDeptScoreDto.getYear());
        queryScoreWrapper.eq("month_quarter", bdIndicatorDeptScoreDto.getMonthQuarter());
        queryScoreWrapper.eq("dimension_flag", bdIndicatorDeptScoreDto.getDimensionFlag());
        queryScoreWrapper.eq("indicator_code", bdIndicatorDeptScoreDto.getIndicatorCode());
        queryScoreWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        //queryScoreWrapper.eq("indicator_name", dept.getDeptCode());
        List<BdIndicatorDeptScore> dbList = bdIndicatorDeptScoreMapper.selectList(queryScoreWrapper);
        try {
            if (dbList != null && dbList.size() > 0) {
                UpdateWrapper<BdIndicatorDeptScore> toUpdScoreWrapper = new UpdateWrapper<>();
                toUpdScoreWrapper.set("final_score", bdIndicatorDeptScoreDto.getFinalScore());
                toUpdScoreWrapper.set("transition_score", bdIndicatorDeptScoreDto.getTransitionScore());
                toUpdScoreWrapper.set("remark", bdIndicatorDeptScoreDto.getRemark());
                toUpdScoreWrapper.set("update_time", new Date());
                toUpdScoreWrapper.eq("id", dbList.get(0).getId());


                bdIndicatorDeptScoreMapper.update(null, toUpdScoreWrapper);
            } else {
                BdIndicatorDeptScore toAdd = new BdIndicatorDeptScore();
                BeanUtils.copyProperties(bdIndicatorDeptScoreDto, toAdd);
                toAdd.setDeletedFlag(CommonConstant.COLUMN_DEL_FLAG);
                toAdd.setCreatedTime(new Date());
                bdIndicatorDeptScoreMapper.insert(toAdd);
            }
        } catch (Exception e) {
            logger.error("处理跑分数据异常", e);
        }

        return null;
    }
}
