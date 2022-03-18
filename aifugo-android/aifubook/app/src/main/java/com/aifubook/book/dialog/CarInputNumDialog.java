package com.aifubook.book.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.aifubook.book.R;
import com.jiarui.base.utils.StringUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;

public class CarInputNumDialog {

    private CarInputNumDialog.OnClickListener listener;

    public void setOnClickListener(CarInputNumDialog.OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {

        void onConfirm(int num);

        void onCancel();
    }

    public Dialog showDialog(final Activity activity) {


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_shopping_car, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        EditText et_num = view.findViewById(R.id.et_num);
        et_num.setFocusable(true);
        et_num.setFocusableInTouchMode(true);
        et_num.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(et_num.getWindowToken(), 0);//显示
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = et_num.getText().toString();
                if (StringUtils.isEmpty(n)) {
                    return;
                }
                int num = Integer.parseInt(n);
                if (num < 1) {
                    ToastUitl.show(activity, "商品数不能小于1", Toast.LENGTH_SHORT);
                    return;
                }
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_num.getWindowToken(), 0);//隐藏
                listener.onConfirm(num);
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

}
