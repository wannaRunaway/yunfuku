package com.journeyapps.barcodescanner.photoscanutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.jiarui.base.utils.LogUtil;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

public class DecodeImgThread extends Thread {


    private static final String TAG = "DecodeImgThread";
    /*图片路径*/
    private String imgPath;
    /*回调*/
    private DecodeImgCallback callback;
    private Bitmap scanBitmap;


    public DecodeImgThread(String imgPath, DecodeImgCallback callback) {

        this.imgPath = imgPath;
        this.callback = callback;
    }

    @Override
    public void run() {
        super.run();

     /*   if (TextUtils.isEmpty(imgPath) || callback == null) {
            return;
        }

//        Bitmap scanBitmap = getBitmap(imgPath, 400, 400);
        Bitmap scanBitmap = getBitmap(imgPath, 400, 400);

        MultiFormatReader multiFormatReader = new MultiFormatReader();
        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();


        // 扫描的类型  一维码和二维码
//        decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // 设置解析的字符编码格式为UTF8
        //  hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        // 设置解析配置参数
        multiFormatReader.setHints(hints);
        // 开始对图像资源解码
        Result rawResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(scanBitmap))));

            LogUtil.e(TAG,"解析结果"+ rawResult.getText());

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG,e.getMessage().toString());
            //  Log.i("解析的图片结果","失败");
        }

        if (rawResult != null) {
            callback.onImageDecodeSuccess(rawResult);
        } else {
            callback.onImageDecodeFailed();
        }*/

        String rawResult = getTwoBarcode(imgPath);
        LogUtil.e(TAG,"rewResult="+rawResult);
        if(rawResult!=null){
            callback.showResult(rawResult);
        }else{
            callback.onImageDecodeFailed();
        }

    }

    public static String getTwoBarcode(String filename) {

        String twobarcode = "";
        Bitmap img = getBitmap(filename, 400, 400);
        ;
        int[] intArray = new int[img.getWidth() * img.getHeight()];
        img.getPixels(intArray, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        LuminanceSource source = new RGBLuminanceSource(img.getWidth(), img.getHeight(), intArray);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeMultiReader odecoder = new QRCodeMultiReader();
        Result[] results;
        String resP = "";
        try {
            results = odecoder.decodeMultiple(bitmap);
            if (results != null) {
                for (Result result2 : results) {
                    if (result2 != null
                            && codeType(DecodeFormatManager.QR_CODE_FORMATS,
                            result2.getBarcodeFormat())) {
                        resP = "|" + result2.getResultPoints()[1].getX() + "|" + result2.getResultPoints()[1].getY() + "|";
                        twobarcode += result2.getText() + resP;


                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return twobarcode;

    }

    private static boolean codeType(final Collection<BarcodeFormat> type, BarcodeFormat barcodeFormat) {
        for (BarcodeFormat barcode : type) {
            if (barcodeFormat.equals(barcode)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 根据路径获取图片
     *
     * @param filePath  文件路径
     * @param maxWidth  图片最大宽度
     * @param maxHeight 图片最大高度
     * @return bitmap
     */
    private static Bitmap getBitmap(final String filePath, final int maxWidth,
                                    final int maxHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * Return the sample size.
     *
     * @param options   The options.
     * @param maxWidth  The maximum width.
     * @param maxHeight The maximum height.
     * @return the sample size
     */
    private static int calculateInSampleSize(final BitmapFactory.Options options,
                                             final int maxWidth,
                                             final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

}
