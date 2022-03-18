package com.aifubook.book.bean;

import java.util.List;

public class GrouponPageBean {

    private List<GrouponBean> list;

    public List<GrouponBean> getList() {
        return list;
    }

    public void setList(List<GrouponBean> list) {
        this.list = list;
    }

    public class GrouponBean {

        private int id;
        private boolean dataStatus;
        private long createTime;
        private long updateTime;
        private String name;
        private int type;
        private int count;
        private int shopId;
        private long startTime;
        private long endTime;
        private long validTime;
        private int limit;
        private int chiefDiscount;
        private boolean chiefReceive;
        private boolean fakeMember;
        private boolean canDiscount;
        private boolean expressFree;
        private int productId;
        private String productName;
        private String productImage;
        private int productStock;
        private int productPrice;
        private int groupPrice;
        private List<StepPrice> stepPrice;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getValidTime() {
            return validTime;
        }

        public void setValidTime(long validTime) {
            this.validTime = validTime;
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

        public int getProductStock() {
            return productStock;
        }

        public void setProductStock(int productStock) {
            this.productStock = productStock;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getGroupPrice() {
            return groupPrice;
        }

        public void setGroupPrice(int groupPrice) {
            this.groupPrice = groupPrice;
        }

        public List<StepPrice> getStepPrice() {
            return stepPrice;
        }

        public void setStepPrice(List<StepPrice> stepPrice) {
            this.stepPrice = stepPrice;
        }

        public class StepPrice{
            private int stepCount;
            private int stepPrice;

            public int getStepCount() {
                return stepCount;
            }

            public void setStepCount(int stepCount) {
                this.stepCount = stepCount;
            }

            public int getStepPrice() {
                return stepPrice;
            }

            public void setStepPrice(int stepPrice) {
                this.stepPrice = stepPrice;
            }
        }


    }



}
