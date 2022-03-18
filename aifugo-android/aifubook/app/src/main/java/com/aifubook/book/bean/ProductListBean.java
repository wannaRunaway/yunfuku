package com.aifubook.book.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/4/28 23:33
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ProductListBean {

        private int pageNo;
        private int pageSize;
        private int offset;
        private int totalCount;
        private List<list> list;

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setList(List<list> list) {
            this.list = list;
        }

        public List<list> getList() {
            return list;
        }

        public class list implements Serializable {

            private int id;
            private String name;
            private String subName;
            private int price;
            private int discountPrice;
            private String image;
            private List<String> images;
            private int categoryId;
            private int shopId;
            private int status;
            private int coupon;
            private int recommend;
            private int soldCount;
            private long shelfTime;
            private int promotion;
            private int stock;
            private int freezeStoke;
            private String reason;
            private long createTime;
            private long updateTime;
            private int commissionRate;
            private int pre;
            private String preTime;
            private int limit;
            private String code;
            private String press;
            private String classes;
            private String subject;
            private int zeroBuy;
            private String preTimeDiff;
            private EBook eBook;
            private int yesBuy;//1是已经领取 0是可以领取

            public int getYesBuy() {
                return yesBuy;
            }

            public void setYesBuy(int yesBuy) {
                this.yesBuy = yesBuy;
            }

            public EBook geteBook() {
                return eBook;
            }

            public void seteBook(EBook eBook) {
                this.eBook = eBook;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }

            public String getSubName() {
                return subName;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getPrice() {
                return price;
            }

            public void setDiscountPrice(int discountPrice) {
                this.discountPrice = discountPrice;
            }

            public int getDiscountPrice() {
                return discountPrice;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImage() {
                return image;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public List<String> getImages() {
                return images;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public int getShopId() {
                return shopId;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStatus() {
                return status;
            }

            public void setCoupon(int coupon) {
                this.coupon = coupon;
            }

            public int getCoupon() {
                return coupon;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setSoldCount(int soldCount) {
                this.soldCount = soldCount;
            }

            public int getSoldCount() {
                return soldCount;
            }

            public void setShelfTime(long shelfTime) {
                this.shelfTime = shelfTime;
            }

            public long getShelfTime() {
                return shelfTime;
            }

            public void setPromotion(int promotion) {
                this.promotion = promotion;
            }

            public int getPromotion() {
                return promotion;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getStock() {
                return stock;
            }

            public void setFreezeStoke(int freezeStoke) {
                this.freezeStoke = freezeStoke;
            }

            public int getFreezeStoke() {
                return freezeStoke;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getReason() {
                return reason;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setCommissionRate(int commissionRate) {
                this.commissionRate = commissionRate;
            }

            public int getCommissionRate() {
                return commissionRate;
            }

            public void setPre(int pre) {
                this.pre = pre;
            }

            public int getPre() {
                return pre;
            }

            public void setPreTime(String preTime) {
                this.preTime = preTime;
            }

            public String getPreTime() {
                return preTime;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCode() {
                return code;
            }

            public void setPress(String press) {
                this.press = press;
            }

            public String getPress() {
                return press;
            }

            public void setClasses(String classes) {
                this.classes = classes;
            }

            public String getClasses() {
                return classes;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }

            public void setZeroBuy(int zeroBuy) {
                this.zeroBuy = zeroBuy;
            }

            public int getZeroBuy() {
                return zeroBuy;
            }

            public void setPreTimeDiff(String preTimeDiff) {
                this.preTimeDiff = preTimeDiff;
            }

            public String getPreTimeDiff() {
                return preTimeDiff;
            }


        }

        public class EBook{
            private String id;
            private boolean dataStatus;
            private String name;
            private String describe;
            private String[] urls;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isDataStatus() {
                return dataStatus;
            }

            public void setDataStatus(boolean dataStatus) {
                this.dataStatus = dataStatus;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String[] getUrls() {
                return urls;
            }

            public void setUrls(String[] urls) {
                this.urls = urls;
            }
        }
    }