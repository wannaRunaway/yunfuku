package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SameCitySendConfDTO implements Serializable {
    @SerializedName("sameCitySendDistance")
    private Integer sameCitySendDistance;
    @SerializedName("sameCityStar")
    private Integer sameCityStar;
    @SerializedName("sameCityBase")
    private Integer sameCityBase;
    @SerializedName("starRadius")
    private Integer starRadius;
    @SerializedName("starHeight")
    private Integer starHeight;
    @SerializedName("plusHeight")
    private Integer plusHeight;
    @SerializedName("plusHeightLogisticsFee")
    private Integer plusHeightLogisticsFee;
    @SerializedName("plusDistance")
    private Integer plusDistance;
    @SerializedName("plusDistanceLogisticsFee")
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
