package com.aifubook.book.activity.main.shopdetail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.api.ApiService
import com.aifubook.book.application.MyApp
import com.aifubook.book.base.IntentKey
import com.aifubook.book.base.ShareKey
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.ShopNew
import com.aifubook.book.bean.TypeBean
import com.aifubook.book.databinding.ActivityShopSearchBinding
import com.aifubook.book.dialog.AffirmMessageDialog
import com.aifubook.book.fragment.MessageEvent
import com.aifubook.book.home.MapLineShowActivity
import com.aifubook.book.product.ProductDetailsActivity
import com.aifubook.book.utils.SharedPreferencesUtil
import com.aifubook.book.utils.StatusBarCompat
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jiarui.base.utils.*
import com.tencent.map.geolocation.TencentLocationUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


private lateinit var shopDetailViewModel: ShopDetailViewModel
private lateinit var binding: ActivityShopSearchBinding
private var subBeanList: ArrayList<TypeBean.ChildrenBean> = ArrayList()
private var itemBeanList: ArrayList<TypeBean.ChildrenBean.ChildrenBeans> = ArrayList()
private var grade: String = ""
private lateinit var typelist: ArrayList<TypeBean>
private var shop_id: String = ""
private var subject: String = ""
private var sort: String = ""
private var categoryId = ""

//private var subItemBeanList: ArrayList<TypeBean.ChildrenBean.ChildrenBeans> = ArrayList()
private lateinit var subjectadapter: ShopDetailListActivity.SubjectAdapter
private lateinit var itemAdapter: ShopDetailListActivity.ItemAdapter
private lateinit var productAdapter: ProductAdapter
private lateinit var recommandAdapter: RecommandAdapter
private var productList: ArrayList<ProductListBean.list> = ArrayList()
private var recommendproductList: ArrayList<ProductListBean.list> = ArrayList()
private var gradelist = ArrayList<String>()

class ShopDetailListActivity : AppCompatActivity(), View.OnClickListener, SelectItem,
    ClickProductsInter, ClickRecommandInter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shopDetailViewModel = ViewModelProvider(this)[ShopDetailViewModel::class.java]
        initrecyclerviewSubject()
        initProductAdapter()
        initRecommandAdapter()
        getintentdata()
        StatusBarCompat.translucentStatusBar(this)
        observerScope()
        onclickRegister()
