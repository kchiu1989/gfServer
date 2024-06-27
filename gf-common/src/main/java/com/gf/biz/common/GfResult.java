package com.gf.biz.common;

import java.io.Serializable;

public class GfResult<T> implements Serializable {
    private Boolean success;

    private String code;

    private String message;

    private T data;

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GfResult))
            return false;
        GfResult<?> other = (GfResult)o;
        if (!other.canEqual(this))
            return false;
        Object this$success = getSuccess(), other$success = other.getSuccess();
        if ((this$success == null) ? (other$success != null) : !this$success.equals(other$success))
            return false;
        Object this$code = getCode(), other$code = other.getCode();
        if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code))
            return false;
        Object this$message = getMessage(), other$message = other.getMessage();
        if ((this$message == null) ? (other$message != null) : !this$message.equals(other$message))
            return false;
        Object this$data = getData(), other$data = other.getData();
        return !((this$data == null) ? (other$data != null) : !this$data.equals(other$data));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GfResult;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $success = getSuccess();
        result = result * 59 + (($success == null) ? 43 : $success.hashCode());
        Object $code = getCode();
        result = result * 59 + (($code == null) ? 43 : $code.hashCode());
        Object $message = getMessage();
        result = result * 59 + (($message == null) ? 43 : $message.hashCode());
        Object $data = getData();
        return result * 59 + (($data == null) ? 43 : $data.hashCode());
    }

    public String toString() {
        return "GfResult(success=" + getSuccess() + ", code=" + getCode() + ", message=" + getMessage() + ", data=" + getData() + ")";
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    private static <T> GfResult<T> result(Boolean success, String code, String message, T data) {
        GfResult<T> result = new GfResult<>();
        result.setSuccess(success);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> GfResult<T> success() {
        return success(null);
    }

    public static <T> GfResult<T> success(T data) {
        return result(Boolean.valueOf(true), GfResultCode.SUCCESS.getCode(), GfResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> GfResult<T> failed() {
        return result(Boolean.valueOf(false), GfResultCode.SYSTEM_EXECUTION_ERROR.getCode(), GfResultCode.SYSTEM_EXECUTION_ERROR.getMessage(), null);
    }

    public static <T> GfResult<T> failed(String message) {
        return result(Boolean.valueOf(false), GfResultCode.SYSTEM_EXECUTION_ERROR.getCode(), message, null);
    }

    public static <T> GfResult<T> failed(IGfResultCode resultCode) {
        return result(Boolean.valueOf(false), resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> GfResult<T> failed(IGfResultCode resultCode, String message) {
        return result(Boolean.valueOf(false), resultCode.getCode(), message, null);
    }
}
