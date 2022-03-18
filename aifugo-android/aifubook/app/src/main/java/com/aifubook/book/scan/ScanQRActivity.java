package com.aifubook.book.scan;

import android.content.Intent;
import android.util.Log;
import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.utils.ContextUtil;
import com.google.zxing.integration.android.IntentResult;
import com.jiarui.base.constants.ParamsKey;
import com.jiarui.base.utils.LogUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.jiarui.base.utils.StringUtils;

public class ScanQRActivity extends BaseActivity {


    private static final String TAG = "ScanQRActivity";

    private static int person_search_req = 0x11;
    private static int business_search_req = 0x12;
    private static int contact_search_req = 0x13;
    private static int get_user_detail = 0x14;
    private static final int GET_REAL_URL = 0x98;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scanqr;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        initData();
    }


    private void initData() {


        IntentIntegrator integrator = new IntentIntegrator(ScanQRActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        // integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setBarColor(ContextUtil.getColor(R.color.blue_text_color));
        integrator.setAppType(IntentIntegrator.APP_TYPE_WANSE);
        integrator.initiateScan();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                if (resultCode == RESULT_OK) {
                    mResult = data.getStringExtra(ParamsKey.bundle_crypto_address);
                    Log.e(TAG, "result==" + mResult);
                    decodeComplete(mResult, 0);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("data_return", "");
                    setResult(RESULT_OK, intent);
                    ScanQRActivity.this.finish();
                    super.onActivityResult(requestCode, resultCode, data);
                }
            } else {
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                mResult = result.getContents();
                LogUtil.e(TAG, "result==" + mResult);
//                textView.setVisibility(View.VISIBLE);
                decodeComplete(mResult, 0);
            }
        } else {
            Intent intent = new Intent();
            intent.putExtra("data_return", "");
            setResult(RESULT_OK, intent);
            ScanQRActivity.this.finish();
            super.onActivityResult(requestCode, resultCode, data);


        }

    }


    private String address;
    private String mResult;

    /**
     * 这里只做了一次处理
     *
     * @param result
     * @param requestCode
     */
    public void decodeComplete(String result, int requestCode) {
        LogUtil.e(TAG, "requestCode==" + requestCode + "ffffff= " + result);
        Intent intent = new Intent();
        if(!StringUtils.isEmpty(result)){
            intent.putExtra("data_return", "" + result.toString());
        }
        setResult(RESULT_OK, intent);
        ScanQRActivity.this.finish();

    }


}
