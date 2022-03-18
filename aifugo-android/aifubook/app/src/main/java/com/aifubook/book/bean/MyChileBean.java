package com.aifubook.book.bean;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/5/30 0:18
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class MyChileBean {

    /**
     * Auto-generated: 2021-06-07 16:9:41
     *
     * @author jb51.net (i@jb51.net)
     * @website http://tools.jb51.net/code/json2javabean
     */
    private int id;
    private String name;
    private String mobile;
    private String shopid;
    private Shop shop;
    private int status;
    private int jobid;
    private String invitecode;
    private int memberid;
    private int schoolid;
    private int classid;
    private int audittime;
    private String credentials;
    private Member member;
    private int provinceid;
    private int districtid;
    private int cityid;
    private String province;
    private String district;
    private String city;
    private String address;
    private int applytime;
    private int createtime;
    private int updatetime;
    private String school;
    private String clazz;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopid() {
        return shopid;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public int getJobid() {
        return jobid;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public int getClassid() {
        return classid;
    }

    public void setAudittime(int audittime) {
        this.audittime = audittime;
    }

    public int getAudittime() {
        return audittime;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setDistrictid(int districtid) {
        this.districtid = districtid;
    }

    public int getDistrictid() {
        return districtid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setApplytime(int applytime) {
        this.applytime = applytime;
    }

    public int getApplytime() {
        return applytime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }


    public class Shop {

        private int id;
        private String name;
        private String logo;
        private List<String> images;
        private long provinceId;
        private long districtId;
        private long cityId;
        private String remark;
        private String address;
        private List<Double> location;
        private int coupon;
        private long createTime;
        private long updateTime;
        private int logisticsFee;
        private String inviteCode;
        private int status;
        private String refuseReason;
        private String sale;
        private String linkman;
        private String phone;
        private String idCardFront;
        private String idCardReverseSide;
        private String publicationBusinessLicense;
        private String memberId;
        private int sameCitySend;
        private int sameCitySendDistance;
        private String sn;
        private String key;
        private String printName;
        private String distance;
        private String apply;

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

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(long provinceId) {
            this.provinceId = provinceId;
        }

        public long getDistrictId() {
            return districtId;
        }

        public void setDistrictId(long districtId) {
            this.districtId = districtId;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
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

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
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

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public String getIdCardReverseSide() {
            return idCardReverseSide;
        }

        public void setIdCardReverseSide(String idCardReverseSide) {
            this.idCardReverseSide = idCardReverseSide;
        }

        public String getPublicationBusinessLicense() {
            return publicationBusinessLicense;
        }

        public void setPublicationBusinessLicense(String publicationBusinessLicense) {
            this.publicationBusinessLicense = publicationBusinessLicense;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getApply() {
            return apply;
        }

        public void setApply(String apply) {
            this.apply = apply;
        }
    }


    /**
     * Copyright 2021 jb51.net
     */
    /**
     * Auto-generated: 2021-06-07 16:9:41
     *
     * @author jb51.net (i@jb51.net)
     * @website http://tools.jb51.net/code/json2javabean
     */
    public class Member {

        private int id;
        private String username;
        private int status;
        private String name;
        private String nickname;
        private String logo;
        private String birthday;
        private int sex;
        private int age;
        private String email;
        private int provinceid;
        private int districtid;
        private int cityid;
        private String province;
        private String district;
        private String city;
        private int createtime;
        private int schoolid;
        private String schoolname;
        private String classid;
        private String classname;
        private String recommendid;
        private String shopid;
        private int isbindwechat;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return logo;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getSex() {
            return sex;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setProvinceid(int provinceid) {
            this.provinceid = provinceid;
        }

        public int getProvinceid() {
            return provinceid;
        }

        public void setDistrictid(int districtid) {
            this.districtid = districtid;
        }

        public int getDistrictid() {
            return districtid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public int getCityid() {
            return cityid;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince() {
            return province;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDistrict() {
            return district;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setSchoolid(int schoolid) {
            this.schoolid = schoolid;
        }

        public int getSchoolid() {
            return schoolid;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getClassname() {
            return classname;
        }

        public void setRecommendid(String recommendid) {
            this.recommendid = recommendid;
        }

        public String getRecommendid() {
            return recommendid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setIsbindwechat(int isbindwechat) {
            this.isbindwechat = isbindwechat;
        }

        public int getIsbindwechat() {
            return isbindwechat;
        }

    }


}
