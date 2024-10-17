package com.gf.biz.tiancaiIfsData.po;

import java.math.BigDecimal;

public class IfScoreEntity {
    private String mcId;
    private String mcName;
    private BigDecimal star;
    private String gcName;
    private String ceCnt;

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }



    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public void setCeCnt(String ceCnt) {
        this.ceCnt = ceCnt;
    }

    public String getMcName() {
        return mcName;
    }

    public BigDecimal getStar() {
        return star;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public void setStar(BigDecimal star) {
        this.star = star;
    }

    public String getMcId() {
        return mcId;
    }


    public String getGcName() {
        return gcName;
    }

    public String getCeCnt() {
        return ceCnt;
    }
}
