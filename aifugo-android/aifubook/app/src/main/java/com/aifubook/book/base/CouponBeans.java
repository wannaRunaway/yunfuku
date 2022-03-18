package com.aifubook.book.base;

import com.aifubook.book.bean.ShopBean;

/**
 * Created by ListKer_Hlg on 2021/5/9 20:09
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class CouponBeans {


    private Integer id;
    private Integer memberId;
    private Integer couponId;
    private CouponDTO coupon;
    private Integer shopId;
    private ShopBean shop;
    private Long startTime;
    private Long endTime;
    private Integer status;
    private Integer type;
    private Object usedTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public CouponDTO getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDTO coupon) {
        this.coupon = coupon;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Object usedTime) {
        this.usedTime = usedTime;
    }

    public static class CouponDTO {
        private Integer id;
        private Integer shopId;
        private Object shop;
        private Integer type;
        private Integer status;
        private Integer fullFee;
        private Integer reduceFee;
        private Long startTime;
        private Long endTime;
        private Integer limit;
        private int discountRate;
        private int discountUpLimit;

        public int getDiscountUpLimit() {
            return discountUpLimit;
        }

        public void setDiscountUpLimit(int discountUpLimit) {
            this.discountUpLimit = discountUpLimit;
        }

        public int getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(int discountRate) {
            this.discountRate = discountRate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
        }

        public Object getShop() {
            return shop;
        }

        public void setShop(Object shop) {
            this.shop = shop;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getFullFee() {
            return fullFee;
        }

        public void setFullFee(Integer fullFee) {
            this.fullFee = fullFee;
        }

        public Integer getReduceFee() {
            return reduceFee;
        }

        public void setReduceFee(Integer reduceFee) {
            this.reduceFee = reduceFee;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }
}
