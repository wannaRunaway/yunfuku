package com.aifubook.book.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.bean.UpdateBean;
import com.aifubook.book.dialog.CenterDialogView;
import com.aifubook.book.web.X5TbsFileServicePage;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtil;
import com.jiarui.base.utils.ToastUtil;
import com.maning.updatelibrary.InstallUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class CheckUpdateUtil {


    private static final String TAG = "CheckUpdateUtil";

    public void setUpdate(FragmentActivity activity, UpdateBean updateBean) {

        int status = updateBean.getStatus();
        if (status == 0) {
            return;
        }
        showUpdateDialog(activity, updateBean);

    }


    private String url;
    private TextView tv_update;

    public Dialog showUpdateDialog(FragmentActivity activity, UpdateBean updateBean) {

        String title = updateBean.getTitle();
        String content = updateBean.getContent();
        String version = updateBean.getVersion();
        url = updateBean.getUrl();
        int status = updateBean.getStatus();

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_update, null);
        TextView tv_title = view.findViewById(R.id.update_content_text);
        TextView tv_desc = view.findViewById(R.id.desc_text);
        tv_update = view.findViewById(R.id.tv_update);
        TextView tv_close = view.findViewById(R.id.tv_close);

        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        tv_title.setText(title);
        tv_desc.setText(content);

        if (status == 2) {
            tv_close.setVisibility(View.GONE);
        } else if (status == 1) {
            tv_close.setVisibility(View.VISIBLE);
        }


        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                initCallBack(activity, tv_update, tv_update);

                PermissionX.init(activity).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                if (allGranted) {
                                    //Toast.makeText(X5TbsFileServicePage.this, "All permissions are granted", Toast.LENGTH_LONG).show();
                                    downloadApk(activity);
                                } else {
                                    ToastUtil.showToast("权限被拒: $deniedList", false);
                                }
                            }
                        });

//                if (!PermissionUtils.isGrantSDCardReadPermission(activity)) {
//                    PermissionUtils.requestSDCardReadPermission(activity, 999);
//                } else {
//                    downloadApk(activity);
//                }
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }


    private InstallUtils.DownloadCallBack downloadCallBack;
    private String apkDownloadPath;

    public void initCallBack(final Activity context, final TextView tv_progress, final TextView btnDownload) {

        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                LogUtil.i(TAG, "InstallUtils---onStart");
                tv_progress.setText("准备下载中");
                tv_progress.setVisibility(View.VISIBLE);
                tv_progress.setEnabled(false);
            }

            @Override
            public void onComplete(String path) {
                apkDownloadPath = path;
                tv_progress.setText("下载完成");
                checkInstallPermisson(context);
                tv_progress.setEnabled(true);

            }

            @Override
            public void onLoading(long total, long current) {
                int progress = (int) (current * 100 / total);
                tv_progress.setText("正在下载 " + progress + "%");
            }

            @Override
            public void onFail(Exception e) {
                btnDownload.setClickable(true);
//                btnDownload.setBackgroundResource(R.color.colorPrimary);
            }

            @Override
            public void cancle() {
                btnDownload.setClickable(true);
//                btnDownload.setBackgroundResource(R.color.colorPrimary);
            }
        };


    }

    private void checkInstallPermisson(Activity context) {

        InstallUtils.checkInstallPermission(context, new InstallUtils.InstallPermissionCallBack() {
            @Override
            public void onGranted() {
                //去安装APK
                installApk(context, apkDownloadPath);
            }

            @Override
            public void onDenied() {
                //弹出弹框提醒用户
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("温馨提示")
                        .setMessage("必须授权才能安装APK，请设置允许安装")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //打开设置页面
                                InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                    @Override
                                    public void onGranted() {
                                        //去安装APK
                                        installApk(context, apkDownloadPath);
                                    }

                                    @Override
                                    public void onDenied() {
                                        //还是不允许咋搞？
                                        Toast.makeText(context, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
    }

    public void downloadApk(Activity activity) {
        if (!StringUtil.checkStr(url)) {
            ToastUtil.showToast("下载链接返回错误", false);
            return;
        }
        LogUtil.e(TAG, "url==" + url);
        tv_update.setVisibility(View.INVISIBLE);
        InstallUtils.with(activity)
                //必须-下载地址
                .setApkUrl(url)
                //非必须-下载保存的文件的完整路径+name.apk
                .setApkPath(Constants.APK_SAVE_PATH)
                //非必须-下载回调
                .setCallBack(downloadCallBack)
                //开始下载
                .startDownload();
    }

    private void installApk(final Activity context, String path) {
        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                //tv_info.setText("安装失败:" + e.toString());
            }
        });
    }


}
