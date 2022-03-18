package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SelfTakeTimeDTO implements Serializable {
    @SerializedName("type")
    private Object type;
    @SerializedName("limitEndHour")
    private Object limitEndHour;
    @SerializedName("limitEndMinute")
    private Object limitEndMinute;
    @SerializedName("limitBeforeDelayDay")
    private Object limitBeforeDelayDay;
    @SerializedName("limitAfterDelayDay")
    private Object limitAfterDelayDay;
    @SerializedName("delayDay")
    private Object delayDay;
    @SerializedName("delayHour")
    private Object delayHour;
    @SerializedName("delayMinute")
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
