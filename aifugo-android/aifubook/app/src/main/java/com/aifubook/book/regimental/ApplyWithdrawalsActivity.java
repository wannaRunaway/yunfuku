package com.aifubook.book.regimental;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.jiarui.base.utils.StringUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyWithdrawalsActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_withdrawals;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        tv_balance.setText("¥" + getIntent().getStringExtra("balance"));
        if (getIntent().getIntExtra("type", 0) == 1) {//提现
            setTitle("申请提现");
            et_price.setHint("最高可提现" + getIntent().getStringExtra("settlementCommission"));
        } else {//充值
            setTitle("充值");
            et_price.setHint("请输入充值金额");
            tv_text.setText("充值方式");
            tv_submit.setText("充值");
        }
    }

    @OnClick(R.id.tv_submit)
    void tv_submit() {
        if (getIntent().getIntExtra("type", 0) == 1) {//提现
            if (StringUtils.isEmpty(et_price.getText().toString())) {
                Toast.makeText(mContext, "请输入提现金额", Toast.LENGTH_SHORT).show();
                return;
            }
            double price = Double.valueOf(getIntent().getStringExtra("settlementCommission")) - Double.valueOf(et_price.getText().toString());
            if (price < 0) {
                Toast.makeText(mContext, "提现金额超过可提现最大金额", Toast.LENGTH_SHORT).show();
                return;
            }
            Map map = new HashMap();
            map.put("fee", (Double.valueOf(et_price.getText().toString()) * 100) + "");
            mPresenter.commissionApply(map);
        } else {//充值
            if (StringUtils.isEmpty(et_price.getText().toString())) {
                Toast.makeText(mContext, "请输入充值金额", Toast.LENGTH_SHORT).show();
                return;
            }
            Map map = new HashMap();
            map.put("fee", ((int)Double.parseDouble(et_price.getText().toString()) * 100) + "");
            map.put("payType", "5");
            mPresenter.orderCreate(map);
        }
    }

    private IWXAPI iwxapi; //微信支付api

    /**
     * 调起微信支付的方法
     **/
    private void toWXPay(RechargeBean notDataBean) {
        iwxapi = WXAPIFactory.createWXAPI(this, "wx494d5354ef916936"); //初始化微信api
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
    public void ChiefApplySuc(String DataBean) {

    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void ChiefMembersSuc(ChiefMembersBean DataBean) {

    }

    @Override
    public void ChiefMyChiefSuc(ChiefMyChiefBean DataBean) {

    }

    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetListDataSuc(List<FindSchoolClassesBean> DataBean) {

    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {

    }

    @Override
    public void UploadImageSuc(String DataBean) {

    }

    @Override
    public void GetChiefOrderByCodeSuc(ChiefOrderByCodeBean DataBean) {

    }

    @Override
    public void SetFetchedSuc(String DataBean) {

    }

    @Override
    public void OrderListSuc(OrderListBean DataBean) {

    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {

    }

    @Override
    public void CommissionApplySuc(String DataBean) {
        Toast.makeText(mContext, "提现申请已提交", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OrderCreateSuc(RechargeBean DataBean) {
        toWXPay(DataBean);
    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void GetDataSucs(ProductListBean DataBean) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}