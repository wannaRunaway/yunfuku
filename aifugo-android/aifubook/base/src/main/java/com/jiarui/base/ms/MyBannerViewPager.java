package com.jiarui.base.ms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.jiarui.base.R;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.ms.banner.Banner;
import com.jiarui.base.ms.banner.Transformer;
import com.jiarui.base.ms.banner.holder.BannerViewHolder;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ScreenUtil;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

/*
 * https://github.com/fccaikai/AutoScrollViewPager
 * https://github.com/wenchaosong/Banner
 * https://github.com/youth5201314/banner
 */
public class MyBannerViewPager {

    private static final String TAG="MyBannerViewPager";
    private View view, viewpager_ll;
    private Context mContext;
    private LinearLayout img_point_viewgroup;
    private CustomViewHolder customViewHolder;
    private Banner banner;

    public MyBannerViewPager(View view) {
        this.view = view;
        mContext = view.getContext();
        viewpager_ll = view.findViewById(R.id.viewpager_ll);
        banner = view.findViewById(R.id.banner);
        img_point_viewgroup = (LinearLayout) view.findViewById(R.id.img_point_viewgroup);
    }

    private BannerOnItemClickListener listener;

    public MyBannerViewPager(View view, BannerOnItemClickListener listener) {
        this.view = view;
        mContext = view.getContext();
        viewpager_ll = view.findViewById(R.id.viewpager_ll);
        banner = view.findViewById(R.id.banner);
        img_point_viewgroup = (LinearLayout) view.findViewById(R.id.img_point_viewgroup);
        this.listener = listener;
    }

    public Banner getBanner() {

        return banner;
    }

    private float ratio;

    public void setAspectRatio(float ratio) {
        this.ratio=ratio;
        banner.setAspectRatio(ratio);
    }

    private ArrayList<String> list;


    public void show(ArrayList<String> list) {
        this.list = list;
        if (null == list || list.size() <= 0) {
            viewpager_ll.setVisibility(View.GONE);
            return;
        }
        viewpager_ll.setVisibility(View.VISIBLE);
        if (list.size() <= 1) {
            img_point_viewgroup.setVisibility(View.INVISIBLE);
        }

        initPointView();
        customViewHolder = new CustomViewHolder(mContext, list, listener);
        // banner.setPages(list, customViewHolder);
        banner.setPages(list, customViewHolder)
                .setBannerAnimation(Transformer.Scale)
                .start();
        setOnPageChangeListener();
        // indicator.setAdapterAndViewPager(myAdapter, view_pager);

    }

    public void show2(ArrayList<String> list) {
        this.list = list;
        if (null == list || list.size() <= 0) {
            viewpager_ll.setVisibility(View.GONE);
            return;
        }
        viewpager_ll.setVisibility(View.VISIBLE);
        if (list.size() <= 1) {
            img_point_viewgroup.setVisibility(View.INVISIBLE);
        }

        initPointView2();
        customViewHolder = new CustomViewHolder(mContext, list, listener);
        // banner.setPages(list, customViewHolder);
        banner.setPagerMargin(0);
        banner.setPages(list, customViewHolder)
                .setBannerAnimation(Transformer.Default)
                .setCurrentPage(0)
                .start();
        setOnPageChangeListener2();
    }


   /* public void updateShow(ArrayList<String> list){
        if(banner!=null){
            banner.update(list);
        }
    }*/

    public interface BannerOnItemClickListener {
        void onListener(int position);
    }

    public int mPosition;

    private void setOnPageChangeListener() {
        if (null == list || list.size() <= 1)
            return;
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                for (int i = 0; i < list.size(); i++) {//
                    ImageView imgview = (ImageView) img_point_viewgroup.getChildAt(i);
                    //   imgview.setBackgroundResource((position == i) ? R.drawable.view_pager_indicator_yellow : R.drawable.view_pager_indicator_selected);
                    imgview.setBackgroundResource((position == i) ? R.drawable.view_pager_indicator_blue : R.drawable.view_pager_indicator_selected);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void setOnPageChangeListener2() {
        if (null == list || list.size() <= 1)
            return;
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                for (int i = 0; i < list.size(); i++) {//
                    ImageView imgview = (ImageView) img_point_viewgroup.getChildAt(i);
                    //   imgview.setBackgroundResource((position == i) ? R.drawable.view_pager_indicator_yellow : R.drawable.view_pager_indicator_selected);
                    imgview.setBackgroundResource((position == i) ? R.drawable.view_pager_indicator_white_select : R.drawable.view_pager_indicator_white);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    private void initPointView2() {
        int w = ScreenUtil.dip2px(mContext, 6.0f);//LinearLayout.LayoutParams.WRAP_CONTENT;
        img_point_viewgroup.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            ImageView imgview = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = w;
            imgview.setLayoutParams(params);
            // imgview.setBackgroundResource(0 == i ? R.drawable.view_pager_indicator_yellow : R.drawable.view_pager_indicator_selected);
            imgview.setBackgroundResource(0 == i ? R.drawable.view_pager_indicator_white_select : R.drawable.view_pager_indicator_white);
            img_point_viewgroup.addView(imgview);
        }
    }


    private void initPointView() {
        int w = ScreenUtil.dip2px(mContext, 6.0f);//LinearLayout.LayoutParams.WRAP_CONTENT;
        img_point_viewgroup.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            ImageView imgview = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = w;
            imgview.setLayoutParams(params);
            // imgview.setBackgroundResource(0 == i ? R.drawable.view_pager_indicator_yellow : R.drawable.view_pager_indicator_selected);
            imgview.setBackgroundResource(0 == i ? R.drawable.view_pager_indicator_blue : R.drawable.view_pager_indicator_selected);
            img_point_viewgroup.addView(imgview);
        }
    }

    private class CustomViewHolder implements BannerViewHolder<String> {

        private CommonImage imageView;

        ArrayList<String> itemList;
        private BannerOnItemClickListener listener;

        public CustomViewHolder(Context context, ArrayList<String> itemList, BannerOnItemClickListener listener) {
            this.itemList = itemList;
            this.listener = listener;
        }

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            imageView = view.findViewById(R.id.imgview);
            if(ratio!=0){
                LogUtil.e(TAG,"ratio="+ratio);
                imageView.setAspectRatio(ratio);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e("image", "ppp");
                    listener.onListener(mPosition);
                }
            });
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            final String url = itemList.get(position);
            imageView.setImageURI(url);
        }
    }

    public void startAutoScroll() {
        if (null != list && list.size() > 1) {
            //开始轮播
            if (banner != null && !banner.isStart() && banner.isPrepare()) {
                banner.startAutoPlay();
            }
        }
    }

    public void stopAutoScroll() {
        if (null != list && list.size() > 1) {
            //开始轮播
            if (banner != null && !banner.isStart() && banner.isPrepare()) {
                banner.stopAutoPlay();
            }
        }
    }


}
