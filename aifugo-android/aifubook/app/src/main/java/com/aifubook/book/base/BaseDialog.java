package com.aifubook.book.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;

/**
 * Created by Administrator on 2018/3/8.
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected View.OnClickListener onClickListener;
    protected BaseViewHolder v;
    private Message Message;
    private SparseArray<View> views;

    public BaseDialog(Context context) {
        super(context, R.style.UniversalDialogStyle);
        mContext = context;
        views = new SparseArray<>();
        setContentView(getContentViewId());
        v = new BaseViewHolder(getWindow());
        setCanceledOnTouchOutside(false);//击屏幕，dialog不消失；点击物理返回键dialog消失
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    protected void toast(String s){
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }
    /**
     * 居中显示
     */
    protected void initCenterLayout() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    /**
     * 底部显示Dialog
     */
    protected void initBottomLayout() {
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialog_animation);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
    }

    protected abstract int getContentViewId();

    public void setMessage(android.os.Message message) {
        Message = message;
    }

    @Override
    public void onClick(View v) {
        v.setTag(Message);
        if (onClickListener != null) onClickListener.onClick(v);
    }

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    protected void setViewClick(int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    protected void setText(int viewId, String text) {
        TextView view = getView(viewId);
        if (view != null) view.setText(text);
    }

    protected EditText getEditText(int viewId) {
        return getView(viewId);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) mContext;
    }
}
