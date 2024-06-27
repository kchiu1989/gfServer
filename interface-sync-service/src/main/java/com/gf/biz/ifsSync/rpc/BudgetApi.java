package com.gf.biz.ifsSync.rpc;

import com.gf.biz.ifsSync.entity.PurchaseM;
import com.gf.biz.common.GfResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "system-service",path="/system/test/")
public interface BudgetApi {


    @RequestMapping(value="/updateBudget",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    GfResult<String> updateBudget(@RequestBody PurchaseM purchaseApply);

}
