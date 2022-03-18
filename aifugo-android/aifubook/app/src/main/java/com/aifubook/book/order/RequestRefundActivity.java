package com.aifubook.book.order;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.order.GlideEngine;
import com.aifubook.book.mine.order.OrderPresenter;
import com.aifubook.book.mine.order.OrderView;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.alibaba.fastjson.JSONObject;
import com.aifubook.book.R;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.aifubook.book.api.ApiService.IMAGE;

/*
 * 3个入口
 * 1、我的订单列表、团购订单列表
 * 2、我的订单详情
 * */
public class RequestRefundActivity extends BaseActivity<OrderPresenter> implements OrderView {

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
    @BindView(R.id.rv_refundReason)
    RecyclerView rv_refundReason;
    // new add
    @BindView(R.id.header_textview)
    TextView header_textview;
    @BindView(R.id.imageview_left)
    ImageView imageView_left;
    @BindView(R.id.shop_imageview)
    ImageView shop_imageview;
    @BindView(R.id.price_single_textview)
    TextView price_single_textview;
    @BindView(R.id.book_count)
    TextView book_count;
    @BindView(R.id.book_name)
    TextView book_name;
    @BindView(R.id.ed_money)
    EditText ed_money;
    @BindView(R.id.money_change_textview)
    TextView money_change_textview;
    @BindView(R.id.ed_replenish)
    EditText ed_replenish;
    @BindView(R.id.upload_imageview)
    ImageView upload_imageview;
    @BindView(R.id.recyclerview_imageview)
    RecyclerView recyclerview_imageview;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_islast)
    TextView tv_islast;
    //    BaseRecyclerAdapter<ItemsBean> productAdapter;
    //    private OrderListBean.ListBean orderlistbean;
    private ItemsBean itemsBean;
    private String phone;
    private ImageViewAdapter imageViewAdapter;
    private List<String> urllist = new ArrayList<>();
    private boolean islast;

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
        itemsBean = (ItemsBean) getIntent().getSerializableExtra("data");
        phone = getIntent().getStringExtra(IntentKey.Companion.getPHONE());
        islast = getIntent().getBooleanExtra(IntentKey.Companion.getISLAST(), false);
        tv_phone.setText("商家联系电话   "+phone);
        Glide.with(this).load(IMAGE + itemsBean.getProductImage()).into(shop_imageview);
        book_name.setText(itemsBean.getProductName() + "");
        price_single_textview.setText("¥" + (itemsBean.getProductPrice()) / ApiService.onehundred);
        book_count.setText("x" + itemsBean.getCount());
        money_change_textview.setOnClickListener(v -> {
            ed_money.requestFocus();
        });
        imageViewAdapter = new ImageViewAdapter(this, urllist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview_imageview.setLayoutManager(linearLayoutManager);
        recyclerview_imageview.setAdapter(imageViewAdapter);
        Map map = new HashMap();
        mPresenter.getRefundReasons(map);
        var maxrefundMap = new HashMap<String,String>();
        maxrefundMap.put("orderItemId", itemsBean.getId()+"");
        mPresenter.getmaxrefundfee(maxrefundMap);
    }

    private int refund = 0;//0 退款 1.退货退款

    @OnClick({R.id.tv_refund, R.id.tv_refunds})
    void tv_refund(View view) {
        switch (view.getId()) {
            case R.id.tv_refund://退款
                refund = 0;
                tv_refund.setTextColor(getResources().getColor(R.color.red_F7553B));
                tv_refunds.setTextColor(getResources().getColor(R.color.black_999999));
                tv_refund.setBackgroundResource(R.drawable.red_4corner_stroke);
                tv_refunds.setBackgroundResource(R.drawable.radis4_full_eeeeee);
                break;
            case R.id.tv_refunds://退货退款
                String status = itemsBean.getStatus();
                if ("10".equals(status)) {
                    ToastUtil.showToast("现在还未发货,只能提交退款申请", true);
                    return;
                }
                refund = 1;
//                tl_courier.setVisibility(View.VISIBLE);
                tv_refund.setTextColor(getResources().getColor(R.color.black_999999));
                tv_refunds.setTextColor(getResources().getColor(R.color.red_F7553B));
                tv_refund.setBackgroundResource(R.drawable.radis4_full_eeeeee);
                tv_refunds.setBackgroundResource(R.drawable.red_4corner_stroke);
                break;
        }
    }

    List<Integer> ids = new ArrayList<>();
    private BaseRecyclerAdapter<RefundReasonsBean> itemAdapter;
    List<RefundReasonsBean> itemList = new ArrayList<>();

    private void initItem(List<RefundReasonsBean> itemList) {
        itemAdapter = new BaseRecyclerAdapter<>(mContext, itemList, R.layout.layout_apply_after_item) {
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
                    llSpec.setBackgroundResource(R.drawable.stroke_1dp_12radius);
                }
                holder.getView(R.id.tv_spec_name).setOnClickListener(v -> {
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
                });
            }
        };
        rv_refundReason.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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

    /**
     * 上传教师资格证
     */
    private List<String> list = new ArrayList<>();

    @OnClick(R.id.upload_imageview)
    void imageUpload() {
//        verifyStoragePermissions();
        list.clear();
        uploadlist.clear();
        imageViewAdapter.notifyDataSetChanged();
        EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.aifubook.book.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(6)//参数说明：最大可选数，默认1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        list.clear();
                        for (Photo photo : photos) {
                            LogUtil.d(photo.path.toString() + "每个图片");
                            list.add(photo.path);
                        }
                        if (list.size() == 0) {
                            return;
                        }
                        startProgressDialog("正在上传...");
                        upload(list);
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.d("onCancel()........");
                    }
                });
    }


    private void upload(List<String> fileList) {
        Map<String, String> map = new HashMap<>();
        List<File> showImgFile = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            showImgFile.add(new File(fileList.get(i)));
        }
        if (showImgFile.size() == 0) {
            return;
        }
        mPresenter.uploadImage(map, showImgFile);
    }

    /**
     * 提交申请
     */
    @OnClick(R.id.tv_submit)
    void tv_submit() {
        if (ids.size() == 0) {
            ToastUitl.showShort(RequestRefundActivity.this, "请选择退款原因");
            return;
        }
        if (StringUtils.isEmpty(ed_replenish.getText().toString())) {
            ToastUitl.showShort(RequestRefundActivity.this, "补充说明不能为空");
            return;
        }
        if (StringUtils.isEmpty(ed_money.getText().toString())) {
            ToastUitl.showShort(RequestRefundActivity.this, "退款金额不能为空");
            return;
        }
        double money = Double.parseDouble(ed_money.getText().toString())*ApiService.onehundred;
        mPresenter.refund(getRequestBodys(money));
    }

    class ImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private RequestRefundActivity requestRefundActivity;
        private List<String> list;

        public ImageViewAdapter(RequestRefundActivity afterDetailsActivity, List<String> list) {
            this.requestRefundActivity = afterDetailsActivity;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = requestRefundActivity.mInflater.inflate(R.layout.recyclerview_imageview, parent, false);
            ImageViewHolder imageViewHolder = new ImageViewHolder(view);
            return imageViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String url = list.get(position);
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Glide.with(requestRefundActivity).load(IMAGE + url).into(imageViewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
            }
        }
    }

    /**
     * orderId (integer, optional): 订单id ,
     * orderItemId (integer, optional): item订单id ,
     * refundFee (integer, optional): 退款金额 单位分 ,
     * refundImage (Array[string], optional): 退款图片 ,
     * refundReasons (Array[integer], optional): 退款原因集合 ,
     * refundRemark (string, optional): 补充说明 ,
     * refundType (integer, optional): 退款类型 0仅退款, 1退款退货
     * @param money
     */
    public RequestBody getRequestBodys(double money) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderItemId", itemsBean.getId() + "");//订单id
        jsonObject.put("refundReasons", ids);
        jsonObject.put("refundRemark", ed_replenish.getText().toString() + "");
        jsonObject.put("refundType", refund + "");
        jsonObject.put("refundImage", list);
        jsonObject.put("refundFee", money);
        if (refund == 1) {
//            jsonObject.put("refundExpressNo", et_courierNumber.getText() + "");
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return requestBody;
    }

    private List<String> uploadlist = new ArrayList<>();
    @Override
    public void UploadImageSuc(String DataBean) {
        stopProgressDialog();
        list.remove(0);
        upload(list);
        urllist.add(DataBean);
        uploadlist.add(DataBean);
        imageViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetRefundReasonsSuc(List<RefundReasonsBean> DataBean) {
        itemList.clear();
        itemList.addAll(DataBean);
        initItem(itemList);
    }

    @Override
    public void RefundSuc(String DataBean) {
        ToastUitl.showShort(MyApp.getInstance(), "申请售后成功，请等待处理");
        finish();
//        stopProgressDialog();
//        ShowReportDialog showReportDialog = new ShowReportDialog();
//        showReportDialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
//            @Override
//            public void onConfirm() {
//                ApplyAfterSalesActivity.this.finish();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//        showReportDialog.showRefundDialog(this);
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
        if (money !=null) {
            ed_money.setText(Integer.parseInt(money) / ApiService.onehundred + "");
            if (islast){
                double moneylogistic = Double.parseDouble(money)-itemsBean.getFee();
                if (moneylogistic>0) {
                    tv_islast.setText("（包含发货运费¥" + moneylogistic/ApiService.onehundred+"）");
                }
            }
        }
    }

    @Override
    public void getmaxrefundfeeError(String message) {
        LogUtil.d("加载失败");
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}