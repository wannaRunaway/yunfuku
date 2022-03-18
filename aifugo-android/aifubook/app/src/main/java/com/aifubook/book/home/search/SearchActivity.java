package com.aifubook.book.home.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.home.shop.ShopDetailsActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jiarui.base.utils.ContextUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.SharedPreferencesUtils;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ListKer_Hlg on 2021/4/25 22:14
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    @BindView(R.id.Search)
    TextView Search;

    @BindView(R.id.EditText)
    EditText EditTexts;

//    @BindView(R.id.recyclear)
//    RecyclerView recyclear;

    @BindView(R.id.cg_hot)
    ChipGroup cg_hot;

    @BindView(R.id.cg_history)
    ChipGroup cg_history;

    private String shopId = "";
    private int zeroBuy = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {

    }

    List<String> hotTagList = new ArrayList<>();

    @Override
    public void initView() {
        setTitle("搜索");

        hotTagList.add("状元语文笔记");
        hotTagList.add("黄冈同步练");
        hotTagList.add("倍速学习法");
        hotTagList.add("中考必备");
        hotTagList.add("状元作业本");
        hotTagList.add("小升初");
        for (String tagStr : hotTagList) {
            cg_hot.addView(createTagTextView(tagStr));
        }


        try {
            shopId = getIntent().getStringExtra("shopId");
            zeroBuy = getIntent().getIntExtra("zeroBuy", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String number = SharedPreferencesUtils.get(this, "SEARCH", "");

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = EditTexts.getText().toString();

                if (StringUtils.isEmpty(content)) {
                    ToastUtil.showToast("搜索内容不能为空",false);
                    return;
                }

                if (StringUtils.isEmpty(number + "")) {
                    SharedPreferencesUtils.put(SearchActivity.this, "SEARCH", "" + EditTexts.getText().toString());
                } else {
                    if (!number.contains(content)) {
                        SharedPreferencesUtils.put(SearchActivity.this, "SEARCH", number + "," + EditTexts.getText().toString());
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("inType", 2 + "");
                bundle.putString("keyWord", EditTexts.getText().toString() + "");
                bundle.putString("shopId", shopId + "");
                bundle.putInt("zeroBuy", zeroBuy);
                startActivity(ShopDetailsActivity.class, bundle);
                SearchActivity.this.finish();
            }
        });

        LogUtil.e("SEARCH", "1111" + number);
        if (StringUtils.isEmpty(number)) {
            return;
        }
        String[] numbers = number.split(",");
        for (int i = 0; i < numbers.length; i++) {
            Log.e("SEARCH", "111" + numbers[i]);
            cg_history.addView(createTagTextView(numbers[i]));
        }
    }


    private Chip createTagTextView(String tagString) {
        Chip chipText = new Chip(SearchActivity.this);
        chipText.setTextColor(ContextUtil.getColor(R.color.black));
        chipText.setTextSize(14);
        chipText.setText(tagString);
        chipText.setChipCornerRadius(4);
        chipText.setOnClickListener(v -> {
            String keyWord = chipText.getText().toString();
            if (!TextUtils.isEmpty(keyWord)) {
                LogUtil.e(TAG, "keyword=" + keyWord);
                EditTexts.setText(keyWord);
                EditTexts.setSelection(EditTexts.getText().length());

            }
        });
        return chipText;
    }


}
