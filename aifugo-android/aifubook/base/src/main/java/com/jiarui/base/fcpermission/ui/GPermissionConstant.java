package com.jiarui.base.fcpermission.ui;

import android.Manifest;

public interface GPermissionConstant {

	/**
	 * normal：这个权限类型并不直接威胁到用户的隐私，可以直接在manifest清单里注册，系统会帮我们默认授权的。
	 */
	public static final String NORMAL_a = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
	public static final String NORMAL_b = Manifest.permission.ACCESS_NETWORK_STATE;
	public static final String NORMAL_c = Manifest.permission.ACCESS_NOTIFICATION_POLICY;
	public static final String NORMAL_d = Manifest.permission.ACCESS_WIFI_STATE;
	public static final String NORMAL_e = Manifest.permission.BLUETOOTH;
	public static final String NORMAL_f = Manifest.permission.BLUETOOTH_ADMIN;
	public static final String NORMAL_g = Manifest.permission.BROADCAST_STICKY;
	public static final String NORMAL_h = Manifest.permission.CHANGE_NETWORK_STATE;
	public static final String NORMAL_i = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
	public static final String NORMAL_j = Manifest.permission.CHANGE_WIFI_STATE;
	public static final String NORMAL_k = Manifest.permission.DISABLE_KEYGUARD;
	public static final String NORMAL_l = Manifest.permission.EXPAND_STATUS_BAR;
	public static final String NORMAL_m = Manifest.permission.GET_PACKAGE_SIZE;
	public static final String NORMAL_n = Manifest.permission.INTERNET;
	public static final String NORMAL_o = Manifest.permission.KILL_BACKGROUND_PROCESSES;
	public static final String NORMAL_p = Manifest.permission.MODIFY_AUDIO_SETTINGS;
	public static final String NORMAL_q = Manifest.permission.NFC;
	public static final String NORMAL_r = Manifest.permission.READ_SYNC_SETTINGS;
	public static final String NORMAL_s = Manifest.permission.READ_SYNC_STATS;
	public static final String NORMAL_t = Manifest.permission.RECEIVE_BOOT_COMPLETED;
	public static final String NORMAL_u = Manifest.permission.REORDER_TASKS;
	public static final String NORMAL_v = Manifest.permission.REQUEST_INSTALL_PACKAGES;
	public static final String NORMAL_w = Manifest.permission.SET_TIME_ZONE;
	public static final String NORMAL_x = Manifest.permission.SET_WALLPAPER;
	public static final String NORMAL_y = Manifest.permission.SET_WALLPAPER_HINTS;
	public static final String NORMAL_z = Manifest.permission.TRANSMIT_IR;
	public static final String NORMAL_A = Manifest.permission.USE_FINGERPRINT;
	public static final String NORMAL_B = Manifest.permission.VIBRATE;
	public static final String NORMAL_C = Manifest.permission.WAKE_LOCK;
	public static final String NORMAL_D = Manifest.permission.WRITE_SYNC_SETTINGS;
	public static final String NORMAL_E = Manifest.permission.SET_ALARM;
	public static final String NORMAL_F = Manifest.permission.INSTALL_SHORTCUT;
	public static final String NORMAL_G = Manifest.permission.UNINSTALL_SHORTCUT;

	/**
	 * dangerous：这个可以直接给app访问用户一些敏感的数据，不仅需要在manifest清单里注册，同时在使用的时候，需要向系统请求授权。
	 */
	public static final String DANGEROUS_a = Manifest.permission.READ_CALENDAR;
	public static final String DANGEROUS_b = Manifest.permission.WRITE_CALENDAR;
	public static final String DANGEROUS_c = Manifest.permission.CAMERA;
	public static final String DANGEROUS_d = Manifest.permission.READ_CONTACTS;
	public static final String DANGEROUS_e = Manifest.permission.WRITE_CONTACTS;
	public static final String DANGEROUS_f = Manifest.permission.GET_ACCOUNTS;
	public static final String DANGEROUS_g = Manifest.permission.ACCESS_FINE_LOCATION;
	public static final String DANGEROUS_h = Manifest.permission.ACCESS_COARSE_LOCATION;
	public static final String DANGEROUS_i = Manifest.permission.RECORD_AUDIO;
	public static final String DANGEROUS_j = Manifest.permission.READ_PHONE_STATE;
	public static final String DANGEROUS_k = Manifest.permission.CALL_PHONE;
	public static final String DANGEROUS_l = Manifest.permission.READ_CALL_LOG;
	public static final String DANGEROUS_m = Manifest.permission.WRITE_CALL_LOG;
	public static final String DANGEROUS_n = Manifest.permission.ADD_VOICEMAIL;
	public static final String DANGEROUS_o = Manifest.permission.USE_SIP;
	public static final String DANGEROUS_p = Manifest.permission.PROCESS_OUTGOING_CALLS;
	public static final String DANGEROUS_q = Manifest.permission.BODY_SENSORS;
	public static final String DANGEROUS_r = Manifest.permission.SEND_SMS;
	public static final String DANGEROUS_s = Manifest.permission.RECEIVE_SMS;
	public static final String DANGEROUS_t = Manifest.permission.READ_SMS;
	public static final String DANGEROUS_u = Manifest.permission.RECEIVE_WAP_PUSH;
	public static final String DANGEROUS_v = Manifest.permission.RECEIVE_MMS;
	public static final String DANGEROUS_w = Manifest.permission.READ_EXTERNAL_STORAGE;
	public static final String DANGEROUS_x = Manifest.permission.WRITE_EXTERNAL_STORAGE;

}
