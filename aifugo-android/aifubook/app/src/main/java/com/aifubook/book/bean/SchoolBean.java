package com.aifubook.book.bean;

import java.util.List;

public class SchoolBean {


    private Integer id;
    private String name;
    private Integer provinceId;
    private Integer districtId;
    private Integer cityId;
    private Object province;
    private Object district;
    private Object city;
    private String address;
    private List<Double> lng;
    private String linkman;
    private String phone;
    private Integer use;

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

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Object getDistrict() {
        return district;
    }

    public void setDistrict(Object district) {
        this.district = district;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Double> getLng() {
        return lng;
    }

    public void setLng(List<Double> lng) {
        this.lng = lng;
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

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }
}
