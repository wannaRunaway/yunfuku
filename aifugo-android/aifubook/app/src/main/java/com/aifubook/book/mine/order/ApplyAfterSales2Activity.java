package com.aifubook.book.mine.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.aifubook.book.order.MyOrderDetailsActivity;
import com.aifubook.book.view.CustomDividerItemDecoration;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.aifubook.book.api.ApiService.IMAGE;

public class ApplyAfterSales2Activity extends BaseActivity<OrderPresenter> implements OrderView {

    @BindView(R.id.rv_product)
    RecyclerView rv_product;
//    @BindView(R.id.tv_goods_total)
//    TextView tv_goods_total;
//    @BindView(R.id.tv_total_price)
//    TextView tv_total_price;
    @BindView(R.id.tv_refund)
    TextView tv_refund;
    @BindView(R.id.tv_refunds)
    TextView tv_refunds;
//    @BindView(R.id.tl_courier)
//    RelativeLayout tl_courier;
//    @BindView(R.id.tv_refundPrice)
//    TextView tv_refundPrice;
//    @BindView(R.id.et_courierNumber)
//    EditText et_courierNumber;
//    @BindView(R.id.et_remake)
//    EditText et_remake;
//    @BindView(R.id.iv_prove)
//    ImageView image;
    @BindView(R.id.rv_refundReason)
    RecyclerView rv_refundReason;
    // new add
    @BindView(R.id.header_textview)
    TextView header_textview;
    @BindView(R.id.imageview_left)
    ImageView imageView_left;

    BaseRecyclerAdapter<ItemsBean> productAdapter;
    private OrderDetailsBean item;

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_refund;
    }

    @Override
    public void initPresenter() {
        mPresenter = new OrderPresenter(this);
    }

    @Override
    public void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        setTitle("申请售后");
        header_textview.setText("申请售后");
        imageView_left.setOnClickListener(v -> finish());
        item = (OrderDetailsBean) getIntent().getSerializableExtra("data");
        int totalCount = 0;
        for (int i = 0; i < item.getItems().size(); i++) {
            ItemsBean itemsBean = item.getItems().get(i);
            totalCount = totalCount + Integer.parseInt(itemsBean.getCount());
        }
