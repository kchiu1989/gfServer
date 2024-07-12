package com.gf.biz.ifsSync.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.ifsSync.entity.BudgetM;
import com.gf.biz.ifsSync.mapper.BudgetMapper;
import com.gf.biz.ifsSync.mapper.PurchaseApplyMapper;

import com.gf.biz.common.GfResult;
import com.gf.biz.ifsSync.entity.PurchaseM;
import com.gf.biz.ifsSync.service.PurchaseApplyService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseApplyService")
@DS(CommonConstant.DATASOURCE_BIZ_1)
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    @Autowired
    private BudgetMapper budgetMapper;
    @Autowired
    private PurchaseApplyMapper purchaseApplyMapper;


    @Transactional(rollbackFor =Exception.class)
    @Override
    public GfResult<String> updateBudgetAmount(String applyId) {

        if(StringUtils.isBlank(applyId)){
            return GfResult.failed("未查询到单据ID");
        }

        PurchaseM currentApply = purchaseApplyMapper.selectById(applyId);
        if(currentApply==null){
            return GfResult.failed("未查询到单据");
        }

        //开始占用预算
        BudgetM currentBudget = budgetMapper.selectById(1);
        if(currentBudget==null){
            return GfResult.failed("未查询到预算数据");
        }

        if(currentBudget.getBudgetAmt().compareTo(currentApply.getAmount())==-1){
            return GfResult.failed("预算不够");
        }

        BudgetM toUpd = new BudgetM();
        toUpd.setId(currentBudget.getId());
        toUpd.setBudgetAmt(currentBudget.getBudgetAmt().subtract(currentApply.getAmount()));
        int updCnt=budgetMapper.updateById(toUpd);

        return GfResult.success("更新成功");
    }
}
