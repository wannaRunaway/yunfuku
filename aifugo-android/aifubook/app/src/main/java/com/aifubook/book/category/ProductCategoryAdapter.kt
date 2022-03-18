package com.aifubook.book.category

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.databinding.RecyclerviewCategoryProductBinding
import com.bumptech.glide.Glide

class ProductCategoryAdapter(
    var activity: MainActivity,
    var productListBean: ArrayList<ProductListBean.list>,
    var inter: ClickCategoryInter
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = activity.layoutInflater.inflate(R.layout.recyclerview_category_product, parent, false)
        return MyViewHolder(view, RecyclerviewCategoryProductBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listBean = productListBean[position]
        val myViewHolder = holder as MyViewHolder
        myViewHolder.binding.tvPrice.text =
            "￥" + ((listBean.price) / ApiService.onehundred) + ""
        myViewHolder.binding.tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        myViewHolder.binding.mPrice.text =
            "￥" + ((listBean.discountPrice) / ApiService.onehundred) + ""
        myViewHolder.binding.mBookName.text = listBean.name + ""
        Glide.with(activity).load(ApiService.IMAGE + listBean.image)
            .into(myViewHolder.binding.mImageView)
        myViewHolder.binding.parent.setOnClickListener {
            inter.recommandClick(listBean)
        }
        myViewHolder.binding.ivAddCart.setOnClickListener {
            inter.addCartClick(listBean)
        }
    }

    override fun getItemCount(): Int {
        return productListBean.size
    }

    class MyViewHolder(itemView: View, var binding:RecyclerviewCategoryProductBinding) : RecyclerView.ViewHolder(itemView){
    }
}