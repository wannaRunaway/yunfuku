package com.aifubook.book.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.aifubook.book.R;
import com.aifubook.book.activity.check.CheckActivity;
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.activity.main.MainActivity;
import com.aifubook.book.activity.main.beans.BeansActivity;
import com.aifubook.book.activity.main.money.MoneyActivity;
import com.aifubook.book.activity.main.shopdetail.DialogGradeKt;
import com.aifubook.book.activity.main.shopdetail.IosDialog;
import com.aifubook.book.activity.teacher.TeacherFreeBookActivity;
import com.aifubook.book.activity.webview.TeacherCheckActivity;
import com.aifubook.book.activity.webview.TeacherWebviewActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.fragment.groupheader.GroupHeaderActivity;
import com.aifubook.book.groupon.GrouponOrderListActivity;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.LoginNewActivity;
import com.aifubook.book.mine.ServiceActivity;
import com.aifubook.book.mine.address.AddressListActivity;
import com.aifubook.book.mine.coupons.CouponsActivity;
import com.aifubook.book.mine.coupons.CouponsUnusedActivity;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.member.MemberInfoPresenter;
import com.aifubook.book.mine.member.MemberInfoView;
import com.aifubook.book.order.ActivityAllOrdersList;
import com.aifubook.book.mine.self.PersonalUpdataActivity;
import com.aifubook.book.mine.setting.AcountMangerActivity;
import com.aifubook.book.mine.setting.SettingActivity;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.service.MyServiceActivity;
import com.aifubook.book.shoprequest.ShopRequestActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment<MemberInfoPresenter> implements MemberInfoView, View.OnClickListener {

    @BindView(R.id.iv_head)
    RoundedImageView iv_head;

    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.textview_teacher_or_student)
    TextView textview_teacher_or_student;
    @BindView(R.id.teacher_recognize_confirm)
    TextView teacher_recognize_confirm;
    @BindView(R.id.textview_beans)
    TextView textview_beans;
    @BindView(R.id.textview_money)
    TextView textview_money;
    @BindView(R.id.re_class)
    RelativeLayout re_class;
    @BindView(R.id.re_recognize)
    RelativeLayout re_recognize;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_wait_count)
    TextView tv_waitCount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_pay_count)
    TextView tv_payCount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_delivery_count)
    TextView tv_deliveryCount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_service_count)
    TextView tv_serviceCount;

    @BindView(R.id.re_groupbuying)
    View re_groupBuying;

    @BindView(R.id.textview_coupons)
    TextView textview_coupons;

    @BindView(R.id.iv_share)
    ImageView iv_share;

    @BindView(R.id.re_teacher)
    RelativeLayout re_teacher;
    @BindView(R.id.tv_count_ziti)
    TextView tv_count_ziti;
    boolean wasGroup = false;
    private static final String TAG = "HomeMyFragment";
    private boolean isteacherrecognize = false;
    private boolean isbindshop = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MemberInfoPresenter(this);
    }

    @Override
    protected void initView() {
//        if (!checkLogin()) {
//            Map map = new HashMap();
//            SelectType = 3;
//            mPresenter.chiefDetail(map);
//        }
        re_groupBuying.setOnClickListener(v -> {
            if (Check()) {
                ToastUitl.showLong(mActivity, "请先登录");
                startActivity(LoginNewActivity.class);
                return;
            }
            startActivity(GrouponOrderListActivity.class);
        });
        re_class.setOnClickListener(v -> gradeDialog = DialogGradeKt.gradeclick(getActivity(), viewOnclickListener));
        re_recognize.setOnClickListener(v -> recognize());

        //展示年级和员工属性:教师、家长
        getShareData();
    }

    private void getShareData() {
        tv_class.setText(SharedPreferencesUtil.get(getContext(), ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade()));
        String identity = SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), ShareKey.Companion.getDefaultTeacherOrStudent());
        textview_teacher_or_student.setText(identity);
        if (identity.equals("家长")) {
            teacher_recognize_confirm.setVisibility(View.GONE);
        } else {
            teacher_recognize_confirm.setVisibility(View.VISIBLE);
        }
    }

    private AlertDialog gradeDialog;
    private View.OnClickListener viewOnclickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textview_grade1:
                case R.id.textview_grade2:
                case R.id.textview_grade3:
                case R.id.textview_grade4:
                case R.id.textview_grade5:
                case R.id.textview_grade6:
                case R.id.textview_grade7:
                case R.id.textview_grade8:
                case R.id.textview_grade9:
                    String text = ((TextView) v).getText().toString();
                    tv_class.setText(text);
                    if (!checkLogin()) {
                        uploadMemberInfo(text);
                    }
                    SharedPreferencesUtil.put(getContext(), ShareKey.Companion.getGRADE(), text);
                    gradeDialog.dismiss();
                    break;
                case R.id.textview_close:
                    gradeDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private void uploadMemberInfo(String text) {
        if (memberInfoBean == null) {
            return;
        }
        Map<String, String> map = new HashMap();
        map.put("grade", text);
        map.put("age", "" + memberInfoBean.getAge());
        map.put("name", "" + memberInfoBean.getName());a p
        map.put("sex", "" + memberInfoBean.getSex());
        map.put("nickname", "" + memberInfoBean.getNickname());
        map.put("cityId", "" + memberInfoBean.getCityId());
        map.put("districtId", "" + memberInfoBean.getDistrictId());
        map.put("provinceId", "" + memberInfoBean.getProvinceId());
        map.put("city", "" + memberInfoBean.getCity());
        map.put("district", "" + memberInfoBean.getDistrict());
        map.put("province", "" + memberInfoBean.getProvince());
        map.put("region", "" + memberInfoBean.getProvince() + "-" + memberInfoBean.getCity() + "-" + memberInfoBean.getDistrict());
        map.put("logo", "" + memberInfoBean.getLogo());
        map.put("schoolName", memberInfoBean.getSchoolName());
        map.put("registeredIdentity", memberInfoBean.getRegisteredIdentity() + "");
        /*
        *        cityId = DataBean.getCityId();
                dirId = DataBean.getDistrictId();
                proId = DataBean.getProvinceId();
        * */
        mPresenter.updateMemberInfo(map);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(com.aifubook.book.fragment.MessageEvent event) {
        uploadMemberInfo(event.grade);
        LogUtil.d("得到的grade_" + event.grade);
    }

    @Override
    public void onHiddenChanged(boolean hidden) { //其他fragment到当前执行
        super.onHiddenChanged(hidden);
        LogUtil.d(isHidden() + " HomeMyFragment.class 文件");
        if (!isHidden()) {
            onrefreshandloadmine();
        }
    }

    public void onrefreshandloadmine() {
        getShareData();
        if (!checkLogin()) {
            Map map = new HashMap();
            mPresenter.memberInfo(map);
            mPresenter.getOrderCount();
            mPresenter.getAccountInfo(map);
        } else {
            re_teacher.setVisibility(View.GONE);
        }
    }

    @Override
    public void getWeChatToken(String token) {
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.LOGIN_OUT) {
            tv_userName.setText("立即登录");
            iv_head.setImageResource(R.mipmap.icon_my_head);

//            tv_baoming.setVisibility(View.INVISIBLE);
//            tv_tuanzhang.setVisibility(View.INVISIBLE);
            tv_deliveryCount.setVisibility(View.INVISIBLE);
            tv_payCount.setVisibility(View.INVISIBLE);
            tv_serviceCount.setVisibility(View.INVISIBLE);
            tv_waitCount.setVisibility(View.INVISIBLE);
            tv_count_ziti.setVisibility(View.INVISIBLE);
        } else if (event.getMsg_type() == MessageEvent.LOGIN) {
            Map map = new HashMap();
//            SelectType = 3;
            mPresenter.chiefDetail(map);
//            mPresenter.getWechatAccessToken();
        }
    }

    private boolean Check() {
        if (SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
    }


    String lag;
    String lon;

    /**
     * 查看订单
     */
    @Override
    public void onClick(View v) {
//        iv_share(v);
    }

    @OnClick({R.id.iv_share, R.id.re_customer, R.id.textview_coupons, R.id.iv_head, R.id.image_arrow_right, R.id.tv_userName,
            R.id.re_announce, R.id.re_safety, R.id.iv_setting, R.id.re_address, R.id.tv_order,
            R.id.ll_daifu, R.id.ll_daifa, R.id.ll_daishou, R.id.ll_ok, R.id.ll_after,
            R.id.tv_getfreebook, R.id.tv_check, R.id.tv_review, R.id.tv_teacher_recognize,
            R.id.textview_beans, R.id.textview_money
    })
    void iv_share(View view) {

        if (Check()) {
            ToastUitl.showLong(mActivity, "请先登录");
            startActivity(LoginNewActivity.class);
            return;
        }

        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_getfreebook://免费领取样书
                if (!isteacherrecognize) { //没有认证教师
                    showteacherrecognizedialog();
                    return;
                }
                if (!isbindshop) { //团长没有绑定shopid
                    showunbindshopdialog();
                    return;
                }
                Intent intent = new Intent(getActivity(), TeacherFreeBookActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_check:
                if (!isteacherrecognize) { //没有认证教师
                    showteacherrecognizedialog();
                    return;
                }
                if (!isbindshop) { //团长没有绑定shopid
                    showunbindshopdialog();
                    return;
                }
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), CheckActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_review:
                Intent reviewIntent = new Intent();
                reviewIntent.setClass(getActivity(), TeacherCheckActivity.class);
                startActivity(reviewIntent);
                break;
            case R.id.tv_teacher_recognize:
                recognize();
                break;
            case R.id.iv_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                //要分享的文本内容，选择某项后会直接把这段文本发送出去，相当于调用选中的应用的接口，并传参
                shareIntent.putExtra(Intent.EXTRA_TEXT, "这里有个APP买教辅很方便，你试用下吧！https://www.aifubook.com/download.html");
                //需要使用Intent.createChooser，这里我们直接复用。第二个参数并不会显示出来
//                shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
                startActivity(shareIntent);
                break;
            case R.id.image_arrow_right:
            case R.id.iv_head:
                startActivity(PersonalUpdataActivity.class);
                break;
            case R.id.re_customer://online customer
                AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.dialog_affirm_btn) {
                            PermissionX.init(getActivity()).permissions(Manifest.permission.CALL_PHONE).request(new RequestCallback() {
                                @Override
                                public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                    if (allGranted) {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:057188198813");
                                        intent.setData(data);
                                        startActivity(intent);
                                        affirmMessageDialog.dismiss();
                                    } else {
                                        ToastUtil.showToast("开启电话权限才能拨打客服热线", false);
                                    }
                                }
                            });
                        }
                    }
                });
                affirmMessageDialog.show("是否拨打客服热线？");
                break;
            case R.id.re_announce:
                startActivity(ServiceActivity.class);
                break;
            case R.id.re_safety://安全中心
                startActivity(AcountMangerActivity.class);
                break;
            case R.id.iv_setting:
                startActivity(SettingActivity.class);
                break;
