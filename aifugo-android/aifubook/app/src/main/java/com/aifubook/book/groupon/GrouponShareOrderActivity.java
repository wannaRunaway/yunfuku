package com.aifubook.book.groupon;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.bean.GrouponShareBean;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.model.GrouponDataModel;
import com.aifubook.book.model.GrouponShareModel;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.utils.BitmapUtil;
import com.aifubook.book.utils.MiniUtil;
import com.aifubook.book.view.RotateTextView;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrouponShareOrderActivity extends BaseActivity implements OnCallBack {

    private static final int type_groupon_order = 0x11;
    private String id;//拼单id
    private String productId;//商品id
    private String shareMiniPic;//分享图片
    private TextView tv_day,tv_full;
    private LinearLayout ll_last;


    @Override
    protected int setContentView() {
        return R.layout.activity_groupon_share_order;
    }

    private TextView title_name;
    private ImageView leftImg;
    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
        title_name = findViewById(R.id.textview);
        leftImg = findViewById(R.id.leftImg);
        title_name.setText("拼单详情");
        leftImg.setOnClickListener(v -> finish());
//        mHeadView.setCentreTextView("拼单详情");
        id = getIntent().getExtras().getString("id");
        productId = getIntent().getExtras().getString("productId");
        shareMiniPic = getIntent().getExtras().getString("shareMiniPic");

        findView();
        initData();
    }

    private void initData() {

        GrouponShareModel model = new GrouponShareModel(new GrouponDataModel());
        model.setOnCallBackListener(this);

        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        model.getGroupnOrderDetail(map, type_groupon_order);

    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type_groupon_order == type) {
            onOrderResult(result);
        }

    }


    @Override
    public void onError(String error, int type) {
        showToast(error);
    }


    private int leftCount;
    private int count;
    private void onOrderResult(Object result) {

        GrouponShareBean bean = (GrouponShareBean) result;


        leftCount = bean.getLeftCount();
        count=bean.getCount();
        tv_group_num.setText(count+"人团");
        long leftTime = bean.getLeftTime();
        String productName = bean.getProductName();
        String productImage = bean.getProductImage();
        int productPrice = bean.getProductPrice();
        int groupPrice = bean.getGroupPrice();
        shareMiniPic=ApiService.IMAGE + productImage;
        mImageView.setImageURI(ApiService.IMAGE + productImage);
        tv_name.setText(productName);
        tv_group_price.setText("¥" + StringUtils.isNull((Double.parseDouble(groupPrice + "") / 100) + ""));
        tv_price.setText("¥" + StringUtils.isNull((Double.parseDouble(productPrice + "") / 100) + ""));
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        tv_last.setText(leftCount + "");
        showTimeCounter(leftTime);

        ll_groupon.removeAllViews();
        List<GrouponShareBean.Users> users = bean.getUsers();
        for (int i = 0; i < users.size(); i++) {
            GrouponShareBean.Users user = users.get(i);
            String userImage = user.getUserImage();
            View view = mInflater.inflate(R.layout.item_groupon_portait, null);
            CommonImage image = view.findViewById(R.id.cimage1);
            TextView tv_chief = view.findViewById(R.id.tv_chief);
            if(!userImage.contains("http")) {
                image.setImageURI(ApiService.IMAGE + userImage);
            }else{
                image.setImageURI(userImage);
            }
            tv_chief.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
            if (i < 4) {
                ll_groupon.addView(view);
            }
        }

        View plusView = mInflater.inflate(R.layout.view_plus, null);
        ImageView iv_add = plusView.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                toShare();
            }
        });

        ll_groupon.addView(plusView);


    }

    private CountDownTimer countDownTimer;

    private void showTimeCounter(long times) {

        if (times <= 0) {
            return;
        }
        countDownTimer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                long hour = (millisUntilFinished - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minute = (millisUntilFinished - days * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = millisUntilFinished % 60000;
                long second = Math.round((float) seconds / 1000);
                if(days>0) {
                    tv_day.setText(days + "天");
                    tv_day.setVisibility(View.VISIBLE);
                }else{
                    tv_day.setVisibility(View.GONE);
                }
                tv_hours.setText(hour + "");
                tv_minutes.setText(minute + "");
                tv_seconds.setText(second + "");

            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    private View rl_groupon;
    private LinearLayout ll_groupon;
    private CommonImage mImageView;
    private TextView tv_type, tv_name, tv_group_price,
            tv_price, tv_count, tv_last, tv_hours, tv_minutes,
            tv_seconds, tv_share;
    private RotateTextView tv_group_num;

    private void findView() {

        rl_groupon = findViewById(R.id.rl_groupon);
        mImageView = findViewById(R.id.mImageView);
        tv_type = findViewById(R.id.tv_type);
        tv_name = findViewById(R.id.tv_name);
        tv_group_price = findViewById(R.id.tv_group_price);
        tv_price = findViewById(R.id.tv_price);
        tv_count = findViewById(R.id.tv_count);
        tv_last = findViewById(R.id.tv_last);
        tv_hours = findViewById(R.id.tv_hours);
        tv_minutes = findViewById(R.id.tv_minutes);
        tv_seconds = findViewById(R.id.tv_seconds);
        tv_share = findViewById(R.id.tv_share);
        ll_groupon = findViewById(R.id.ll_groupon);
        tv_day = findViewById(R.id.tv_day);
        ll_last = findViewById(R.id.ll_last);
        tv_full = findViewById(R.id.tv_full);
        tv_group_num=findViewById(R.id.tv_group_num);



        rl_groupon.setOnClickListener(this);
        tv_share.setOnClickListener(this);

    }

    private void toShare() {
        ShowReportDialog dialog = new ShowReportDialog();
        dialog.showGrouponShareDialog(GrouponShareOrderActivity.this, leftCount+"");
        dialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                showLoadingDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapUtil.getBitmap(shareMiniPic);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeLoadingDialog();
                                MiniUtil.shareMiniToWx(bitmap, productId);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onCancel() {

            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_groupon:
                startActivity(GrouponActivity.class);
                break;
            case R.id.tv_share:
                //分享到微信 小程序
                toShare();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


}
