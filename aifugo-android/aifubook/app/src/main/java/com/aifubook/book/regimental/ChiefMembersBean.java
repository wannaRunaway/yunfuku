package com.aifubook.book.regimental;

import java.io.Serializable;
import java.util.List;

public class ChiefMembersBean implements Serializable {


    /**
     * offset : 8322
     * totalCount : 8108
     * pageNo : 3348
     * pageSize : 2385
     * list : [{"member":{"cityId":3731,"username":"deserunt laborum ex cupidatat do","district":"aliquip laborum dolore enim nulla","name":"minim anim fugiat officia commodo","status":0,"className":"Ut Excepteur elit sed est","classId":8208,"age":9712,"province":"ex","provinceId":7164,"sex":1,"id":2579,"logo":"proident dolore ut","districtId":5132,"birthday":"cillum enim veniam dolore esse","recommendId":5086,"email":"m.nugljss@leyk.au","city":"quis id","schoolName":"ad fugiat pariatur sed","createTime":1620733669537,"nickname":"irure deserunt","schoolId":6471,"shopId":7059},"id8a":1.763704487967281E7,"sint_33":-8.075890835835074E7,"dolor_3":2957267.5206380486,"pariaturf":"ex cupidatat do irure enim","sint5":"id sit incididunt cupidatat in"},{"member":{"schoolId":8695,"classId":1411,"name":"do ut est ea","nickname":"dolore","status":20,"logo":"ut labore occaecat","createTime":1620733669163,"districtId":9761,"className":"veniam","shopId":4135,"provinceId":5678,"id":8880,"province":"sit id dolor ad et","recommendId":2301,"district":"velit aliqua eiusmod","email":"y.famny@gqrojjnip.cv","city":"in consectetur irure in ea","username":"aliqua","sex":1,"birthday":"labore","cityId":4539,"age":9114,"schoolName":"ex sed do"},"Duis_a4":32706827},{"member":{"provinceId":6677,"district":"dolore","classId":4733,"province":"aliqua","id":7381,"username":"tempor consequat","createTime":1620733669653,"nickname":"enim sed in Excepteur","shopId":9895,"className":"Excepteur Ut qui elit veniam","logo":"pariatur","schoolId":235,"sex":0,"email":"n.esekts@rbgqgcz.zr","birthday":"enim irure","status":10,"cityId":3912,"recommendId":363,"name":"non reprehenderit cupidatat sed amet","age":9138,"city":"nostrud pariatur dolore ea cillum","districtId":3582,"schoolName":"adipisicing Lorem incididunt"},"ad_":true},{"member":{"className":"veniam","province":"laborum dolor","sex":1,"birthday":"consectetur adipisicing proident qui","createTime":1620733669621,"classId":7277,"age":8762,"username":"magna esse ullamco labore","schoolName":"proident occaecat et veniam dolore","email":"c.ezk@zpoeslldr.sl","schoolId":3973,"nickname":"cillum","id":485,"name":"sit et ullamco commodo Excepteur","city":"Lorem aliquip","cityId":2890,"provinceId":6362,"districtId":5810,"recommendId":9294,"district":"consequat exercitation","shopId":1816,"logo":"commodo culpa","status":20},"in__2":"amet incididunt nisi","dolore_":"ipsum labore irure"}]
     */

    private int offset;
    private int totalCount;
    private int pageNo;
    private int pageSize;
    private List<ListBean> list;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * member : {"cityId":3731,"username":"deserunt laborum ex cupidatat do","district":"aliquip laborum dolore enim nulla","name":"minim anim fugiat officia commodo","status":0,"className":"Ut Excepteur elit sed est","classId":8208,"age":9712,"province":"ex","provinceId":7164,"sex":1,"id":2579,"logo":"proident dolore ut","districtId":5132,"birthday":"cillum enim veniam dolore esse","recommendId":5086,"email":"m.nugljss@leyk.au","city":"quis id","schoolName":"ad fugiat pariatur sed","createTime":1620733669537,"nickname":"irure deserunt","schoolId":6471,"shopId":7059}
         * id8a : 1.763704487967281E7
         * sint_33 : -8.075890835835074E7
         * dolor_3 : 2957267.5206380486
         * pariaturf : ex cupidatat do irure enim
         * sint5 : id sit incididunt cupidatat in
         * Duis_a4 : 32706827
         * ad_ : true
         * in__2 : amet incididunt nisi
         * dolore_ : ipsum labore irure
         */

        private MemberBean member;
        private double id8a;
        private double sint_33;
        private double dolor_3;
        private String pariaturf;
        private String sint5;
        private int Duis_a4;
        private boolean ad_;
        private String in__2;
        private String dolore_;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public double getId8a() {
            return id8a;
        }

        public void setId8a(double id8a) {
            this.id8a = id8a;
        }

        public double getSint_33() {
            return sint_33;
        }

        public void setSint_33(double sint_33) {
            this.sint_33 = sint_33;
        }

        public double getDolor_3() {
            return dolor_3;
        }

        public void setDolor_3(double dolor_3) {
            this.dolor_3 = dolor_3;
        }

        public String getPariaturf() {
            return pariaturf;
        }

        public void setPariaturf(String pariaturf) {
            this.pariaturf = pariaturf;
        }

        public String getSint5() {
            return sint5;
        }

        public void setSint5(String sint5) {
            this.sint5 = sint5;
        }

        public int getDuis_a4() {
            return Duis_a4;
        }

        public void setDuis_a4(int duis_a4) {
            Duis_a4 = duis_a4;
        }

        public boolean isAd_() {
            return ad_;
        }

        public void setAd_(boolean ad_) {
            this.ad_ = ad_;
        }

        public String getIn__2() {
            return in__2;
        }

        public void setIn__2(String in__2) {
            this.in__2 = in__2;
        }

        public String getDolore_() {
            return dolore_;
        }

        public void setDolore_(String dolore_) {
            this.dolore_ = dolore_;
        }

        public static class MemberBean implements Serializable {
            /**
             * cityId : 3731
             * username : deserunt laborum ex cupidatat do
             * district : aliquip laborum dolore enim nulla
             * name : minim anim fugiat officia commodo
             * status : 0
             * className : Ut Excepteur elit sed est
             * classId : 8208
             * age : 9712
             * province : ex
             * provinceId : 7164
             * sex : 1
             * id : 2579
             * logo : proident dolore ut
             * districtId : 5132
             * birthday : cillum enim veniam dolore esse
             * recommendId : 5086
             * email : m.nugljss@leyk.au
             * city : quis id
             * schoolName : ad fugiat pariatur sed
             * createTime : 1620733669537
             * nickname : irure deserunt
             * schoolId : 6471
             * shopId : 7059
             */

            private int cityId;
            private String username;
            private String district;
            private String name;
            private int status;
            private String className;
            private int classId;
            private int age;
            private String province;
            private int provinceId;
            private int sex;
            private int id;
            private String logo;
            private int districtId;
            private String birthday;
            private int recommendId;
            private String email;
            private String city;
            private String schoolName;
            private long createTime;
            private String nickname;
            private int schoolId;
            private int shopId;

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getDistrictId() {
                return districtId;
            }

            public void setDistrictId(int districtId) {
                this.districtId = districtId;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(int recommendId) {
                this.recommendId = recommendId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(int schoolId) {
                this.schoolId = schoolId;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }
        }
    }
}
