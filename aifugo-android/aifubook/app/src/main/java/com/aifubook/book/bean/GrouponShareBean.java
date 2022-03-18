package com.aifubook.book.bean;

import java.util.List;

public class GrouponShareBean {

    private int id;
    private boolean dataStatus;
    private long createTime;
    private long updateTime;
    private int groupProductId;
    private int memberId;
    private String memberName;
    private String memberMobile;
    private int shopId;
    private int status;
    private int type;
    private int count;
    private int leftCount;
    private int limit;
    private int chiefDiscount;
    private boolean chiefReceive;
    private boolean fakeMember;
    private boolean canDiscount;
    private boolean expressFree;
    private int productId;
    private String productName;
    private String productImage;
    private int productPrice;
    private int groupPrice;
    private String finalPrice;
    private long leftTime;
    private List<Users> users;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(boolean dataStatus) {
        this.dataStatus = dataStatus;
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

    public int getGroupProductId() {
        return groupProductId;
    }

    public void setGroupProductId(int groupProductId) {
        this.groupProductId = groupProductId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public long getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(long leftTime) {
        this.leftTime = leftTime;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public class Users {

        private int userId;
        private String userName;
        private String userImage;
        public void setUserId(int userId) {
            this.userId = userId;
        }
        public int getUserId() {
            return userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserName() {
            return userName;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }
        public String getUserImage() {
            return userImage;
        }

    }

}
