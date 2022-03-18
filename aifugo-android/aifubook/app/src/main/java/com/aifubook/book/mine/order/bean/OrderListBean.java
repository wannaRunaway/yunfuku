package com.aifubook.book.mine.order.bean;

import java.io.Serializable;
import java.util.List;

public class OrderListBean implements Serializable {

    /**
     * pageNo : 0
     * pageSize : 0
     * offset : 0
     * totalCount : 2
     * list : [{"id":20210503569,"items":[{"id":20210503570,"orderId":20210503569,"fee":1,"productPrice":1,"productId":5578,"count":1,"productName":"倍速学习法  六年级 语文","productSubName":"","productImage":"891c2d53b2e08d36eea3e3dc348addb7fb766681.jpg","status":0,"commission":null}],"shopId":35,"shop":{"id":35,"name":"爱辅购","logo":"e33203328bf059a9bff69deeee25252cb99eb32e.jpg","provinceId":330000,"districtId":330100,"cityId":330109,"remark":null,"address":"浙江民企大厦B座17楼","location":[30.2310661638367,120.25625672929891],"coupon":0,"createTime":1615527733855,"updateTime":1616756099514,"logisticsFee":800,"inviteCode":"5V6KBS","status":1,"sale":null,"linkman":"迦叶","phone":null,"memberId":null,"sameCitySend":1,"sameCitySendDistance":5,"sn":"920514446","key":"g794npwt","prStringName":"测试打印","distance":null},"totalFee":1,"proProductTotalFee":1,"productTotalFee":1,"memberId":105,"logisticsFee":0,"status":0,"completedTime":null,"payType":0,"payTime":null,"paymentNo":null,"address":null,"payOrderId":null,"member":null,"expressNo":null,"createTime":1620037851942,"logisticsType":2,"chiefId":null,"chief":null,"code":"vwjqz4tb","fetchTime":null,"remarks":"dolor occaecat","discountInfos":null,"refundReasons":null,"refundType":null,"refundExpressNo":null,"refundRemark":null,"refundRefuseReason":null,"refundImage":null,"refundStatus":null,"refundOperator":null,"refundApplyTime":null,"refusalReasons":null,"refundAuditTime":null,"discountMap":null,"orderTotalCommission":0},{"id":20210503568,"items":[{"id":20210503569,"orderId":20210503568,"fee":1,"productPrice":1,"productId":5578,"count":1,"productName":"倍速学习法  六年级 语文","productSubName":"","productImage":"891c2d53b2e08d36eea3e3dc348addb7fb766681.jpg","status":0,"commission":null}],"shopId":35,"shop":{"id":35,"name":"爱辅购","logo":"e33203328bf059a9bff69deeee25252cb99eb32e.jpg","provinceId":330000,"districtId":330100,"cityId":330109,"remark":null,"address":"浙江民企大厦B座17楼","location":[30.2310661638367,120.25625672929891],"coupon":0,"createTime":1615527733855,"updateTime":1616756099514,"logisticsFee":800,"inviteCode":"5V6KBS","status":1,"sale":null,"linkman":"迦叶","phone":null,"memberId":null,"sameCitySend":1,"sameCitySendDistance":5,"sn":"920514446","key":"g794npwt","prStringName":"测试打印","distance":null},"totalFee":1,"proProductTotalFee":1,"productTotalFee":1,"memberId":105,"logisticsFee":0,"status":0,"completedTime":null,"payType":0,"payTime":null,"paymentNo":null,"address":null,"payOrderId":null,"member":null,"expressNo":null,"createTime":1620037811945,"logisticsType":2,"chiefId":null,"chief":null,"code":"5xt56qrc","fetchTime":null,"remarks":"dolor occaecat","discountInfos":null,"refundReasons":null,"refundType":null,"refundExpressNo":null,"refundRemark":null,"refundRefuseReason":null,"refundImage":null,"refundStatus":null,"refundOperator":null,"refundApplyTime":null,"refusalReasons":null,"refundAuditTime":null,"discountMap":null,"orderTotalCommission":0}]
     */

