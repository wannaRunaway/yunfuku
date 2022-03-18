package com.aifubook.book.order;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.logistics.LogisticsActivity;
import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.PayResult;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.groupon.GrouponActivity;
import com.aifubook.book.groupon.GrouponPaySuccessActivity;
import com.aifubook.book.home.MapLineShowActivity;
import com.aifubook.book.mine.order.OrderPresenter;
import com.aifubook.book.mine.order.OrderView;
import com.aifubook.book.mine.order.Refundstatus;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.aifubook.book.productcar.PayOkeyActivity;
import com.aifubook.book.utils.BitmapUtil;
import com.aifubook.book.utils.MiniUtil;
import com.aifubook.book.view.PayPwdDialog;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aifubook.book.api.ApiService.IMAGE;

/*
 * logisticsType (integer, optional): 物流类型 0-发快递,1-团长处提货,2-到店铺地址提货,3-同城配送,4团长/店铺自提(1和2的并集)
 * status (integer, optional): 订单状态 0-待支付 10-已支付 20-已发货 30-确认收货已完成或者提货核销成功
 * 40-售后中 98-超时取消 99-人工取消 100-订单删除 ,

 * */
public class MyOrderDetailsActivity extends BaseActivity<OrderPresenter> implements OrderView {

    @BindView(R.id.tv_orderState)
    TextView tv_orderState;
    @BindView(R.id.tv_storeName)
    TextView tv_storeName;
    @BindView(R.id.tv_productPrice)
    TextView tv_productPrice;
    @BindView(R.id.tv_freightPrice)
    TextView tv_freightPrice;
    @BindView(R.id.tv_couponsPrice)
    TextView tv_couponsPrice;
    @BindView(R.id.tv_actualPrice)
    TextView tv_actualPrice;
    @BindView(R.id.tv_orderNo)
    TextView tv_orderNo;
    @BindView(R.id.tv_tradeForm)
    TextView tv_tradeForm;
    @BindView(R.id.tv_creationTime)
    TextView tv_creationTime;
    @BindView(R.id.tv_paymentTime)
    TextView tv_paymentTime;
    @BindView(R.id.tv_deliveryTime)
    TextView tv_deliveryTime;
    @BindView(R.id.tv_goodsTime)
    TextView tv_goodsTime;
    @BindView(R.id.tv_bottom)
    TextView tv_bottom;
    @BindView(R.id.tv_bottom2)
    TextView tv_bottom2;
    @BindView(R.id.tv_bottom3)
    TextView tv_bottom3;

    @BindView(R.id.ll_tradeForm)
    LinearLayout ll_tradeForm;
    @BindView(R.id.ll_paymentTime)
    LinearLayout ll_paymentTime;
    @BindView(R.id.ll_deliveryTime)
    LinearLayout ll_deliveryTime;
    @BindView(R.id.ll_goodsTime)
    LinearLayout ll_goodsTime;

    @BindView(R.id.rv_product)
    RecyclerView rv_product;

    @BindView(R.id.tv_time_down)
    TextView tv_time_down;

    @BindView(R.id.tv_copy)
    TextView tv_copy;

    //    @BindView(R.id.tv_return)
//    TextView tv_return;
    //new add
    @BindView(R.id.imageview_left)
    ImageView imageView_left;
    @BindView(R.id.nameandphone_mine_textview)
    TextView nameandphone_mine_textview;
    @BindView(R.id.address_mine_real_textview)
    TextView address_mine_real_textview;
    @BindView(R.id.nameandphone_shop_textview)
    TextView nameandphone_shop_textview;
    @BindView(R.id.address_shop_real_textview)
    TextView address_shop_real_textview;
    @BindView(R.id.gudie_textview)
    TextView gudie_textview;
    @BindView(R.id.re_address_mine)
    RelativeLayout re_address_mine;
    @BindView(R.id.re_address_shop)
    RelativeLayout re_address_shop;
    @BindView(R.id.re_logistics)
    RelativeLayout re_logistics;
    @BindView(R.id.logistics_textview)
    TextView logistics_textview;
    @BindView(R.id.time_textview)
    TextView time_textview;
    @BindView(R.id.type_receive_textview)
    TextView type_receive_textview;
    private String orderId;
    private int payType;
    BaseRecyclerAdapter<ItemsBean> productAdapter;
    List<ItemsBean> productList = new ArrayList<>();
    private static final int SDK_PAY_FLAG = 0x11;

