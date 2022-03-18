package com.aifubook.book.productcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.activity.main.MainActivity;
import com.aifubook.book.order.MyOrderDetailsActivity;
import com.jiarui.base.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/4/26 1:11
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class PayOkeyActivity extends BaseActivity {

    @BindView(R.id.header_textview)
    TextView header_textview;
    @BindView(R.id.imageview_left)
    ImageView imageview_left;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_ok;
    }

    private String orderId;
    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
//        viewLeft.setVisibility(View.GONE);
        orderId=getIntent().getStringExtra("OrderId");
//        imageview_left.setOnClickListener(v -> finish());
        header_textview.setText("支付成功");
    }

    @OnClick({R.id.showDetails, R.id.imageview_left})
    void Onclicks(View view){
        switch (view.getId()){
            case R.id.showDetails:
            case R.id.imageview_left:
                LogUtil.e("PayOkeyActivity","orderId="+orderId);
                if (orderId.equals("0")){
                    finish();
                    Intent intent = new Intent();
                    intent.putExtra("main", 3);
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", orderId);
                startActivity(MyOrderDetailsActivity.class, bundle);
                this.finish();
                break;
        }
    }
}
