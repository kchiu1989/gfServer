package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.MdUnit;
import com.gf.biz.dingSync.service.MdUnitService;
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
 * @since 2024-05-24 17:02:17
 */
@RestController
@RequestMapping("/mdUnit")
public class MdUnitController {

    @Autowired
    private MdUnitService mdUnitService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return com.github.common.utils.Response
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        return Response.success(this.mdUnitService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, MdUnit mdUnit) {
        return Response.success(this.mdUnitService.page(new Query<MdUnit>().getPage(params), new QueryWrapper<MdUnit>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param mdUnit 实体
     * @return com.github.common.utils.Response<MdUnit>
     */
    @PostMapping("/add")
    public Response add(@RequestBody MdUnit mdUnit) {
        //TODO 添加数据校验
        boolean res = this.mdUnitService.save(mdUnit);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<MdUnit>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        MdUnit mdUnit = new MdUnit();
        mdUnit.setId(id);
        mdUnit.setUpdatedTime(new Date());
        mdUnit.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.mdUnitService.updateById(mdUnit);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param mdUnit 实体
     * @return com.github.common.utils.Response<MdUnit>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody MdUnit mdUnit) {
        if (mdUnit.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        mdUnit.setUpdatedTime(new Date());
        boolean res = this.mdUnitService.updateById(mdUnit);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
