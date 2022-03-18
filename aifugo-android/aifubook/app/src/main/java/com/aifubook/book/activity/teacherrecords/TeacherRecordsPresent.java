package com.aifubook.book.activity.teacherrecords;

import com.aifubook.book.mine.order.bean.OrderListBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

public class TeacherRecordsPresent extends BasePresenter<TeacherRecordsView, TeacherRecordsModel> {
    public TeacherRecordsPresent(TeacherRecordsView view) {
        setVM(view, new TeacherRecordsModel());
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void getFreeBooksRecords(Map<String, String> requestData, boolean _isrefresh) {
        mRxManage.add(
                mModel.orderList(requestData, new RxSubscriber<OrderListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(OrderListBean DataBean) {
                        mView.stopLoading();
                        mView.orderList(DataBean, _isrefresh);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.failed(message);
                    }
                }));
    }
}
