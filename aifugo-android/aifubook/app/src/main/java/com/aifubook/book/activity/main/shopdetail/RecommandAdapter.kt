package com.aifubook.book.activity.main.shopdetail

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.databinding.RecyclerviewProductRecommandBinding
import com.bumptech.glide.Glide

class RecommandAdapter(var shopDetailListActivity: ShopDetailListActivity,
                       var productList: ArrayList<ProductListBean.list>,
                       var clickProductsInter: ClickRecommandInter
) : RecyclerView.Adapter<RecommandAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewProductRecommandBinding
        fun setBinding(binding: RecyclerviewProductRecommandBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewProductRecommandBinding {
            return binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = shopDetailListActivity.layoutInflater.inflate(
            R.layout.recyclerview_product_recommand,
            parent,
            false
        )
        var holder = ViewHolder(view)
        var binding = RecyclerviewProductRecommandBinding.bind(view)
        holder.setBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listBean = productList.get(position)
//        holder.getBinding().mCount.text = "销量" + listBean.soldCount + ""
        holder.getBinding().tvPrice.text = "￥" + ((listBean.getPrice()) / ApiService.onehundred) + ""
        holder.getBinding().tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.getBinding().mPrice.text =
            "￥" + ((listBean.getDiscountPrice()) / ApiService.onehundred) + ""
        holder.getBinding().mBookName.text = listBean.name + ""
        Glide.with(shopDetailListActivity).load(ApiService.IMAGE + listBean.image)
            .into(holder.getBinding().mImageView)
        holder.getBinding().parent.setOnClickListener {
            clickProductsInter.clickrecommandproducts(listBean)
        }
        holder.getBinding().ivAddCart.setOnClickListener {
            clickProductsInter.clickrecommandaddCart(listBean)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}