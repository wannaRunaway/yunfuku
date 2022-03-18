package com.aifubook.book.regimental;

public interface EditorCenterView {

    // 团长详情信息
    void ChiefDetailSuc(ChiefDetailsBean DataBean);

    void getChiefError(String msg);

    void getWeChatToken(String token);


}
