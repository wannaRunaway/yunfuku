package com.aifubook.book.shop;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseActivity;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ShopPicActivity extends BaseActivity {

    private ViewPager viewPager;
    private  ArrayList<String> picList;
    private ArrayList<View> viewLists = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_shop_pic;
    }

    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
        viewPager = findViewById(R.id.viewpager);

        int  pos = getIntent().getExtras().getInt("pos");
        picList =getIntent().getExtras().getStringArrayList("pics");

        if(picList!=null && picList.size()>0) {
            for (int i=0;i<picList.size();i++){
                View view = mInflater.inflate(R.layout.item_pic,null);
                viewLists.add(view);
            }

            viewPager.setAdapter(new MyAdapter());
            viewPager.setCurrentItem(pos);
        }

    }

//    List<String> picList = new ArrayList<>();

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
            return view==object;
        }

        @NonNull
        @NotNull
        @Override
        public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
            View view = viewLists.get(position);
            CommonImage image = view.findViewById(R.id.iv_book);
            String pic = picList.get(position);
            if (!StringUtils.isEmpty(pic)) {
                image.setImageURI(ApiService.IMAGE+pic);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
            View view = viewLists.get(position);
            container.removeView(view);
        }
    }
}
