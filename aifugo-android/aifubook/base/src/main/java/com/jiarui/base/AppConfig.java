package com.jiarui.base;



public class AppConfig {

    public static final int cacheSize = 10 * 1024 * 1024;
    public static final int read_time = 30 * 1000;
    public static final int write_time = 30 * 1000;
    public static final int connect_time = 30 * 1000;

    public static String app_name;
    public static String app_ver;
    public static String down_cl;


    public static final boolean needUmengStatistics = true;//开发阶段为false,上线后改为true
    public static final boolean IS_DEBUG = true;//Log日志开关，true为打开日志
//    public static final String push_alias = "test";
    public static final String push_alias = "pro";

    /*public void setOnLine(boolean isOnLine) {
        UserDataService userDataService = new UserDataService();
        userDataService.setOnLine(isOnLine);
    }

    public boolean getIsOnLine() {
        return new UserDataService().getOnLine();
    }*/




}
