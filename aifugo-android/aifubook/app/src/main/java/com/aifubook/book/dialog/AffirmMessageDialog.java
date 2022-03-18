package com.aifubook.book.dialog;

import android.content.Context;
import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.base.BaseDialog;

/**
 * 消息确认dialog
 * Created by Administrator on 2018/3/8.
 */

public class AffirmMessageDialog extends BaseDialog {

    public AffirmMessageDialog(Context context) {
        super(context);
        initCenterLayout();
        v.setOnClickListener(R.id.dialog_cancel_btn, this);
        v.setOnClickListener(R.id.dialog_affirm_btn, this);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.dialog_affirm_message;
    }


    public void show(int src) {
        show(mContext.getResources().getString(src));
    }

    public void dismiss(){
        super.dismiss();
    }

    /**
     * 显示内容 确认
     *
     * @param t
     */
    public void show(String t) {
        v.setText(R.id.message_view, t);
        super.show();
    }

    public void setBtnText(int bt1, int bt2) {
        setBtnText(mContext.getString(bt1), mContext.getString(bt2));
    }

    public void setBtnText(String bt1, String bt2) {
        v.setText(R.id.dialog_cancel_btn, bt1);
        v.setText(R.id.dialog_affirm_btn, bt2);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_cancel_btn) {
            dismiss();
        } else if (id == R.id.dialog_affirm_btn) {
        }
        super.onClick(v);
    }
}
