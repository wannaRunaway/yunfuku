package com.aifubook.book.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.PayResult;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.AffirmMessageDialog2;
import com.aifubook.book.dialog.ShowReportDialog;
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
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.aifubook.book.view.PayPwdDialog;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;

import static com.aifubook.book.api.ApiService.IMAGE;

//??????????????????fragment
@SuppressLint("ValidFragment")
public class MyOrdersFragment extends BaseFragment<OrderPresenter> implements OrderView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "OrderFragment";

    private PayPwdDialog payPwdDialog;
    private int payType;
    private static final int SDK_PAY_FLAG = 0x11;
    private static final int CHECK_ALIPAY_SUCCESS = 0x12;

    List<OrderListBean.ListBean> OrderList = new ArrayList<>();
    private int type;//0.?????? 1.????????? 2.?????????  3.????????? 4.????????? 5.???????????? 6.??????

    @BindView(R.id.root)
    View root;
    @BindView(R.id.re_empty)
    RelativeLayout re_empty;

    //?????????????????????????????????????????????
//    @BindView(R.id.ll_status)
//    LinearLayout ll_status;

    private MySwipeRefresh mMySwipeRefresh;

    public MyOrdersFragment(int type) {
        super();
        this.type = type;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_myorders;
    }

    @Override
    public void initPresenter() {
        mPresenter = new OrderPresenter(this);
    }

    private String groupStatus;
    private OrderAdapter orderAdapter;

    @Override
    protected void initView() {
        groupStatus = SharedPreferencesUtil.get(getActivity(), "GROUPSTATUS", "2");
        mMySwipeRefresh = new MySwipeRefresh(root, null);
        orderAdapter = new OrderAdapter();
        mMySwipeRefresh.setAdapter(orderAdapter);
        mMySwipeRefresh.setOnRefreshListener(MyOrdersFragment.this);
        orderAdapter.getLoadMoreModule().setAutoLoadMore(true);
        orderAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        orderAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            pageNo++;
            initData();
        });
//        setTypeStatus(type);
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh();
        LogUtil.e(TAG, "onResume");
        open = false;
        initData();
    }

//    @Override
//    protected void loadData() {
//        super.loadData();
//        LogUtil.e(TAG,"loadData");
//
//    }


    private String payOrderId, orderId;
    private boolean open = false;

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.ORDER_PAY_SUCCESS) {
            LogUtil.e(TAG, "????????????");
//            Bundle bundle = new Bundle();
//            bundle.putString("OrderId", "" + payOrderId + "");
//            startActivity(PayOkeyActivity.class, bundle);

            LogUtil.e(TAG, "orderId=" + orderId);
            if (!StringUtils.isEmpty(orderId) && !open) {
                LogUtil.e(TAG, "open");
                open = true;
                Bundle bundle = new Bundle();
                bundle.putString("Id", "" + payOrderId + "");
                bundle.putString("OrderId", "" + orderId);
                startActivity(PayOkeyActivity.class, bundle);
            }
        }

    }

    int pageNo = 1;
    int pageSize = 10;


    //20  4


    private String logisticsType = "";
    private String status = "";

    private void initData() {
        Map map = new HashMap<String, String>();
        if (type == 0) { //??????
            logisticsType = "";
        } else if (type == 1) { //????????????
            logisticsType = "";
            status = "0";
        } else if (type == 2) {  //?????????
            logisticsType = "";
            status = "10";
        } else if (type == 3) { //?????????
            logisticsType = "";
            status = "20";
        } else if (type == 4) {  //?????????
            logisticsType = "4";
            status = "50";
        } else if (type == 5) { //????????????
            logisticsType = "";
            status = "30";
        } else if (type == 6) { //????????????
            logisticsType = "";
            status = "40";
        }
        //??????????????????
        if (type == 6) {
            map.put("refundState", 0);
        }
        if (type != 6) {
            map.put("status", status);
        }
        map.put("logisticsType", logisticsType);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");

//        map.put("memberId", SharedPreferencesUtil.get(mActivity, KeyCom.USER_ID, ""));

        mPresenter.orderList(map);
    }

