package com.aifubook.book.base;

public class CouponBean {

    private int id;
    private int shopId;
    private int type;
    private int status;
    private int fullFee;
    private int reduceFee;
    private long startTime;
    private long endTime;
    private int limit;
    private int discountRate;
    private int discountUpLimit;
    private boolean possess;

    public boolean isPossess() {
        return possess;
    }

    public void setPossess(boolean possess) {
        this.possess = possess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFullFee() {
        return fullFee;
    }

    public void setFullFee(int fullFee) {
        this.fullFee = fullFee;
    }

    public int getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(int reduceFee) {
        this.reduceFee = reduceFee;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getDiscountUpLimit() {
        return discountUpLimit;
    }

    public void setDiscountUpLimit(int discountUpLimit) {
        this.discountUpLimit = discountUpLimit;
    }
}
