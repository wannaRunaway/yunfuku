package com.aifubook.book.mine.order.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ToPayBean implements Serializable {

    /**
     * nonceStr : adipisicing nisi
     * orderAmount : 4441.95
     * package : dolore cupidatat pariatur sString laboris
     * signType : dolor
     * appId : 99d52aa39d424b65cb5bd4e6
     * prePayParams : pariatur
     * payUri : {"string":"sed sit consequat minim officia","labore__f":9.252529960247761E7}
     * outTradeNo : minim in
     * paySign : qui quis nisi
     * payOrderId : 275
     * timeStamp : ut pariatur sunt
     * orderId : 424e94aa52c03ccd4b971d84
     */

    private String nonceStr;
    private String orderAmount;
    @SerializedName("package")
    private String packageX;
    private String signType;
    private String appId;
    private String prePayParams;
    private PayUriBean payUri;
    private String outTradeNo;
    private String paySign;
    private String payOrderId;
    private String timeStamp;
    private String orderId;

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrePayParams() {
        return prePayParams;
    }

    public void setPrePayParams(String prePayParams) {
        this.prePayParams = prePayParams;
    }

    public PayUriBean getPayUri() {
        return payUri;
    }

    public void setPayUri(PayUriBean payUri) {
        this.payUri = payUri;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static class PayUriBean implements Serializable {
        /**
         * string : sed sit consequat minim officia
         * labore__f : 9.252529960247761E7
         */

        private String string;
        private String labore__f;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getLabore__f() {
            return labore__f;
        }

        public void setLabore__f(String labore__f) {
            this.labore__f = labore__f;
        }
    }
}
