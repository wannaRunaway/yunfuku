package com.aifubook.book.bean;

import java.util.List;

public class ShopHomeBean {

    private boolean hasNext;
    private List<ShopHome> list;
    private int offset;
    private int pageNo;
    private int pageSize;
    private int totalCount;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<ShopHome> getList() {
        return list;
    }

    public void setList(List<ShopHome> list) {
        this.list = list;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public class ShopHome {
        private int categoryId;
        private String classes;
        private String code;
        private int commissionRate;
        private int coupon;
        private double discountPrice;
        private int freezeStoke;
        private int id;
        private String image;
        private List<String> images;
        private int limit;
        private String name;
        private String press;
        private double price;
        private String reason;
        private int recommend;
        private int shopId;
        private int soldCount;
        private int status;
        private String subName;
        private String subject;

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(int commissionRate) {
            this.commissionRate = commissionRate;
        }

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }


        public int getFreezeStoke() {
            return freezeStoke;
        }

        public void setFreezeStoke(int freezeStoke) {
            this.freezeStoke = freezeStoke;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getPress() {
            return press;
        }

        public void setPress(String press) {
            this.press = press;
        }


        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }


        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getSoldCount() {
            return soldCount;
        }

        public void setSoldCount(int soldCount) {
            this.soldCount = soldCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

    }

}