package com.aifubook.book.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/5/5 17:12
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class CarInBean implements Serializable {

    private String Name;
    List<productListBean> productListBeans;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<productListBean> getProductListBeans() {
        return productListBeans;
    }

    public void setProductListBeans(List<productListBean> productListBeans) {
        this.productListBeans = productListBeans;
    }

    public static class productListBean implements Serializable {

        private int id;
        private int count;
        private String name;
        private int price;
        private int discountPrice;
        private String image;
        private int shopId;
        private int chiefId;
        private Integer limit;//库存
        private int stock;//库存
        private int zeroBuy;//零元购
        private int groupBuy;//是否是团购
//        private int groupType;//拼团类型 1-普通团 2-阶梯团
        private String groupBuyOrderId;
        private int chiefDiscount;
        private boolean chiefReceive;//团长代收
        private boolean fakeMember;
        private String memberId;
        private String memberName;//团购 下单是展示的名字 和手机号 不用展示地址
        private String memberMobile;
        private String shareMiniPic;
        private boolean canDiscount;//是否优惠叠加  可以使用优惠券
        private boolean expressFree;//是否包邮


        public String getShareMiniPic() {
            return shareMiniPic;
        }

        public void setShareMiniPic(String shareMiniPic) {
            this.shareMiniPic = shareMiniPic;
        }


        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberMobile() {
            return memberMobile;
        }

        public void setMemberMobile(String memberMobile) {
            this.memberMobile = memberMobile;
        }

        public String getGroupBuyOrderId() {
            return groupBuyOrderId;
        }

        public void setGroupBuyOrderId(String groupBuyOrderId) {
            this.groupBuyOrderId = groupBuyOrderId;
        }

        public int getChiefDiscount() {
            return chiefDiscount;
        }

        public void setChiefDiscount(int chiefDiscount) {
            this.chiefDiscount = chiefDiscount;
        }

        public boolean isChiefReceive() {
            return chiefReceive;
        }

        public void setChiefReceive(boolean chiefReceive) {
            this.chiefReceive = chiefReceive;
        }

        public boolean isFakeMember() {
            return fakeMember;
        }

        public void setFakeMember(boolean fakeMember) {
            this.fakeMember = fakeMember;
        }

        public boolean isCanDiscount() {
            return canDiscount;
        }

        public void setCanDiscount(boolean canDiscount) {
            this.canDiscount = canDiscount;
        }

        public boolean isExpressFree() {
            return expressFree;
        }

        public void setExpressFree(boolean expressFree) {
            this.expressFree = expressFree;
        }

        public int getGroupBuy() {
            return groupBuy;
        }

        public void setGroupBuy(int groupBuy) {
            this.groupBuy = groupBuy;
        }

        public int getZeroBuy() {
            return zeroBuy;
        }

        public void setZeroBuy(int zeroBuy) {
            this.zeroBuy = zeroBuy;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public int getChiefId() {
            return chiefId;
        }

        public void setChiefId(int chiefId) {
            this.chiefId = chiefId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }

}