//    private void setTypeStatus(int type) {
//        ll_status.removeAllViews();
//        if (type == 4) {
//            return;
//        }
//        for (int i = 0; i < 4; i++) {
//            View itemView = getLayoutInflater().inflate(R.layout.order_status, null);
//            TextView tv_status = itemView.findViewById(R.id.tv_status);
//            switch (i) {
//                case 0:
//                    tv_status.setText("??????");
//                    tv_status.setEnabled(false);
//                    break;
//                case 1:
//                    tv_status.setText("????????????");
//                    break;
//                case 2:
//                    if (type != 2 && type != 3) {
//                        tv_status.setText("??????");
//                    }
//                    break;
//                case 3:
//                    tv_status.setText("????????????");
//                    break;
//            }
//            int finalI = i;
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setEnable(finalI);
//                    switch (finalI) {
//                        case 0:
//                            logisticsType = "";
//                            break;
//                        case 1:
//                            logisticsType = "0";
//                            break;
//                        case 2:
//                            logisticsType = "4";
//                            break;
//                        case 3:
//                            logisticsType = "3";
//                            break;
//
//                    }
//                    getData();
//                }
//            });
//
//            if (i == 2) {
//                if (type == 2 || type == 3) {
//                    continue;
//                }
//            }
//            ll_status.addView(itemView);
//        }
//    }

    private void getData() {
        mMySwipeRefresh.setRefreshing(true);
        Map map = new HashMap();
        //??????????????????
        if (type == 6) {
            map.put("refundState", 0);
        }
        if (type != 6) {
            map.put("status", status);
        }
        map.put("logisticsType", logisticsType);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        mPresenter.orderList(map);
    }

