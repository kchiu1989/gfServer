package com.gf.biz.LecaiSync.dto;

public class Lecai_gangwei_requestData {
    private String corpId;
    private String organId;

    private String lastDate;
    private String id;

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getOrganId() {
        return organId;
    }

    public String getId() {
        return id;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
