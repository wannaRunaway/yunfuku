package com.aifubook.book.category

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.activity.main.shopdetail.ShopDetailListActivity
import com.aifubook.book.application.MyApp
import com.aifubook.book.base.IntentKey
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.TypeBean
import com.aifubook.book.databinding.CategoryFragmentBinding
import com.jiarui.base.utils.ToastUitl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryFragment : Fragment(), ClickCategoryInter {

    private lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: CategoryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.category_fragment, container, false)
        binding = CategoryFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        mainActivity = activity as MainActivity
        val clickintentshoplist = View.OnClickListener {
            when(it.id){
                R.id.textview_search,R.id.imageView_search_include ->{
                    val intent = Intent(context, ShopDetailListActivity::class.java).apply {
                        putExtras(Bundle().apply {
                            putSerializable(IntentKey.CLASSFICATION, mainActivity.mainTypeBeanList)
                            putString(IntentKey.SHOPID, mainActivity.shopid)
                            putString(IntentKey.GRADE, mainActivity.grade)
                            putString(IntentKey.FROMWHERE, IntentKey.FROMMAININPUTTYPE)
                        })
                    }
                    startActivity(intent)
                }
            }
        }
        binding.textviewSearch.setOnClickListener(clickintentshoplist)
        binding.imageViewSearchInclude.setOnClickListener(clickintentshoplist)
        typelistOrshopidReceived()
        binding.textViewGradeInclude.text = mainActivity.grade
        initrecyclerviewSubject()
        initrecyclerviewCategory()
        initrecyclerviewProduct()
        binding.smartrefresh.setOnRefreshListener {
            pageNo = 1
            loadData()
        }
        binding.smartrefresh.setOnLoadMoreListener {
            pageNo += 1
            loadData()
        }
        loadData()
        launcherscope()
    }

    private var productListBean: ArrayList<ProductListBean.list> = ArrayList<ProductListBean.list>()
    private fun launcherscope() {
        lifecycleScope.launch {
            viewModel.recommandProducts.collect {
                if (it.result != null) {
                    if (pageNo == 1) {
                        productListBean.clear()
                    }
                    productListBean.addAll(it.result.list)
                    productcategoryAdapter.notifyDataSetChanged()
                    smartRefreshFinish()
                }
            }
        }
    }


    private fun smartRefreshFinish() {
        binding.smartrefresh.finishRefresh()
        binding.smartrefresh.finishLoadMore()
    }

    private var pageNo = 1;
    private fun loadData() {
        if (mainActivity.shopid==null){
            ToastUitl.showShort(MyApp.getInstance(), "请等待店铺加载")
            return
        }
        val recommandProductsMap: MutableMap<String, String> = HashMap()
        recommandProductsMap["from"] = 10.toString()
        recommandProductsMap["pageNo"] = pageNo.toString()
        recommandProductsMap["pageSize"] = 20.toString()
        recommandProductsMap["categoryId"] = ""
        recommandProductsMap["shopId"] = mainActivity.shopid
//        var grade = SharedPreferencesUtil.get(context, ShareKey.GRADE, ShareKey.defautGrade)
//        LogUtil.d("首页推荐 " + grade)
//        recommandProductsMap["grade"] = grade
        viewModel.flowrequestRecommandProducts(recommandProductsMap)
    }

    private lateinit var productcategoryAdapter: ProductCategoryAdapter
    private fun initrecyclerviewProduct() {
        binding.recyclerviewProducts.layoutManager = GridLayoutManager(context, 2)
        productcategoryAdapter = ProductCategoryAdapter(activity as MainActivity, productListBean, this)
        binding.recyclerviewProducts.adapter = productcategoryAdapter
    }

    private lateinit var categoryitemadapter: CategoryItemAdapter
    private fun initrecyclerviewCategory() {
        binding.recyclerviewCategory.layoutManager = LinearLayoutManager(context)
        categoryitemadapter = CategoryItemAdapter(activity as MainActivity, mainActivity.categorylist, this)
        binding.recyclerviewCategory.adapter = categoryitemadapter
    }

    private lateinit var categorySubjectAdapter: CategorySubjectAdapter
    private fun initrecyclerviewSubject() {
        binding.recyclerViewSubject.layoutManager =
            LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
        categorySubjectAdapter =
            CategorySubjectAdapter(activity as MainActivity, mainActivity.subjectlist, this)
        binding.recyclerViewSubject.adapter = categorySubjectAdapter
    }

    fun typelistOrshopidReceived() {
        if (mainActivity.mainTypeBeanList == null) {
            return
        }
        if (mainActivity.shopid == null) {
            return
        }

    }

    /**
     * shopid 在首页加载完成
     * */
    fun mainshopidreceivedSuccess() {
        loadData()
    }

    /**
     * 点击科目
     * */
    override fun clicksubject(bean: TypeBean.ChildrenBean) {

    }

    /**
     * 点击三级分类
     * */
    override fun clickcategory(bean: TypeBean.ChildrenBean.ChildrenBeans) {

    }

    override fun recommandClick(productListBeaninListBean: ProductListBean.list) {

    }

    override fun addCartClick(productListBeaninListBean: ProductListBean.list) {

    }
}