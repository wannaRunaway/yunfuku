package com.aifubook.book.activity.main.shopdetail

import com.aifubook.book.bean.ProductListBean

interface ClickProductsInter {
    fun clickproducts(bean: ProductListBean.list)
    fun clickaddCart(bean: ProductListBean.list)
}