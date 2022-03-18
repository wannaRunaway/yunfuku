package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShopDTO implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("provinceId")
    private Integer provinceId;
    @SerializedName("cityId")
    private Integer cityId;
    @SerializedName("districtId")
    private Integer districtId;
    @SerializedName("remark")
    private Object remark;
    @SerializedName("address")
    private String address;
    @SerializedName("coupon")
    private Integer coupon;
    @SerializedName("createTime")
    private Long createTime;
    @SerializedName("updateTime")
    private Long updateTime;
    @SerializedName("inviteCode")
    private String inviteCode;
    @SerializedName("status")
    private Integer status;
    @SerializedName("refuseReason")
    private Object refuseReason;
    @SerializedName("sale")
    private Object sale;
    @SerializedName("linkman")
    private String linkman;
    @SerializedName("phone")
    private String phone;
    @SerializedName("idCardFront")
    private Object idCardFront;
    @SerializedName("idCardReverseSide")
    private Object idCardReverseSide;
    @SerializedName("publicationBusinessLicense")
    private Object publicationBusinessLicense;
    @SerializedName("memberId")
    private Object memberId;
    @SerializedName("sn")
    private String sn;
    @SerializedName("key")
    private String key;
    @SerializedName("printName")
    private String printName;
    @SerializedName("printModel")
    private String printModel;
    @SerializedName("distance")
    private Object distance;
    @SerializedName("apply")
    private Object apply;
    @SerializedName("dadaShopNo")
    private String dadaShopNo;
    @SerializedName("opening")
    private Boolean opening;
    @SerializedName("shopReceivingAddress")
    private ShopReceivingAddressDTO shopReceivingAddress;
    @SerializedName("isPress")
    private Boolean isPress;
    @SerializedName("synopsis")
    private Object synopsis;
    @SerializedName("operateStatus")
    private Integer operateStatus;
    @SerializedName("defaultGive")
    private Object defaultGive;
    @SerializedName("sameCitySendStatus")
    private Integer sameCitySendStatus;
    @SerializedName("selfTakeStatus")
    private Object selfTakeStatus;
    @SerializedName("expressStatus")
    private Object expressStatus;
    @SerializedName("logisticsFee")
    private Integer logisticsFee;
    @SerializedName("selfTakeTime")
    private SelfTakeTimeDTO selfTakeTime;
    @SerializedName("logisticsFeeType")
    private Integer logisticsFeeType;
    @SerializedName("sameCitySendConf")
    private SameCitySendConfDTO sameCitySendConf;
    @SerializedName("images")
    private List<String> images;
    @SerializedName("location")
    private List<Double> location;
    @SerializedName("workTime")
    private List<WorkTimeDTO> workTime;

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

    public Object getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(Object refuseReason) {
        this.refuseReason = refuseReason;
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

    public Object getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(Object idCardFront) {
        this.idCardFront = idCardFront;
    }

    public Object getIdCardReverseSide() {
        return idCardReverseSide;
    }

    public void setIdCardReverseSide(Object idCardReverseSide) {
        this.idCardReverseSide = idCardReverseSide;
    }

    public Object getPublicationBusinessLicense() {
        return publicationBusinessLicense;
    }

    public void setPublicationBusinessLicense(Object publicationBusinessLicense) {
        this.publicationBusinessLicense = publicationBusinessLicense;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
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

    public String getPrintModel() {
        return printModel;
    }

    public void setPrintModel(String printModel) {
        this.printModel = printModel;
    }

    public Object getDistance() {
        return distance;
    }

    public void setDistance(Object distance) {
        this.distance = distance;
    }

    public Object getApply() {
        return apply;
    }

    public void setApply(Object apply) {
        this.apply = apply;
    }

    public String getDadaShopNo() {
        return dadaShopNo;
    }

    public void setDadaShopNo(String dadaShopNo) {
        this.dadaShopNo = dadaShopNo;
    }

    public Boolean getOpening() {
        return opening;
    }

    public void setOpening(Boolean opening) {
        this.opening = opening;
    }

    public ShopReceivingAddressDTO getShopReceivingAddress() {
        return shopReceivingAddress;
    }

    public void setShopReceivingAddress(ShopReceivingAddressDTO shopReceivingAddress) {
        this.shopReceivingAddress = shopReceivingAddress;
    }

    public Boolean getPress() {
        return isPress;
    }

    public void setPress(Boolean press) {
        isPress = press;
    }

    public Object getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(Object synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public Object getDefaultGive() {
        return defaultGive;
    }

    public void setDefaultGive(Object defaultGive) {
        this.defaultGive = defaultGive;
    }

    public Integer getSameCitySendStatus() {
        return sameCitySendStatus;
    }

    public void setSameCitySendStatus(Integer sameCitySendStatus) {
        this.sameCitySendStatus = sameCitySendStatus;
    }

    public Object getSelfTakeStatus() {
        return selfTakeStatus;
    }

    public void setSelfTakeStatus(Object selfTakeStatus) {
        this.selfTakeStatus = selfTakeStatus;
    }

    public Object getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Object expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Integer getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(Integer logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public SelfTakeTimeDTO getSelfTakeTime() {
        return selfTakeTime;
    }

    public void setSelfTakeTime(SelfTakeTimeDTO selfTakeTime) {
        this.selfTakeTime = selfTakeTime;
    }

    public Integer getLogisticsFeeType() {
        return logisticsFeeType;
    }

    public void setLogisticsFeeType(Integer logisticsFeeType) {
        this.logisticsFeeType = logisticsFeeType;
    }

    public SameCitySendConfDTO getSameCitySendConf() {
        return sameCitySendConf;
    }

    public void setSameCitySendConf(SameCitySendConfDTO sameCitySendConf) {
        this.sameCitySendConf = sameCitySendConf;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public List<WorkTimeDTO> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<WorkTimeDTO> workTime) {
        this.workTime = workTime;
    }
}
