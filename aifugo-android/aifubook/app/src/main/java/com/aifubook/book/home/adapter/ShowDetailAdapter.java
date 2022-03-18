package com.aifubook.book.home.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.product.ProductDetailsActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.glide.GlideImageLoader;

import org.jetbrains.annotations.NotNull;


public class ShowDetailAdapter extends BaseQuickAdapter<ProductListBean.list, BaseViewHolder> implements LoadMoreModule {

    public ShowDetailAdapter() {
        super(R.layout.home_product_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ProductListBean.list item) {
        holder.setText(R.id.mCount, "销量" + item.getSoldCount() + "");
        holder.getView(R.id.bySelef).setVisibility(item.getShopId() == 0 ? View.VISIBLE : View.GONE);
        holder.setText(R.id.mPrice, "￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
        holder.setText(R.id.mBookName, item.getName() + "");
        int zeroBuy = item.getZeroBuy();
        if (zeroBuy == 1) {
            holder.setVisible(R.id.tv_zeroBy,true);
        } else {
            holder.setVisible(R.id.tv_zeroBy,false);
        }
        CommonImage imageView = holder.getView(R.id.mImageView);
        imageView.setImageURI(ApiService.IMAGE + "" + item.getImage());
        ProductListBean.EBook eBook = item.geteBook();
        if (eBook != null) {
            holder.setVisible(R.id.tv_ebook, true);
        } else {
            holder.setVisible(R.id.tv_ebook, false);
        }


    }
}
