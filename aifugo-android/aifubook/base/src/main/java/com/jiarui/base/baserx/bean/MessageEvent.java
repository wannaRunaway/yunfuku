package com.jiarui.base.baserx.bean;

/**
 * Created by ListKer_Hlg on 2020/2/29
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class MessageEvent {
    public static final int ORDER_PAY_SUCCESS = 1;
    public static final int WECHAT_LOGIN = 2;
    public static final int LOGIN_OUT = 3;
    public static final int SEND_CART = 4;
    public static final int ORDER_ADD_CAR = 5;
    public static final int CHANGE_PHONE = 6;
    public static final int LOGIN = 7;
    public static final int TAB_SELECT = 8;
    public static final int TAB_UPDATE = 9;
    public static final int CANCEL_WECHAT_PAY = 10;
    public static final int PAY_SUCCESS = 11;
    public static final int BACK_MAIN = 12;

    private String msg_content;
    private int msg_type;
    private boolean boo_msg;

    public MessageEvent(){

    }

    public MessageEvent(int msg_type) {
        this.msg_type = msg_type;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public int getMsg_type() {
        return msg_type;
    }


    public void setBoolMsgContent(boolean boo_msg) {
        this.boo_msg = boo_msg;
    }

    public boolean isBoo_msg() {
        return boo_msg;
    }
}
