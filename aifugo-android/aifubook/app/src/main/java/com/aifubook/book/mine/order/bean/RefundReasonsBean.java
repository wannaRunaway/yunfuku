package com.aifubook.book.mine.order.bean;

import java.io.Serializable;

public class RefundReasonsBean implements Serializable {

    /**
     * type : 1
     * desc : 包装破损
     */

    private int type;
    private String desc;
    private int selectIndex;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
