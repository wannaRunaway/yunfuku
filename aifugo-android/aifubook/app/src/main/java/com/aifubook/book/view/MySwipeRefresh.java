package com.aifubook.book.view;

import android.graphics.Rect;
import android.view.View;


import com.aifubook.book.R;
import com.jiarui.base.utils.SpaceItemDecoration;
import com.jiarui.base.utils.StringUtil;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/*
 *  对应的布局
 *  <include layout="@layout/refresh_recyclerview_layout"/>
 *  此类需与上述布局配套使用，不可更改view对应id
 */
public class MySwipeRefresh {

    //    private NodataView mNodataView;
    private SwipeRefreshLayout swipe_refresh_widget;
    private RecyclerView recyclerView;

    public MySwipeRefresh(View view, RecyclerView.LayoutManager layoutManager) {
//        mNodataView = new NodataView(view);
        swipe_refresh_widget = view.findViewById(R.id.swipe_refresh_widget);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipe_refresh_widget.setColorSchemeResources(R.color.colorAccent);
        swipe_refresh_widget.setRefreshing(true);

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    staggeredGridLayoutManager.invalidateSpanAssignments();//这行主要解决了当加载更多数据时，底部需要重绘，否则布局可能衔接不上。
                }
            });
        } else if (layoutManager instanceof GridLayoutManager) {
            recyclerView.setLayoutManager((GridLayoutManager) layoutManager);
            recyclerView.addItemDecoration(new GridDecoration(view.getContext(), 10, android.R.color.darker_gray) {
                @Override
                public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
                    //顺序:left, top, right, bottom
                    boolean[] booleans = {false, false, false, false};
                    if (itemPosition == 0) {
                        //因为给 RecyclerView 添加了 header，所以原本的 position 发生了变化
                        //position 为 0 的地方实际上是 header，真正的列表 position 从 1 开始
                    } else {
                        switch (itemPosition % 2) {
                            case 0:
                                //每一行第二个只显示左边距和下边距
                                booleans[0] = true;
                                booleans[3] = true;
                                break;
                            case 1:
                                //每一行第一个显示右边距和下边距
                                booleans[2] = true;
                                booleans[3] = true;
                                break;
                        }
                    }
                    return booleans;
                }
            });
        } else {
            recyclerView.setLayoutManager(null == layoutManager ? new LinearLayoutManager(view.getContext()) : layoutManager);
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private int space=10;
    public void setGridSpace(int space){
        this.space=space;
    }


    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener l) {
        swipe_refresh_widget.setOnRefreshListener(l);
    }

    public void setOnClickListener(View.OnClickListener l) {
//        mNodataView.setOnClickListener(l);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }


    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe_refresh_widget;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRefreshing(boolean refreshing) {
        swipe_refresh_widget.setRefreshing(refreshing);
    }


    public void showContentView() {
//        mNodataView.hideEmptyView();
        recyclerView.setVisibility(View.VISIBLE);
    }

    private int responseCode;
    private String errorMsg;

    public void setResponseError(int code, String errorMsg) {
        this.responseCode = code;
        this.errorMsg = errorMsg;
    }

    public void showEmptyView(String text, int resId) {
        if (!StringUtil.checkStr(text)) {
            text = errorMsg;
        }
        recyclerView.setVisibility(View.GONE);
//        mNodataView.showEmptyView(responseCode,text, resId);
    }

}
