package com.aifubook.book.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by ListKer_Hlg on 2020/3/20 23:25
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class PlayVoices {

    private static MediaPlayer mediaPlayer;

    /**
     * 打开assets下的音乐mp3文件
     */
    public static void openVoices(Context context, String Paths) {
        try {
            //播放 assets/a2.mp3 音乐文件
            AssetFileDescriptor fd = context.getAssets().openFd(Paths + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.e("XXXXXXXXXXX", "KKKKKKKKKKKKKKKKKKK");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("XXXXXXXXXXX", "FFFFFFFFFFFFFFFFFFF");
        }
    }

//    /**
//     * 打开assets下的音乐mp3文件
//     */
//    public static void openWav(Context context, String Paths) {
//        try {
//            FileInputStream fis=new FileInputStream(file);
//            buffer=new byte[1024*1024*2];//2M
//            int len=fis.read(buffer);
//            Log.i(tag, "fis len="+len);
//            Log.i(tag, "0:"+(char)buffer[0]);
//            pcmlen=0;
//            pcmlen+=buffer[0x2b];
//            pcmlen=pcmlen*256+buffer[0x2a];
//            pcmlen=pcmlen*256+buffer[0x29];
//            pcmlen=pcmlen*256+buffer[0x28];
//
//            int channel=buffer[0x17];
//            channel=channel*256+buffer[0x16];
//
//            int bits=buffer[0x23];
//            bits=bits*256+buffer[0x22];
//            Log.i(tag, "pcmlen="+pcmlen+",channel="+channel+",bits="+bits);
//            at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,   
//                                    channel,
//                                    AudioFormat.ENCODING_PCM_16BIT,   
//                                    pcmlen,   
//                                 AudioTrack.MODE_STATIC);  
//            at.write(buffer, 0x2C, pcmlen);
//            Log.i(tag, "write 1...");
//            at.play();
//            Log.i(tag, "play 1...");
//
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /**
     * 打开assets下的音乐mp3文件
     */
    public static void closeVoice() {
        Log.e("closeVoVoicecloseVoice","WWWWWWWWW");
        if (mediaPlayer != null) {
            Log.e("closeVoVoicecloseVoice","WWWWWWWWW");
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
    }


}
