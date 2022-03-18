package com.aifubook.book.activity.teacher

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.BaseActivity
import com.aifubook.book.activity.teacherfreeproductsdetails.TeacherFreeBookProductsDetailsActivity
import com.aifubook.book.activity.teacherrecords.TeacherRecordsActivity
import com.aifubook.book.api.ApiService
import com.aifubook.book.base.ShareKey
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.databinding.ActivityTeacherFreeBookBinding
import com.aifubook.book.databinding.RecyclerviewTeacherFreebooksBinding
import com.aifubook.book.utils.SharedPreferencesUtil
import com.aifubook.book.utils.StatusBarCompat
import com.aifubook.book.utils.StatusBarUtil
import com.bumptech.glide.Glide
import com.jiarui.base.utils.ToastUitl

class TeacherFreeBookActivity : BaseActivity<TeacherFreeBooksPresenter>(),
    TeacherFreeBooksListView {
    private lateinit var binding: ActivityTeacherFreeBookBinding
    private lateinit var myadapter: TeacherFreeBookAdapter
    private var pageNo = 1;
    private var list: ArrayList<ProductListBean.list> = ArrayList<ProductListBean.list>()

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_free_book
    }

    override fun initPresenter() {
        mPresenter = TeacherFreeBooksPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        binding.smartrefresh.autoRefresh()
    }

    override fun initView() {
        binding = ActivityTeacherFreeBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusTextColor(false, this)
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.red_F7553B))
        binding.includeHeader.tvName.setText("免费领取样书")
        binding.includeHeader.imageviewLeftWhite.setOnClickListener { finish() }
        binding.imageviewGetfreebooks.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, TeacherRecordsActivity::class.java)
            startActivity(intent)
        }
        myadapter = TeacherFreeBookAdapter(list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myadapter
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
        binding.imageSearch.setOnClickListener {
            pageNo = 1
            loadData(pageNo, true)
        }
    }

    private fun loadData(pageNo: Int, _isrefresh: Boolean) {
        var requestBody = initRequest(pageNo, binding.editTextInput.text.toString())
        mPresenter.productList(requestBody, _isrefresh)
    }

    override fun showLoading(title: String?) {

    }

    override fun stopLoading() {

    }

    override fun getProductBean(bean: ProductListBean?, _isrefresh: Boolean) {
        binding.smartrefresh.finishRefresh()
        binding.smartrefresh.finishLoadMore()
        if (_isrefresh) {
            list.clear()
        }
        if (bean != null) {
            list.addAll(bean.list)
        }
        myadapter.notifyDataSetChanged()
    }

    override fun GetDataFail(message: String?) {
        ToastUitl.showShort(this, message)
    }

    inner class TeacherFreeBookAdapter(var list: List<ProductListBean.list>) :
        RecyclerView.Adapter<TeacherFreeBookAdapter.TeacherFreeBookViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): TeacherFreeBookViewHolder {
            var myItemView =
                layoutInflater.inflate(R.layout.recyclerview_teacher_freebooks, parent, false)
            var holder = TeacherFreeBookViewHolder(myItemView)
            var binding = RecyclerviewTeacherFreebooksBinding.bind(myItemView)
            holder.setRecyclerViewBinding(binding)
            return holder
        }

        override fun onBindViewHolder(holder: TeacherFreeBookViewHolder, position: Int) {
            var singlebook = list.get(position)
            holder.getRecyclerViewBinding().textViewBookName.text = singlebook.name
            Glide.with(this@TeacherFreeBookActivity).load(ApiService.IMAGE + singlebook.image)
                .into(holder.getRecyclerViewBinding().imageViewHeader)
            holder.getRecyclerViewBinding().textviewBookInfo.text =
                singlebook.classes + " / " + singlebook.subject + " / " + singlebook.press
            holder.getRecyclerViewBinding().textviewBookPriceBefore.text =
                "¥ " + (singlebook.price/ApiService.onehundred)
            holder.getRecyclerViewBinding().textviewBookPriceBefore.paint.flags =
                Paint.STRIKE_THRU_TEXT_FLAG
            holder.getRecyclerViewBinding().textviewBookGet.setOnClickListener {
                //可以领取
                val bundle = Bundle()
                bundle.putString("productId", "" + singlebook.id)
                bundle.putInt("zeroBuy", singlebook.zeroBuy)
                bundle.putSerializable("productDetails", singlebook)
                startActivity(TeacherFreeBookProductsDetailsActivity::class.java, bundle)
            }
            //productPrice  *  commissionRate
            var count:Int = ((singlebook.price)*singlebook.commissionRate).toInt()
            if (count==0){
                holder.getRecyclerViewBinding().tvBeans.visibility = View.GONE
            }else{
                holder.getRecyclerViewBinding().tvBeans.visibility = View.VISIBLE
                holder.getRecyclerViewBinding().tvBeans.text = "赚"+count+"粉豆"
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class TeacherFreeBookViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            private lateinit var binding: RecyclerviewTeacherFreebooksBinding
            fun getRecyclerViewBinding(): RecyclerviewTeacherFreebooksBinding {
                return binding
            }

            fun setRecyclerViewBinding(binding: RecyclerviewTeacherFreebooksBinding) {
                this.binding = binding
            }
        }
    }

    /*
    *   status (integer, optional): 动态查询条件 状态 2上架，3下架，5删除 ,
        zeroBuy (integer, optional): 教师0元购 1:是 0:否 ,
        zeroType (integer, optional): 零元购查询类型 1:查询所有 0:查未领的
        }
    * */
    private fun initRequest(pageNo: Int, keyWord: String): HashMap<String, Any>? {
        var map = HashMap<String, Any>()
        map.put("from", 4)
        map.put("offset", 0)
        map.put("pageNo", pageNo)
        map.put("pageSize", 20)
        map.put("shopId", SharedPreferencesUtil.get(this, ShareKey.SHOPID, ""))
        map.put("status", 2)
//        map.put("zeroBuy", 1)
        map.put("zeroType", 0)
        if (!keyWord.isEmpty()) {
            map.put("keyword", keyWord)
        }
        return map
    }
}