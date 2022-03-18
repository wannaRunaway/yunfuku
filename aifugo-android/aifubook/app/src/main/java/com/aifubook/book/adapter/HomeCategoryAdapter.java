package com.aifubook.book.adapter;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.HomeCategoryBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCategoryAdapter extends BaseQuickAdapter<HomeCategoryBean, BaseViewHolder> {

    public HomeCategoryAdapter() {
        super(R.layout.item_home_category);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, HomeCategoryBean bean) {

       CommonImage image =  holder.getView(R.id.mImageView);

        LogUtil.e("HomeCategoryAdapter","ll="+ApiService.IMAGE+bean.getLogo());
       image.setImageURI(ApiService.IMAGE+bean.getLogo());

    }


}
