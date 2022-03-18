package com.aifubook.book.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.databinding.RecyclerviewStatusServiceBinding;
import com.aifubook.book.databinding.RecyclerviewStatusServiceWithimageBinding;
import com.aifubook.book.mine.order.OrderPresenter;
import com.aifubook.book.mine.order.OrderView;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.OrderRefundRecordVOsDTO;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.aifubook.book.mine.order.bean.afterdetails.ShopDTO;
import com.bumptech.glide.Glide;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

public class RefundDetailsActivity extends BaseActivity<OrderPresenter> implements OrderView {

    private static final String TAG = "AfterDetailsActivity";
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_refundPrice)
    TextView tv_refundPrice;
    @BindView(R.id.tv_refundWilling)
    TextView tv_refundWilling;
    @BindView(R.id.tv_orderNo)
    TextView tv_orderNo;
    @BindView(R.id.tv_applyTime)
    TextView tv_applyTime;
    //new Add
    @BindView(R.id.shop_name)
    TextView shop_name;
    @BindView(R.id.shop_imageview)
    ImageView shop_imageview;
    @BindView(R.id.price_single_textview)
    TextView price_single_textview;
    @BindView(R.id.book_count)
    TextView book_count;
    @BindView(R.id.book_name)
    TextView book_name;
    @BindView(R.id.imageview_left)
    ImageView imageview_left;
    @BindView(R.id.header_textview)
    TextView header_textview;
    @BindView(R.id.request_write_textview)
    TextView request_write_textview;
    @BindView(R.id.request_cancel_textview)
    TextView request_cancel_textview;
    @BindView(R.id.request_changed_textview)
    TextView request_changed_textview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.header_imageview)
    ImageView header_imageview;
    @BindView(R.id.header_warning_textview)
    TextView header_warning_textview;
    @BindView(R.id.re_paytype)
    RelativeLayout re_paytype;
    @BindView(R.id.money_textview)
    TextView money_textview;
    @BindView(R.id.backtype_textview)
    TextView backtype_textview;
    @BindView(R.id.re_bottom)
    RelativeLayout re_bottom;
    private String orderId;
    private ServiceAdapter serviceAdapter;
    List<ItemsBean> productList = new ArrayList<>();
    private ItemsBean item;
    private String phone;
    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_details;
    }

    @Override
    public void initPresenter() {
        mPresenter = new OrderPresenter(this);
    }

    @Override
    public void initView() {
//        setTitle("售后详情");
        orderId = getIntent().getStringExtra("id");
        initData();
        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.imageview_left:
                    finish();
                    break;
                case R.id.request_cancel_textview: //撤销申请
                    Map map = new HashMap();
                    map.put("orderItemId", orderId);
                    mPresenter.cancelfrefund(map);
                    break;
                case R.id.request_write_textview: //填写单号
                    Map map1 = new HashMap();
                    mPresenter.companylist(map1);
                    break;
                case R.id.request_changed_textview: //修改申请
                    if (item==null){
                        ToastUitl.showShort(MyApp.getInstance(), "请等待网络加载完成");
                        return;
                    }
                    finish();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", item);
                    bundle.putString(IntentKey.Companion.getPHONE(), phone);
                    startActivity(RequestRefundActivity.class, bundle);
                    break;
                default:
                    break;
            }
        };
        imageview_left.setOnClickListener(onClickListener);
        request_cancel_textview.setOnClickListener(onClickListener);
        request_changed_textview.setOnClickListener(onClickListener);
        request_write_textview.setOnClickListener(onClickListener);
        header_textview.setText("售后详情");
    }

    private void initData() {
        Map map = new HashMap();
        map.put("orderItemId", orderId);
        mPresenter.serviceDetail(map);
    }

    private void initrecyclerview(List<OrderRefundRecordVOsDTO> list, ShopDTO shopDTO, ItemsBean item) {
        serviceAdapter = new ServiceAdapter(this, list, shopDTO, item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public void OrderDetailSuc(OrderDetailsBean DataBean) {
        productList.clear();
        productList.addAll(DataBean.getItems());

        String status = DataBean.getRefundStatus();
        if ("1".equals(status)) {
            tv_status.setText("退货申请中");
        } else if ("2".equals(status)) {
            tv_status.setText("退款成功");
        } else if ("3".equals(status)) {
            tv_status.setText("平台审核未通过，退款失败");
        } else if ("4".equals(status)) {
            tv_status.setText("待退货");
        } else if ("5".equals(status)) {
            tv_status.setText("退货中");
        }
        int totalCount = 0;
        for (int i = 0; i < DataBean.getItems().size(); i++) {
            ItemsBean itemsBean = DataBean.getItems().get(i);
            totalCount = totalCount + Integer.parseInt(itemsBean.getCount());
        }
    }

    @Override
    public void servicedetails(ServiceDetailsBean serviceDetailsBean) {
        //refundStatus (integer, optional): 退款状态 null说明未发生退款,1-申请售后,2-退款成功,
        // 3-退款拒绝,4-待退货,5-退货中,6-退款关闭,7-到账成功,8-售后完成 ,
        //null说明未发生退款  2,7退款成功，6,8退款关闭，其他退款中，
        /**
         * headerview
         * */
        item = serviceDetailsBean.getOrderItem();
        phone = serviceDetailsBean.getShop().getPhone();
        switch (item.getRefundStatus()) {
            case 2:
            case 7:
                header_imageview.setImageResource(R.mipmap.service_success_image);
                tv_status.setText("退款成功 ¥" + serviceDetailsBean.getRefundFee() / ApiService.onehundred);
                header_warning_textview.setVisibility(View.VISIBLE);
                re_paytype.setVisibility(View.VISIBLE);
                request_write_textview.setVisibility(View.GONE);
                request_changed_textview.setVisibility(View.GONE);
                request_cancel_textview.setVisibility(View.GONE);
                re_bottom.setVisibility(View.GONE);
                break;
            case 1:
            case 4:
            case 5:
                header_imageview.setImageResource(R.mipmap.service_loading_image);
                tv_status.setText("请等待商家处理");
                header_warning_textview.setVisibility(View.GONE);
                re_paytype.setVisibility(View.GONE);
                re_bottom.setVisibility(View.VISIBLE);
                request_changed_textview.setVisibility(View.VISIBLE);
                request_cancel_textview.setVisibility(View.VISIBLE);
                request_write_textview.setVisibility(item.getRefundStatus()==4?View.VISIBLE:View.GONE);
                break;
            case 6:
            case 8:
                header_imageview.setImageResource(R.mipmap.service_close_image);
                tv_status.setText("退款关闭");

                header_warning_textview.setVisibility(View.GONE);
                re_paytype.setVisibility(View.GONE);
                re_bottom.setVisibility(View.VISIBLE);
                request_write_textview.setVisibility(View.GONE);
                request_changed_textview.setVisibility(View.VISIBLE);
                request_cancel_textview.setVisibility(View.GONE);
                break;
            case 3:
                header_imageview.setImageResource(R.mipmap.service_close_image);
                tv_status.setText("退款关闭");
                header_warning_textview.setVisibility(View.GONE);
                re_paytype.setVisibility(View.GONE);
                re_bottom.setVisibility(View.VISIBLE);
                request_write_textview.setVisibility(View.GONE);
                request_changed_textview.setVisibility(View.VISIBLE);
                request_cancel_textview.setVisibility(View.GONE);
                break;
        }
        /**
         *最后店铺金额等等
         * */
        shop_name.setText(serviceDetailsBean.getShop().getName() + "");
        Glide.with(this).load(ApiService.IMAGE + item.getProductImage()).into(shop_imageview);
        book_name.setText(item.getProductName() + "");
        book_count.setText("x" + item.getCount());
        price_single_textview.setText("¥" + item.getProductPrice() / ApiService.onehundred);
        tv_refundPrice.setText("¥" + serviceDetailsBean.getRefundFee() / ApiService.onehundred);
        tv_orderNo.setText(item.getBillNo() + "");
        String refundReasons = "";
        if (item.getRefundReasonStr() != null) {
            for (int i = 0; i < item.getRefundReasonStr().size(); i++) {
                refundReasons += item.getRefundReasonStr().get(i) + " ";
            }
            tv_refundWilling.setText(refundReasons);
        } else {
            tv_refundWilling.setText("无");
        }
        if (item.getRefundApplyTime() != null) {
            tv_applyTime.setText(TimeUtil.formatMsecConvertTime(Long.valueOf(item.getRefundApplyTime())) + "");
        } else {
            tv_applyTime.setText("无");
        }
        /**
         * 中间退款退款
         * */
        if (serviceDetailsBean.getRefundFee() != null) {
            money_textview.setText("¥" + serviceDetailsBean.getRefundFee() / ApiService.onehundred);
        }
        switch (item.getPayType()) {
            case 0:
                backtype_textview.setText("退回余额");
                break;
            case 1:
                backtype_textview.setText("退回支付宝");
                break;
            case 5:
                backtype_textview.setText("退回微信");
                break;
        }
        /**
         * 退款申请的整个数组
         **/
        if (serviceDetailsBean.getOrderRefundRecordVOs() != null) {
            initrecyclerview(serviceDetailsBean.getOrderRefundRecordVOs(), serviceDetailsBean.getShop(), item);
        }
        //TODO
    }

    @Override
    public void cancelrefund(String s) {
        ToastUitl.showShort(MyApp.getInstance(), "撤销申请完成");
        finish();
    }

    private AlertDialog alertDialog;
    private String spinnerstring;
    private String code;
    private List<String> spinnerstringList = new ArrayList<>();
    private List<CompantItem> compantItemList = new ArrayList<>();
    @Override
    public void companylist(List<CompantItem> list) {
        compantItemList.clear();
        compantItemList.addAll(list);
        spinnerstringList.clear();
        for (CompantItem compantItem : list){
            spinnerstringList.add(compantItem.getName());
        }
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_logistics, null);
            EditText editText = view.findViewById(R.id.ed_code);
            Spinner spinner = view.findViewById(R.id.spinner);
            TextView tv_confirm = view.findViewById(R.id.tv_confirm);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, spinnerstringList);
            spinnerstring = "请选择物流公司";
            spinner.setPrompt(spinnerstring);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new MySelectedListener());
            tv_confirm.setOnClickListener(v -> {
                String uploadcode = editText.getText().toString();
                if (StringUtils.isEmpty(uploadcode)){
                    ToastUitl.showShort(MyApp.getInstance(),"请填写物流单号");
                    return;
                }
                if (spinnerstring.equals("请选择物流公司")){
                    ToastUitl.showShort(MyApp.getInstance(), "请选择物流公司");
                    return;
                }
                /**
                 * {
                 *   "orderItemId": 0,
                 *   "refundExpressCompany": "string",
                 *   "refundExpressNo": "string"
                 * }
                 * */
                Map<String, String> map = new HashMap<>();
                map.put("orderItemId", orderId);
                map.put("refundExpressCompany", code);
                map.put("refundExpressNo", uploadcode);
                mPresenter.uploadRefundNo(map);
                alertDialog.dismiss();
            });
            builder.setView(view);
            alertDialog = builder.create();
        }
        alertDialog.show();
     }

    @Override
    public void getmaxrefundfee(String money) {

    }

    @Override
    public void getmaxrefundfeeError(String message) {

    }

    class MySelectedListener implements AdapterView.OnItemSelectedListener{

         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             spinnerstring = spinnerstringList.get(position);
             code = compantItemList.get(position).getCode();
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     }

    class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private RefundDetailsActivity refundDetailsActivity;
        private List<OrderRefundRecordVOsDTO> list;
        private ShopDTO shopDTO;
        private ItemsBean orderItemDTO;
        private final int head = 0;
        private final int normal = 1;

        public ServiceAdapter(RefundDetailsActivity refundDetailsActivity, List<OrderRefundRecordVOsDTO> list, ShopDTO shopDTO, ItemsBean orderItemDTO) {
            this.refundDetailsActivity = refundDetailsActivity;
            this.list = list;
            this.shopDTO = shopDTO;
            this.orderItemDTO = orderItemDTO;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case head:
                    View view = refundDetailsActivity.mInflater.inflate(R.layout.recyclerview_status_service_withimage, parent, false);
                    RecyclerviewStatusServiceWithimageBinding binding = RecyclerviewStatusServiceWithimageBinding.bind(view);
                    ServiceWithImageViewViewHolder holder = new ServiceWithImageViewViewHolder(view, binding);
                    return holder;
                default:
                    View view1 = refundDetailsActivity.mInflater.inflate(R.layout.recyclerview_status_service, parent, false);
                    RecyclerviewStatusServiceBinding binding1 = RecyclerviewStatusServiceBinding.bind(view1);
                    ServiceViewHolder holder1 = new ServiceViewHolder(view1, binding1);
                    return holder1;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            OrderRefundRecordVOsDTO orderRefundRecordsDTO = list.get(position);
            //status (integer, optional): 售后状态 null说明未发生退款,1-申请售后,2-退款成功,3-退款拒绝,
            // 4-待退货,5-退货中,6-退款关闭,7-到账成功,8-售后完成 ,
            String name = getName(orderRefundRecordsDTO.getStatus());
            if (holder instanceof ServiceViewHolder) {
                ServiceViewHolder serviceViewHolder = (ServiceViewHolder) holder;
                if (orderRefundRecordsDTO.getStatus() == 5){ //退货中
                    serviceViewHolder.binding.content.setText("物流公司："+orderRefundRecordsDTO.getRefundExpressCompany()+
                            "\n物流单号："+orderRefundRecordsDTO.getRefundExpressNo());
                }else {
                    serviceViewHolder.binding.content.setText(orderRefundRecordsDTO.getRemarks() + "");
                }
                serviceViewHolder.binding.time.setText(TimeUtil.formatMsecConvertTime(orderRefundRecordsDTO.getCreateTime()));
                serviceViewHolder.binding.name.setText(name);
                if (position == 0) { // -2是因为第一个走售后申请
                    serviceViewHolder.binding.view1.setVisibility(View.INVISIBLE);
                    serviceViewHolder.binding.imageView.setImageResource(R.mipmap.red_nocenterofcircle);
                }
            } else {
                //imageview recyclerview
                ServiceWithImageViewViewHolder serviceWithImageViewViewHolder = (ServiceWithImageViewViewHolder) holder;
                serviceWithImageViewViewHolder.binding.name.setText(name);
                serviceWithImageViewViewHolder.binding.time.setText(TimeUtil.formatMsecConvertTime(orderRefundRecordsDTO.getCreateTime()));
                serviceWithImageViewViewHolder.binding.content.setText(orderRefundRecordsDTO.getRemarks() + "");
                List<String> images = orderItemDTO.getRefundImage();
                if (images!=null) {
                    if (images.size() > 0) {
                        serviceWithImageViewViewHolder.binding.recyclerview.setVisibility(View.VISIBLE);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(refundDetailsActivity);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        serviceWithImageViewViewHolder.binding.recyclerview.setLayoutManager(layoutManager);
                        ImageViewAdapter imageViewAdapter = new ImageViewAdapter(refundDetailsActivity, images);
                        serviceWithImageViewViewHolder.binding.recyclerview.setAdapter(imageViewAdapter);
                    } else {
                        serviceWithImageViewViewHolder.binding.recyclerview.setVisibility(View.GONE);
                    }
                }else {
                    serviceWithImageViewViewHolder.binding.recyclerview.setVisibility(View.GONE);
                }
                if (list.size() == 1) { //有2条以上数据
                    serviceWithImageViewViewHolder.binding.view1.setVisibility(View.INVISIBLE);
                } else {
                    serviceWithImageViewViewHolder.binding.view1.setVisibility(View.VISIBLE);
                }
            }
        }

        private String getName(int status) {
            switch (status) {
                case 2:
                    return "退款成功";
                case 7:
                    return "到账成功";
                case 1:
                    return "您发起了售后申请";
                case 3:
                    return "退款拒绝";
                case 4:
                    return "待退货";
                case 5:
                    return "退货中";
                case 6:
                case 8:
                    return "退款关闭";
            }
            return "";
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            super.getItemViewType(position);
            if (position == list.size() - 1) { //最后一个就是带着商品imageviewrecyclerview
                return head;
            }
            return normal;
        }

        class ServiceViewHolder extends RecyclerView.ViewHolder {
            private RecyclerviewStatusServiceBinding binding;

            public ServiceViewHolder(@NonNull View itemView, RecyclerviewStatusServiceBinding binding) {
                super(itemView);
                this.binding = binding;
            }
        }

        class ServiceWithImageViewViewHolder extends RecyclerView.ViewHolder {
            private RecyclerviewStatusServiceWithimageBinding binding;

            public ServiceWithImageViewViewHolder(@NonNull View itemView, RecyclerviewStatusServiceWithimageBinding binding) {
                super(itemView);
                this.binding = binding;
            }
        }

        class ImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            private RefundDetailsActivity refundDetailsActivity;
            private List<String> images;

            public ImageViewAdapter(RefundDetailsActivity refundDetailsActivity, List<String> images) {
                this.refundDetailsActivity = refundDetailsActivity;
                this.images = images;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = refundDetailsActivity.mInflater.inflate(R.layout.recyclerview_imageview, parent, false);
                ImageViewHolder imageViewHolder = new ImageViewHolder(view);
                return imageViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                Glide.with(refundDetailsActivity).load(ApiService.IMAGE + images.get(position)).into(imageViewHolder.imageView);
            }

            @Override
            public int getItemCount() {
                return images.size();
            }

            class ImageViewHolder extends RecyclerView.ViewHolder {
                private ImageView imageView;

                public ImageViewHolder(@NonNull View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.imageview);
                }
            }
        }
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

    }

    @Override
    public void GetRefundReasonsSuc(List<RefundReasonsBean> DataBean) {

    }

    @Override
    public void RefundSuc(String DataBean) {

    }

    @Override
    public void SetFetchedSuc(String DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void CardAddSuc(String DataBean) {

    }

    @Override
    public void CardAddFail(String message) {

    }

    @Override
    public void uploadRefundNoResult(String result) {
        request_write_textview.setVisibility(View.GONE);
        ToastUitl.showShort(MyApp.getInstance(), "上传物流单号成功");
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
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

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
}