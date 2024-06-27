package com.gf.biz.ifsSync.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购申请朱丹
 */
@TableName("purchase_m")
public class PurchaseM implements Serializable {

    private static final long serialVersionUID = -2009416897671572263L;
    @TableId(type= IdType.AUTO)
    private Long id;
    @TableField(value="sn")
    private String sn;

    @TableField(value="amount")
    private BigDecimal amount;

    @TableField(value="process_status")
    private String processStatus;

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
