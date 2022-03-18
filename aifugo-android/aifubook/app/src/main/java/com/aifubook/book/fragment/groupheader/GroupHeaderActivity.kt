package com.aifubook.book.fragment.groupheader

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.application.MyApp
import com.aifubook.book.base.ShareKey
import com.aifubook.book.bean.NearShopBean
import com.aifubook.book.databinding.ActivityGroupHeaderBinding
import com.aifubook.book.databinding.RecyclerviewGroupheaderBinding
import com.aifubook.book.utils.SharedPreferencesUtil
import com.aifubook.book.utils.StatusBarCompat
import com.bumptech.glide.Glide
import com.jiarui.base.utils.*
import com.tencent.map.geolocation.TencentLocationUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GroupHeaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupHeaderBinding
    private lateinit var adapter: GroupHeaderAdapter
    private lateinit var viewModel: GroupHeaderViewModel
    private var list = ArrayList<NearShopBean>()
    private var shopid = 0
    private var ischecked = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[GroupHeaderViewModel::class.java]
        binding = ActivityGroupHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textview1.setOnClickListener {
            startActivity(Intent(this, RuleActivity::class.java))
        }
        StatusBarCompat.translucentStatusBar(this)
        binding.header.apply {
            headerTextview.text = "绑定店铺"
            imageviewLeft.setOnClickListener { finish() }
        }
        binding.smartrefresh.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                loadData()
            }
        }
        loadData()
        initrecyclerView()
        viewmodellauncher()
        binding.checkBox.setOnClickListener {
            binding.checkBox.isChecked = ischecked
            ischecked = !ischecked
        }
        binding.confirm.setOnClickListener {
            if (!binding.checkBox.isChecked) {
                ToastUitl.showShort(MyApp.getInstance(), "请签署协议《团长绑定店铺规则》")
                return@setOnClickListener
            }
            if (shopid == 0) {
                ToastUitl.showShort(MyApp.getInstance(), "请选择店铺")
                return@setOnClickListener
            }
            var map = HashMap<String, String>()
            map["shopId"] = shopid.toString()
            viewModel.bindshop(map)
        }
    }

    private fun viewmodellauncher() {
        lifecycleScope.launch {
            viewModel.shoplistbean.collect {
                it.result?.apply {
                    binding.smartrefresh.finishRefresh()
                    list.clear()
                    list.addAll(this)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.bindingresults.collect {
                if (it.message != "") {
                    when (it.isOk) {
                        true -> {
                            finish()
                            ToastUitl.showShort(MyApp.getInstance(), "绑定店铺成功")
                        }
                        false -> ToastUitl.showShort(MyApp.getInstance(), it.message)
                    }
                }
            }
        }
    }

    lateinit var mylat: String
    lateinit var mylon: String
    private fun initrecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mylat = SharedPreferencesUtil.get(this, ShareKey.LATITUTE, "0")
        mylon = SharedPreferencesUtil.get(this, ShareKey.LONGTITUDE, "0")
        adapter = GroupHeaderAdapter(this, list)
        binding.recyclerView.adapter = adapter
    }

    private fun loadData() {
        val map: HashMap<String, String> = HashMap()
        map["longitude"] = SharedPreferencesUtil.get(this, "Long", "")
        map["latitude"] = SharedPreferencesUtil.get(this, "Lat", "")
        viewModel.getmyshoplist(map)
    }

    inner class GroupHeaderAdapter(
        var activity: GroupHeaderActivity,
        var list: ArrayList<NearShopBean>
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                activity.layoutInflater.inflate(R.layout.recyclerview_groupheader, parent, false)
            var holder = MyViewHolder(view)
            val binding = RecyclerviewGroupheaderBinding.bind(view)
            holder.setBinding(binding)
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var myViewHolder = holder as MyViewHolder
            var nearShopBean = list[position]
            nearShopBean.apply {
                var drawable =
                    ContextCompat.getDrawable(activity, R.mipmap.image_fragment_mine_address)
                drawable?.setBounds(
                    0,
                    0,
                    DisplayUtil.dip2px(activity, 13f),
                    DisplayUtil.dip2px(activity, 15f)
                )
                myViewHolder.getBinding().detailsTv.setCompoundDrawables(drawable, null, null, null)
                myViewHolder.getBinding().tvAddress.text = name
                myViewHolder.getBinding().detailsTv.text = address
                Glide.with(activity).load(ApiService.IMAGE + logo)
                    .into(myViewHolder.getBinding().addressImageView)
                val lag: String = location[0].toString() + ""
                val lon: String = location[1].toString() + ""
                if (!StringUtils.isEmpty(mylat) && !StringUtils.isEmpty(mylon)) {
                    val dis = TencentLocationUtils.distanceBetween(
                        mylat.toDouble(),
                        mylon.toDouble(),
                        lag.toDouble(),
                        lon.toDouble()
                    )
                    if (dis > 1000) {
                        myViewHolder.getBinding().addressTextView.text =
                            "距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米"
                    } else {
                        myViewHolder.getBinding().addressTextView.text =
                            "距离您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米"
                    }
                }
                myViewHolder.getBinding().checkBox.isChecked = nearShopBean.isIscheck
                myViewHolder.getBinding().checkBox.setOnClickListener {
                    list.forEach { nearShopBean: NearShopBean ->
                        nearShopBean.isIscheck = false
                    }
                    nearShopBean.isIscheck = true
                    shopid = id
                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewGroupheaderBinding
            fun setBinding(binding: RecyclerviewGroupheaderBinding) {
                this.binding = binding
            }

            fun getBinding(): RecyclerviewGroupheaderBinding {
                return binding
            }
        }
    }
}