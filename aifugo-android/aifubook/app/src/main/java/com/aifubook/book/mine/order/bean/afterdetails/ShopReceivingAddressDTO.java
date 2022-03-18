package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopReceivingAddressDTO implements Serializable {
    @SerializedName("linkman")
    private String linkman;
    @SerializedName("phone")
    private String phone;
    @SerializedName("provinceId")
    private Integer provinceId;
    @SerializedName("cityId")
    private Integer cityId;
    @SerializedName("districtId")
    private Integer districtId;
    @SerializedName("address")
    private String address;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
