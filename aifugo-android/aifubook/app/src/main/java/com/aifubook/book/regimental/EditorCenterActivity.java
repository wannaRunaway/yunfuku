package com.aifubook.book.regimental;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.scan.RQcodeUtil;
import com.aifubook.book.utils.MiniCodeUtil;
import com.aifubook.book.utils.StatusBarUtil;
import com.aifubook.book.utils.ViewToBitmapUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class EditorCenterActivity extends BaseActivity<EditorCenterPresenter> implements EditorCenterView, View.OnClickListener {


    private static final String TAG = "EditorCenterActivity";
    @BindView(R.id.iv_head)
    RoundedImageView iv_head;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.tv_inviteCode)
    TextView tv_inviteCode;


    @BindView(R.id.layout_share)
    View layout_share;

    @BindView(R.id.showSharCancel)
    TextView showSharCancel;

    @BindView(R.id.showSharWechar)
    TextView showSharWechar;

    @BindView(R.id.showSharSave)
    TextView showSharSave;

    @BindView(R.id.ll_share)
    View ll_share;

    @BindView(R.id.image1)
    CommonImage image1;

    @BindView(R.id.image2)
    CommonImage image2;

    @BindView(R.id.image3)
    CommonImage image3;

    @BindView(R.id.ll_sh)
    View ll_sh;

    @BindView(R.id.iv_teacher)
    CommonImage iv_teacher;


    @Override
    public int getLayoutId() {
        return R.layout.activity_editor_center;
    }

    @Override
    public void initPresenter() {
        mPresenter = new EditorCenterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showSharSave:
                viewSaveToImage(ll_share);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharWechar:
                OnShareElves(ll_share);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharCancel:
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.ll_sh:
                layout_share.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_teacher:

                startActivity(TeacherAreaActivity.class);

                break;

        }
    }

    @Override
    public void initView() {

        View barView = findViewById(R.id.fillStatusBarView);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(EditorCenterActivity.this);
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusBarHeight;
        barView.setLayoutParams(layoutParams);
        ll_share.setOnClickListener(this);
        showSharSave.setOnClickListener(this);
        showSharWechar.setOnClickListener(this);
        showSharCancel.setOnClickListener(this);
        ll_sh.setOnClickListener(this);
        iv_teacher.setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startProgressDialog();
        Map map = new HashMap();
        mPresenter.chiefDetail(map);

//        iv_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layout_share.setVisibility(View.VISIBLE);
//            }
//        });


    }

    private String inviteCode;

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean dataBean) {
        stopProgressDialog();
        String name = dataBean.getName();
        String mobile = dataBean.getMobile();
        inviteCode = dataBean.getInviteCode();
        mPresenter.getWechatAccessToken();
        String unSettlementCommission = dataBean.getMemberAccount().getUnSettlementCommission();

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head); //圆角
        Glide.with(mContext)
                .load(ApiService.IMAGE + dataBean.getMember().getLogo())
                .apply(options)
                .into(iv_head);

        tv_name.setText(name);
        tv_phone.setText(mobile);
        tv_inviteCode.setText(inviteCode);
        tv_total.setText(unSettlementCommission);


    }

    @Override
    public void getChiefError(String msg) {
        stopProgressDialog();
    }

    @Override
    public void getWeChatToken(String token) {
        MiniCodeUtil codeUtil = new MiniCodeUtil();
        codeUtil.getRegisterBitmapData(token, inviteCode);

        codeUtil.setOnRequestListener(new MiniCodeUtil.RequestResponse() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onResponse(Bitmap wechatqrcode) {
                if (image3 != null) {
                    image3.setImageBitmap(wechatqrcode);
                }
            }
        });

        String shareUrl = Constants.shareUrl + "regist?inviteCode=" + inviteCode;
        LogUtil.e(TAG, "shareUrl=" + shareUrl);
        Bitmap rQcode = RQcodeUtil.getRQcode(shareUrl);
        image2.setImageBitmap(rQcode);

        Bitmap rQcode1 = RQcodeUtil.getRQcode("https://www.aifubook.com/download.html");
        image1.setImageBitmap(rQcode1);
    }

    private void viewSaveToImage(View view) {

        // 把一个View转换成图片
        // Bitmap cachebmp = loadBitmapFromView(view);

        ViewToBitmapUtil viewToBitmapUtil = new ViewToBitmapUtil();
        viewToBitmapUtil.setCacheResultListener(new ViewToBitmapUtil.CacheResult() {
            @Override
            public void result(Bitmap bitmap) {

                FileOutputStream fos;
                String imagePath = "";
                try {
                    // 判断手机设备是否有SD卡
                    boolean isHasSDCard = Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
                    if (isHasSDCard) {
                        // SD卡根目录
                        File sdRoot = Environment.getExternalStorageDirectory();
                        File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
                        fos = new FileOutputStream(file);
                        imagePath = file.getAbsolutePath();
                    } else
                        throw new Exception("创建文件失败!");

                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("asdasd", "imagePath=" + imagePath);

                view.destroyDrawingCache();
                Toast.makeText(EditorCenterActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                // 发送广播，通知刷新图库的显示
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));

            }
        });
        viewToBitmapUtil.getBitmapFromView(EditorCenterActivity.this, view);


    }

    private void OnShareElves(View view) {

        if (!MyApp.mWxApi.isWXAppInstalled()) {
            return;
        }

        ViewToBitmapUtil viewToBitmapUtil = new ViewToBitmapUtil();
        viewToBitmapUtil.setCacheResultListener(new ViewToBitmapUtil.CacheResult() {
            @Override
            public void result(Bitmap bitmap) {

                WXImageObject imageObject = new WXImageObject(bitmap);
                WXMediaMessage msg = new WXMediaMessage(imageObject);
                msg.title = "云辅库";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                //WXSceneTimeline朋友圈    WXSceneSession聊天界面
                req.scene = SendMessageToWX.Req.WXSceneSession;//聊天界面  1 == 1 ? SendMessageToWX.Req.WXSceneTimeline :
                req.message = msg;
                req.transaction = String.valueOf(System.currentTimeMillis());
                MyApp.mWxApi.sendReq(req);


            }
        });
        viewToBitmapUtil.getBitmapFromView(EditorCenterActivity.this, view);


    }


}
