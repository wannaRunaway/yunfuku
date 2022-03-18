package com.aifubook.book.activity.main.mainfragment

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.bean.TypeBean
import com.bumptech.glide.Glide

class SubjectItemRecyclerViewAdapter(
    val activity: Activity,
    val subject: ArrayList<TypeBean.ChildrenBean.ChildrenBeans>,
    private var inter: MainFragmentInter
) : RecyclerView.Adapter<SubjectItemRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textview_subject_name)
        var imageView: ImageView = itemView.findViewById(R.id.imageview_subject_item_icon)
        var parent: RelativeLayout = itemView.findViewById(R.id.parent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            activity.layoutInflater.inflate(
                R.layout.recyclerview_item_subject,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var childrenBean = subject[position]
        holder.textView.text = childrenBean.name
        Glide.with(activity).load(ApiService.IMAGE + childrenBean.logo).into(holder.imageView)
        holder.parent.setOnClickListener {
//            inter.headiconClick(childrenBean)
        }
    }

    override fun getItemCount(): Int {
        return subject.size
    }
}