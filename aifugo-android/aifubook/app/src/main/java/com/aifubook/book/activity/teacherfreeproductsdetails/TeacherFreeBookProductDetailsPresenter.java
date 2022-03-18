package com.aifubook.book.activity.teacherfreeproductsdetails;

import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.utils.OkHttpUtils;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;

public class TeacherFreeBookProductDetailsPresenter extends BasePresenter<TeacherFreeBookView, TeacherFreeBookModel> {

    private static final String TAG = "ProductDetailsPresenter";

    public TeacherFreeBookProductDetailsPresenter(TeacherFreeBookView view) {
        setVM(view, new TeacherFreeBookModel());
    }


    public void getWechatAccessToken() {
        LogUtil.e(TAG, "getWechatAccessToken");
        /*mRxManage.add(mModel.getWechatAccessToken(new RxSubscriber<WeChatToken>(mContext) {
            @Override
            protected void _onNext(WeChatToken weChatToken) {
                LogUtil.e(TAG, "token==" + weChatToken.toString());
                mView.getWeChatToken(weChatToken);
            }

            @Override
            protected void _onError(String message) {
                LogUtil.e(TAG, "error=" + message);

            }
        }));*/

        String url = ApiService.BASE_HOST+"config/wechatAccessToken";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String access_token = obj.optString("result");
                    if(mView!=null) {
                        mView.getWeChatToken(access_token);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OkHttpUtils.get(url, resultCallback);
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.productDetail(requestData, new RxSubscriber<ProductDetailBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ProductDetailBean DataBean) {
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
                        mView.SendSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.SendSucFail(message);
                    }
                }));
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
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CardAddSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void getConfigValue(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getConfigValue(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.StringRol(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }

    //领取样书
    public void getMyBooks(RequestBody requestBody) {
        mRxManage.add(
                mModel.createOrder(requestBody, new RxSubscriber<CreateOrderBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(CreateOrderBean DataBean) {
                        mView.stopLoading();
                        mView.getCreateOrder(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    //获取address
    public void getMyAddress(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.getDefaultAddress(new RxSubscriber<AddressListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(AddressListBean DataBean) {
                        mView.stopLoading();
                        mView.getDefaultAddressBean(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }


}
