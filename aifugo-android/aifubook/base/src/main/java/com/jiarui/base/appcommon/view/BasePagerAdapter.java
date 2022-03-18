package com.jiarui.base.appcommon.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public abstract class BasePagerAdapter<T> extends PagerAdapter {
    private List<T> data;

    private AdapterNotifyListener listener;

    private List<View> viewCacheList = new ArrayList<>();

    public void setListener(AdapterNotifyListener listener) {
        this.listener = listener;
    }

    public BasePagerAdapter() {
    }

    public BasePagerAdapter(List<T> data) {
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.notifyDataSetChanged();
        if(listener != null){
            listener.notifyChange();
        }
    }

    @Override
    public int getCount() {
        if(data == null)return 0;
        if(data.size() == 1)return 1;
        return Integer.MAX_VALUE;
    }

    public int getRealSize(){
        return data == null? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if(getRealSize()<=0){
            return view;
        }

        int realPosition = getRealSize() > 3 ?position % getRealSize() : position % (getRealSize()*2);
        if(viewCacheList.size() > realPosition){
            view = viewCacheList.get(realPosition);
        }

        if(view == null){
            view = instantiateItem(container.getContext() ,data.get(position % data.size()),position);
            if(getRealSize() > 0)
            viewCacheList.add(view);
        }

        ViewParent viewParent = view.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(view);
        }
        container.addView(view);
        return view;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    protected abstract View instantiateItem(Context context ,T t,int position);


    public interface AdapterNotifyListener {
        void notifyChange();
    }

}
