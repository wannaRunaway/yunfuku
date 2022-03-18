package com.aifubook.book.productcar.cart;

import java.io.Serializable;
import java.util.List;

/**
 * Author by Damon.
 */
public class CarItemsBean implements Serializable {

    /**
     * productId : 5541
     * product : {"id":5541,"name":"世界上最伟大的博物馆：卢浮宫","subName":"","price":19800,"discountPrice":17880,"image":"3e6b39807dc9ae6875011d1ff0dc0bbcbde8f3af.png","images":["adfb4e945d25e7e5f2ac54d814bba390ba18a6e1.png"],"categoryId":116,"shopId":32,"status":2,"coupon":0,"recommend":1,"soldCount":5,"shelfTime":1615533155278,"promotion":1,"stock":1000,"freezeStoke":0,"reason":null,"createTime":1615532879611,"updateTime":1615533155278,"commissionRate":10,"pre":0,"preTime":null,"limit":0,"code":"00010","press":"广东大音音像出版社","classes":"高","subject":"历史","zeroBuy":0,"preTimeDiff":null}
     * count : 1
     * shopId : 32
     * updateTime : 1620205841874
     */

    private int productId;
    private ProductBean product;
    private int count;
    private int shopId;
    private long updateTime;
    private boolean isSelect;
    private int chiefId;
    private int zeroBuy;

    public int getZeroBuy() {
        return zeroBuy;
    }

    public void setZeroBuy(int zeroBuy) {
        this.zeroBuy = zeroBuy;
    }

    public int getChiefId() {
        return chiefId;
    }

    public void setChiefId(int chiefId) {
        this.chiefId = chiefId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public static class ProductBean {
        /**
         * id : 5541
         * name : 世界上最伟大的博物馆：卢浮宫
         * subName :
         * price : 19800
         * discountPrice : 17880
         * image : 3e6b39807dc9ae6875011d1ff0dc0bbcbde8f3af.png
         * images : ["adfb4e945d25e7e5f2ac54d814bba390ba18a6e1.png"]
         * categoryId : 116
         * shopId : 32
         * status : 2
         * coupon : 0
         * recommend : 1
         * soldCount : 5
         * shelfTime : 1615533155278
         * promotion : 1
         * stock : 1000
         * freezeStoke : 0
         * reason : null
         * createTime : 1615532879611
         * updateTime : 1615533155278
         * commissionRate : 10
         * pre : 0
         * preTime : null
         * limit : 0
         * code : 00010
         * press : 广东大音音像出版社
         * classes : 高
         * subject : 历史
         * zeroBuy : 0
         * preTimeDiff : null
         */

        private int id;
        private String name;
        private String subName;
        private int price;
        private int discountPrice;
        private String image;
        private int categoryId;
        private int shopId;
        private int status;//状态 只有2是上架状态
        private int coupon;
        private int recommend;
        private int soldCount;
        private long shelfTime;
        private int promotion;
        private int stock;//库存
        private int freezeStoke;
        private Object reason;
        private long createTime;
        private long updateTime;
        private int commissionRate;
        private int pre;
        private Object preTime;
        private int limit;//限购
        private String code;
        private String press;
        private String classes;
        private String subject;
        private int zeroBuy;
        private Object preTimeDiff;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public int getSoldCount() {
            return soldCount;
        }

        public void setSoldCount(int soldCount) {
            this.soldCount = soldCount;
        }

        public long getShelfTime() {
            return shelfTime;
        }

        public void setShelfTime(long shelfTime) {
            this.shelfTime = shelfTime;
        }

        public int getPromotion() {
            return promotion;
        }

        public void setPromotion(int promotion) {
            this.promotion = promotion;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getFreezeStoke() {
            return freezeStoke;
        }

        public void setFreezeStoke(int freezeStoke) {
            this.freezeStoke = freezeStoke;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(int commissionRate) {
            this.commissionRate = commissionRate;
        }

        public int getPre() {
            return pre;
        }

        public void setPre(int pre) {
            this.pre = pre;
        }

        public Object getPreTime() {
            return preTime;
        }

        public void setPreTime(Object preTime) {
            this.preTime = preTime;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPress() {
            return press;
        }

        public void setPress(String press) {
            this.press = press;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getZeroBuy() {
            return zeroBuy;
        }

        public void setZeroBuy(int zeroBuy) {
            this.zeroBuy = zeroBuy;
        }

        public Object getPreTimeDiff() {
            return preTimeDiff;
        }

        public void setPreTimeDiff(Object preTimeDiff) {
            this.preTimeDiff = preTimeDiff;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
