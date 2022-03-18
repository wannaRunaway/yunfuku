package com.aifubook.book.activity.main.mainfragment

import android.app.Activity
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.SceneBean
import com.aifubook.book.databinding.RecyclerviewAdapterEveryonebuysBinding
import com.aifubook.book.databinding.RecyclerviewEveryonebysInsideBinding
import com.aifubook.book.databinding.RecyclerviewMainHeaderBinding
import com.aifubook.book.databinding.RecyclerviewMainRecommandBinding
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.indicator.CircleIndicator
import retrofit2.http.HEAD

class RecommandRecyclerViewAdapter(
    var activity: Activity,
    var everyonebuyslist: ArrayList<EveryOneBuys>,
    var productlist: ArrayList<ProductListBean.list>,
    var scenebeanList: ArrayList<SceneBean.ItemsDTO>,
    var inter: MainFragmentInter,
    var lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER = 0
    private val RECOMMANDTEXT = 1
    private val EVERYONEBUYS = 2
    private val RECOMMANDS = 3

    class RecommandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewMainRecommandBinding
        fun setBinding(binding: RecyclerviewMainRecommandBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewMainRecommandBinding {
            return binding
        }
    }

    class EverybuysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewAdapterEveryonebuysBinding
        fun setBinding(binding: RecyclerviewAdapterEveryonebuysBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewAdapterEveryonebuysBinding {
            return binding
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewMainHeaderBinding
        fun setBinding(binding: RecyclerviewMainHeaderBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewMainHeaderBinding {
            return binding
        }
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById<TextView>(R.id.main_recommandtext)
    }

//    private var lifecycleOwner = LifecycleOwner()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER -> {
                var view = activity.layoutInflater.inflate(
                    R.layout.recyclerview_main_header,
                    parent,
                    false
                )
                var holder = HeaderViewHolder(view)
                holder.setBinding(RecyclerviewMainHeaderBinding.bind(view))
                return holder
            }
            EVERYONEBUYS -> {
                var view = activity.layoutInflater.inflate(
                    R.layout.recyclerview_adapter_everyonebuys,
                    parent,
                    false
                )
                var holder = EverybuysViewHolder(view)
                holder.setBinding(RecyclerviewAdapterEveryonebuysBinding.bind(view))
                return holder
            }
            RECOMMANDTEXT -> {
                return TextViewHolder(
                    activity.layoutInflater.inflate(
                        R.layout.main_recommand_text,
                        parent,
                        false
                    )
                )
            }
            else -> {
                var view = activity.layoutInflater.inflate(
                    R.layout.recyclerview_main_recommand,
                    parent,
                    false
                )
                var holder = RecommandViewHolder(view)
                var binding = RecyclerviewMainRecommandBinding.bind(view)
                holder.setBinding(binding)
                return holder
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.getBinding().banner.addBannerLifecycleObserver(lifecycleOwner)
                    .setAdapter((ImageAdapter(
                        scenebeanList,
                        activity,
                        inter
                    ))).setBannerRound(15f)
                    .indicator = CircleIndicator(activity)
                holder.getBinding().imageviewGroupbuy.setOnClickListener {
                    inter.clickgroup(holder.getBinding().imageviewGroupbuy)
                }
                holder.getBinding().lineNew.setOnClickListener {
                    inter.clickcategory(1)
                }
                holder.getBinding().lineRank.setOnClickListener {
                    inter.clickcategory(2)
                }
                holder.getBinding().lineDiscount.setOnClickListener {
                    inter.clickcategory(3)
                }
            }
            is EverybuysViewHolder -> {
                var index = position - 1 //大家都在买 position - first
                var everyOneBuys = everyonebuyslist[index]
                holder.getBinding().name.text = "大家都在买·" + everyOneBuys.categoryName
                var recyclerView = holder.getBinding().recyclerView
                var layoutManager = LinearLayoutManager(activity)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = InnerAdapter(
                    activity,
                    everyOneBuys.productList,
                    inter
                )
            }
            is TextViewHolder -> {

            }
            is RecommandViewHolder -> { //first position = 4
                var index =
                    position - everyonebuyslist.size - 2 //position - 1 - everyonebuyslist.size - 1
//                if (index % 2 == 0) {
                var listBean = productlist[index]
                holder.getBinding().tvPrice.text =
                    "￥" + ((listBean.price) / ApiService.onehundred) + ""
                holder.getBinding().tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.getBinding().mPrice.text =
                    "￥" + ((listBean.discountPrice) / ApiService.onehundred) + ""
                holder.getBinding().mBookName.text = listBean.name + ""
                Glide.with(activity).load(ApiService.IMAGE + listBean.image)
                    .into(holder.getBinding().mImageView)
                holder.getBinding().parent.setOnClickListener {
                    inter.recommandClick(listBean)
                }
                holder.getBinding().ivAddCart.setOnClickListener {
                    inter.addCartClick(listBean)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return productlist.size + everyonebuyslist.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            in 1..everyonebuyslist.size -> EVERYONEBUYS
            everyonebuyslist.size + 1 -> RECOMMANDTEXT
            else -> RECOMMANDS
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        var gridLayoutManager: GridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (getItemViewType(position)) {
                    HEADER -> 2
                    EVERYONEBUYS -> 2
                    RECOMMANDTEXT -> 2
                    RECOMMANDS -> 1
                    else -> 2
                }
            }
        }
    }



    class InnerAdapter(
        var activity: Activity,
        var everyoneBysInside: List<ProductListBean.list>,
        var mainFragmentInter: MainFragmentInter
    ) : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

        class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewEveryonebysInsideBinding
            fun setBinding(binding: RecyclerviewEveryonebysInsideBinding) {
                this.binding = binding
            }

            fun getBinding(): RecyclerviewEveryonebysInsideBinding {
                return binding
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            var view = activity.layoutInflater.inflate(
                R.layout.recyclerview_everyonebys_inside,
                parent,
                false
            )
            var holder = InnerViewHolder(view)
            holder.setBinding(RecyclerviewEveryonebysInsideBinding.bind(view))
            return holder
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            var everyBeans = everyoneBysInside[position]
            Glide.with(activity).load(ApiService.IMAGE + everyBeans.image)
                .into(holder.getBinding().mImageView)
            holder.getBinding().mBookName.text = everyBeans.name
            holder.getBinding().tvPrice.text =
                "￥" + ((everyBeans.getPrice()) / ApiService.onehundred) + ""
            holder.getBinding().tvPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.getBinding().mPrice.text =
                "￥" + ((everyBeans.getDiscountPrice()) / ApiService.onehundred) + ""
            holder.getBinding().parent.setOnClickListener {
                mainFragmentInter.everyonebusrecyclerviewClick(everyBeans)
            }
        }

        override fun getItemCount(): Int {
            return everyoneBysInside.size
        }
    }
}