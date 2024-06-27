package com.gf.biz.ifsSync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class Material {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull(message = "分类编码不能为空")
    private String categoryCode;

    @NotNull(message = "物料编码不能为空")
    @Length(max = 20, message = "物料编码不能超过20个字符")
    private String code;

    @NotNull(message = "物料名称不能为空")
    @Length(max = 20, message = "物料名称不能超过20个字符")
    private String name;

    @Length(max = 200, message = "描述不能超过200个字符")
    private String description;

    @NotNull
    private Double price;
}
