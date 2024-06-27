package com.gf.biz.ifsSync.controller;

import cn.dev33.satoken.annotation.SaIgnore;

import com.gf.biz.ifsSync.service.PurchaseApplyService;
import com.gf.biz.common.GfResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iss/test")
@Api("测试")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    /**
     * 先更新预算，再更新申请单状态
     * @return
     */
    @ApiOperation("测试功能")
    @RequestMapping(value="testUpdApply",method= RequestMethod.GET)

    public @ResponseBody GfResult<String> testUpdApply(@RequestParam String id){

        logger.info("id:{}",id);
        return purchaseApplyService.updateBudgetAmount(id);
    }



    @SaIgnore
    @GetMapping("testGlobal")
    public String testGlobal(){
        return "hello world";
    }
}
