package com.jiarui.base.baserx;

public class ApiException extends RuntimeException {
    private String errorMsg;

    public ApiException(String msg) {
        super(msg);
        this.errorMsg = msg;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

