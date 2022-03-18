package com.aifubook.book.mine.coupons;

import java.io.Serializable;
import java.util.List;

public class MyCouponsBean implements Serializable {


    /**
     * shop : {"name":"aliqua Excepteur ullamco","sameCitySendDistance":4322,"distance":3671.4879481266153,"address":"反严路1000号","id":2008,"provinceId":6757,"districtId":5247,"remark":"cupidatat","coupon":1,"logo":"fugiat aliquip occaecat commodo","phone":"18795821572","logisticsFee":6647,"updateTime":1619849993417,"cityId":3492,"location":[7246.482499870434,1995,8393.2,2967.4423],"inviteCode":"est deserunt in irure","createTime":1619849993581,"status":2,"memberId":3710,"sameCitySend":5125,"linkman":"minim"}
     * fullFee : 6791
     * reduceFee : 2813
     * type : 0
     * endTime : 1631
     * id : 8443
     * status : 1
     * shopId : 4512
     * startTime : 705
     */
    private String id;
    private String shopId;
    private String type;
    private String status;
    private String fullFee;
    private String reduceFee;
    private String startTime;
    private String endTime;
    private String discountRate;
    private String discountUpLimit;

//    private ShopBean shop;



//    public ShopBean getShop() {
//        return shop;
//    }
//
//    public void setShop(ShopBean shop) {
//        this.shop = shop;
//    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getDiscountUpLimit() {
        return discountUpLimit;
    }

    public void setDiscountUpLimit(String discountUpLimit) {
        this.discountUpLimit = discountUpLimit;
    }

    public String getFullFee() {
        return fullFee;
    }

    public void setFullFee(String fullFee) {
        this.fullFee = fullFee;
    }

    public String getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(String reduceFee) {
        this.reduceFee = reduceFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static class ShopBean implements Serializable {
        /**
         * name : aliqua Excepteur ullamco
         * sameCitySendDistance : 4322
         * distance : 3671.4879481266153
         * address : 反严路1000号
         * id : 2008
         * provinceId : 6757
         * districtId : 5247
         * remark : cupidatat
         * coupon : 1
         * logo : fugiat aliquip occaecat commodo
         * phone : 18795821572
         * logisticsFee : 6647
         * updateTime : 1619849993417
         * cityId : 3492
         * location : [7246.482499870434,1995,8393.2,2967.4423]
         * inviteCode : est deserunt in irure
         * createTime : 1619849993581
         * status : 2
         * memberId : 3710
         * sameCitySend : 5125
         * linkman : minim
         */

        private String name;
        private String sameCitySendDistance;
        private String distance;
        private String address;
        private String id;
        private String provinceId;
        private String districtId;
        private String remark;
        private String coupon;
        private String logo;
        private String phone;
        private String logisticsFee;
        private long updateTime;
        private String cityId;
        private String inviteCode;
        private long createTime;
        private String status;
        private String memberId;
        private String sameCitySend;
        private String linkman;
        private List<String> location;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSameCitySendDistance() {
            return sameCitySendDistance;
        }

        public void setSameCitySendDistance(String sameCitySendDistance) {
            this.sameCitySendDistance = sameCitySendDistance;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(String logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getSameCitySend() {
            return sameCitySend;
        }

        public void setSameCitySend(String sameCitySend) {
            this.sameCitySend = sameCitySend;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }
}
