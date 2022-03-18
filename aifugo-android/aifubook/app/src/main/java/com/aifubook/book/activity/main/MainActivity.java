package com.aifubook.book.activity.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.mainfragment.MainFragment;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.bean.UpdateBean;
import com.aifubook.book.category.CategoryFragment;
import com.aifubook.book.fragment.FoundFragment;
import com.aifubook.book.fragment.MineFragment;
import com.aifubook.book.fragment.ShopCartFragment;
import com.aifubook.book.utils.CheckUpdateUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.status.FlymeOSStatusBarFontUtils;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, View.OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * shopid 2个存储的地方：获取门店接口、切换门店
     * */
    public String shopid;
    public ArrayList<TypeBean> mainTypeBeanList = new ArrayList<>();
    public ArrayList<TypeBean.ChildrenBean> subjectlist = new  ArrayList<TypeBean.ChildrenBean>();
    public ArrayList<TypeBean.ChildrenBean.ChildrenBeans> categorylist = new ArrayList<TypeBean.ChildrenBean.ChildrenBeans>();
    public String grade = "一年级";

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void cutHome() {
        showmain();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.SEND_CART) {
            showcart();
//            if (mTabHost != null) {
//                mTabHost.setCurrentTab(2);
//            }
        } else if (event.getMsg_type() == MessageEvent.ORDER_ADD_CAR) {
            showcart();
//            if (mTabHost != null) {
//                mTabHost.setCurrentTab(2);
//            }
        } else if (event.getMsg_type() == MessageEvent.TAB_SELECT) {
            String msg_content = event.getMsg_content();
            if (!StringUtils.isEmpty(msg_content)) {
                showmain();
//                if (mTabHost != null) {
//                    mTabHost.setCurrentTab(Integer.parseInt(msg_content));
//                }
            }
        } else if (event.getMsg_type() == MessageEvent.BACK_MAIN) {
            showmain();
//            if (mTabHost != null) {
//                mTabHost.setCurrentTab(0);
//            }
        }
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    //    private int tabIndex;
    @Override
    public void initView() {
        mPresenter.getUpdate();
        findviews();
        int position = getIntent().getIntExtra("main", 0);
        grade = getIntent().getStringExtra(IntentKey.Companion.getGRADE());
        if (grade==null){
            grade = SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade());
        }
        if (position == 0) {
            initFragments0();
        }else if (position == 3){
            initFragments3();
        }
        FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this, true);
    }

    private TextView tv_main, tv_classfication, tv_cart, tv_mine;
    private ImageView im_main, im_classfication, im_cart, im_mine;
    private LinearLayout line_main, line_classfication, line_cart, line_mine;
    private TextView[] tv_array;

    private void findviews() {
        tv_main = findViewById(R.id.tv_main);
        tv_classfication = findViewById(R.id.tv_classfication);
        tv_cart = findViewById(R.id.tv_cart);
        tv_mine = findViewById(R.id.tv_mine);
        im_main = findViewById(R.id.imageview_main);
        im_classfication = findViewById(R.id.imageview_classfication);
        im_cart = findViewById(R.id.imageview_cart);
        im_mine = findViewById(R.id.imageview_mine);
        line_main = findViewById(R.id.line_main);
        line_classfication = findViewById(R.id.line_classfication);
        line_cart = findViewById(R.id.line_cart);
        line_mine = findViewById(R.id.line_mine);
        tv_array = new TextView[]{tv_main, tv_classfication, tv_cart, tv_mine};
        line_main.setOnClickListener(this);
        line_classfication.setOnClickListener(this);
        line_cart.setOnClickListener(this);
        line_mine.setOnClickListener(this);
    }

    private String graycolor = "#949494";
    private String redcolor = "#F85537";

    private void clearbottombar() {
        for (TextView textView : tv_array) {
            textView.setTextColor(Color.parseColor(graycolor));
        }
        im_main.setSelected(false);
        im_classfication.setSelected(false);
        im_cart.setSelected(false);
        im_mine.setSelected(false);
    }

    public void typelistrefresh(){
        if (categoryfragment != null){
            categoryfragment.typelistOrshopidReceived();
        }
    }

    public void shopidloadsuccess(){
        if (categoryfragment != null){
            categoryfragment.mainshopidreceivedSuccess();
        }
    }

    private void selectmain() {
        clearbottombar();
        im_main.setSelected(true);
        tv_main.setTextColor(Color.parseColor(redcolor));
    }

    private void selectclassfication() {
        clearbottombar();
        im_classfication.setSelected(true);
        tv_classfication.setTextColor(Color.parseColor(redcolor));
    }

    private void selectcart() {
        clearbottombar();
        im_cart.setSelected(true);
        tv_cart.setTextColor(Color.parseColor(redcolor));
    }

    private void selectmine() {
        clearbottombar();
        im_mine.setSelected(true);
        tv_mine.setTextColor(Color.parseColor(redcolor));
    }

    private Fragment mainfragment, cartfragment, minefragment;
    private CategoryFragment categoryfragment;
    private FragmentTransaction fragmentTransaction;

    private void initFragments0() {
        mainfragment = new MainFragment();
        categoryfragment = new CategoryFragment();
        cartfragment = new ShopCartFragment();
        minefragment = new MineFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, mainfragment).add(R.id.content, categoryfragment).add(R.id.content, cartfragment).add(R.id.content, minefragment)
                .hide(categoryfragment).hide(cartfragment).hide(minefragment).show(mainfragment).commit();
        selectmain();
    }

    private void initFragments3() {
        mainfragment = new MainFragment();
        categoryfragment = new CategoryFragment();
        cartfragment = new ShopCartFragment();
        minefragment = new MineFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, mainfragment).add(R.id.content, categoryfragment).add(R.id.content, cartfragment).add(R.id.content, minefragment)
                .hide(categoryfragment).hide(cartfragment).hide(mainfragment).show(minefragment).commit();
        selectmain();
    }

    private void showmain() {
        selectmain();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mainfragment == null) {
            mainfragment = new MainFragment();
        }
        if (!mainfragment.isAdded()) {
            transaction.add(R.id.content, mainfragment);
        }
        transaction.hide(categoryfragment).hide(cartfragment).hide(minefragment).show(mainfragment).commit();
    }

    private void showclassfication() {
        selectclassfication();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (categoryfragment == null) {
            categoryfragment = new CategoryFragment();
        }
        if (!categoryfragment.isAdded()) {
            transaction.add(R.id.content, categoryfragment);
        }
        transaction.hide(mainfragment).hide(cartfragment).hide(minefragment).show(categoryfragment).commit();
    }

    private void showcart() {
        selectcart();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (cartfragment == null) {
            cartfragment = new ShopCartFragment();
        }
        if (!cartfragment.isAdded()) {
            transaction.add(R.id.content, cartfragment);
        }
        transaction.hide(mainfragment).hide(categoryfragment).hide(minefragment).show(cartfragment).commit();
    }

    private void showmine() {
        selectmine();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (minefragment == null) {
            minefragment = new MineFragment();
        }
        if (!minefragment.isAdded()) {
            transaction.add(R.id.content, minefragment);
        }
        transaction.hide(mainfragment).hide(categoryfragment).hide(cartfragment).show(minefragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainfragment != null && !mainfragment.isHidden()){ //activity显示在当前fragment 跳转其他界面
            ((MainFragment)mainfragment).loadbannerlistsorteveryonebuys();
        }
        if (categoryfragment !=null && !categoryfragment.isHidden()){

        }
        if (cartfragment !=null && !cartfragment.isHidden()){
            ((ShopCartFragment)cartfragment).onrefreshandloadshop();
        }
        if (minefragment !=null && !minefragment.isHidden()){
            ((MineFragment)minefragment).onrefreshandloadmine();
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    // 设置双按返回键退出
    private static boolean isExit = false;

    // 设置一个handler线程来执行程序
    protected MyHandler mMyHandler;

    // 调用方法设置双按退出程序
    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUitl.showShort(this, "再按一次退出程序");
            mMyHandler = new MyHandler();
            mMyHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }


    @Override
    public void updateResult(UpdateBean updateBean) {
        LogUtil.e(TAG, "updateBean=" + updateBean.toString());
        CheckUpdateUtil updateUtil = new CheckUpdateUtil();
        updateUtil.setUpdate(MainActivity.this, updateBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_main:
                showmain();
                break;
            case R.id.line_classfication:
                showclassfication();
                break;
            case R.id.line_cart:
                showcart();
                break;
            case R.id.line_mine:
                showmine();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }


    @SuppressLint("HandlerLeak")
    protected class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}