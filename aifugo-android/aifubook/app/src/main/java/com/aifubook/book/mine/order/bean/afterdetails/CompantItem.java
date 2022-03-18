package com.aifubook.book.mine.order.bean.afterdetails;

import java.io.Serializable;

public class CompantItem implements Serializable {
    /**
     * {
     *       "code": "string",
     *       "logo": "string",
     *       "name": "string",
     *       "phone": "string"
     *     }
     * */
    private String code, logo, name, phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
