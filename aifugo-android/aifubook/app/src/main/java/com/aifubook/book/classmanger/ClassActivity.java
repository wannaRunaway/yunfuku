package com.aifubook.book.classmanger;

import android.os.Bundle;
import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.bean.getKidBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.jiarui.base.utils.ToastUitl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/9 20:40
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ClassActivity extends BaseActivity<ClassPresenter> implements ClassView{

    @BindView(R.id.refreshLayout)   // 刷新控件
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<ClassBean> beanBaseRecyclerAdapter;
    List<ClassBean> productListBeans = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 10;

    private boolean isRefresh = true;//是否刷新
    private boolean isLoad = false;//是否加载

    @Override
    public int getLayoutId() {
        return R.layout.activity_class;
    }

    @Override
    public void initPresenter() {
       mPresenter = new ClassPresenter(this);
    }

    @OnClick({R.id.addChil})
    void Onclicks(View view){
        switch (view.getId()){
            case R.id.addChil: // 添加孩子
                Bundle bundle = new Bundle();
                bundle.putString("typeSelect","1");
                startActivity(addChileActivity.class,bundle);
                break;
        }
    }

    // 获取店铺详情的接口
    private void  childList(){
        Map<String, String> map = new HashMap<>();
        mPresenter.childList(map);
    }

    // 获取店铺详情的接口
    public void childDeletes(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",""+id);
        mPresenter.childDelete(map);
    }


    //    /***************************************************************************************热门景点*********************************************************************************/
//

    private void initRighRecycler() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ClassBean>(this, productListBeans, R.layout.activity_chile_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ClassBean item, int position, boolean isScrolling) {
                holder.setText(R.id.Name,""+item.getName());
                holder.setText(R.id.address,""+item.getSchoolName());



                holder.getView(R.id.detele).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(ClassActivity.this);
                        affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getId() == R.id.dialog_affirm_btn) {
                                    childDeletes(item.getId()+"");
                                }
                            }
                        });
                        affirmMessageDialog.show("确认要删除孩子信息？");
                    }
                });

                holder.getView(R.id.upDate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("typeSelect","2");
                        bundle.putString("id",""+item.getId());
                        startActivity(addChileActivity.class,bundle);
                    }
                });

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(beanBaseRecyclerAdapter);
    }

    @Override
    public void initView() {
      setTitle("用户管理");
        productListBeans = new ArrayList<>();
        initRighRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        childList();
    }

    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {

    }

    // 下面是网络接口请求的回调
    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetShopSuc(List<ClassBean> DataBean) {
        productListBeans.clear();
        productListBeans.addAll(DataBean);
        beanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetShopFail(String Message) {
        ToastUitl.showShort(this,Message+"!");

    }

    @Override
    public void GetListDataSuc(List<FindSchoolClassesBean> DataBean) {

    }

    @Override
    public void AddDataSuc(String DataBean) {

    }

    @Override
    public void childDelete(String DataBean) {
        childList();
    }

    @Override
    public void getChildById(getKidBean DataBean) {

    }

    @Override
    public void GetListDataFail(String Message) {
        ToastUitl.showShort(this,Message+"!");

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
