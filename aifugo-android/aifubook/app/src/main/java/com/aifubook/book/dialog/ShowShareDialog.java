package com.aifubook.book.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.utils.ViewToBitmapUtil;
import com.jiarui.base.fresco.CommonImage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ShowShareDialog {


    private ChooseClickListener clickListener;
    private Activity activity;


    public interface ChooseClickListener {
        void weChatClick(View view);

        void saveClick();
    }

    public void setOnClickListener(ChooseClickListener listener) {
        this.clickListener = listener;
    }


    public Dialog shareDialog(final Activity activity, String head, String bookImg, String name, String bookName, String price, String discount, Bitmap bitmap) {
        this.activity=activity;
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, true, true);
        ConstraintLayout shareView = view.findViewById(R.id.shareView);
        CommonImage iv_header = view.findViewById(R.id.iv_header);
        CommonImage iv_book = view.findViewById(R.id.iv_book);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_book_name = view.findViewById(R.id.tv_book_name);
        TextView tv_book_price = view.findViewById(R.id.tv_book_price);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        ImageView showSharCode = view.findViewById(R.id.showSharCode);

        TextView showSharWechar = view.findViewById(R.id.showSharWechar);
        TextView showSharSave = view.findViewById(R.id.showSharSave);
        TextView showSharCancel = view.findViewById(R.id.showSharCancel);
        iv_header.setImageURI(head);
        iv_book.setImageURI(bookImg);
        tv_name.setText(name);
        tv_book_name.setText(bookName);
        tv_book_price.setText(discount);
        tv_delete.setText(price);
        tv_delete.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        showSharCode.setImageBitmap(bitmap);


        showSharWechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.weChatClick(shareView);
            }
        });

        showSharSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // clickListener.saveClick();
                viewSaveToImage(shareView);
            }
        });


        showSharCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    private void viewSaveToImage(View view) {

        // 把一个View转换成图片
        // Bitmap cachebmp = loadBitmapFromView(view);

        ViewToBitmapUtil viewToBitmapUtil = new ViewToBitmapUtil();
        viewToBitmapUtil.setCacheResultListener(new ViewToBitmapUtil.CacheResult() {
            @Override
            public void result(Bitmap bitmap) {

                FileOutputStream fos;
                String imagePath = "";
                try {
                    // 判断手机设备是否有SD卡
                    boolean isHasSDCard = Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
                    if (isHasSDCard) {
                        // SD卡根目录
                        File sdRoot = Environment.getExternalStorageDirectory();
                        File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
                        fos = new FileOutputStream(file);
                        imagePath = file.getAbsolutePath();
                    } else
                        throw new Exception("创建文件失败!");

                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("asdasd", "imagePath=" + imagePath);

                view.destroyDrawingCache();
                Toast.makeText(activity, "保存成功!", Toast.LENGTH_SHORT).show();
                // 发送广播，通知刷新图库的显示
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));

            }
        });
        viewToBitmapUtil.getBitmapFromView(activity, view);


    }


}
