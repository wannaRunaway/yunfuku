package com.aifubook.book.regimental;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.fragment.FoundFragment;
import com.aifubook.book.home.shop.ShopDetailsActivity;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.model.ProductCategoryModel;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.type.TypeModel;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.glide.GlideImageLoader;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ToastUtil;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductCategoryActivity extends BaseActivity implements OnCallBack {

    private static final String TAG = "ProductCategoryActivity";
    private static final int category_type = 0x11;
    private static final int scene_type = 0x12;

    @Override
    protected int setContentView() {
        return R.layout.activity_product_category;
    }

    private int zeroBuy;


    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
        zeroBuy =getIntent().getExtras().getInt("zeroBuy");
        LogUtil.e(TAG, "zeroBuy=" + zeroBuy);

        findView();
        initData();


    }

    private ProductCategoryModel model;

    private void initData() {
        leftRecyclerView();
        initRighRecycler();

        model = new ProductCategoryModel(new TypeModel());
        model.setOnCallBackListener(this);

        HomePageImg();
    }

    // 获取首页轮播图
    private void HomePageImg() {
        Map<String, Object> map = new HashMap<>();
        map.put("scene", 2);
        model.getByScene(map,scene_type);
    }

    // 获取所有类型
    private void getCategoryAll() {
        Map<String, String> map = new HashMap<>();
        String shopId = SharedPreferencesUtil.get(this, "SHOP_ID", "");
        map.put("shopId", shopId);
        model.categoryAll(map, category_type);
    }

    private RecyclerView rv_left, rv_right;
    private MZBannerView banner;

    private void findView() {
        rv_left = findViewById(R.id.rv_left);
        rv_right = findViewById(R.id.rv_right);
        banner=findViewById(R.id.banner);

//        mHeadView.setCentreTextView("商品分类");

    }

    @Override
    public void onNext(Object result, int type) {
        if (type == category_type) {
            onCategoryResult(result);
        }else if(type==scene_type){
            getCategoryAll();
            onSceneResult(result);
        }
    }

    List<SceneBean.ItemsDTO> bannerList = new ArrayList<>();

    private void onSceneResult(Object result) {

        SceneBean DataBean= (SceneBean) result;

        List<SceneBean.ItemsDTO> picList = DataBean.getItems();
        if (picList == null) {
            banner.setVisibility(View.GONE);
            return;
        }
        if (picList.size() == 0) {
            banner.setVisibility(View.GONE);
            return;
        }
        banner.setVisibility(View.VISIBLE);
        bannerList.clear();
        bannerList.addAll(DataBean.getItems());
        Log.e("http", "WhatMessage" + DataBean.getItems().size());
        banner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public ProductCategoryActivity.BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.start();

    }

    List<TypeBean> leftList = new ArrayList<>();
    List<TypeBean.ChildrenBean> rightList = new ArrayList<>();

    private void onCategoryResult(Object result) {
        List<TypeBean> list = (List<TypeBean>) result;

        leftList.clear();
        rightList.clear();
        if (list != null && list.size() > 0) {
            leftList.addAll(list);
            List<TypeBean.ChildrenBean> children = list.get(0).getChildren();
            if (children != null && children.size() > 0) {
                rightList.addAll(children);
            }
        }
        typeBeanBaseRecyclerAdapter.notifyDataSetChanged();
        childrenBeanBaseRecyclerAdapter.notifyDataSetChanged();

    }

    BaseRecyclerAdapter<TypeBean> typeBeanBaseRecyclerAdapter;
    int selectIndex = 0;

    private void leftRecyclerView() {
        typeBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean>(mActivity, leftList, R.layout.type_left_item) {
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
                        rightList.clear();
                        if (item.getChildren() != null) {
                            rightList.addAll(item.getChildren());
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
        rv_left.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        rv_left.setAdapter(typeBeanBaseRecyclerAdapter);
    }

    BaseRecyclerAdapter<TypeBean.ChildrenBean> childrenBeanBaseRecyclerAdapter;

    private void initRighRecycler() {
        childrenBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean>(mActivity, rightList, R.layout.type_right_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean.ChildrenBean item, int position, boolean isScrolling) {
                holder.setText(R.id.secName, item.getName());
                RecyclerView recyclerView = holder.getView(R.id.right_down_recycle);
                initSendRecycler(recyclerView, item.getChildren());
            }
        };
        rv_right.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        rv_right.setAdapter(childrenBeanBaseRecyclerAdapter);
    }

    private void initSendRecycler(RecyclerView recyclerView, List<TypeBean.ChildrenBean.ChildrenBeans> mItem) {
        BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans> childrenBeansBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans>(mActivity, mItem, R.layout.type_right_item_down) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean.ChildrenBean.ChildrenBeans item, int position, boolean isScrolling) {
                holder.setText(R.id.threeName, item.getName());
                holder.setImageFresco(R.id.image, ApiService.IMAGE + item.getLogo());
                holder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("inType", 1 + "");
                        bundle.putString("categoryId", item.getId() + "");
                        bundle.putInt("zeroBuy",1);
                        String shopId = SharedPreferencesUtil.get(ProductCategoryActivity.this, "SHOP_ID", "");
                        bundle.putString("shopId", shopId);
                        startActivity(ShopDetailsActivity.class, bundle);
                    }
                });


            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        recyclerView.setAdapter(childrenBeansBaseRecyclerAdapter);
    }

    @Override
    public void onError(String error, int type) {
        ToastUtil.showToast(error, false);
    }

    public static class BannerViewHolder implements MZViewHolder<SceneBean.ItemsDTO> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.mBanner_ImageView);
            return view;
        }

        @Override
        public void onBind(Context context, int position, SceneBean.ItemsDTO data) {
            // 数据绑定

//            Log.e("http", "WhatMessage" + data);


//            mImageView.setImageResource(data);
            GlideImageLoader.create(mImageView).load(ApiService.IMAGE + data.getImage(), new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (data.getLinkType() == 0) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", "" + data.getValue());
                        bundle.putInt("zeroBuy",1);
                        intent.setClass(context, ProductDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (data.getLinkType() == 2) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent();

                        bundle.putString("inType", 0 + "");
                        bundle.putString("shopId", data.getValue());
                        bundle.putInt("zeroBuy",1);
                        intent.setClass(context, ShopDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
