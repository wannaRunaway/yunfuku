package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderItemDTO implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("orderId")
    private Long orderId;
    @SerializedName("fee")
    private Integer fee;
    @SerializedName("productPrice")
    private Integer productPrice;
    @SerializedName("proProductPrice")
    private Integer proProductPrice;
    @SerializedName("productId")
    private Integer productId;
    @SerializedName("productCode")
    private Object productCode;
    @SerializedName("count")
    private Integer count;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productSubName")
    private Object productSubName;
    @SerializedName("productImage")
    private String productImage;
    @SerializedName("status")
    private Integer status;
    @SerializedName("chiefId")
    private Integer chiefId;
    @SerializedName("shopId")
    private Integer shopId;
    @SerializedName("memberId")
    private Integer memberId;
    @SerializedName("commission")
    private Integer commission;
    @SerializedName("billNo")
    private Object billNo;
    @SerializedName("itemBillNo")
    private String itemBillNo;
    @SerializedName("zeroBuy")
    private Integer zeroBuy;
    @SerializedName("groupBuyOrderId")
    private Object groupBuyOrderId;
    @SerializedName("commissionRate")
    private Integer commissionRate;
    @SerializedName("chiefName")
    private Object chiefName;
    @SerializedName("itemStatus")
    private Integer itemStatus;
    @SerializedName("refundType")
    private Integer refundType;
    @SerializedName("payType")
    private Integer payType;
    @SerializedName("refundExpressNo")
    private String refundExpressNo;
    @SerializedName("refundExpressCompany")
    private String refundExpressCompany;
    @SerializedName("refundApplyTime")
    private Long refundApplyTime;
    @SerializedName("refundAuditTime")
    private Object refundAuditTime;
    @SerializedName("refundFee")
    private Integer refundFee;
    @SerializedName("refundExpressTime")
    private Object refundExpressTime;
    @SerializedName("refundRemark")
    private String refundRemark;
    @SerializedName("refundRefuseReason")
    private String refundRefuseReason;
    @SerializedName("refundRefuseImage")
    private Object refundRefuseImage;
    @SerializedName("refundStatus")
    private Integer refundStatus;
    @SerializedName("refundOperator")
    private Object refundOperator;
    @SerializedName("refusalReasons")
    private Object refusalReasons;
    @SerializedName("refundReasons")
    private List<Integer> refundReasons;
    @SerializedName("refundReasonStr")
    private List<String> refundReasonStr;
    @SerializedName("refundImage")
    private List<String> refundImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProProductPrice() {
        return proProductPrice;
    }

    public void setProProductPrice(Integer proProductPrice) {
        this.proProductPrice = proProductPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Object getProductCode() {
        return productCode;
    }

    public void setProductCode(Object productCode) {
        this.productCode = productCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Object getProductSubName() {
        return productSubName;
    }

    public void setProductSubName(Object productSubName) {
        this.productSubName = productSubName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChiefId() {
        return chiefId;
    }

    public void setChiefId(Integer chiefId) {
        this.chiefId = chiefId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Object getBillNo() {
        return billNo;
    }

    public void setBillNo(Object billNo) {
        this.billNo = billNo;
    }

    public String getItemBillNo() {
        return itemBillNo;
    }

    public void setItemBillNo(String itemBillNo) {
        this.itemBillNo = itemBillNo;
    }

    public Integer getZeroBuy() {
        return zeroBuy;
    }

    public void setZeroBuy(Integer zeroBuy) {
        this.zeroBuy = zeroBuy;
    }

    public Object getGroupBuyOrderId() {
        return groupBuyOrderId;
    }

    public void setGroupBuyOrderId(Object groupBuyOrderId) {
        this.groupBuyOrderId = groupBuyOrderId;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Object getChiefName() {
        return chiefName;
    }

    public void setChiefName(Object chiefName) {
        this.chiefName = chiefName;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRefundExpressNo() {
        return refundExpressNo;
    }

    public void setRefundExpressNo(String refundExpressNo) {
        this.refundExpressNo = refundExpressNo;
    }

    public String getRefundExpressCompany() {
        return refundExpressCompany;
    }

    public void setRefundExpressCompany(String refundExpressCompany) {
        this.refundExpressCompany = refundExpressCompany;
    }

    public Long getRefundApplyTime() {
        return refundApplyTime;
    }

    public void setRefundApplyTime(Long refundApplyTime) {
        this.refundApplyTime = refundApplyTime;
    }

    public Object getRefundAuditTime() {
        return refundAuditTime;
    }

    public void setRefundAuditTime(Object refundAuditTime) {
        this.refundAuditTime = refundAuditTime;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Object getRefundExpressTime() {
        return refundExpressTime;
    }

    public void setRefundExpressTime(Object refundExpressTime) {
        this.refundExpressTime = refundExpressTime;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getRefundRefuseReason() {
        return refundRefuseReason;
    }

    public void setRefundRefuseReason(String refundRefuseReason) {
        this.refundRefuseReason = refundRefuseReason;
    }

    public Object getRefundRefuseImage() {
        return refundRefuseImage;
    }

    public void setRefundRefuseImage(Object refundRefuseImage) {
        this.refundRefuseImage = refundRefuseImage;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Object getRefundOperator() {
        return refundOperator;
    }

    public void setRefundOperator(Object refundOperator) {
        this.refundOperator = refundOperator;
    }

    public Object getRefusalReasons() {
        return refusalReasons;
    }

    public void setRefusalReasons(Object refusalReasons) {
        this.refusalReasons = refusalReasons;
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

    public List<String> getRefundImage() {
        return refundImage;
    }

    public void setRefundImage(List<String> refundImage) {
        this.refundImage = refundImage;
    }
}
