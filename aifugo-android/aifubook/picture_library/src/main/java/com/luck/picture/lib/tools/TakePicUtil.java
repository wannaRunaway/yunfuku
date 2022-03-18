//package com.luck.picture.lib.tools;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.net.Uri;
//import android.os.Environment;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//
//import com.jiarui.base.R;
//import com.jiarui.base.utils.ScreenUtil;
//import com.jph.takephoto.app.TakePhoto;
//import com.jph.takephoto.app.TakePhotoImpl;
//import com.jph.takephoto.compress.CompressConfig;
//import com.jph.takephoto.model.TakePhotoOptions;
//import com.jph.takephoto.permission.InvokeListener;
//import com.jph.takephoto.permission.TakePhotoInvocationHandler;
//
//import java.io.File;
//
///*
// * 拍照 ，相册
// * 在调用的activity配置android:configChanges="mcc|mnc|keyboard|keyboardHidden|navigation|orientation|screenSize|fontScale"
// */
//@SuppressWarnings("deprecation")
//public class TakePicUtil implements OnClickListener {
//
//	private static final String TAG = "TakePicUtil";
//	private Activity mActivity;
//	private InvokeListener mInvokeListener;
//	private TakePhoto.TakeResultListener mTakeResultListener;
//	public TakePicUtil(Activity activity, InvokeListener l, TakePhoto.TakeResultListener takeResultListener) {
//		this.mActivity = activity;
//		this.mInvokeListener = l;
//		this.mTakeResultListener = takeResultListener;
//	}
//
//
//	/**
//	 * 选择提示对话框
//	 */
//	private AlertDialog dialog;
//	public void ShowPickDialog() {
//		dialog = new AlertDialog.Builder(mActivity).create();
//		dialog.show();
//		Window window = dialog.getWindow();
//		window.setGravity(Gravity.CENTER);
//		int width = ScreenUtil.getWidth() * 6 / 7;
//		window.setLayout(width,
//				android.view.WindowManager.LayoutParams.WRAP_CONTENT);
//		View view = mActivity.getLayoutInflater().inflate(
//				R.layout.dialog_choose_pics, null);
//		window.setContentView(view);//
//		view.findViewById(R.id.take_pic_text).setOnClickListener(this);
//		view.findViewById(R.id.choose_pic_text).setOnClickListener(this);
//		view.findViewById(R.id.cancle).setOnClickListener(this);
//	}
//	private void choosePics() {
//		TakePhoto tf = getTakePhoto();
//		tf.onPickFromGallery();
//	}
//
//	private void takePhotos() {
//		TakePhoto tf = getTakePhoto();
//		Uri outPutUri = Uri.fromFile(getLocalImgPath(mActivity));
//		tf.onPickFromCapture(outPutUri);
//	}
//
//
//	/**
//	 *  获取TakePhoto实例
//	 * @return
//	 */
//	private TakePhoto takePhoto;
//	public TakePhoto getTakePhoto(){
//		if (takePhoto==null){
//			takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(mInvokeListener).bind(new TakePhotoImpl(mActivity,mTakeResultListener));
//		}
//		CompressConfig config = new CompressConfig.Builder()
//				.setMaxSize(300*1024)
//				.setMaxPixel(700)
//				.enablePixelCompress(true)
//				.enableQualityCompress(true).create();
//		takePhoto.onEnableCompress(config,false);
//		TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
//		builder.setCorrectImage(true);
//		builder.setCorrectImage(true);
//		takePhoto.setTakePhotoOptions(builder.create());
//		return takePhoto;
//	}
//
//	/*
//	 * 取得本地图片存储路径
//	 * Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//	 * || !Environment.isExternalStorageRemovable()
//	 */
//	public static File getLocalImgPath(Activity activity) {
//		/*String cachePath = null;
//		if (SDCardUtil.hasSdcard()) {
//			cachePath = activity.getExternalCacheDir().getPath();
//		} else {
//			cachePath = activity.getCacheDir().getPath();
//		}
//		return cachePath + File.separator +"xmqd"+File.separator+ System.currentTimeMillis() + ".jpg";*/
//		File file=new File(Environment.getExternalStorageDirectory(), File.separator+"xmqd"+File.separator+System.currentTimeMillis() + ".jpg");
//		if (!file.getParentFile().exists())file.getParentFile().mkdirs();
//		return file;
//	}
//
//	@Override
//	public void onClick(View v) {
//		if(null!=dialog)
//			dialog.dismiss();
//
//		if(v.getId() == R.id.take_pic_text){
//			takePhotos();
//		}else if(v.getId() == R.id.choose_pic_text){
//			choosePics();
//		}
//	}
//}
