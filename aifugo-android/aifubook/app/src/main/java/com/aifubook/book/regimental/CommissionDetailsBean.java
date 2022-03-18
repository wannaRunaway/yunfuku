package com.aifubook.book.regimental;

import java.io.Serializable;
import java.util.List;

public class CommissionDetailsBean implements Serializable {

    /**
     * totalCount : 9580
     * list : [{"scene":1,"objId":4384,"createTime":1620812091882,"balance":1706,"value":9948,"objId2":8606,"commission":2736,"type":1},{"balance":8572,"type":0,"createTime":1620812091757,"value":608,"objId2":3675,"objId":3230,"commission":9577,"scene":2}]
     * offset : 4652
     * pageSize : 9661
     * pageNo : 9366
     */

    private String totalCount;
    private String offset;
    private String pageSize;
    private String pageNo;
    private List<ListBean> list;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * scene : 1
         * objId : 4384
         * createTime : 1620812091882
         * balance : 1706
         * value : 9948
         * objId2 : 8606
         * commission : 2736
         * type : 1
         *
         * 	"type": 0,
         * 			"value": 500,
         * 			"balance": 100,
         * 			"freezeFee": 0,
         * 			"commission": 1400,
         * 			"totalCommission": 1500,
         * 			"unSettlementCommission": 500,
         * 			"freezeCommission": 0,
         * 			"tradeNo": "10202201211506451338985",
         * 			"balanceChange": 0, ss
         * 			"freezeFeeChange": 0,
         * 			"commissionChange": 500,ff
         * 			"unSettlementCommissionChange": -500,
         * 			"scene": 1,
         * 			"sceneName": "团长粉豆进账",
         * 			"objId": 1,
         * 			"objId2": null,
         * 			"createTime": 1642748805133
         */

        private String scene;
        private String objId;
        private long createTime;
        private String balance;
        private String value;
        private String objId2;
        private String commission;
        private String type;
        private String sceneName;
        private int commissionChange, balanceChange;

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getObjId() {
            return objId;
        }

        public void setObjId(String objId) {
            this.objId = objId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getObjId2() {
            return objId2;
        }

        public void setObjId2(String objId2) {
            this.objId2 = objId2;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public int getCommissionChange() {
            return commissionChange;
        }

        public void setCommissionChange(int commissionChange) {
            this.commissionChange = commissionChange;
        }

        public int getBalanceChange() {
            return balanceChange;
        }

        public void setBalanceChange(int balanceChange) {
            this.balanceChange = balanceChange;
        }
    }
}
