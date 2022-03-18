package com.aifubook.book.home.product;

import com.aifubook.book.bean.ProductListBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class ProductListPresenter extends BasePresenter<ProductListView, ProductListModel> {

    public ProductListPresenter(ProductListView view) {
        setVM(view, new ProductListModel());
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productList(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        mView.stopLoading();
                        mView.GetDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

}