//    private void setEnable(int position) {
//        if (type == 2 || type == 3) {
//            if (position == 3) {
//                position = 2;
//            }
//        }
//
//
//        for (int i = 0; i < ll_status.getChildCount(); i++) {
//            View itemView = ll_status.getChildAt(i);
//            TextView tv_status = itemView.findViewById(R.id.tv_status);
//            if (position == i) {
//                tv_status.setEnabled(false);
//            } else {
//                tv_status.setEnabled(true);
//            }
//        }
//    }


    @Override
    public void onRefresh() {
        pageNo = 1;
        getData();
    }

    class OrderAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> implements LoadMoreModule {

        public OrderAdapter() {
            super(R.layout.fragment_order_list_item);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, OrderListBean.ListBean item) {
            RelativeLayout ll_orderDetails = holder.getView(R.id.ll_orderDetails);
            TextView tv_bottom = holder.getView(R.id.tv_bottom);
            TextView tv_bottom2 = holder.getView(R.id.tv_bottom2);
            TextView tv_bottom3 = holder.getView(R.id.tv_bottom3);
            List<ItemsBean> itemList = item.getItems();
            holder.setText(R.id.tv_total_price, "??????????????" + Double.parseDouble(item.getTotalFee()) / 100);
            LinearLayout ll_products = holder.getView(R.id.ll_products);
            showProductList(ll_products, item.getItems());
            holder.setText(R.id.tv_storeName, item.getShop().getName());
            LogUtil.e(TAG, "status=" + item.getStatus());
            String cancelCode = "";//?????????
            switch (item.getStatus()) {
                case "0"://?????????
                    tv_bottom.setVisibility(View.GONE);
                    tv_bottom2.setVisibility(View.VISIBLE);
                    tv_bottom3.setVisibility(View.VISIBLE);
                    tv_bottom2.setText("????????????");
                    tv_bottom3.setText("?????????");
                    holder.setText(R.id.tv_orderState, "?????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    break;
                case "10"://?????????
                    tv_bottom.setVisibility(View.GONE);
                    tv_bottom2.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.GONE);
                    tv_bottom3.setText("????????????");
                    holder.setText(R.id.tv_orderState, "?????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    break;
                case "20"://?????????
                    tv_bottom.setVisibility(View.GONE);
                    tv_bottom2.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.VISIBLE);
                    tv_bottom2.setVisibility(View.VISIBLE);
                    tv_bottom2.setText("????????????");
                    tv_bottom3.setText("????????????");
                    holder.setText(R.id.tv_orderState, "?????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    break;
                case "30"://????????????
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom3.setVisibility(View.VISIBLE);
                    tv_bottom.setText("????????????");
                    tv_bottom3.setText("????????????");
                    //logisticsType (integer, optional): ???????????? 0-?????????,1-???????????????,2-?????????????????????,3-????????????,4??????/????????????(1???2?????????) ,
                    if (item.getLogisticsType().equals("0")||item.getLogisticsType().equals("3")) {
                        tv_bottom2.setText("????????????");
                        tv_bottom2.setVisibility(View.VISIBLE);
                    }else {
                        tv_bottom2.setVisibility(View.GONE);
                    }
                    holder.setText(R.id.tv_orderState, "????????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    break;
                case "50": //?????????
                    tv_bottom2.setVisibility(View.VISIBLE);
                    tv_bottom2.setText("???????????????");
                    tv_bottom.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.VISIBLE);
                    tv_bottom3.setText("????????????");
                    cancelCode = item.getCode();
                    holder.setText(R.id.tv_orderState, "?????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    break;
                case "40"://??????
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_F7553B));
                    if (item.getRefundType() == null) {
                        tv_bottom.setVisibility(View.VISIBLE);
//                        tv_bottom2.setVisibility(View.VISIBLE);
                        tv_bottom3.setVisibility(View.GONE);
                        tv_bottom.setText("????????????");
//                        tv_bottom2.setText("????????????");
                        holder.setText(R.id.tv_orderState, "????????????");
                    } else {
                        int status = item.getRefundStatus();
                        LogUtil.e(TAG, "refundStatus=" + status);
                        tv_bottom.setVisibility(View.GONE);
                        if ("1".equals(status + "")) {
                            holder.setText(R.id.tv_orderState, "???????????????");
                        } else if ("2".equals(status + "")) {
                            holder.setText(R.id.tv_orderState, "????????????");
                        } else if ("3".equals(status + "")) {
                            holder.setText(R.id.tv_orderState, "????????????");
                        } else if ("4".equals(status + "")) {
                            holder.setText(R.id.tv_orderState, "?????????");
                            tv_bottom.setText("??????????????????");
                            tv_bottom.setVisibility(View.VISIBLE);
                        } else if ("5".equals(status + "")) {
                            holder.setText(R.id.tv_orderState, "?????????");
                        }
                        tv_bottom3.setVisibility(View.GONE);
                    }
                    break;
                case "44":
                case "97":
                case "98": //??????
                case "99": //????????????
                case "100": //????????????
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom2.setVisibility(View.GONE);
                    tv_bottom3.setVisibility(View.VISIBLE);
                    tv_bottom.setText("????????????");
                    tv_bottom3.setText("????????????");
                    holder.setText(R.id.tv_orderState, "????????????");
                    ((TextView) holder.getView(R.id.tv_orderState)).setTextColor(ContextCompat.getColor(getContext(), R.color.black_999999));
                    break;
            }
            ll_orderDetails.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", item.getId() + "");
                startActivity(MyOrderDetailsActivity.class, bundle);
            });

            tv_bottom.setOnClickListener(v -> {
                if (tv_bottom.getText().equals("????????????")) {
                    AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                    affirmMessageDialog.setOnClickListener(v1 -> {
                        if (v1.getId() == R.id.dialog_affirm_btn) {
                            affirmMessageDialog.dismiss();
                            Map map = new HashMap();
                            map.put("id", item.getId() + "");
                            mPresenter.orderDelete(map);
                        }
                    });
                    affirmMessageDialog.show("????????????????????????");
                } else if (tv_bottom.getText().equals("??????????????????")) {
//                    showReturnDialog(item.getId() + "");
                }
            });

            String finalCancelCode = cancelCode;
            tv_bottom2.setOnClickListener(v -> {
                if (tv_bottom2.getText().equals("????????????")) {
                    AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                    affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == R.id.dialog_affirm_btn) {
                                affirmMessageDialog.dismiss();
                                Map map = new HashMap();
                                map.put("id", item.getId() + "");
                                mPresenter.orderSetCancle(map);
                            }
                        }
                    });
                    affirmMessageDialog.show("????????????????????????");
                } else if (tv_bottom2.getText().equals("????????????")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", item.getId() + "");
                    startActivity(RefundDetailsActivity.class, bundle);
                } else if (tv_bottom2.getText().equals("?????????")) {
                    AffirmMessageDialog2 affirmMessageDialog2 = new AffirmMessageDialog2(mActivity);
                    affirmMessageDialog2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!groupStatus.equals("1")) {
                                showShortToast("???????????????????????????");
                                return;
                            }
                            if (v.getId() == R.id.dialog_affirm_btn) {
                                if (StringUtils.isEmpty(affirmMessageDialog2.getText())) {
                                    Toast.makeText(mActivity, "??????????????????", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                affirmMessageDialog2.dismiss();
                                Map map = new HashMap();
                                map.put("code", affirmMessageDialog2.getText() + "");
                                map.put("id", item.getId() + "");
                                mPresenter.setFetched(map);
                            }
                        }
                    });
                    affirmMessageDialog2.show();
                } else if (tv_bottom2.getText().equals("???????????????")) {
                    ShowReportDialog showReportDialog = new ShowReportDialog();
                    showReportDialog.showCancelTipDialog(getActivity(), "?????????", finalCancelCode);
                } else if (tv_bottom2.getText().equals("????????????")) {
                    if (item.getLogisticsType().equals("3")) { //3???????????????
                        HashMap<String, String> map = new HashMap<>();
                        map.put("billNo", item.getBillNo());
                        mPresenter.getDadamessage(map);
                    } else {
                        if (item.getExpressNo() == null) {
                            Intent intent = new Intent();
                            intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getNOTMAKESCENE());
                            intent.setClass(getContext(), LogisticsActivity.class);
                            startActivity(intent);
                        } else {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("expressCompanyCode", item.getExpressCompany());
                            map.put("expressNo", item.getExpressNo());
                            mPresenter.getmylogisticsMessage(map);
                        }
                    }
                }
            });
            tv_bottom3.setOnClickListener(v -> {
                if (tv_bottom3.getText().equals("?????????")) {
                    ShowReportDialog dialog = new ShowReportDialog();
                    dialog.choosePayType(getActivity(), payType);
                    dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                        @Override
                        public void onConfirm(Object bean) {
                            payType = (int) bean;
                            orderId = item.getId() + "";
                            payOrderId = item.getPayOrderId() + "";
                            LogUtil.e(TAG, "click=orderId=" + orderId + "payOrderId=" + payOrderId);
                            Map<String, String> map = new HashMap<>();
                            map.put("orderId", item.getId() + "");
                            if (payType == 0) {
                                //?????????
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
                } else if (tv_bottom3.getText().equals("????????????")) {
                    Map map = new HashMap();
                    map.put("id", item.getId() + "");
                    mPresenter.orderCompleted(map);
                } else if (tv_bottom3.getText().equals("????????????")) {

                    Map<String, String> map = new HashMap<>();
                    map.put("orderId", "" + item.getId());
                    mPresenter.reBuy(map);
                }
            });
        }
    }

    private void showProductList(LinearLayout ll_products, List<ItemsBean> items) {
        ll_products.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_order_product_list, null);
            ImageView iv_product = itemView.findViewById(R.id.iv_product);
            TextView tv_product_name = itemView.findViewById(R.id.tv_product_name);
            TextView tv_price = itemView.findViewById(R.id.tv_price);
            TextView tv_product_number = itemView.findViewById(R.id.tv_product_number);
            RelativeLayout ll_details = itemView.findViewById(R.id.parent);
            TextView tv_status = itemView.findViewById(R.id.tv_status);
            ItemsBean itemsBean = items.get(i);
            Glide.with(mActivity).load(IMAGE + itemsBean.getProductImage()).into(iv_product);
            tv_product_name.setText(itemsBean.getProductName());
            tv_price.setText("??" + (itemsBean.getProductPrice()) / ApiService.onehundred);
            tv_product_number.setText("x" + itemsBean.getCount());
            String textstring = Refundstatus.getstatus(itemsBean.getRefundStatus());
            if (!textstring.equals("??????")) {
                tv_status.setText(textstring);
            }
            ll_details.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", itemsBean.getOrderId() + "");
                startActivity(MyOrderDetailsActivity.class, bundle);
            });
            ll_products.addView(itemView);
        }
    }

