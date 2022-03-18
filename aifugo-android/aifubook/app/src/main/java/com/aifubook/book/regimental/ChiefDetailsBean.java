package com.aifubook.book.regimental;

import java.io.Serializable;
import java.util.List;

public class ChiefDetailsBean implements Serializable {

    /**
     * school : {"province":"proident irure mollit adipisicing","districtId":5905,"id":829,"name":"mollit ea Excepteur dolore Lorem","lng":[803.223605391431,7877.5462634,974.15524117,9249.53],"cityId":3910,"city":"qui occaecat consequat cillum elit","phone":"13691733316","address":"题国公路251号","linkman":"sString tempor ea","provinceId":7587,"district":"Duis Lorem pariatur","use":0}
     * status : 1
     * cityId : 4478
     * id : 427
     * createTime : 1620733283856
     * classId : 2327
     * districtId : 7946
     * district : enim ullamco
     * applyTime : 7811
     * name : quis est in Duis
     * shopId : 6671
     * member : {"age":6105,"shopId":8738,"schoolName":"in ipsum voluptate","district":"commodo culpa irure","sex":0,"email":"b.ugivpeifr@dzoadvawe.is","id":5487,"nickname":"pariatur cillum Duis anim","className":"Ut in","provinceId":8984,"recommendId":2082,"birthday":"dolor non ut ullamco amet","createTime":1620733283291,"username":"sit veniam non enim","districtId":5848,"classId":2324,"province":"elit labore","logo":"elit id","cityId":9390,"name":"nisi aliquip","status":20,"city":"ut voluptate","schoolId":620}
     * inviteCode : id quis enim et ut
     * credentials : cupidatat qui aliqua occaecat tempor
     * auditTime : 1143
     * clazz : {"name":"sed deserunt","phone":"13027227174","owner":"et sunt","id":4619,"schoolId":1380}
     * address : 党时元路1459号
     * province : ex ut sed aliquip est
     * city : aliquip ad enim deserunt
     * mobile : 13038724876
     * jobId : 6217
     * provinceId : 675
     * updateTime : 1620733283718
     * schoolId : 4447
     * shop : {"remark":"Ut esse eu","districtId":9403,"createTime":1620733283362,"inviteCode":"aliqua esse","logisticsFee":1737,"sameCitySend":5022,"cityId":3258,"updateTime":1620733283482,"phone":"18733344548","name":"et dolor consequat","memberId":8699,"sameCitySendDistance":3272,"id":4256,"logo":"ad","status":0,"distance":6599.65642621147,"linkman":"ad","provinceId":4738,"address":"走速始路772号","location":[9590.13753735484],"coupon":0}
     * memberAccount : {"freezeFee":4875,"freezeCommission":7378,"balance":950,"totalCommission":8088,"freezeScore":7340,"unSettlementCommission":1634,"score":8177,"settlementCommission":7604}
     * memberId : 341
     */

    private SchoolBean school;
    private String status;
    private String cityId;
    private String id;
    private String createTime;
    private String classId;
    private String districtId;
    private String district;
    private String applyTime;
    private String name;
    private String shopId;
    private MemberBean member;
    private String inviteCode;
    private String credentials;
    private String auditTime;
    private ClazzBean clazz;
    private String address;
    private String province;
    private String city;
    private String mobile;
    private String jobId;
    private String provinceId;
    private String updateTime;
    private String schoolId;
    private ShopBean shop;
    private MemberAccountBean memberAccount;
    private String memberId;

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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
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

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public ClazzBean getClazz() {
        return clazz;
    }

