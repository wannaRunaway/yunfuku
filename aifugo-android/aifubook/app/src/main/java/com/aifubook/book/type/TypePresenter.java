package com.aifubook.book.type;

import android.util.Log;

import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class TypePresenter extends BasePresenter<TypeView, TypeModel> {

    private static final String TAG="TypePresenter";

    public TypePresenter(TypeView view) {
        setVM(view, new TypeModel());
    }


    public void bindChief(Map<String, String> requestData) {

        mRxManage.add(mModel.bindChief(requestData, new RxSubscriber<String>(mContext) {
            @Override
            protected void _onNext(String s) {
                mView.getChiefResult();
            }

            @Override
            protected void _onError(String message) {
                LogUtil.e(TAG,"msg="+message);
                mView.showChiefResultError(message);
            }
        }));


    }


    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void categoryAll(Map<String, String> requestData) {
        mRxManage.add(
                mModel.categoryAll(requestData, new RxSubscriber<List<TypeBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<TypeBean> DataBean) {
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

    /**
     * 团长详情信息
     *
     * @param requestData 参数
     */
    public void chiefDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.chiefDetail(requestData, new RxSubscriber<ChiefDetailsBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ChiefDetailsBean DataBean) {
                        mView.stopLoading();
                        mView.ChiefDetailSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 获取所有店铺可用优惠券
     */
    public void fullSiteReduceCoupons(Map<String, String> requestData) {
        Log.e("htttp", "ininin");
        mRxManage.add(
                mModel.fullSiteReduceCoupons(requestData, new RxSubscriber<List<MyCouponsBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<MyCouponsBean> DataBean) {
                        mView.stopLoading();
                        mView.ShopCouponsSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void getMostNearShop(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.getMostNearShop(requestData, new RxSubscriber<NearShopBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(NearShopBean DataBean) {
                        mView.stopLoading();
                        mView.GetNearShop(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void getByScene(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.getByScene(requestData, new RxSubscriber<SceneBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(SceneBean DataBean) {
                        mView.stopLoading();
                        mView.GetHomePage(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void shopDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.shopDetail(requestData, new RxSubscriber<ShopBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        mView.stopLoading();
                        mView.GetShopSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetShopFail(message);
                    }
                }));
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
                        mView.GetProSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetProFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productLists(Map<String, Object> requestData) {
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
                        mView.GetProSucs(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetProFail(message);
                    }
                }));
    }

}
