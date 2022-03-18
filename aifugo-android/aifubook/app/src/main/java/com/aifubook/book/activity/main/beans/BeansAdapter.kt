package com.aifubook.book.activity.main.beans

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.databinding.RecyclerviewBeanBinding
import com.aifubook.book.regimental.CommissionDetailsBean
import com.jiarui.base.utils.TimeUtil

class BeansAdapter(var context: Activity,var list: ArrayList<CommissionDetailsBean.ListBean>) : RecyclerView.Adapter<BeansAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewBeanBinding
        fun setBinding(binding: RecyclerviewBeanBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewBeanBinding {
            return binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = context.layoutInflater.inflate(R.layout.recyclerview_bean, parent, false)
        var holder = MyViewHolder(view)
        var binding = RecyclerviewBeanBinding.bind(view)
        holder.setBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var commissionData = list[position]
        holder.getBinding().tvName.text = commissionData.sceneName
        holder.getBinding().tvTime.text = TimeUtil.formatMsecConvertTime(commissionData.getCreateTime()) + ""
        holder.getBinding().tvCount.text = commissionData.commissionChange.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}