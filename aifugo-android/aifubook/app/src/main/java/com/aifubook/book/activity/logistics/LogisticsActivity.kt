package com.aifubook.book.activity.logistics

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean
import com.aifubook.book.api.ApiService
import com.aifubook.book.application.MyApp
import com.aifubook.book.base.IntentKey
import com.aifubook.book.databinding.ActivityLogisticsBinding
import com.aifubook.book.databinding.RecyclerviewLogisticsBinding
import com.aifubook.book.dialog.AffirmMessageDialog
import com.aifubook.book.utils.StatusBarCompat
import com.bumptech.glide.Glide
import com.jiarui.base.utils.LogUtil
import com.jiarui.base.utils.TimeUtil
import com.jiarui.base.utils.ToastUitl
import com.jiarui.base.utils.ToastUtil
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback


class LogisticsActivity : AppCompatActivity() {
    private lateinit var viewModel: LogisticsViewModel
    private lateinit var binding: ActivityLogisticsBinding
    private lateinit var logisticsAdapter: LogisticsAdapter
    private lateinit var dadaadapter: DadaLogisticsAdapter
    private var list: ArrayList<DataDTO> = ArrayList()
    private var dadalist: ArrayList<DadaResultBean> = ArrayList()
    private lateinit var logisticsAllBean: LogisticsAllBean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LogisticsViewModel::class.java]
        binding = ActivityLogisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        when (intent.getStringExtra(IntentKey.LOGISTICSTYPE)) {
            IntentKey.SHUNFENG -> {
                binding.re.visibility = View.VISIBLE
                binding.textInfo.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                logisticsAllBean =
                    intent.getSerializableExtra(IntentKey.SHUNFENG) as LogisticsAllBean
                var nu = intent.getStringExtra(IntentKey.LOGISTICNO)
                scopelauncher(nu)
            }
            IntentKey.DADA -> {
                dadalist = intent.getSerializableExtra(IntentKey.DADA) as ArrayList<DadaResultBean>
                binding.re.visibility = View.GONE
                binding.textInfo.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                dadaaddFirst()
                dadaadapter = DadaLogisticsAdapter(this, dadalist)
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@LogisticsActivity)
                    adapter = dadaadapter
                }
            }
            IntentKey.NOTMAKESCENE -> {
                binding.re.visibility = View.GONE
                binding.textInfo.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.textInfo.text = "商家已发货，正在通知快递取件"
            }
        }
        binding.header.textview.text = "物流详情"
        binding.header.leftImg.setOnClickListener {
            finish()
        }
        StatusBarCompat.translucentStatusBar(this)
    }

    private fun dadaaddFirst() {
        //已下单构造
        var resultDTO =
            DadaResultBean()
        resultDTO.orderStatus = -1
        if (dadalist.size > 0) {
            resultDTO.dadaUpdateTime = dadalist[0].dadaUpdateTime
            resultDTO.dmName = dadalist[0].dmName
            resultDTO.dmMobile = dadalist[0].dmMobile
        } else {
            resultDTO.dadaUpdateTime = 0
            resultDTO.dmName = ""
            resultDTO.dmMobile = ""
        }
        dadalist.add(resultDTO)
    }

    private fun initRecyclerView() {
        logisticsAdapter = LogisticsAdapter(this, list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LogisticsActivity)
            adapter = logisticsAdapter
        }
    }

    private fun clipData(string: String) {
        val cm: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText(null, string)
        cm.setPrimaryClip(mClipData)
        ToastUitl.showShort(MyApp.getInstance(), string + "复制成功")
    }

    private fun callphone(phone: String) {
        val affirmMessageDialog = AffirmMessageDialog(this)
        affirmMessageDialog.onClickListener = View.OnClickListener { v ->
            if (v.id == R.id.dialog_affirm_btn) {
                PermissionX.init(this).permissions(Manifest.permission.CALL_PHONE).request(
                    RequestCallback { allGranted, grantedList, deniedList ->
                        if (allGranted) {
                            val intent = Intent(Intent.ACTION_CALL)
                            val data = Uri.parse("tel:$phone")
                            intent.data = data
                            startActivity(intent)
                            affirmMessageDialog.dismiss()
                        } else {
                            ToastUtil.showToast("开启电话权限才能拨打客服热线", false)
                        }
                    })
            }
        }
        affirmMessageDialog.show("是否拨打客服热线？")
    }

    private fun scopelauncher(nu: String?) {
//        lifecycleScope.launch {
//            viewModel.logisticsBean.collect {
        logisticsAllBean.apply {
            Glide.with(this@LogisticsActivity).load(ApiService.IMAGE + company.logo)
                .into(binding.imageView)
            binding.name.text = company.name
            binding.code.text = nu
            binding.callphone.setOnClickListener {
                if (company.phone==null){
                    ToastUitl.showShort(MyApp.getInstance(), "当前物流电话号码为空")
                    return@setOnClickListener
                }
                callphone(company.phone)
            }
            binding.copy.setOnClickListener {
                clipData(binding.code.text.toString() + "")
            }
            list.clear()
            list.addAll(info.data)
            //已下单构造
            var dataDTO = DataDTO()
            dataDTO.statusCode = "-1"
            if (list.size > 0) {
                dataDTO.ftime = list[0].ftime
            } else {
                dataDTO.ftime = ""
            }
            dataDTO.context = "商品已下单"
            list.add(dataDTO)
            initRecyclerView()
        }
    }
//        }
//    }

