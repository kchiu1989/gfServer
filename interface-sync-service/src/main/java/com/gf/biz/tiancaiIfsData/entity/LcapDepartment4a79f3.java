package com.gf.biz.tiancaiIfsData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门实体。新增部门的同时一般需要指定上一级部门。默认生成的字段不允许改动，可新增自定义字段。
 * </p>
 *
 * @author Gf
 * @since 2024-09-24 14:02:13
 */
@Data
@TableName("lcap_department_4a79f3")
    public class LcapDepartment4a79f3 implements Serializable {

    private static final long serialVersionUID = 1L;

                @TableId(value = "id", type = IdType.AUTO)
                private Long id;

        /**
         * 创建时间
         */
    @TableField("created_time")
        private Date createdTime;

        /**
         * 更新时间
         */
    @TableField("updated_time")
        private Date updatedTime;

        /**
         * 创建者
         */
    @TableField("created_by")
        private String createdBy;

        /**
         * 更新者
         */
    @TableField("updated_by")
        private String updatedBy;

        /**
         * 部门名称
         */
    @TableField("name")
        private String name;

        /**
         * 部门标识
         */
    @TableField("dept_id")
        private String deptId;

        /**
         * 父部门标识
         */
    @TableField("parent_dept_id")
        private String parentDeptId;

        /**
         * 主数据系统id
         */
    @TableField("if_id")
        private Long ifId;

        /**
         * 主数据系统对应parent_id
         */
    @TableField("if_parent_id")
        private Long ifParentId;

        /**
         * '0'运营'1'部门
         */
    @TableField("dept_classify")
        private String deptClassify;

        /**
         * 部门编码
         */
    @TableField("dept_code")
        private String deptCode;

        /**
         * 品牌或者中心名称
         */
    @TableField("brand_center_name")
        private String brandCenterName;

        /**
         * 区域名称
         */
    @TableField("region_name")
        private String regionName;


}