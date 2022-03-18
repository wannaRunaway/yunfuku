package com.aifubook.book.mine.address;

import java.io.Serializable;
import java.util.List;

public class AddressListBean implements Serializable {


    /**
     * id : 9811
     * mobile : 17087274924
     * city : nisi
     * provinceId : 4875
     * cityId : 4408
     * memberId : 8003
     * province : commodo ut ullamco aute consequat
     * district : ad ullamco
     * name : adipisicing amet
     * defaultAddress : false
     * address : 世金可路390号
     * location : [4031.634,9307.4559474,1264.3114422,5933.3809578,8142.053041]
     * districtId : 6863
     */

    private String id = "";
    private String mobile;
    private String city;
    private String provinceId;
    private String cityId;
    private String memberId;
    private String province;
    private String district;
    private String name;
    private boolean defaultAddress;
    private String address;
    private String districtId;
    private List<String> location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
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

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }
}
