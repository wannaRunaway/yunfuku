package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
public class OrderRefundRecordVOsDTO implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("orderId")
    private Long orderId;
    @SerializedName("orderItemId")
    private Long orderItemId;
    @SerializedName("status")
    private Integer status;
    @SerializedName("memberId")
    private Integer memberId;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("orderStatus")
    private Integer orderStatus;
    @SerializedName("refundRemark")
    private String refundRemark;
    @SerializedName("refundRefuseReason")
    private Object refundRefuseReason;
    @SerializedName("refundRefuseImage")
    private Object refundRefuseImage;
    @SerializedName("refundExpressCompany")
    private Object refundExpressCompany;
    @SerializedName("refundExpressNo")
    private Object refundExpressNo;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("refundFee")
    private Integer refundFee;
    @SerializedName("refundType")
    private Integer refundType;
    @SerializedName("createTime")
    private Long createTime;
    @SerializedName("updateTime")
    private Long updateTime;
    @SerializedName("refundReasons")
    private List<Integer> refundReasons;
    @SerializedName("refundReasonStr")
    private List<String> refundReasonStr;
    @SerializedName("refundImage")
    private List<?> refundImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public Object getRefundRefuseReason() {
        return refundRefuseReason;
    }

    public void setRefundRefuseReason(Object refundRefuseReason) {
        this.refundRefuseReason = refundRefuseReason;
    }

    public Object getRefundRefuseImage() {
        return refundRefuseImage;
    }

    public void setRefundRefuseImage(Object refundRefuseImage) {
        this.refundRefuseImage = refundRefuseImage;
    }

    public Object getRefundExpressCompany() {
        return refundExpressCompany;
    }

    public void setRefundExpressCompany(Object refundExpressCompany) {
        this.refundExpressCompany = refundExpressCompany;
    }

    public Object getRefundExpressNo() {
        return refundExpressNo;
    }

    public void setRefundExpressNo(Object refundExpressNo) {
        this.refundExpressNo = refundExpressNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public List<Integer> getRefundReasons() {
        return refundReasons;
    }

    public void setRefundReasons(List<Integer> refundReasons) {
        this.refundReasons = refundReasons;
    }

    public List<String> getRefundReasonStr() {
        return refundReasonStr;
    }

    public void setRefundReasonStr(List<String> refundReasonStr) {
        this.refundReasonStr = refundReasonStr;
    }

    public List<?> getRefundImage() {
        return refundImage;
    }

    public void setRefundImage(List<?> refundImage) {
        this.refundImage = refundImage;
    }
}
