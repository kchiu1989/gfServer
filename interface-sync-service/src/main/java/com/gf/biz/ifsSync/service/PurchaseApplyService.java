package com.gf.biz.ifsSync.service;


import com.gf.biz.common.GfResult;

public interface PurchaseApplyService {
    /**
     * 根据申请单Id更新预算
     * @param applyId
     * @return
     */
    GfResult<String> updateBudgetAmount(String applyId);
}
