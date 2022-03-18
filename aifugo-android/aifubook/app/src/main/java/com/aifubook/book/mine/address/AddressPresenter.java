package com.aifubook.book.mine.address;

import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class AddressPresenter extends BasePresenter<AddressView, AddressModel> {

    public AddressPresenter(AddressView view) {
        setVM(view, new AddressModel());
    }

    /**
     * 获取用户所有收货地址
     */
    public void addressList(Map<String, String> requestData) {
        mRxManage.add(
                mModel.addressList(requestData, new RxSubscriber<List<AddressListBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
//                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<AddressListBean> DataBean) {
//                        mView.stopLoading();
                        mView.AddressListSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
//                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 修改默认收货地址
     *
     * @param requestData
     */
    public void updateDefaultAddress(Map<String, String> requestData) {

        mRxManage.add(mModel.updateDefaultAddress(requestData, new RxSubscriber<String>(mContext) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("正在加载,请稍后...");
            }

            @Override
            protected void _onNext(String addressListBean) {
                mView.stopLoading();
                mView.updateDefAddress();
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.updateDefAddressError(message);
            }
        }));
    }


    /**
     * 获取默认的收货地址
     */
    public void addressCurrent(Map<String, String> requestData) {
        mRxManage.add(
                mModel.addressCurrent(requestData, new RxSubscriber<AddressListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(AddressListBean DataBean) {
                        mView.stopLoading();
                        mView.AddressCurrentSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 添加收货地址
     */
    public void addressAdd(Map<String, String> requestData) {
        mRxManage.add(
                mModel.addressAdd(requestData, new RxSubscriber<AddressListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(AddressListBean DataBean) {
                        mView.stopLoading();
                        mView.AddressAddSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 更新收货地址
     */
    public void addressUpdate(Map<String, String> requestData) {
        mRxManage.add(
                mModel.addressUpdate(requestData, new RxSubscriber<AddressListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(AddressListBean DataBean) {
                        mView.stopLoading();
                        mView.AddressUpdateSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 删除收货地址
     */
    public void addressDelete(Map<String, String> requestData) {
        mRxManage.add(
                mModel.addressDelete(requestData, new RxSubscriber<AddressListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(AddressListBean DataBean) {
                        mView.stopLoading();
                        mView.AddressDeleteSuc();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

}
