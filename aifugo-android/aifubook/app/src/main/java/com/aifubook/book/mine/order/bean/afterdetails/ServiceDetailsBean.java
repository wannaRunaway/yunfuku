package com.aifubook.book.mine.order.bean.afterdetails;

import com.aifubook.book.mine.order.bean.ItemsBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceDetailsBean implements Serializable {

    @SerializedName("refundCloseTimeDown")
    private Integer refundCloseTimeDown;
    @SerializedName("refundFee")
    private Integer refundFee;
    @SerializedName("orderItem")
    private ItemsBean orderItem;
    @SerializedName("shop")
    private ShopDTO shop;
    @SerializedName("orderRefundRecordVOs")
    private List<OrderRefundRecordVOsDTO> orderRefundRecordVOs;

    public Integer getRefundCloseTimeDown() {
        return refundCloseTimeDown;
    }

    public void setRefundCloseTimeDown(Integer refundCloseTimeDown) {
        this.refundCloseTimeDown = refundCloseTimeDown;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public ItemsBean getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(ItemsBean orderItem) {
        this.orderItem = orderItem;
    }

    public ShopDTO getShop() {
        return shop;
    }

    public void setShop(ShopDTO shop) {
        this.shop = shop;
    }

    public List<OrderRefundRecordVOsDTO> getOrderRefundRecordVOs() {
        return orderRefundRecordVOs;
    }

    public void setOrderRefundRecordVOs(List<OrderRefundRecordVOsDTO> orderRefundRecordVOs) {
        this.orderRefundRecordVOs = orderRefundRecordVOs;
    }
}
