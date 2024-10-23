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
 * 办公室部门检查表
 * </p>
 *
 * @author Gf
 * @since 2024-10-23 14:35:52
 */
@Data
@TableName("bf_office_dept_check")
    public class BfOfficeDeptCheck implements Serializable {

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
         * 提交/生效日期
         */
    @TableField("submit_date")
        private Date submitDate;

        /**
         * 部门类型
         */
    @TableField("evaluated_dept_type_name")
        private String evaluatedDeptTypeName;

        /**
         * 部门类型编码
         */
    @TableField("evaluated_dept_type_code")
        private String evaluatedDeptTypeCode;

        /**
         * 品牌/中心名称
         */
    @TableField("evaluated_brand_center_name")
        private String evaluatedBrandCenterName;

        /**
         * 品牌/中心编码
         */
    @TableField("evaluated_brand_center_code")
        private String evaluatedBrandCenterCode;

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
    @TableField("appr_status")
        private String apprStatus;

        /**
         * 审批状态名
         */
    @TableField("appr_status_name")
        private String apprStatusName;

        /**
         * 被评价部门id
         */
    @TableField("evaluated_dept_id")
        private Long evaluatedDeptId;

        /**
         * 被评价部门编码
         */
    @TableField("evaluated_dept_code")
        private String evaluatedDeptCode;

        /**
         * 被评价部门名称
         */
    @TableField("evaluated_dept_name")
        private String evaluatedDeptName;

        /**
         * 检察员id
         */
    @TableField("evaluate_user_id")
        private Long evaluateUserId;

        /**
         * 检察院名称
         */
    @TableField("evaluate_user_name")
        private String evaluateUserName;

        /**
         * 总分
         */
    @TableField("total_score")
        private BigDecimal totalScore;

        /**
         * 被检查部门负责人id
         */
    @TableField("evaluated_user_id")
        private Long evaluatedUserId;

        /**
         * 被检查部门负责人名称
         */
    @TableField("evaluated_user_name")
        private String evaluatedUserName;

        /**
         * 检查日期
         */
    @TableField("evaluated_date")
        private Date evaluatedDate;

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
         * 办公环境图片url
         */
    @TableField("image_url1")
        private String imageUrl1;

        /**
         * 员工状态图片url
         */
    @TableField("image_url2")
        private String imageUrl2;

        /**
         * 办公环境得分
         */
    @TableField("env_score")
        private BigDecimal envScore;

        /**
         * 员工状态得分
         */
    @TableField("status_score")
        private BigDecimal statusScore;

        /**
         * 审批状态标识
         */
    @TableField("status")
        private String status;

        /**
         * 业务年
         */
    @TableField("biz_year")
        private Integer bizYear;

        /**
         * 业务季度
         */
    @TableField("biz_season")
        private Integer bizSeason;

        /**
         * 业务月
         */
    @TableField("biz_month")
        private Integer bizMonth;


}