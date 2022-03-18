package com.aifubook.book.bean;

import java.io.Serializable;
import java.util.List;
public class ShopNew implements Serializable {
    private Integer id;
    private String name;
    private String logo;
    private List<String> images;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private Object remark;
    private String address;
    private List<Double> location;
    private Integer coupon;
    private Long createTime;
    private Long updateTime;
    private String inviteCode;
    private Integer status;
    private Object refuseReason;
    private Object sale;
    private String linkman;
    private String phone;
    private Object idCardFront;
    private Object idCardReverseSide;
    private Object publicationBusinessLicense;
    private Object memberId;
    private String sn;
    private String key;
    private String printName;
    private Object printModel;
    private Object distance;
    private Object apply;
    private String dadaShopNo;
    private Boolean opening;
    private ShopReceivingAddressDTO shopReceivingAddress;
    private Boolean isPress;
    private Object synopsis;
    private Object operateStatus;
    private List<WorkTime> workTime;

    public List<WorkTime> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<WorkTime> workTime) {
        this.workTime = workTime;
    }
    private Object defaultGive;
    private Integer sameCitySendStatus;
    private Object selfTakeStatus;
    private Object expressStatus;
    private Integer logisticsFee;
    private SelfTakeTimeDTO selfTakeTime;
    private Integer logisticsFeeType;
    private SameCitySendConfDTO sameCitySendConf;
    private Object chiefZeroBuyProducts;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public Object getPrintModel() {
        return printModel;
    }

    public void setPrintModel(Object printModel) {
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

    public Object getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Object operateStatus) {
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

    public Object getChiefZeroBuyProducts() {
        return chiefZeroBuyProducts;
    }

    public void setChiefZeroBuyProducts(Object chiefZeroBuyProducts) {
        this.chiefZeroBuyProducts = chiefZeroBuyProducts;
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

    public static class ShopReceivingAddressDTO {
        private String linkman;
        private String phone;
        private Integer provinceId;
        private Integer cityId;
        private Integer districtId;
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

    public static class SelfTakeTimeDTO {
        private Object type;
        private Object limitEndHour;
        private Object limitEndMinute;
        private Object limitBeforeDelayDay;
        private Object limitAfterDelayDay;
        private Object delayDay;
        private Object delayHour;
        private Object delayMinute;

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getLimitEndHour() {
            return limitEndHour;
        }

        public void setLimitEndHour(Object limitEndHour) {
            this.limitEndHour = limitEndHour;
        }

        public Object getLimitEndMinute() {
            return limitEndMinute;
        }

        public void setLimitEndMinute(Object limitEndMinute) {
            this.limitEndMinute = limitEndMinute;
        }

        public Object getLimitBeforeDelayDay() {
            return limitBeforeDelayDay;
        }

        public void setLimitBeforeDelayDay(Object limitBeforeDelayDay) {
            this.limitBeforeDelayDay = limitBeforeDelayDay;
        }

        public Object getLimitAfterDelayDay() {
            return limitAfterDelayDay;
        }

        public void setLimitAfterDelayDay(Object limitAfterDelayDay) {
            this.limitAfterDelayDay = limitAfterDelayDay;
        }

        public Object getDelayDay() {
            return delayDay;
        }

        public void setDelayDay(Object delayDay) {
            this.delayDay = delayDay;
        }

        public Object getDelayHour() {
            return delayHour;
        }

        public void setDelayHour(Object delayHour) {
            this.delayHour = delayHour;
        }

        public Object getDelayMinute() {
            return delayMinute;
        }

        public void setDelayMinute(Object delayMinute) {
            this.delayMinute = delayMinute;
        }
    }

    public static class SameCitySendConfDTO {
        private Integer sameCitySendDistance;
        private Integer sameCityStar;
        private Integer sameCityBase;
        private Integer starRadius;
        private Integer starHeight;
        private Integer plusHeight;
        private Integer plusHeightLogisticsFee;
        private Integer plusDistance;
        private Integer plusDistanceLogisticsFee;

        public Integer getSameCitySendDistance() {
            return sameCitySendDistance;
        }

        public void setSameCitySendDistance(Integer sameCitySendDistance) {
            this.sameCitySendDistance = sameCitySendDistance;
        }

        public Integer getSameCityStar() {
            return sameCityStar;
        }

        public void setSameCityStar(Integer sameCityStar) {
            this.sameCityStar = sameCityStar;
        }

        public Integer getSameCityBase() {
            return sameCityBase;
        }

        public void setSameCityBase(Integer sameCityBase) {
            this.sameCityBase = sameCityBase;
        }

        public Integer getStarRadius() {
            return starRadius;
        }

        public void setStarRadius(Integer starRadius) {
            this.starRadius = starRadius;
        }

        public Integer getStarHeight() {
            return starHeight;
        }

        public void setStarHeight(Integer starHeight) {
            this.starHeight = starHeight;
        }

        public Integer getPlusHeight() {
            return plusHeight;
        }

        public void setPlusHeight(Integer plusHeight) {
            this.plusHeight = plusHeight;
        }

        public Integer getPlusHeightLogisticsFee() {
            return plusHeightLogisticsFee;
        }

        public void setPlusHeightLogisticsFee(Integer plusHeightLogisticsFee) {
            this.plusHeightLogisticsFee = plusHeightLogisticsFee;
        }

        public Integer getPlusDistance() {
            return plusDistance;
        }

        public void setPlusDistance(Integer plusDistance) {
            this.plusDistance = plusDistance;
        }

        public Integer getPlusDistanceLogisticsFee() {
            return plusDistanceLogisticsFee;
        }

        public void setPlusDistanceLogisticsFee(Integer plusDistanceLogisticsFee) {
            this.plusDistanceLogisticsFee = plusDistanceLogisticsFee;
        }
    }
    /*
    * 		"id": 120,
		"name": "西安出版社",
		"logo": "0132d9396f67cc5c6ede848b7c681791fc61b35b.jpg",
		"images": ["06121444e0acf8ef1b9a8fe005063ef2cf282a9e.jpg", "39d79404e62509357a53b253045565a0ef0e2e70.jpg", "0874080f7345499a70ffeea19dfe5de5259205f8.jpg", "782477ab37b1d54ea775c2b56877b49624b4e766.jpg"],
		"provinceId": 610000,
		"cityId": 610100,
		"districtId": 610113,
		"remark": null,
		"address": "西安市雁塔区长延堡街道雁南五路1868号曲江影视大厦11楼",
		"location": [34.192608, 108.955019],
		"coupon": 0,
		"createTime": 1632453195539,
		"updateTime": 1632453195539,
		"inviteCode": "BDKNX6",
		"status": 1,
		"refuseReason": null,
		"sale": null,
		"linkman": "迦叶",
		"phone": "18645956950",
		"idCardFront": null,
		"idCardReverseSide": null,
		"publicationBusinessLicense": null,
		"memberId": null,
		"sn": "922501396",
		"key": "dt5xmbsy",
		"printName": "测试打印1",
		"printModel": null,
		"distance": null,
		"apply": null,
		"dadaShopNo": "183883-3058657",
		"opening": true,
		"shopReceivingAddress": {
			"linkman": "迦叶",
			"phone": "18645956950",
			"provinceId": 330000,
			"cityId": 330100,
			"districtId": 330106,
			"address": "华彩国际"
		},
		"isPress": true,
		"synopsis": null,
		"operateStatus": null,
		"workTime": null,
		"defaultGive": null,
		"sameCitySendStatus": 0,
		"selfTakeStatus": null,
		"expressStatus": null,
		"logisticsFee": 0,
		"selfTakeTime": {
			"type": null,
			"limitEndHour": null,
			"limitEndMinute": null,
			"limitBeforeDelayDay": null,
			"limitAfterDelayDay": null,
			"delayDay": null,
			"delayHour": null,
			"delayMinute": null
		},
		"logisticsFeeType": 0,
		"sameCitySendConf": {
			"sameCitySendDistance": 0,
			"sameCityStar": 0,
			"sameCityBase": 0,
			"starRadius": 0,
			"starHeight": 0,
			"plusHeight": 0,
			"plusHeightLogisticsFee": 0,
			"plusDistance": 0,
			"plusDistanceLogisticsFee": 0
		},
		"chiefZeroBuyProducts": null
    * */


}
