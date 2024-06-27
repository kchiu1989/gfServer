package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;

import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.MdDepartment;
import com.gf.biz.dingSync.service.MdDepartmentService;
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
 * @since 2024-05-24 17:02:35
 */
@RestController
@RequestMapping("/mdDepartment")
public class MdDepartmentController {

    @Autowired
    private MdDepartmentService mdDepartmentService;

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
        return Response.success(this.mdDepartmentService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, MdDepartment mdDepartment) {
        return Response.success(this.mdDepartmentService.page(new Query<MdDepartment>().getPage(params), new QueryWrapper<MdDepartment>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param mdDepartment 实体
     * @return com.github.common.utils.Response<MdDepartment>
     */
    @PostMapping("/add")
    public Response add(@RequestBody MdDepartment mdDepartment) {
        //TODO 添加数据校验
        boolean res = this.mdDepartmentService.save(mdDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<MdDepartment>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        MdDepartment mdDepartment = new MdDepartment();
        mdDepartment.setId(id);
        mdDepartment.setUpdatedTime(new Date());
        mdDepartment.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.mdDepartmentService.updateById(mdDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param mdDepartment 实体
     * @return com.github.common.utils.Response<MdDepartment>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody MdDepartment mdDepartment) {
        if (mdDepartment.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        mdDepartment.setUpdatedTime(new Date());
        boolean res = this.mdDepartmentService.updateById(mdDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