    private String pageNo;
    private String pageSize;
    private String offset;
    private String totalCount;
    private List<ListBean> list;
    private boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 20210503569
         * items : [{"id":20210503570,"orderId":20210503569,"fee":1,"productPrice":1,"productId":5578,"count":1,"productName":"倍速学习法  六年级 语文","productSubName":"","productImage":"891c2d53b2e08d36eea3e3dc348addb7fb766681.jpg","status":0,"commission":null}]
         * shopId : 35
         * shop : {"id":35,"name":"爱辅购","logo":"e33203328bf059a9bff69deeee25252cb99eb32e.jpg","provinceId":330000,"districtId":330100,"cityId":330109,"remark":null,"address":"浙江民企大厦B座17楼","location":[30.2310661638367,120.25625672929891],"coupon":0,"createTime":1615527733855,"updateTime":1616756099514,"logisticsFee":800,"inviteCode":"5V6KBS","status":1,"sale":null,"linkman":"迦叶","phone":null,"memberId":null,"sameCitySend":1,"sameCitySendDistance":5,"sn":"920514446","key":"g794npwt","prStringName":"测试打印","distance":null}
         * totalFee : 1
         * proProductTotalFee : 1
         * productTotalFee : 1
         * memberId : 105
         * logisticsFee : 0
         * status : 0
         * completedTime : null
         * payType : 0
         * payTime : null
         * paymentNo : null
         * address : null
         * payOrderId : null
         * member : null
         * expressNo : null
         * createTime : 1620037851942
         * logisticsType : 2
         * chiefId : null
         * chief : null
         * code : vwjqz4tb
         * fetchTime : null
         * remarks : dolor occaecat
         * discountInfos : null
         * refundReasons : null
         * refundType : null
         * refundExpressNo : null
         * refundRemark : null
         * refundRefuseReason : null
         * refundImage : null
         * refundStatus : null
         * refundOperator : null
         * refundApplyTime : null
         * refusalReasons : null
         * refundAuditTime : null
         * discountMap : null
         * orderTotalCommission : 0
         *     private String expressCompany;
         *     private String expressNo;
         *     private String expressCompanyCode;
         *     			"expressNo": "SF1340254693978",
         * 			"expressCompany": "shunfeng",
         */

