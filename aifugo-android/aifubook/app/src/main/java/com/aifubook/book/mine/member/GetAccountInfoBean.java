package com.aifubook.book.mine.member;

import java.io.Serializable;

public class GetAccountInfoBean implements Serializable {

    /**
     * unSettlementCommission : 6761
     * score : 5612
     * freezeScore : 2698
     * balance : 3990
     * freezeCommission : 3957
     * totalCommission : 293
     * freezeFee : 3073
     * settlementCommission : 1257
     *
     * balance (integer, optional): 余额 单位分 ,
     * commission (integer, optional): 可用粉豆 ,
     * freezeFee (integer, optional): 冻结金额 ,
     * freezeScore (integer, optional): 冻结积分 ,
     * score (integer, optional): 总积分 ,
     * totalCommission (integer, optional): 累计获得粉豆 ,
     * unSettlementCommission (integer, optional): 未结算粉豆
     */

    private String unSettlementCommission;
    private String score;
    private String freezeScore;
    private int balance;
    private String freezeCommission;
    private String totalCommission;
    private String freezeFee;
    private String settlementCommission;
    private String commission;

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getUnSettlementCommission() {
        return unSettlementCommission;
    }

    public void setUnSettlementCommission(String unSettlementCommission) {
        this.unSettlementCommission = unSettlementCommission;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFreezeScore() {
        return freezeScore;
    }

    public void setFreezeScore(String freezeScore) {
        this.freezeScore = freezeScore;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getFreezeCommission() {
        return freezeCommission;
    }

    public void setFreezeCommission(String freezeCommission) {
        this.freezeCommission = freezeCommission;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(String totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getFreezeFee() {
        return freezeFee;
    }

    public void setFreezeFee(String freezeFee) {
        this.freezeFee = freezeFee;
    }

    public String getSettlementCommission() {
        return settlementCommission;
    }

    public void setSettlementCommission(String settlementCommission) {
        this.settlementCommission = settlementCommission;
    }
}
