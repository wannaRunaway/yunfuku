package com.aifubook.book.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.fresco.CommonImage;
import com.yalantis.ucrop.PicturePhotoGalleryAdapter;
import com.yalantis.ucrop.model.CutInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeNAdapter extends RecyclerView.Adapter<HomeNAdapter.ViewHolder> {
    private Context context;
    private List<ProductListBean.list> list = new ArrayList<>();
    private LayoutInflater mInflater;

    public HomeNAdapter(Context context, List<ProductListBean.list> list) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void setData(List<ProductListBean.list> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_product_item_new,
                parent, false);
        return new HomeNAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ProductListBean.list item = this.list.get(position);
        holder.mCount.setText("销量" + item.getSoldCount() + "");
        holder.tv_price.setText("￥" + (Double.parseDouble(item.getPrice() + "") / 100) + "");
        holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.mPrice.setText("￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
        holder.mBookName.setText(item.getName() + "");
        holder.image.setImageURI(ApiService.IMAGE + "" + item.getImage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCount, tv_price, mPrice, mBookName;
        CommonImage image;
        ImageView iv_addCart;

        public ViewHolder(View view) {
            super(view);
            mCount = view.findViewById(R.id.mCount);
            tv_price = view.findViewById(R.id.tv_price);
            mPrice = view.findViewById(R.id.mPrice);
            mBookName = view.findViewById(R.id.mBookName);
            iv_addCart = view.findViewById(R.id.iv_addCart);
            image=view.findViewById(R.id.mImageView);

        }
    }

}
