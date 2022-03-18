package com.aifubook.book.mine.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.jiarui.base.utils.ToastUitl;

import butterknife.BindView;

/**
 * Created by ListKer_Hlg on 2021/5/13 21:35
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class BackMessageActivity extends BaseActivity {

    @BindView(R.id.mEditText)
    EditText mEditText;

    @BindView(R.id.submitTextView)
    TextView submitTextView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_back_message;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
setTitle("意见反馈");
        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUitl.showShort(BackMessageActivity.this,"您的意见已提交!");
                BackMessageActivity.this.finish();
            }
        });

    }
}