    public void setClazz(ClazzBean clazz) {
        this.clazz = clazz;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public MemberAccountBean getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(MemberAccountBean memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public static class SchoolBean implements Serializable {
        /**
         * province : proident irure mollit adipisicing
         * districtId : 5905
         * id : 829
         * name : mollit ea Excepteur dolore Lorem
         * lng : [803.223605391431,7877.5462634,974.15524117,9249.53]
         * cityId : 3910
         * city : qui occaecat consequat cillum elit
         * phone : 13691733316
         * address : 题国公路251号
         * linkman : sString tempor ea
         * provinceId : 7587
         * district : Duis Lorem pariatur
         * use : 0
         */

        private String province;
        private String districtId;
        private String id;
        private String name;
        private String cityId;
        private String city;
        private String phone;
        private String address;
        private String linkman;
        private String provinceId;
        private String district;
        private String use;
        private List<String> lng;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
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

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
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
         * age : 6105
         * shopId : 8738
         * schoolName : in ipsum voluptate
         * district : commodo culpa irure
         * sex : 0
         * email : b.ugivpeifr@dzoadvawe.is
         * id : 5487
         * nickname : pariatur cillum Duis anim
         * className : Ut in
         * provinceId : 8984
         * recommendId : 2082
         * birthday : dolor non ut ullamco amet
         * createTime : 1620733283291
         * username : sit veniam non enim
         * districtId : 5848
         * classId : 2324
         * province : elit labore
         * logo : elit id
         * cityId : 9390
         * name : nisi aliquip
         * status : 20
         * city : ut voluptate
         * schoolId : 620
         */

        private String age;
        private String shopId;
        private String schoolName;
        private String district;
        private String sex;
        private String email;
        private String id;
        private String nickname;
        private String className;
        private String provinceId;
        private String recommendId;
        private String birthday;
        private String createTime;
        private String username;
        private String districtId;
        private String classId;
        private String province;
        private String logo;
        private String cityId;
        private String name;
        private String status;
        private String city;
        private String schoolId;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }
    }


    public static class ClazzBean implements Serializable {
        /**
         * name : sed deserunt
         * phone : 13027227174
         * owner : et sunt
         * id : 4619
         * schoolId : 1380
         */

        private String name;
        private String phone;
        private String owner;
        private String id;
        private String schoolId;

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

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }
    }


    public static class ShopBean implements Serializable {
        /**
         * remark : Ut esse eu
         * districtId : 9403
         * createTime : 1620733283362
         * inviteCode : aliqua esse
         * logisticsFee : 1737
         * sameCitySend : 5022
         * cityId : 3258
         * updateTime : 1620733283482
         * phone : 18733344548
         * name : et dolor consequat
         * memberId : 8699
         * sameCitySendDistance : 3272
         * id : 4256
         * logo : ad
         * status : 0
         * distance : 6599.65642621147
         * linkman : ad
         * provinceId : 4738
         * address : 走速始路772号
         * location : [9590.13753735484]
         * coupon : 0
         */

        private String remark;
        private String districtId;
        private String createTime;
        private String inviteCode;
        private String logisticsFee;
        private String sameCitySend;
        private String cityId;
        private String updateTime;
        private String phone;
        private String name;
        private String memberId;
        private String sameCitySendDistance;
        private String id;
        private String logo;
        private String status;
        private String distance;
        private String linkman;
        private String provinceId;
        private String address;
        private String coupon;
        private List<String> location;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(String logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public String getSameCitySend() {
            return sameCitySend;
        }

        public void setSameCitySend(String sameCitySend) {
            this.sameCitySend = sameCitySend;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
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

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }

    public static class MemberAccountBean implements Serializable {
        /**
         * freezeFee : 4875
         * freezeCommission : 7378
         * balance : 950
         * totalCommission : 8088
         * freezeScore : 7340
         * unSettlementCommission : 1634
         * score : 8177
         * settlementCommission : 7604
         */

        private String freezeFee;
        private String freezeCommission;
        private String balance;
        private String totalCommission;
        private String freezeScore;
        private String unSettlementCommission;
        private String score;
        private String settlementCommission;

        public String getFreezeFee() {
            return freezeFee;
        }

        public void setFreezeFee(String freezeFee) {
            this.freezeFee = freezeFee;
        }

        public String getFreezeCommission() {
            return freezeCommission;
        }

        public void setFreezeCommission(String freezeCommission) {
            this.freezeCommission = freezeCommission;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTotalCommission() {
            return totalCommission;
        }

        public void setTotalCommission(String totalCommission) {
            this.totalCommission = totalCommission;
        }

        public String getFreezeScore() {
            return freezeScore;
        }

        public void setFreezeScore(String freezeScore) {
            this.freezeScore = freezeScore;
        }

        public String getUnSettlementCommission() {
            return unSettlementCommission;
        }

        public void setUnSettlementCommission(String unSettlementCommission) {
            this.unSettlementCommission = unSettlementCommission;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getSettlementCommission() {
            return settlementCommission;
        }

        public void setSettlementCommission(String settlementCommission) {
            this.settlementCommission = settlementCommission;
        }
    }
}
