package com.jiarui.base.widgets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.jiarui.base.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.bluemobi.dylan.photoview.library.PhotoView;


/**
 * 异步保存图片
 *
 * @author Administrator
 */
public class SaveImageTask extends AsyncTask<Bitmap, Void, String>

{
    private Activity context;
    private PhotoView photoView;

    public SaveImageTask(Activity context, PhotoView photoView) {
        this.context = context;
        this.photoView = photoView;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        String result = context.getResources().getString(R.string.save_picture_failed);
        try {
            String sdcard = Environment.getExternalStorageDirectory().toString();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(sdcard + "/imgsbaocun");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(), new Date().getTime() + ".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = context.getResources().getString(R.string.save_picture_success, file.getAbsolutePath());
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)

    {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        photoView.setDrawingCacheEnabled(false);
    }
}