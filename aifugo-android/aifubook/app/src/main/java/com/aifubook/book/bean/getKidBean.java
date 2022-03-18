package com.aifubook.book.bean;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/5/13 16:01
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class getKidBean {


    private Integer id;
    private Integer memberId;
    private String name;
    private Integer schoolId;
    private Object schoolName;
    private SchoolBean school;
    private Integer classId;
    private Object className;
    private Integer age;
    private Integer sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Object getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(Object schoolName) {
        this.schoolName = schoolName;
    }

    public SchoolBean getSchool() {
        return school;
    }

    public void setSchool(SchoolBean school) {
        this.school = school;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Object getClassName() {
        return className;
    }

    public void setClassName(Object className) {
        this.className = className;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public static class SchoolBean {
        private Integer id;
        private String name;
        private Integer provinceId;
        private Integer districtId;
        private Integer cityId;
        private String province;
        private String district;
        private String city;
        private String address;
        private List<Double> lng;
        private String linkman;
        private String phone;
        private Integer use;

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

        public Integer getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Integer provinceId) {
            this.provinceId = provinceId;
        }

        public Integer getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Integer districtId) {
            this.districtId = districtId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<Double> getLng() {
            return lng;
        }

        public void setLng(List<Double> lng) {
            this.lng = lng;
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

        public Integer getUse() {
            return use;
        }

        public void setUse(Integer use) {
            this.use = use;
        }
    }
}