//            case R.id.tv_redemption:
//                startActivity(CouponRedemptionActivity.class);
//                break;
            case R.id.tv_shop:
//                MainActivity.mActivity.cutHome();
                ((MainActivity) getActivity()).cutHome();
                break;
            case R.id.tv_order:
                bundle.putInt("type", 0);
                startActivity(ActivityAllOrdersList.class, bundle);
                break;
            case R.id.ll_daifu:
                bundle.putInt("type", 1);
                startActivity(ActivityAllOrdersList.class, bundle);
                break;
            case R.id.ll_daifa:
                bundle.putInt("type", 2);
                startActivity(ActivityAllOrdersList.class, bundle);
                break;
            case R.id.ll_daishou:
                bundle.putInt("type", 3);
                startActivity(ActivityAllOrdersList.class, bundle);
                break;
            case R.id.ll_ok:
                bundle.putInt("type", 4);
                startActivity(ActivityAllOrdersList.class, bundle);
                break;
            case R.id.ll_after:
                bundle.putInt("type", 6);
//                startActivity(MyOrderListActivity.class, bundle);
                startActivity(MyServiceActivity.class, bundle);
                break;
            case R.id.re_address://my address
                bundle.putString("typeSelect", "2");
                startActivity(AddressListActivity.class, bundle);
                break;
            case R.id.textview_coupons:
                startActivity(CouponsUnusedActivity.class);
                break;
            case R.id.textview_beans:
                startActivity(BeansActivity.class);
                break;
            case R.id.textview_money:
                startActivity(MoneyActivity.class);
                break;
