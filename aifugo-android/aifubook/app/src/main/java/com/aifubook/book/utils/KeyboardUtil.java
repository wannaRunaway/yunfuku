package com.aifubook.book.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;


public class KeyboardUtil {


	public static void showSoftInputFromWindow(AppCompatEditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager inputManager =
				(InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(editText, 0);

	}

	public static void toggleKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(null!=imm){
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	public static void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(null!=imm){
			view.requestFocus();
			//显示键盘
			imm.showSoftInput(view,0);

		}
	}

	public static void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(null!=imm){
			imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		}
	}

	/**
	 * 显示软键盘
	 */
	public static void showInputMethod(Context context, View view) {
		if (context == null || view == null) {
			return;
		}

		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 关闭软键盘
	 */
	public static boolean hideInputMethod(Context context, View view) {
		if (context == null || view == null) {
			return false;
		}

		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && imm.isActive()) {
			return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		return false;
	}

}
