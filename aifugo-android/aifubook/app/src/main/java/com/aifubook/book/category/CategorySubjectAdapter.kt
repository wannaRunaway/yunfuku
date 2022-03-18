package com.aifubook.book.category

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.bean.TypeBean
import com.aifubook.book.databinding.RecyclerviewSubjectCategoryBinding
import java.util.ArrayList

class CategorySubjectAdapter(
    var activity: MainActivity,
    var subjectlist: ArrayList<TypeBean.ChildrenBean>,
    var clickCategoryInter: ClickCategoryInter
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = activity.layoutInflater.inflate(R.layout.recyclerview_subject_category, parent, false)
        return MyViewHolder(view, RecyclerviewSubjectCategoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        val subjectbean = subjectlist[position]
        myViewHolder.binding.textview.text = subjectbean.name
        myViewHolder.binding.textview.setOnClickListener {
            for (sub in subjectlist){
                sub.clickposition = -1
            }
            subjectbean.clickposition = position
            notifyDataSetChanged()
            clickCategoryInter.clicksubject(subjectbean)
        }
        when(position){
            subjectbean.clickposition -> {
                myViewHolder.binding.textview.background = activity.getDrawable(R.drawable.category_subject_selected)
                myViewHolder.binding.textview.setTextColor(ContextCompat.getColor(activity, R.color.black))
            }
            else ->{
                myViewHolder.binding.textview.background = activity.getDrawable(R.drawable.corner4_f0f0f0)
                myViewHolder.binding.textview.setTextColor(ContextCompat.getColor(activity, R.color.color666666))
            }
        }
    }

    override fun getItemCount(): Int {
        return subjectlist.size
    }

    class MyViewHolder(itemView: View, var binding: RecyclerviewSubjectCategoryBinding) : RecyclerView.ViewHolder(itemView){

    }
}