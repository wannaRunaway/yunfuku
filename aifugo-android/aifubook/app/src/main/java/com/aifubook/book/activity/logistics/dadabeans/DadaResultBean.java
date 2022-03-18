package com.aifubook.book.activity.logistics.dadabeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DadaResultBean implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("dataStatus")
    private Boolean dataStatus;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("clientId")
    private String clientId;
    @SerializedName("billNo")
    private String billNo;
    @SerializedName("orderStatus")
    private Integer orderStatus;
    @SerializedName("cancelReason")
    private String cancelReason;
    @SerializedName("cancelFrom")
    private Integer cancelFrom;
    @SerializedName("dadaUpdateTime")
    private Long dadaUpdateTime;
    @SerializedName("signature")
    private String signature;
    @SerializedName("dmId")
    private Integer dmId;
    @SerializedName("dmName")
    private String dmName;
    @SerializedName("dmMobile")
    private String dmMobile;
    @SerializedName("finishCode")
    private String finishCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Boolean dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getCancelFrom() {
        return cancelFrom;
    }

    public void setCancelFrom(Integer cancelFrom) {
        this.cancelFrom = cancelFrom;
    }

    public Long getDadaUpdateTime() {
        return dadaUpdateTime;
    }

    public void setDadaUpdateTime(Long dadaUpdateTime) {
        this.dadaUpdateTime = dadaUpdateTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getDmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String getDmName() {
        return dmName;
    }

    public void setDmName(String dmName) {
        this.dmName = dmName;
    }

    public String getDmMobile() {
        return dmMobile;
    }

    public void setDmMobile(String dmMobile) {
        this.dmMobile = dmMobile;
    }

    public String getFinishCode() {
        return finishCode;
    }

    public void setFinishCode(String finishCode) {
        this.finishCode = finishCode;
    }
}
