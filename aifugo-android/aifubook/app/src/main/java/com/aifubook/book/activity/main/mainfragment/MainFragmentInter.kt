package com.aifubook.book.activity.main.mainfragment

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.SceneBean
import com.aifubook.book.bean.SceneBean.ItemsDTO
import com.aifubook.book.bean.TypeBean
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter

interface MainFragmentInter {
//    fun everyonebusClick(everyOneBuys: EveryOneBuys)
    fun bannerClick(sencebeanItemsDTO: SceneBean.ItemsDTO)
    fun recommandClick(productListBeaninListBean: ProductListBean.list)
    fun addCartClick(productListBeaninListBean: ProductListBean.list)
    fun subjectClick(typebeanchildrenbean: TypeBean.ChildrenBean)
//    fun headiconClick(typebeanchildrenbeaninsie: TypeBean.ChildrenBean.ChildrenBeans)
    fun everyonebusrecyclerviewClick(everyOneBuysProductBean: ProductListBean.list)

    //headview in mainfragment
    fun clickgroup(imageview: ImageView)
    fun clickcategory(type: Int)
}