        private String expressNo;
        private String expressCompany;
        private long id;
        private String shopId;
        private ShopBean shop;
        private String totalFee;
        private String proProductTotalFee;
        private String productTotalFee;
        private String discountTotalFee;
        private String memberId;
        private String logisticsFee;
        private String status;
        private Object completedTime;
        private String payType;
        private Object payTime;
        private Object paymentNo;
        private Object address;
        private Object payOrderId;
        private Object member;
        private long createTime;
        private String logisticsType;
        private Object chiefId;
        private Object chief;
        private Object fetchTime;
        private String remarks;
        private Object discountInfos;
        private Object refundReasons;
        private Object refundType;
        private Object refundExpressNo;
        private Object refundRemark;
        private Object refundRefuseReason;
        private Object refundImage;
        private int refundStatus;
        private Object refundOperator;
        private Object refundApplyTime;
        private Object refusalReasons;
        private Object refundAuditTime;
        private Object discountMap;
        private String orderTotalCommission;
        private List<ItemsBean> items;
        private long validTime;
        private String expressCompanyCode;
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

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }

        public String getExpressCompanyCode() {
            return expressCompanyCode;
        }

        public void setExpressCompanyCode(String expressCompanyCode) {
            this.expressCompanyCode = expressCompanyCode;
        }

        public long getValidTime() {
            return validTime;
        }

        public void setValidTime(long validTime) {
            this.validTime = validTime;
        }

        public String getDiscountTotalFee() {
            return discountTotalFee;
        }

        public void setDiscountTotalFee(String discountTotalFee) {
            this.discountTotalFee = discountTotalFee;
        }

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getProProductTotalFee() {
            return proProductTotalFee;
        }

        public void setProProductTotalFee(String proProductTotalFee) {
            this.proProductTotalFee = proProductTotalFee;
        }

        public String getProductTotalFee() {
            return productTotalFee;
        }

        public void setProductTotalFee(String productTotalFee) {
            this.productTotalFee = productTotalFee;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(String logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCompletedTime() {
            return completedTime;
        }

        public void setCompletedTime(Object completedTime) {
            this.completedTime = completedTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public Object getPaymentNo() {
            return paymentNo;
        }

        public void setPaymentNo(Object paymentNo) {
            this.paymentNo = paymentNo;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getPayOrderId() {
            return payOrderId;
        }

        public void setPayOrderId(Object payOrderId) {
            this.payOrderId = payOrderId;
        }

        public Object getMember() {
            return member;
        }

        public void setMember(Object member) {
            this.member = member;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getLogisticsType() {
            return logisticsType;
        }

        public void setLogisticsType(String logisticsType) {
            this.logisticsType = logisticsType;
        }

        public Object getChiefId() {
            return chiefId;
        }

        public void setChiefId(Object chiefId) {
            this.chiefId = chiefId;
        }

        public Object getChief() {
            return chief;
        }

        public void setChief(Object chief) {
            this.chief = chief;
        }

        public Object getFetchTime() {
            return fetchTime;
        }

        public void setFetchTime(Object fetchTime) {
            this.fetchTime = fetchTime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Object getDiscountInfos() {
            return discountInfos;
        }

        public void setDiscountInfos(Object discountInfos) {
            this.discountInfos = discountInfos;
        }

        public Object getRefundReasons() {
            return refundReasons;
        }

        public void setRefundReasons(Object refundReasons) {
            this.refundReasons = refundReasons;
        }

        public Object getRefundType() {
            return refundType;
        }

        public void setRefundType(Object refundType) {
            this.refundType = refundType;
        }

        public Object getRefundExpressNo() {
            return refundExpressNo;
        }

        public void setRefundExpressNo(Object refundExpressNo) {
            this.refundExpressNo = refundExpressNo;
        }

        public Object getRefundRemark() {
            return refundRemark;
        }

        public void setRefundRemark(Object refundRemark) {
            this.refundRemark = refundRemark;
        }

        public Object getRefundRefuseReason() {
            return refundRefuseReason;
        }

        public void setRefundRefuseReason(Object refundRefuseReason) {
            this.refundRefuseReason = refundRefuseReason;
        }

        public Object getRefundImage() {
            return refundImage;
        }

        public void setRefundImage(Object refundImage) {
            this.refundImage = refundImage;
        }


        public Object getRefundOperator() {
            return refundOperator;
        }

        public void setRefundOperator(Object refundOperator) {
            this.refundOperator = refundOperator;
        }

        public Object getRefundApplyTime() {
            return refundApplyTime;
        }

        public void setRefundApplyTime(Object refundApplyTime) {
            this.refundApplyTime = refundApplyTime;
        }

        public Object getRefusalReasons() {
            return refusalReasons;
        }

        public void setRefusalReasons(Object refusalReasons) {
            this.refusalReasons = refusalReasons;
        }

        public Object getRefundAuditTime() {
            return refundAuditTime;
        }

        public void setRefundAuditTime(Object refundAuditTime) {
            this.refundAuditTime = refundAuditTime;
        }

        public Object getDiscountMap() {
            return discountMap;
        }

        public void setDiscountMap(Object discountMap) {
            this.discountMap = discountMap;
        }

        public String getOrderTotalCommission() {
            return orderTotalCommission;
        }

        public void setOrderTotalCommission(String orderTotalCommission) {
            this.orderTotalCommission = orderTotalCommission;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ShopBean implements Serializable {
            /**
             * id : 35
             * name : 爱辅购
             * logo : e33203328bf059a9bff69deeee25252cb99eb32e.jpg
             * provinceId : 330000
             * districtId : 330100
             * cityId : 330109
             * remark : null
             * address : 浙江民企大厦B座17楼
             * location : [30.2310661638367,120.25625672929891]
             * coupon : 0
             * createTime : 1615527733855
             * updateTime : 1616756099514
             * logisticsFee : 800
             * inviteCode : 5V6KBS
             * status : 1
             * sale : null
             * linkman : 迦叶
             * phone : null
             * memberId : null
             * sameCitySend : 1
             * sameCitySendDistance : 5
             * sn : 920514446
             * key : g794npwt
             * prStringName : 测试打印
             * distance : null
             */

            private String id;
            private String name;
            private String logo;
            private String provinceId;
            private String districtId;
            private String cityId;
            private Object remark;
            private String address;
            private String coupon;
            private long createTime;
            private long updateTime;
            private String logisticsFee;
            private String inviteCode;
            private String status;
            private Object sale;
            private String linkman;
            private String phone;
            private Object memberId;
            private String sameCitySend;
            private String sameCitySendDistance;
            private String sn;
            private String key;
            private String prStringName;
            private Object distance;
            private List<String> location;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
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

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
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

            public String getLogisticsFee() {
                return logisticsFee;
            }

            public void setLogisticsFee(String logisticsFee) {
                this.logisticsFee = logisticsFee;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getMemberId() {
                return memberId;
            }

            public void setMemberId(Object memberId) {
                this.memberId = memberId;
            }

            public String getSameCitySend() {
                return sameCitySend;
            }

            public void setSameCitySend(String sameCitySend) {
                this.sameCitySend = sameCitySend;
            }

            public String getSameCitySendDistance() {
                return sameCitySendDistance;
            }

            public void setSameCitySendDistance(String sameCitySendDistance) {
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

            public String getPrStringName() {
                return prStringName;
            }

            public void setPrStringName(String prStringName) {
                this.prStringName = prStringName;
            }

            public Object getDistance() {
                return distance;
            }

            public void setDistance(Object distance) {
                this.distance = distance;
            }

            public List<String> getLocation() {
                return location;
            }

            public void setLocation(List<String> location) {
                this.location = location;
            }
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", shopId='" + shopId + '\'' +
                    ", shop=" + shop +
                    ", totalFee='" + totalFee + '\'' +
                    ", proProductTotalFee='" + proProductTotalFee + '\'' +
                    ", productTotalFee='" + productTotalFee + '\'' +
                    ", discountTotalFee='" + discountTotalFee + '\'' +
                    ", memberId='" + memberId + '\'' +
                    ", logisticsFee='" + logisticsFee + '\'' +
                    ", status='" + status + '\'' +
                    ", completedTime=" + completedTime +
                    ", payType='" + payType + '\'' +
                    ", payTime=" + payTime +
                    ", paymentNo=" + paymentNo +
                    ", address=" + address +
                    ", payOrderId=" + payOrderId +
                    ", member=" + member +
                    ", expressNo=" + expressNo +
                    ", createTime=" + createTime +
                    ", logisticsType='" + logisticsType + '\'' +
                    ", chiefId=" + chiefId +
                    ", chief=" + chief +
                    ", fetchTime=" + fetchTime +
                    ", remarks='" + remarks + '\'' +
                    ", discountInfos=" + discountInfos +
                    ", refundReasons=" + refundReasons +
                    ", refundType=" + refundType +
                    ", refundExpressNo=" + refundExpressNo +
                    ", refundRemark=" + refundRemark +
                    ", refundRefuseReason=" + refundRefuseReason +
                    ", refundImage=" + refundImage +
                    ", refundStatus=" + refundStatus +
                    ", refundOperator=" + refundOperator +
                    ", refundApplyTime=" + refundApplyTime +
                    ", refusalReasons=" + refusalReasons +
                    ", refundAuditTime=" + refundAuditTime +
                    ", discountMap=" + discountMap +
                    ", orderTotalCommission='" + orderTotalCommission + '\'' +
                    ", items=" + items +
                    '}';
        }
    }
}
