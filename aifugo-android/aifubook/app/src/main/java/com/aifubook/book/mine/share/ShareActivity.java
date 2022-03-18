package com.aifubook.book.mine.share;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jiarui.base.glide.GlideImageLoader;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.homeImg)
    ImageView homeImg;

    @BindView(R.id.shareCode)
    ImageView shareCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    //    https://api.aifubook.com/bookstatic/static/images/share/my-share-bg-1.jpg
    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("分享有礼");
        GlideImageLoader.create(homeImg).load("https://api.aifubook.com/bookstatic/static/images/share/my-share-bg-1.jpg", new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
        shareCode.setImageBitmap(createQRCode("{\"inviteCode\":" + SharedPreferencesUtil.get(ShareActivity.this, "INVICODE", "") + ",\"type\":2}"));
    }

    /**
     * 生成二维码,默认500大小
     *
     * @param contents 需要生成二维码的文字、网址等
     * @return bitmap
     */
    BarcodeEncoder barcodeEncoder;

    public Bitmap createQRCode(String contents) {
        barcodeEncoder = new BarcodeEncoder();
        try {
            return barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 500, 500);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
