package com.jiarui.base.utils;

import android.nfc.Tag;
import android.util.Log;

import com.jiarui.base.AppConfig;


public class LogUtil {

	private static String TAG = "xuedi";
	private static String DINEEDLOGTAG = "dineedlog";

	public static void i(String tag, String message) {
		if (AppConfig.IS_DEBUG)
			Log.i(tag, message);
	}

	public static void e(String tag, String message) {
		if (AppConfig.IS_DEBUG)
			Log.e(tag, message);
	}

	public static void e(String tag, String message, Throwable throwble) {
		if (AppConfig.IS_DEBUG)
			Log.e(tag, message, throwble);
	}

	public static void w(String tag, String message) {
		if (AppConfig.IS_DEBUG)
			Log.w(tag, message);
	}

	public static void w(String tag, String message, Throwable throwble) {
		if (AppConfig.IS_DEBUG)
			Log.w(tag, message, throwble);
	}

	public static void d(String tag, String message) {
		if (AppConfig.IS_DEBUG)
			Log.d(TAG, message);
	}

	public static void d(String message) {
		if (AppConfig.IS_DEBUG)
			Log.d(TAG, message);
	}

	public static void dneedlog(String message) {
		if (AppConfig.IS_DEBUG)
			Log.d(DINEEDLOGTAG, message);
	}

	public static void dall(String bodyMsg){
		if (bodyMsg.length() > 4000) {
			for (int i = 0; i < bodyMsg.length(); i += 4000) {
				//当前截取的长度<总长度则继续截取最大的长度来打印
				if (i + 4000 < bodyMsg.length()) {
					Log.d(TAG, bodyMsg.substring(i, i + 4000));
				} else {
					//当前截取的长度已经超过了总长度，则打印出剩下的全部信息
					Log.i(TAG, bodyMsg.substring(i, bodyMsg.length()));
				}
			}
		} else {
			//直接打印
			Log.d(TAG, bodyMsg);
		}
	}

	public static void v(String tag, String message) {
		if (AppConfig.IS_DEBUG)
			Log.v(tag, message);
	}

	public static void e(Throwable e) {
		if (AppConfig.IS_DEBUG)
			e.printStackTrace();
	}

	public static void p(Object e) {
		if (AppConfig.IS_DEBUG)
			System.out.println(e.toString());
	}
}
