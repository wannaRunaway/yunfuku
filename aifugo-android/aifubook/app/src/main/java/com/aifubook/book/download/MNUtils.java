package com.aifubook.book.download;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class MNUtils {

    public MNUtils() {
    }

    public static String getCachePath(Context context) {
        String cachePath;
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            cachePath = context.getCacheDir().getPath();
        } else {
            cachePath = context.getExternalCacheDir().getPath();
        }

        return cachePath;
    }

    public static void changeApkFileMode(File file) {
        try {
            String cmd1 = "chmod 777 " + file.getParent();
            Runtime.getRuntime().exec(cmd1);
            String cmd = "chmod 777 " + file.getAbsolutePath();
            Runtime.getRuntime().exec(cmd);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
