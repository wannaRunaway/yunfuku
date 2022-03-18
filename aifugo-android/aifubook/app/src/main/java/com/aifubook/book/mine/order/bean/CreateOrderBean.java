package com.aifubook.book.mine.order.bean;

import java.io.Serializable;
import java.util.List;

public class CreateOrderBean implements Serializable {

    /**
     * memberId : 8513
     * status : 20
     * id : 4263
     * orderIds : [2504,6072]
     * payType : 4
     * paymentNo : adipisicing est
     * totalFee : 1710
     * payTime : 6697
     */

    private String memberId;
    private String status;
    private String id;
    private String payType;
    private String paymentNo;
    private String totalFee;
    private String payTime;
    private List<String> orderIds;
    private Long groupBuyOrderId;
    private Integer leftCount;//拼团剩余名额

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public Long getGroupBuyOrderId() {
        return groupBuyOrderId;
    }

    public void setGroupBuyOrderId(Long groupBuyOrderId) {
        this.groupBuyOrderId = groupBuyOrderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }
}
