package com.jiarui.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.jiarui.base.R;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by wanghao on 2016/8/26.
 */
public class DialogUtil {

    public static void showAlert(Context context,String content,DialogInterface.OnClickListener l){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(content).setPositiveButton(context.getResources().getString(R.string.Confirm),l).show();
    }

    public static void showConfirmCancleAlert(Context context,String content,DialogInterface.OnClickListener l){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setNegativeButton(context.getResources().getString(R.string.btn_cancel),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
            }
        });
        AlertDialog dialog = builder.setPositiveButton(context.getResources().getString(R.string.Confirm), l).show();
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setT
    }

    /**
     * 有取消回调的进度dialog
     * @param context
     * @return dialog
     */
    public static Dialog createLoadingDialog(Activity context) {
        if(context == null || context.isFinishing())return null;
        final Dialog dialog = new Dialog(context , R.style.NoBackGroundDialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        View view = context.getLayoutInflater().inflate(
                R.layout.loading_dialog, null);
        window.setContentView(view);//
        return dialog;

    }
}
