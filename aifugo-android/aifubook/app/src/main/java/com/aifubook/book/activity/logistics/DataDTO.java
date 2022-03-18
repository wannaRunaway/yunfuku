package com.aifubook.book.activity.logistics;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class DataDTO implements Serializable {
    @SerializedName("time")
    private String time;
    @SerializedName("context")
    private String context;
    @SerializedName("ftime")
    private String ftime;
    @SerializedName("areaCode")
    private String areaCode;
    @SerializedName("areaName")
    private String areaName;
    @SerializedName("status")
    private String status;
    @SerializedName("location")
    private String location;
    @SerializedName("areaCenter")
    private String areaCenter;
    @SerializedName("areaPinYin")
    private String areaPinYin;
    @SerializedName("statusCode")
    private String statusCode;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAreaCenter() {
        return areaCenter;
    }

    public void setAreaCenter(String areaCenter) {
        this.areaCenter = areaCenter;
    }

    public String getAreaPinYin() {
        return areaPinYin;
    }

    public void setAreaPinYin(String areaPinYin) {
        this.areaPinYin = areaPinYin;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
