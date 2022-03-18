package com.aifubook.book.login;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;

/**
 * Created by ListKer_Hlg on 2021/5/26 1:08
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class WriteActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_write;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("安全管理");
    }
}
