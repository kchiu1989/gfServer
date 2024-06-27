package com.gf.biz.dingSync.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.Query;
import com.gf.biz.common.Response;
import com.gf.biz.dingSync.po.UserInfo;
import com.gf.biz.dingSync.service.UserInfoService;
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
 * @since 2024-05-24 17:00:11
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

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
        return Response.success(this.userInfoService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param params
     * @return com.github.common.utils.Response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam Map<String, Object> params, UserInfo userInfo) {
        return Response.success(this.userInfoService.page(new Query<UserInfo>().getPage(params), new QueryWrapper<UserInfo>()
                .eq(CommonConstant.IS_DELETE, CommonConstant.INT_STATUS_NORMAL)));
    }

    /**
     * 添加
     *
     * @param userInfo 实体
     * @return com.github.common.utils.Response<UserInfo>
     */
    @PostMapping("/add")
    public Response add(@RequestBody UserInfo userInfo) {
        //TODO 添加数据校验
        boolean res = this.userInfoService.save(userInfo);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 删除
     *
     * @param id ID
     * @return com.github.common.utils.Response<UserInfo>
     */
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        if (id == null) {
            return Response.errorResponse("ID不能为空");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUpdatedTime(new Date());
        userInfo.setDeletedFlag(CommonConstant.STATUS_DEL);
        boolean res = this.userInfoService.updateById(userInfo);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

    /**
     * 编辑
     *
     * @param userInfo 实体
     * @return com.github.common.utils.Response<UserInfo>
     */
    @PutMapping("/edit")
    public Response edit(@RequestBody UserInfo userInfo) {
        if (userInfo.getId() == null) {
            return Response.errorResponse("ID不能为空");
        }
        //TODO 添加数据校验
        userInfo.setUpdatedTime(new Date());
        boolean res = this.userInfoService.updateById(userInfo);
        if (res) {
            return Response.successResponse("操作成功");
        }
        return Response.errorResponse("操作失败");
    }

}
