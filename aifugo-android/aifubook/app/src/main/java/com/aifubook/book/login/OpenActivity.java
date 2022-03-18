package com.aifubook.book.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.activity.main.MainActivity;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.mine.setting.PriviteActivity;
//import com.aifubook.book.utils.PushHelper;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.UmenStatisticsUtil;
//import com.umeng.message.PushAgent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/15 1:19
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class OpenActivity extends BaseActivity {

    @BindView(R.id.firstShow)
    RelativeLayout firstShow;
    private Handler mHandler;
    private int delayTime = 1500;

    @Override
    public int getLayoutId() {
        return R.layout.activity_open;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        mHandler=new Handler();
        //
        if (SharedPreferencesUtil.get(this, "isFristAgen", "1").equals("1")) {
            firstShow.setVisibility(View.VISIBLE);
        }else {
//            verifyStoragePermissions();
//            PushAgent.getInstance(this).onAppStart();
            OnclicksTimeDown();
        }
    }


    @OnClick({R.id.Services, R.id.Selfs, R.id.Ager,R.id.dotAger})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.Services:
                Bundle bundle = new Bundle();
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html");
                bundle.putString("title", "用户协议");
                startActivity(PriviteActivity.class, bundle);
                break;
            case R.id.Selfs:
                Bundle bundle1 = new Bundle();
                bundle1.putString("fig", "https://api.aifubook.com/bookstatic/html/privacy-policy-privacy.html");
                bundle1.putString("title", "隐私条款");
                startActivity(PriviteActivity.class, bundle1);
                break;
            case R.id.Ager:
//                UmenStatisticsUtil.init(OpenActivity.this, Constants.UMENG_APPKEY);
                SharedPreferencesUtil.put(this, "isFristAgen", "2");
                firstShow.setVisibility(View.GONE);
//                UmenStatisticsUtil.init(this, Constants.UMENG_APPKEY);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        PushHelper.init(getApplicationContext());
//                    }
//                });

                OnclicksTimeDown();
//                verifyStoragePermissions();
                break;
            case R.id.dotAger:
                finish();
                break;
        }
    }




    /**
     * 8      * Checks if the app has permission to write to device storage
     * 9      *
     * 10      * If the app does not has permission then the user will be prompted to
     * 11      * grant permissions
     * 12      *
     * 13      * @param activity
     * 14
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE};

//    public void verifyStoragePermissions() {
//        if (Build.VERSION.SDK_INT >= 23) {            //检查是否已经给了权限
//            int permission = ActivityCompat.checkSelfPermission(MyApp.getInstance(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                //没有给权限
//                // Log.e("permission","动态申请");                //参数分别是当前活动，权限字符串数组，requestcode
//                ActivityCompat.requestPermissions(OpenActivity.this, PERMISSIONS_STORAGE, 100);
//            } else {
//                OnclicksTimeDown();
//            }
//        } else {
//            OnclicksTimeDown();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnclicksTimeDown();
    }

    private void OnclicksTimeDown() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //set grade -> mainActivity else set grade
                String grade = SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.Companion.getGRADE(), "");
                if (grade.equals("")){
                    startActivity(CheckRoleActivity.class);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentKey.Companion.getGRADE(), grade);
                    startActivity(MainActivity.class, bundle);
                }
                OpenActivity.this.finish();
            }
        },delayTime);



     /*   new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(MainActivity.class);
                OpenActivity.this.finish();
            }
        }.start();*/
    }


}
