package com.gf.biz.ifsSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.ifsSync.entity.IfScoreMv;
import com.gf.biz.ifsSync.service.IfScoreMvService;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gf
 * @since 2024-04-22 17:29:02
 */
@Api
@RestController
@RequestMapping("/ifScoreMv")
public class IfScoreMvController {

    @Autowired
    private IfScoreMvService ifScoreMvService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return com.github.common.utils.Response
     */
    @ApiOperation("测试神秘访客1")
    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        return Response.success(this.ifScoreMvService.getById(id));
    }

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return com.github.common.utils.Response
     */
    @ApiOperation("测试神秘访客2")
    @RequestMapping(value="getById",method=RequestMethod.GET)
    public Response getById(@RequestParam Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        return Response.success(this.ifScoreMvService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, IfScoreMv ifScoreMv) {
        return Response.success(this.ifScoreMvService.page(new Query<IfScoreMv>().getPage(params), new QueryWrapper<IfScoreMv>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param ifScoreMv 实体
     * @return com.github.common.utils.Response<IfScoreMv>
     */
    @PostMapping("/add")
    public Response add(@RequestBody IfScoreMv ifScoreMv) {
        //TODO 添加数据校验
        boolean res = this.ifScoreMvService.save(ifScoreMv);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<IfScoreMv>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        IfScoreMv ifScoreMv = new IfScoreMv();
        ifScoreMv.setId(id);
        ifScoreMv.setUpdatedTime(new Date());
        ifScoreMv.setUpdatedBy("SYS");
        ifScoreMv.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.ifScoreMvService.updateById(ifScoreMv);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param ifScoreMv 实体
     * @return com.github.common.utils.Response<IfScoreMv>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody IfScoreMv ifScoreMv) {
        if (ifScoreMv.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        ifScoreMv.setUpdatedTime(new Date());
        boolean res = this.ifScoreMvService.updateById(ifScoreMv);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
