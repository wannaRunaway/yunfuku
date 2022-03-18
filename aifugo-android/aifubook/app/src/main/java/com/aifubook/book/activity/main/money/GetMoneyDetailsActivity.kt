package com.aifubook.book.activity.main.money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityGetMoneyDetailsBinding
import com.aifubook.book.utils.StatusBarCompat

class GetMoneyDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetMoneyDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.translucentStatusBar(this)
        binding = ActivityGetMoneyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.header.title.text = "提现详情"
        binding.header.leftImg.setOnClickListener { finish() }
        binding.back.setOnClickListener { finish() }
    }
}