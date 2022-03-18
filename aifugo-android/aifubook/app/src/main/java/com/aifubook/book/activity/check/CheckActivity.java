package com.aifubook.book.activity.check;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.TeacherFreeBooksList.TeacherFreeBooksListActivity;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.ms.MyCsBannerViewPager;
import com.jiarui.base.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CheckActivity extends BaseActivity<CheckPresent> implements CheckView {

    //    private static final String TAG = "FoundFragment";
    @BindView(R.id.typeRightRecycle)
    RecyclerView typeRightRecycle;

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.banner_view)
    View bannerView;

    @BindView(R.id.ll_view)
    View ll_view;

    BaseRecyclerAdapter<com.aifubook.book.bean.TypeBean> typeBeanBaseRecyclerAdapter;
    List<TypeBean> TypeBean = new ArrayList<>();

    BaseRecyclerAdapter<TypeBean.ChildrenBean> childrenBeanBaseRecyclerAdapter;
    List<TypeBean.ChildrenBean> childrenBeanArrayList = new ArrayList<>();

    private String shopId;

    @Override
    public void initPresenter() {
        mPresenter = new CheckPresent(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    public void initView() {
        selectIndex=0;
        imageRecyclerView();
        initRighRecycler();
        categoryAll();
        HomePageImg();
    }


//    @Override
//    public void onMessageEvent(MessageEvent event) {
//        super.onMessageEvent(event);
//        if(event.getMsg_type()==MessageEvent.TAB_UPDATE){
//            String msg_content = event.getMsg_content();
//            if("1".equals(msg_content)){
//                selectIndex=0;
//                categoryAll();
//                HomePageImg();
//            }
//
//        }
//    }


    // 获取首页轮播图
    private void HomePageImg() {
        Map<String, Object> map = new HashMap<>();
        map.put("scene", 2);
        mPresenter.getByScene(map);
    }


    // 获取所有类型
    private void categoryAll() {
        Map<String, String> map = new HashMap<>();
        shopId = SharedPreferencesUtil.get(this,"SHOP_ID","");
        map.put("shopId",shopId);
        mPresenter.categoryAll(map);
    }


    @Override
    public void GetDataSuc(List<TypeBean> DataBean) {
        TypeBean.clear();
        TypeBean.addAll(DataBean);
        childrenBeanArrayList.clear();
        childrenBeanArrayList.addAll(DataBean.get(0).getChildren());
        typeBeanBaseRecyclerAdapter.notifyDataSetChanged();
        childrenBeanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    List<SceneBean.ItemsDTO> list = new ArrayList<>();
    ArrayList<String> bannerList = new ArrayList<>();
    private MyCsBannerViewPager bannerViewPager;

    @Override
    public void GetHomePage(SceneBean DataBean) {
        List<SceneBean.ItemsDTO> picList = DataBean.getItems();
        if (picList == null) {
            bannerView.setVisibility(View.GONE);
            return;
        }
        if (picList.size() == 0) {
            bannerView.setVisibility(View.GONE);
            return;
        }
        bannerView.setVisibility(View.VISIBLE);
        list.clear();
        list.addAll(DataBean.getItems());
        LogUtil.e("http", "WhatMessage" + DataBean.getItems().size());
        bannerList.clear();
        for (int i = 0; i < list.size(); i++) {
            String image = list.get(i).getImage();
            bannerList.add(ApiService.IMAGE + image);

        }

        if (bannerList.size() > 0) {
            if (null == bannerViewPager) {
                bannerViewPager = new MyCsBannerViewPager(ll_view, new MyCsBannerViewPager.BannerOnItemClickListener() {

                    @Override
                    public void onListener(int position) {
                        SceneBean.ItemsDTO data = list.get(position);
                        if (data.getLinkType() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("productId", "" + data.getValue());
                            Intent intent = new Intent();
                            intent.setClass(CheckActivity.this, TeacherFreeBooksListActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (data.getLinkType() == 2) {
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent();
                            bundle.putString("inType", 0 + "");
                            bundle.putString("shopId", data.getValue());
                            intent.setClass(CheckActivity.this, TeacherFreeBooksListActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
            }
            bannerViewPager.setAspectRatio(2.5f);
            bannerViewPager.show(bannerList);
        }



    }


    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

//    /***************************************************************************************热门景点*********************************************************************************/
//

    private void initRighRecycler() {
        childrenBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean>(this, childrenBeanArrayList, R.layout.type_right_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean.ChildrenBean item, int position, boolean isScrolling) {
                holder.setText(R.id.secName, item.getName());
                RecyclerView recyclerView = holder.getView(R.id.right_down_recycle);
                initSendRecycler(recyclerView, item.getChildren());
            }
        };
        typeRightRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        typeRightRecycle.setAdapter(childrenBeanBaseRecyclerAdapter);
    }

    private void initSendRecycler(RecyclerView recyclerView, List<TypeBean.ChildrenBean.ChildrenBeans> mItem) {
        BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans> childrenBeansBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans>(this, mItem, R.layout.type_right_item_down) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean.ChildrenBean.ChildrenBeans item, int position, boolean isScrolling) {
//                holder.setImageByUrl(R.id.threeImg, ApiService.IMAGE + item.getLogo());
                holder.setText(R.id.threeName, item.getName());
                holder.setImageFresco(R.id.image,ApiService.IMAGE + item.getLogo());
                holder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("inType", 1 + "");
                        bundle.putString("categoryId", item.getId() + "");
                        shopId = SharedPreferencesUtil.get(CheckActivity.this,"SHOP_ID","");
                        bundle.putString("shopId",shopId);
                        startActivity(TeacherFreeBooksListActivity.class, bundle);
                    }
                });

            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(childrenBeansBaseRecyclerAdapter);
    }

    int selectIndex = 0;
    private void imageRecyclerView() {
        typeBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean>(this, TypeBean, R.layout.type_left_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean item, int position, boolean isScrolling) {
                holder.setText(R.id.mName, item.getName());

                TextView inLine = holder.getView(R.id.inLine);
                TextView mName = holder.getView(R.id.mName);

                mName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectIndex = position;
                        typeBeanBaseRecyclerAdapter.notifyDataSetChanged();
                        childrenBeanArrayList.clear();
                        if(item.getChildren()!=null) {
                            childrenBeanArrayList.addAll(item.getChildren());
                        }
                        childrenBeanBaseRecyclerAdapter.notifyDataSetChanged();
                    }
                });

                if (selectIndex == position) {
                    inLine.setBackground(getResources().getDrawable(R.color.F95435));
                    mName.setTextColor(getResources().getColor(R.color.F95435));
                } else {
                    inLine.setBackground(getResources().getDrawable(R.color.view_color_F8F8F8));
                    mName.setTextColor(getResources().getColor(R.color.black_three));
                }


            }
        };
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(typeBeanBaseRecyclerAdapter);
    }
}