    private static final String TAG = "OrderDetailsActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_myorder_details;
    }

    @Override
    public void initPresenter() {
        mPresenter = new OrderPresenter(this);
    }

    @Override
    public void initView() {
//        setTitle("订单详情");
        orderId = getIntent().getStringExtra("id");
        LogUtil.e(TAG, "orderId=" + orderId);
        imageView_left.setOnClickListener(v -> finish());
    }

    private void initData() {
        Map map = new HashMap();
        map.put("id", orderId);
        mPresenter.orderDetail(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.ORDER_PAY_SUCCESS) {
            LogUtil.e(TAG, "支付成功");
            LogUtil.e(TAG, "orderId=" + orderId);

            if (!StringUtils.isEmpty(groupOrderId + "")) {
                //拼团
                openGrouponSuccessActivity();

            } else {
                if (!StringUtils.isEmpty(orderId)) {
                    LogUtil.e(TAG, "open");
                    Bundle bundle = new Bundle();
                    bundle.putString("OrderId", "" + orderId);
                    startActivity(PayOkeyActivity.class, bundle);
                    MyOrderDetailsActivity.this.finish();
                }
            }
        }

    }

    private void openGrouponSuccessActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("id", groupOrderId + "");
        bundle.putString("shareMiniPic", shareMiniPic);
        bundle.putString("productId", productId);
        bundle.putString("leftCount", leftCount);
        startActivity(GrouponPaySuccessActivity.class, bundle);
    }

    private void initProductList(List<ItemsBean> productList, String status, OrderDetailsBean.ShopBeanX shopBeanX, boolean islast) {
        productAdapter = new BaseRecyclerAdapter<>(mContext, productList, R.layout.recyclerview_my_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, ItemsBean item, int position, boolean isScrolling) {
                Glide.with(mContext)
                        .load(IMAGE + item.getProductImage())
                        .into((ImageView) holder.getView(R.id.shop_imageview));
                holder.setText(R.id.book_name, item.getProductName());
                holder.setText(R.id.price_single_textview, "¥" + (item.getProductPrice()) / ApiService.onehundred);
                holder.setText(R.id.book_count, "x" + item.getCount());
                //rebound_status 退款状态 //TODO
                /**
                 * refundStatus (integer, optional): 退款状态 null说明未发生退款,1-申请售后,2-退款成功,3-退款拒绝,
                 * 4-待退货,5-退货中,6-退款关闭,7-到账成功,8-售后完成
                 * status (integer, optional): 订单状态 0-待支付 10-已支付 20-已发货 30-确认收货已完成或者提货核销成功
                 * 40-售后中 98-超时取消 99-人工取消 100-订单删除 ,
                 * */
                String textstring = null;
                if (status.equals("10") | status.equals("20") | status.equals("50")) {
                    textstring = Refundstatus.getstatus(item.getRefundStatus());
                }
                holder.setText(R.id.rebound_status, textstring);
                if (textstring != null) {
                    holder.getView(R.id.rebound_status).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.rebound_status).setVisibility(View.GONE);
                }
                holder.getView(R.id.rebound_status).setOnClickListener(v -> {
                    if (item.getRefundStatus() == null) { //退款
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", productList.get(position));
                        bundle.putString(IntentKey.Companion.getPHONE(), shopBeanX.getPhone());
                        bundle.putString(IntentKey.Companion.getLOGISTICSSTATUS(), item.getStatus());
                        bundle.putBoolean(IntentKey.Companion.getISLAST(), islast);
                        startActivity(RequestRefundActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getId() + "");
                        startActivity(RefundDetailsActivity.class, bundle);
                    }
                });
            }
        };
        rv_product.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_product.setAdapter(productAdapter);
    }

    private PayPwdDialog payPwdDialog;
