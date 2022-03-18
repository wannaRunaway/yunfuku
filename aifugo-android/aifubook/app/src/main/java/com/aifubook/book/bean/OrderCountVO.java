package com.aifubook.book.bean;

public class OrderCountVO {

    /**
     * {
     *     "deliveryCount": 0,
     *     "payCount": 0,
     *     "serviceCount": 0,
     *     "waitCount": 0,
     *     "waitingCount": 0
     *   }
     * */
    private int deliveryCount;//待收获数量
    private int payCount;//待发货数量
    private int serviceCount;//售后
    private int waitCount;//待支付
    private int waitingCount;//待自提

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }
}
