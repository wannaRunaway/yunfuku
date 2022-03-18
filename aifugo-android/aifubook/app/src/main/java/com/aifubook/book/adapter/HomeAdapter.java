package com.aifubook.book.adapter;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.product.ProductDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

public class HomeAdapter extends BaseQuickAdapter<ProductListBean.list, BaseViewHolder> implements LoadMoreModule {

    private static final String TAG = "HomeAdapter";

    public HomeAdapter() {
        super(R.layout.home_product_item_new);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ProductListBean.list item) {

        holder.setText(R.id.mCount, "销量" + item.getSoldCount() + "");
        // holder.getView(R.id.bySelef).setVisibility(item.getShopId() == 0 ? View.VISIBLE : View.GONE);
        holder.setText(R.id.tv_price, "￥" + (Double.parseDouble(item.getPrice() + "") / 100) + "");
        TextView tv_price = holder.getView(R.id.tv_price);
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.setText(R.id.mPrice, "￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
        holder.setText(R.id.mBookName, item.getName() + "");
        //holder.setImageByUrl(R.id.mImageView, ApiService.IMAGE + "" + item.getImage());
//        holder.setImageFresco(R.id.mImageView, ApiService.IMAGE + "" + item.getImage());
        CommonImage image = holder.getView(R.id.mImageView);
        image.setImageURI(ApiService.IMAGE + "" + item.getImage());
        ProductListBean.EBook eBook = item.geteBook();
        if(eBook!=null){
            LogUtil.e(TAG,"ebook="+eBook.getName());
            holder.setVisible(R.id.tv_ebook,true);
        }else{
            holder.setVisible(R.id.tv_ebook,false);
        }


       /* holder.getView(R.id.inProductDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + item.getId());
                startActivity(ProductDetailsActivity.class, bundle);
            }
        });
    }*/

    }


}
