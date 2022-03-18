package com.aifubook.book.productcar.cart;

import java.io.Serializable;
import java.util.List;

public class CartGetBean implements Serializable {

    /**
     * shopCars : [{"shop":{"provinceId":5071,"memberId":1780,"sameCitySend":7718,"status":1,"coupon":0,"inviteCode":"ex eiusmod amet","id":6304,"createTime":1619933706070,"linkman":"enim ea ipsum","updateTime":1619933706631,"logisticsFee":640,"location":[4482.534760874223,711.74],"sameCitySendDistance":6412,"name":"voluptate velit qui quis","address":"往江走路1395号","phone":"18826553214","remark":"fugiat pariatur","districtId":8519,"logo":"anim incididunt fugiat occaecat Ut","distance":3422.81486,"cityId":6501},"carItems":[{"product":{"reason":"in adipisicing","updateTime":1619933706110,"image":"cupidatat","commissionRate":8859,"categoryId":4576,"preTime":1375,"createTime":1619933706002,"limit":5411,"name":"dolore sString Lorem veniam ullamco","shopId":3311,"recommend":1,"price":3726,"stock":4968,"id":9321,"images":["in pariatur sString","est labore do","non"],"soldCount":4212,"status":1,"coupon":1,"pre":0,"subject":"ipsum","freezeStoke":8736,"code":"ex magna","subName":"quis sString aute","press":"quis labore deserunt voluptate nostrud","classes":"in Duis do sunt","zeroBuy":1,"discountPrice":6136,"shelfTime":8017,"promotion":6637},"updateTime":1619933706104,"shopId":1424,"count":8133,"productId":927},{"productId":7737,"updateTime":1619933706810,"count":5810,"product":{"zeroBuy":0,"shelfTime":3381,"id":7618,"name":"elit Lorem labore dolor fugiat","promotion":3736,"categoryId":4070,"updateTime":1619933706935,"status":0,"image":"adipisicing","discountPrice":9874,"reason":"do quis","subject":"culpa","press":"aliqua Duis","subName":"dolor sunt Lorem ullamco","shopId":419,"soldCount":6688,"recommend":0,"coupon":0,"images":["consequat nulla culpa eu sed","dolore voluptate dolor in","irure sunt pariatur ea","labore non dolore","consequat"],"freezeStoke":4060,"price":1310,"limit":2781,"code":"dolore","commissionRate":5247,"preTime":980,"classes":"deserunt id","createTime":1619933706574,"pre":1,"stock":7686},"shopId":7704},{"productId":9208,"shopId":7323,"count":1548,"updateTime":1619933706818,"product":{"pre":0,"preTime":6566,"status":2,"shopId":4280,"createTime":1619933706057,"classes":"incididunt","price":8860,"recommend":0,"freezeStoke":1332,"images":["est adipisicing magna nisi ex","cupidatat","ipsum ullamco","labore proident esse consectetur velit"],"stock":6476,"shelfTime":8431,"code":"consequat dolor do id","id":9809,"coupon":0,"categoryId":1650,"image":"mollit aliquip pariatur cupidatat","press":"veniam","reason":"fugiat dolor","name":"veniam do adipisicing ex","discountPrice":7675,"updateTime":1619933706370,"limit":942,"zeroBuy":1,"subject":"ea","commissionRate":4981,"promotion":7060,"soldCount":516,"subName":"velit sString in irure"}}]}]
     * sStringa7 : -8436440
     * elit_781 : -3.2092871992985874E7
     */

    private String sStringa7;
    private String elit_781;
    private List<ShopCarsBean> shopCars;

    public String getSStringa7() {
        return sStringa7;
    }

    public void setSStringa7(String sStringa7) {
        this.sStringa7 = sStringa7;
    }

    public String getElit_781() {
        return elit_781;
    }

    public void setElit_781(String elit_781) {
        this.elit_781 = elit_781;
    }

    public List<ShopCarsBean> getShopCars() {
        return shopCars;
    }

    public void setShopCars(List<ShopCarsBean> shopCars) {
        this.shopCars = shopCars;
    }

