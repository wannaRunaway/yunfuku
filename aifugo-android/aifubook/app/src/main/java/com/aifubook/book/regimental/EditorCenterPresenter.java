package com.aifubook.book.regimental;

import com.aifubook.book.api.ApiService;
import com.aifubook.book.regimental.mvp.HeadApplyModel;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.aifubook.book.utils.OkHttpUtils;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class EditorCenterPresenter extends BasePresenter<EditorCenterView, HeadApplyModel> {

    public EditorCenterPresenter(EditorCenterView view) {
        setVM(view, new HeadApplyModel());
    }

    public void getWechatAccessToken() {

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
                    }

                    @Override
                    protected void _onNext(ChiefDetailsBean DataBean) {
                        mView.ChiefDetailSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.getChiefError(message);
                    }
                }));
    }

}
