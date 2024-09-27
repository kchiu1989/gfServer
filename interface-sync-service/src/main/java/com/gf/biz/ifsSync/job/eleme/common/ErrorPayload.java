package com.gf.biz.ifsSync.job.eleme.common;

import java.io.Serializable;

public class ErrorPayload implements Serializable {


    private static final long serialVersionUID = -977068825093544010L;
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
