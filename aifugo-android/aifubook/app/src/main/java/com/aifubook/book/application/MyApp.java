package com.aifubook.book.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.aifubook.book.Constants;
import com.aifubook.book.utils.ArouterUtil;
//import com.aifubook.book.utils.PushHelper;
import com.aifubook.book.web.X5App;
import com.jiarui.base.appcommon.GlobalAppComponent;
import com.jiarui.base.bases.BaseApplication;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;

import java.util.HashMap;


/**
 * Created by ListKer_Hlg on 2018/7/30
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class MyApp extends BaseApplication {

    public static String Card = "";
    private static final String TAG = "MyApp";

    public static String headimgurl = "";
    public static String nickname = "";
    public static String openId = "";
    public static String unionId = "";
    public static String ResumeTime = "1";


    private static MyApp app;
    //埋点session 应用关闭 session消失
    public String session;
    Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    public static MyApp getInstance() {
        return app;
    }


//    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        //默认加载刷新
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
//            layout.setPrimaryColorsId(R.color.white, R.color.black);//全局设置主题颜色
//            return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
//        });
//        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
//            layout.setPrimaryColorsId(R.color.white, R.color.black);//全局设置主题颜色
//            return new ClassicsFooter(context);
//        });
////        RefreshLayout
//    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setReboundDuration(10);
//                layout.setPrimaryColorsId(R.color.red_F7553B, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            layout.setReboundDuration(10);
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context);
//                return new BallPulseFooter(context);
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        new X5App().initX5app(getApplicationContext());
        app = this;
        ArouterUtil.init(this);
        registToWX();
//        initUmeng();
        GlobalAppComponent.init(this);
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        SpeechUtility.createUtility(app, SpeechConstant.APPID + "=5b03e854"); //初始化
//        initJPush();
        //Umeng统计
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

//    private void initUmeng() {
//        String channelName = PackageUtil.getChannelName(this);
//        UmenStatisticsUtil.preInit(this, Constants.UMENG_APPKEY, channelName);
////        UMConfigure.setLogEnabled(AppConfig.IS_DEBUG);
//        if (!SharedPreferencesUtil.get(this, "isFristAgen", "1").equals("1")) {
//            UmenStatisticsUtil.init(this, Constants.UMENG_APPKEY);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    LogUtil.e(TAG,"push");
//                    PushHelper.init(getApplicationContext());
//                }
//            }).start();
//        }
//    }

    public static IWXAPI mWxApi;

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WANSE_WEIXIN_APP_ID);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.WANSE_WEIXIN_APP_ID);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                mWxApi.registerApp(Constants.WANSE_WEIXIN_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));


    }


//    @Override
//    protected void attachBaseContext(Context context) {
//        super.attachBaseContext(context);
//        MultiDex.install(this);
//    }

    /**
     *  
     *
     * @dec : 初始化推送消息
     *  @author ListKer-Hlg
     *  @time   2018/6/11 0011 : 17:35 
     *  @return  none
     */
    private void initJPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

}
