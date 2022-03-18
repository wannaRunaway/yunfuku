package com.aifubook.book.web;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.base.BaseActivity;
import com.jiarui.base.utils.LogUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class X5TbsFileServicePage extends BaseActivity implements TbsReaderView.ReaderCallback {


    private static final String TAG = "X5TbsFileServicePage";
    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    TbsReaderView mTbsReaderView;
    RelativeLayout mRelativeLayout;

    private String filePath;


    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);

        filePath = getIntent().getStringExtra("filePath");
        LogUtil.e(TAG, "filePath=" + filePath);

        // 绑定容器
        mRelativeLayout = findViewById(R.id.X5TbsView);
        PermissionX.init(this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            //Toast.makeText(X5TbsFileServicePage.this, "All permissions are granted", Toast.LENGTH_LONG).show();
                            displayFile(filePath);
                        } else {
                            Toast.makeText(X5TbsFileServicePage.this, "权限被拒: $deniedList", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_x5_tbs_file_service_page;
    }

    public void displayFile(String filePath) {
        mTbsReaderView = new TbsReaderView(this, this);
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        File bsReaderTempFile = new File(filePath);

        if (!bsReaderTempFile.exists()) {
            LogUtil.e("print", "文件不存在准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                LogUtil.e("print", "创建/TbsReaderTemp失败！！！！！");
            }
            Toast.makeText(this, "文件不存在！", Toast.LENGTH_SHORT).show();
        }
        File fileDir = new File(tbsReaderTemp);
        if (!fileDir.exists()) {
            if (!fileDir.mkdir()) {
                LogUtil.e("print2", "创建/TbsReaderTemp失败！！！！！");
            }
        }

        mRelativeLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(filePath), false);
        LogUtil.e("print", "查看文档---" + result);
        if (result) {

            try {
                mTbsReaderView.openFile(bundle);
            }catch (Exception e){
                e.printStackTrace();
                openByOther();
            }

        } else {
            //如果打开失败 就调用本机其它软件进行打开查看
          openByOther();

        }
    }

    private void openByOther(){
        try {
            String fileType = getFileType(filePath);
            if ("pdf".equals(fileType)) {
                Intent intent = getPdfFileIntent(filePath);
                startActivity(intent);
                finish();
            } else if ("ppt".equals(fileType)) {
                Intent intent = getPPTFileIntent(filePath);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "请在手机安装支持打开ppt和pdf的软件进行查看", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    //android获取一个用于打开PDF文件的intent
    public Intent getPdfFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(X5TbsFileServicePage.this, "com.aifubook.book.provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/pdf");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
        }
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public Intent getPPTFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(X5TbsFileServicePage.this, "com.aifubook.book.provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        }
        return intent;
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        LogUtil.e(TAG,"onCallBackAction="+integer+" "+o.toString()+" "+o1.toString());
    }

    /**
     * 返回上个页面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
            mTbsReaderView.destroyDrawingCache();
        }
    }

}
