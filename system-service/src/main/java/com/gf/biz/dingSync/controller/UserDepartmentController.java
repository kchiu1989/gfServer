package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.UserDepartment;
import com.gf.biz.dingSync.service.UserDepartmentService;
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
 * @since 2024-05-24 17:01:30
 */
@RestController
@RequestMapping("/userDepartment")
public class UserDepartmentController {

    @Autowired
    private UserDepartmentService userDepartmentService;

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
        return Response.success(this.userDepartmentService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, UserDepartment userDepartment) {
        return Response.success(this.userDepartmentService.page(new Query<UserDepartment>().getPage(params), new QueryWrapper<UserDepartment>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param userDepartment 实体
     * @return com.github.common.utils.Response<UserDepartment>
     */
    @PostMapping("/add")
    public Response add(@RequestBody UserDepartment userDepartment) {
        //TODO 添加数据校验
        boolean res = this.userDepartmentService.save(userDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<UserDepartment>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setId(id);
        userDepartment.setUpdatedTime(new Date());
        userDepartment.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.userDepartmentService.updateById(userDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param userDepartment 实体
     * @return com.github.common.utils.Response<UserDepartment>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody UserDepartment userDepartment) {
        if (userDepartment.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        userDepartment.setUpdatedTime(new Date());
        boolean res = this.userDepartmentService.updateById(userDepartment);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
