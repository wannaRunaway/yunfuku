package com.aifubook.book.utils;

/**
 * Created by ListKer_Hlg on 2018/10/25
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class Param {
    String key;
    String value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
