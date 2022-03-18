package com.aifubook.book.mine.order.bean.afterdetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkTimeDTO implements Serializable {
    @SerializedName("startHour")
    private String startHour;
    @SerializedName("endHour")
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