//    private OrderListBean.ListBean item;

    @OnClick({R.id.tv_bottom, R.id.tv_bottom2, R.id.tv_bottom3, R.id.tv_copy})
    void tv_bottom(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                //复制订单单号
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm1.setText(tv_orderNo.getText());
                showToast("复制成功");
                break;
            case R.id.tv_bottom:
                if (tv_bottom.getText().equals("删除订单")) {
                    AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mContext);
                    affirmMessageDialog.setOnClickListener(v -> {
                        if (v.getId() == R.id.dialog_affirm_btn) {
                            affirmMessageDialog.dismiss();
                            Map map = new HashMap();
                            map.put("id", orderId);
                            mPresenter.orderDelete(map);
                        }
                    });
                    affirmMessageDialog.show("确认要删除订单？");

                }
                break;
            case R.id.tv_bottom2:
                if (tv_bottom2.getText().equals("取消订单")) {
                    if (detailsBean.getStatus().equals("9")) {
                        long validTime = detailsBean.getValidTime();
                        long hour = validTime / (1000 * 60 * 60);
                        ShowReportDialog showReportDialog = new ShowReportDialog();
                        showReportDialog.showAlertTipDialog(MyOrderDetailsActivity.this, "若发起拼单" + hour + "小时后未拼单成功，将取消订单并自动退款至原支付渠道");


                    } else {

                        AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mContext);
                        affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getId() == R.id.dialog_affirm_btn) {
                                    affirmMessageDialog.dismiss();
                                    Map map = new HashMap();
                                    map.put("id", orderId);
                                    mPresenter.orderSetCancle(map);
                                }
                            }
                        });
                        affirmMessageDialog.show("确认要取消订单？");
                    }
                } else if (tv_bottom2.getText().equals("查看核销码")) {
                    ShowReportDialog showReportDialog = new ShowReportDialog();
                    showReportDialog.showCancelTipDialog(MyOrderDetailsActivity.this, "核销码", cancelCode);
                }
                break;
            case R.id.tv_bottom3:
                if (tv_bottom3.getText().equals("去支付")) {

                    //现在只做微信支付
//                    orderId = item.getId() + "";
//                    payOrderId = item.getPayOrderId() + "";
                    LogUtil.e(TAG, "click=orderId=" + orderId);

                    ShowReportDialog dialog = new ShowReportDialog();
                    dialog.choosePayType(MyOrderDetailsActivity.this, payType);
                    dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                        @Override
                        public void onConfirm(Object bean) {
                            payType = (int) bean;
//                            orderId = item.getId() + "";
//                            payOrderId = item.getPayOrderId() + "";
//
//                            LogUtil.e(TAG, "click=orderId=" + orderId+"payOrderId="+payOrderId);
                            Map<String, String> map = new HashMap<>();
                            map.put("orderId", orderId + "");
                            if (payType == 0) {
                                //支付宝
                                map.put("payType", "1");
                            } else {
                                //wechat
                                map.put("payType", "5");
                            }
                            mPresenter.orderWXRepay(map);

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                } else if (tv_bottom3.getText().equals("确认收货")) {
                    Map map = new HashMap();
                    map.put("id", orderId);
                    mPresenter.orderCompleted(map);
                } else if (tv_bottom3.getText().equals("再次购买")) {
                    // 添加到购物车
                    Map<String, String> map = new HashMap<>();
                    map.put("orderId", "" + orderId);
                    mPresenter.reBuy(map);
                } else if (tv_bottom3.getText().equals("分享好友")) {
                    //分享到微信 小程序
                    startProgressDialog();
                    new Thread(() -> {
                        Bitmap bitmap = BitmapUtil.getBitmap(shareMiniPic);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                                MiniUtil.shareMiniToWx(bitmap, productId);
                            }
                        });
                    }).start();
                }
                break;
        }
    }

