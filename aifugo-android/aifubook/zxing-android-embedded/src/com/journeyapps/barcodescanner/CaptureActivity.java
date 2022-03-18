package com.journeyapps.barcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.android.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.jiarui.base.constants.ParamsKey;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUtil;
import com.journeyapps.barcodescanner.photoscanutil.DecodeImgCallback;
import com.journeyapps.barcodescanner.photoscanutil.DecodeImgThread;
import com.journeyapps.barcodescanner.photoscanutil.ImageUtil;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * https://www.jianshu.com/p/9bd4e5d8a405
 */
public class CaptureActivity extends Activity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x12;
    private static final int REQUEST_CODE_GET_PIC_URI = 0x11;
    private static final String TAG = "CaptureActivity";
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private boolean isTorchOn;
    private TextView tv_album;
    private ImageView iv_left;
    private Button bt_open;
    private String appType;
    private View head_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int barColor = intent.getIntExtra(IntentIntegrator.BAR_COLOR, 0);
        appType = intent.getStringExtra(IntentIntegrator.APP_TYPE);

        barcodeScannerView = initializeContent();

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        iv_left = findViewById(R.id.iv_left);

        iv_left.setOnClickListener(this);

        if (barColor != 0) {
            barcodeScannerView.setBarViewColor(barColor);
        }

    }


    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        if (StringUtil.checkStr(appType)) {
            setContentView(R.layout.zxing_capture_wanse);
            head_layout=findViewById(R.id.head_layout);
            head_layout.getBackground().setAlpha(255);

        } else {
            setContentView(R.layout.zxing_capture);
            bt_open = findViewById(R.id.bt_open);
            bt_open.setOnClickListener(this);
        }
        tv_album = findViewById(R.id.tv_album);
        tv_album.setOnClickListener(this);
//        tv_album.setVisibility(View.INVISIBLE);
        return (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[],  int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goPicture();
                return;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_album) {
            //先申请权限

            int checked = ContextCompat.checkSelfPermission(CaptureActivity.this
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checked == PackageManager.PERMISSION_GRANTED) {
                goPicture();
            } else {
                ActivityCompat.requestPermissions(CaptureActivity.this
                        , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }

        } else if (i == R.id.iv_left) {
            Intent intent = new Intent();
            intent.putExtra(ParamsKey.bundle_crypto_address, "");
            setResult(RESULT_OK, intent);
            CaptureActivity.this.finish();
        } else if (i == R.id.bt_open) {

            if (barcodeScannerView != null) {
                if (isTorchOn) {
                    barcodeScannerView.setTorchOff();
                } else {
                    barcodeScannerView.setTorchOn();
                }
                isTorchOn = !isTorchOn;
            }
        }
    }

    private void goPicture() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GET_PIC_URI);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GET_PIC_URI && resultCode == Activity.RESULT_OK && data != null) {
            String path = ImageUtil.getImageAbsolutePath(this, data.getData());


            try {
                new DecodeImgThread(path, new DecodeImgCallback() {
                    @Override
                    public void onImageDecodeSuccess(Result result) {
                        handleDecode(result);
                    }

                    @Override
                    public void showResult(String result) {
                        LogUtil.e(TAG,"result=="+result);
                        if(StringUtils.isEmpty(result)){
                            return;
                        }
                        int aifugo = result.indexOf("aifugo");
                        String last = result.substring(aifugo, result.length());
                        int i = last.indexOf("|");
                        String link = last.substring(0, i);

                        Intent intent = new Intent();
                        intent.putExtra(ParamsKey.bundle_crypto_address, link);
                        setResult(RESULT_OK, intent);
                        CaptureActivity.this.finish();

                    }

                    @Override
                    public void onImageDecodeFailed() {
                        // Toast.makeText(MainActivity.this, R.string.scan_failed_tip, Toast.LENGTH_SHORT).show();
//                    ToastUtil.showTopToast(R.drawable.icon_error, "无法识别");
                        ToastUtil.showToast("无法识别", false);
                    }
                }).run();
            }catch (Exception e){
                e.printStackTrace();
                ToastUtil.showToast("无法识别", false);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                CaptureActivity.this.finish();
            }


        }


    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {
        if(rawResult==null){
            ToastUtil.showToast("无法识别",false);
            CaptureActivity.this.finish();
            return;
        }

        LogUtil.e(TAG, "rawResult==" + rawResult.getText());
        Intent intent = new Intent();
        intent.putExtra(ParamsKey.bundle_crypto_address, rawResult.getText());
        setResult(RESULT_OK, intent);
        CaptureActivity.this.finish();


    }


}
