package com.aifubook.book.groupon;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.MiniUtil;
import com.jiarui.base.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class GrouponPaySuccessActivity extends BaseActivity implements OnCallBack {


    private static final int type_groupon_order = 0x11;
    private static final String TAG ="GrouponPaySuccessActivity";

    private String shareMiniPic, productId, leftCount;
    private TextView tv_content;

    @Override
    protected int setContentView() {
        return R.layout.activity_groupon_pay_success;
    }


    private TextView title_name;
    private ImageView leftImg;
    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
        title_name = findViewById(R.id.textview);
        leftImg = findViewById(R.id.leftImg);
        title_name.setText("支付成功");
        leftImg.setOnClickListener(v -> finish());
        String id = getIntent().getExtras().getString("id");
        shareMiniPic = getIntent().getExtras().getString("shareMiniPic");
        productId = getIntent().getExtras().getString("productId");
//        leftCount = getIntent().getExtras().getString("leftCount");

        LogUtil.e(TAG,"productId="+productId+" shareMiniPic="+shareMiniPic);
        TextView tv_detail = findViewById(R.id.tv_detail);
        TextView tv_share = findViewById(R.id.tv_share);
        tv_content = findViewById(R.id.tv_content);

        GrouponShareModel model = new GrouponShareModel(new GrouponDataModel());
        model.setOnCallBackListener(this);

        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        model.getGroupnOrderDetail(map, type_groupon_order);


        tv_detail.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", id);
            bundle1.putString("productId", productId);
            bundle1.putString("shareMiniPic", shareMiniPic);
            startActivity(GrouponShareOrderActivity.class, bundle1);
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReportDialog dialog = new ShowReportDialog();
                dialog.showGrouponShareDialog(GrouponPaySuccessActivity.this, leftCount);
                dialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        //分享到微信 小程序
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
        });


    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == type_groupon_order) {
            onOrderResult(result);
        }

    }

    @Override
    public void onError(String error, int type) {

    }

    private void onOrderResult(Object result) {
        GrouponShareBean bean = (GrouponShareBean) result;
        leftCount = bean.getLeftCount() + "";

        if(leftCount.equals("0")){

        }else{
            tv_content.setText(ContextUtil.getString(R.string.groupon_text));
        }



    }

}
