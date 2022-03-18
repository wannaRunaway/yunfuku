package com.aifubook.book.home.product;


import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.ProductListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ListKer_Hlg on 2021/4/25 22:51
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ProductListActivity extends BaseActivity<ProductListPresenter> implements  ProductListView{

    @BindView(R.id.Recycler)
    RecyclerView Recycler;

    BaseRecyclerAdapter<ProductListBean> beanBaseRecyclerAdapter;
    List<ProductListBean> productListBeans = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_product;
    }

    @Override
    public void initPresenter() {
         mPresenter = new ProductListPresenter(this);
    }

    @Override
    public void initView() {
      setTitle("商品列表");
        initRighRecycler();
        productList();
    }

    // 发送验证码接口
    private void  productList(){
        Map<String, Object> map = new HashMap<>();
        mPresenter.productList(map);
    }


    private void initRighRecycler() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ProductListBean>(this, productListBeans, R.layout.type_right_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ProductListBean item, int position, boolean isScrolling) {

            }
        };
        Recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Recycler.setAdapter(beanBaseRecyclerAdapter);
    }


    @Override
    public void GetDataSuc(ProductListBean DataBean) {
//        productListBeans.addAll(DataBean);
//        beanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

//    RecyclerView.Recycler


}
