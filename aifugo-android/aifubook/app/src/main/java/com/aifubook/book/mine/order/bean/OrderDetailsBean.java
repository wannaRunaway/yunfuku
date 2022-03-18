package com.aifubook.book.mine.order.bean;

import java.io.Serializable;
import java.util.List;

public class OrderDetailsBean implements Serializable {

    /**
     * chief : {"jobId":1815,"credentials":"Lorem non id cillum sString","name":"veniam proident","classId":461,"status":1,"auditTime":5970,"memberId":5901,"district":"quis dolore","province":"incididunt dolore adipisicing","schoolId":6257,"member":{"nickname":"velit ea elit Excepteur laborum","logo":"aliquip","schoolId":1756,"district":"dolor adipisicing Duis proident","cityId":2772,"className":"in amet labore ipsum","schoolName":"qui velit elit deserunt","id":9462,"sex":2,"city":"in","provinceId":9239,"shopId":7703,"districtId":5420,"username":"ullamco","recommendId":2588,"email":"o.ouyvdeumwb@bwjmnj.za","status":99,"name":"elit commodo consequat qui","birthday":"incididunt","age":6655,"createTime":1619932082227,"classId":8590,"province":"fugiat Excepteur elit reprehenderit"},"createTime":1619932082207,"inviteCode":"elit laboris occaecat sit quis","updateTime":1619932082158,"clazz":{"id":8646,"name":"elit quis","schoolId":7648,"owner":"Duis pariatur do amet","phone":"18576670202"},"provinceId":9501,"id":2991,"shop":{"createTime":1619932082252,"districtId":6399,"coupon":1,"provinceId":5217,"memberId":329,"logisticsFee":9844,"address":"越作基路891号","phone":"13951282498","sameCitySend":4040,"name":"minim","linkman":"magna","updateTime":1619932082746,"location":[1814.885,323.666674195,3800.16276343,3271.52714],"cityId":1862,"status":1,"remark":"dolore cupidatat in laboris quis","id":4881,"inviteCode":"et aute ullamco","logo":"cillum mollit nulla laborum reprehenderit","distance":273.532,"sameCitySendDistance":2532},"city":"officia et ullamco","applyTime":3490,"cityId":8699,"school":{"linkman":"nostrud sed","address":"引小米路834号","districtId":6054,"province":"elit","city":"aliqua","name":"laborum consequat pariatur","phone":"13670212395","district":"adipisicing laboris consectetur","lng":[6954.8174515947],"id":5865,"use":0,"provinceId":4403,"cityId":1393},"mobile":"17051758374","shopId":5622,"districtId":4188,"address":"取感路734号"}
     * refundRefuseReason : Duis id et incididunt ex
     * logisticsFee : 5691
     * address : {"name":"quis minim incididunt in","cityId":5121,"defaultAddress":false,"districtId":4221,"mobile":"15761484556","address":"之的路662号","memberId":8872,"provinceId":6666,"id":1030,"province":"do Lorem","district":"ullamco sed laboris est dolore","city":"sit do quis nostrud cillum","location":[1959.53873582968,793.4175881155645,8417.339434626674]}
     * payTime : 4094
     * refundStatus : 5
     * payType : 6
     * createTime : 1619932082763
     * productTotalFee : 7967
     * id : 8538自提
     * refundImage : cillum pariatur
     * refundApplyTime : 3644
     * fetchTime : 4880
     * refusalReasons : culpa dolore
     * remarks : dolore id aliqua quis
     * shop : {"logisticsFee":2570,"provinceId":3161,"distance":306.635228,"inviteCode":"elit consectetur nostrud aute","cityId":3856,"phone":"15582434857","districtId":919,"remark":"enim consequat id irure eiusmod","id":1115,"address":"较线区路983号","logo":"pariatur ullamco","location":[8381.66617264556,3565.9487267333243,5450.054862833587],"status":1,"updateTime":1619932082492,"coupon":0,"sameCitySend":1846,"linkman":"magna non sString ut","sameCitySendDistance":2017,"createTime":1619932082186,"memberId":5701,"name":"in in ad enim dolor"}
     * shopId : 4280
     * status : 30
     * refundExpressNo : exercitation ad aute proident
     * code : est dolore deserunt
     * payOrderId : 8219
     * logisticsType : 3
     * refundType : 1
     * refundReasons : [8457,1987,4856,122,9363]
     * completedTime : 3438
     * refundRemark : cupidatat
     * memberId : 1244
     * items : [{"productPrice":7409,"fee":6198,"productName":"do qui ad et eiusmod","productSubName":"velit et occaecat ex aute","productImage":"voluptate culpa","commission":7286,"productId":5421,"count":5243,"id":5918,"status":3,"orderId":6392},{"productSubName":"dolore","fee":1090,"productPrice":9639,"productImage":"in eiusmod velit dolore","id":1952,"status":0,"count":2264,"productName":"commodo Excepteur cupidatat","orderId":2398,"productId":5413,"commission":5571}]
     * chiefId : 8416
     * refundAuditTime : 4684
     * expressNo : sit sunt proident
     * discountInfos : [{"orderId":3970,"discountType":0,"objId":7296,"id":5348,"value":9423}]
     * member : {"district":"sString esse laboris Ut","schoolName":"commodo","province":"pariatur reprehenderit in do","name":"nisi","shopId":1137,"email":"w.ueeil@hrzo.ml","recommendId":2698,"sex":2,"city":"in laborum sString","username":"esse non culpa laborum in","createTime":1619932082622,"districtId":7954,"classId":1922,"cityId":3511,"birthday":"consectetur deserunt Excepteur commodo","className":"elit","status":10,"logo":"laboris","nickname":"velit consectetur et ipsum","schoolId":1232,"provinceId":4988,"id":9945,"age":2005}
     * totalFee : 6894
     * proProductTotalFee : 9479
     * paymentNo : reprehenderit
     * refundOperator : 9189
     * expressCompany (string, optional): 快递公司 ,
     * expressFree (boolean, optional): 是否包邮 ,
     * expressNo (string, optional): 快递单号 ,
     * "expressNo":"SF1340254693978",
     * "expressCompany":"shunfeng",
     */

