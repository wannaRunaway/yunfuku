package com.jiarui.base.db.service;


import com.jiarui.base.appcommon.GlobalAppComponent;
import com.jiarui.base.constants.ParamsKey;
import com.jiarui.base.db.SharePreferDB;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtil;

public class UserDataService {

    private static final String TAG = "UserDataService";
    private SharePreferDB mSharePreferDB;

    public UserDataService() {
        mSharePreferDB = new SharePreferDB(GlobalAppComponent.getAppComponent().getContext(), "logindata");
    }

    public void setCrypto_address(String address) {
        mSharePreferDB.saveData("crypto_address", address);
    }

    public String getCryptoAddress() {
        return mSharePreferDB.getValue("crypto_address");
    }

    public void setFirstNot(boolean isFirst) {
        mSharePreferDB.saveBooleanData("is_first", isFirst);
    }

    public boolean isFirstNot() {
        return mSharePreferDB.getBooleanData("is_first");
    }

    public void saveAccessToken(String accesstoken) {
        mSharePreferDB.saveData(ParamsKey.access_token, accesstoken);
    }

    public String getAccessToken() {
        return mSharePreferDB.getValue(ParamsKey.access_token, "");
    }

    public void saveSession(String session) {
        mSharePreferDB.saveData(ParamsKey.session, session);
    }

    public String getSession() {
        return mSharePreferDB.getValue(ParamsKey.session);
    }

    public void saveAccount(String userAccount) {
        mSharePreferDB.saveData(ParamsKey.userAccount, userAccount);
    }

    public String getAccount() {
        return mSharePreferDB.getValue(ParamsKey.userAccount);
    }

    public void setLogin(boolean isLogin) {
        mSharePreferDB.saveBooleanData(ParamsKey.islogin, isLogin);
    }

    public boolean isLogin() {
        return StringUtil.checkStr(getAccessToken());
    }

}
