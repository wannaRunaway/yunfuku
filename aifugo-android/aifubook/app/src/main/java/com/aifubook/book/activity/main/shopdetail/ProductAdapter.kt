package com.aifubook.book.activity.main.shopdetail

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.databinding.RecyclerviewProductSearchBinding
import com.bumptech.glide.Glide

class ProductAdapter(
    var shopDetailListActivity: ShopDetailListActivity,
    var productList: ArrayList<ProductListBean.list>,
    var clickProductsInter: ClickProductsInter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOT = 1
    private val NORMAL = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewProductSearchBinding
        fun setBinding(binding: RecyclerviewProductSearchBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewProductSearchBinding {
            return binding
        }
    }

    class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = shopDetailListActivity.layoutInflater.inflate(
            R.layout.recyclerview_product_search,
            parent,
            false
        )
//        when (viewType) {
//            NORMAL -> {
        var holder = ViewHolder(view)
        var binding = RecyclerviewProductSearchBinding.bind(view)
        holder.setBinding(binding)
        return holder
//            }
//            FOOT -> {
//                var view = shopDetailListActivity.layoutInflater.inflate(
//                    R.layout.recyclerview_foot,
//                    parent,
//                    false
//                )
//                var holder = FootViewHolder(view)
//                return holder
//            }
//        }
//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            var listBean = productList.get(position)
            holder.getBinding().tvPrice.text = "￥" + ((listBean.getPrice()) / ApiService.onehundred) + ""
            holder.getBinding().tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.getBinding().mPrice.text =
                "￥" + ((listBean.getDiscountPrice()) / ApiService.onehundred) + ""
            holder.getBinding().mBookName.text = listBean.name + ""
            Glide.with(shopDetailListActivity).load(ApiService.IMAGE + listBean.image)
                .into(holder.getBinding().mImageView)
            holder.getBinding().parent.setOnClickListener {
                clickProductsInter.clickproducts(listBean)
            }
            holder.getBinding().ivAddCart.setOnClickListener {
                clickProductsInter.clickaddCart(listBean)
            }
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (productList.size >= 20 && !islast) {
//            if (position == productList.size) {
//                FOOT
//            } else {
//                NORMAL
//            }
//        } else {
//            NORMAL
//        }
//    }

    override fun getItemCount(): Int {
//        if (productList.size>=20 && !islast){
//            return productList.size + 1
//        }
        return productList.size
//        return if (productList.size >= 20) {
//            productList.size + 1 //有一个尾布局
//        } else {
//            productList.size
//        }
    }

//    var islast = false
}