package com.gf.biz.ifsSync.controller.purchase;

import com.alibaba.nacos.common.utils.StringUtils;
import com.gf.biz.common.GfResult;
import com.gf.biz.ifsSync.entity.PurchaseM;
import com.gf.biz.ifsSync.service.PurchaseApplyService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 采购申请
 */
@RestController("purchaseApplyController")
@RequestMapping("/lvshi/purchaseApply")
public class PurchaseApplyController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseApplyController.class);

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    /**
     *
     * @return
     */
    @RequestMapping(value="updateBudget",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GfResult<String> updateBudget(@RequestBody PurchaseM purchaseApply, @RequestParam("xId") String xId){
        logger.info("xId:{}",xId);
        if(StringUtils.isNotBlank(xId)){
            if(!RootContext.inGlobalTransaction()){
                RootContext.bind(xId);
            }
        }
        logger.info(purchaseApply.toString());
        return purchaseApplyService.updateBudgetAmount(String.valueOf(purchaseApply.getId()));
    }

}
