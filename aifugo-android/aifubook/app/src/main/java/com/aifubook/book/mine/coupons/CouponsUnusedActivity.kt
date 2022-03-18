package com.aifubook.book.mine.coupons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityCouponsUnusedBinding
import com.aifubook.book.utils.StatusBarCompat

class CouponsUnusedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCouponsUnusedBinding
    private lateinit var fragment: CouponsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.translucentStatusBar(this)
        binding = ActivityCouponsUnusedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageviewLeft.setOnClickListener { finish() }
        binding.headerRight.setOnClickListener {
            startActivity(Intent().setClass(this, CouponsActivity::class.java))
        }
        binding.cardviewGocenter.setOnClickListener {
            startActivity(Intent().setClass(this, CouponRedemptionActivity::class.java))
        }
        fragment = CouponsFragment(0);
        supportFragmentManager.beginTransaction().add(R.id.re,fragment).show(fragment).commit()
    }
}