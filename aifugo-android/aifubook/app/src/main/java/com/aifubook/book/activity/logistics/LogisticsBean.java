package com.aifubook.book.activity.logistics;

import com.aifubook.book.flowapi.ApiException;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kotlin.jvm.Throws;

public class LogisticsBean implements Serializable {

    @SerializedName("message")
    private String message;
    @SerializedName("nu")
    private String nu;
    @SerializedName("ischeck")
    private String ischeck;
    @SerializedName("com")
    private String com;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("state")
    private String state;
    @SerializedName("condition")
    private String condition;
    @SerializedName("routeInfo")
    private RouteInfoDTO routeInfo;
    @SerializedName("isLoop")
    private Boolean isLoop;

    public class FromDTO implements Serializable {
        @SerializedName("number")
        private String number;
        @SerializedName("name")
        private String name;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class RouteInfoDTO implements Serializable {
        @SerializedName("from")
        private FromDTO from;
        @SerializedName("cur")
        private FromDTO cur;
        @SerializedName("to")
        private Object to;

        public FromDTO getFrom() {
            return from;
        }

        public void setFrom(FromDTO from) {
            this.from = from;
        }

        public FromDTO getCur() {
            return cur;
        }

        public void setCur(FromDTO cur) {
            this.cur = cur;
        }

        public Object getTo() {
            return to;
        }

        public void setTo(Object to) {
            this.to = to;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public RouteInfoDTO getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfoDTO routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Boolean getIsLoop() {
        return isLoop;
    }

    public void setIsLoop(Boolean isLoop) {
        this.isLoop = isLoop;
    }
}
