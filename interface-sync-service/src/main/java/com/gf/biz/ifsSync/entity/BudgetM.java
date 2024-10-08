package com.gf.biz.ifsSync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("budget_month")
public class BudgetM implements Serializable {

    private static final long serialVersionUID = -116613276239480033L;
    @TableId(type= IdType.AUTO)
    private Long id;

    @TableField(value="budget_amt")
    private BigDecimal budgetAmt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(BigDecimal budgetAmt) {
        this.budgetAmt = budgetAmt;
    }
}
