package com.aifubook.book.service;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aifubook.book.R;
import com.aifubook.book.databinding.ActivityMyServiceBinding;
import com.aifubook.book.order.MyOrdersFragment;
import com.aifubook.book.utils.StatusBarCompat;

//加载orderfragment type=6
public class MyServiceActivity extends AppCompatActivity {
    private ActivityMyServiceBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StatusBarCompat.translucentStatusBar(this);
        binding.header.imageviewLeft.setOnClickListener(v -> finish());
        binding.header.headerTextview.setText("退款/售后");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MyOrdersFragment(6)).commit();
    }
}
