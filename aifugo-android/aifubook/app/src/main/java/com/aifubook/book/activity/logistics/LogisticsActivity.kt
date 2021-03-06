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
                binding.textInfo.text = "??????????????????????????????????????????"
            }
        }
        binding.header.textview.text = "????????????"
        binding.header.leftImg.setOnClickListener {
            finish()
        }
        StatusBarCompat.translucentStatusBar(this)
    }

    private fun dadaaddFirst() {
        //???????????????
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
        ToastUitl.showShort(MyApp.getInstance(), string + "????????????")
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
                            ToastUtil.showToast("??????????????????????????????????????????", false)
                        }
                    })
            }
        }
        affirmMessageDialog.show("???????????????????????????")
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
                    ToastUitl.showShort(MyApp.getInstance(), "??????????????????????????????")
                    return@setOnClickListener
                }
                callphone(company.phone)
            }
            binding.copy.setOnClickListener {
                clipData(binding.code.text.toString() + "")
            }
            list.clear()
            list.addAll(info.data)
            //???????????????
            var dataDTO = DataDTO()
            dataDTO.statusCode = "-1"
            if (list.size > 0) {
                dataDTO.ftime = list[0].ftime
            } else {
                dataDTO.ftime = ""
            }
            dataDTO.context = "???????????????"
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
            * ??????????????????
            * 1	??????	????????????
            * 101	?????????	??????????????????
            102	?????????	?????????????????????
            103	?????????	????????????????????????
            *
            * 5	??????	??????????????????
              501	???????????????	????????????????????????????????????????????????
              *
            * ??????	3	??????	???????????????
            301	????????????	?????????????????????
            302	?????????????????????	????????????????????????????????????????????????
            303	??????	??????????????????
            304	??????????????????	???????????????????????????????????????
            * */
            var imageviewId: Int = R.mipmap.gray_logistics_circle
            var textviewString: String = ""
            var isshowName = false
            var logisticViewHolder = holder as LogisticViewHolder
            var data = list[position]
            when (data.statusCode) {
                "-1" -> {
                    imageviewId = R.mipmap.yixiadan
                    textviewString = "?????????"
                    isshowName = true
                }
                "102" -> {
                    imageviewId = R.mipmap.yifahuo
                    textviewString = "?????????"
                    isshowName = true
                }
                "103", "1" -> {
                    imageviewId = R.mipmap.yilanjian
                    textviewString = "?????????"
                    isshowName = true
                }
                "5", "501" -> {
                    imageviewId = R.mipmap.paisongzhong
                    textviewString = "?????????"
                    isshowName = true
                }
                "3", "301", "302", "303", "304" -> {
                    imageviewId = R.mipmap.yiqianshou
                    textviewString = "?????????"
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
           orderStatus (integer, optional): ????????????(????????????1,????????????2,????????????3,????????????4,????????????5,
           * ?????????=8,??????????????????????????????=9, ?????????????????????????????????=10, ????????????=100,????????????????????????=1000 ????????????????????????????????? ,
            * */
            var imageviewId: Int = R.mipmap.gray_logistics_circle
            var textviewString: String = ""
            var isshowName = false
            var logisticViewHolder = holder as LogisticViewHolder
            var data = list[position]
            when (data.orderStatus) {
                -1 -> {
                    imageviewId = R.mipmap.yixiadan
                    textviewString = "?????????"
                    isshowName = true
                }
                3 -> {
                    imageviewId = R.mipmap.yifahuo
                    textviewString = "?????????"
                    isshowName = true
                }
                1 -> {
                    imageviewId = R.mipmap.yilanjian
                    textviewString = "?????????"
                    isshowName = true
                }
                2 -> {
                    imageviewId = R.mipmap.paisongzhong
                    textviewString = "?????????"
                    isshowName = true
                }
                4 -> {
                    imageviewId = R.mipmap.yiqianshou
                    textviewString = "?????????"
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