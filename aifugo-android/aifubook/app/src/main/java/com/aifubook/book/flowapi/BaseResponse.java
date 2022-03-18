package com.aifubook.book.flowapi;


import java.io.Serializable;

import kotlin.jvm.Throws;

public class BaseResponse<T> implements Serializable {
    //返回的数据
    private T result;
    private String code;
    private boolean ok;
    private String message = "";

    public BaseResponse() {
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    @Throws(exceptionClasses = ApiException.class)
//    public void throwAPIException() throws ApiException {
//        throw new ApiException(code, message);
//    }
}