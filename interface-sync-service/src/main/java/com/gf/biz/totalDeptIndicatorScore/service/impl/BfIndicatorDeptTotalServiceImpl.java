package com.gf.biz.totalDeptIndicatorScore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.GfResult;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalDto;
import com.gf.biz.totalDeptIndicatorScore.dto.BfIndicatorDeptTotalScoreDto;
import com.gf.biz.totalDeptIndicatorScore.mapper.BfIndicatorDeptTotalMapper;
import com.gf.biz.totalDeptIndicatorScore.mapper.BfIndicatorDeptTotalScoreMapper;
import com.gf.biz.totalDeptIndicatorScore.po.BfIndicatorDeptTotal;
import com.gf.biz.totalDeptIndicatorScore.po.BfIndicatorDeptTotalScore;
import com.gf.biz.totalDeptIndicatorScore.service.BfIndicatorDeptTotalService;
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
 * @since 2024-10-29 16:29:41
 */

@Service
public class BfIndicatorDeptTotalServiceImpl extends ServiceImpl<BfIndicatorDeptTotalMapper, BfIndicatorDeptTotal> implements BfIndicatorDeptTotalService {
    private static final Logger logger = LoggerFactory.getLogger(BfIndicatorDeptTotalServiceImpl.class);
    @Autowired
    private BfIndicatorDeptTotalScoreMapper bfIndicatorDeptTotalScoreMapper;
    @Autowired
    private BfIndicatorDeptTotalMapper bfIndicatorDeptTotalMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GfResult<String> createOrUpdateData(BfIndicatorDeptTotalDto bfIndicatorDeptTotalDto) {

        QueryWrapper<BfIndicatorDeptTotal> queryTotalWrapper = new QueryWrapper<>();
        queryTotalWrapper.eq("year", bfIndicatorDeptTotalDto.getYear());
        queryTotalWrapper.eq("month_quarter", bfIndicatorDeptTotalDto.getMonthQuarter());
        queryTotalWrapper.eq("dimension_flag", bfIndicatorDeptTotalDto.getDimensionFlag());
        queryTotalWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
        //queryScoreWrapper.eq("indicator_name", dept.getDeptCode());
        List<BfIndicatorDeptTotal> dbTotalList = bfIndicatorDeptTotalMapper.selectList(queryTotalWrapper);


        Long masterId = null;
        if (dbTotalList != null && dbTotalList.size() > 0) {
            //if(!"2".equals(dbList.get(0).getStatus()) {
            UpdateWrapper<BfIndicatorDeptTotal> toUpdTotalWrapper = new UpdateWrapper<>();

            toUpdTotalWrapper.set("updated_time", new Date());
            toUpdTotalWrapper.eq("id", dbTotalList.get(0).getId());


            bfIndicatorDeptTotalMapper.update(null, toUpdTotalWrapper);
            //}else{
            //logger.info("数据用户已确认，不做更新");
            //}
            masterId = dbTotalList.get(0).getId();

        } else {
            BfIndicatorDeptTotal toAdd = new BfIndicatorDeptTotal();
            BeanUtils.copyProperties(bfIndicatorDeptTotalDto, toAdd);
            toAdd.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
            toAdd.setCreatedTime(new Date());
            //toAdd.setStatus("1");
            bfIndicatorDeptTotalMapper.insert(toAdd);
            masterId =toAdd.getId();
        }

        //更新明细
        if(masterId != null){
            List<BfIndicatorDeptTotalScoreDto> itemList = bfIndicatorDeptTotalDto.getItemList();
            QueryWrapper<BfIndicatorDeptTotalScore> queryScoreWrapper = new QueryWrapper<>();
            for(BfIndicatorDeptTotalScoreDto bfIndicatorDeptTotalScoreDto : itemList) {
                queryScoreWrapper.clear();
                queryScoreWrapper.eq("dept_id", bfIndicatorDeptTotalScoreDto.getDeptId());
                queryScoreWrapper.eq("year", bfIndicatorDeptTotalScoreDto.getYear());
                queryScoreWrapper.eq("month_quarter", bfIndicatorDeptTotalScoreDto.getMonthQuarter());
                queryScoreWrapper.eq("dimension_flag", bfIndicatorDeptTotalScoreDto.getDimensionFlag());
                queryScoreWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                //queryScoreWrapper.eq("indicator_name", dept.getDeptCode());
                List<BfIndicatorDeptTotalScore> dbList = bfIndicatorDeptTotalScoreMapper.selectList(queryScoreWrapper);

                if (dbList != null && dbList.size() > 0) {
                    //if(!"2".equals(dbList.get(0).getStatus()) {
                    UpdateWrapper<BfIndicatorDeptTotalScore> toUpdScoreWrapper = new UpdateWrapper<>();
                    toUpdScoreWrapper.set("final_score", bfIndicatorDeptTotalScoreDto.getFinalScore());
                    toUpdScoreWrapper.set("final_rank", bfIndicatorDeptTotalScoreDto.getFinalRank());
                    toUpdScoreWrapper.set("transition_score", bfIndicatorDeptTotalScoreDto.getTransitionScore());
                    toUpdScoreWrapper.set("transition_rank", bfIndicatorDeptTotalScoreDto.getTransitionRank());
                    toUpdScoreWrapper.set("transition_deduct_score", bfIndicatorDeptTotalScoreDto.getTransitionDeductScore());
                    toUpdScoreWrapper.set("transition_rank_number", bfIndicatorDeptTotalScoreDto.getTransitionRankNumber());
                    toUpdScoreWrapper.set("transition_food_rank_number", bfIndicatorDeptTotalScoreDto.getTransitionFoodRankNumber());
                    toUpdScoreWrapper.set("remark", bfIndicatorDeptTotalScoreDto.getRemark());
                    toUpdScoreWrapper.set("updated_time", new Date());
                    toUpdScoreWrapper.eq("id", dbList.get(0).getId());


                    bfIndicatorDeptTotalScoreMapper.update(null, toUpdScoreWrapper);
                    //}else{
                    //logger.info("数据用户已确认，不做更新");
                    //}


                } else {
                    BfIndicatorDeptTotalScore toAdd = new BfIndicatorDeptTotalScore();
                    BeanUtils.copyProperties(bfIndicatorDeptTotalScoreDto, toAdd);
                    toAdd.setMasterId(masterId);
                    toAdd.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                    toAdd.setCreatedTime(new Date());
                    toAdd.setStatus("1");
                    bfIndicatorDeptTotalScoreMapper.insert(toAdd);
                }
            }
        }else{
            logger.error("关联主表id为空，不做明细处理");
        }

        return null;
    }
}
