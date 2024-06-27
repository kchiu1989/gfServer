package com.gf.biz.ifsSync.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.gf.biz.ifsSync.entity.PurchaseM;
import com.gf.biz.ifsSync.service.PurchaseApplyService;
import com.gf.biz.common.GfResult;
import io.seata.core.context.RootContext;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/system/test")
@Api("测试")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    /**
     * 先更新预算，再更新申请单状态
     * @return
     */
    @RequestMapping(value="updateBudget",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GfResult<String> updateBudget(@RequestBody PurchaseM purchaseApply){

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String rXid=request.getHeader(RootContext.KEY_XID);
        String xId = RootContext.getXID();
        logger.info("xId:{},rXid:{}",xId,rXid);
        /*if(StringUtils.isNotBlank(xId)){
            if(!RootContext.inGlobalTransaction()){
                RootContext.bind(xId);
            }
        }*/
        logger.info(purchaseApply.toString());
        return purchaseApplyService.updateBudgetAmount(String.valueOf(purchaseApply.getId()));
    }



    @SaIgnore
    @GetMapping("testGlobal")
    public String testGlobal(){
        return "hello world";
    }
}