    public static class ShopCarsBean implements Serializable {
        /**
         * shop : {"provinceId":5071,"memberId":1780,"sameCitySend":7718,"status":1,"coupon":0,"inviteCode":"ex eiusmod amet","id":6304,"createTime":1619933706070,"linkman":"enim ea ipsum","updateTime":1619933706631,"logisticsFee":640,"location":[4482.534760874223,711.74],"sameCitySendDistance":6412,"name":"voluptate velit qui quis","address":"往江走路1395号","phone":"18826553214","remark":"fugiat pariatur","districtId":8519,"logo":"anim incididunt fugiat occaecat Ut","distance":3422.81486,"cityId":6501}
         * carItems : [{"product":{"reason":"in adipisicing","updateTime":1619933706110,"image":"cupidatat","commissionRate":8859,"categoryId":4576,"preTime":1375,"createTime":1619933706002,"limit":5411,"name":"dolore sString Lorem veniam ullamco","shopId":3311,"recommend":1,"price":3726,"stock":4968,"id":9321,"images":["in pariatur sString","est labore do","non"],"soldCount":4212,"status":1,"coupon":1,"pre":0,"subject":"ipsum","freezeStoke":8736,"code":"ex magna","subName":"quis sString aute","press":"quis labore deserunt voluptate nostrud","classes":"in Duis do sunt","zeroBuy":1,"discountPrice":6136,"shelfTime":8017,"promotion":6637},"updateTime":1619933706104,"shopId":1424,"count":8133,"productId":927},{"productId":7737,"updateTime":1619933706810,"count":5810,"product":{"zeroBuy":0,"shelfTime":3381,"id":7618,"name":"elit Lorem labore dolor fugiat","promotion":3736,"categoryId":4070,"updateTime":1619933706935,"status":0,"image":"adipisicing","discountPrice":9874,"reason":"do quis","subject":"culpa","press":"aliqua Duis","subName":"dolor sunt Lorem ullamco","shopId":419,"soldCount":6688,"recommend":0,"coupon":0,"images":["consequat nulla culpa eu sed","dolore voluptate dolor in","irure sunt pariatur ea","labore non dolore","consequat"],"freezeStoke":4060,"price":1310,"limit":2781,"code":"dolore","commissionRate":5247,"preTime":980,"classes":"deserunt id","createTime":1619933706574,"pre":1,"stock":7686},"shopId":7704},{"productId":9208,"shopId":7323,"count":1548,"updateTime":1619933706818,"product":{"pre":0,"preTime":6566,"status":2,"shopId":4280,"createTime":1619933706057,"classes":"incididunt","price":8860,"recommend":0,"freezeStoke":1332,"images":["est adipisicing magna nisi ex","cupidatat","ipsum ullamco","labore proident esse consectetur velit"],"stock":6476,"shelfTime":8431,"code":"consequat dolor do id","id":9809,"coupon":0,"categoryId":1650,"image":"mollit aliquip pariatur cupidatat","press":"veniam","reason":"fugiat dolor","name":"veniam do adipisicing ex","discountPrice":7675,"updateTime":1619933706370,"limit":942,"zeroBuy":1,"subject":"ea","commissionRate":4981,"promotion":7060,"soldCount":516,"subName":"velit sString in irure"}}]
         */

        private ShopBean shop;
        private List<CarItemsBean> carItems;

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

        public static class ShopBean implements Serializable {
            /**
             * provinceId : 5071
             * memberId : 1780
             * sameCitySend : 7718
             * status : 1
             * coupon : 0
             * inviteCode : ex eiusmod amet
             * id : 6304
             * createTime : 1619933706070
             * linkman : enim ea ipsum
             * updateTime : 1619933706631
             * logisticsFee : 640
             * location : [4482.534760874223,711.74]
             * sameCitySendDistance : 6412
             * name : voluptate velit qui quis
             * address : 往江走路1395号
             * phone : 18826553214
             * remark : fugiat pariatur
             * districtId : 8519
             * logo : anim incididunt fugiat occaecat Ut
             * distance : 3422.81486
             * cityId : 6501
             */

            private String provinceId;
            private String memberId;
            private String sameCitySend;
            private String status;
            private String coupon;
            private String inviteCode;
            private String id;
            private long createTime;
            private String linkman;
            private long updateTime;
            private String logisticsFee;
            private String sameCitySendDistance;
            private String name;
            private String address;
            private String phone;
            private String remark;
            private String districtId;
            private String logo;
            private String distance;
            private String cityId;
            private List<String> location;

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getLogisticsFee() {
                return logisticsFee;
            }

            public void setLogisticsFee(String logisticsFee) {
                this.logisticsFee = logisticsFee;
            }

            public String getSameCitySendDistance() {
                return sameCitySendDistance;
            }

            public void setSameCitySendDistance(String sameCitySendDistance) {
                this.sameCitySendDistance = sameCitySendDistance;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public List<String> getLocation() {
                return location;
            }

            public void setLocation(List<String> location) {
                this.location = location;
            }
        }
        
