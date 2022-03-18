package com.aifubook.book.activity.main.mainfragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiarui.base.utils.LogUtil

class MainItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        var viewposition = parent.getChildAdapterPosition(view);
        var layoutManager = parent.layoutManager as GridLayoutManager
        var spancount = layoutManager.spanCount
//        LogUtil.d(viewposition+"ß sadas"+spancount)
    }
}