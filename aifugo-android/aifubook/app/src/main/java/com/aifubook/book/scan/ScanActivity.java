//package com.aifubook.book.scan;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.aifubook.book.R;
//import com.aifubook.book.activity.main.BaseActivity;
//import com.google.zxing.ResultPoint;
//import com.jiarui.base.promptlibrary.PromptDialog;
//import com.jiarui.base.utils.ImageUtil;
//import com.jiarui.base.utils.LogUtil;
//import com.jiarui.base.utils.ToastUtil;
//import com.journeyapps.barcodescanner.BarcodeCallback;
//import com.journeyapps.barcodescanner.BarcodeResult;
//import com.journeyapps.barcodescanner.CaptureManager;
//import com.journeyapps.barcodescanner.DecoratedBarcodeView;
//
//import java.util.List;
//
///**
// * Created by ListKer_Hlg on 2019/12/5
// * 此类是作用于:
// * 邮箱: 1425034784@qq.com
// */
//public class ScanActivity extends BaseActivity implements DecoratedBarcodeView.TorchListener {
//    DecoratedBarcodeView mDBV;
//    private CaptureManager captureManager;
//    LinearLayout iv_left;
//    private static final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x12;
//    private static final int REQUEST_CODE_GET_PIC_URI = 0x11;
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        captureManager.onPause();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        captureManager.onResume();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        captureManager.onDestroy();
//    }
//
//    Bundle outStates;
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outStates = outState;
//        captureManager.onSaveInstanceState(outState);
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
//    }
//
//    List<String> sadas;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_scan;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
//        promptDialog = new PromptDialog(this);
//        iv_left = findViewById(R.id.iv_left);
//        iv_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ScanActivity.this.finish();
//            }
//        });
//        mDBV.setTorchListener(this);
//
//        // 如果没有闪光灯功能，就去掉相关按钮
//        if (!hasFlash()) {
//        }
//        //重要代码，初始化捕获
//        captureManager = new CaptureManager(this, mDBV);
//        captureManager.initializeFromIntent(getIntent(), outStates);
//        captureManager.decode();
//        mDBV.decodeContinuous(callback);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_GET_PIC_URI && resultCode == Activity.RESULT_OK && data != null) {
//            String path = ImageUtil.getImageAbsolutePath(this, data.getData());
//
//
//            new DecodeImgThread(path, new DecodeImgCallback() {
//                @Override
//                public void onImageDecodeSuccess(Result result) {
//                    handleDecode(result);
//                }
//
//                @Override
//                public void onImageDecodeFailed() {
//                    // Toast.makeText(MainActivity.this, R.string.scan_failed_tip, Toast.LENGTH_SHORT).show();
//                    ToastUtil.showTopToast(com.google.zxing.client.android.R.drawable.icon_error, "无法识别");
//                }
//            }).run();
//
//
//        }
//
//
//    }
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 扫码回调
//     * 简述:
//     * 时间: 2019/1/17
//     */
//    private BarcodeCallback callback = new BarcodeCallback() {
//        @Override
//        public void barcodeResult(final BarcodeResult result) {
//            LogUtil.e("barcodeResult", "www" + result.toString());
////            String Results = result.toString();
////            SaveSelect(Results);
//            Intent intent = new Intent();
//            intent.putExtra("data_return", "" + result.toString());
//            setResult(RESULT_OK, intent);
//            ScanActivity.this.finish();
//        }
//
//        @Override
//        public void possibleResultPoints(List<ResultPoint> resultPoints) {
////             Log.e("XXXXXXXXXX", "MMMMMMMMM" + resultPoints.size());
////            ToastUitl.showShort(CustomScanAct.this, resultPoints.size() + "");
//        }
//    };
//
//    private void SaveSelect(String Id) {
//    }
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 手电筒
//     * 简述:
//     * 时间: 2019/1/17
//     */
//    @Override
//    public void onTorchOn() {
//        Toast.makeText(this, "torch on", Toast.LENGTH_LONG).show();
//    }
//
//
//    @Override
//    public void onTorchOff() {
//        Toast.makeText(this, "torch off", Toast.LENGTH_LONG).show();
//    }
//
//    /**
//     * 作者: ListKer_Hlg
//     * 用途: 闪光灯
//     * 简述:
//     * 时间: 2019/1/17
//     */
//    private boolean hasFlash() {
//        return getApplicationContext().getPackageManager()
//                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//    }
//}
