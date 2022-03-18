package com.aifubook.book.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/4/29 0:32
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ShopBean {


    private Integer id;
    private String name;
    private String logo;
    private Integer provinceId;
    private Integer districtId;
    private Integer cityId;
    private Object remark;
    private String address;
    private List<Double> location;
    private List<String> images;
    private Integer coupon;
    private Long createTime;
    private Long updateTime;
    private Integer logisticsFee;
    private String inviteCode;
    private String status;
    private Object sale;
    private String linkman;
    private Object phone;
    private Object memberId;
    private int sameCitySend;
    private Integer sameCitySendDistance;
    private Object distance;
    private String startHour;
    private String endHour;
    private boolean opening;
    private List<WorkTime> workTime;
    private int sameCitySendStatus;

    public int getSameCitySendStatus() {
        return sameCitySendStatus;
    }

    public void setSameCitySendStatus(int sameCitySendStatus) {
        this.sameCitySendStatus = sameCitySendStatus;
    }

    public List<WorkTime> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<WorkTime> workTime) {
        this.workTime = workTime;
    }

   public class WorkTime implements Serializable{
        //		"startHour": "08:00",
        //			"endHour": "22:30"
        private String startHour;
        private String endHour;

        public String getStartHour() {
            return startHour;
        }

        public void setStartHour(String startHour) {
            this.startHour = startHour;
        }

        public String getEndHour() {
            return endHour;
        }

        public void setEndHour(String endHour) {
            this.endHour = endHour;
        }
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public Integer getSameCitySendDistance() {
        return sameCitySendDistance;
    }

    public void setSameCitySendDistance(Integer sameCitySendDistance) {
        this.sameCitySendDistance = sameCitySendDistance;
    }

    public Object getDistance() {
        return distance;
    }

    public void setDistance(Object distance) {
        this.distance = distance;
    }
}
