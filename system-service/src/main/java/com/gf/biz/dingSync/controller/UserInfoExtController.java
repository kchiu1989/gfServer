package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.UserInfoExt;
import com.gf.biz.dingSync.service.UserInfoExtService;
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
 * @since 2024-05-24 17:00:48
 */
@RestController
@RequestMapping("/userInfoExt")
public class UserInfoExtController {

    @Autowired
    private UserInfoExtService userInfoExtService;

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
        return Response.success(this.userInfoExtService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, UserInfoExt userInfoExt) {
        return Response.success(this.userInfoExtService.page(new Query<UserInfoExt>().getPage(params), new QueryWrapper<UserInfoExt>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param userInfoExt 实体
     * @return com.github.common.utils.Response<UserInfoExt>
     */
    @PostMapping("/add")
    public Response add(@RequestBody UserInfoExt userInfoExt) {
        //TODO 添加数据校验
        boolean res = this.userInfoExtService.save(userInfoExt);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<UserInfoExt>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        UserInfoExt userInfoExt = new UserInfoExt();
        userInfoExt.setId(id);
        userInfoExt.setUpdatedTime(new Date());
        userInfoExt.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.userInfoExtService.updateById(userInfoExt);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param userInfoExt 实体
     * @return com.github.common.utils.Response<UserInfoExt>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody UserInfoExt userInfoExt) {
        if (userInfoExt.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        userInfoExt.setUpdatedTime(new Date());
        boolean res = this.userInfoExtService.updateById(userInfoExt);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