//    private void uploadRefundNo(String expressNo) {
//        Map<String, String> map = new HashMap<>();
//        map.put("orderId", orderId);
//        map.put("expressNo", expressNo);
//        mPresenter.uploadRefundNo(map);
//    }


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
        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
    }

    private String groupOrderId = "";//下单拼团id

    private String leftCount = "";

    @Override
    public void orderToPayWeChat(SendRechargeBean Message) {

        if (Message.getGroupBuyOrderId() != null) {
            groupOrderId = Message.getGroupBuyOrderId() + "";
            leftCount = Message.getLeftCount() + "";
        }


        if (payType == 0) {
            toAliPay(Message.getPayInfo());
        } else {
            toWXPay(Message);
        }
    }

    private void toAliPay(String orderInfo) {

        if (StringUtils.isEmpty(orderInfo)) {

            ToastUtil.showToast("支付异常", false);
            return;
        }

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MyOrderDetailsActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.e(TAG, "alipay-result=" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        LogUtil.e(TAG, "支付成功1");
//                        Map<String, String> map = new HashMap<>();
//                        map.put("payOrderId", payOrderId + "");
//                        mPresenter.orderIsPaySuccess(map);
                        if (!StringUtils.isEmpty(groupOrderId + "")) {
                            //拼团
                            openGrouponSuccessActivity();

                        }
                        showToast("支付成功");
                        MyOrderDetailsActivity.this.finish();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        ToastUtil.showToast("支付异常", false);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public void OrderIsPaySuccessSuc(Boolean DataBean) {
        boolean isSuccess = (boolean) DataBean;
        if (isSuccess) {
            LogUtil.e(TAG, "支付成功");
            ToastUtil.showToast("支付成功", false);

        }

        initData();

    }

    private String productId, shareMiniPic;
    private OrderDetailsBean detailsBean;
    private String logisticsType;
    private String cancelCode;
    private String payOrderId = "";

    @Override
    public void OrderDetailSuc(OrderDetailsBean DataBean) {
        this.detailsBean = DataBean;
        payOrderId = DataBean.getPayOrderId();
        productId = DataBean.getItems().get(0).getProductId();
        shareMiniPic = ApiService.IMAGE + DataBean.getItems().get(0).getProductImage();
        productList.clear();
        productList.addAll(DataBean.getItems());
        int count10 = 0;
        for (int i = 0; i < DataBean.getItems().size(); i++) {
            if (DataBean.getItems().get(i).getStatus().equals("10")){
                count10 = count10+1;
            }
        }
        boolean islast = count10 == 1;
        initProductList(productList, DataBean.getStatus(), DataBean.getShop(), islast);
        //logisticsType (integer, optional): 物流类型 0-发快递,1-团长处提货,2-到店铺地址提货,3-同城配送,4团长/店铺自提(1和2的并集)
        // status (integer, optional): 订单状态 0-待支付 10-已支付 20-已发货
        // 30-确认收货已完成或者提货核销成功40-售后中 98-超时取消 99-人工取消 100-订单删除 ,
        logisticsType = DataBean.getLogisticsType();
        if (DataBean.getLogisticsType().equals("0") || DataBean.getLogisticsType().equals("3")) {//物流订单
            re_address_mine.setVisibility(View.VISIBLE);
            re_address_shop.setVisibility(View.GONE);
            if (DataBean.getStatus().equals("20") | DataBean.getStatus().equals("30")) { //物流订单且已发货或已完成
                if (DataBean.getLogisticsType().equals("3")) { //嗒哒物流
                    HashMap<String, String> map = new HashMap<>();
                    map.put("billNo", DataBean.getBillNo());
                    mPresenter.getDadamessage(map);
                } else {
                    if (DataBean.getExpressNo() == null) {
                        re_logistics.setVisibility(View.VISIBLE);
                        logistics_textview.setText("商家已发货，正在通知快递取件");
                        time_textview.setText("商家等待取件");
                        re_logistics.setOnClickListener(v -> {
                            Intent intent = new Intent();
                            intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getNOTMAKESCENE());
                            intent.setClass(MyOrderDetailsActivity.this, LogisticsActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("expressCompanyCode", DataBean.getExpressCompany());
                        map.put("expressNo", DataBean.getExpressNo());
                        mPresenter.getmylogisticsMessage(map);
                    }
                }
            } else {
                re_logistics.setVisibility(View.GONE);
            }
        } else if (DataBean.getLogisticsType().equals("2") || DataBean.getLogisticsType().equals("1")) {//店铺自提订单
            re_address_mine.setVisibility(View.GONE);
            re_address_shop.setVisibility(View.VISIBLE);
            re_logistics.setVisibility(View.GONE);
            cancelCode = DataBean.getCode();
        }
        initAddress(DataBean);
        tv_storeName.setText(DataBean.getShop().getName());
        List<ItemsBean> itemList = DataBean.getItems();
        int totalCount = 0;
        for (int i = 0; i < itemList.size(); i++) {
            ItemsBean itemsBean = itemList.get(i);
            totalCount = totalCount + Integer.parseInt(itemsBean.getCount());
        }
        tv_productPrice.setText("¥" + Double.parseDouble(DataBean.getDiscountTotalFee()) / 100 + "");//商品总结
        tv_freightPrice.setText("¥" + Double.parseDouble(DataBean.getLogisticsFee()) / 100 + "");//运费
        String discount = (Integer.parseInt(DataBean.getDiscountTotalFee()) + Integer.parseInt(DataBean.getLogisticsFee()) - Integer.parseInt(DataBean.getTotalFee())) / 100 + "";
        tv_couponsPrice.setText("-¥" + discount);
        tv_actualPrice.setText("¥" + Double.parseDouble(DataBean.getTotalFee()) / 100);//应付款
        tv_orderNo.setText(DataBean.getBillNo());
        switch (DataBean.getLogisticsType()) { //物流类型 0-发快递,1-团长处提货,2-到店铺地址提货,3-同城配送,4团长/店铺自提(1和2的并集) ,
            case "0":
                type_receive_textview.setText("快递");
                break;
            case "1":
            case "2":
                type_receive_textview.setText("自提");
                break;
            case "3":
                type_receive_textview.setText("同城配送");
                break;
        }
       /* switch (DataBean.getPayType()) {
            case "0":
                tv_tradeForm.setText("余额");
                break;
            case "1":
            case "2":
            case "3":
            case "6":
                tv_tradeForm.setText("支付宝");
                break;
            case "4":
            case "5":
            case "7":
            case "8":
                tv_tradeForm.setText("微信");
                break;
        }*/

        tv_creationTime.setText(TimeUtil.formatMsecConvertTime(DataBean.getCreateTime()) + "");
        tv_paymentTime.setText(DataBean.getPayTime() == null ? "" : TimeUtil.formatMsecConvertTime(Long.valueOf(DataBean.getPayTime())) + "");
//        tv_deliveryTime.setText("");
        tv_goodsTime.setText(DataBean.getCompletedTime() == null ? "" : TimeUtil.formatMsecConvertTime(Long.valueOf(DataBean.getCompletedTime())));
        String payType = DataBean.getPayType();
        if (!StringUtils.isEmpty(payType)) {
            if ("0".equals(payType)) {
                tv_tradeForm.setText("余额支付");
            } else if ("1".equals(payType)) {
                tv_tradeForm.setText("支付宝支付");
            } else if ("2".equals(payType)) {
                tv_tradeForm.setText("支付宝pc端支付");
            } else if ("3".equals(payType)) {
                tv_tradeForm.setText("支付宝wap端支付");
            } else if ("4".equals(payType)) {
                tv_tradeForm.setText("微信公众账号或H5支付");
            } else if ("5".equals(payType)) {
                tv_tradeForm.setText("微信支付");
            } else if ("6".equals(payType)) {
                tv_tradeForm.setText("支付宝扫码支付");
            } else if ("7".equals(payType)) {
                tv_tradeForm.setText("微信小程序支付");
            } else if ("8".equals(payType)) {
                tv_tradeForm.setText("微信扫码支付");
            } else if ("9".equals(payType)) {
                tv_tradeForm.setText("教师0元购");
            }
        }
//        tv_return.setVisibility(View.GONE);
        //订单状态 0-待支付 10-已支付 20-已发货 30-确认收货已完成或者提货核销成功 40-售后中 98-超时取消 99-人工取消 100-订单删除 ,
        switch (DataBean.getStatus()) {
            case "0"://待付款
                long closeCountDown = DataBean.getCloseCountDown();
                showTimeCounter(closeCountDown);
                tv_bottom.setVisibility(View.GONE);
                tv_bottom2.setVisibility(View.VISIBLE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom2.setText("取消订单");
                tv_bottom3.setText("去支付");
                tv_orderState.setText("待付款");
//                ll_tradeForm.setVisibility(View.GONE);
                ll_paymentTime.setVisibility(View.GONE);
                ll_deliveryTime.setVisibility(View.GONE);
                ll_goodsTime.setVisibility(View.GONE);
                break;
            case "9"://拼团中
//                Long leftTime = DataBean.getLeftTime();
                showTimeCounter(DataBean.getLeftTime());
                tv_bottom.setVisibility(View.GONE);
                tv_bottom2.setVisibility(View.VISIBLE);
                tv_bottom3.setVisibility(View.VISIBLE);
//                tv_bottom.setText("取消订单");
                tv_bottom2.setText("取消订单");
                tv_bottom3.setText("分享好友");
//                holder.setText(R.id.tv_orderState, "拼团中");
                break;
            case "10"://待发货
                tv_bottom.setVisibility(View.GONE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.GONE);
                ll_tradeForm.setVisibility(View.VISIBLE);
//                tv_bottom3.setText("确认收货");
                if ("0".equals(logisticsType) || "1".equals(logisticsType) || "2".equals(logisticsType)) {
                    tv_orderState.setText("待发货");
                } else if ("3".equals(logisticsType)) {
                    tv_orderState.setText("骑手等待取货");
                }
                ll_deliveryTime.setVisibility(View.GONE);
                ll_goodsTime.setVisibility(View.GONE);
                break;
            case "20"://待收货
                tv_bottom.setVisibility(View.GONE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                ll_tradeForm.setVisibility(View.VISIBLE);
                tv_bottom3.setText("确认收货");
                if ("0".equals(logisticsType) || "1".equals(logisticsType) || "2".equals(logisticsType)) {
                    tv_orderState.setText("待收货");
                } else if ("3".equals(logisticsType)) {
                    tv_orderState.setText("骑手正在配送中");
                    tv_bottom2.setVisibility(View.GONE);
                }
                ll_goodsTime.setVisibility(View.GONE);
                break;
            case "30"://交易完成
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                ll_tradeForm.setVisibility(View.VISIBLE);
                tv_bottom.setText("删除订单");
                tv_bottom3.setText("再次购买");
                tv_orderState.setText("交易完成");
                break;
            case "40"://售后
                ll_tradeForm.setVisibility(View.VISIBLE);
                if (DataBean.getRefundType() == null) {
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom2.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.GONE);
                    tv_bottom.setText("删除订单");
//                    tv_bottom2.setText("查看售后");
                    tv_orderState.setText("退款失败");
                } else {
                    String status = DataBean.getRefundStatus();
                    if ("1".equals(status)) {
//                        tv_bottom2.setText("查看售后");
                        tv_orderState.setText("退货申请中");
                    } else if ("2".equals(status)) {
                        tv_orderState.setText("退款成功");
                    } else if ("3".equals(status)) {
                        tv_orderState.setText("退款拒绝");
                    } else if ("4".equals(status)) {
                        tv_orderState.setText("待退货");
//                        tv_return.setVisibility(View.VISIBLE);
                    } else if ("5".equals(status)) {
                        tv_orderState.setText("退货中");
                    }
//                    tv_bottom2.setText("查看售后");
                    tv_bottom.setVisibility(View.GONE);
                    tv_bottom2.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.GONE);
                    tv_orderState.setVisibility(View.VISIBLE);
                }
                break;
            case "50": //待自提
                tv_bottom2.setVisibility(View.VISIBLE);
                tv_bottom2.setText("查看核销码");
                tv_bottom.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom3.setText("确认收货");
                tv_orderState.setText("待自提");
                ll_goodsTime.setVisibility(View.GONE);
                break;
            case "44":
            case "97":
            case "98":
            case "99":
            case "100":
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                ll_tradeForm.setVisibility(View.VISIBLE);
                tv_bottom.setText("删除订单");
                tv_bottom3.setText("再次购买");
                tv_orderState.setText("交易关闭");
                ll_tradeForm.setVisibility(View.GONE);
                ll_paymentTime.setVisibility(View.GONE);
                ll_deliveryTime.setVisibility(View.GONE);
                ll_goodsTime.setVisibility(View.GONE);
                break;

        }
        stopProgressDialog();
    }

    private void initAddress(OrderDetailsBean dataBean) {
        if (dataBean.getAddress() != null) {
            if (dataBean.getAddress() != null) {
                nameandphone_mine_textview.setText(dataBean.getAddress().getName() + "  " + dataBean.getAddress().getMobile());
                address_mine_real_textview.setText(dataBean.getAddress().getProvince() + dataBean.getAddress().getDistrict() + dataBean.getAddress().getCity() + dataBean.getAddress().getAddress() + "");
            }
        }
        //logisticsType (integer, optional): 物流类型 0-发快递,1-团长处提货,2-到店铺地址提货,3-同城配送,4团长/店铺自提(1和2的并集)
        if (dataBean.getLogisticsType().equals("2")) { //2-到店铺地址提货
            if (dataBean.getShop() != null) {
                nameandphone_shop_textview.setText(dataBean.getShop().getName() + "  " + dataBean.getShop().getPhone());
                address_shop_real_textview.setText(dataBean.getShop().getAddress() + "");
            }
        } else {
            if (dataBean.getChief() != null) {
                nameandphone_shop_textview.setText("团长：" + dataBean.getChief().getName());
                address_shop_real_textview.setText(dataBean.getChief().getAddress() + "");
            }
        }
        List<String> location = dataBean.getShop().getLocation();
        LogUtil.e(TAG, "location=" + location.toString());
        String lat = "", lon = "";
        if (location.size() > 0) {
            lat = location.get(0) + "";
            lon = location.get(1) + "";
        }
        Bundle bundle = new Bundle();
        bundle.putString("lag", "" + lat);
        bundle.putString("lon", "" + lon);
        bundle.putString("name", "" + dataBean.getShop().getName());
        bundle.putString("add", "" + dataBean.getShop().getAddress());
        gudie_textview.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(
                    this,
                    MapLineShowActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private CountDownTimer countDownTimer;
    private String stringremain = "剩余";
    private String stringcloseauto = "自动关闭";

    private void showTimeCounter(long times) {
        LogUtil.e(TAG, "times=" + times);
        if (times <= 0) {
            return;
        }
        countDownTimer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                long hour = (millisUntilFinished - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minute = (millisUntilFinished - days * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = millisUntilFinished % 60000;
                long second = Math.round((float) seconds / 1000);
                LogUtil.e(TAG, "times=" + minute + " - " + second);

                if (detailsBean.getStatus().equals("9")) {
                    if (days > 0) {
                        if (tv_time_down != null) {
                            tv_time_down.setText(stringremain + days + "天" + hour + "小时" + minute + "分" + second + "秒" + stringcloseauto);
                        }
                    } else {
                        if (tv_time_down != null) {
                            tv_time_down.setText(stringremain + hour + "小时" + minute + "分" + second + "秒" + stringcloseauto);
                        }
                    }
                } else {
                    if (tv_time_down != null) {
                        tv_time_down.setText(stringremain + minute + "分" + second + "秒" + stringcloseauto);
                    }
                }
            }

            @Override
            public void onFinish() {
//                ll_sale.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

    }

    private IWXAPI iwxapi; //微信支付api

    /**
     * 调起微信支付的方法
     **/
    private void toWXPay(SendRechargeBean notDataBean) {
        iwxapi = WXAPIFactory.createWXAPI(MyOrderDetailsActivity.this, "wx494d5354ef916936", true);
        iwxapi.registerApp("wx494d5354ef916936"); //注册appid  appid可以在开发平台获取
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = "wx494d5354ef916936";
                request.partnerId = notDataBean.getPartnerid();
                request.prepayId = notDataBean.getPrepayId();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = notDataBean.getNonceStr();
                request.timeStamp = notDataBean.getTimeStamp();
                request.sign = notDataBean.getSignType();

                iwxapi.sendReq(request);//发送调起微信的请求
            }


        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void OrderCompletedSuc(String DataBean) {
        Toast.makeText(mContext, "确认收货成功", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void OrderSetCancleSuc(String DataBean) { //取消订单
        Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();
        countDownTimer.cancel();
        countDownTimer = null;
        tv_time_down.setText("");
        initData();
    }

    @Override
    public void OrderDeleteSuc(String DataBean) {
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
//        initData();
        MyOrderDetailsActivity.this.finish();
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
        Toast.makeText(mContext, "核销成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetHomePageFail(String Message) {

        ShowReportDialog dialog = new ShowReportDialog();
        if (Message.contains("该拼团已超时")) {
            dialog.showGrouponResultDialog(MyOrderDetailsActivity.this, Message, 1);

        } else if (Message.contains("该拼团已满额")) {
            dialog.showGrouponResultDialog(MyOrderDetailsActivity.this, Message, 2);

        }
        dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
            @Override
            public void onConfirm(Object bean) {
                //参与其他团
                startActivity(GrouponActivity.class);
                MyOrderDetailsActivity.this.finish();
            }

            @Override
            public void onCancel() {
                //关闭
                MyOrderDetailsActivity.this.finish();
            }
        });

        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
//        initData();
    }

    @Override
    public void CardAddSuc(String DataBean) {
        MessageEvent event = new MessageEvent(MessageEvent.ORDER_ADD_CAR);
        EventBusUtil.post(event);
        MyOrderDetailsActivity.this.finish();
    }

    @Override
    public void CardAddFail(String message) {

    }

    @Override
    public void uploadRefundNoResult(String result) {
        ToastUtil.showToast("提交成功", false);
        MyOrderDetailsActivity.this.finish();
    }

    @Override
    public void uploadRefundNoError(String error) {
        ToastUtil.showToast(error, false);
    }

    @Override
    public void shunfenMessage(LogisticsAllBean logisticsAllBean) {
        if (logisticsAllBean.getInfo() != null) {
            if (logisticsAllBean.getInfo().getData() != null) {
                if (logisticsAllBean.getInfo().getData().size() > 0) {
                    re_logistics.setVisibility(View.VISIBLE);
                    logistics_textview.setText(logisticsAllBean.getInfo().getData().get(0).getContext());
                    time_textview.setText(logisticsAllBean.getInfo().getData().get(0).getFtime());
                    re_logistics.setOnClickListener(v -> {
                        Intent intent = new Intent();
                        intent.putExtra(IntentKey.Companion.getSHUNFENG(), logisticsAllBean);
                        intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getSHUNFENG());
                        intent.putExtra(IntentKey.Companion.getLOGISTICNO(), logisticsAllBean.getInfo().getNu());
                        intent.setClass(MyOrderDetailsActivity.this, LogisticsActivity.class);
                        startActivity(intent);
                    });
                }
            }
        }
    }

    @Override
    public void dadamessage(List<DadaResultBean> list) {
        if (list != null) {
            if (list.size() == 0) {
                re_logistics.setVisibility(View.VISIBLE);
                logistics_textview.setText("商家已发货，正在通知快递取件");
                time_textview.setText("商家等待取件");
                return;
            }
            DadaResultBean dadaResultBean = list.get(0);
            re_logistics.setVisibility(View.VISIBLE);
            logistics_textview.setText("接单信息" + dadaResultBean.getDmName() + "  " + dadaResultBean.getDmMobile());
            time_textview.setText(dadaResultBean.getUpdateTime() + "");
            re_logistics.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra(IntentKey.Companion.getDADA(), (Serializable) list);
                intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getDADA());
                intent.setClass(MyOrderDetailsActivity.this, LogisticsActivity.class);
                startActivity(intent);
            });
        }
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