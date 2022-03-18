package com.aifubook.book.bean;

//import com.umeng.commonsdk.debug.I;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/4/28 23:58
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ProductDetailBean {


    private Integer id;
    private String name;
    private String subName;
    private Integer price;//定价
    private Integer discountPrice;//折扣价
    private String image;
    private List<String> images;
    private Integer categoryId;
    private Integer shopId;
    private Integer status;
    private Integer coupon;
    private Integer recommend;
    private Integer soldCount;
    private Long shelfTime;
    private Integer promotion;
    private Integer stock;
    private Integer freezeStoke;
    private Object reason;
    private Long createTime;
    private Long updateTime;
    private Integer commissionRate;
    private Integer pre;
    private Long preTime;
    private Integer limit;
    private String code;//书本编码
    private String press;//出版社
    private String classes;//年纪
    private String subject;//科目
    private Integer zeroBuy;
    private DescribeBean describe;
    private Long preTimeDiff;
    private int groupPrice;
    private boolean chiefReceive;//是否团长代收
    private boolean canDiscount;//是否优惠叠加  可以使用优惠券
    private boolean expressFree;//是否包邮
    private Integer chiefDiscount;//团长优惠 空-无优惠
    //    private Integer type;//1-普通团 2-阶梯团 空为没有拼团
    private List<GroupOrder> groupOrders;
    private EBook eBook;

    public Integer getChiefDiscount() {
        return chiefDiscount;
    }

    public void setChiefDiscount(Integer chiefDiscount) {
        this.chiefDiscount = chiefDiscount;
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

    public boolean isChiefReceive() {
        return chiefReceive;
    }

    public void setChiefReceive(boolean chiefReceive) {
        this.chiefReceive = chiefReceive;
    }


    public List<GroupOrder> getGroupOrders() {
        return groupOrders;
    }

    public void setGroupOrders(List<GroupOrder> groupOrders) {
        this.groupOrders = groupOrders;
    }

    public int getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        this.groupPrice = groupPrice;
    }

    public EBook geteBook() {
        return eBook;
    }

    public void seteBook(EBook eBook) {
        this.eBook = eBook;
    }

    public class GroupOrder {
        private String id;
        private List<User> users;
        private int chiefDiscount;
        private boolean fakeMember;
        private String memberId;
        private String memberName;//团购 下单是展示的名字 和手机号 不用展示地址
        private String memberMobile;
        //        private int type;//拼团类型 1-普通团 2-阶梯团
        private boolean chiefReceive;//是否团长代收
        private boolean canDiscount;//是否优惠叠加  可以使用优惠券
        private boolean expressFree;//是否包邮


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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public class User {
            private String userId;
            private String userName;
            private String userImage;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }
        }

    }


    public static class DescribeBean {
        private List<String> detailImages;
        private String content;

        public List<String> getDetailImages() {
            return detailImages;
        }

        public void setDetailImages(List<String> detailImages) {
            this.detailImages = detailImages;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class EBook {
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public Long getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Long shelfTime) {
        this.shelfTime = shelfTime;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getFreezeStoke() {
        return freezeStoke;
    }

    public void setFreezeStoke(Integer freezeStoke) {
        this.freezeStoke = freezeStoke;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Integer getPre() {
        return pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public Long getPreTime() {
        return preTime;
    }

    public void setPreTime(Long preTime) {
        this.preTime = preTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
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

    public Integer getZeroBuy() {
        return zeroBuy;
    }

    public void setZeroBuy(Integer zeroBuy) {
        this.zeroBuy = zeroBuy;
    }

    public DescribeBean getDescribe() {
        return describe;
    }

    public void setDescribe(DescribeBean describe) {
        this.describe = describe;
    }

    public Long getPreTimeDiff() {
        return preTimeDiff;
    }

    public void setPreTimeDiff(Long preTimeDiff) {
        this.preTimeDiff = preTimeDiff;
    }
}
