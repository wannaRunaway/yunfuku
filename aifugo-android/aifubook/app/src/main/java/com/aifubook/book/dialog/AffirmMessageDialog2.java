package com.aifubook.book.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.aifubook.book.R;
import com.aifubook.book.base.BaseDialog;

/**
 * 消息确认dialog
 * Created by Administrator on 2018/3/8.
 */

public class AffirmMessageDialog2 extends BaseDialog {

    private EditText message_view;

    public AffirmMessageDialog2(Context context) {
        super(context);
        initCenterLayout();
        v.setOnClickListener(R.id.dialog_cancel_btn, this);
        v.setOnClickListener(R.id.dialog_affirm_btn, this);

        message_view = v.getView(R.id.message_view);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.dialog_affirm_message2;
    }


//    public void show(int src) {
//        show();
//    }

    /**
     * 显示内容 确认
     *
     */
    public void show() {
        super.show();
    }

    public void setBtnText(int bt1, int bt2) {
        setBtnText(mContext.getString(bt1), mContext.getString(bt2));
    }

    public void setBtnText(String bt1, String bt2) {
        v.setText(R.id.dialog_cancel_btn, bt1);
        v.setText(R.id.dialog_affirm_btn, bt2);
    }

    public String getText() {
        return message_view.getText().toString();
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
