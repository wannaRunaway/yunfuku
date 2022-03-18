package com.aifubook.book.activity.logistics;

import java.io.Serializable;

public class LogisticsAllBean implements Serializable {

    private Company company;
    private LogisticsBean info;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LogisticsBean getInfo() {
        return info;
    }

    public void setInfo(LogisticsBean info) {
        this.info = info;
    }

    public class Company implements Serializable{
        /*
        * "code": "shunfeng",
			"phone": "123456",
			"name": "顺丰速运",
			"logo": "560aeb08b1e3193742bc00a76151ca3224007735.png"
        * */
        private String code;
        private String phone;
        private String name;
        private String logo;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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
    }
}
