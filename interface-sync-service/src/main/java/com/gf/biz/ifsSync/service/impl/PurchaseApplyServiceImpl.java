package com.gf.biz.ifsSync.service.impl;


import com.gf.biz.ifsSync.mapper.PurchaseApplyMapper;
import com.gf.biz.ifsSync.rpc.BudgetApi;
import com.gf.biz.common.GfResult;
import com.gf.biz.ifsSync.entity.PurchaseM;
import com.gf.biz.ifsSync.service.PurchaseApplyService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor =Exception.class)
@Service("purchaseApplyService")
public class PurchaseApplyServiceImpl implements PurchaseApplyService {


    @Autowired
    private PurchaseApplyMapper purchaseApplyMapper;
    @Autowired
    private BudgetApi budgetApi;


    @GlobalTransactional(rollbackFor =Exception.class)
    @Override
    public GfResult<String> updateBudgetAmount(String applyId) {

        if(StringUtils.isBlank(applyId)){
            return GfResult.failed("未查询到单据ID");
        }

        PurchaseM currentApply = purchaseApplyMapper.selectById(applyId);
        if(currentApply==null){
            return GfResult.failed("未查询到单据");
        }

        //更新采购单状态
        currentApply.setProcessStatus("TESTING");
        int updCnt=purchaseApplyMapper.updateById(currentApply);

        budgetApi.updateBudget(currentApply);
        int b =1/0;
        return GfResult.success("更新成功");
    }
}
