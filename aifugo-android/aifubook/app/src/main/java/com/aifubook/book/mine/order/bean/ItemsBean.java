package com.aifubook.book.mine.order.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemsBean implements Serializable {
    /**
     * id : 20210503570
     * orderId : 20210503569
     * fee : 1
     * productPrice : 1
     * productId : 5578
     * count : 1
     * productName : 倍速学习法  六年级 语文
     * productSubName :
     * productImage : 891c2d53b2e08d36eea3e3dc348addb7fb766681.jpg
     * status : 0
     * commission : null
     * refundStatus (integer, optional): 退款状态 null说明未发生退款,1-申请售后,2-退款成功,3-退款拒绝,
     * 4-待退货,5-退货中,6-退款关闭,7-到账成功,8-售后完成 ,
     *
     */

    private long id;
    private long orderId;
    private Integer fee;
    private Integer productPrice;
    private String productId;
    private String count;
    private String productName;
    private String productSubName;
    private String productImage;
    private String status;
    private Object commission;
    private double commissionRate;
    private Integer refundStatus;
    private String refundExpressNo;
    private List<String> refundReasonStr;
    private Long refundApplyTime;
    private Integer payType;
    private Object refundAuditTime;
    private List<String> refundImage;
    //"refundExpressNo":"GmbH HVDC",
    //"refundExpressCompany":"EMS包裹",
    private String refundExpressCompany;
    private String billNo;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getRefundExpressCompany() {
        return refundExpressCompany;
    }

    public void setRefundExpressCompany(String refundExpressCompany) {
        this.refundExpressCompany = refundExpressCompany;
    }

    public List<String> getRefundImage() {
        return refundImage;
    }

    public void setRefundImage(List<String> refundImage) {
        this.refundImage = refundImage;
    }

    public Object getRefundAuditTime() {
        return refundAuditTime;
    }

    public void setRefundAuditTime(Object refundAuditTime) {
        this.refundAuditTime = refundAuditTime;
    }

    public String getRefundExpressNo() {
        return refundExpressNo;
    }

    public void setRefundExpressNo(String refundExpressNo) {
        this.refundExpressNo = refundExpressNo;
    }

    public List<String> getRefundReasonStr() {
        return refundReasonStr;
    }

    public void setRefundReasonStr(List<String> refundReasonStr) {
        this.refundReasonStr = refundReasonStr;
    }

    public Long getRefundApplyTime() {
        return refundApplyTime;
    }

    public void setRefundApplyTime(Long refundApplyTime) {
        this.refundApplyTime = refundApplyTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubName() {
        return productSubName;
    }

    public void setProductSubName(String productSubName) {
        this.productSubName = productSubName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCommission() {
        return commission;
    }

    public void setCommission(Object commission) {
        this.commission = commission;
    }
}
