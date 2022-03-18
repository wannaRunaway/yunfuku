package com.aifubook.book.home.address;

import com.aifubook.book.bean.NearShopBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class AddressProductPresenter extends BasePresenter<AddressProductView, AddressProductModel> {

    public AddressProductPresenter(AddressProductView view) {
        setVM(view, new AddressProductModel());
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void findShops(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.findShops(requestData, new RxSubscriber<List<NearShopBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<NearShopBean> DataBean) {
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
