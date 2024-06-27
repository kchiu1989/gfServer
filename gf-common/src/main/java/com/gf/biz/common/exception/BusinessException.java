package com.gf.biz.common.exception;

import com.gf.biz.common.GfResultCode;

public class BusinessException extends RuntimeException{

    private final String userMessage;

    /**
     * 错误码<br>
     * 调用成功时，为 null。<br>
     * 示例：10001
     */

    private final String errorCode;

    /**
     * 错误信息<br>
     * 调用成功时，为 null。<br>
     * 示例："验证码无效"
     */

    private final String errorMessage;


    public BusinessException(GfResultCode gfResultCode) {
        super(String.format("错误码：[%s]，错误信息：[%s]，用户提示：[%s]", gfResultCode.getCode(), gfResultCode.getMessage(), gfResultCode.getMessage()));
        this.userMessage = gfResultCode.getMessage();
        this.errorCode = gfResultCode.getCode();
        this.errorMessage = gfResultCode.getMessage();
    }


    public BusinessException(String userMessage, String errorCode, String errorMessage) {
        super(String.format("错误码：[%s]，错误信息：[%s]，用户提示：[%s]", errorCode, errorMessage, userMessage));
        this.userMessage = userMessage;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorCode, String errorMessage) {
        super(String.format("错误码：[%s]，错误信息：[%s]，用户提示：[%s]", errorCode, errorMessage, errorMessage));
        this.userMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