//    private fun getDatas() {
//        ////GET {{baseUrl}}/order/expressInfo?expressCompanyCode=shunfeng&expressNo=SF1340254693978
//        var map = HashMap<String, String>()
//        map.put("expressCompanyCode", "shunfeng")
//        map.put("expressNo", "SF1340254693978")
//        viewModel.getlogisticbean(map)
//    }

    class LogisticsAdapter(
        var activity: LogisticsActivity,
        var list: ArrayList<DataDTO>
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view =
                activity.layoutInflater.inflate(R.layout.recyclerview_logistics, parent, false)
            val holder = LogisticViewHolder(view).apply {
                setBinding(RecyclerviewLogisticsBinding.bind(view))
            }
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            /*
            * https://api.kuaidi100.com/document/5f0ffb5ebc8da837cbd8aefc
            * 运单快递状态
            * 1	揽收	快件揽件
            * 101	已下单	已经下快件单
            102	待揽收	待快递公司揽收
            103	已揽收	快递公司已经揽收
            *
            * 5	派件	快件正在派件
              501	投柜或驿站	快件已经投递到快递柜或者快递驿站
              *
            * 签收	3	签收	快件已签收
            301	本人签收	收件人正常签收
            302	派件异常后签收	快件显示派件异常，但后续正常签收
            303	代签	快件已被代签
            304	投柜或站签收	快件已由快递柜或者驿站签收
            * */
            var imageviewId: Int = R.mipmap.gray_logistics_circle
            var textviewString: String = ""
            var isshowName = false
            var logisticViewHolder = holder as LogisticViewHolder
            var data = list[position]
            when (data.statusCode) {
                "-1" -> {
                    imageviewId = R.mipmap.yixiadan
                    textviewString = "已下单"
                    isshowName = true
                }
                "102" -> {
                    imageviewId = R.mipmap.yifahuo
                    textviewString = "已发货"
                    isshowName = true
                }
                "103", "1" -> {
                    imageviewId = R.mipmap.yilanjian
                    textviewString = "已揽件"
                    isshowName = true
                }
                "5", "501" -> {
                    imageviewId = R.mipmap.paisongzhong
                    textviewString = "派送中"
                    isshowName = true
                }
                "3", "301", "302", "303", "304" -> {
                    imageviewId = R.mipmap.yiqianshou
                    textviewString = "已签收"
                    isshowName = true
                }
            }
            logisticViewHolder.getBinding().name.apply {
                visibility = if (isshowName) View.VISIBLE else View.GONE
                text = textviewString
            }
            logisticViewHolder.getBinding().imageView.setImageResource(imageviewId)
            logisticViewHolder.getBinding().time.text = data.ftime
            logisticViewHolder.getBinding().content.text = data.context
            when (position) {
                0 -> logisticViewHolder.getBinding().view1.visibility = View.INVISIBLE
                list.size - 1 -> logisticViewHolder.getBinding().view2.visibility = View.INVISIBLE
                else -> {
                    logisticViewHolder.getBinding().view1.visibility = View.VISIBLE
                    logisticViewHolder.getBinding().view2.visibility = View.VISIBLE
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class LogisticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewLogisticsBinding
            fun setBinding(binding: RecyclerviewLogisticsBinding) {
                this.binding = binding
            }

            fun getBinding(): RecyclerviewLogisticsBinding {
                return binding
            }
        }
    }

    class DadaLogisticsAdapter(
        var activity: LogisticsActivity,
        var list: ArrayList<DadaResultBean>
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view =
                activity.layoutInflater.inflate(R.layout.recyclerview_logistics, parent, false)
            val holder = LogisticViewHolder(view).apply {
                setBinding(RecyclerviewLogisticsBinding.bind(view))
            }
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            /*
            * https://api.kuaidi100.com/document/5f0ffb5ebc8da837cbd8aefc
           orderStatus (integer, optional): 订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5,
           * 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000 可参考文末的状态说明） ,
            * */
            var imageviewId: Int = R.mipmap.gray_logistics_circle
            var textviewString: String = ""
            var isshowName = false
            var logisticViewHolder = holder as LogisticViewHolder
            var data = list[position]
            when (data.orderStatus) {
                -1 -> {
                    imageviewId = R.mipmap.yixiadan
                    textviewString = "已下单"
                    isshowName = true
                }
                3 -> {
                    imageviewId = R.mipmap.yifahuo
                    textviewString = "配送中"
                    isshowName = true
                }
                1 -> {
                    imageviewId = R.mipmap.yilanjian
                    textviewString = "待接单"
                    isshowName = true
                }
                2 -> {
                    imageviewId = R.mipmap.paisongzhong
                    textviewString = "待取货"
                    isshowName = true
                }
                4 -> {
                    imageviewId = R.mipmap.yiqianshou
                    textviewString = "已完成"
                    isshowName = true
                }
            }
            logisticViewHolder.getBinding().name.apply {
                visibility = if (isshowName) View.VISIBLE else View.GONE
                text = textviewString
            }
            logisticViewHolder.getBinding().imageView.setImageResource(imageviewId)
            logisticViewHolder.getBinding().time.text = TimeUtil.formatMsecConvertTime(data.dadaUpdateTime)
            logisticViewHolder.getBinding().content.text = data.dmName + "  "+data.dmMobile
            when (position) {
                0 -> logisticViewHolder.getBinding().view1.visibility = View.INVISIBLE
                list.size - 1 -> logisticViewHolder.getBinding().view2.visibility = View.INVISIBLE
                else -> {
                    logisticViewHolder.getBinding().view1.visibility = View.VISIBLE
                    logisticViewHolder.getBinding().view2.visibility = View.VISIBLE
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class LogisticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewLogisticsBinding
            fun setBinding(binding: RecyclerviewLogisticsBinding) {
                this.binding = binding
            }

            fun getBinding(): RecyclerviewLogisticsBinding {
                return binding
            }
        }
    }
}