package com.aifubook.book.activity.main.money

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.databinding.RecyclerviewMoneyBinding
import com.aifubook.book.regimental.CommissionDetailsBean
import com.jiarui.base.utils.TimeUtil

class MoneyAdapter(var context: Activity,var list: ArrayList<CommissionDetailsBean.ListBean>) : RecyclerView.Adapter<MoneyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: RecyclerviewMoneyBinding
        fun setBinding(binding: RecyclerviewMoneyBinding) {
            this.binding = binding
        }

        fun getBinding(): RecyclerviewMoneyBinding {
            return binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = context.layoutInflater.inflate(R.layout.recyclerview_money, parent, false)
        var holder = MyViewHolder(view)
        var binding = RecyclerviewMoneyBinding.bind(view)
        holder.setBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var commissionData = list[position]
        holder.getBinding().tvName.text = commissionData.sceneName
        holder.getBinding().tvTime.text = TimeUtil.formatMsecConvertTime(commissionData.getCreateTime()) + ""
        holder.getBinding().tvCount.text = commissionData.balance.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}