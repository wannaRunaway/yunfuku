package com.aifubook.book.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.activity.main.shopdetail.ShopDetailListActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.home.shop.ShopDetailsActivity;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.type.TypePresenter;
import com.aifubook.book.type.TypeView;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.glide.GlideImageLoader;
import com.jiarui.base.ms.MyCsBannerViewPager;
import com.jiarui.base.utils.LogUtil;
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
import butterknife.BindView;

public class FoundFragment extends BaseFragment<TypePresenter> implements TypeView {
    private static final String TAG = "FoundFragment";
    @BindView(R.id.typeRightRecycle)
    RecyclerView typeRightRecycle;

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.banner_view)
    View bannerView;

//    @BindView(R.id.tv_search)
//    TextView tv_search;

    @BindView(R.id.ll_view)
    View ll_view;

    BaseRecyclerAdapter<TypeBean> typeBeanBaseRecyclerAdapter;
    List<TypeBean> TypeBean = new ArrayList<>();

    BaseRecyclerAdapter<TypeBean.ChildrenBean> childrenBeanBaseRecyclerAdapter;
    List<TypeBean.ChildrenBean> childrenBeanArrayList = new ArrayList<>();

    private String shopId;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_found;
    }

    @Override
    public void initPresenter() {
        mPresenter = new TypePresenter(this);
    }


    @Override
    protected void initView() {

        imageRecyclerView();
        initRighRecycler();

//        view_tollbar_lr_tou.setVisibility(View.GONE);
//        imageInit();
//        initViewPager();
//        categoryAll();
//        HomePageImg();
//        tv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SearchActivity.class);
//            }
//        });

        selectIndex=0;
        categoryAll();
        HomePageImg();
    }


    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.TAB_UPDATE){
            String msg_content = event.getMsg_content();
            if("1".equals(msg_content)){
                selectIndex=0;
                categoryAll();
                HomePageImg();
            }

        }
    }

    // 获取首页轮播图
    private void HomePageImg() {
        Map<String, Object> map = new HashMap<>();
        map.put("scene", 2);
        mPresenter.getByScene(map);
    }


    // 获取所有类型
    private void categoryAll() {
        Map<String, String> map = new HashMap<>();
        shopId = SharedPreferencesUtil.get(getActivity(),"SHOP_ID","");
        map.put("shopId",shopId);
        mPresenter.categoryAll(map);
    }


    @Override
    public void GetDataSuc(List<TypeBean> DataBean) {
        TypeBean.clear();
        TypeBean.addAll(DataBean);
        if (TypeBean.size() > 0){ //默认选择第一个
            grade = TypeBean.get(0).getName();
        }
        childrenBeanArrayList.clear();
        childrenBeanArrayList.addAll(DataBean.get(0).getChildren());
        typeBeanBaseRecyclerAdapter.notifyDataSetChanged();
        childrenBeanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetNearShop(NearShopBean DataBean) {

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
//        mMZBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//            @Override
//            public BannerViewHolder createViewHolder() {
//                return new BannerViewHolder();
//            }
//        });
//        mMZBanner.start();

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
                            intent.setClass(getActivity(), ProductDetailsActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        } else if (data.getLinkType() == 2) {
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent();

                            bundle.putString("inType", 0 + "");
                            bundle.putString("shopId", data.getValue());
                            intent.setClass(getActivity(), ShopDetailsActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }

                    }
                });
            }
            bannerViewPager.setAspectRatio(2.5f);
            bannerViewPager.show(bannerList);
        }



    }

 /*   public static class BannerViewHolder implements MZViewHolder<SceneBean.ItemsDTO> {
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
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", "" + data.getValue());
                        Intent intent = new Intent();
                        intent.setClass(context, ProductDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (data.getLinkType() == 2) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent();

                        bundle.putString("inType", 0 + "");
                        bundle.putString("shopId", data.getValue());
                        intent.setClass(context, ShopDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }*/


    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetProSuc(ProductListBean DataBean) {

    }

    @Override
    public void GetProSucs(ProductListBean DataBean) {

    }

    @Override
    public void GetProFail(String Message) {

    }

    @Override
    public void GetShopSuc(ShopBean DataBean) {

    }

    @Override
    public void GetShopFail(String Message) {

    }

    @Override
    public void ShopCouponsSuc(List<MyCouponsBean> DataBean) {

    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void getChiefResult() {

    }

    @Override
    public void showChiefResultError(String msg) {

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
        childrenBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean>(mActivity, childrenBeanArrayList, R.layout.type_right_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, TypeBean.ChildrenBean item, int position, boolean isScrolling) {
                holder.setText(R.id.secName, item.getName());
                RecyclerView recyclerView = holder.getView(R.id.right_down_recycle);
                initSendRecycler(recyclerView, item.getChildren());
            }
        };
        typeRightRecycle.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        typeRightRecycle.setAdapter(childrenBeanBaseRecyclerAdapter);
    }

    private void initSendRecycler(RecyclerView recyclerView, List<TypeBean.ChildrenBean.ChildrenBeans> mItem) {
        BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans> childrenBeansBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean.ChildrenBean.ChildrenBeans>(mActivity, mItem, R.layout.type_right_item_down) {
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
                        bundle.putString(IntentKey.Companion.getCATEGORYID(), item.getId() + "");
                        bundle.putString(IntentKey.Companion.getFROMWHERE(), IntentKey.Companion.getFROMCLASSFICATION());
                        bundle.putString(IntentKey.Companion.getGRADE(), grade);
                        bundle.putSerializable(IntentKey.Companion.getCLASSFICATION(), "");
                        shopId = SharedPreferencesUtil.get(getActivity(), ShareKey.Companion.getSHOPID(), "");
                        bundle.putString(IntentKey.Companion.getSHOPID(), shopId);
                        startActivity(ShopDetailListActivity.class, bundle);
//                        startActivity(ShopDetailsActivity.class, bundle);
                    }
                });

             /*   holder.getView(R.id.threeImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("inType", 1 + "");
                        bundle.putString("categoryId", item.getId() + "");
                        startActivity(ShopDetailsActivity.class, bundle);

                    }
                });*/

            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        recyclerView.setAdapter(childrenBeansBaseRecyclerAdapter);
    }


    int selectIndex = 0;
    private String grade = "";

    private void imageRecyclerView() {
        typeBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<TypeBean>(mActivity, TypeBean, R.layout.type_left_item) {
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
                        grade = item.getName();
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
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(typeBeanBaseRecyclerAdapter);
    }
//
//    /***************************************************************************************tablayout*********************************************************************************/
//
//    private void initViewPager() {
//        titles.add("精选");
//        titles.add("最IN趋势");
//        titles.add("即看即玩");
//        titles.add("行无止境");
//        fragmentList.add(new FoundItemFragment(1));
//        fragmentList.add(new FoundItemFragment(2));
//        fragmentList.add(new FoundItemFragment(3));
//        fragmentList.add(new FoundItemFragment(4));
//        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), fragmentList, titles));
//        tabLayout.setViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(titles.size());
//        viewPager.setCurrentItem(0, false);
//    }
}
