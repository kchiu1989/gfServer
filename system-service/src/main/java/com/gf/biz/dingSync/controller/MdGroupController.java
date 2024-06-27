package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.MdGroup;
import com.gf.biz.dingSync.service.MdGroupService;
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
 * @since 2024-05-24 17:01:58
 */
@RestController
@RequestMapping("/mdGroup")
public class MdGroupController {

    @Autowired
    private MdGroupService mdGroupService;

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
        return Response.success(this.mdGroupService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, MdGroup mdGroup) {
        return Response.success(this.mdGroupService.page(new Query<MdGroup>().getPage(params), new QueryWrapper<MdGroup>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param mdGroup 实体
     * @return com.github.common.utils.Response<MdGroup>
     */
    @PostMapping("/add")
    public Response add(@RequestBody MdGroup mdGroup) {
        //TODO 添加数据校验
        boolean res = this.mdGroupService.save(mdGroup);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<MdGroup>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        MdGroup mdGroup = new MdGroup();
        mdGroup.setId(id);
        mdGroup.setUpdatedTime(new Date());
        mdGroup.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.mdGroupService.updateById(mdGroup);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param mdGroup 实体
     * @return com.github.common.utils.Response<MdGroup>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody MdGroup mdGroup) {
        if (mdGroup.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        mdGroup.setUpdatedTime(new Date());
        boolean res = this.mdGroupService.updateById(mdGroup);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
