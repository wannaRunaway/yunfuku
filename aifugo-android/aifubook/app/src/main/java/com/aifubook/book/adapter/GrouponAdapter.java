package com.aifubook.book.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.GrouponPageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;
import com.luck.picture.lib.tools.Constant;

import org.jetbrains.annotations.NotNull;

public class GrouponAdapter extends BaseQuickAdapter<GrouponPageBean.GrouponBean, BaseViewHolder> implements LoadMoreModule {


    public GrouponAdapter() {
        super(R.layout.item_groupon);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, GrouponPageBean.GrouponBean bean) {

        CommonImage image = holder.getView(R.id.mImageView);

        image.setImageURI(ApiService.IMAGE + bean.getProductImage());

        holder.setText(R.id.tv_name, bean.getProductName());

        int groupPrice = bean.getGroupPrice();

        holder.setText(R.id.tv_group_price, "￥" + (Double.parseDouble(groupPrice + "") / 100) + "");
        TextView tv_price = holder.getView(R.id.tv_price);
        tv_price.setText("￥" + (Double.parseDouble(bean.getProductPrice() + "") / 100) + "");
        holder.setText(R.id.tv_count, bean.getCount() + "人团");
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线


    }


}
