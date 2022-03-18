//package com.aifubook.book.utils;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.widget.RemoteViews;
//import android.widget.Toast;
//
//import com.aifubook.book.R;
//import com.aifubook.book.application.MyApp;
//import com.aifubook.book.home.shop.ShopDetailsActivity;
//import com.aifubook.book.keycontent.KeyCom;
//import com.aifubook.book.mine.ServiceActivity;
//import com.aifubook.book.product.ProductDetailsActivity;
//import com.jiarui.base.AppConfig;
//import com.jiarui.base.utils.LogUtil;
//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.MsgConstant;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UTrack;
//import com.umeng.message.UmengMessageHandler;
//import com.umeng.message.UmengNotificationClickHandler;
//import com.umeng.message.api.UPushRegisterCallback;
//import com.umeng.message.entity.UMessage;
//
//import java.util.Map;
//
//import static android.os.Looper.getMainLooper;
//
//public class PushHelper {
//
//    private static final String TAG = "PushHelper";
//
//    public static void init(Context context) {
//        //获取消息推送实例
//        PushAgent pushAgent = PushAgent.getInstance(context);
//        Handler handler = new Handler(getMainLooper());
//
//        //sdk开启通知声音
//        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//
//            /**
//             * 通知的回调方法（通知送达时会回调）
//             */
//            @Override
//            public void dealWithNotificationMessage(Context context, UMessage msg) {
//                //调用super，会展示通知，不调用super，则不展示通知。
//                super.dealWithNotificationMessage(context, msg);
//            }
//
//            /**
//             * 自定义消息的回调方法
//             */
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//
//                handler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(context).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(context).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            /**
//             * 自定义通知栏样式的回调方法
//             */
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder;
//                        if (Build.VERSION.SDK_INT >= 26) {
//                            if (!UmengMessageHandler.isChannelSet) {
//                                UmengMessageHandler.isChannelSet = true;
//                                NotificationChannel chan = new NotificationChannel(UmengMessageHandler.PRIMARY_CHANNEL,
//                                        PushAgent.getInstance(context).getNotificationChannelName(),
//                                        NotificationManager.IMPORTANCE_DEFAULT);
//                                NotificationManager manager = (NotificationManager) context.getSystemService(
//                                        Context.NOTIFICATION_SERVICE);
//                                if (manager != null) {
//                                    manager.createNotificationChannel(chan);
//                                }
//                            }
//                            builder = new Notification.Builder(context, UmengMessageHandler.PRIMARY_CHANNEL);
//                        } else {
//                            builder = new Notification.Builder(context);
//                        }
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
//                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                                getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//                        return builder.getNotification();
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//        pushAgent.setMessageHandler(messageHandler);
//
//        /*
//         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
//         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
//         */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//
//            @Override
//            public void launchApp(Context context, UMessage msg) {
//                super.launchApp(context, msg);
//                LogUtil.e(TAG, "launchApp" + "msg=" + msg.getRaw().toString());
//                Map<String, String> extra = msg.extra;
//                String type = extra.get("type");
//                if ("1".equals(type)) {
//                    //商品详情
//                    String jumpId = extra.get("jumpId");
//                    Intent intent = new Intent();
//                    intent.setClass(context, ProductDetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("productId", "" + jumpId);
//                    intent.putExtras(bundle);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//                } else if ("2".equals(type)) {
//                    //商品分类
//                    String jumpId = extra.get("jumpId");
//                    Intent intent = new Intent();
//                    intent.setClass(context, ShopDetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("inType", "1");
//                    bundle.putString("categoryId", jumpId + "");
//                    bundle.putString("shopId", SharedPreferencesUtil.get(context, "SHOP_ID", ""));
//                    intent.putExtras(bundle);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//                } else if ("3".equals(type)) {
//                    //公告
//                    Intent intent = new Intent();
//                    intent.setClass(context, ServiceActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//
//
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage msg) {
//                super.openUrl(context, msg);
//                LogUtil.e(TAG, "openUrl" + "msg=" + msg.toString());
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
//                LogUtil.e(TAG, "openActivity" + "msg=" + msg.toString());
//            }
//
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                LogUtil.e(TAG, "dealWithCustomAction" + "msg=" + msg.toString());
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//            }
//        };
//        //使用自定义的NotificationHandler
//        pushAgent.setNotificationClickHandler(notificationClickHandler);
//
//
//        //注册推送服务 每次调用register都会回调该接口
//        pushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                LogUtil.e(TAG, "device token: " + deviceToken);
////                context.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//                if (!SharedPreferencesUtil.get(context, KeyCom.IS_LOGIN, "0").equals("0")) {
//                    //别名增加，将某一类型的别名ID绑定至某设备，老的绑定设备信息还在，别名ID和device_token是一对多的映射关系
//                    String userId = SharedPreferencesUtil.get(context, KeyCom.USER_ID, "");
//                    pushAgent.addAlias(userId, AppConfig.push_alias, new UTrack.ICallBack() {
//                        @Override
//                        public void onMessage(boolean isSuccess, String message) {
//                            LogUtil.e(TAG, "addAlias==" + isSuccess);
//                        }
//                    });
//                    //别名绑定，将某一类型的别名ID绑定至某设备，老的绑定设备信息被覆盖，别名ID和deviceToken是一对一的映射关系
//                    pushAgent.setAlias(userId, AppConfig.push_alias, new UTrack.ICallBack() {
//                        @Override
//                        public void onMessage(boolean isSuccess, String message) {
//                            LogUtil.e(TAG, "setAlias==" + isSuccess);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                LogUtil.e(TAG, "register failed: " + s + " " + s1);
////                context.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//            }
//        });
//
//
//    }
//
//}
