package com.aifubook.book.groupon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.adapter.GrouponOrderAdapter;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseFragment;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.PayResult;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.AffirmMessageDialog2;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.order.MyOrderDetailsActivity;
import com.aifubook.book.order.RefundDetailsActivity;
import com.aifubook.book.order.RequestRefundActivity;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.OrderModel;
import com.aifubook.book.model.GrouponOrderModel;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.productcar.PayOkeyActivity;
import com.aifubook.book.utils.BitmapUtil;
import com.aifubook.book.utils.MiniUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GrouponOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, @Nullable OnLoadMoreListener, OnCallBack {

    private static final String TAG = "GrouponOrderFragment";
    private static final int type_order_list = 0x11;
    private static final int type_delete = 0x12;
    private static final int type_upload_refund = 0x13;
    private static final int type_cancel_order = 0x14;
    private static final int type_fetch = 0x15;
    private static final int type_order_to_pay = 0x16;
    private static final int SDK_PAY_FLAG = 0x17;
    private static final int type_order_completed = 0x18;
    private static final int type_rebuy = 0x19;


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
//                        map.put("payOrderId", orderId + "");
//                        mPresenter.orderIsPaySuccess(map);
                        if (!StringUtils.isEmpty(groupOrderId + "")) {
                            //拼团
                            openGrouponSuccessActivity();

                        }

                        ToastUitl.showShort(mActivity, "支付成功");
                        loadData();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showToast("支付异常", false);
                    }
                    break;
                }
            }
        }
    };

    private void openGrouponSuccessActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("id", groupOrderId + "");
        bundle.putString("shareMiniPic", shareMiniPic);
        bundle.putString("productId", productId);
        bundle.putString("leftCount", leftCount);
        startActivity(GrouponPaySuccessActivity.class, bundle);
    }

    private String shareMiniPic, productId;

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.ORDER_PAY_SUCCESS) {
            LogUtil.e(TAG, "支付成功");
//            Bundle bundle = new Bundle();
//            bundle.putString("OrderId", "" + payOrderId + "");
//            startActivity(PayOkeyActivity.class, bundle);

            LogUtil.e(TAG, "orderId=" + orderId);
            if (!StringUtils.isEmpty(groupOrderId + "")) {
                //拼团
                openGrouponSuccessActivity();

            } else {
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

    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_order_group_list;
    }

    private String groupStatus;
    private View root;
    private LinearLayout ll_status;
    private MySwipeRefresh mMySwipeRefresh;
    private BaseRecyclerAdapter<ItemsBean> productAdapter;
    private GrouponOrderAdapter orderAdapter;
    private GrouponOrderModel model;
    private int payType;
    private String payOrderId, orderId;


    public GrouponOrderFragment(int type) {
        super();
        this.type = type;
    }

    @Override
    protected void initView() {

        root = findViewById(R.id.root);
        ll_status = findViewById(R.id.ll_status);

        groupStatus = SharedPreferencesUtil.get(getActivity(), "GROUPSTATUS", "2");
        mMySwipeRefresh = new MySwipeRefresh(root, null);
        orderAdapter = new GrouponOrderAdapter();
        mMySwipeRefresh.setAdapter(orderAdapter);
        mMySwipeRefresh.setOnRefreshListener(GrouponOrderFragment.this);
        orderAdapter.getLoadMoreModule().setAutoLoadMore(true);
        orderAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        orderAdapter.getLoadMoreModule().setOnLoadMoreListener(this);

        setTypeStatus(type);

        model = new GrouponOrderModel(new OrderModel());
        model.setOnCallBackListener(this);

        orderAdapter.addChildClickViewIds(R.id.tv_bottom, R.id.tv_bottom2, R.id.tv_bottom3);

        orderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                OrderListBean.ListBean item = orderList.get(position);
                switch (view.getId()) {
                    case R.id.tv_bottom:
                        if (((TextView) view).getText().equals("申请售后")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", item);
                            if (item.getShop() != null) {
                                bundle.putString(IntentKey.Companion.getPHONE(), item.getShop().getPhone());
                            }
                            startActivity(RequestRefundActivity.class, bundle);
                        } else if (((TextView) view).getText().equals("删除订单")) {
                            AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                            affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (v.getId() == R.id.dialog_affirm_btn) {
                                        affirmMessageDialog.dismiss();
                                        showLoadingDialog();
                                        Map map = new HashMap();
                                        map.put("id", item.getId() + "");
                                        model.orderDelete(map, type_delete);
                                    }
                                }
                            });
                            affirmMessageDialog.show("确认要删除订单？");
                        } else if (((TextView) view).getText().equals("填写退货单号")) {
                            showReturnDialog(item.getId() + "");
                        } else if (((TextView) view).getText().equals("查看订单")) {
                            Intent intent = new Intent(getContext(), MyOrderDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", item.getId() + "");
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        } else if (((TextView) view).getText().equals("取消订单")) {
                            long validTime = item.getValidTime();
                            long hour = validTime  / (1000 * 60 * 60);
//                            long minute = (validTime - hour * (1000 * 60 * 60)) / (1000 * 60);
//                            LogUtil.e(TAG,"validTime"+validTime+"hour="+hour+" minute="+minute);
                            ShowReportDialog showReportDialog = new ShowReportDialog();
                            showReportDialog.showAlertTipDialog(getActivity(), "若发起拼单"+hour+"小时后未拼单成功，将取消订单并自动退款至原支付渠道");
                        }
                        break;
                    case R.id.tv_bottom2:
                        if (((TextView) view).getText().equals("取消订单")) {
                            AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                            affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (v.getId() == R.id.dialog_affirm_btn) {
                                        affirmMessageDialog.dismiss();
                                        Map map = new HashMap();
                                        map.put("id", item.getId() + "");
                                        model.orderSetCancle(map, type_cancel_order);
                                    }
                                }
                            });
                            affirmMessageDialog.show("确认要取消订单？");
                        } else if (((TextView) view).getText().equals("查看售后")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", item.getId() + "");
                            startActivity(RefundDetailsActivity.class, bundle);
                        } else if (((TextView) view).getText().equals("去核销")) {
                            AffirmMessageDialog2 affirmMessageDialog2 = new AffirmMessageDialog2(mActivity);
                            affirmMessageDialog2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!groupStatus.equals("1")) {
                                        ToastUitl.showShort(mActivity, "只有团长才能核销！");
                                        return;
                                    }

                                    if (v.getId() == R.id.dialog_affirm_btn) {
                                        if (StringUtils.isEmpty(affirmMessageDialog2.getText())) {
                                            Toast.makeText(mActivity, "请输入核销码", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        affirmMessageDialog2.dismiss();
                                        Map map = new HashMap();
                                        map.put("code", affirmMessageDialog2.getText() + "");
                                        map.put("id", item.getId() + "");
                                        model.setFetched(map, type_fetch);
                                    }
                                }
                            });
                            affirmMessageDialog2.show();
                        } else if (((TextView) view).getText().equals("查看核销码")) {
                            String status = item.getStatus();
                            String cancelCode = "";
                            if ("20".equals(status)) {
                                //待提货
                                cancelCode = item.getCode();
                            }
                            ShowReportDialog showReportDialog = new ShowReportDialog();
                            showReportDialog.showCancelTipDialog(getActivity(), "核销码", cancelCode);

                        } else if (((TextView) view).getText().equals("查看订单")) {
                            Intent intent = new Intent(getContext(), MyOrderDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", item.getId() + "");
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        }

                        break;
                    case R.id.tv_bottom3:

                        if (((TextView) view).getText().equals("去支付")) {
                            ShowReportDialog dialog = new ShowReportDialog();
                            dialog.choosePayType(getActivity(), payType);
                            dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                                @Override
                                public void onConfirm(Object bean) {
                                    shareMiniPic = ApiService.IMAGE + item.getItems().get(0).getProductImage();
                                    productId = item.getItems().get(0).getProductId();
                                    payType = (int) bean;
                                    orderId = item.getId() + "";
                                    payOrderId = item.getPayOrderId() + "";
                                    LogUtil.e(TAG, "click=orderId=" + orderId + "payOrderId=" + payOrderId);
                                    Map<String, String> map = new HashMap<>();
                                    map.put("orderId", item.getId() + "");
                                    if (payType == 0) {
                                        //支付宝
                                        map.put("payType", "1");
                                    } else {
                                        //wechat
                                        map.put("payType", "5");
                                    }
                                    model.orderWXRepay(map, type_order_to_pay);

                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        } else if (((TextView) view).getText().equals("确认收货")) {
                            Map map = new HashMap();
                            map.put("id", item.getId() + "");
                            model.orderCompleted(map, type_order_completed);
                        } else if (((TextView) view).getText().equals("再次购买")) {
                            Map<String, String> map = new HashMap<>();
                            map.put("orderId", "" + item.getId());
                            model.reBuy(map, type_rebuy);
                        } else if (((TextView) view).getText().equals("分享好友")) {
                            String productId = item.getItems().get(0).getProductId();
                            String shareMiniPic = ApiService.IMAGE+item.getItems().get(0).getProductImage();
                            showLoadingDialog();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bitmap = BitmapUtil.getBitmap(shareMiniPic);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            closeLoadingDialog();
                                            MiniUtil.shareMiniToWx(bitmap, productId);
                                        }
                                    });
                                }
                            }).start();
                        }
                        break;


                }

            }
        });

    }

    private boolean open;

    @Override
    public void onResume() {
        super.onResume();
        //refresh();
        open = false;
        loadData();
    }


    private int pageNo = 1;


    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type_order_list == type) {
            onOrderListResult(result);
        } else if (type_delete == type) {
            ToastUitl.showShort(mContext, "删除成功");
            loadData();
        } else if (type_upload_refund == type) {
            ToastUitl.showShort(mContext, "运单号码已提交成功,请耐心等待");
            loadData();
        } else if (type_cancel_order == type) {
            ToastUitl.showShort(mActivity, "取消成功");
            loadData();
        } else if (type_fetch == type) {
            ToastUitl.showShort(mActivity, "核销成功");
            loadData();
        } else if (type_order_to_pay == type) {
            onToPayResult(result);
        } else if (type_rebuy == type) {
            MessageEvent event = new MessageEvent(MessageEvent.ORDER_ADD_CAR);
            EventBusUtil.post(event);
        }
    }


    @Override
    public void onError(String error, int type) {
        if(type==type_order_to_pay) {
            ShowReportDialog dialog = new ShowReportDialog();
            if (error.contains("该拼团已超时")) {
                dialog.showGrouponResultDialog(getActivity(), error, 1);

            } else if (error.contains("该拼团已满额")) {
                dialog.showGrouponResultDialog(getActivity(), error, 2);

            }
            dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                @Override
                public void onConfirm(Object bean) {
                    //参与其他团
                    startActivity(GrouponActivity.class);
                    getActivity().finish();
                }

                @Override
                public void onCancel() {
                    //关闭
                    getActivity().finish();
                }
            });
        }else {
            ToastUitl.showShort(mActivity, error);
        }

    }

    private String groupOrderId="";//下单拼团id

    private String leftCount;

    private void onToPayResult(Object result) {
        SendRechargeBean Message = (SendRechargeBean) result;
        //TODO 拼单重新付款  如何判断是不是拼单
        if(Message.getGroupBuyOrderId()!=null) {
            groupOrderId = Message.getGroupBuyOrderId() + "";
            if(Message.getLeftCount()!=0) {
                leftCount = (Message.getLeftCount() - 1) + "";
            }else{
                leftCount = Message.getLeftCount() + "";
            }
        }
        if (payType == 0) {
            toAliPay(Message.getPayInfo());
        } else {
            toWXPay(Message);
        }
    }

    private IWXAPI iwxapi; //微信支付api

    private void toWXPay(SendRechargeBean notDataBean) {


        iwxapi = WXAPIFactory.createWXAPI(getActivity(), "wx494d5354ef916936", true);
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

    private void toAliPay(String orderInfo) {

        if (StringUtils.isEmpty(orderInfo)) {

            ToastUtil.showToast("支付异常", false);
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

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    List<OrderListBean.ListBean> orderList = new ArrayList<>();

    private void onOrderListResult(Object result) {
        OrderListBean DataBean = (OrderListBean) result;
        mMySwipeRefresh.setRefreshing(false);
        orderAdapter.getLoadMoreModule().setEnableLoadMore(true);

        List<OrderListBean.ListBean> dataList = DataBean.getList();


        if (dataList == null) {
            orderAdapter.getLoadMoreModule().loadMoreFail();

            return;
        }
        if (pageNo == 1) {
            orderList.clear();
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
            orderList.addAll(DataBean.getList());
        }

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadData();
    }

    private int type, pageSize = 10;
    private String logisticsType, status;

    @Override
    protected void loadData() {
        super.loadData();

        Map map = new HashMap();
        if (type == 0) {
            logisticsType = "";
        } else if (type == 1) {
            logisticsType = "";
            status = "0";
        } else if (type == 2) {
            logisticsType = "";
            status = "9";
        } else if (type == 3) {
            logisticsType = "";
            status = "10";
        } else if (type == 4) {
            logisticsType = "";
            status = "20";
        } else if (type == 5) {
            logisticsType = "4";
            status = "20";
        } else if (type == 6) {
            logisticsType = "";
            status = "30";
        } else if (type == 7) {
            logisticsType = "";
            status = "40";
        }

        map.put("status", status);
        map.put("logisticsType", logisticsType);
        map.put("from", "3");//1-不包含拼团订单(客户端不传则默认1) 2-包含拼团订单 3-只看拼团订单")
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");

        model.getOrderList(map, type_order_list);

    }

    private void setTypeStatus(int type) {

        ll_status.removeAllViews();
        if (type == 2 || type == 5) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.order_status, null);
            TextView tv_status = itemView.findViewById(R.id.tv_status);
            switch (i) {
                case 0:
                    tv_status.setText("全部");
                    tv_status.setEnabled(false);
                    break;
                case 1:
                    tv_status.setText("快递发货");
                    break;
                case 2:
                    if (type != 3 && type != 4) {
                        tv_status.setText("自提");
                    }
                    break;
                case 3:
                    tv_status.setText("同城配送");
                    break;
            }
            int finalI = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setEnable(finalI);
                    switch (finalI) {
                        case 0:
                            logisticsType = "";
                            break;
                        case 1:
                            logisticsType = "0";
                            break;
                        case 2:
                            logisticsType = "4";
                            break;
                        case 3:
                            logisticsType = "3";
                            break;

                    }
                    loadData();
                }
            });

            if (i == 2) {
                if (type == 3 || type == 4) {
                    continue;
                }
            }
            ll_status.addView(itemView);
        }

    }

    private void setEnable(int position) {
        if (type == 3 || type == 4) {
            if (position == 4) {
                position = 3;
            }
        }
        for (int i = 0; i < ll_status.getChildCount(); i++) {
            View itemView = ll_status.getChildAt(i);
            TextView tv_status = itemView.findViewById(R.id.tv_status);
            if (position == i) {
                tv_status.setEnabled(false);
            } else {
                tv_status.setEnabled(true);
            }
        }

    }

    private void showReturnDialog(String orderId) {

        ShowReportDialog showReportDialog = new ShowReportDialog();
        showReportDialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
            @Override
            public void onConfirm(Object bean) {
                String number = (String) bean;
                showLoadingDialog();
                uploadRefundNo(number, orderId);

            }

            @Override
            public void onCancel() {

            }
        });
        showReportDialog.showReturnDialog(getActivity());


    }

    private void uploadRefundNo(String expressNo, String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("expressNo", expressNo);
        model.uploadRefundNo(map, type_upload_refund);
    }


}
