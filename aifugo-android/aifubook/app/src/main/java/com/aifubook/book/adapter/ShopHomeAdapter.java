package com.aifubook.book.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ShopHomeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;

import org.jetbrains.annotations.NotNull;

public class ShopHomeAdapter extends BaseQuickAdapter<ShopHomeBean.ShopHome, BaseViewHolder> implements LoadMoreModule {

    public ShopHomeAdapter() {
        super(R.layout.item_shop_home);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ShopHomeBean.ShopHome shopHome) {

        holder.setText(R.id.tv_name, shopHome.getName());
        double price = shopHome.getPrice();
        double discountPrice = shopHome.getDiscountPrice();
        String image = shopHome.getImage();

        CommonImage iv_book = holder.getView(R.id.iv_book);
        iv_book.setImageURI(ApiService.IMAGE + image);

        holder.setText(R.id.tv_old_price, "￥" + (Double.parseDouble(price + "") / 100) + "");

        holder.setText(R.id.tv_price, "￥" + (Double.parseDouble(discountPrice + "") / 100) + "");
        TextView tv_price = holder.getView(R.id.tv_old_price);
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        int position = holder.getLayoutPosition();

        if (position < 4) {
            holder.setText(R.id.tv_num, "TOP" + position);
            holder.setVisible(R.id.iv_top,true);
        } else {
            holder.setText(R.id.tv_num, "热销中...");
            holder.setVisible(R.id.iv_top,false);

        }


    }


}
