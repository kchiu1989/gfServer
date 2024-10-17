package com.gf.biz.tiancaiIfsData.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。 Mapper 接口
 * </p>
 *
 * @author Gf
 * @since 2024-09-24 14:02:13
 */
    public interface LcapDepartment4a79f3Mapper extends BaseMapper<LcapDepartment4a79f3> {
        @Select("select * from lcap_department_4a79f3 where dept_classify = #{deptClassify}")
        List<LcapDepartment4a79f3> getAllDeptInfo(@Param("deptClassify") String deptClassify);

}