    private String expressCompany;
    private String expressNo;
    private String expressCompanyCode;
    private ChiefBean chief;
    private String refundRefuseReason;
    private String logisticsFee;
    private AddressBean address;
    private String payTime;
    private String refundStatus;
    private String payType;
    private long createTime;
    private String productTotalFee;
    private String discountTotalFee;
    private String id;
    private String refundImage;
    private String refundApplyTime;
    private String fetchTime;
    private String refusalReasons;
    private String remarks;
    private ShopBeanX shop;
    private String shopId;
    private String status;
    private String refundExpressNo;
    private String code;
    private String payOrderId;
    private String logisticsType;
    private String refundType;
    private String completedTime;
    private String refundRemark;
    private String memberId;
    private String chiefId;
    private String refundAuditTime;
    private MemberBeanX member;
    private String totalFee;
    private String proProductTotalFee;
    private String paymentNo;
    private String refundOperator;
    private List<String> refundReasons;
    private List<String> refundReasonsString;
    private List<ItemsBean> items;
    private List<DiscountInfosBean> discountInfos;
    private long closeCountDown;
    private String billNo;
    private Long leftTime;//拼团倒计时
    private long validTime;

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

    public Long getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }

    public String getDiscountTotalFee() {
        return discountTotalFee;
    }

    public void setDiscountTotalFee(String discountTotalFee) {
        this.discountTotalFee = discountTotalFee;
    }

    public List<String> getRefundReasonsString() {
        return refundReasonsString;
    }

    public void setRefundReasonsString(List<String> refundReasonsString) {
        this.refundReasonsString = refundReasonsString;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public long getCloseCountDown() {
        return closeCountDown;
    }

    public void setCloseCountDown(long closeCountDown) {
        this.closeCountDown = closeCountDown;
    }

    public ChiefBean getChief() {
        return chief;
    }

    public void setChief(ChiefBean chief) {
        this.chief = chief;
    }

    public String getRefundRefuseReason() {
        return refundRefuseReason;
    }

    public void setRefundRefuseReason(String refundRefuseReason) {
        this.refundRefuseReason = refundRefuseReason;
    }

    public void setRefundImage(String refundImage) {
        this.refundImage = refundImage;
    }

    public String getRefundApplyTime() {
        return refundApplyTime;
    }

    public void setRefundApplyTime(String refundApplyTime) {
        this.refundApplyTime = refundApplyTime;
    }

    public String getRefundAuditTime() {
        return refundAuditTime;
    }

    public void setRefundAuditTime(String refundAuditTime) {
        this.refundAuditTime = refundAuditTime;
    }

    public String getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(String logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefundImage() {
        return refundImage;
    }

    public String getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(String fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getRefusalReasons() {
        return refusalReasons;
    }

    public void setRefusalReasons(String refusalReasons) {
        this.refusalReasons = refusalReasons;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ShopBeanX getShop() {
        return shop;
    }

    public void setShop(ShopBeanX shop) {
        this.shop = shop;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundExpressNo() {
        return refundExpressNo;
    }

    public void setRefundExpressNo(String refundExpressNo) {
        this.refundExpressNo = refundExpressNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
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

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getChiefId() {
        return chiefId;
    }

    public void setChiefId(String chiefId) {
        this.chiefId = chiefId;
    }


    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public MemberBeanX getMember() {
        return member;
    }

    public void setMember(MemberBeanX member) {
        this.member = member;
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

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getRefundOperator() {
        return refundOperator;
    }

    public void setRefundOperator(String refundOperator) {
        this.refundOperator = refundOperator;
    }

    public List<String> getRefundReasons() {
        return refundReasons;
    }

    public void setRefundReasons(List<String> refundReasons) {
        this.refundReasons = refundReasons;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public List<DiscountInfosBean> getDiscountInfos() {
        return discountInfos;
    }

    public void setDiscountInfos(List<DiscountInfosBean> discountInfos) {
        this.discountInfos = discountInfos;
    }

    public static class ChiefBean implements Serializable {
        /**
         * jobId : 1815
         * credentials : Lorem non id cillum sString
         * name : veniam proident
         * classId : 461
         * status : 1
         * auditTime : 5970
         * memberId : 5901
         * district : quis dolore
         * province : incididunt dolore adipisicing
         * schoolId : 6257
         * member : {"nickname":"velit ea elit Excepteur laborum","logo":"aliquip","schoolId":1756,"district":"dolor adipisicing Duis proident","cityId":2772,"className":"in amet labore ipsum","schoolName":"qui velit elit deserunt","id":9462,"sex":2,"city":"in","provinceId":9239,"shopId":7703,"districtId":5420,"username":"ullamco","recommendId":2588,"email":"o.ouyvdeumwb@bwjmnj.za","status":99,"name":"elit commodo consequat qui","birthday":"incididunt","age":6655,"createTime":1619932082227,"classId":8590,"province":"fugiat Excepteur elit reprehenderit"}
         * createTime : 1619932082207
         * inviteCode : elit laboris occaecat sit quis
         * updateTime : 1619932082158
         * clazz : {"id":8646,"name":"elit quis","schoolId":7648,"owner":"Duis pariatur do amet","phone":"18576670202"}
         * provinceId : 9501
         * id : 2991
         * shop : {"createTime":1619932082252,"districtId":6399,"coupon":1,"provinceId":5217,"memberId":329,"logisticsFee":9844,"address":"越作基路891号","phone":"13951282498","sameCitySend":4040,"name":"minim","linkman":"magna","updateTime":1619932082746,"location":[1814.885,323.666674195,3800.16276343,3271.52714],"cityId":1862,"status":1,"remark":"dolore cupidatat in laboris quis","id":4881,"inviteCode":"et aute ullamco","logo":"cillum mollit nulla laborum reprehenderit","distance":273.532,"sameCitySendDistance":2532}
         * city : officia et ullamco
         * applyTime : 3490
         * cityId : 8699
         * school : {"linkman":"nostrud sed","address":"引小米路834号","districtId":6054,"province":"elit","city":"aliqua","name":"laborum consequat pariatur","phone":"13670212395","district":"adipisicing laboris consectetur","lng":[6954.8174515947],"id":5865,"use":0,"provinceId":4403,"cityId":1393}
         * mobile : 17051758374
         * shopId : 5622
         * districtId : 4188
         * address : 取感路734号
         */

        private String jobId;
        private String credentials;
        private String name;
        private String classId;
        private String status;
        private String auditTime;
        private String memberId;
        private String district;
        private String province;
        private String schoolId;
        private MemberBean member;
        private long createTime;
        private String inviteCode;
        private long updateTime;
        private ClazzBean clazz;
        private String provinceId;
        private String id;
        private ShopBean shop;
        private String city;
        private String applyTime;
        private String cityId;
        private SchoolBean school;
        private String mobile;
        private String shopId;
        private String districtId;
        private String address;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public ClazzBean getClazz() {
            return clazz;
        }

        public void setClazz(ClazzBean clazz) {
            this.clazz = clazz;
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

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
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

        public static class MemberBean implements Serializable {
            /**
             * nickname : velit ea elit Excepteur laborum
             * logo : aliquip
             * schoolId : 1756
             * district : dolor adipisicing Duis proident
             * cityId : 2772
             * className : in amet labore ipsum
             * schoolName : qui velit elit deserunt
             * id : 9462
             * sex : 2
             * city : in
             * provinceId : 9239
             * shopId : 7703
             * districtId : 5420
             * username : ullamco
             * recommendId : 2588
             * email : o.ouyvdeumwb@bwjmnj.za
             * status : 99
             * name : elit commodo consequat qui
             * birthday : incididunt
             * age : 6655
             * createTime : 1619932082227
             * classId : 8590
             * province : fugiat Excepteur elit reprehenderit
             */

            private String nickname;
            private String logo;
            private String schoolId;
            private String district;
            private String cityId;
            private String className;
            private String schoolName;
            private String id;
            private String sex;
            private String city;
            private String provinceId;
            private String shopId;
            private String districtId;
            private String username;
            private String recommendId;
            private String email;
            private String status;
            private String name;
            private String birthday;
            private String age;
            private long createTime;
            private String classId;
            private String province;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(String schoolId) {
                this.schoolId = schoolId;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(String recommendId) {
                this.recommendId = recommendId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }


        public static class ClazzBean implements Serializable {
            /**
             * id : 8646
             * name : elit quis
             * schoolId : 7648
             * owner : Duis pariatur do amet
             * phone : 18576670202
             */

            private String id;
            private String name;
            private String schoolId;
            private String owner;
            private String phone;

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

            public String getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(String schoolId) {
                this.schoolId = schoolId;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class ShopBean implements Serializable {
            /**
             * createTime : 1619932082252
             * districtId : 6399
             * coupon : 1
             * provinceId : 5217
             * memberId : 329
             * logisticsFee : 9844
             * address : 越作基路891号
             * phone : 13951282498
             * sameCitySend : 4040
             * name : minim
             * linkman : magna
             * updateTime : 1619932082746
             * location : [1814.885,323.666674195,3800.16276343,3271.52714]
             * cityId : 1862
             * status : 1
             * remark : dolore cupidatat in laboris quis
             * id : 4881
             * inviteCode : et aute ullamco
             * logo : cillum mollit nulla laborum reprehenderit
             * distance : 273.532
             * sameCitySendDistance : 2532
             */

            private long createTime;
            private String districtId;
            private String coupon;
            private String provinceId;
            private String memberId;
            private String logisticsFee;
            private String address;
            private String phone;
            private String sameCitySend;
            private String name;
            private String linkman;
            private long updateTime;
            private String cityId;
            private String status;
            private String remark;
            private String id;
            private String inviteCode;
            private String logo;
            private String distance;
            private String sameCitySendDistance;
            private List<String> location;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

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

            public String getLogisticsFee() {
                return logisticsFee;
            }

            public void setLogisticsFee(String logisticsFee) {
                this.logisticsFee = logisticsFee;
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

            public String getSameCitySend() {
                return sameCitySend;
            }

            public void setSameCitySend(String sameCitySend) {
                this.sameCitySend = sameCitySend;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getSameCitySendDistance() {
                return sameCitySendDistance;
            }

            public void setSameCitySendDistance(String sameCitySendDistance) {
                this.sameCitySendDistance = sameCitySendDistance;
            }

            public List<String> getLocation() {
                return location;
            }

            public void setLocation(List<String> location) {
                this.location = location;
            }
        }

        public static class SchoolBean implements Serializable {
            /**
             * linkman : nostrud sed
             * address : 引小米路834号
             * districtId : 6054
             * province : elit
             * city : aliqua
             * name : laborum consequat pariatur
             * phone : 13670212395
             * district : adipisicing laboris consectetur
             * lng : [6954.8174515947]
             * id : 5865
             * use : 0
             * provinceId : 4403
             * cityId : 1393
             */

            private String linkman;
            private String address;
            private String districtId;
            private String province;
            private String city;
            private String name;
            private String phone;
            private String district;
            private String id;
            private String use;
            private String provinceId;
            private String cityId;
            private List<String> lng;

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

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUse() {
                return use;
            }

            public void setUse(String use) {
                this.use = use;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public List<String> getLng() {
                return lng;
            }

            public void setLng(List<String> lng) {
                this.lng = lng;
            }
        }
    }

    public static class AddressBean implements Serializable {
        /**
         * name : quis minim incididunt in
         * cityId : 5121
         * defaultAddress : false
         * districtId : 4221
         * mobile : 15761484556
         * address : 之的路662号
         * memberId : 8872
         * provinceId : 6666
         * id : 1030
         * province : do Lorem
         * district : ullamco sed laboris est dolore
         * city : sit do quis nostrud cillum
         * location : [1959.53873582968,793.4175881155645,8417.339434626674]
         * "expressNo":"SF1340254693978",
         * "expressCompany":"shunfeng",
         */

        private String name;
        private String cityId;
        private boolean defaultAddress;
        private String districtId;
        private String mobile;
        private String address;
        private String memberId;
        private String provinceId;
        private String id;
        private String province;
        private String district;
        private String city;
        private List<String> location;
        private String expressNo, expressCompany;

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public boolean isDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(boolean defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }

    public static class ShopBeanX implements Serializable {
        /**
         * logisticsFee : 2570
         * provinceId : 3161
         * distance : 306.635228
         * inviteCode : elit consectetur nostrud aute
         * cityId : 3856
         * phone : 15582434857
         * districtId : 919
         * remark : enim consequat id irure eiusmod
         * id : 1115
         * address : 较线区路983号
         * logo : pariatur ullamco
         * location : [8381.66617264556,3565.9487267333243,5450.054862833587]
         * status : 1
         * updateTime : 1619932082492
         * coupon : 0
         * sameCitySend : 1846
         * linkman : magna non sString ut
         * sameCitySendDistance : 2017
         * createTime : 1619932082186
         * memberId : 5701
         * name : in in ad enim dolor
         */

        private String logisticsFee;
        private String provinceId;
        private String distance;
        private String inviteCode;
        private String cityId;
        private String phone;
        private String districtId;
        private String remark;
        private String id;
        private String address;
        private String logo;
        private String status;
        private long updateTime;
        private String coupon;
        private String sameCitySend;
        private String linkman;
        private String sameCitySendDistance;
        private long createTime;
        private String memberId;
        private String name;
        private List<String> location;

        public String getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(String logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
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

        public String getSameCitySendDistance() {
            return sameCitySendDistance;
        }

        public void setSameCitySendDistance(String sameCitySendDistance) {
            this.sameCitySendDistance = sameCitySendDistance;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }

    public static class MemberBeanX implements Serializable {
        /**
         * district : sString esse laboris Ut
         * schoolName : commodo
         * province : pariatur reprehenderit in do
         * name : nisi
         * shopId : 1137
         * email : w.ueeil@hrzo.ml
         * recommendId : 2698
         * sex : 2
         * city : in laborum sString
         * username : esse non culpa laborum in
         * createTime : 1619932082622
         * districtId : 7954
         * classId : 1922
         * cityId : 3511
         * birthday : consectetur deserunt Excepteur commodo
         * className : elit
         * status : 10
         * logo : laboris
         * nickname : velit consectetur et ipsum
         * schoolId : 1232
         * provinceId : 4988
         * id : 9945
         * age : 2005
         */

        private String district;
        private String schoolName;
        private String province;
        private String name;
        private String shopId;
        private String email;
        private String recommendId;
        private String sex;
        private String city;
        private String username;
        private long createTime;
        private String districtId;
        private String classId;
        private String cityId;
        private String birthday;
        private String className;
        private String status;
        private String logo;
        private String nickname;
        private String schoolId;
        private String provinceId;
        private String id;
        private String age;

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    public static class DiscountInfosBean implements Serializable {
        /**
         * orderId : 3970
         * discountType : 0
         * objId : 7296
         * id : 5348
         * value : 9423
         */

        private String orderId;
        private String discountType;
        private String objId;
        private String id;
        private String value;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
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
