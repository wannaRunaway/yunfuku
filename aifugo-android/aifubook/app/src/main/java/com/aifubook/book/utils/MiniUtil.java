package com.aifubook.book.utils;

import android.graphics.Bitmap;

import com.aifubook.book.Constants;
import com.aifubook.book.application.MyApp;
import com.jiarui.base.utils.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;

public class MiniUtil {


    public static void shareMiniToWx(Bitmap bitmap, String productId) {
        if (!MyApp.mWxApi.isWXAppInstalled()) {
            return;
        }
        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = "http://www.qq.com";//自定义
        miniProgram.userName = Constants.min_origin_id;//小程序端提供参数
        miniProgram.path = "/pages-home/home/groupDetails?id=" + productId;//小程序端提供参数
        LogUtil.e("TAG","path="+miniProgram.path);
        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
        mediaMessage.title = "一起来参加拼团，抢最低价！";//自定义
        mediaMessage.description = "this is miniProgram's description";//自定义
        miniProgram.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
//        miniProgram.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;//正式
//        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher);
//        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
       /* if (BitmapUtil.isOverSize(bitmap, 128)) {//判断bitmap size是否大于128  大于在进行压缩
            Bitmap ysBitmap = BitmapUtil.zoomImage(bitmap, 300.0, 240.0);
            mediaMessage.thumbData = BitmapUtil.createBitmapThumbnail(ysBitmap, 128);   // 小程序消息封面图片，小于128k
        } else {
            mediaMessage.thumbData = BitmapUtil.createBitmapThumbnail(bitmap, 128);  // 小程序消息封面图片，小于128k
        }*/
        mediaMessage.thumbData = BitmapUtil.createBitmapThumbnail(bitmap, 200);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "111";
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        MyApp.mWxApi.sendReq(req);
    }

}
