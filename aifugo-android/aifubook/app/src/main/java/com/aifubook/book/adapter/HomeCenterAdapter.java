package com.aifubook.book.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductListBean;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class HomeCenterAdapter extends RecyclerView.Adapter<HomeCenterAdapter.ViewHolder> {

    List<ProductListBean.list> mProductList;

    public HomeCenterAdapter(List<ProductListBean.list> list) {
        this.mProductList = list;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    @Override
    public HomeCenterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_home_center, viewGroup, false);
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(HomeCenterAdapter.ViewHolder holder, int position) {
        holder.getImageView().setImageURI(ApiService.IMAGE + mProductList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CommonImage image;

        public ViewHolder(View view,final OnItemClickListener onClickListener) {
            super(view);

            image = view.findViewById(R.id.mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClicked(view, position);
                        }
                    }
                }
            });

        }

        public CommonImage getImageView() {
            return image;
        }
    }
}
