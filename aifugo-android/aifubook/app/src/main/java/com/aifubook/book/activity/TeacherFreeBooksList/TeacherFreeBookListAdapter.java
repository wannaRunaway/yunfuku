package com.aifubook.book.activity.TeacherFreeBooksList;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;

import org.jetbrains.annotations.NotNull;

public class TeacherFreeBookListAdapter extends BaseQuickAdapter<ProductListBean.list, BaseViewHolder> implements LoadMoreModule {

    public TeacherFreeBookListAdapter() {
        super(R.layout.recyclerview_adapter_list_teacherfreebooks);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ProductListBean.list item) {
        holder.setText(R.id.textView_book_name, item.getName());
        holder.setText(R.id.textview_book_info, item.getClasses() + " / " + item.getSubject() + " / " + item.getPress());
        holder.setText(R.id.textview_book_price, "¥ " + item.getSoldCount());
        holder.setText(R.id.textview_book_price_before, "¥ " + item.getDiscountPrice() + "");
        ((TextView) holder.getView(R.id.textview_book_price_before)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        CommonImage imageView = holder.getView(R.id.imageView_header);
        imageView.setImageURI(ApiService.IMAGE + "" + item.getImage());
        //1是已经领取 0是可以领取
        if (item.getYesBuy() == 1) {//不可领取
            holder.getView(R.id.textview_get_already).setVisibility(View.VISIBLE);
            holder.setText(R.id.textview_book_get, "分享");
        } else if (item.getYesBuy() == 0) {
            holder.getView(R.id.textview_get_already).setVisibility(View.GONE);
            holder.setText(R.id.textview_book_get, "0元领样");
        }
        //productPrice  *  commissionRate
        int count = (item.getPrice())*item.getCommissionRate();
        if (count==0){
            holder.getView(R.id.tv_beans).setVisibility(View.GONE);
        }else{
            holder.getView(R.id.tv_beans).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_beans, "赚"+count+"粉豆");
        }
    }
}
