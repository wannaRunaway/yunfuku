package com.aifubook.book.activity.main.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityMoneyBinding
import com.aifubook.book.regimental.CommissionDetailsBean
import com.aifubook.book.utils.StatusBarCompat
import com.jiarui.base.utils.ToastUitl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoneyBinding
    private lateinit var adapter: MoneyAdapter
    private var pageNum = 1
    private lateinit var viewModel: MoneyViewModel
    private var type: String = "1"
    private var list = ArrayList<CommissionDetailsBean.ListBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MoneyViewModel::class.java]
        StatusBarCompat.translucentStatusBar(this)
        binding.header.title.text = "零钱"
        adapter = MoneyAdapter(this, list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.smartrefresh.setOnRefreshListener {
            pageNum = 1
            loadData(1)
        }
        binding.smartrefresh.setOnLoadMoreListener {
            pageNum += 1
            loadData(pageNum)
        }
        binding.smartrefresh.autoRefresh()
        onclickevent()
        lifecycleScope.launch {
            viewModel.comissBean.collect {
                it.result?.let {
                    if (pageNum == 1) {
                        list.clear()
                    }
                    list.addAll(it.list)
                    adapter.notifyDataSetChanged()
                    binding.smartrefresh.finishLoadMore()
                    binding.smartrefresh.finishRefresh()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.accountInfo.collect {
                it.result?.let {
//                    count = it.commission.toLong()
                    binding.tvBeansCount.text = (it.balance / 100.00).toString()
                }
            }
        }
        var map = HashMap<String, String>()
        viewModel.getaccountInfo(map)
    }

    //加载数据
    private fun loadData(pageNum: Int) {
        /*
               * pageNo (integer, optional): 基于页码查询走这个 1开始 ,
               pageSize (integer, optional): 分页条数 ,
               scene (integer, optional): 财务场景查询条件 ,
               scenes (Array[integer], optional): 财务多个场景查询条件 ,
               type (integer, optional): 进账还是出账动态查询条件 1-收入 0-支出 ,
               walletType (integer, optional): 查询类型 0-零钱 1-粉豆L
               * */
        var map = HashMap<String, String>()
        map["pageNo"] = pageNum.toString()
        map["pageSize"] = "20"
        map["type"] = type
        map["walletType"] = "0"
        viewModel.commissionlist(map)
    }

    private fun onclickevent() {
        binding.tvMyincome.setOnClickListener {
            binding.tvMyincome.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.view1.visibility = View.VISIBLE
            binding.tvMyexpenditure.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view2.visibility = View.INVISIBLE
            binding.tvRecords.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view3.visibility = View.INVISIBLE
            type = "1"
            binding.smartrefresh.autoRefresh()
        }
        binding.tvMyexpenditure.setOnClickListener {
            binding.tvMyincome.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view1.visibility = View.INVISIBLE
            binding.tvRecords.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view3.visibility = View.INVISIBLE
            binding.tvMyexpenditure.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.view2.visibility = View.VISIBLE
        }
        binding.tvRecords.setOnClickListener {
            binding.tvMyincome.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view1.visibility = View.INVISIBLE
            binding.tvMyexpenditure.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view2.visibility = View.INVISIBLE
            binding.tvRecords.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.view3.visibility = View.VISIBLE
        }
        binding.header.leftImg.setOnClickListener { finish() }
        binding.tvConvert.setOnClickListener {
            startActivity(Intent().setClass(this, TakeMoneyActivity::class.java))
//            dialogshow()
//            ToastUitl.showShort(this, "该功能暂未开启")
        }
    }
}