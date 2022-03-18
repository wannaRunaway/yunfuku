package com.aifubook.book.productcar.cart;

import java.util.List;

public class CartBean {

    private List<ShopCarsBean> shopCars;

    public List<ShopCarsBean> getShopCars() {
        return shopCars;
    }

    public void setShopCars(List<ShopCarsBean> shopCars) {
        this.shopCars = shopCars;
    }

    public static class ShopCarsBean {
        /**
         * shop : {"id":32,"name":"民企大厦店","logo":"eb86ec71d82c6bf960ededcca03c579deee197b0.jpg","provinceId":330000,"districtId":330100,"cityId":330109,"remark":null,"address":"钱江世纪城民企大厦B座17楼","location":[30.23176,120.257034],"coupon":0,"createTime":1615387935218,"updateTime":1616747957042,"logisticsFee":0,"inviteCode":"WO2LG0","status":1,"sale":null,"linkman":"黄白","phone":null,"memberId":null,"sameCitySend":1,"sameCitySendDistance":10,"sn":"","key":"","printName":"","distance":null}
         * carItems : [{"productId":5541,"product":{"id":5541,"name":"世界上最伟大的博物馆：卢浮宫","subName":"","price":19800,"discountPrice":17880,"image":"3e6b39807dc9ae6875011d1ff0dc0bbcbde8f3af.png","images":["adfb4e945d25e7e5f2ac54d814bba390ba18a6e1.png"],"categoryId":116,"shopId":32,"status":2,"coupon":0,"recommend":1,"soldCount":5,"shelfTime":1615533155278,"promotion":1,"stock":1000,"freezeStoke":0,"reason":null,"createTime":1615532879611,"updateTime":1615533155278,"commissionRate":10,"pre":0,"preTime":null,"limit":0,"code":"00010","press":"广东大音音像出版社","classes":"高","subject":"历史","zeroBuy":0,"preTimeDiff":null},"count":1,"shopId":32,"updateTime":1620205841874}]
         */

        private ShopBean shop;
        private List<CarItemsBean> carItems;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public List<CarItemsBean> getCarItems() {
            return carItems;
        }

        public void setCarItems(List<CarItemsBean> carItems) {
            this.carItems = carItems;
        }

        public static class ShopBean {
            /**
             * id : 32
             * name : 民企大厦店
             * logo : eb86ec71d82c6bf960ededcca03c579deee197b0.jpg
             * provinceId : 330000
             * districtId : 330100
             * cityId : 330109
             * remark : null
             * address : 钱江世纪城民企大厦B座17楼
             * location : [30.23176,120.257034]
             * coupon : 0
             * createTime : 1615387935218
             * updateTime : 1616747957042
             * logisticsFee : 0
             * inviteCode : WO2LG0
             * status : 1
             * sale : null
             * linkman : 黄白
             * phone : null
             * memberId : null
             * sameCitySend : 1
             * sameCitySendDistance : 10
             * sn :
             * key :
             * printName :
             * distance : null
             */

            private int id;
            private String name;
            private String logo;
            private int provinceId;
            private int districtId;
            private int cityId;
            private Object remark;
            private String address;
            private int coupon;
            private long createTime;
            private long updateTime;
            private int logisticsFee;
            private String inviteCode;
            private int status;
            private Object sale;
            private String linkman;
            private Object phone;
            private Object memberId;
            private int sameCitySend;
            private int sameCitySendDistance;
            private String sn;
            private String key;
            private String printName;
            private Object distance;
            private List<Double> location;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public int getDistrictId() {
                return districtId;
            }

            public void setDistrictId(int districtId) {
                this.districtId = districtId;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCoupon() {
                return coupon;
            }

            public void setCoupon(int coupon) {
                this.coupon = coupon;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public int getLogisticsFee() {
                return logisticsFee;
            }

            public void setLogisticsFee(int logisticsFee) {
                this.logisticsFee = logisticsFee;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getSale() {
                return sale;
            }

            public void setSale(Object sale) {
                this.sale = sale;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getMemberId() {
                return memberId;
            }

            public void setMemberId(Object memberId) {
                this.memberId = memberId;
            }

            public int getSameCitySend() {
                return sameCitySend;
            }

            public void setSameCitySend(int sameCitySend) {
                this.sameCitySend = sameCitySend;
            }

            public int getSameCitySendDistance() {
                return sameCitySendDistance;
            }

            public void setSameCitySendDistance(int sameCitySendDistance) {
                this.sameCitySendDistance = sameCitySendDistance;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getPrintName() {
                return printName;
            }

            public void setPrintName(String printName) {
                this.printName = printName;
            }

            public Object getDistance() {
                return distance;
            }

            public void setDistance(Object distance) {
                this.distance = distance;
            }

            public List<Double> getLocation() {
                return location;
            }

            public void setLocation(List<Double> location) {
                this.location = location;
            }
        }

    }
}