        public static class CarItemsBean implements Serializable {
            /**
             * product : {"reason":"in adipisicing","updateTime":1619933706110,"image":"cupidatat","commissionRate":8859,"categoryId":4576,"preTime":1375,"createTime":1619933706002,"limit":5411,"name":"dolore sString Lorem veniam ullamco","shopId":3311,"recommend":1,"price":3726,"stock":4968,"id":9321,"images":["in pariatur sString","est labore do","non"],"soldCount":4212,"status":1,"coupon":1,"pre":0,"subject":"ipsum","freezeStoke":8736,"code":"ex magna","subName":"quis sString aute","press":"quis labore deserunt voluptate nostrud","classes":"in Duis do sunt","zeroBuy":1,"discountPrice":6136,"shelfTime":8017,"promotion":6637}
             * updateTime : 1619933706104
             * shopId : 1424
             * count : 8133
             * productId : 927
             */

            private ProductBean product;
            private long updateTime;
            private String shopId;
            private String count;
            private String productId;

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public static class ProductBean implements Serializable {
                /**
                 * reason : in adipisicing
                 * updateTime : 1619933706110
                 * image : cupidatat
                 * commissionRate : 8859
                 * categoryId : 4576
                 * preTime : 1375
                 * createTime : 1619933706002
                 * limit : 5411
                 * name : dolore sString Lorem veniam ullamco
                 * shopId : 3311
                 * recommend : 1
                 * price : 3726
                 * stock : 4968
                 * id : 9321
                 * images : ["in pariatur sString","est labore do","non"]
                 * soldCount : 4212
                 * status : 1
                 * coupon : 1
                 * pre : 0
                 * subject : ipsum
                 * freezeStoke : 8736
                 * code : ex magna
                 * subName : quis sString aute
                 * press : quis labore deserunt voluptate nostrud
                 * classes : in Duis do sunt
                 * zeroBuy : 1
                 * discountPrice : 6136
                 * shelfTime : 8017
                 * promotion : 6637
                 */

                private String reason;
                private long updateTime;
                private String image;
                private String commissionRate;
                private String categoryId;
                private String preTime;
                private long createTime;
                private String limit;
                private String name;
                private String shopId;
                private String recommend;
                private String price;
                private String stock;
                private String id;
                private String soldCount;
                private String status;
                private String coupon;
                private String pre;
                private String subject;
                private String freezeStoke;
                private String code;
                private String subName;
                private String press;
                private String classes;
                private String zeroBuy;
                private String discountPrice;
                private String shelfTime;
                private String promotion;
                private List<String> images;

                public String getReason() {
                    return reason;
                }

                public void setReason(String reason) {
                    this.reason = reason;
                }

                public long getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(long updateTime) {
                    this.updateTime = updateTime;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getCommissionRate() {
                    return commissionRate;
                }

                public void setCommissionRate(String commissionRate) {
                    this.commissionRate = commissionRate;
                }

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getPreTime() {
                    return preTime;
                }

                public void setPreTime(String preTime) {
                    this.preTime = preTime;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public String getLimit() {
                    return limit;
                }

                public void setLimit(String limit) {
                    this.limit = limit;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getShopId() {
                    return shopId;
                }

                public void setShopId(String shopId) {
                    this.shopId = shopId;
                }

                public String getRecommend() {
                    return recommend;
                }

                public void setRecommend(String recommend) {
                    this.recommend = recommend;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getStock() {
                    return stock;
                }

                public void setStock(String stock) {
                    this.stock = stock;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSoldCount() {
                    return soldCount;
                }

                public void setSoldCount(String soldCount) {
                    this.soldCount = soldCount;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getCoupon() {
                    return coupon;
                }

                public void setCoupon(String coupon) {
                    this.coupon = coupon;
                }

                public String getPre() {
                    return pre;
                }

                public void setPre(String pre) {
                    this.pre = pre;
                }

                public String getSubject() {
                    return subject;
                }

                public void setSubject(String subject) {
                    this.subject = subject;
                }

                public String getFreezeStoke() {
                    return freezeStoke;
                }

                public void setFreezeStoke(String freezeStoke) {
                    this.freezeStoke = freezeStoke;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getSubName() {
                    return subName;
                }

                public void setSubName(String subName) {
                    this.subName = subName;
                }

                public String getPress() {
                    return press;
                }

                public void setPress(String press) {
                    this.press = press;
                }

                public String getClasses() {
                    return classes;
                }

                public void setClasses(String classes) {
                    this.classes = classes;
                }

                public String getZeroBuy() {
                    return zeroBuy;
                }

                public void setZeroBuy(String zeroBuy) {
                    this.zeroBuy = zeroBuy;
                }

                public String getDiscountPrice() {
                    return discountPrice;
                }

                public void setDiscountPrice(String discountPrice) {
                    this.discountPrice = discountPrice;
                }

                public String getShelfTime() {
                    return shelfTime;
                }

                public void setShelfTime(String shelfTime) {
                    this.shelfTime = shelfTime;
                }

                public String getPromotion() {
                    return promotion;
                }

                public void setPromotion(String promotion) {
                    this.promotion = promotion;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }
            }
        }
    }
}