//        tv_goods_total.setText("共" + totalCount + "件商品");
//        tv_total_price.setText("¥" + Double.parseDouble(item.getTotalFee()) / 100);
//        tv_refundPrice.setHint("最大为¥" + item.getTotalFee());
//        tv_refundPrice.setText("¥" + Double.parseDouble(item.getTotalFee()) / 100);
        initProductList(item.getItems());

        CustomDividerItemDecoration itemDecorationV = new CustomDividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecorationV.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_divider_white_10));
        CustomDividerItemDecoration itemDecorationH = new CustomDividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        itemDecorationH.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_divider_white_horiz_10));
        rv_refundReason.addItemDecoration(itemDecorationV);
        rv_refundReason.addItemDecoration(itemDecorationH);

        Map map = new HashMap();
        mPresenter.getRefundReasons(map);
    }

    private void initProductList(List<ItemsBean> productList) {
        productAdapter = new BaseRecyclerAdapter<ItemsBean>(this, productList, R.layout.layout_order_list_product_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ItemsBean item, int position, boolean isScrolling) {
                Glide.with(ApplyAfterSales2Activity.this)
                        .load(IMAGE + item.getProductImage())
                        .into((ImageView) holder.getView(R.id.iv_product));

                holder.setText(R.id.tv_product_name, item.getProductName());
                holder.setText(R.id.tv_price, "¥" + (item.getProductPrice()) / ApiService.onehundred);
                holder.setText(R.id.tv_product_number, "x" + item.getCount());
                holder.setText(R.id.tv_sp, item.getProductSubName());

                holder.getView(R.id.ll_details).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getOrderId() + "");
                        startActivity(MyOrderDetailsActivity.class, bundle);
                    }
                });
            }
        };
        rv_product.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_product.setAdapter(productAdapter);
    }

    private int refund = 0;//0 退款 1.退货退款

    @OnClick({R.id.tv_refund, R.id.tv_refunds})
    void tv_refund(View view) {
        switch (view.getId()) {
            case R.id.tv_refund://退款
                refund = 0;
//                tl_courier.setVisibility(View.GONE);
                tv_refund.setTextColor(getResources().getColor(R.color.view_color_FF3333));
                tv_refunds.setTextColor(getResources().getColor(R.color.view_color_363636));
                tv_refund.setBackgroundResource(R.color.view_color_FFF1F1);
                tv_refunds.setBackgroundResource(R.color.eeeeee);
                break;
            case R.id.tv_refunds://退货退款
                String status = item.getStatus();
                if("10".equals(status)){
                    ToastUtil.showToast("现在还未发货,只能提交退款申请",true);
                    return;
                }

                refund = 1;
                if(item.getLogisticsType().equals("1") || item.getLogisticsType().equals("2")){
//                    tl_courier.setVisibility(View.GONE);
                }else{
//                    tl_courier.setVisibility(View.VISIBLE);
                }
                tv_refund.setTextColor(getResources().getColor(R.color.view_color_363636));
                tv_refunds.setTextColor(getResources().getColor(R.color.view_color_FF3333));
                tv_refund.setBackgroundResource(R.color.eeeeee);
                tv_refunds.setBackgroundResource(R.color.view_color_FFF1F1);
                break;
        }
    }

    List<Integer> ids = new ArrayList<>();
    private BaseRecyclerAdapter<RefundReasonsBean> itemAdapter;
    List<RefundReasonsBean> itemList = new ArrayList<>();

    private void initItem(List<RefundReasonsBean> itemList) {
        itemAdapter = new BaseRecyclerAdapter<RefundReasonsBean>(mContext, itemList, R.layout.layout_apply_after_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, RefundReasonsBean item, int position, boolean isScrolling) {
                RelativeLayout llSpec = holder.getView(R.id.ll_spec);
                TextView tv_spec_name = holder.getView(R.id.tv_spec_name);
                holder.setText(R.id.tv_spec_name, "" + item.getDesc());
                if (item.getSelectIndex() == 1) {
                    llSpec.setBackgroundResource(R.drawable.shape_apply_select_spe);
                    tv_spec_name.setTextColor(ContextCompat.getColor(mContext, R.color.view_color_FF3333));
                } else {
                    tv_spec_name.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                    llSpec.setBackgroundResource(R.drawable.shape_apply_spe);
                }
                holder.getView(R.id.tv_spec_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getSelectIndex() == 1) {
                            item.setSelectIndex(0);
                        } else {
                            item.setSelectIndex(1);
                        }
                        notifyDataSetChanged();
                        ids.clear();
                        for (int i = 0; i < itemList.size(); i++) {
                            if (itemList.get(i).getSelectIndex() == 1) {
                                ids.add(itemList.get(i).getType());
                            }
                        }
                    }
                });
            }
        };
        rv_refundReason.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_refundReason.setAdapter(itemAdapter);
    }

    private static final int REQ_PERMISSION_CODE = 0x100;

    //动态权限检查
    public boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    List<String> fileList = new ArrayList<>();
    private String uploadImage = "";

    /**
     * 上传教师资格证
     */
    @OnClick(R.id.upload_imageview)
    void imageUpload() {
        if (!checkPermission(this)) {
            return;
        }
//        // 进入相册 以下是例子：不需要的api可以不写
//            int themeId = R.style.picture_default_style;
        PictureSelector.create(ApplyAfterSales2Activity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    fileList.clear();
                    for (LocalMedia media : selectList) {
                        Log.e("图片-----》", media.getCompressPath());
                        fileList.add(media.getCompressPath());
//                        stringMap.get().put("1-1", media.getCompressPath());
//                        mPresenter.getSendImage(stringMap);
                    }
                    GetData();
                    break;
            }
        }
    }

    private void GetData() {
        Map<String, String> map = new HashMap<>();
        List<File> showImgFile = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            if (!fileList.get(i).equals("无")) {
                showImgFile.add(new File(fileList.get(i)));
            }
        }
        if (showImgFile.size() == 0) {
            return;
        }
        startProgressDialog("正在上传...");
