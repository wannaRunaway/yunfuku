package com.aifubook.book.activity.main.mainfragment

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.bean.TypeBean

class SubjectRecyclerViewAdapter(val activity: Activity, val subject:List<TypeBean.ChildrenBean>, private val inter: MainFragmentInter) : RecyclerView.Adapter<SubjectRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView:TextView = itemView.findViewById(R.id.textview_subject_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(activity.layoutInflater.inflate(R.layout.recyclerview_subject, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var childrenBean = subject[position]
        holder.textView.text = childrenBean.name
        holder.textView.setOnClickListener {
            inter.subjectClick(childrenBean)
        }
    }

    override fun getItemCount(): Int {
        return subject.size
    }
}