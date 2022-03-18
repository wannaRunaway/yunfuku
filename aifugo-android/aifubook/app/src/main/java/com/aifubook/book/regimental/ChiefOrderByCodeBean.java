package com.aifubook.book.regimental;

import java.io.Serializable;
import java.util.List;


public class ChiefOrderByCodeBean implements Serializable {

    /**
     * status : 0
     * refundImage : aliquip
     * refundRefuseReason : qui occaecat reprehenderit
     * memberId : 4369
     * refundExpressNo : dolor reprehenderit qui veniam labore
     * chiefId : 4005
     * refundStatus : 8
     * address : {"mobile":"13828342379","id":8299,"districtId":2104,"address":"话断回路1010号","memberId":1844,"province":"sString deserunt","provinceId":7506,"defaultAddress":false,"location":[9965.969251983264],"cityId":4996,"name":"laboris ut nulla","city":"Duis in in eu anim","district":"labore"}
     * expressNo : elit Duis dolore
     * remarks : in
     * refundApplyTime : 5379
     * fetchTime : 8958
     * createTime : 1620802850674
     * productTotalFee : 9044
     * shop : {"provinceId":9964,"coupon":1,"phone":"1584861149","createTime":1620802850518,"location":[54.733],"memberId":669,"sameCitySendDistance":5938,"inviteCode":"fugiat cupidatat non","logo":"aute id consequat nisi tempor","sn":"amet reprehenderit sunt magna","address":"它按县路180号","sameCitySend":876,"id":8356,"prStringName":"Excepteur ex do Lorem dolore","distance":429.6193791,"logisticsFee":2713,"sale":4110.837958,"name":"nostrud aliqua Excepteur","updateTime":1620802850586,"cityId":291,"key":"in magna enim qui","remark":"occaecat adipisicing sString in","status":2,"linkman":"culpa quis veniam","districtId":861}
     * refundAuditTime : 1327
     * refundOperator : 6833
     * logisticsFee : 5164
     * completedTime : 16
     * items : [{"orderId":8298,"id":2232,"productSubName":"velit","commission":9056,"fee":2322,"productPrice":7581,"productId":9704,"count":829,"status":4,"productName":"in officia commodo incididunt","productImage":"aliqua et laboris"}]
     * paymentNo : cupidatat
     * refusalReasons : proident
     * shopId : 702
     * refundRemark : sunt
     * code : labore
     * chief : {"districtId":4959,"address":"知美路1314号","province":"tempor nisi labore","updateTime":1620802850001,"provinceId":8807,"clazz":{"phone":"15760673546","id":550,"name":"sed Excepteur labore dolore","owner":"in id dolore eiusmod fugiat","schoolId":8758},"createTime":1620802850053,"credentials":"mollit sit nulla minim","schoolId":8438,"school":{"city":"mollit Excepteur","name":"consectetur ut irure","province":"adipisicing","phone":"18835847374","districtId":2727,"district":"enim","provinceId":3428,"id":8297,"lng":[1893.7269558144176,354.1908518224589,591.8875548268,9304.7344483835],"address":"程么需路599号","cityId":9727,"use":1,"linkman":"laboris Lorem cupidatat"},"status":0,"shopId":4476,"district":"elit occaecat veniam sunt","mobile":"13703235432","applyTime":3467,"inviteCode":"in aliquip commodo Lorem deserunt","name":"est","city":"consequat","id":1418,"jobId":8678,"cityId":5907,"member":{"sex":2,"className":"aute","name":"Ut est occaecat","status":99,"district":"non consectetur enim","districtId":3285,"birthday":"esse","logo":"qui","schoolName":"pariatur ea","createTime":1620802850762,"id":7675,"email":"k.ytswdvuyt@ryrbsaypq.gw","classId":2005,"nickname":"tempor","province":"sString ipsum","username":"Excepteur dolor","schoolId":5297,"city":"fugiat","age":1730,"cityId":2481,"shopId":7032,"recommendId":4055,"provinceId":9352},"classId":515,"auditTime":8767,"memberId":5705,"shop":{"coupon":1,"location":[3452.74815901536],"updateTime":1620802850421,"key":"Excepteur in dolor sit in","name":"qui ea labore et proident","sameCitySendDistance":1563,"inviteCode":"in laboris nostrud quis","status":2,"cityId":4740,"districtId":1662,"sameCitySend":8823,"provinceId":115,"prStringName":"officia eu labore irure","createTime":1620802850282,"id":5235,"logisticsFee":2283,"sn":"in elit minim id anim","phone":"13566976550","distance":2055.7076819343,"remark":"laborum ullamco occaecat cupidatat","memberId":3836,"logo":"labore incididunt irure veniam","linkman":"cillum dolore dolor","address":"命常路886号","sale":8421.1798}}
     * payType : 8
     * id : 6398
     * payTime : 3527
     * logisticsType : 1
     * refundType : 0
     * payOrderId : 2104
     * refundReasons : [2615,5567]
     * totalFee : 678
     * discountInfos : [{"discountType":0,"orderId":8606,"objId":4709,"id":4583,"value":2323},{"discountType":0,"id":9044,"orderId":292,"value":1127,"objId":1733},{"value":9593,"discountType":0,"orderId":4125,"id":9596,"objId":9476},{"objId":7958,"id":6403,"value":6409,"discountType":0,"orderId":184},{"id":2906,"value":7623,"discountType":0,"objId":9802,"orderId":4714}]
     * proProductTotalFee : 3941
     * member : {"createTime":1620802850291,"email":"n.ulvxquw@luqczh.cd","id":7311,"sex":1,"birthday":"ut","shopId":3331,"province":"fugiat cupidatat eiusmod occaecat","username":"aute quis aliqua","name":"in occaecat nostrud laborum","districtId":2870,"status":0,"cityId":3032,"classId":9061,"schoolId":6019,"age":7728,"city":"id pariatur occaecat","district":"magna minim","schoolName":"non","nickname":"quis dolore tempor","recommendId":1554,"className":"ea ullamco voluptate enim","provinceId":2740,"logo":"non mollit labore"}
     */

