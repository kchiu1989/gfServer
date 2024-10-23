package com.gf.biz.funcDeptIndicatorScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 员工满意度评分表
 * </p>
 *
 * @author Gf
 * @since 2024-10-23 13:49:25
 */
@Data
@TableName("bf_employee_satisfy_survey")
    public class BfEmployeeSatisfySurvey implements Serializable {

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
         * '0'正常 '1'删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

        /**
         * 提交日期
         */
    @TableField("submit_date")
        private Date submitDate;

        /**
         * 部门类型
         */
    @TableField("dept_type_name")
        private String deptTypeName;

        /**
         * 部门类型编码
         */
    @TableField("dept_type_code")
        private String deptTypeCode;

        /**
         * 品牌/中心名称
         */
    @TableField("brand_center_name")
        private String brandCenterName;

        /**
         * 品牌/中心编码
         */
    @TableField("brand_center_code")
        private String brandCenterCode;

        /**
         * 区域
         */
    @TableField("region_name")
        private String regionName;

        /**
         * 区域名称
         */
    @TableField("region_code")
        private String regionCode;

        /**
         * 评价部门id
         */
    @TableField("dept_id")
        private Long deptId;

        /**
         * 评价部门编码
         */
    @TableField("dept_code")
        private String deptCode;

        /**
         * 评价部门名称
         */
    @TableField("dept_name")
        private String deptName;

        /**
         * 审批人id
         */
    @TableField("appr_user_id")
        private Long apprUserId;

        /**
         * 审批人名称
         */
    @TableField("appr_user_name")
        private String apprUserName;

        /**
         * 审批状态标识
         */
    @TableField("status")
        private String status;

        /**
         * 审批状态名
         */
    @TableField("status_name")
        private String statusName;

        /**
         * 评价人id
         */
    @TableField("evaluate_user_id")
        private Long evaluateUserId;

        /**
         * 评价人名
         */
    @TableField("evaluate_user_name")
        private String evaluateUserName;

        /**
         * 总分
         */
    @TableField("total_score")
        private BigDecimal totalScore;

        /**
         * 流程实例id
         */
    @TableField("process_instance_id")
        private String processInstanceId;

        /**
         * 表单单据类型id
         */
    @TableField("bd_form_type_id")
        private Long bdFormTypeId;

        /**
         * 表单单据类型代码
         */
    @TableField("bd_form_type_code")
        private String bdFormTypeCode;

        /**
         * 业务日期
         */
    @TableField("business_date")
        private Date businessDate;

        /**
         * 业务年
         */
    @TableField("business_year")
        private String businessYear;

        /**
         * 业务月
         */
    @TableField("business_month")
        private String businessMonth;

        /**
         * 业务季度
         */
    @TableField("business_season")
        private String businessSeason;


}