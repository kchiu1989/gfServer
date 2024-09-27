package com.gf.biz.ifsSync.job.eleme.common;



import java.io.Serializable;

public class ResponsePayload implements Serializable {


    private static final long serialVersionUID = 4002913794436425708L;
    private String id;

    /**
     * Invocation result
     */
    private Object result;

    /**
     * Invocation error
     */
    private ErrorPayload error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ErrorPayload getError() {
        return error;
    }

    public void setError(ErrorPayload error) {
        this.error = error;
    }
}
