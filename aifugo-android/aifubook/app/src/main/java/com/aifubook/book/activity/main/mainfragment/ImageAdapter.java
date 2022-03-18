package com.aifubook.book.activity.main.mainfragment;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.SceneBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class ImageAdapter extends BannerAdapter<SceneBean.ItemsDTO, ImageAdapter.BannerViewHolder> {

    private Context context;
    private MainFragmentInter itemsDTO;
    public ImageAdapter(List<SceneBean.ItemsDTO> mDatas, Context context, MainFragmentInter mainFragmentInter) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.context = context;
        this.itemsDTO = mainFragmentInter;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setBackground(context.getDrawable(R.drawable.shape_retange_16_white));
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, SceneBean.ItemsDTO data, int position, int size) {
//        holder.imageView.setImageResource(R.mipmap.imageview_groupbug);
        Glide.with(context).load(ApiService.IMAGE+data.getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v -> itemsDTO.bannerClick(data));
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}