//        mPresenter.uploadImage(map, showImgFile);
    }

    /**
     * 自定义压缩存储地址
     *
     * @return 返回新的地址
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/smart_canteen/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 提交申请
     */
    @OnClick(R.id.tv_submit)
    void tv_submit() {
        if (ids.size() == 0) {
            ToastUitl.showShort(ApplyAfterSales2Activity.this, "请选择退款原因");
            return;
        }
//        if(StringUtils.isEmpty(et_remake.getText().toString())){
//            ToastUitl.showShort(ApplyAfterSales2Activity.this, "补充说明不能为空");
//            return;
//        }
        if (StringUtils.isEmpty(uploadImage)) {
            ToastUitl.showShort(ApplyAfterSales2Activity.this, "请上传图片证明");
            return;
        }
//        if (refund == 1) {
//            if (StringUtils.isEmpty(et_courierNumber.getText().toString())) {
//                ToastUitl.showShort(ApplyAfterSales2Activity.this, "请输入快递单号");
//                return;
//            }
//        }
        startProgressDialog();
        mPresenter.refund(getRequestBodys());
    }

    public RequestBody getRequestBodys() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", item.getId() + "");//订单id
        jsonObject.put("refundReasons", ids);
//        jsonObject.put("refundRemark", et_remake.getText() + "");
        jsonObject.put("refundType", refund + "");
        jsonObject.put("refundImage", uploadImage + "");
        if (refund == 1) {
//            jsonObject.put("refundExpressNo", et_courierNumber.getText() + "");
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return requestBody;
    }


    @Override
    public void OrderListSuc(OrderListBean DataBean) {

    }

    @Override
    public void CreateOrderSuc(CreateOrderBean DataBean) {

    }

    @Override
    public void OrderToPaySuc(ToPayBean DataBean) {

    }

    @Override
    public void OrderRepaySuc(ToPayBean DataBean) {

    }

    @Override
    public void orderToPayWeChat(SendRechargeBean Message) {

    }

    @Override
    public void OrderIsPaySuccessSuc(Boolean DataBean) {

    }

    @Override
    public void OrderDetailSuc(OrderDetailsBean DataBean) {

    }

    @Override
    public void OrderCompletedSuc(String DataBean) {

    }

    @Override
    public void OrderSetCancleSuc(String DataBean) {

    }

    @Override
    public void OrderDeleteSuc(String DataBean) {

    }

    @Override
    public void UploadImageSuc(String DataBean) {
        stopProgressDialog();
        LogUtil.e("TAG","data="+DataBean);
        uploadImage = DataBean;
//        Glide.with(ApplyAfterSales2Activity.this)
//                .load(IMAGE + DataBean)
//                .into(image);
    }

    @Override
    public void GetRefundReasonsSuc(List<RefundReasonsBean> DataBean) {
        itemList.clear();
        itemList.addAll(DataBean);
        initItem(itemList);
    }

    @Override
    public void RefundSuc(String DataBean) {
//        Toast.makeText(mContext, "申请已提交", Toast.LENGTH_SHORT).show();
//        finish();
        stopProgressDialog();
        ShowReportDialog showReportDialog = new ShowReportDialog();
        showReportDialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                ApplyAfterSales2Activity.this.finish();
            }

            @Override
            public void onCancel() {

            }
        });
        showReportDialog.showRefundDialog(this);
    }

    @Override
    public void SetFetchedSuc(String DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CardAddSuc(String DataBean) {

    }

    @Override
    public void CardAddFail(String message) {

    }

    @Override
    public void uploadRefundNoResult(String result) {

    }

    @Override
    public void uploadRefundNoError(String error) {

    }

    @Override
    public void shunfenMessage(LogisticsAllBean logisticsAllBean) {

    }

    @Override
    public void dadamessage(List<DadaResultBean> list) {

    }

    @Override
    public void servicedetails(ServiceDetailsBean serviceDetailsBean) {

    }

    @Override
    public void cancelrefund(String s) {

    }

    @Override
    public void companylist(List<CompantItem> list) {

    }

    @Override
    public void getmaxrefundfee(String money) {

    }

    @Override
    public void getmaxrefundfeeError(String message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}