//        initSmartrefresh()
        binding.imageviewBackTop.setOnClickListener {
            shopFinish()
        }
        binding.imageviewBackOutside.setOnClickListener {
            shopFinish()
        }
        binding.editTextInputInclude.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchreal()
                        return true
                    }
                }
                return false
            }
        })
        initslideto()
    }

    private fun recyclerviewlistener() {
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v != null) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    loadmoredata()
                    LogUtil.d("滑动到底部了")
                }
            }
        })
    }

    private fun loadmoredata() {
        pageNo += 1
        loadDataCategoryId(pageNo, categoryId, sort, grade, subject, true)
    }

    //自定义滑动计算
    var heightscrollneed: Int = 0
    var minHeight: Int = 0
    var originHeight = 0
    var lastY = 0 //按下之后的y
    private fun initslideto() {
        originHeight = binding.cardview.layoutParams.height
        heightscrollneed =
            binding.imageviewShop.layoutParams.height + binding.cardview.layoutParams.height
        minHeight = binding.cardview.layoutParams.height //最小高度为cardview高度，cardview覆盖在头布局
        LogUtil.d("binding.imageviewShop.layoutParams.height:  " + binding.imageviewShop.layoutParams.height + "binding.cardview.layoutParams.height: " + binding.cardview.layoutParams.height)
    }

    private var keyboardshow = true
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = ev.y.toInt()
                return super.dispatchTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                var distanceY = lastY - ev.y.toInt()
                if (distanceY > 20) { //滑动则取消弹窗
                    if (binding.parentBottom.visibility == View.VISIBLE) {
                        topbargray()
                    }
                }
                if (distanceY < 0 && binding.scrollView.scrollY == 0) { //顶到头部的下滑
                    if (binding.header.layoutParams.height == originHeight) {
                        if (Math.abs(distanceY) > 200) {
                            var layoutParams = binding.header.layoutParams
                            layoutParams.height = heightscrollneed
                            binding.header.requestLayout()
                            binding.reHeaderOutside.visibility = View.GONE
                            if (binding.parentBottom.visibility == View.VISIBLE) {
                                binding.parentBottom.visibility = View.GONE
                            }
                            clearInputKeyboard()
                        }
                    }
                }
                if (distanceY > 0) { //上滑
                    if (binding.header.layoutParams.height == heightscrollneed) {
                        var layoutParams = binding.header.layoutParams
                        layoutParams.height = originHeight
                        binding.header.requestLayout()
                        binding.reHeaderOutside.visibility = View.VISIBLE
//                        binding.lineInside.visibility = View.VISIBLE
                    }
                    return super.dispatchTouchEvent(ev)
                }
                if (binding.scrollView.scrollY > 0) {
                    LogUtil.d(binding.scrollView.scrollY.toString())
                    return super.dispatchTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_UP -> {
                return super.dispatchTouchEvent(ev)
            }
        }
        return false
    }

    //更改view layout
    private fun changeLayout(distanceY: Int) {
        var layoutparams = binding.header.layoutParams
        layoutparams.height = (layoutparams.height - distanceY).toInt()
//        binding.header.layoutParams = layoutparams
        LogUtil.d("minheight=  $minHeight")
        if (binding.header.layoutParams.height < minHeight) { //最小75dp
            binding.header.layoutParams.height = minHeight
            return
        }
        if (binding.header.layoutParams.height > heightscrollneed) { //最大相加
            binding.header.layoutParams.height = heightscrollneed
            return
        }
        if (binding.header.layoutParams.height in minHeight..heightscrollneed) {
            binding.header.requestLayout()
        }
//        binding.header.requestLayout()
        // 当前view高度-初始view高度  除以需要滑动的距离
        var mcurrentDistance =
            (binding.header.layoutParams.height - binding.cardview.height) / heightscrollneed.toFloat()
        var alpha = 1 - mcurrentDistance * 10
        LogUtil.d(
            "\nbinding.header.layoutParams.height: " + binding.header.layoutParams.height +
                    "\nbinding.cardview.height:  " + binding.cardview.height +
                    "\nheightscrollneed:  " + heightscrollneed
                    + "\nmcurrentDistance:  " + mcurrentDistance
                    + "\nalpha:  " + alpha.toString()
        )
        binding.reHeaderOutside.alpha = alpha
    }

    private fun initRecommandAdapter() {
        recommandAdapter = RecommandAdapter(this, recommendproductList, this)
        binding.recyclerViewRecommand.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewRecommand.adapter = recommandAdapter
    }

    private fun initProductAdapter() {
        productAdapter = ProductAdapter(this, productList, this)
        binding.recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerviewProducts.adapter = productAdapter
    }

    private var pageNo = 1

    private fun loadRecommandData() {
        val recommandProductsMap: MutableMap<String, String> = java.util.HashMap()
        recommandProductsMap["from"] = 10.toString()
        recommandProductsMap["pageNo"] = "1"
        recommandProductsMap["pageSize"] = 20.toString()
        recommandProductsMap["categoryId"] = ""
        recommandProductsMap["shopId"] = shop_id
//        recommandProductsMap["grade"] = grade
        shopDetailViewModel.flowrequestRecommandProducts(recommandProductsMap)
    }

    /* 初始化请求的方法 */
    private fun loadDataCategoryId(
        pageNo: Int,
        categoryId: String,
        sort: String,
        grade: String,
        subject: String
    ) {
        //subject (string, optional): 科目 , grade (string, optional): 年級
        val map: MutableMap<String, String> = java.util.HashMap()
        map["offset"] = "0"
        if (categoryId != "") {
            map["categoryId"] = categoryId
            map["from"] = "2"
        }else{
            map["from"] = "6"
        }
        var keyword = binding.editTextInputInclude.text.toString()
        if (!keyword.isEmpty()) {
            map["keyword"] = keyword
        }
        map["sort"] = sort
        map["shopId"] = shop_id
        map["zeroBuy"] = ""
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = "20"
        map["grade"] = grade
        if (categoryId == "") {
            map["subject"] = subject
        }
        shopDetailViewModel.flowproducts(map)
        loadshow()
    }

    //分页请求的方法
    private fun loadDataCategoryId(
        pageNo: Int,
        categoryId: String,
        sort: String,
        grade: String,
        subject: String,
        loadmore: Boolean
    ) {
        //subject (string, optional): 科目 , grade (string, optional): 年級
        val map: MutableMap<String, String> = java.util.HashMap()
        map["offset"] = "0"
        if (categoryId != "") {
            map["categoryId"] = categoryId
            map["from"] = "2"
        }else{
            map["from"] = "6"
        }
        var keyword = binding.editTextInputInclude.text.toString()
        if (!keyword.isEmpty()) {
            map["keyword"] = keyword
        }
        map["sort"] = sort
        map["shopId"] = shop_id
        map["zeroBuy"] = "0"
        map["from"] = "6"
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = "20"
        if (categoryId == "") {
            map["grade"] = grade
            map["subject"] = subject
        }
        shopDetailViewModel.flowproducts(map)
    }

    private fun loadshow() {
        binding.load.visibility = View.VISIBLE
        binding.load.show()
        binding.recyclerviewProducts.visibility = View.INVISIBLE
    }

    private fun loadhide() {
        binding.load.visibility = View.INVISIBLE
        binding.load.hide()
        binding.recyclerviewProducts.visibility = View.VISIBLE
    }

    private var fromwhereval = ""
    private fun getintentdata() {
        //shopid必传
        shop_id = intent.extras?.getString(IntentKey.SHOPID).toString()
        shop_id?.let {
            requestServer(shop_id)
        }
        //三级分类，不传就取本地
        if (intent.extras?.getSerializable(IntentKey.CLASSFICATION) != "") {
            typelist =
                intent.extras?.getSerializable(IntentKey.CLASSFICATION) as ArrayList<TypeBean>
        } else {
            var string = SharedPreferencesUtil.get(this, ShareKey.THREECLASSFICATION, "").toString()
            val type = object : TypeToken<List<TypeBean>>() {}.type
            typelist = Gson().fromJson(string, type)
        }
        //grade必传
        grade = intent.extras?.getString(IntentKey.GRADE).toString()
        if (grade == "") {
            grade = SharedPreferencesUtil.get(this, ShareKey.GRADE, ShareKey.defautGrade)
        }
        refreshSubjectlist(typelist, grade)
        binding.textViewGradeInclude.text = grade
        // 首页学科、搜索、特价 分类fragment
        var fromwhere = intent.extras?.getString(IntentKey.FROMWHERE).toString()
        fromwhereval = fromwhere
        when (fromwhere) {
            IntentKey.FROMMAINSUBJECT -> { //首页 学科
                subject = intent.extras?.getString(IntentKey.SUBJECTNAME).toString()
                clearelements(1, "", "", subject)
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                clearInputKeyboard()
            }
            IntentKey.FROMMAININPUTTYPE -> { //首页 搜索
                var message =
                    SharedPreferencesUtil.get(
                        MyApp.getInstance(),
                        ShareKey.SEARCH,
                        ShareKey.defaultKey
                    )
                if (message.equals(ShareKey.defaultKey)) { //历史记录没数据
                    binding.constraintHistory.visibility = View.GONE
                } else {
                    initHistory() //历史记录初始化
                    binding.constraintHistory.visibility = View.VISIBLE
                }
                binding.lineInside.visibility = View.GONE
                showInputTips(binding.editTextInputInclude)
                clearelements(1, "", "", "")
                productList.clear()
                productAdapter.notifyDataSetChanged()
                loadRecommandData()
            }
            IntentKey.FROMMAINCATEGORY -> { //首页 特价图书4按钮
                categoryId = intent.extras?.getString(IntentKey.CATEGORYID).toString()
                clearelements(1, categoryId, "", "")
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                clearInputKeyboard()
            }
            IntentKey.FROMCLASSFICATION -> { //分类fragment
                categoryId = intent.extras?.getString(IntentKey.CATEGORYID).toString()
                clearelements(1, categoryId, "", "")
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                clearInputKeyboard()
            }
            IntentKey.FROMRANK -> { //销量榜单
                clearelements(1, "", "", "")
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                clearInputKeyboard()
            }
            IntentKey.FROMSHOPHOME ->{ //shophome来的
                clearelements(1, "", "", "")
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                clearInputKeyboard()
            }
        }
    }

    private fun clearelements(
        pageNovar: Int,
        categoryIdvar: String,
        sortvar: String,
        subjectvar: String
    ) {
        pageNo = pageNovar
        categoryId = categoryIdvar
        sort = sortvar
        subject = subjectvar
    }

    private fun refreshSubjectlist(typelist: ArrayList<TypeBean>, grade: String) {
        subBeanList.clear()
        for (type in typelist) {
            if (type.name.equals(grade)) {
                type.children?.let {
                    subBeanList.addAll(type.children)
                    subjectadapter.notifyDataSetChanged()
                    if (subBeanList.size > 0) {
                        itemBeanList.clear()
                        itemBeanList.addAll(subBeanList[0].children)
                        itemAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun showInputTips(et_text: EditText) {
        et_text.isFocusable = true
        et_text.isFocusableInTouchMode = true
        et_text.requestFocus()
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(et_text, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun clearInputKeyboard() {
        binding.editTextInputInclude.clearFocus()
        var inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.editTextInputInclude.windowToken, 0)
        keyboardshow = false
    }

//    private fun smartrefreshfinish() {
//        binding.smartrefresh.finishRefresh()
//        binding.smartrefresh.finishLoadMore()
//    }

    private fun shopFinish() {
        if (binding.parentBottom.visibility == View.VISIBLE) {
            binding.parentBottom.visibility = View.GONE
            return
        }
        finish()
    }

    private fun observerScope() {
        lifecycleScope.launch {
            shopDetailViewModel.getproducts.collect {
                it.result?.let {
                    if (pageNo == 1) { //筛选 搜索等按钮刷新 刷新动画
                        productList.clear()
                        loadhide()
                    }
                    productList.addAll(it.list)
                    if (productList.size == 0) { //商品数据为空，显示空界面
                        binding.reEmpty.visibility = View.VISIBLE
                    } else {
                        binding.reEmpty.visibility = View.GONE
                        if (productList.size >= 20) {
                            recyclerviewlistener()
                        } else {
                            loadRecommandData()
                        }
                    }
                    if (it.list.size == 0) { //商品搜索完毕，加载推荐
                        loadRecommandData()
                    }
                    productAdapter.notifyDataSetChanged()
                }
            }
        }
        lifecycleScope.launch {
            shopDetailViewModel.recommandProducts.collect {
                it.result?.let {
                    recommendproductList.clear()
                    recommendproductList.addAll(it.list)
                    if (recommendproductList.size > 0){
                        binding.textviewRecommand.visibility = View.VISIBLE
                    }
                    recommandAdapter.notifyDataSetChanged()
//                    smartrefreshfinish()
                }
            }
        }
        lifecycleScope.launch {
            shopDetailViewModel.addcart.collect {
                if (it.isOk) {
                    ToastUitl.showShort(MyApp.getInstance(), "加入购物车成功")
                }
            }
        }
        //init shop top
        lifecycleScope.launch {
            shopDetailViewModel.shopDetail.collect {
                it.result?.let { shopbean ->
                    try {
                        if (shopbean.workTime == null) {
                            binding.textviewShopTimeOutside.text = "营业中:全天"
                            binding.textviewContentShopTime.text = "营业中:全天"
                        }
                        if (shopbean.workTime is List<ShopNew.WorkTime>) {
                            var worktimearray = shopbean.workTime as List<ShopNew.WorkTime>
                            if (worktimearray?.size!! > 0) { //营业时间
                                var workTime = worktimearray[0]
                                binding.textviewShopTimeOutside.text =
                                    "营业中:" + workTime.startHour + "-" + workTime.endHour
                                binding.textviewContentShopTime.text =
                                    "营业中:" + workTime.startHour + "-" + workTime.endHour
                            } else {
                                binding.textviewShopTimeOutside.text = "营业中:全天"
                                binding.textviewContentShopTime.text = "营业中:全天"
                            }
                        }
                    }catch (e:Exception){
                    }
                    //店铺名称
                    binding.textviewContentShopName.text = shopbean.name
                    binding.textviewShopNameOutside.text = shopbean.name
                    Glide.with(this@ShopDetailListActivity).load(ApiService.IMAGE + shopbean.logo)
                        .into(binding.imageviewShop)
                    binding.textviewContentShopName.setOnClickListener {
                        val affirmMessageDialog = AffirmMessageDialog(this@ShopDetailListActivity)
                        affirmMessageDialog.onClickListener = View.OnClickListener { v ->
                            if (v.id == R.id.dialog_affirm_btn) {
                                val intent = Intent(Intent.ACTION_DIAL)
                                val data = Uri.parse("tel:" + shopbean.phone)
                                intent.data = data
                                startActivity(intent)
                            }
                        }
                        affirmMessageDialog.show("是否拨打书店热线？")
                    }
                    val lag: String = shopbean.location[0].toString() + ""
                    val lon: String = shopbean.location[1].toString() + ""
                    var myLong = SharedPreferencesUtil.get(
                        this@ShopDetailListActivity,
                        ShareKey.LONGTITUDE,
                        ""
                    )
                    var myLat = SharedPreferencesUtil.get(
                        this@ShopDetailListActivity,
                        ShareKey.LATITUTE,
                        ""
                    )
                    if (!myLong.isEmpty() && !myLat.isEmpty()) {
                        val dis = TencentLocationUtils.distanceBetween(
                            myLat.toDouble(),
                            myLong.toDouble(),
                            lag.toDouble(),
                            lon.toDouble()
                        )
                        // addressDis.setText("距离您" + BigDecimalUtil.divide(dis.toString(),"1000",2)+ "km");
                        if (dis > 1000) {
                            binding.textviewContentShopDistance.text =
                                "距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米"
                        } else {
                            binding.textviewContentShopDistance.text =
                                "距离您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米"
                        }
                    }
                    binding.textviewContentShopGuide.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString("lag", "" + lag)
                        bundle.putString("lon", "" + lon)
                        bundle.putString("name", "" + shopbean.name)
                        bundle.putString("add", "" + shopbean.address)
                        var intent = Intent()
                        intent.setClass(
                            this@ShopDetailListActivity,
                            MapLineShowActivity::class.java
                        )
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }
            }
        }

        //
    }

    private fun requestServer(shop_id: String) {
        var map = HashMap<String, String>()
        map.put("id", shop_id)
        shopDetailViewModel.flowShopDetail(map)
    }

    var viewOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.textview_grade1, R.id.textview_grade2, R.id.textview_grade3, R.id.textview_grade4,
            R.id.textview_grade5, R.id.textview_grade6, R.id.textview_grade7, R.id.textview_grade8,
            R.id.textview_grade9 -> {
                binding.textViewGradeInclude.text = (v as TextView).text
                gradeDialog?.dismiss()
                var grade = binding.textViewGradeInclude.text.toString()
                refreshSubjectlist(typelist, grade) //筛选
                //更新列表数据
                clearelements(1, categoryId, "", subject)
                loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
                gradeclickmorethanThree(grade)
            }
            R.id.textview_close -> gradeDialog?.dismiss()
        }
    }

    private fun gradeclickmorethanThree(grade: String) {
        gradelist.add(grade)
        var map = HashMap<String, Int>()
        for (string in gradelist) {
            var count = map[string]
            if (count == null) {
                map.put(string, 1)
            } else {
                map.put(string, count + 1)
            }
            if (count != null) {
                if (count > 1) { //why?
                    showmydialog(string)
                    gradelist.clear()
                    return
                }
            }
        }
    }

    private fun showmydialog(string: String) {
        IosDialog(this).builder().setGone()
            .setTitle("提示").setMsg("是否将您所在的年级更改为$string").setNegativeButton("取消", null)
            .setPositiveButton("确定") {
                changeGrade(string)
            }.setCancelable(true).show()
    }

    private fun changeGrade(grade: String) {
        var messageevent = MessageEvent()
        messageevent.grade = grade
        SharedPreferencesUtil.put(this, ShareKey.GRADE, grade)
        EventBus.getDefault().post(messageevent)
    }

    private var gradeDialog: AlertDialog? = null
    private var graycolor = "#999999"
    private var redcolor = "#F64A33"
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageview_back_outside, R.id.imageview_back_top -> finish() //three back buttons
            R.id.textView_grade_include -> {
                gradeDialog = gradeclick(this, viewOnClickListener)
            }
            R.id.imageView_search_include -> search()
            R.id.textview_all_inside -> toppriceupordown() //综合
            R.id.textview_rank_count_inside -> {
                if (binding.parentBottom.visibility == View.VISIBLE) { //显示弹窗 -> 关闭弹窗
                    binding.parentBottom.visibility = View.GONE
                }
                if (rankup) {
                    iconredup(binding.textviewRankCountInside)
                    icongraydown(binding.textviewSelectInside)
                    icongraydown(binding.textviewAllInside)
                    binding.textviewRankCountInside.setTextColor(Color.parseColor(redcolor))
                    binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                    binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
                    ranklist("")
                } else {
                    iconreddown(binding.textviewRankCountInside)
                    icongraydown(binding.textviewSelectInside)
                    icongraydown(binding.textviewAllInside)
                    binding.textviewRankCountInside.setTextColor(Color.parseColor(redcolor))
                    binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                    binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
                    ranklist("-soldCount")
                }
            }
            R.id.textview_select_inside -> filterDialog() //筛选
            R.id.imageView_detele -> detele()
            R.id.bottom -> {
                topbargray()
            }
            R.id.textview_up -> {
                binding.parentBottom.visibility = View.GONE
                upsearch()
                icongraydown(binding.textviewRankCountInside)
                icongraydown(binding.textviewSelectInside)
                iconredup(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(redcolor)))
            }
            R.id.textview_down -> {
                binding.parentBottom.visibility = View.GONE
                downsearch()
                icongraydown(binding.textviewRankCountInside)
                icongraydown(binding.textviewSelectInside)
                iconreddown(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(redcolor)))
            }
        }
    }

    private fun topbargray(){
        binding.parentBottom.visibility = View.GONE
        icongraydown(binding.textviewSelectInside)
        icongraydown(binding.textviewAllInside)
        binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
        binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
    }

    private var rankup = true
    private fun ranklist(sortvar: String) {
        pageNo = 1
        sort = sortvar
        sort = "-soldCount"
        loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
        rankup = !rankup
    }

    private fun iconredup(textView: TextView) {
        var drawavle = ContextCompat.getDrawable(this, R.mipmap.product_up_red)
        drawavle?.setBounds(0, 0, drawavle?.minimumWidth, drawavle?.minimumHeight)
        textView.setCompoundDrawables(null, null, drawavle, null)
    }

    private fun iconreddown(textView: TextView) {
        var drawavle = ContextCompat.getDrawable(this, R.mipmap.product_down_red)
        drawavle?.setBounds(0, 0, drawavle?.minimumWidth, drawavle?.minimumHeight)
        textView.setCompoundDrawables(null, null, drawavle, null)
    }

    private fun icongraydown(textView: TextView) {
        var drawavle = ContextCompat.getDrawable(this, R.mipmap.arrow_down_black)
        drawavle?.setBounds(0, 0, drawavle?.minimumWidth, drawavle?.minimumHeight)
        textView.setCompoundDrawables(null, null, drawavle, null)
    }

    private fun downsearch() {
        pageNo = 1
        sort = "-price"
        loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
    }

    private fun upsearch() {
        pageNo = 1
        sort = "price"
        loadDataCategoryId(pageNo, categoryId, sort, grade, subject)
    }

    override fun onBackPressed() {
        if (binding.parentBottom.visibility == View.VISIBLE) {
            binding.parentBottom.visibility = View.GONE
            return
        }
        super.onBackPressed()
    }

    private fun detele() {
        SharedPreferencesUtil.put(MyApp.getInstance().applicationContext, ShareKey.SEARCH, "")
        binding.chipGroupHistory.removeAllViews()
        binding.constraintHistory.visibility = View.GONE
    }


    private fun filterDialog() {
        if (binding.parentBottom.visibility == View.GONE) {
            binding.parentBottom.visibility = View.VISIBLE
            binding.lineAllButton.visibility = View.GONE
            binding.lineFilterButton.visibility = View.VISIBLE
            icongraydown(binding.textviewRankCountInside)
            icongraydown(binding.textviewAllInside)
            iconredup(binding.textviewSelectInside)
            binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
            binding.textviewSelectInside.setTextColor(Color.parseColor(redcolor))
            binding.textviewAllInside.setTextColor(Color.parseColor(graycolor))
        } else {
            if (binding.lineFilterButton.visibility == View.VISIBLE) { //筛选弹窗存在
                binding.parentBottom.visibility = View.GONE
                icongraydown(binding.textviewRankCountInside)
                icongraydown(binding.textviewSelectInside)
                icongraydown(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
            } else { //筛选弹窗不存在
                binding.lineAllButton.visibility = View.GONE
                binding.lineFilterButton.visibility = View.VISIBLE
                icongraydown(binding.textviewRankCountInside)
                iconreddown(binding.textviewSelectInside)
                icongraydown(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(redcolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
            }
        }
    }

    private fun toppriceupordown() {//综合
        if (binding.parentBottom.visibility == View.GONE) { //显示价格综合布局   隐藏筛选
            binding.parentBottom.visibility = View.VISIBLE
            binding.lineAllButton.visibility = View.VISIBLE
            binding.lineFilterButton.visibility = View.GONE
            icongraydown(binding.textviewRankCountInside)
            icongraydown(binding.textviewSelectInside)
            iconredup(binding.textviewAllInside)
            binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
            binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
            binding.textviewAllInside.setTextColor(ContextCompat.getColor(this, R.color.red_F64A33))
        } else {
            if (binding.lineAllButton.visibility == View.VISIBLE) { //综合弹窗存在
                binding.parentBottom.visibility = View.GONE
                icongraydown(binding.textviewRankCountInside)
                icongraydown(binding.textviewSelectInside)
                icongraydown(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(graycolor)))
            } else {  //综合弹窗不存在
                binding.lineAllButton.visibility = View.VISIBLE
                binding.lineFilterButton.visibility = View.GONE
                icongraydown(binding.textviewRankCountInside)
                icongraydown(binding.textviewSelectInside)
                iconreddown(binding.textviewAllInside)
                binding.textviewRankCountInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewSelectInside.setTextColor(Color.parseColor(graycolor))
                binding.textviewAllInside.setTextColor((Color.parseColor(redcolor)))
            }
        }
    }

    private fun initrecyclerviewClassfication(name: String) {
        binding.recyclerviewItem.visibility = View.VISIBLE
        binding.tvClassfication.visibility = View.VISIBLE
        itemAdapter = ItemAdapter(this, itemBeanList, this, name)
        var layoutManager = GridLayoutManager(this, 3)
        binding.recyclerviewItem.layoutManager = layoutManager
        binding.recyclerviewItem.adapter = itemAdapter
    }

    private fun initrecyclerviewSubject() {
        subjectadapter = SubjectAdapter(this, subBeanList)
        LogUtil.d("", subBeanList.toString())
        var layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewSubject.layoutManager = layoutManager
        binding.recyclerViewSubject.adapter = subjectadapter
        initrecyclerviewClassfication("全部")
    }

    private fun searchreal() {
        binding.constraintHistory.visibility = View.GONE
        if (binding.lineInside.visibility == View.GONE) {
            binding.lineInside.visibility = View.VISIBLE
        }
        clearInputKeyboard()
        var text = binding.editTextInputInclude.text.toString()
        if (text.isEmpty()) {
            ToastUitl.showShort(MyApp.getInstance(), "请确保搜索内容不为空")
            return
        }
        setchips(text)
        pageNo = 1
        loadDataCategoryId(pageNo, categoryId, "", grade, subject)
        binding.imageViewSearchInclude.setImageResource(R.mipmap.search_close)
        issearch = false
    }

    /*
    * 1、右边按钮搜索
    * 2、底部输入按键搜索
    * 3、搜索历史bar搜索
    * */
    private var issearch = true
    private fun search() {
        when (issearch) {
            true -> {
                searchreal()
            }
            false -> {
                if (fromwhereval.equals(IntentKey.FROMMAININPUTTYPE)) {
                    binding.constraintHistory.visibility = View.VISIBLE
                    binding.lineInside.visibility = View.GONE
                    binding.reEmpty.visibility = View.GONE
                }
                binding.editTextInputInclude.setText("")
                binding.imageViewSearchInclude.setImageResource(R.mipmap.imageview_search_red)
                issearch = true
            }
        }

    }

    private fun setchips(text: String) {
        var message =
            SharedPreferencesUtil.get(MyApp.getInstance().applicationContext, ShareKey.SEARCH, "")
        if (message.contains(",")) {
            var isexit = false
            for (string in message.split(",")) {
                if (text.equals(string)) {
                    isexit = true
                }
            }
            if (!isexit) {
                SharedPreferencesUtil.put(
                    MyApp.getInstance().applicationContext,
                    ShareKey.SEARCH,
                    message + text + ","
                )
            }
        } else {//没有第一次插入
            SharedPreferencesUtil.put(
                MyApp.getInstance().applicationContext,
                ShareKey.SEARCH,
                message + text + ","
            )
        }
        initHistory()
    }

    private fun initHistory() {
        binding.chipGroupHistory.removeAllViews()
        var message =
            SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.SEARCH, ShareKey.defaultKey)
        for (string in message.split(","))
            if (string != "") {
                binding.chipGroupHistory.addView(createChipTextView(string))
            }
    }

    private fun createChipTextView(string: String): View? {
        val chipText = Chip(this@ShopDetailListActivity)
        chipText.setTextColor(ContextUtil.getColor(R.color.black))
//        chipText.background =
        chipText.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        chipText.textSize = 12f
        chipText.setText(string)
        chipText.chipCornerRadius = 4f
        chipText.setOnClickListener { v: View? ->
            val keyWord = chipText.text.toString()
            keyWord?.isEmpty().let { //点击搜索历史标签
                binding.editTextInputInclude.setText(keyWord)
                searchreal()
            }
        }
        return chipText
    }

    private fun onclickRegister() {
        var clickArray = arrayOf(
            binding.imageviewBackOutside,
            binding.imageviewBackTop, //three back buttons
            binding.textViewGradeInclude,
            binding.imageViewSearchInclude,
            binding.textviewAllInside,
            binding.textviewRankCountInside,
            binding.textviewSelectInside, //筛选inside
            binding.imageViewDetele,//点击删除
            binding.bottom, //最底下的view
            binding.textviewUp, binding.textviewDown
        )
        for (books in clickArray) {
            books.setOnClickListener(this)
        }
    }

    var subposition = 0

    inner class SubjectAdapter(
        var activity: ShopDetailListActivity,
        var subBeanList: ArrayList<TypeBean.ChildrenBean>
    ) :
        RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                activity.layoutInflater.inflate(
                    R.layout.textview_viewholder,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var subbean = subBeanList[position]
            holder.textview.text = subbean.name
            holder.textview.setOnClickListener {
                subposition = position
                subjectadapter.notifyDataSetChanged()
                itemBeanList.clear()
                itemBeanList.addAll(subbean.children)
                itemAdapter.notifyDataSetChanged()
            }
            if (position == subposition) {
                holder.textview.setTextColor(ContextCompat.getColor(activity, R.color.red_F64A33))
                holder.textview.background =
                    ContextCompat.getDrawable(activity, R.drawable.grade_dialog_red)
            } else {
                holder.textview.setTextColor(ContextCompat.getColor(activity, R.color.black))
                holder.textview.background =
                    ContextCompat.getDrawable(activity, R.drawable.grade_dialog)
            }
        }

        override fun getItemCount(): Int {
            return subBeanList.size
        }
    }

    var itemposition = -1

    inner class ItemAdapter(
        var activity: ShopDetailListActivity,
        var itemList: ArrayList<TypeBean.ChildrenBean.ChildrenBeans>,
        var inter: SelectItem,
        var name: String
    ) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                activity.layoutInflater.inflate(
                    R.layout.textview_viewholder,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == itemList.size) { // list.size-1=position
                holder.textview.text = "全部"
                holder.textview.setOnClickListener {
                    inter.selectname(name)
                    itemposition = position
                    itemAdapter.notifyDataSetChanged()
                    LogUtil.d("itemposition click$itemposition")
                }
            } else {
                var bean = itemList[position]
                holder.textview.text = bean.name
                holder.textview.setOnClickListener {
                    inter.selectList(bean)
                    itemposition = position
                    itemAdapter.notifyDataSetChanged()
                    LogUtil.d("itemposition click$itemposition")
                }
            }
            if (position == itemposition) {
                LogUtil.d("itemposition == $itemposition")
                holder.textview.setTextColor(ContextCompat.getColor(activity, R.color.red_F64A33))
                holder.textview.background =
                    ContextCompat.getDrawable(activity, R.drawable.grade_dialog_red)
            } else {
                holder.textview.setTextColor(ContextCompat.getColor(activity, R.color.black))
                holder.textview.background =
                    ContextCompat.getDrawable(activity, R.drawable.grade_dialog)
            }
        }

        override fun getItemCount(): Int {
            return itemList.size + 1
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview = itemView.findViewById(R.id.textview_viewholder) as TextView
    }

    //筛选分类条目
    override fun selectList(childrenBeans: TypeBean.ChildrenBean.ChildrenBeans) {
        clearelements(1, childrenBeans.id.toString(), "", "")
        loadDataCategoryId(pageNo, categoryId, "", grade, subject)
        binding.parentBottom.visibility = View.GONE
    }

    //筛选分类全部
    override fun selectname(string: String) {
        clearelements(1, "", "", string)
        loadDataCategoryId(pageNo, categoryId, "", grade, subject)
        binding.parentBottom.visibility = View.GONE
    }

    override fun clickproducts(bean: ProductListBean.list) {
        val bundle = Bundle()
        bundle.putString("productId", "" + bean.id)
        var intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(this, ProductDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun clickaddCart(bean: ProductListBean.list) {
        val map: MutableMap<String, String> = java.util.HashMap()
        map["productId"] = "" + bean.id
        map["count"] = "" + 1
        shopDetailViewModel.addcart(map)
    }

    //推荐的点击商品
    override fun clickrecommandproducts(bean: ProductListBean.list) {
        val bundle = Bundle()
        bundle.putString("productId", "" + bean.id)
        var intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(this, ProductDetailsActivity::class.java)
        startActivity(intent)
    }

    //推荐的点击购物车
    override fun clickrecommandaddCart(bean: ProductListBean.list) {
        val map: MutableMap<String, String> = java.util.HashMap()
        map["productId"] = "" + bean.id
        map["count"] = "" + 1
        shopDetailViewModel.addcart(map)
    }
}