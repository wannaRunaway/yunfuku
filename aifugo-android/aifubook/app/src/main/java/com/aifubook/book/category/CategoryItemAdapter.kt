package com.aifubook.book.category

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.bean.TypeBean
import com.aifubook.book.databinding.RecyclerviewCategoryBinding
import java.util.ArrayList

class CategoryItemAdapter(
    var activity: MainActivity,
    var categorylist: ArrayList<TypeBean.ChildrenBean.ChildrenBeans>,
    var clickCategoryInter: ClickCategoryInter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = activity.layoutInflater.inflate(R.layout.recyclerview_category, parent, false)
        return MyViewHolder(view, RecyclerviewCategoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        val subjectbean = categorylist[position]
        myViewHolder.binding.textview.text = subjectbean.name
        myViewHolder.binding.textview.setOnClickListener {
            for (sub in categorylist){
                sub.clickposition = -1
            }
            subjectbean.clickposition = position
            notifyDataSetChanged()
            clickCategoryInter.clickcategory(subjectbean)
        }
        when(position){
            subjectbean.clickposition -> {
                myViewHolder.binding.textview.setBackgroundColor(ContextCompat.getColor(activity ,R.color.white))
                myViewHolder.binding.textview.setTextColor(ContextCompat.getColor(activity, R.color.black))
                myViewHolder.binding.textview.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            }
            else ->{
                myViewHolder.binding.textview.setBackgroundColor(ContextCompat.getColor(activity ,R.color.gray_F5F5F5))
                myViewHolder.binding.textview.setTextColor(ContextCompat.getColor(activity, R.color.color666666))
                myViewHolder.binding.textview.setTypeface(Typeface.DEFAULT, Typeface.NORMAL)
            }
        }
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }

    class MyViewHolder(itemView: View, var binding: RecyclerviewCategoryBinding) :
        RecyclerView.ViewHolder(itemView) {
    }
}