//    private void showReturnDialog(String orderId) {
//
//        ShowReportDialog showReportDialog = new ShowReportDialog();
//        showReportDialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
//            @Override
//            public void onConfirm(Object bean) {
//                String number = (String) bean;
//                startProgressDialog();
//                uploadRefundNo(number, orderId);
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//        showReportDialog.showReturnDialog(getActivity());
//
//
//    }

//    private void uploadRefundNo(String expressNo, String orderId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("orderId", orderId);
//        map.put("expressNo", expressNo);
//        mPresenter.uploadRefundNo(map);
//    }

    @Override
    public void OrderListSuc(OrderListBean DataBean) {
        mMySwipeRefresh.setRefreshing(false);
        orderAdapter.getLoadMoreModule().setEnableLoadMore(true);

        List<OrderListBean.ListBean> dataList = DataBean.getList();


        if (dataList == null) {
            orderAdapter.getLoadMoreModule().loadMoreFail();

            return;
        }
        if (pageNo == 1) {
            OrderList.clear();
            orderAdapter.setList(DataBean.getList());
        } else {
            orderAdapter.addData(DataBean.getList());
        }
        if (dataList.size() < pageSize) {
            orderAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            orderAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (dataList.size() > 0) {
            OrderList.addAll(DataBean.getList());
        }
        if (OrderList.size()==0){
            re_empty.setVisibility(View.VISIBLE);
        }else {
            re_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void CreateOrderSuc(CreateOrderBean DataBean) {

    }

    @Override
    public void OrderToPaySuc(ToPayBean DataBean) {

    }

    @Override
    public void OrderRepaySuc(ToPayBean DataBean) {

        Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void orderToPayWeChat(SendRechargeBean Message) {


        if (payType == 0) {
            toAliPay(Message.getPayInfo());
        } else {
            toWXPay(Message);
        }
    }


    @Override
    public void OrderIsPaySuccessSuc(Boolean DataBean) {

        boolean isSuccess = (boolean) DataBean;
        if (isSuccess) {
            LogUtil.e(TAG, "????????????");
            showShortToast("????????????");

        } else {

        }

        initData();

    }

    @Override
    public void OrderDetailSuc(OrderDetailsBean DataBean) {

    }

    @Override
    public void OrderCompletedSuc(String DataBean) {
        Toast.makeText(mActivity, "??????????????????", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void OrderSetCancleSuc(String DataBean) {
        Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void OrderDeleteSuc(String DataBean) {
        Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        initData();
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
        Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mActivity, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CardAddSuc(String DataBean) {
        MessageEvent event = new MessageEvent(MessageEvent.ORDER_ADD_CAR);
        EventBusUtil.post(event);
    }

    @Override
    public void CardAddFail(String message) {

    }

    @Override
    public void uploadRefundNoResult(String result) {
        showLongToast("???????????????????????????,???????????????");
        stopProgressDialog();
        initData();
    }

    @Override
    public void uploadRefundNoError(String error) {
        showShortToast(error);
    }

    @Override
    public void shunfenMessage(LogisticsAllBean logisticsAllBean) {
        if (logisticsAllBean.getInfo() != null) {
            if (logisticsAllBean.getInfo().getData() != null) {
                if (logisticsAllBean.getInfo().getData().size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(IntentKey.Companion.getSHUNFENG(), logisticsAllBean);
                    intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getSHUNFENG());
                    intent.putExtra(IntentKey.Companion.getLOGISTICNO(), logisticsAllBean.getInfo().getNu());
                    intent.setClass(getContext(), LogisticsActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void dadamessage(List<DadaResultBean> list) {
        if (list != null) {
            if (list.size() == 0) {
                ToastUitl.showShort(MyApp.getInstance(), "??????????????????????????????????????????");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(IntentKey.Companion.getDADA(), (Serializable) list);
            intent.putExtra(IntentKey.Companion.getLOGISTICSTYPE(), IntentKey.Companion.getDADA());
            intent.setClass(getContext(), LogisticsActivity.class);
            startActivity(intent);
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        LogUtil.e(TAG, "????????????1");
//                        Map<String, String> map = new HashMap<>();
//                        map.put("payOrderId", orderId + "");
//                        mPresenter.orderIsPaySuccess(map);
                        showShortToast("????????????");
                        initData();

                    } else {
                        // ???????????????????????????????????????????????????????????????????????????
                        ToastUtil.showToast("????????????", false);
                    }
                    break;
                }
            }
        }
    };

    private IWXAPI iwxapi; //????????????api

    private void toAliPay(String orderInfo) {

        if (StringUtils.isEmpty(orderInfo)) {

            ToastUtil.showToast("????????????", false);
            return;
        }

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.e(TAG, "alipay-result=" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // ??????????????????
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * ???????????????????????????
     **/
    private void toWXPay(SendRechargeBean notDataBean) {


        iwxapi = WXAPIFactory.createWXAPI(getActivity(), "wx494d5354ef916936", true);
        iwxapi.registerApp("wx494d5354ef916936"); //??????appid  appid???????????????????????????

        Runnable payRunnable = new Runnable() {  //??????????????????????????????
            @Override
            public void run() {
                PayReq request = new PayReq(); //????????????APP?????????
                //????????????????????????????????????????????????????????????,?????????????????????????????????????????????
                request.appId = "wx494d5354ef916936";
                request.partnerId = notDataBean.getPartnerid();
                request.prepayId = notDataBean.getPrepayId();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = notDataBean.getNonceStr();
                request.timeStamp = notDataBean.getTimeStamp();
                request.sign = notDataBean.getSignType();

                iwxapi.sendReq(request);//???????????????????????????
            }


        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
