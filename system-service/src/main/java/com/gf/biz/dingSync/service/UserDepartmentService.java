package com.gf.biz.dingSync.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gf.biz.dingSync.po.MdDepartment;
import com.gf.biz.dingSync.po.UserDepartment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gf
 * @since 2024-05-24 17:01:30
 */
public interface UserDepartmentService extends IService<UserDepartment> {
    /**
     * 先更新user_department 该部门的dept_leader_flag为0
     * 再更新部门主管
     * @param toOptDept
     * @param manageIfUserList
     */
   void updateManageUser(MdDepartment toOptDept, List<String> manageIfUserList);

}
