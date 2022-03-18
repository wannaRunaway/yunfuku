package com.aifubook.book.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.aifubook.book.R;
import com.aifubook.book.utils.ScreenUtils;


/**
 * Created by AnyOne on 2018/3/7.
 */

public class BaseDialog extends Dialog {
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private int mGravity;
    private boolean mOnTouchCanceled;
    private  boolean mCanCanceled=true;
    private View mView;
    private int mStyleAnimation;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBotton;

    public BaseDialog(Builder builder) {
        super(builder.bContext);
    }

    public BaseDialog(Builder builder, int themeResId) {
        super(builder.bContext, themeResId);
        this.mContext = builder.bContext;
        this.mHeight = builder.bHeight;
        this.mWidth = builder.bWidth;
        this.mView = builder.bView;
        this.mGravity = builder.bGravity;
        this.mStyleAnimation = builder.bStyleAnimation;
        this.mOnTouchCanceled = builder.bOnTouchCanceled;
        this.mCanCanceled=builder.isCanCanceled;
        this.mPaddingLeft = builder.bPaddingLeft;
        this.mPaddingTop = builder.bPaddingTop;
        this.mPaddingRight = builder.bPaddingRight;
        this.mPaddingBotton = builder.bPaddingBotton;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.mView);
        this.setCanceledOnTouchOutside(this.mOnTouchCanceled);
        this.setCancelable(this.mCanCanceled);
        Window window = this.getWindow();
        if(this.mPaddingLeft != -11111) {
            window.getDecorView().setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBotton);
        }

        if(this.mStyleAnimation != -11111) {
            window.setWindowAnimations(this.mStyleAnimation);
        }

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = this.mGravity != -11111?this.mGravity:17;
        layoutParams.width = this.mWidth != -11111?this.mWidth:-2;
        layoutParams.height = this.mHeight != -11111?this.mHeight:-2;
        window.setAttributes(layoutParams);
    }

    public void close() {
        if(!((Activity)this.mContext).isFinishing()) {
            ((Activity)this.mContext).runOnUiThread(new Runnable() {
                public void run() {
                    if(BaseDialog.this.isShowing()) {
                        BaseDialog.this.dismiss();
                    }

                }
            });
        }

    }

    @Override
    public void dismiss() {
        hideKeyboard();
        super.dismiss();

    }



    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public <T extends View> T getView(int resId) {
        return this.mView != null? (T) this.mView.findViewById(resId) :null;
    }

    public static final class Builder {
        private Context bContext;
        private int bHeight = -11111;
        private int bWidth = -11111;
        private int bGravity = -11111;
        private boolean bOnTouchCanceled;
        private boolean isCanCanceled;
        private View bView;
        private int bThemeResId;
        private int bStyleAnimation;
        private int bPaddingLeft;
        private int bPaddingTop;
        private int bPaddingRight;
        private int bPaddingBotton;

        public Builder(Context mContext) {
            this.bThemeResId = R.style.dialogWindowAnim;
            this.bStyleAnimation = -11111;
            this.bPaddingLeft = -11111;
            this.bContext = mContext;


        }

        public Builder setViewId(int viewId) {
            this.bView = LayoutInflater.from(this.bContext).inflate(viewId, (ViewGroup)null);
           // AutoUtils.auto(bView);
            return this;
        }



        public Builder setWidthHeightdp(int whidth, int height) {
            this.bWidth = BaseDialog.dip2px(this.bContext, (float)whidth);
            this.bHeight = BaseDialog.dip2px(this.bContext, (float)height);
            return this;
        }



        public Builder setHeightdp(int height) {
            this.bHeight = BaseDialog.dip2px(this.bContext, (float)height);
            return this;
        }



        public Builder setWidthMatchparent(Context context) {
            this.bWidth = ScreenUtils.getScreenWidth(context);
            return this;
        }

        public Builder setWidthPercent(Context context, float percent) {
            this.bWidth = (int) (ScreenUtils.getScreenWidth(context)*percent);
            return this;
        }

        public Builder setWidthdp(int whidth) {
            this.bWidth = BaseDialog.dip2px(this.bContext, (float)whidth);
            return this;
        }



        public Builder isOnTouchCanceled(boolean var) {
            this.bOnTouchCanceled = var;
            return this;
        }

        public Builder isCanceled(boolean var) {
            this.isCanCanceled = var;
            return this;
        }

        public Builder addViewOnClickListener(int viewId, OnClickListener listener) {
            this.bView.findViewById(viewId).setOnClickListener((View.OnClickListener) listener);
            return this;
        }

        public Builder setStyle(int themeResId) {
            this.bThemeResId = themeResId;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.bGravity = gravity;
            return this;
        }

        public Builder setAnimation(int styleAnimation) {
            this.bStyleAnimation = styleAnimation;
            return this;
        }

        public Builder setPaddingdp(int paddingLeft, int paddingTop, int paddingRight, int paddingBotton) {
            this.bPaddingLeft = BaseDialog.dip2px(this.bContext, (float)paddingLeft);
            this.bPaddingTop = BaseDialog.dip2px(this.bContext, (float)paddingTop);
            this.bPaddingRight = BaseDialog.dip2px(this.bContext, (float)paddingRight);
            this.bPaddingBotton = BaseDialog.dip2px(this.bContext, (float)paddingBotton);
            return this;
        }

        public Builder setPaddingpx(int paddingLeft, int paddingTop, int paddingRight, int paddingBotton) {
            this.bPaddingLeft = paddingLeft;
            this.bPaddingTop = paddingTop;
            this.bPaddingRight = paddingRight;
            this.bPaddingBotton = paddingBotton;
            return this;
        }

        public BaseDialog builder() {
            return new BaseDialog(this, this.bThemeResId);
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
        }
    }
}