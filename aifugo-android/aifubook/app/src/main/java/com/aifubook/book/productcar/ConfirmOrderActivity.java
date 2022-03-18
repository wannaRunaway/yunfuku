package com.aifubook.book.productcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.CarInBean;
import com.aifubook.book.bean.MyChileBean;
import com.aifubook.book.bean.PayResult;
import com.aifubook.book.bean.SameBean;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TrueOrderNModel;
import com.aifubook.book.dialog.CarInputNumDialog;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.groupon.GrouponActivity;
import com.aifubook.book.groupon.GrouponPaySuccessActivity;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.mine.address.AddressListActivity;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.BigDecimalUtil;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ConfirmOrderActivity extends BaseActivity implements TrueOrderNModel.OnCallBack {


    private static final String TAG = "TrueOrderNewActivity";
    private static final int DEFAULT_ADDRESS = 0x11;
    private static final int BOTTOM_DETAIL = 0x12;
    private static final int CHIEF_DETAIL = 0x13;
    private static final int SAME_CITY_INFO = 0x14;
    private static final int UN_USE_COUPON = 0x15;
    private static final int CREATE_ORDER = 0x16;
    private static final int CREATE_ORDER_ZEROBUY = 0x89;
    private static final int CHOOSE_ADDRESS = 0x17;
    private static final int PAY_WECHAT = 0x18;
    private static final int SDK_PAY_FLAG = 0x19;
    private static final int CHECK_ALIPAY_SUCCESS = 0x20;


    private int chiefId;
    private boolean hasGroup = false;//是否可以团长处自提
    private boolean isSameCity = false;//是否可以同城配送
    private int zerobuy = 0;

    @Override
    protected int setContentView() {
        return R.layout.activity_confirm_order;
    }

    private RadioGroup mRadioGroup, selfRadioGroup;
    private RadioButton fastSend, rb_shop, rb_group, selfGet, cityGo;
    private View lineOne, lineTwo, lineThree;
    private LinearLayout ll_address, ll_self, ll_self_shop, ll_group;// 快递 自提
    private TextView tv_name, tv_phone, tv_address;//快递或者同城的  姓名电话和地址
    private TextView tv_shop_name, tv_shop_address;//到店自提的地址
    private TextView tv_group_name, tv_group_address;//团长处自己的地址
    private LinearLayout ll_pay_mode;//支付方式
    private TextView tv_pay_mode;
    private TextView tv_shop;//购买商品的店铺名称
    private LinearLayout ll_books, ll_coupon;
    private TextView tv_discount;//优惠
    private EditText et_remark;
    private TextView tv_goods_total, tv_carriage, tv_discount_coupon, tv_total;
    private TextView tv_bottom_total, tv_save, tv_submit, tv_goods_num;
    private ImageView iv_alipay, iv_wechat;

    private void findView() {

        mRadioGroup = findViewById(R.id.mRadioGroup);
        selfGet = findViewById(R.id.selfGet);
        cityGo = findViewById(R.id.cityGo);
        ll_coupon = findViewById(R.id.ll_coupon);
        fastSend = findViewById(R.id.fastSend);
        lineOne = findViewById(R.id.lineOne);
        lineTwo = findViewById(R.id.lineTwo);
        lineThree = findViewById(R.id.lineThree);
        ll_address = findViewById(R.id.ll_address);//快递
        ll_self = findViewById(R.id.ll_self);//自提
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_address = findViewById(R.id.tv_address);
        selfRadioGroup = findViewById(R.id.selfRadioGroup);//自提 到店自提或者团长处自提
        tv_shop_name = findViewById(R.id.tv_shop_name);
        tv_shop_address = findViewById(R.id.tv_shop_address);
        ll_self_shop = findViewById(R.id.ll_self_shop);//到店自提
        ll_group = findViewById(R.id.ll_group);//团长处自提
        tv_group_name = findViewById(R.id.tv_group_name);//团长  名称
        tv_group_address = findViewById(R.id.tv_group_address);//团长  地址
        ll_pay_mode = findViewById(R.id.ll_pay_mode);
        tv_pay_mode = findViewById(R.id.tv_pay_mode);
        tv_shop = findViewById(R.id.tv_shop);
        ll_books = findViewById(R.id.ll_books);
        tv_discount = findViewById(R.id.tv_discount);
        et_remark = findViewById(R.id.et_remark);
        tv_goods_total = findViewById(R.id.tv_goods_total);
        tv_carriage = findViewById(R.id.tv_carriage);
        tv_discount_coupon = findViewById(R.id.tv_discount_coupon);
        tv_total = findViewById(R.id.tv_total);
        tv_bottom_total = findViewById(R.id.tv_bottom_total);
        tv_save = findViewById(R.id.tv_save);
        tv_submit = findViewById(R.id.tv_submit);
        rb_shop = findViewById(R.id.rb_shop);
        rb_group = findViewById(R.id.rb_group);
        iv_alipay = findViewById(R.id.iv_alipay);
        iv_wechat = findViewById(R.id.iv_wechat);
        tv_goods_num = findViewById(R.id.tv_goods_num);
//        mHeadView.setCentreTextView("确认订单");
        ll_coupon.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_pay_mode.setOnClickListener(this);
        var imageviewleft = findViewById(R.id.imageview_left);
        var title = (TextView)findViewById(R.id.header_textview);
        imageviewleft.setOnClickListener(v -> finish());
        title.setText("确认订单");
    }

    private CouponBeans selectCoupon;
    private int payType;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_coupon:
                if (1 == groupBuy) {
                    if (!canDiscount) {
                        ToastUitl.showShort(mContext, "该拼团不能使用优惠券");
                        return;
                    }
                }
                if (couponBeansList != null) {
                    ShowReportDialog dialog = new ShowReportDialog();
                    dialog.showOrderCouponDialog(ConfirmOrderActivity.this, couponBeansList);
                    dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                        @Override
                        public void onConfirm(Object bean) {
                            selectCoupon = (CouponBeans) bean;
                            if (totalMoney < selectCoupon.getCoupon().getFullFee()) {
                                ToastUitl.showShort(ConfirmOrderActivity.this, "未达到使用金额!");
                                return;
                            }
                            ToastUitl.showShort(ConfirmOrderActivity.this, "已选择");
                            Integer type = selectCoupon.getType();
                            if (0 == type) {
                                tv_discount.setText((int) (Double.parseDouble(selectCoupon.getCoupon().getReduceFee() + "") / 100) + "元满减券");
                            } else if (1 == type) {
                                tv_discount.setText((int) (Double.parseDouble(selectCoupon.getCoupon().getReduceFee() + "") / 100) + "元无敌券");
                            } else if (2 == type) {
                                tv_discount.setText((int) (Double.parseDouble(selectCoupon.getCoupon().getReduceFee() + "") / 100) + "元满减券");
                            } else if (3 == type) {
                                tv_discount.setText((Double.parseDouble(selectCoupon.getCoupon().getDiscountRate() + "") / 10) + "折折扣券");
                            }

                            couponSelect(selectCoupon);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }

                break;
            case R.id.tv_submit:
                toSubmit();
                break;
            case R.id.ll_address:

                if (chiefReceive) {
                    String memberName = productBean.getMemberName();
                    if (StringUtils.isEmpty(memberName)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("typeSelect", "1");
                        startActivityForResult(AddressListActivity.class, bundle, CHOOSE_ADDRESS);
                    } else {
                        ToastUitl.showShort(mContext, "该拼团为团长代收团，收货地址只有团长可以修改");
                    }

                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("typeSelect", "1");
                startActivityForResult(AddressListActivity.class, bundle, CHOOSE_ADDRESS);
                break;
            case R.id.ll_pay_mode:
                //支付方式
                ShowReportDialog dialog = new ShowReportDialog();
                dialog.choosePayType(ConfirmOrderActivity.this, payType);
                dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                    @Override
                    public void onConfirm(Object bean) {
                        payType = (int) bean;
                        if (payType == 0) {
                            //支付宝
                            iv_alipay.setVisibility(View.VISIBLE);
                            iv_wechat.setVisibility(View.GONE);
                            tv_pay_mode.setText("支付宝支付");
                        } else {
                            //wechat
                            iv_alipay.setVisibility(View.GONE);
                            iv_wechat.setVisibility(View.VISIBLE);
                            tv_pay_mode.setText("微信支付");
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });


                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("XXXXXXXXXXXXX", "!!!!!!!!!!!!!!!!!");
        if (requestCode == CHOOSE_ADDRESS && resultCode == -1) {
            addressId = data.getStringExtra("addid");
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");

            tv_name.setText(StringUtils.isNull(name));
            tv_phone.setText(StringUtils.isNull(phone));
            tv_address.setText(StringUtils.isNull(address));
        }
    }

    private void toSubmit() {
        if (selectType == 0 || selectType == 3) {
            if (StringUtils.isEmpty(addressId)) {
                ToastUitl.showShort(this, "地址不能为空!");
                return;
            }
        }
        if (selectType == 1) {
            //如果本人就是团长 选择的是多个商品绑定了不同团长
            if (!menuFlag && chiefList.size() > 1) {
                //订单存在多个自提地址，请单本单独购买，或选择其他配送方式
                ShowReportDialog dialog = new ShowReportDialog();
                dialog.showAlertTipDialog(ConfirmOrderActivity.this, "订单存在多个自提地址，请单独购买，或选择其他配送方式");
                return;
            }
            if (!hasGroup) {
                ToastUtil.showToast("请选择其它配送方式", false);
                return;
            }

        }
        if (selectType == 3) {
            if (shopBean.getSameCitySendStatus() == 0) {
                ToastUitl.showShort(ConfirmOrderActivity.this, "该商品不支持同城配送");
                fastSend.setChecked(true);
                return;
            }
            if (shopBean.getSameCitySendStatus() == 2) {
                ToastUitl.showShort(ConfirmOrderActivity.this, "该商品不支持同城配送");
                fastSend.setChecked(true);
                return;
            }
            if (!isSameCity) {
                if (sameCityError!=null) {
                    ToastUitl.showShort(MyApp.getInstance(), sameCityError);
                }else {
                    ToastUitl.showShort(MyApp.getInstance(), "当前商品获取不到运费");
                }
                return;
            }
        }
        if (selectType == 2 || selectType == 1){ //0元购 快递是0 ，同城是3， 到店自提1，团长自提2
            if (zerobuy == 1){//1就是0元购买
                orderNModel.createOrder(getRequestBodys(), CREATE_ORDER_ZEROBUY);
                return;
            }
        }
//        showLoadingDialog();
        createOrder();
    }

    private void createOrder() {
        orderNModel.createOrder(getRequestBodys(), CREATE_ORDER);
    }

    private int groupBuy;

    public RequestBody getRequestBodys() {
        Log.e("http", "" + productId);
        JSONObject jsonObject3 = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < carInBeans.size(); i++) {
            for (int j = 0; j < carInBeans.get(i).getProductListBeans().size(); j++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId", carInBeans.get(i).getProductListBeans().get(j).getId());
                jsonObject.put("count", carInBeans.get(i).getProductListBeans().get(j).getCount());
                jsonObject.put("recommend", 0);
                jsonObject.put("shopId", carInBeans.get(i).getProductListBeans().get(j).getShopId());
                jsonObject.put("chiefId", carInBeans.get(i).getProductListBeans().get(j).getChiefId());
                jsonObject.put("zeroBuy", carInBeans.get(i).getProductListBeans().get(j).getZeroBuy());
                jsonArray.add(jsonObject);
            }
        }
        jsonObject3.put("orderItems", jsonArray);
        groupBuy = carInBeans.get(0).getProductListBeans().get(0).getGroupBuy();
        String groupBuyOrderId = carInBeans.get(0).getProductListBeans().get(0).getGroupBuyOrderId();
        LogUtil.e(TAG, "groupBuy=" + groupBuy);
        jsonObject3.put("groupBuy", groupBuy);
        if (1 == groupBuy) {
            jsonObject3.put("groupBuyOrderId", groupBuyOrderId);
        }
        if (!StringUtils.isEmpty(couponSelect)) {
            JSONArray jsonArray1 = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("discountType", 1);
            jsonObject1.put("objId", couponSelectId);
            jsonArray1.add(jsonObject1);
            jsonObject3.put("discountUsedInfos", jsonArray1);
        }
        jsonObject3.put("logisticsType", selectType + "");
        jsonObject3.put("inviteCode", inviteCode);
        jsonObject3.put("remarks", "" + StringUtils.isNull(et_remark.getText().toString()));
        if (selectType == 2) {
            jsonObject3.put("shopId", carInBeans.get(0).getProductListBeans().get(0).getShopId());
        } else if (selectType == 0) {
            if (chiefReceive) {
                //设置团长自提的，不需要传这个参数
                String memberName = productBean.getMemberName();
                if (StringUtils.isEmpty(memberName)) {
                    jsonObject3.put("memberAddressId", addressId);
                }
            } else {
                jsonObject3.put("memberAddressId", addressId);
            }
        }
//        if (selectType == 3) {
//            jsonObject3.put("logisticsType", 4);
            jsonObject3.put("memberAddressId", addressId);
            jsonObject3.put("from", "app");
            jsonObject3.put("zeroBuy", zerobuy);
//        }


//        if (selectType == 1) {
//            if (chiefList.size() == 1) {
//                jsonObject3.put("chiefId", chiefId + "");
//            }
//        }
        jsonObject3.put("viewPrice", totalMoney + "");

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject3.toString());
        return requestBody;
    }


    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);

        findView();
        initData();
        initTab();


    }

    // 获取未使用优惠券
    private void getUnUsedCoupons() {
        Map<String, String> map = new HashMap<>();
        map.put("shopId", shopId);
        orderNModel.memberUnUsedCoupons(map, UN_USE_COUPON);
    }
    private TrueOrderNModel orderNModel;
    private String inviteCode = "";
    private boolean menuFlag;
    private String shopId;
    List<CarInBean> carInBeans;
    private int totalMoney;
    private int saveMoney;//优惠 折扣 计算的钱
    List<Integer> chiefList = new ArrayList();
    private String productId;
    private boolean hasCarriageFee = true; //默认不是0元购
    private int totalCount = 0;//总共的商品个数
    private void initData() {

        TrueOrderModel model = new TrueOrderModel();
        orderNModel = new TrueOrderNModel(model);
        orderNModel.setOnCallBackListener(this);
        menuFlag = SharedPreferencesUtil.get(ConfirmOrderActivity.this, KeyCom.MENU_FLAG, false);

        carInBeans = (List<CarInBean>) getIntent().getExtras().getSerializable("key");
        inviteCode = getIntent().getStringExtra("inviteCode");
        productId = getIntent().getExtras().getString("productId");
        zerobuy = getIntent().getIntExtra(IntentKey.Companion.getZEROBUY(), 0);

        LogUtil.e(TAG, carInBeans.get(0).getProductListBeans().get(0).getName());
        if (carInBeans == null) {
            finish();
        }
        shopId = carInBeans.get(0).getProductListBeans().get(0).getShopId() + "";


        chiefId = -1;
        ll_books.removeAllViews();
        productBean = carInBeans.get(0).getProductListBeans().get(0);
        groupBuyOrderId = productBean.getGroupBuyOrderId();
//        groupType = productBean.getGroupType();
        expressFree = productBean.isExpressFree();
        canDiscount = productBean.isCanDiscount();
        chiefReceive = productBean.isChiefReceive();
        shareMiniPic = productBean.getShareMiniPic();

        for (int i = 0; i < carInBeans.size(); i++) {
            List<CarInBean.productListBean> productList = carInBeans.get(i).getProductListBeans();
            for (int j = 0; j < carInBeans.get(i).getProductListBeans().size(); j++) {
                CarInBean.productListBean item = carInBeans.get(i).getProductListBeans().get(j);
//                shareMiniPic = item.getShareMiniPic();
//                chiefReceive = item.isChiefReceive();
                int zeroBuy = item.getZeroBuy();
                int count = item.getCount();
                totalCount = totalCount + count;
                chiefDiscount = chiefDiscount + (item.getChiefDiscount() * item.getCount());
                if (zeroBuy == 1) {
                    hasCarriageFee = false;
                    saveMoney = saveMoney + item.getDiscountPrice();
                    if (count > 1) {
//                        saveMoney = saveMoney + item.getDiscountPrice();
//                        saveMoney = item.getDiscountPrice();
//                        saveMoney = saveMoney + (item.getPrice() - item.getDiscountPrice()) * (count - 1);
                        totalMoney = totalMoney + item.getDiscountPrice() * (count - 1);
                    } else {
//                        saveMoney = saveMoney + item.getDiscountPrice();
//                        saveMoney = item.getDiscountPrice();
                    }
                } else {
//                    saveMoney = saveMoney + (item.getPrice() - item.getDiscountPrice()) * count;
//                    saveMoney = saveMoney + (item.getDiscountPrice()) * count;
//                    if(item.getChiefDiscount()!=0) {
//                        saveMoney=saveMoney+(item.getChiefDiscount())*item.getCount();
//                        totalMoney = totalMoney + (item.getDiscountPrice()-item.getChiefDiscount()) * item.getCount();
//                    }else{
//                        totalMoney = totalMoney + item.getDiscountPrice() * item.getCount();
//                    }
                    totalMoney = totalMoney + item.getDiscountPrice() * item.getCount();

                }

//                totalCount = totalCount + item.getCount();
                CarInBean.productListBean productListBean = productList.get(j);
                int id = productListBean.getChiefId();
                LogUtil.e(TAG, "id==" + id);
                if (chiefId != id) {
                    chiefList.add(id);
                }
                chiefId = id;

                View item_product = mInflater.inflate(R.layout.item_order_product, null);
                CommonImage iv_product = item_product.findViewById(R.id.iv_product);
                TextView tv_product_name = item_product.findViewById(R.id.tv_product_name);
                TextView tv_product_price = item_product.findViewById(R.id.tv_product_price);
                TextView tv_limit = item_product.findViewById(R.id.tv_limit);
                TextView tv_stock = item_product.findViewById(R.id.tv_stock);
                TextView tv_product_num = item_product.findViewById(R.id.tv_product_num);
                ImageView iv_remove = item_product.findViewById(R.id.iv_remove);
                ImageView iv_add = item_product.findViewById(R.id.iv_add);
                TextView tv_zeroBy = item_product.findViewById(R.id.tv_zeroBy);

                tv_product_name.setText(item.getName());
                tv_product_price.setText("￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
                tv_product_num.setText(item.getCount() + "");
                iv_product.setImageURI(ApiService.IMAGE + item.getImage());
                if (item.getLimit() == 0) {
                    tv_limit.setVisibility(View.GONE);
                } else {
                    tv_limit.setVisibility(View.VISIBLE);
                    tv_limit.setText("限购" + item.getLimit() + "件");
                }
                int stock = item.getStock();
                if (stock <= 10) {
                    tv_stock.setText("仅剩" + stock + "件");
                    tv_stock.setVisibility(View.VISIBLE);
                } else {
                    tv_stock.setVisibility(View.GONE);
                }
                tv_zeroBy.setVisibility(zeroBuy == 1 ? View.VISIBLE : View.GONE);

                iv_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (item.getCount() > 1) {
                            item.setCount(item.getCount() - 1);
                            tv_product_num.setText(item.getCount() + "");
                            updatePriceTotal();

                        } else {
                            ToastUtil.showToast("数量不能少于1", false);
                        }

                    }
                });

                iv_add.setOnClickListener(v -> {
                    if (zerobuy == 1){ //1就是0元购买
                        ToastUitl.showShort(MyApp.getInstance(), "零元购商品不能增加数量");
                        return;
                    }
                    int stock1 = item.getStock();
                    int limit = item.getLimit();
                    if (limit == 0) {
                        if (item.getCount() >= stock1) {
                            ToastUtil.showToast("库存不足", false);
                            return;
                        }
                    } else if (stock1 > limit) {
                        if (item.getCount() >= limit) {
                            ToastUtil.showToast("不能超出限购", false);
                            return;
                        }
                    } else {
                        if (item.getCount() >= stock1) {
                            ToastUtil.showToast("库存不足", false);
                            return;
                        }
                    }
                    item.setCount(item.getCount() + 1);
                    tv_product_num.setText(item.getCount() + "");
                    updatePriceTotal();
                });
                tv_product_num.setOnClickListener(v -> {

                    //修改购买数量
                    CarInputNumDialog dialog = new CarInputNumDialog();
                    dialog.setOnClickListener(new CarInputNumDialog.OnClickListener() {
                        @Override
                        public void onConfirm(int num) {
                            int stock12 = item.getStock();
                            int limit = item.getLimit();
                            int count1 = item.getCount();
                            if (limit == 0) {
                                if (num > stock12) {
                                    ToastUtil.showToast("库存不足", false);
                                    return;
                                }
                            } else if (stock12 > limit) {
                                if (num > limit) {
                                    ToastUtil.showToast("不能超出限购", false);
                                    return;
                                }
                            } else {
                                if (num > stock12) {
                                    ToastUtil.showToast("库存不足", false);
                                    return;
                                }
                            }
                            item.setCount(num);
                            tv_product_num.setText(item.getCount() + "");
                            updatePriceTotal();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    dialog.showDialog(ConfirmOrderActivity.this);

                });


                ll_books.addView(item_product);

            }
        }


        String saveM = Double.parseDouble((saveMoney + chiefDiscount) + "") / 100 + "";
        saveTotal = BigDecimalUtil.getFixedPointNum(saveM, 2);

//        showLoadingDialog();
        orderNModel.getDefaultAddress(DEFAULT_ADDRESS);

        LogUtil.e(TAG, "chiefList" + chiefList.size());
    }

    private boolean expressFree;//是否包邮
    private boolean canDiscount;//是否可以使用优惠券
    private boolean chiefReceive;//是否团长代收
    private String shareMiniPic;
    private int chiefDiscount;//团长优惠


    //    private int groupType;
    private String groupBuyOrderId;
    private CarInBean.productListBean productBean;

    //商品数量修改 调用这个方法
    public void updatePriceTotal() {
        totalMoney = 0;
        saveMoney = 0;
        totalCount = 0;
        chiefDiscount = 0;
        for (int i = 0; i < carInBeans.size(); i++) {
            List<CarInBean.productListBean> productList = carInBeans.get(i).getProductListBeans();
            for (int j = 0; j < carInBeans.get(i).getProductListBeans().size(); j++) {
                CarInBean.productListBean item = carInBeans.get(i).getProductListBeans().get(j);
                int zeroBuy = item.getZeroBuy();
                int count = item.getCount();
                totalCount = totalCount + count;
                chiefDiscount = chiefDiscount + (item.getChiefDiscount() * item.getCount());
                if (1 == zeroBuy) {
                    saveMoney = saveMoney + item.getDiscountPrice();
                    if (count > 1) {
//                        saveMoney = saveMoney + (item.getPrice() - item.getDiscountPrice()) * (count - 1);
                        totalMoney = totalMoney + item.getDiscountPrice() * (count - 1);
                        if (selectType == 0) {
                            //快递
                            carriageTotal = StringUtils.isNull((Double.parseDouble(logisticsFee + "") / 100) + "");
                            carriageLastTotal = carriageTotal;

                        } else if (selectType == 3) {
                            //同城
                            if (isSameCity) {
                                carriageTotal = StringUtils.isNull((Double.parseDouble(sameBean.getFee() + "") / 100) + "");
                                carriageLastTotal = carriageTotal;
                            }
                        }
                    }
                } else {
                    totalMoney = totalMoney + item.getDiscountPrice() * count;
                }

            }
        }
        LogUtil.e(TAG, "chiefDiscount=" + chiefDiscount);
        goodsTotal = BigDecimalUtil.getFixedPointNum(Double.parseDouble(totalMoney + "") / 100 + "", 2) + "";
        if (selectCoupon != null) {
            couponSelect(selectCoupon);

        } else {
            //修改商品总额和合计 要判断有没有运费
            if (selectType == 0 || selectType == 3) {
                //快递或者同城配送 需要加上 配送费
                if (selectType == 0) {
                    //快递
                    //如果是0元购就不要快递费
                    String showTotalMoney;
                    if (hasCarriageFee) {
                        if (expressFree) {
                            showTotalMoney = ((Double.parseDouble((totalMoney - chiefDiscount) + "")) / 100) + "";

                        } else {
                            showTotalMoney = ((Double.parseDouble((totalMoney - chiefDiscount) + "") + Double.parseDouble(logisticsFee + "")) / 100) + "";
                        }
                    } else {
                        showTotalMoney = ((Double.parseDouble((totalMoney - chiefDiscount) + "")) / 100) + "";
                    }
                    totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                    String saveM = Double.parseDouble((saveMoney + chiefDiscount) + "") / 100 + "";
                    saveTotal = BigDecimalUtil.getFixedPointNum(saveM, 2);

                } else {
                    //同城配送
                    if (isSameCity) {
                        String showTotalMoney;
                        if (expressFree) {
                            showTotalMoney = ((Double.parseDouble((totalMoney - chiefDiscount) + "")) / 100) + "";
                        } else {
                            showTotalMoney = ((Double.parseDouble((totalMoney - chiefDiscount) + "") + Double.parseDouble(sameCityFee + "")) / 100) + "";
                        }
                        totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                        String saveM = Double.parseDouble((saveMoney + chiefDiscount) + "") / 100 + "";
                        saveTotal = BigDecimalUtil.getFixedPointNum(saveM, 2);

                    }

                }

            } else {
                //自提 和 团长处自提
                if (selectType == 1 || selectType == 2) {

                    totalAmount = BigDecimalUtil.getFixedPointNum((totalMoney - chiefDiscount) + "", 2);
                    String saveM = Double.parseDouble((saveMoney + chiefDiscount) + "") / 100 + "";
                    saveTotal = BigDecimalUtil.getFixedPointNum(saveM, 2);
                }


            }

            updateNumberShow();
        }


    }


    private void getBottomDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", shopId);
        orderNModel.shopDetail(map, BOTTOM_DETAIL);
    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == DEFAULT_ADDRESS) {
//            showLoadingDialog();
            getBottomDetail();
            onAddressResult(result);
        } else if (type == BOTTOM_DETAIL) {
//            showLoadingDialog();
            getUnUsedCoupons();
            onBottomDetail(result);
        } else if (type == CHIEF_DETAIL) {
            onChiefDetail(result);
        } else if (type == SAME_CITY_INFO) {
            onSameCityInfo(result);
        } else if (type == UN_USE_COUPON) {
            onCouponResult(result);
        } else if (type == CREATE_ORDER) {
            createOrderResult(result);
        } else if (type == PAY_WECHAT) {
            chatPayResult(result);
        } else if (type == CHECK_ALIPAY_SUCCESS) {
            onCheckAlipaySuccess(result);
        } else if (type == CREATE_ORDER_ZEROBUY) { //0元购成功
            Bundle bundle = new Bundle();
            bundle.putString("OrderId", "0");
            startActivity(PayOkeyActivity.class, bundle);
            MessageEvent event = new MessageEvent(MessageEvent.PAY_SUCCESS);
            EventBusUtil.post(event);
            ConfirmOrderActivity.this.finish();
        }
    }

    private void onCheckAlipaySuccess(Object result) {
//        String rr = (String) result;
        boolean isSuccess = (boolean) result;
        if (isSuccess) {
            LogUtil.e(TAG, "支付成功");
            Bundle bundle = new Bundle();
            bundle.putString("Id", "" + payOrderId + "");
            bundle.putString("OrderId", "" + DataBean.getOrderIds().get(0));
            startActivity(PayOkeyActivity.class, bundle);
        } else {
            ToastUtil.showToast("支付失败", true);
        }
        ConfirmOrderActivity.this.finish();

    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);

        if (event.getMsg_type() == MessageEvent.ORDER_PAY_SUCCESS) {
            LogUtil.e(TAG, "支付成功");
            if (groupBuy == 1) {
                //拼团
                openGrouponSuccessActivity();

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Id", "" + payOrderId + "");
                bundle.putString("OrderId", "" + DataBean.getOrderIds().get(0));
                startActivity(PayOkeyActivity.class, bundle);
            }
            MessageEvent event1 = new MessageEvent(MessageEvent.PAY_SUCCESS);
            EventBusUtil.post(event1);
            ConfirmOrderActivity.this.finish();
        } else if (event.getMsg_type() == MessageEvent.CANCEL_WECHAT_PAY) {
            ConfirmOrderActivity.this.finish();
        }
    }

    private void openGrouponSuccessActivity() {
        Bundle bundle = new Bundle();
        //判断不是0元购就leftCount就减一
        if (!StringUtils.isEmpty(leftCount)) {
            if (!status.equals("10")) {
                int count = Integer.parseInt(leftCount) - 1;
                leftCount = count + "";
            }
        }

        if (leftCount.equals("0")) {
            bundle.putString("Id", "" + payOrderId + "");
            bundle.putString("OrderId", "" + DataBean.getOrderIds().get(0));
            startActivity(PayOkeyActivity.class, bundle);
        } else {
            bundle.putString("id", groupOrderId + "");
            bundle.putString("shareMiniPic", shareMiniPic);
            bundle.putString("productId", productId);
            bundle.putString("leftCount", leftCount);
            startActivity(GrouponPaySuccessActivity.class, bundle);
        }
        finish();
    }

    private void chatPayResult(Object result) {
        SendRechargeBean DataBean = (SendRechargeBean) result;
        if (DataBean.getGroupBuyOrderId() != null) {
            groupOrderId = DataBean.getGroupBuyOrderId() + "";
            leftCount = DataBean.getLeftCount() + "";
        }
        if (payType == 0) {
            toAliPay(DataBean.getPayInfo());
        } else {
            toWXPay(DataBean);
        }

    }


    private CreateOrderBean DataBean;
    private String payOrderId;

    private String groupOrderId = "";//下单拼团id

    private String leftCount;
    private String status;

    private void createOrderResult(Object result) {

        // {"ok":true,"code":0,"message":"ok","result":{"id":1605,"orderIds":[202108111653],
        // "payType":null,"payTime":0,"paymentNo":null,"memberId":192,"totalFee":3180,"status":0}}

        DataBean = (CreateOrderBean) result;

        status = DataBean.getStatus();
        if ("10".equals(status)) {
            //零元购
//            ToastUtil.showToast("您已购买成功", true);
            if (DataBean.getGroupBuyOrderId() != null) {
                groupOrderId = DataBean.getGroupBuyOrderId() + "";
                leftCount = DataBean.getLeftCount() + "";
            }
            //判断是不是拼团
            if (groupBuy == 1) {
                //拼团
                openGrouponSuccessActivity();

            } else {

                Bundle bundle = new Bundle();
                bundle.putString("OrderId", "" + DataBean.getOrderIds().get(0));
                startActivity(PayOkeyActivity.class, bundle);
                MessageEvent event = new MessageEvent(MessageEvent.PAY_SUCCESS);
                EventBusUtil.post(event);
                ConfirmOrderActivity.this.finish();
            }

            return;
        }

        payOrderId = DataBean.getId();
        Map<String, String> map = new HashMap<>();
        map.put("payOrderId", payOrderId + "");

        if (payType == 0) {
            //支付宝
            map.put("payType", "1");
        } else {
            //微信
            map.put("payType", "5");

        }
//        showLoadingDialog();
        orderNModel.orderToPayWeChat(map, PAY_WECHAT);

    }

    private String sameCityError;

    @Override
    public void onError(String error, int type) {
        closeLoadingDialog();
        if (type == CHIEF_DETAIL) {
            hasGroup = false;
        } else if (type == SAME_CITY_INFO) {
            isSameCity = false;
            sameCityError = error;

        } else if (type == CREATE_ORDER) {
            if (!StringUtils.isEmpty(error)) {

                if (1 == groupBuy) {

                    ShowReportDialog dialog = new ShowReportDialog();
                    if (error.contains("该拼团已超时")) {
                        dialog.showGrouponResultDialog(ConfirmOrderActivity.this, error, 1);

                    } else if (error.contains("该拼团已满额")) {
                        dialog.showGrouponResultDialog(ConfirmOrderActivity.this, error, 2);

                    } else {
                        ToastUtil.showToast(error, false);
                    }
                    dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                        @Override
                        public void onConfirm(Object bean) {
                            //参与其他团
                            startActivity(GrouponActivity.class);
                            ConfirmOrderActivity.this.finish();
                        }

                        @Override
                        public void onCancel() {
                            //关闭
                            ConfirmOrderActivity.this.finish();
                        }
                    });
                } else {
                    ToastUtil.showToast(error, false);
                }

            }

            return;
        }

        ToastUtil.showToast(error, false);
    }

    private List<CouponBeans> couponBeansList;

    private void onCouponResult(Object result) {

        couponBeansList = (List<CouponBeans>) result;
        if (couponBeansList != null) {
            tv_discount.setText(couponBeansList.size() + "张");
        }


    }

    private String goodsTotal;
    private String carriageTotal;
    private String couponTotal = "0.0";
    private String totalAmount;
    private String saveTotal;//优惠
    private String carriageLastTotal;//运费 显示

    public void updateNumberShow() {
        //商品总额  tv_goods_total ￥100
        //运费    tv_carriage ￥100
        //优惠券   tv_discount_coupon  ￥100
        //合计    tv_total ￥100

        //底部合计  tv_bottom_total ￥600
        //共省  tv_save  共省￥900 就是优惠券 还有加上优惠的钱

        tv_goods_total.setText("￥" + goodsTotal);
        //判断是否是0元购 判断是多个商品
        //是单个0元购商品 carriageLastTotal=0
        //是多个 carriageLastTotal=carriageTotal
        if (hasCarriageFee) { //不是0元购
            carriageLastTotal = carriageTotal;
        } else {
            if (isSameCity && selectType == 3 || selectType == 0) { //0元购 快递是0 ，同城是3， 到店自提1，团长自提2
                carriageLastTotal = carriageTotal;
            } else {
                carriageLastTotal = "0";
            }
        }
//        boolean expressFree = productBean.isExpressFree();
        if (expressFree) {
            carriageLastTotal = "0";
        }


        tv_carriage.setText("￥" + carriageLastTotal);
        tv_discount_coupon.setText("-￥" + couponTotal);
        tv_total.setText("￥" + totalAmount);
        tv_bottom_total.setText("￥" + totalAmount);

        tv_save.setText("优惠￥" + saveTotal);

        tv_goods_num.setText("共" + totalCount + "件");

    }

    private SameBean sameBean;//同城
    private Integer sameCityFee;


    private void onSameCityInfo(Object result) {
        sameBean = (SameBean) result;
        isSameCity = true;
        sameCityFee = sameBean.getFee();
        carriageTotal = StringUtils.isNull((Double.parseDouble(sameBean.getFee() + "") / 100) + "");
        carriageLastTotal = carriageTotal;
        // 更改 运费 和 合计
//        if (!StringUtils.isEmpty(discountMoney)) {
//            couponTotal=discountMoney;
//            totalAmount=
//            sendMoney.setText("￥" + StringUtils.isNull((Double.parseDouble(sameBean.getFee() + "") / 100) + ""));
//            moneyAll.setText("￥" + BigDecimalUtil.getFixedPointNum(showTotalMoney, 2));
        if (selectCoupon != null) {
            couponSelect(selectCoupon);
        } else {
            carriageTotal = StringUtils.isNull((Double.parseDouble(sameBean.getFee() + "") / 100) + "");
            carriageLastTotal = carriageTotal;
            if (expressFree) {
                totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney) + "") / 100) + "", 2);
            } else {
                totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney + sameBean.getFee()) + "") / 100) + "", 2);
            }
            updateNumberShow();
        }
    }

    private void onChiefDetail(Object result) {
        MyChileBean DataBean = (MyChileBean) result;
        hasGroup = true;
        String groupName = DataBean.getName();
        String address = DataBean.getProvince() + "" + DataBean.getCity() + "" + DataBean.getDistrict() + "" + DataBean.getAddress();
        LogUtil.e(TAG, "name=" + groupName + " add=" + address);
        tv_group_name.setText(groupName + "");
        tv_group_address.setText(address + "");


    }

    private ShopBean shopBean;
    private Integer logisticsFee;

    private void onBottomDetail(Object result) {
        shopBean = (ShopBean) result;
        String shopName = shopBean.getName();
        String shopAddress = shopBean.getAddress();
        logisticsFee = shopBean.getLogisticsFee();

        tv_shop.setText(shopName);
        tv_shop_name.setText(shopName);
        tv_shop_address.setText(shopAddress);
        carriageTotal = StringUtils.isNull((Double.parseDouble(logisticsFee + "") / 100) + "");
        carriageLastTotal = carriageTotal;
        goodsTotal = BigDecimalUtil.getFixedPointNum((Double.parseDouble(totalMoney + "") / 100) + "", 2);

        if (hasCarriageFee) {
            if (expressFree) {
                totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney - chiefDiscount) + "") / 100) + "", 2);
            } else {
                totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney + logisticsFee - chiefDiscount) + "") / 100) + "", 2);
            }
        } else {
            //0元购 之前没有加运费
            totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney + logisticsFee - chiefDiscount) + "") / 100) + "", 2);
        }
        updateNumberShow();
    }


    private String addressId;

    private void onAddressResult(Object result) {

        AddressListBean addressListBean = (AddressListBean) result;
        if (addressListBean == null) {
            return;
        }
        addressId = addressListBean.getId();
        String name = addressListBean.getName();
        String address = addressListBean.getProvince() + addressListBean.getCity() + addressListBean.getDistrict() + addressListBean.getAddress();
        String phone = addressListBean.getMobile();

        if (chiefReceive) {
            String memberName = productBean.getMemberName();
            String memberMobile = productBean.getMemberMobile();
            tv_name.setText(memberName);
            tv_phone.setText(memberMobile);
            tv_address.setText("");
            if (StringUtils.isEmpty(memberName)) {
                tv_name.setText(name);
                tv_address.setText(address);
                tv_phone.setText(phone);
            }

        } else {
            tv_name.setText(name);
            tv_address.setText(address);
            tv_phone.setText(phone);
        }
    }


    private String couponSelect;
    private int couponSelectId;
    private int discountType;

    //展示的money
    private String discountMoney;
    private String showTotalMoney;

    private void couponSelect(CouponBeans couponBean) {

        CouponBeans.CouponDTO item = couponBean.getCoupon();

        couponSelect = item.getReduceFee() + "";
        discountType = item.getType();
        couponSelectId = couponBean.getId();

        if (selectType == 0 || selectType == 3) {
            //快递或者同城配送 需要加上 配送费
            if (selectType == 0) {
                if (discountType == 3) {
                    //折扣券
                    //有折扣上限 需要判断
                    int discountUpLimit = item.getDiscountUpLimit();


                    Double dis = (Double.parseDouble(item.getDiscountRate() + "") / 100);
                    String r = Double.parseDouble(totalMoney + "") * dis + "";//打过折之后的价格
                    Double discountDouble = Double.parseDouble(r) / 100;//打过折之后的价格

                    discountMoney = (Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";//折扣可节省的钱
                    saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";
                    Double rr = Double.parseDouble(r) / 100;
                    if (!StringUtils.isEmpty(discountUpLimit + "") && discountUpLimit > 0) {
                        if (Double.parseDouble(discountMoney) > (Double.parseDouble(discountUpLimit + "") / 100)) {
                            //如果折扣后可节省的钱大于折扣上限，就用折扣上限的钱
                            discountMoney = Double.parseDouble(discountUpLimit + "") / 100 + "";
                            saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(discountUpLimit + "")) / 100 + "";
                            rr = ((Double.parseDouble(totalMoney + "")) - Double.parseDouble(discountUpLimit + "")) / 100;
                        }
                    }
                    LogUtil.e(TAG, "dis" + dis + "r" + "=" + r);
                    String showTotalMoney;
                    if (hasCarriageFee) {
                        showTotalMoney = (rr + (Double.parseDouble(logisticsFee + "") / 100)) + "";
                    } else {
                        showTotalMoney = rr + "";
                    }
                    totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                    couponTotal = discountMoney;
                    updateNumberShow();

                } else {
                    //满减券
                    discountMoney = Double.parseDouble(item.getReduceFee() + "") / 100 + "";
                    saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(item.getReduceFee() + "")) / 100 + "";
                    Integer fullFee = item.getFullFee();
                    //先计算是否达到满减金额
                    if (Double.parseDouble(totalMoney + "") >= Double.parseDouble(fullFee + "")) {
                        //可以使用满减券
                        couponTotal = discountMoney;
                        if (hasCarriageFee) {
                            showTotalMoney = (Double.parseDouble((totalMoney + logisticsFee - item.getReduceFee()) + "") / 100) + "";
                        } else {
                            showTotalMoney = (Double.parseDouble((totalMoney - item.getReduceFee()) + "") / 100) + "";
                        }
                        if (Double.parseDouble(showTotalMoney) <= Double.parseDouble("0")) {
                            showTotalMoney = "0";
                            totalAmount = showTotalMoney;
                        } else {
                            totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                        }
                    } else {
                        if (hasCarriageFee) {
                            showTotalMoney = (Double.parseDouble((totalMoney + logisticsFee + "") + "") / 100) + "";
                        } else {
                            showTotalMoney = (Double.parseDouble((totalMoney + "") + "") / 100) + "";
                        }
                        totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                        couponTotal = "0";
                        saveTotal = (Double.parseDouble(saveMoney + "")) / 100 + "";
                    }


                    updateNumberShow();
                }
            } else {
                //同城
                if (isSameCity) {
                    if (discountType == 3) {
                        //折扣券
                        //有折扣上限 需要判断
                        Double dis = (Double.parseDouble(item.getDiscountRate() + "") / 100);
                        String r = Double.parseDouble(totalMoney + "") * dis + "";
                        discountMoney = (Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";
                        saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";

//                        pushMoney.setText("-￥" + discountMoney);
                        couponTotal = discountMoney;
                        LogUtil.e(TAG, "dis" + dis + "r" + "=" + r);
                        Double rr = Double.parseDouble(r) / 100;
                        showTotalMoney = (rr + (Double.parseDouble(sameBean.getFee() + "") / 100)) + "";
//                        moneyAll.setText("￥" + BigDecimalUtil.getFixedPointNum(showTotalMoney, 2));
                        totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                    } else {
                        discountMoney = Double.parseDouble(item.getReduceFee() + "") / 100 + "";
                        saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(item.getReduceFee() + "")) / 100 + "";

                        Integer fullFee = item.getFullFee();
                        //先计算是否达到满减金额
                        if (Double.parseDouble(totalMoney + "") >= Double.parseDouble(fullFee + "")) {
                            //可以使用满减券
                            couponTotal = discountMoney;
                            showTotalMoney = (Double.parseDouble((totalMoney + sameBean.getFee() - item.getReduceFee()) + "") / 100) + "";
                            if (Double.parseDouble(showTotalMoney) <= 0) {
                                showTotalMoney = "0";
                                totalAmount = "0";
                            } else {
                                totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                            }

                        } else {

                            showTotalMoney = (Double.parseDouble((totalMoney + sameBean.getFee()) + "") / 100) + "";
                            totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                            saveTotal = (Double.parseDouble(saveMoney + "")) / 100 + "";


                        }


                    }
                    updateNumberShow();
                }


            }


        } else {
            if (selectType == 1 || selectType == 2) {
                //自提 需要计算满减券和折扣券
                if (discountType == 3) {
                    //折扣券
                    Double dis = (Double.parseDouble(item.getDiscountRate() + "") / 100);
                    String r = Double.parseDouble(totalMoney + "") * dis + "";
                    discountMoney = (Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";
                    saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(totalMoney + "") - Double.parseDouble(r)) / 100 + "";
                    couponTotal = discountMoney;
                    LogUtil.e(TAG, "dis" + dis + "r" + "=" + r);
                    Double rr = Double.parseDouble(r) / 100;
                    showTotalMoney = BigDecimalUtil.getFixedPointNum(rr + "", 2);
                    totalAmount = showTotalMoney;

                } else {
                    //满减券
                    discountMoney = Double.parseDouble(item.getReduceFee() + "") / 100 + "";
                    saveTotal = (Double.parseDouble(saveMoney + "") + Double.parseDouble(item.getReduceFee() + "")) / 100 + "";

                    Integer fullFee = item.getFullFee();
                    if (Double.parseDouble(totalMoney + "") >= Double.parseDouble(fullFee + "")) {
                        //可以使用满减券
                        couponTotal = discountMoney;
                        showTotalMoney = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney - item.getReduceFee()) + "") / 100) + "", 2);
                        if (Double.parseDouble(showTotalMoney + "") > 0) {
                            totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                        } else {
                            totalAmount = "0";
                        }

                    } else {
                        couponTotal = "0";
                        showTotalMoney = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney) + "") / 100) + "", 2);
                        totalAmount = BigDecimalUtil.getFixedPointNum(showTotalMoney, 2);
                        saveTotal = (Double.parseDouble(saveMoney + "")) / 100 + "";

                    }


                }

                updateNumberShow();

            }


        }

    }

    private int selectType = 0;

    private void initTab() {

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.fastSend://快递
                    if (selectType != 0) {
                        selectType = 0;
                        changeTab(0);
                        if (shopBean != null) {
                            carriageTotal = StringUtils.isNull((Double.parseDouble(logisticsFee + "") / 100) + "");
                            carriageLastTotal = carriageTotal;
                        }
                        if (selectCoupon != null) {
                            couponSelect(selectCoupon);
                        } else {
                            onBottomDetail(shopBean);
                        }
                    }

                    break;
                case R.id.selfGet://自提
                    if (chiefReceive) {
                        String memberName = productBean.getMemberName();
                        if (StringUtils.isEmpty(memberName)) {
                            setSelfGet();
                        } else {
                            fastSend.setChecked(true);
                            selfGet.setChecked(false);
                            ToastUitl.showShort(mContext, "该拼团为团长代收团，收货地址只有团长可以修改");
                        }
                        return;
                    }

                    setSelfGet();

                    break;
                case R.id.cityGo://同城
                    if (chiefReceive) {

                        String memberName = productBean.getMemberName();
                        if (StringUtils.isEmpty(memberName)) {
                            if (shopBean == null) {
                                return;
                            }
                            selectType = 3;
                            changeTab(2);
                            onSameCityAction();
                        } else {
                            ToastUitl.showShort(mContext, "该拼团为团长代收团，收货地址只有团长可以修改");
                            fastSend.setChecked(true);
                            cityGo.setChecked(false);
                            return;
                        }
                    }
                    if (shopBean == null) {
                        return;
                    }
                    selectType = 3;
                    changeTab(2);
                    onSameCityAction();
                    break;

            }
        });

        selfRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_shop://到店
                    selectType = 2;
                    ll_group.setVisibility(View.GONE);
                    ll_self_shop.setVisibility(View.VISIBLE);

                    break;
                case R.id.rb_group://团长处
                    selectType = 1;
                    ll_group.setVisibility(View.VISIBLE);
                    ll_self_shop.setVisibility(View.GONE);
                    onSelfGetAction();
                    break;
            }
        });
    }

    private void setSelfGet() {
        if (selectType != 1 && selectType != 2) {
            if (rb_shop.isChecked()) {
                selectType = 2;
            } else {
                selectType = 1;
            }
            changeTab(1);
            carriageTotal = "0.0";
            carriageLastTotal = "0.0";
            if (selectCoupon != null) {
                couponSelect(selectCoupon);
            } else {
                totalAmount = BigDecimalUtil.getFixedPointNum((Double.parseDouble((totalMoney - chiefDiscount) + "") / 100) + "", 2);
                updateNumberShow();
            }
        }
    }


    private void onSameCityAction() {

        if (shopBean == null) {
            return;
        }

        if (shopBean.getSameCitySendStatus() == 0) {
            ToastUitl.showShort(ConfirmOrderActivity.this, "该商品不支持同城配送");
//            fastSend.setChecked(true);
            return;
        }
        if (shopBean.getSameCitySendStatus() == 2) {
            ToastUitl.showShort(ConfirmOrderActivity.this, "该商品不支持同城配送");
//            fastSend.setChecked(true);
            return;
        }
        if (StringUtils.isEmpty(addressId)) {
            ToastUitl.showShort(ConfirmOrderActivity.this, "请先选择地址");
//            fastSend.setChecked(true);
            return;
        }
//        showLoadingDialog();
        //通常配送 商品数量变化的时候，需要重新获取运费
        Map<String, String> map = new HashMap<>();
        map.put("shopId", shopId);
        map.put("addressId", addressId + "");
        map.put("totalFee", totalMoney + "");
        orderNModel.getSameCityInfo(map, SAME_CITY_INFO);


    }

    private void onSelfGetAction() {

        if (chiefList.size() == 1) {
//            showLoadingDialog();
            chiefId = chiefList.get(0);
            Map map = new HashMap();
            map.put("chiefId", chiefId + "");
            orderNModel.myChief(map, CHIEF_DETAIL);
        } else if (menuFlag && chiefList.size() > 1) {
//            showLoadingDialog();
            Map map = new HashMap();
            map.put("chiefId", SharedPreferencesUtil.get(ConfirmOrderActivity.this, KeyCom.USER_ID, "") + "");
            orderNModel.myChief(map, CHIEF_DETAIL);
        }
    }


    private void changeTab(int tab) {
        if (tab == 0) {
            ll_address.setVisibility(View.VISIBLE);
            ll_self.setVisibility(View.GONE);

            lineOne.setBackground(getResources().getDrawable(R.color.F95435));
            lineTwo.setBackground(getResources().getDrawable(R.color.white));
            lineThree.setBackground(getResources().getDrawable(R.color.white));
        } else if (tab == 1) {
            ll_address.setVisibility(View.GONE);
            ll_self.setVisibility(View.VISIBLE);

            lineTwo.setBackground(getResources().getDrawable(R.color.F95435));
            lineOne.setBackground(getResources().getDrawable(R.color.white));
            lineThree.setBackground(getResources().getDrawable(R.color.white));

        } else if (tab == 2) {
            ll_address.setVisibility(View.VISIBLE);
            ll_self.setVisibility(View.GONE);

            lineThree.setBackground(getResources().getDrawable(R.color.F95435));
            lineTwo.setBackground(getResources().getDrawable(R.color.white));
            lineOne.setBackground(getResources().getDrawable(R.color.white));
        }
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
//                        orderNModel.checkAliPaySuccess(map, CHECK_ALIPAY_SUCCESS);

                        if (groupBuy == 1) {
                            //拼团
                            openGrouponSuccessActivity();

                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("Id", "" + payOrderId + "");
                            bundle.putString("OrderId", "" + DataBean.getOrderIds().get(0));
                            startActivity(PayOkeyActivity.class, bundle);
                        }

                        MessageEvent event = new MessageEvent(MessageEvent.PAY_SUCCESS);
                        EventBusUtil.post(event);
                        ConfirmOrderActivity.this.finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        ToastUtil.showToast("支付异常", false);
                        MessageEvent event = new MessageEvent(MessageEvent.PAY_SUCCESS);
                        EventBusUtil.post(event);
                        ConfirmOrderActivity.this.finish();
                    }
                    break;
                }
            }
        }
    };

    private void toAliPay(String orderInfo) {

        if (StringUtils.isEmpty(orderInfo)) {

            ToastUtil.showToast("支付异常", false);
            return;
        }

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
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

    private void toWXPay(SendRechargeBean notDataBean) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, "wx494d5354ef916936", true);
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


}
