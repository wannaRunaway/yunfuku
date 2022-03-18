package com.aifubook.book.activity.main.beans

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityBeansBinding
import com.aifubook.book.databinding.DialogBeansBinding
import com.aifubook.book.regimental.CommissionDetailsBean
import com.aifubook.book.utils.StatusBarCompat
import com.jiarui.base.utils.ToastUitl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeansActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeansBinding
    private lateinit var adapter: BeansAdapter
    private var pageNum = 1
    private lateinit var dialog: AlertDialog
    private lateinit var viewModel: BeansViewModel
    private var type: String = "1"
    private var showString = ""
    private var list = ArrayList<CommissionDetailsBean.ListBean>()
    private var count:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeansBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[BeansViewModel::class.java]
        StatusBarCompat.translucentStatusBar(this)
        binding.header.title.text = "粉豆"
        adapter = BeansAdapter(this, list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.smartrefresh.setOnRefreshListener {
            pageNum = 1
            loadData(1, type)
        }
        binding.smartrefresh.setOnLoadMoreListener {
            pageNum += 1
            loadData(pageNum, type)
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
                    count = it.commission.toLong()
                    binding.tvBeansCount.text = it.commission
                }
            }
        }
        lifecycleScope.launch {
            viewModel.beansconfig.collect {
                it.result?.let {
                    showString = it
                }
            }
        }
        lifecycleScope.launch {
            viewModel.beanstomoney.collect {
                it.result?.let {
                    ToastUitl.showShort(this@BeansActivity, it)
                }
            }
        }
        var map = HashMap<String, String>()
        viewModel.getaccountInfo(map)
        map.put("key", "price_info")
        viewModel.getbeansConfig(map)
    }

    //加载数据
    private fun loadData(pageNum: Int, type: String) {
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
        map["walletType"] = "1"
        viewModel.commissionlist(map)
    }

    private fun onclickevent() {
        binding.tvMyincome.setOnClickListener {
            binding.tvMyincome.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.view1.visibility = View.VISIBLE
            binding.tvMyexpenditure.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view2.visibility = View.INVISIBLE
            type = "1"
            binding.smartrefresh.autoRefresh()
        }
        binding.tvMyexpenditure.setOnClickListener {
            binding.tvMyincome.setTextColor(ContextCompat.getColor(this, R.color.black_999999))
            binding.view1.visibility = View.INVISIBLE
            binding.tvMyexpenditure.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.view2.visibility = View.VISIBLE
            type = "0"
            binding.smartrefresh.autoRefresh()
        }
        binding.header.leftImg.setOnClickListener { finish() }
        binding.tvConvert.setOnClickListener {
            dialogshow()
        }
        binding.textView3.setOnClickListener {
            AlertDialog.Builder(this).setMessage(Html.fromHtml(showString))
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()})
                .create().show()
        }
    }

    private fun dialogshow() {
        var builder = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.dialog_beans, null)
        var dialogBeansBinding = DialogBeansBinding.bind(view)
        builder.setView(view)
        dialogBeansBinding.tvConfirm.setOnClickListener {
            if (dialogBeansBinding.edMybeans.text.toString().isEmpty()){
                ToastUitl.showShort(this, "请填写兑换粉豆数量")
                return@setOnClickListener
            }
            var beans = dialogBeansBinding.edMybeans.text.toString().toInt()
            if (beans == 0){
                ToastUitl.showShort(this, "请确保兑换粉豆数量不为空")
                return@setOnClickListener
            }
            if (beans > binding.tvBeansCount.text.toString().toInt()){
                ToastUitl.showShort(this, "请确保兑换粉豆数量不能大于可兑换粉豆")
                return@setOnClickListener
            }
            var map = HashMap<String,String>()
            map["fee"] = beans.toString()
            viewModel.beanstomoney(map)
            dialog.dismiss()
            var accountMap = HashMap<String, String>()
            viewModel.getaccountInfo(accountMap)
        }
        dialogBeansBinding.tvCount.text = "可兑粉豆:"+ count
        dialogBeansBinding.tvGetBeansAll.setOnClickListener {
            dialogBeansBinding.edMybeans.setText("$count")
        }
        dialog = builder.create()
        dialog.show()
    }
}