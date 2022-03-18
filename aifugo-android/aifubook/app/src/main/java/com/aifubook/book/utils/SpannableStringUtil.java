package com.aifubook.book.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.jiarui.base.utils.LogUtil;


public class SpannableStringUtil {

    private static final String TAG = "SpannableStringUtil";


    public static SpannableStringBuilder spannableString(Context context,TextView textView, String text, int start1, int end1, int start2, int end2) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        //setSpan可多次使用
        ForegroundColorSpan fcs1 = new ForegroundColorSpan(ContextUtil.getColor(context,R.color.view_color_ff0101));
        spannableString.setSpan(fcs1, start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(new TextClick(1), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs2 = new ForegroundColorSpan(ContextUtil.getColor(context,R.color.view_color_ff0101));
        spannableString.setSpan(fcs2, start2, end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new TextClick(2), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

   /* public static SpannableStringBuilder ws_spannableString(TextView textView, String text, int start1, int end1, int start2, int end2) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        //setSpan可多次使用
        ForegroundColorSpan fcs1 = new ForegroundColorSpan(ContextUtil.getColor(R.color.view_color_ff0101));
        spannableString.setSpan(fcs1, start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(new TextClick(1), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs2 = new ForegroundColorSpan(ContextUtil.getColor(R.color.view_color_ff0101));
        spannableString.setSpan(fcs2, start2, end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new TextClick(2), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }*/


    public static SpannableStringBuilder spannableString2(Context context,String text,TextView textView, int start1, int end1) {

        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        //setSpan可多次使用
        ForegroundColorSpan fcs1 = new ForegroundColorSpan(ContextUtil.getColor(context,R.color.view_color_ff0101));
        spannableString.setSpan(fcs1, start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(new TextClick(3), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

   /* public static SpannableStringBuilder spannableString3(TextView textView, String text, String url, int start1, int end1) {

        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        //setSpan可多次使用
        ForegroundColorSpan fcs1 = new ForegroundColorSpan(ContextUtil.getColor(R.color.view_color_ff0101));
        spannableString.setSpan(fcs1, start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //textView.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(new TextClick(1,url), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;

    }*/


    private static class TextClick extends ClickableSpan {

        private int flag;
        private String url;

        public TextClick(int i) {
            this.flag = i;
        }

        public TextClick(int i, String url) {
            this.flag = i;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            LogUtil.e(TAG, "onClick==" + url);
            Bundle bundle = new Bundle();

            if (flag == 1) {
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html");
                bundle.putString("title", "用户协议");
            } else if (flag == 2) {
                bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/privacy-policy-privacy.html");
                bundle.putString("title", "隐私条款");
            } else if(flag==3){
                bundle.putString("fig", "https://www.aifubook.com/destory_privacy.html");
                bundle.putString("title", "注销须知");
            }
            //startActivity(PriviteActivity.class, bundle);
            ArouterUtil.greenChannel("/mine/setting/PriviteActivity",bundle);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);//去除超链接的下划线
        }
    }


}