//            case R.id.myWallet:
//                startActivity(MyWalletActivity.class);
//                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void GetSendImageSuc(String notDataBean) {

    }

    @Override
    public void GetSendImageFail(String Message) {

    }

    String AddressName = "";
    String Address = "";

    @Override
    public void GetShopSuc(ShopBean DataBean) {
        AddressName = DataBean.getName();
        Address = DataBean.getAddress();
        if (DataBean.getLocation().size() > 0) {
            lag = DataBean.getLocation().get(0) + "";
            lon = DataBean.getLocation().get(1) + "";
        }
//        tv_storeAddress.setText(DataBean.getAddress());
//        tv_storeName.setText(DataBean.getName());
//        tv_storeDistance.setText("距离您" + 0.3125 + "米");
//        GlideImageLoader.create(iv_storeHead).load(ApiService.IMAGE + DataBean.getLogo(), new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));

    }

    @Override
    public void GetShopFail(String Message) {

    }

    private MemberInfoBean memberInfoBean;

    @Override
    public void MemberInfoSuc(MemberInfoBean DataBean) {
        memberInfoBean = DataBean;
        if (DataBean.getGrade() != null) {
            if (!DataBean.getGrade().isEmpty() && !DataBean.getGrade().equals("未填写")) {
                tv_class.setText(DataBean.getGrade());
                String oldgrade = SharedPreferencesUtil.get(getActivity(), ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade());
                if (!oldgrade.equals(DataBean.getGrade())) { //新拿到的和本地存储不同
                    SharedPreferencesUtil.put(MyApp.getInstance(), DataBean.getGrade(), ShareKey.Companion.getDefautGrade());
                    com.aifubook.book.fragment.MessageEvent event = new com.aifubook.book.fragment.MessageEvent();
                    event.grade = DataBean.getGrade();
                    EventBus.getDefault().post(event);
                }
            }
        }
        if (DataBean.getLogo()!=null) {
            SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_LOGO, "" + DataBean.getLogo());
        }
        if (DataBean.getRegisteredIdentity() == 0) { //0家长 1教师
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "家长");
            textview_teacher_or_student.setText("家长");
            teacher_recognize_confirm.setVisibility(View.GONE);
            re_teacher.setVisibility(View.GONE);
        } else {
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "教师");
            textview_teacher_or_student.setText("教师");
            teacher_recognize_confirm.setVisibility(View.VISIBLE);
            re_teacher.setVisibility(View.VISIBLE);
        }
        String mobile = DataBean.getMobile();
        if (!StringUtils.isEmpty(mobile)) {
            SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.PHONE, "" + DataBean.getMobile());
        }
        //shopid > 0 判断教师是否认证
        tv_userName.setText(DataBean.getNickname() == null ? "未设置用户昵称" : DataBean.getNickname());
        SharedPreferencesUtil.put(mActivity, "isBindWeChat", DataBean.getIsBindWeChat());
        SharedPreferencesUtil.put(mActivity, "email", "" + DataBean.getEmail());
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head); //圆角
        String logo = DataBean.getLogo();
        if (!StringUtils.isEmpty(logo) && !logo.contains("http")) {
            logo = ApiService.IMAGE + DataBean.getLogo();
        }
        Glide.with(mActivity)
                .load(logo)
                .apply(options)
                .into(iv_head);
        //chief
        ChiefDetailsBean chiefDetailsBean = DataBean.getChiefsVO();
        if (chiefDetailsBean != null) {//0 :申请中 1 :店铺审核通过 2 :审核拒绝
            if (chiefDetailsBean.getStatus().equals("1")) {
                teacher_recognize_confirm.setText("已认证");
                re_recognize.setClickable(false);
//                re_teacher.setVisibility(View.VISIBLE);
                isteacherrecognize = true;
                wasGroup = true;
                SharedPreferencesUtil.put(mActivity, "INVICODE", "" + chiefDetailsBean.getInviteCode());
                SharedPreferencesUtil.put(mActivity, "GROUPSTATUS", "" + chiefDetailsBean.getStatus());
            } else {
                isteacherrecognize = false;
//                re_teacher.setVisibility(View.GONE);
                teacher_recognize_confirm.setText("未认证");
                re_recognize.setClickable(true);
            }
            if (chiefDetailsBean.getShopId() != null) {
                isbindshop = true;
            } else {
                isbindshop = false;
            }
        } else {
            isteacherrecognize = false;
            isbindshop = false;
        }
    }

    private void showteacherrecognizedialog() {
        new IosDialog(getContext()).builder().setGone()
                .setTitle("提示").setMsg("您还未完成教师认证，是否去认证")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    recognize();
                }).setCancelable(true).show();
    }

    private void showunbindshopdialog() {
        new IosDialog(getContext()).builder().setGone()
                .setTitle("提示").setMsg("您还绑定店铺，是否去绑定店铺")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    startActivity(new Intent(getContext(), GroupHeaderActivity.class));
                }).setCancelable(true).show();
    }

    //TODO
    private void recognize() {
        if (Check()) {
            ToastUitl.showLong(mActivity, "请先登录");
            startActivity(LoginNewActivity.class);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(IntentKey.Companion.getINVITECODE(), ApiService.shopinvitecode);
        intent.setClass(
                getContext(),
                TeacherWebviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void appliedShop(ShopBean DataBean) {
        Bundle bundle = new Bundle();
        if (DataBean != null) {//0 :申请中 1 :店铺审核通过 2 :审核拒绝
            if (DataBean.getStatus().equals("1")) {
                bundle.putString("type", "1");
            } else if (DataBean.getStatus().equals("0")) {//申请中
                bundle.putString("type", "0");
            } else if (DataBean.getStatus().equals("2")) {//审核拒绝
                bundle.putString("type", "2");
                ToastUitl.showShort(mActivity, "申请遭拒绝");
            }
        } else {
            bundle.putString("type", "3");
        }
        startActivity(ShopRequestActivity.class, bundle);
    }

    @Override
    public void UpdateMemberInfoSuc(String DataBean) { //修改个人信息
//        Map map = new HashMap();
//        mPresenter.memberInfo(map);
    }

    @Override
    public void GetCanUsedBalanceSuc(String DataBean) {

    }

    @Override
    public void GetAccountInfoSuc(GetAccountInfoBean _data) {
        textview_beans.setText("粉豆 " + _data.getCommission());
        textview_money.setText("零钱 " + _data.getBalance() / 100.00);
    }

    @Override
    public void HasPayPasswordSuc(String DataBean) {

    }

    @Override
    public void SetPayPasswordSuc(String DataBean) {

    }

    @Override
    public void getOrderCountResult(OrderCountVO orderCountVO) {
        LogUtil.e(TAG, "orderCountVO=" + orderCountVO.toString());
        int deliveryCount = orderCountVO.getDeliveryCount();
        int payCount = orderCountVO.getPayCount();
        int serviceCount = orderCountVO.getServiceCount();
        int waitCount = orderCountVO.getWaitCount();
        int waitingcount = orderCountVO.getWaitingCount();//获取自提订单数量

        tv_waitCount.setText(waitCount < 100 ? waitCount + "" : "...");
        tv_payCount.setText(payCount < 100 ? payCount + "" : "...");
        tv_deliveryCount.setText(deliveryCount < 100 ? deliveryCount + "" : "...");
        tv_serviceCount.setText(serviceCount < 100 ? serviceCount + " " : "...");
        tv_count_ziti.setText(waitingcount < 100 ? waitingcount + " " : "...");
        tv_deliveryCount.setVisibility(deliveryCount > 0 ? View.VISIBLE : View.INVISIBLE);
        tv_payCount.setVisibility(payCount > 0 ? View.VISIBLE : View.INVISIBLE);
        tv_serviceCount.setVisibility(serviceCount > 0 ? View.VISIBLE : View.INVISIBLE);
        tv_count_ziti.setVisibility(waitingcount>0?View.VISIBLE:View.INVISIBLE);
        tv_waitCount.setVisibility(waitCount > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void getOrderCountError(String msg) {

    }


    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mActivity, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetDataSuc(ProductListBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }


    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
