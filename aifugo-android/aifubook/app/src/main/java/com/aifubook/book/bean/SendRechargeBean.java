package com.aifubook.book.bean;

/**
 * Created by ListKer_Hlg on 2021/5/23 22:43
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class SendRechargeBean {


    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String signType;
    private String paySign;
    private String packageX;
    private String partnerid;
    private String payInfo;
    private Long groupBuyOrderId;
    private Integer leftCount;//拼团剩余名额

    public Long getGroupBuyOrderId() {
        return groupBuyOrderId;
    }

    public void setGroupBuyOrderId(Long groupBuyOrderId) {
        this.groupBuyOrderId = groupBuyOrderId;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }
}
