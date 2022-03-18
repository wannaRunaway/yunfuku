package com.aifubook.book.bean;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/5/7 20:22
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class NearShopBean {


    private Integer id;
    private String name;
    private String logo;
    private Integer provinceId;
    private Integer districtId;
    private Integer cityId;
    private String remark;
    private String address;
    private List<Double> location;
    private Integer coupon;
    private Long createTime;
    private Long updateTime;
    private Integer logisticsFee;
    private String inviteCode;
    private Integer status;
    private Object sale;
    private String linkman;
    private String phone;
    private Integer memberId;
    private Integer sameCitySend;
    private Integer sameCitySendDistance;
    private String sn;
    private String key;
    private String printName;
    private Double distance;
    private boolean ischeck;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
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

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(Integer logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSameCitySend() {
        return sameCitySend;
    }

    public void setSameCitySend(Integer sameCitySend) {
        this.sameCitySend = sameCitySend;
    }

    public Integer getSameCitySendDistance() {
        return sameCitySendDistance;
    }

    public void setSameCitySendDistance(Integer sameCitySendDistance) {
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
