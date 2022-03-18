package com.aifubook.book.activity.main.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityTakeMoneyBinding
import com.aifubook.book.databinding.DialogGetmoneyBinding
import com.aifubook.book.utils.StatusBarCompat
import com.jiarui.base.utils.LogUtil

class TakeMoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTakeMoneyBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeMoneyBinding.inflate(layoutInflater)
        StatusBarCompat.translucentStatusBar(this)
        setContentView(binding.root)
        binding.header.leftImg.setOnClickListener { finish() }
        binding.header.title.text = "提现"
        binding.wxChange.setOnClickListener {
            showInfoDialog();
        }
        binding.aliChange.setOnClickListener {
            startActivity(Intent().setClass(this, BindAliPayActivity::class.java))
        }
        binding.getmoneyTextview.setOnClickListener {
            startActivity(Intent().setClass(this, GetMoneyDetailsActivity::class.java))
        }
    }

    private fun dialogbindingviews() {
        dialogGetmoneyBinding.close.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogGetmoneyBinding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0){
                    dialogGetmoneyBinding.info.visibility = View.GONE
                }else {
                    dialogGetmoneyBinding.info.visibility = View.VISIBLE
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.progress!! < 100){
                    seekBar.progress = 0
                }
            }
        })
        dialogGetmoneyBinding.smsconfirm.setOnClickListener {
            LogUtil.d("progress " + dialogGetmoneyBinding.seekbar.progress)
        }
    }

    //显示弹窗
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialogGetmoneyBinding: DialogGetmoneyBinding
    private fun showInfoDialog() {
        if (!this::alertDialog.isInitialized) {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_getmoney, null)
            dialogGetmoneyBinding = DialogGetmoneyBinding.bind(view)
            builder.setView(view)
            alertDialog = builder.create()
            dialogbindingviews()
        }
        alertDialog.show()
    }
}