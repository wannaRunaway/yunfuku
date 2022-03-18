package com.aifubook.book.bean;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.type.TypeModel;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.baserx.bean.BaseBean;

import java.util.List;
import java.util.Map;

public class HomeModel extends BaseModel {

    private TypeModel mModel;

    private OnCallBack callBack;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnCallBack {
        void onNext(Object result, int type);

        void onError(String error);
    }

    public HomeModel(TypeModel model) {
        this.mModel = model;
    }


    public void bindChief(Map<String, String> requestData, int type) {

        mRxManage.add(mModel.bindChief(requestData, new RxSubscriber<String>() {
            @Override
            protected void _onNext(String s) {
                callBack.onNext(s, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message);
            }
        }));

    }


    public void getShopForHome(Map<String, Object> requestData, int type) {
        mRxManage.add(
                mModel.getMostNearShop(requestData, new RxSubscriber<NearShopBean>() {
                    @Override
                    protected void _onNext(NearShopBean nearShopBean) {
                        callBack.onNext(nearShopBean,type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message);
                    }
                }));


    }

    /**
     * 首页轮播图
     *
     * @param requestData
     * @param type
     */
    public void getByScene(Map<String, Object> requestData, int type) {
        mRxManage.add(mModel.getByScene(requestData, new RxSubscriber<SceneBean>() {

            @Override
            protected void _onNext(SceneBean DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message);
            }
        }));
    }


    public void categoryAll(Map<String, String> requestData, int type) {
        mRxManage.add(mModel.categoryAll(requestData, new RxSubscriber<List<TypeBean>>() {
            @Override
            protected void _onNext(List<TypeBean> typeBeans) {
                callBack.onNext(typeBeans, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message);
            }
        }));
    }

    public void productList(Map<String, Object> requestData, int type) {
        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>() {

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message);
                    }
                }));
    }

    /**
     * 获取所有的类型
     *
     * @param requestData
     * @param type
     */
    public void productLists(Map<String, Object> requestData, int type) {
        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>() {

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message);
                    }
                }));
    }

    public void getHomeCategory(int type) {
        mRxManage.add(mModel.getHomeCategory(new RxSubscriber<List<HomeCategoryBean>>() {
            @Override
            protected void _onNext(List<HomeCategoryBean> list) {
                callBack.onNext(list, type);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    public void shopDetail(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.shopDetail(requestData, new RxSubscriber<ShopBean>() {

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message);
                    }
                }));
    }

    public void addCart(Map<String, String> requestData, int type) {
        mRxManage.add(mModel.carAdd(requestData, new RxSubscriber<String>() {
            @Override
            protected void _onNext(String s) {
                callBack.onNext(s, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message);
            }
        }));
    }


}
