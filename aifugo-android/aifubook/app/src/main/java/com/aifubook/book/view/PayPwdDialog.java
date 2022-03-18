package com.aifubook.book.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.aifubook.book.R;
import com.aifubook.book.utils.DensityUtil;
import com.aifubook.book.utils.ScreenUtils;

public class PayPwdDialog {


    private Context mContext;
    private BaseDialog mDialog;

    private CustomEditor et;
    private CustomKeyBoard keyBoard;
    private InputOver inputOver;
    ImageView ivBack;


    public PayPwdDialog(Context context) {
        mContext = context;

        init();
    }

    public PayPwdDialog(Context context, InputOver inputOver) {
        mContext = context;
        this.inputOver = inputOver;
        init();
    }


    private void init() {
        int width = DensityUtil.px2dp2(mContext, ScreenUtils.getScreenWidth(mContext));
        int height = DensityUtil.px2dp2(mContext, ScreenUtils.getScreenHeight(mContext));
        BaseDialog.Builder mBuilder = new BaseDialog.Builder(mContext);
        mDialog = mBuilder.setViewId(R.layout.item_pay_pwd)
                .setAnimation(R.style.dialogWindowAnim)
                .setGravity(Gravity.BOTTOM)
                .isCanceled(false)
                .isOnTouchCanceled(false)
                .setHeightdp(height / 2)
                .setWidthdp(width)
                .builder();
        keyBoard = mDialog.getView(R.id.cus_keyboard);
        et = mDialog.getView(R.id.et_pwd);

        keyBoard.setOnKeyBoardClickLitener(new CustomKeyBoard.OnKeyBoardClickLitener() {

            @Override
            public void getNumber(int num) {
                et.setPassword(num);
            }
        });

        et.setOnPasswordSixListener(new CustomEditor.OnPasswordInputOverListener() {

            @Override
            public void inputOver(String password) {
                if (inputOver != null) {
                    inputOver.onInputOver(password);
                    et.setPassword(-2);
                    mDialog.close();
                }
            }

        });
        mDialog.getView(R.id.tv_forget_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((BaseActivity)mContext).launch(ForgetPayPwdActivity.class);
            }
        });

        mDialog.getView(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputOver != null) {
                    inputOver.onCancel();
                    et.setPassword(-2);
                    mDialog.dismiss();
                }
            }
        });

    }


    public void show() {
        mDialog.show();
    }

    public void setInputOver(InputOver inputOver) {
        this.inputOver = inputOver;
    }

    public interface InputOver {
        void onInputOver(String password);

        void onCancel();
    }
}
