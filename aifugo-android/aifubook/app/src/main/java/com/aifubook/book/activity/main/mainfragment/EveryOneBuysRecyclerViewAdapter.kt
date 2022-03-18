package com.aifubook.book.activity.main.mainfragment

import android.app.Activity
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.databinding.RecyclerviewAdapterEveryonebuysBinding
import com.aifubook.book.databinding.RecyclerviewEveryonebysInsideBinding
import com.bumptech.glide.Glide

class EveryOneBuysRecyclerViewAdapter(
    var context: Activity,
    var everyOneBuysBeanList: ArrayList<EveryOneBuys>,
    var mainFragmentInter: MainFragmentInter
) :
    RecyclerView.Adapter<EveryOneBuysRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewAdapterEveryonebuysBinding
        fun setBinding(binding: RecyclerviewAdapterEveryonebuysBinding){
            this.binding = binding
        }
        fun getBinding(): RecyclerviewAdapterEveryonebuysBinding{
            return binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = context.layoutInflater.inflate(R.layout.recyclerview_adapter_everyonebuys, parent, false)
        var holder = MyViewHolder(view)
        holder.setBinding(RecyclerviewAdapterEveryonebuysBinding.bind(view))
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var everyOneBuys = everyOneBuysBeanList[position]
        holder.getBinding().name.text = "大家都在买·"+everyOneBuys.categoryName
//        holder.getBinding().checkAll.setOnClickListener {
//            mainFragmentInter.everyonebusClick(everyOneBuys)
//        }
        var recyclerView = holder.getBinding().recyclerView
        var layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = InnerAdapter(context, everyOneBuys.productList, mainFragmentInter)
    }

    override fun getItemCount(): Int {
        return everyOneBuysBeanList.size
    }

    class InnerAdapter(var activity: Activity, var everyoneBysInside: List<ProductListBean.list>, var mainFragmentInter: MainFragmentInter) : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

        class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewEveryonebysInsideBinding
            fun setBinding(binding: RecyclerviewEveryonebysInsideBinding){
                this.binding = binding
            }
            fun getBinding(): RecyclerviewEveryonebysInsideBinding{
                return binding
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            var view = activity.layoutInflater.inflate(R.layout.recyclerview_everyonebys_inside, parent, false)
            var holder = InnerViewHolder(view)
            holder.setBinding(RecyclerviewEveryonebysInsideBinding.bind(view))
            return holder
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            var everyBeans = everyoneBysInside[position]
            Glide.with(activity).load(ApiService.IMAGE+everyBeans.image).into(holder.getBinding().mImageView)
            holder.getBinding().mBookName.text = everyBeans.name
            holder.getBinding().tvPrice.text = "￥" + (everyBeans.getPrice()) / ApiService.onehundred + ""
            holder.getBinding().tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.getBinding().mPrice.text = "￥" + (everyBeans.getDiscountPrice()) / ApiService.onehundred + ""
            holder.getBinding().parent.setOnClickListener {
                mainFragmentInter.everyonebusrecyclerviewClick(everyBeans)
            }
        }

        override fun getItemCount(): Int {
            return everyoneBysInside.size
        }
    }
}