    private String status;
    private String refundImage;
    private String refundRefuseReason;
    private String memberId;
    private String refundExpressNo;
    private String chiefId;
    private String refundStatus;
    private AddressBean address;
    private String expressNo;
    private String remarks;
    private String refundApplyTime;
    private String fetchTime;
    private long createTime;
    private String productTotalFee;
    private ShopBean shop;
    private String refundAuditTime;
    private String refundOperator;
    private String logisticsFee;
    private String completedTime;
    private String paymentNo;
    private String refusalReasons;
    private String shopId;
    private String refundRemark;
    private String code;
    private ChiefBean chief;
    private String payType;
    private String id;
    private String payTime;
    private String logisticsType;
    private String refundType;
    private String payOrderId;
    private String totalFee;
    private String proProductTotalFee;
    private MemberBeanX member;
    private List<ItemsBean> items;
    private List<Integer> refundReasons;
    private List<DiscountInfosBean> discountInfos;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundImage() {
        return refundImage;
    }

    public void setRefundImage(String refundImage) {
        this.refundImage = refundImage;
    }

    public String getRefundRefuseReason() {
        return refundRefuseReason;
    }

    public void setRefundRefuseReason(String refundRefuseReason) {
        this.refundRefuseReason = refundRefuseReason;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRefundExpressNo() {
        return refundExpressNo;
    }

    public void setRefundExpressNo(String refundExpressNo) {
        this.refundExpressNo = refundExpressNo;
    }

    public String getChiefId() {
        return chiefId;
    }

    public void setChiefId(String chiefId) {
        this.chiefId = chiefId;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRefundApplyTime() {
        return refundApplyTime;
    }

    public void setRefundApplyTime(String refundApplyTime) {
        this.refundApplyTime = refundApplyTime;
    }

    public String getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(String fetchTime) {
        this.fetchTime = fetchTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getProductTotalFee() {
        return productTotalFee;
    }

    public void setProductTotalFee(String productTotalFee) {
        this.productTotalFee = productTotalFee;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public String getRefundAuditTime() {
        return refundAuditTime;
    }

    public void setRefundAuditTime(String refundAuditTime) {
        this.refundAuditTime = refundAuditTime;
    }

    public String getRefundOperator() {
        return refundOperator;
    }

    public void setRefundOperator(String refundOperator) {
        this.refundOperator = refundOperator;
    }

    public String getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(String logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getRefusalReasons() {
        return refusalReasons;
    }

    public void setRefusalReasons(String refusalReasons) {
        this.refusalReasons = refusalReasons;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ChiefBean getChief() {
        return chief;
    }

    public void setChief(ChiefBean chief) {
        this.chief = chief;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
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

    public MemberBeanX getMember() {
        return member;
    }

    public void setMember(MemberBeanX member) {
        this.member = member;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public List<Integer> getRefundReasons() {
        return refundReasons;
    }

    public void setRefundReasons(List<Integer> refundReasons) {
        this.refundReasons = refundReasons;
    }

    public List<DiscountInfosBean> getDiscountInfos() {
        return discountInfos;
    }

    public void setDiscountInfos(List<DiscountInfosBean> discountInfos) {
        this.discountInfos = discountInfos;
    }
    
    public static class AddressBean implements Serializable {
        /**
         * mobile : 13828342379
         * id : 8299
         * districtId : 2104
         * address : 话断回路1010号
         * memberId : 1844
         * province : sString deserunt
         * provinceId : 7506
         * defaultAddress : false
         * location : [9965.969251983264]
         * cityId : 4996
         * name : laboris ut nulla
         * city : Duis in in eu anim
         * district : labore
         */

        private String mobile;
        private String id;
        private String districtId;
        private String address;
        private String memberId;
        private String province;
        private String provinceId;
        private boolean defaultAddress;
        private String cityId;
        private String name;
        private String city;
        private String district;
        private List<String> location;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public boolean isDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(boolean defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }
    
    public static class ShopBean implements Serializable {
        /**
         * provinceId : 9964
         * coupon : 1
         * phone : 1584861149
         * createTime : 1620802850518
         * location : [54.733]
         * memberId : 669
         * sameCitySendDistance : 5938
         * inviteCode : fugiat cupidatat non
         * logo : aute id consequat nisi tempor
         * sn : amet reprehenderit sunt magna
         * address : 它按县路180号
         * sameCitySend : 876
         * id : 8356
         * prStringName : Excepteur ex do Lorem dolore
         * distance : 429.6193791
         * logisticsFee : 2713
         * sale : 4110.837958
         * name : nostrud aliqua Excepteur
         * updateTime : 1620802850586
         * cityId : 291
         * key : in magna enim qui
         * remark : occaecat adipisicing sString in
         * status : 2
         * linkman : culpa quis veniam
         * districtId : 861
         */

        private String provinceId;
        private String coupon;
        private String phone;
        private long createTime;
        private String memberId;
        private String sameCitySendDistance;
        private String inviteCode;
        private String logo;
        private String sn;
        private String address;
        private String sameCitySend;
        private String id;
        private String prStringName;
        private String distance;
        private String logisticsFee;
        private String sale;
        private String name;
        private long updateTime;
        private String cityId;
        private String key;
        private String remark;
        private String status;
        private String linkman;
        private String districtId;
        private List<String> location;

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getSameCitySendDistance() {
            return sameCitySendDistance;
        }

        public void setSameCitySendDistance(String sameCitySendDistance) {
            this.sameCitySendDistance = sameCitySendDistance;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSameCitySend() {
            return sameCitySend;
        }

        public void setSameCitySend(String sameCitySend) {
            this.sameCitySend = sameCitySend;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrStringName() {
            return prStringName;
        }

        public void setPrStringName(String prStringName) {
            this.prStringName = prStringName;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(String logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }
    
    public static class ChiefBean implements Serializable {
        /**
         * districtId : 4959
         * address : 知美路1314号
         * province : tempor nisi labore
         * updateTime : 1620802850001
         * provinceId : 8807
         * clazz : {"phone":"15760673546","id":550,"name":"sed Excepteur labore dolore","owner":"in id dolore eiusmod fugiat","schoolId":8758}
         * createTime : 1620802850053
         * credentials : mollit sit nulla minim
         * schoolId : 8438
         * school : {"city":"mollit Excepteur","name":"consectetur ut irure","province":"adipisicing","phone":"18835847374","districtId":2727,"district":"enim","provinceId":3428,"id":8297,"lng":[1893.7269558144176,354.1908518224589,591.8875548268,9304.7344483835],"address":"程么需路599号","cityId":9727,"use":1,"linkman":"laboris Lorem cupidatat"}
         * status : 0
         * shopId : 4476
         * district : elit occaecat veniam sunt
         * mobile : 13703235432
         * applyTime : 3467
         * inviteCode : in aliquip commodo Lorem deserunt
         * name : est
         * city : consequat
         * id : 1418
         * jobId : 8678
         * cityId : 5907
         * member : {"sex":2,"className":"aute","name":"Ut est occaecat","status":99,"district":"non consectetur enim","districtId":3285,"birthday":"esse","logo":"qui","schoolName":"pariatur ea","createTime":1620802850762,"id":7675,"email":"k.ytswdvuyt@ryrbsaypq.gw","classId":2005,"nickname":"tempor","province":"sString ipsum","username":"Excepteur dolor","schoolId":5297,"city":"fugiat","age":1730,"cityId":2481,"shopId":7032,"recommendId":4055,"provinceId":9352}
         * classId : 515
         * auditTime : 8767
         * memberId : 5705
         * shop : {"coupon":1,"location":[3452.74815901536],"updateTime":1620802850421,"key":"Excepteur in dolor sit in","name":"qui ea labore et proident","sameCitySendDistance":1563,"inviteCode":"in laboris nostrud quis","status":2,"cityId":4740,"districtId":1662,"sameCitySend":8823,"provinceId":115,"prStringName":"officia eu labore irure","createTime":1620802850282,"id":5235,"logisticsFee":2283,"sn":"in elit minim id anim","phone":"13566976550","distance":2055.7076819343,"remark":"laborum ullamco occaecat cupidatat","memberId":3836,"logo":"labore incididunt irure veniam","linkman":"cillum dolore dolor","address":"命常路886号","sale":8421.1798}
         */

        private String districtId;
        private String address;
        private String province;
        private long updateTime;
        private String provinceId;
        private ClazzBean clazz;
        private long createTime;
        private String credentials;
        private String schoolId;
        private SchoolBean school;
        private String status;
        private String shopId;
        private String district;
        private String mobile;
        private String applyTime;
        private String inviteCode;
        private String name;
        private String city;
        private String id;
        private String jobId;
        private String cityId;
        private MemberBean member;
        private String classId;
        private String auditTime;
        private String memberId;
        private ShopBeanX shop;

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public ClazzBean getClazz() {
            return clazz;
        }

        public void setClazz(ClazzBean clazz) {
            this.clazz = clazz;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public ShopBeanX getShop() {
            return shop;
        }

        public void setShop(ShopBeanX shop) {
            this.shop = shop;
        }

        public static class ClazzBean implements Serializable {
            /**
             * phone : 15760673546
             * id : 550
             * name : sed Excepteur labore dolore
             * owner : in id dolore eiusmod fugiat
             * schoolId : 8758
             */

            private String phone;
            private String id;
            private String name;
            private String owner;
            private String schoolId;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

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

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(String schoolId) {
                this.schoolId = schoolId;
            }
        }

        
        public static class SchoolBean implements Serializable {
            /**
             * city : mollit Excepteur
             * name : consectetur ut irure
             * province : adipisicing
             * phone : 18835847374
             * districtId : 2727
             * district : enim
             * provinceId : 3428
             * id : 8297
             * lng : [1893.7269558144176,354.1908518224589,591.8875548268,9304.7344483835]
             * address : 程么需路599号
             * cityId : 9727
             * use : 1
             * linkman : laboris Lorem cupidatat
             */

            private String city;
            private String name;
            private String province;
            private String phone;
            private String districtId;
            private String district;
            private String provinceId;
            private String id;
            private String address;
            private String cityId;
            private String use;
            private String linkman;
            private List<String> lng;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getUse() {
                return use;
            }

            public void setUse(String use) {
                this.use = use;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public List<String> getLng() {
                return lng;
            }

            public void setLng(List<String> lng) {
                this.lng = lng;
            }
        }

      
        public static class MemberBean implements Serializable {
            /**
             * sex : 2
             * className : aute
             * name : Ut est occaecat
             * status : 99
             * district : non consectetur enim
             * districtId : 3285
             * birthday : esse
             * logo : qui
             * schoolName : pariatur ea
             * createTime : 1620802850762
             * id : 7675
             * email : k.ytswdvuyt@ryrbsaypq.gw
             * classId : 2005
             * nickname : tempor
             * province : sString ipsum
             * username : Excepteur dolor
             * schoolId : 5297
             * city : fugiat
             * age : 1730
             * cityId : 2481
             * shopId : 7032
             * recommendId : 4055
             * provinceId : 9352
             */

            private String sex;
            private String className;
            private String name;
            private String status;
            private String district;
            private String districtId;
            private String birthday;
            private String logo;
            private String schoolName;
            private long createTime;
            private String id;
            private String email;
            private String classId;
            private String nickname;
            private String province;
            private String username;
            private String schoolId;
            private String city;
            private String age;
            private String cityId;
            private String shopId;
            private String recommendId;
            private String provinceId;

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(String schoolId) {
                this.schoolId = schoolId;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(String recommendId) {
                this.recommendId = recommendId;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }
        }
        
        public static class ShopBeanX implements Serializable {
            /**
             * coupon : 1
             * location : [3452.74815901536]
             * updateTime : 1620802850421
             * key : Excepteur in dolor sit in
             * name : qui ea labore et proident
             * sameCitySendDistance : 1563
             * inviteCode : in laboris nostrud quis
             * status : 2
             * cityId : 4740
             * districtId : 1662
             * sameCitySend : 8823
             * provinceId : 115
             * prStringName : officia eu labore irure
             * createTime : 1620802850282
             * id : 5235
             * logisticsFee : 2283
             * sn : in elit minim id anim
             * phone : 13566976550
             * distance : 2055.7076819343
             * remark : laborum ullamco occaecat cupidatat
             * memberId : 3836
             * logo : labore incididunt irure veniam
             * linkman : cillum dolore dolor
             * address : 命常路886号
             * sale : 8421.1798
             */

            private String coupon;
            private long updateTime;
            private String key;
            private String name;
            private String sameCitySendDistance;
            private String inviteCode;
            private String status;
            private String cityId;
            private String districtId;
            private String sameCitySend;
            private String provinceId;
            private String prStringName;
            private long createTime;
            private String id;
            private String logisticsFee;
            private String sn;
            private String phone;
            private String distance;
            private String remark;
            private String memberId;
            private String logo;
            private String linkman;
            private String address;
            private String sale;
            private List<String> location;

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

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

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getSameCitySend() {
                return sameCitySend;
            }

            public void setSameCitySend(String sameCitySend) {
                this.sameCitySend = sameCitySend;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }

            public String getPrStringName() {
                return prStringName;
            }

            public void setPrStringName(String prStringName) {
                this.prStringName = prStringName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLogisticsFee() {
                return logisticsFee;
            }

            public void setLogisticsFee(String logisticsFee) {
                this.logisticsFee = logisticsFee;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public List<String> getLocation() {
                return location;
            }

            public void setLocation(List<String> location) {
                this.location = location;
            }
        }
    }
    
    public static class MemberBeanX implements Serializable {
        /**
         * createTime : 1620802850291
         * email : n.ulvxquw@luqczh.cd
         * id : 7311
         * sex : 1
         * birthday : ut
         * shopId : 3331
         * province : fugiat cupidatat eiusmod occaecat
         * username : aute quis aliqua
         * name : in occaecat nostrud laborum
         * districtId : 2870
         * status : 0
         * cityId : 3032
         * classId : 9061
         * schoolId : 6019
         * age : 7728
         * city : id pariatur occaecat
         * district : magna minim
         * schoolName : non
         * nickname : quis dolore tempor
         * recommendId : 1554
         * className : ea ullamco voluptate enim
         * provinceId : 2740
         * logo : non mollit labore
         */

        private long createTime;
        private String email;
        private String id;
        private String sex;
        private String birthday;
        private String shopId;
        private String province;
        private String username;
        private String name;
        private String districtId;
        private String status;
        private String cityId;
        private String classId;
        private String schoolId;
        private String age;
        private String city;
        private String district;
        private String schoolName;
        private String nickname;
        private String recommendId;
        private String className;
        private String provinceId;
        private String logo;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
    
    public static class ItemsBean implements Serializable {
        /**
         * orderId : 8298
         * id : 2232
         * productSubName : velit
         * commission : 9056
         * fee : 2322
         * productPrice : 7581
         * productId : 9704
         * count : 829
         * status : 4
         * productName : in officia commodo incididunt
         * productImage : aliqua et laboris
         */

        private String orderId;
        private String id;
        private String productSubName;
        private String commission;
        private String fee;
        private String productPrice;
        private String productId;
        private String count;
        private String status;
        private String productName;
        private String productImage;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductSubName() {
            return productSubName;
        }

        public void setProductSubName(String productSubName) {
            this.productSubName = productSubName;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
    
    public static class DiscountInfosBean implements Serializable {
        /**
         * discountType : 0
         * orderId : 8606
         * objId : 4709
         * id : 4583
         * value : 2323
         */

        private String discountType;
        private String orderId;
        private String objId;
        private String id;
        private String value;

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getObjId() {
            return objId;
        }

        public void setObjId(String objId) {
            this.objId = objId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
