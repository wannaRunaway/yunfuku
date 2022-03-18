package com.aifubook.book.shop;

import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.ShopHomeBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

public class ShopHomePresenter extends BasePresenter<ShopHomeView, ShopHomeModel> {

    private static final String TAG = "ProductDetailsPresenter";

    public ShopHomePresenter(ShopHomeView view) {
        setVM(view, new ShopHomeModel());
    }

    public void getShopDetail(Map<String, String> requestData) {

        mModel.getShopDetail(requestData, new RxSubscriber<ShopHomeBean>(mContext) {
            @Override
            protected void _onNext(ShopHomeBean shopHomeBean) {
                if(mView!=null) {
                    mView.showShopData(shopHomeBean);
                }
            }

            @Override
            protected void _onError(String message) {
                if(mView!=null) {
                    mView.getDataError(message);
                }
            }
        });
    }

    public void getShopTopDetail(Map<String, String> requestData) {

        mModel.getShopTopDetail(requestData, new RxSubscriber<ShopBean>(mContext) {
            @Override
            protected void _onNext(ShopBean shopBean) {
                if(mView!=null) {
                    mView.showTopData(shopBean);
                }
            }

            @Override
            protected void _onError(String message) {
                if (mView != null) {
                    mView.showTopDataError(message);
                }
            }
        });

    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void carAdd(Map<String, String> requestData) {
        mRxManage.add(
                mModel.carAdd(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if(mView!=null) {
                            mView.showLoading("正在加载,请稍后...");
                        }
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        if(mView!=null) {
                            mView.stopLoading();
                            mView.CardAddSuc(DataBean);
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        if(mView!=null) {
                            mView.stopLoading();
                            mView.CardAddFail(message);
                        }
                    }
                }));
    }


}
