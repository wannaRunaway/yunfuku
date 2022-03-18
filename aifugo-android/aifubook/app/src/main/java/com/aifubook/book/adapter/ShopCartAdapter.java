package com.aifubook.book.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.fragment.Addcart;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

public class ShopCartAdapter  extends BaseQuickAdapter<ProductListBean.list, BaseViewHolder> implements LoadMoreModule {

    private static final String TAG ="TeacherAreaAdapter";
    private Addcart addcart;
    public ShopCartAdapter(Addcart addcart) {
        super(R.layout.item_shop_cart);
        this.addcart = addcart;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, ProductListBean.list item) {
        holder.setText(R.id.tv_price, "￥" + (Double.parseDouble(item.getPrice() + "") / 100) + "");
        TextView tv_price = holder.getView(R.id.tv_price);
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.setText(R.id.mPrice, "￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
        holder.setText(R.id.mBookName, item.getName() + "");
        Glide.with(getContext()).load(ApiService.IMAGE+item.getImage()).into((ImageView) holder.getView(R.id.mImageView));
//        CommonImage image = holder.getView(R.id.mImageView);
//        image.setImageURI(ApiService.IMAGE + "" + item.getImage());
        holder.getView(R.id.iv_addCart).setOnClickListener(v -> {
            addcart.addcart(item.getId());
        });
    }
}
