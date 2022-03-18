package com.aifubook.book.activity.teacherrecords

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.BaseActivity
import com.aifubook.book.api.ApiService
import com.aifubook.book.databinding.ActivityTeacherRecordsBinding
import com.aifubook.book.databinding.RecyclerviewGetfreebooksRecordsBinding
import com.aifubook.book.mine.order.bean.ItemsBean
import com.aifubook.book.mine.order.bean.OrderListBean
import com.aifubook.book.utils.StatusBarCompat
import com.aifubook.book.utils.StatusBarUtil
import com.bumptech.glide.Glide
import com.jiarui.base.utils.ToastUitl
import java.util.HashMap

class TeacherRecordsActivity : BaseActivity<TeacherRecordsPresent>(), TeacherRecordsView {
    private lateinit var binding: ActivityTeacherRecordsBinding
    private var pageNo = 1
    private lateinit var present: TeacherRecordsPresent
    private lateinit var myadapter: MyAdapter
    private var itemBeanList = ArrayList<ItemsBean>()

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_records
    }

    override fun initPresenter() {
        present = TeacherRecordsPresent(this)
    }

    override fun initView() {
        binding = ActivityTeacherRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusTextColor(false, this)
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.red_F7553B))
        binding.includeHeader.tvName.setText("样书领取记录")
        binding.includeHeader.imageviewLeftWhite.setOnClickListener { finish() }
        myadapter = MyAdapter(itemBeanList)
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = myadapter
        }
        binding.smartrefresh.let {
            it.setOnRefreshListener {
                pageNo = 1
                loadData(pageNo, true)
            }
            it.setOnLoadMoreListener {
                pageNo += 1
                loadData(pageNo, false)
            }
        }
        binding.smartrefresh.autoRefresh()
        binding.imageSearch.setOnClickListener {
            pageNo = 1
            loadData(pageNo, true)
        }
    }

    private fun loadData(pageNo: Int, _isrefresh: Boolean) {
        /*
        *   Map map = new HashMap();
        map.put("logisticsType", logisticsType);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("status", status);
        * */
        var map = HashMap<String, String>()
        map.put("isZeroBuy", "1")
        map.put("pageNo", pageNo.toString())
        map.put("pageSize", "20")
        val textInputText = binding.editTextInput.text.toString()
        if (!textInputText.isEmpty()) {
            map.put("keyword", textInputText)
        }
        present.getFreeBooksRecords(map, _isrefresh)
    }

    override fun orderList(orderListBean: OrderListBean, _isrefresh: Boolean) {
        binding.smartrefresh.finishLoadMore()
        binding.smartrefresh.finishRefresh()
        if (_isrefresh){
            itemBeanList.clear()
        }
        if (orderListBean.list != null) {
            for (listbean in orderListBean.list) {
                itemBeanList.addAll(listbean.items)
            }
        }
        myadapter.notifyDataSetChanged()
    }

    override fun failed(message: String) {
        binding.smartrefresh.finishLoadMore()
        binding.smartrefresh.finishRefresh()
        message?.let {
            ToastUitl.showShort(this, message)
        }
    }

    inner class MyAdapter(var list: ArrayList<ItemsBean>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var itemView =
                layoutInflater.inflate(R.layout.recyclerview_getfreebooks_records, parent, false)
            var binding = RecyclerviewGetfreebooksRecordsBinding.bind(itemView)
            var holder = MyViewHolder(itemView)
            holder.setBinding(binding)
            return holder
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var listBean = list.get(position)
            holder.getBinding().textViewBookName.text = listBean.productName
            Glide.with(this@TeacherRecordsActivity).load(ApiService.IMAGE + listBean.productImage)
                .into(holder.getBinding().imageViewHeader)
            holder.getBinding().textviewBookGet.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "这里有个APP买教辅很方便，你试用下吧！https://www.aifubook.com/download.html"
                )
                startActivity(shareIntent)
            }
//            holder.getBinding().textviewBookInfo.text =
            //productPrice  *  commissionRate
            var count:Int = ((listBean.productPrice.toInt())*listBean.commissionRate).toInt()
//            if (count==0){
//                holder.getBinding().tvBeans.visibility = View.GONE
//            }else{
//                holder.getBinding().tvBeans.visibility = View.VISIBLE
                holder.getBinding().tvBeans.text = "赚"+count+"粉豆"
//            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewGetfreebooksRecordsBinding
            fun setBinding(binding: RecyclerviewGetfreebooksRecordsBinding) {
                this.binding = binding
            }

            fun getBinding(): RecyclerviewGetfreebooksRecordsBinding {
                return binding
            }
        }
    }

    override fun showLoading(title: String?) {

    }

    override fun stopLoading() {

    }
}