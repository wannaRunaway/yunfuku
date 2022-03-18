package com.aifubook.book.activity.main.mainfragment;

import com.aifubook.book.bean.ProductListBean;

import java.io.Serializable;
import java.util.List;

public class EveryOneBuys implements Serializable {
    private String categoryName;
    private List<ProductListBean.list> productList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductListBean.list> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean.list> productList) {
        this.productList = productList;
    }

    //    public class EveryOneBuysProductBean implements Serializable{
//        private int id;
//        private String name;
//        private String subName;
//        private int price;
//        private int discountPrice;
//        private String image;
//        private List<String> images;
//        private int categoryId;
//        private int shopId;
//        private int status;
//        private int coupon;
//        private int recommend;
//        private int soldCount;
//        private long shelfTime;
//        private int promotion;
//        private int stock;
//        private int freezeStoke;
//        private String reason;
//        private long createTime;
//        private long updateTime;
//        private int commissionRate;
//        private int pre;
//        private String preTime;
//        private int limit;
//        private String code;
//        private String press;
//        private String classes;
//        private String subject;
//        private int zeroBuy;
//        private String preTimeDiff;
//        private int yesBuy;//1是已经领取 0是可以领取
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getSubName() {
//            return subName;
//        }
//
//        public void setSubName(String subName) {
//            this.subName = subName;
//        }
//
//        public int getPrice() {
//            return price;
//        }
//
//        public void setPrice(int price) {
//            this.price = price;
//        }
//
//        public int getDiscountPrice() {
//            return discountPrice;
//        }
//
//        public void setDiscountPrice(int discountPrice) {
//            this.discountPrice = discountPrice;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public List<String> getImages() {
//            return images;
//        }
//
//        public void setImages(List<String> images) {
//            this.images = images;
//        }
//
//        public int getCategoryId() {
//            return categoryId;
//        }
//
//        public void setCategoryId(int categoryId) {
//            this.categoryId = categoryId;
//        }
//
//        public int getShopId() {
//            return shopId;
//        }
//
//        public void setShopId(int shopId) {
//            this.shopId = shopId;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public int getCoupon() {
//            return coupon;
//        }
//
//        public void setCoupon(int coupon) {
//            this.coupon = coupon;
//        }
//
//        public int getRecommend() {
//            return recommend;
//        }
//
//        public void setRecommend(int recommend) {
//            this.recommend = recommend;
//        }
//
//        public int getSoldCount() {
//            return soldCount;
//        }
//
//        public void setSoldCount(int soldCount) {
//            this.soldCount = soldCount;
//        }
//
//        public long getShelfTime() {
//            return shelfTime;
//        }
//
//        public void setShelfTime(long shelfTime) {
//            this.shelfTime = shelfTime;
//        }
//
//        public int getPromotion() {
//            return promotion;
//        }
//
//        public void setPromotion(int promotion) {
//            this.promotion = promotion;
//        }
//
//        public int getStock() {
//            return stock;
//        }
//
//        public void setStock(int stock) {
//            this.stock = stock;
//        }
//
//        public int getFreezeStoke() {
//            return freezeStoke;
//        }
//
//        public void setFreezeStoke(int freezeStoke) {
//            this.freezeStoke = freezeStoke;
//        }
//
//        public String getReason() {
//            return reason;
//        }
//
//        public void setReason(String reason) {
//            this.reason = reason;
//        }
//
//        public long getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(long createTime) {
//            this.createTime = createTime;
//        }
//
//        public long getUpdateTime() {
//            return updateTime;
//        }
//
//        public void setUpdateTime(long updateTime) {
//            this.updateTime = updateTime;
//        }
//
//        public int getCommissionRate() {
//            return commissionRate;
//        }
//
//        public void setCommissionRate(int commissionRate) {
//            this.commissionRate = commissionRate;
//        }
//
//        public int getPre() {
//            return pre;
//        }
//
//        public void setPre(int pre) {
//            this.pre = pre;
//        }
//
//        public String getPreTime() {
//            return preTime;
//        }
//
//        public void setPreTime(String preTime) {
//            this.preTime = preTime;
//        }
//
//        public int getLimit() {
//            return limit;
//        }
//
//        public void setLimit(int limit) {
//            this.limit = limit;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getPress() {
//            return press;
//        }
//
//        public void setPress(String press) {
//            this.press = press;
//        }
//
//        public String getClasses() {
//            return classes;
//        }
//
//        public void setClasses(String classes) {
//            this.classes = classes;
//        }
//
//        public String getSubject() {
//            return subject;
//        }
//
//        public void setSubject(String subject) {
//            this.subject = subject;
//        }
//
//        public int getZeroBuy() {
//            return zeroBuy;
//        }
//
//        public void setZeroBuy(int zeroBuy) {
//            this.zeroBuy = zeroBuy;
//        }
//
//        public String getPreTimeDiff() {
//            return preTimeDiff;
//        }
//
//        public void setPreTimeDiff(String preTimeDiff) {
//            this.preTimeDiff = preTimeDiff;
//        }
//
//        public int getYesBuy() {
//            return yesBuy;
//        }
//
//        public void setYesBuy(int yesBuy) {
//            this.yesBuy = yesBuy;
//        }
//
//    }

}
