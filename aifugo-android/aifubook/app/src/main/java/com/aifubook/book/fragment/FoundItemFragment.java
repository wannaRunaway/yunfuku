package com.aifubook.book.fragment;

import android.annotation.SuppressLint;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@SuppressLint("ValidFragment")
public class FoundItemFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<String> BaseAdapter;
    List<String> list = new ArrayList<>();

    private int type = 0;//0.精选 1.最IN趋势 2.即看即玩 3.行无止境

    public FoundItemFragment(int type) {
        super();
        this.type = type;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.found_item_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        listInit();
    }

    /***************************************************************************************热门景点*********************************************************************************/

    private void listInit() {
        list.clear();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        imageRecyclerView(list);
    }

    private void imageRecyclerView(List<String> list) {
        BaseAdapter = new BaseRecyclerAdapter<String>(mActivity, list, R.layout.found_item_fragment_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(BaseAdapter);
    }
}
