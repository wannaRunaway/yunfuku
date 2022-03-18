package com.aifubook.book.activity.main.money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityBindAliPayBinding
import com.aifubook.book.utils.StatusBarCompat

class BindAliPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindAliPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.translucentStatusBar(this)
        binding = ActivityBindAliPayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.header.title.text = "绑定支付宝"
        binding.header.leftImg.setOnClickListener {
            finish()
        }

    }
}