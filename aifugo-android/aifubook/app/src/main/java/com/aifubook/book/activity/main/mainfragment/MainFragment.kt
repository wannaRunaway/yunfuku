package com.aifubook.book.activity.main.mainfragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.activity.main.shopdetail.ShopDetailListActivity
import com.aifubook.book.activity.webview.TeacherCheckActivity
import com.aifubook.book.activity.webview.TeacherWebviewActivity
import com.aifubook.book.api.ApiService
import com.aifubook.book.application.MyApp
import com.aifubook.book.base.IntentKey
import com.aifubook.book.base.ShareKey
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.SceneBean
import com.aifubook.book.bean.SceneBean.ItemsDTO
import com.aifubook.book.bean.TypeBean
import com.aifubook.book.databinding.MainFragmentBinding
import com.aifubook.book.dialog.ShowReportDialog
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.catchError
import com.aifubook.book.groupon.GrouponActivity
import com.aifubook.book.home.address.ShopListActivity
import com.aifubook.book.home.shop.ShopDetailsActivity
import com.aifubook.book.keycontent.KeyCom
import com.aifubook.book.login.LoginNewActivity
import com.aifubook.book.product.ProductDetailsActivity
import com.aifubook.book.recyclerviewutil.RecyclerviewUtil
import com.aifubook.book.scan.ScanQRActivity
import com.aifubook.book.utils.LocationUtil
import com.aifubook.book.utils.SharedPreferencesUtil
import com.google.gson.Gson
import com.jiarui.base.utils.LogUtil
import com.jiarui.base.utils.StringUtils
import com.jiarui.base.utils.ToastUitl
import com.jiarui.base.utils.ToastUtil
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject

class MainFragment() : Fragment(), TencentLocationListener, MainFragmentInter {

    private lateinit var binding: MainFragmentBinding
//    private var shopId = ""
//    private var grade = ""
//    private var teacherorstudent = ""

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var subjectRecyclerViewAdapter: SubjectRecyclerViewAdapter

    //    private lateinit var subjectItemRecyclerViewAdapter: SubjectItemRecyclerViewAdapter
    private lateinit var recommandRecyclerViewAdapter: RecommandRecyclerViewAdapter

    //    private lateinit var everyOneBuysRecyclerviewAdapter: EveryOneBuysRecyclerViewAdapter
//    private var typeBeanList: ArrayList<TypeBean> = ArrayList<TypeBean>()
//    private var subBeanList: ArrayList<TypeBean.ChildrenBean> = ArrayList()

    //    private var subItemBeanList: ArrayList<TypeBean.ChildrenBean.ChildrenBeans> = ArrayList()
    private var productListBean: ArrayList<ProductListBean.list> = ArrayList<ProductListBean.list>()
    private var everyOneBuysBeanList: ArrayList<EveryOneBuys> = ArrayList()
    private var scenebeanList: ArrayList<ItemsDTO> = ArrayList()
    private lateinit var cameraResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var shopAddressResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = MainFragmentBinding.bind(view)
        mainActivity = activity as MainActivity
        //??????
        initSubjectRecyclerView()
        //?????????iconh
//        initSubjectItemRecyclerView()
        cameraResultLauncher()
        shopAddressResultLauncherCreate() //????????????
        onclick()
        verifyLocationPermissions()
        location()
        return view
    }

    private var pageNo: Int = 1
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.smartrefresh.setOnRefreshListener {
            loadbannerlistsorteveryonebuys()
        }
        binding.smartrefresh.setOnLoadMoreListener {
            loadMore()
        }
        initrecyclerview()
        lifecycleScopeLaunch()
        loadbannerlistsorteveryonebuys()
    }

    private fun loadMore() {
        pageNo += 1
        getRecommandList(pageNo)
    }

//    override fun onResume() {
//        super.onResume()
//        datagetFromSP()
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        EventBus.getDefault().register(this)
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun eventbus(event: MessageEvent) {
//        grade = event.grade
//        SharedPreferencesUtil.put(requireContext(), ShareKey.GRADE, grade)
//        onrefresh()
//    }

    //    private var shopname = ""
//    private var shopaddress = ""
    private fun shopAddressResultLauncherCreate() { //????????????
        shopAddressResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
//                    val map: MutableMap<String, String> = HashMap()
                    if (it.data?.extras?.getString(IntentKey.SHOPID)?.isEmpty() == false) {
                        mainActivity.shopid =
                            it.data?.extras?.getString(IntentKey.SHOPID).toString()
                        val shopname = it.data?.extras?.getString(IntentKey.SHOPNAME).toString()
                        val shopaddress =
                            it.data?.extras?.getString(IntentKey.SHOPADDRESS).toString()
                        binding.textviewAddress.text = shopaddress
                        binding.textviewShopname.text = shopname
                        SharedPreferencesUtil.put(
                            requireContext(),
                            ShareKey.SHOPID,
                            mainActivity.shopid
                        )
                        LogUtil.d("shopid ${mainActivity.shopid} shopname $shopname shopaddress $shopaddress")
//                        map["id"] = it.data?.getStringExtra(IntentKey.SHOPID).toString()
                    } else {
                        mainActivity.shopid =
                            "" + SharedPreferencesUtil.get(context, ShareKey.SHOPID, "")
//                        map["id"] = "" + SharedPreferencesUtil.get(context, ShareKey.SHOPID, "")
                    }
//                    viewModel.flowShopDetail(map)
                }
            }
    }

    private fun location() {
        val locationEnabled = LocationUtil.isLocationEnabled(activity)
        LogUtil.d("", "locationEnabled==$locationEnabled")
        if (!locationEnabled) {
            val dialog = ShowReportDialog()
            dialog.showLocationDialog(activity)
            dialog.setOnClickListener(object : ShowReportDialog.OnClickListener {
                override fun onConfirm() {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity!!.startActivity(intent)
                }

                override fun onCancel() {
                    LogUtil.d("", "onCancel getShopForHome")
                    var map = HashMap<String, String>()
                    viewModel.flowNearShop(map)
                }
            })
        }
    }

    private fun onclick() {
        binding.textviewShopname.setOnClickListener {
            var intent = Intent(context, ShopListActivity::class.java)
            shopAddressResultLauncher.launch(intent)
        }
        binding.imageViewScan.setOnClickListener {
            if (activity == null) {
                return@setOnClickListener
            }
            if (checkLogin()) {
                var intent = Intent()
                ToastUitl.showLong(context, "????????????")
                intent.setClass(requireContext(), LoginNewActivity::class.java)
                startActivity(intent)
                return@setOnClickListener
            }
            PermissionX.init(this@MainFragment).permissions(Manifest.permission.CAMERA).request(
                RequestCallback { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        var intent = Intent()
                        intent.setClass(
                            this@MainFragment.requireContext(),
                            ScanQRActivity::class.java
                        )
                        cameraResultLauncher.launch(intent)
                    } else {
                        ToastUtil.showToast("???????????????????????????????????????", false)
                    }
                })
        }
        //??????????????????????????????
        binding.textviewInput.setOnClickListener {
            var intent = Intent(context, ShopDetailListActivity::class.java).apply {
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

    private fun initrecyclerview() {
        activity?.let {
            recommandRecyclerViewAdapter = RecommandRecyclerViewAdapter(
                it,
                this.everyOneBuysBeanList, productListBean, scenebeanList, this, this
            )
            var layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerview.layoutManager = layoutManager
            binding.recyclerview.adapter = recommandRecyclerViewAdapter
        }
    }

//    private fun initSubjectItemRecyclerView() {
//        activity?.let {
//            subjectItemRecyclerViewAdapter =
//                SubjectItemRecyclerViewAdapter(it, subItemBeanList, this)
//            var layoutManager = LinearLayoutManager(context)
//            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//            binding.recyclerViewSubjectItem.layoutManager = layoutManager
//            binding.recyclerViewSubjectItem.adapter = subjectItemRecyclerViewAdapter
//        }
//    }

    private fun initSubjectRecyclerView() {
        activity?.let {
            subjectRecyclerViewAdapter =
                SubjectRecyclerViewAdapter(it, mainActivity.subjectlist, this)
            var layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.recyclerViewSubject.layoutManager = layoutManager
            binding.recyclerViewSubject.adapter = subjectRecyclerViewAdapter
        }
    }

    /*
    * 1?????????banner???2?????????id???3?????????
    * 2?????????????????? finish-> ??????
    * 4?????????????????????
    * ????????????????????????????????????fragment??????????????????activity onresum()??????
    * */
    fun loadbannerlistsorteveryonebuys() {
        pageNo = 1
        val map: MutableMap<String, Any> = HashMap()
        map["scene"] = 1
        viewModel.requestBannerList(map) //banner
        viewModel.requestCategoryList()  //2?????????id
        val categoryAllmap: MutableMap<String, String> = HashMap()
        viewModel.requestCategoryAllList(categoryAllmap) //3?????????
        if (mainActivity.shopid != null) {
            everyonebuysrequest(mainActivity.shopid) //????????????????????? finish -> ????????????
        }
    }

    private fun everyonebuysrequest(shopId: String) {
        //???????????????
//        val shopId = SharedPreferencesUtil.get(context, "SHOP_ID", "")
        val buylistMap: MutableMap<String, String> = HashMap()
        buylistMap["from"] = 10.toString()
        buylistMap["pageNo"] = "1"
        buylistMap["pageSize"] = "100"
        buylistMap["categoryId"] = ""
        buylistMap["shopId"] = shopId
//        grade = SharedPreferencesUtil.get(context, ShareKey.GRADE, ShareKey.defautGrade)
        buylistMap["grade"] = mainActivity.grade
        LogUtil.d("??????????????????${mainActivity.grade}")
        viewModel.flowProductbuylist(buylistMap)
    }

    fun verifyLocationPermissions() {
        PermissionX.init(this@MainFragment).permissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                val mLocationManager = TencentLocationManager.getInstance(requireContext())
                val request = TencentLocationRequest.create()
                request.requestLevel = TencentLocationRequest.REQUEST_LEVEL_NAME
                mLocationManager.requestSingleFreshLocation(
                    request,
                    this@MainFragment,
                    Looper.getMainLooper()
                )
            } else {
                ToastUtil.showToast("??????????????????????????????????????????", true)
                var map = HashMap<String, String>()
                viewModel.flowNearShop(map)
//                    val intent = Intent(
//                        activity,
//                        AddressProductActivity::class.java
//                    )
//                    intent.putExtra("choose_address", 1)
//                    startActivityForResult(intent, HomeNFragment.SHOW_ADDRESS_LOCAITON)
            }
        }
    }

    private fun getRecommandList(pageNo: Int) {
        //????????????
//        val shopId = SharedPreferencesUtil.get(context, "SHOP_ID", "")
        val recommandProductsMap: MutableMap<String, String> = HashMap()
        recommandProductsMap["from"] = 10.toString()
        recommandProductsMap["pageNo"] = pageNo.toString()
        recommandProductsMap["pageSize"] = 20.toString()
        recommandProductsMap["categoryId"] = ""
        recommandProductsMap["shopId"] = mainActivity.shopid
//        var grade = SharedPreferencesUtil.get(context, ShareKey.GRADE, ShareKey.defautGrade)
//        LogUtil.d("???????????? " + grade)
//        recommandProductsMap["grade"] = grade
        viewModel.flowrequestRecommandProducts(recommandProductsMap)
    }

    private fun lifecycleScopeLaunch() {
        lifecycleScope.launch { //everyone buys
            viewModel.productbuylist.collect {
                it?.result?.let {
                    everyOneBuysBeanList.clear()
                    var list = ArrayList<EveryOneBuys>()
                    for (everyone in it) {
                        if (everyone.productList.size != 0) {
                            list.add(everyone)
                        }
                    }
                    everyOneBuysBeanList.addAll(list)
                    getRecommandList(pageNo)
                }
            }
        }
        lifecycleScope.launch {//recommand products
            viewModel.recommandProducts.collect {
                if (it.result != null) {
                    if (pageNo == 1) {
                        productListBean.clear()
                    }
                    productListBean.addAll(it.result.list)
                    recommandRecyclerViewAdapter.notifyDataSetChanged()
                    smartRefreshFinish()
                }
            }
        }
        lifecycleScope.launch {//banner
            viewModel.bannerList.collect {
                if (it.result != null) {
                    it.result.items?.apply {
                        scenebeanList.clear()
                        scenebeanList.addAll(this)
                    }
                    recommandRecyclerViewAdapter.notifyItemChanged(0)
                }
            }
        }
        lifecycleScope.launch {//??????????????????
            viewModel.categoryAllList.collect {
                if (it.result != null) {
                    mainActivity.mainTypeBeanList.clear()
                    mainActivity.subjectlist.clear()
                    mainActivity.mainTypeBeanList.addAll(it.result)
                    (activity as MainActivity).typelistrefresh()
                    /**
                     * ???????????????????????????
                     * */
                    for (type in mainActivity.mainTypeBeanList) {
                        if (type.name.equals(mainActivity.grade)) {
                            type.children?.let {
                                val bean = TypeBean.ChildrenBean()
                                bean.name = "????????????"
                                mainActivity.subjectlist.add(bean)
                                mainActivity.subjectlist.addAll(type.children)
                            }
                        }
                    }
                    /**
                     * ???????????????????????????
                     * */
                    mainActivity.categorylist.clear()
                    val bean = TypeBean.ChildrenBean.ChildrenBeans()
                    bean.name = "??????"
                    mainActivity.categorylist.add(bean)
                    for (subitem in mainActivity.subjectlist) {
                        /**??????????????????*/
                        if (subitem.id!=null) {
                            for (categoryitem in subitem.children) {
                                /**????????????????????????????????????*/
                                var hassameelement = false
                                for (myitem in mainActivity.categorylist) {
                                    if (myitem.name.equals(categoryitem.name)) {
                                        hassameelement = true
                                        break
                                    }
                                }
                                if (!hassameelement) {
                                    mainActivity.categorylist.add(categoryitem)
                                }
                            }
                        }
                    }
                    subjectRecyclerViewAdapter.notifyDataSetChanged()
//                        subjectItemRecyclerViewAdapter.notifyDataSetChanged()
                    smartRefreshFinish()
                    //????????????????????????
                    SharedPreferencesUtil.put(
                        context,
                        ShareKey.THREECLASSFICATION,
                        Gson().toJson(mainActivity.mainTypeBeanList)
                    )
                    var jsonArray = JSONArray()
                    var j = 0
                    for (bean in it.result) {
                        val name = bean.name
                        val id = bean.id
                        try {
                            if (name.contains("??????") || name.contains("????????????")) {
                                val jsonObject = JSONObject()
                                jsonObject.put("name", name)
                                jsonObject.put("id", id)
                                jsonArray.put(j, jsonObject)
                                j++
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val gradeList = jsonArray.toString()
                    LogUtil.d("", "gradeList=$gradeList")
                    SharedPreferencesUtil.put(activity, "gradeList", gradeList)
                }
            }
        }
        lifecycleScope.launch {//books type ?????????????????????
            viewModel.categoryList.collect { it ->
                if (it?.result != null && context != null) {
                    if (it.result.size > 0) { //??????
                        spciabookid = it.result[0].id
                    }
                    if (it.result.size > 3) { //??????
                        newbookid = it.result[3].id
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.bindChief.collect {
//                ToastUtil.showToast("????????????", false)
            }
        }
        lifecycleScope.launch {
            viewModel.getNearlyShop.collect {   //???????????????shop ?????????????????????????????????????????????3?????????
                if (it?.result != null) {       //?????????????????????????????????
                    binding.textviewShopname.text = it.result.name
                    mainActivity.shopid = it.result.id.toString()
                    binding.textviewAddress.text = it.result.address
                    everyonebuysrequest(mainActivity.shopid)
                    SharedPreferencesUtil.put(context, ShareKey.SHOPID, mainActivity.shopid)
                    (activity as MainActivity).shopidloadsuccess()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.addcart.collect {
                if (it.isOk) {
                    ToastUtil.showToast("????????????????????????", false)
                }
            }
        }
    }

    //????????????????????????????????????????????????
    private fun imagecategorytoshop(id: Int) {
        if (mainActivity.shopid.equals("")) {
            ToastUitl.showShort(context, "?????????????????????????????????")
            return
        }
        var bundle = Bundle()
        bundle.putString(IntentKey.FROMWHERE, IntentKey.FROMMAINCATEGORY)
        bundle.putString(IntentKey.CATEGORYID, id.toString())
        bundle.putString(IntentKey.GRADE, mainActivity.grade)
        bundle.putString(IntentKey.SHOPID, mainActivity.shopid)
        bundle.putSerializable(IntentKey.CLASSFICATION, mainActivity.mainTypeBeanList)
        toShopDetailsListActivity(bundle)
    }

    private var newbookid = 0
    private var spciabookid = 0

    override fun onDestroy() {
        super.onDestroy()
//        binding.banner.destroy()
        EventBus.getDefault().unregister(this)
    }

    private fun smartRefreshFinish() {
        binding.smartrefresh.finishRefresh()
        binding.smartrefresh.finishLoadMore()
    }

    private fun cameraResultLauncher() {
        cameraResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val link: String? = it.data?.getStringExtra("data_return")
                    if (StringUtils.isEmpty(link)) {
                        return@registerForActivityResult
                    }
                    LogUtil.d("", "data==$link")
                    //TODO ???????????????????????????
                    // aifugo://aifubook.com/product?id=xxx&inviteCode=   ???????????????????????????
                    if (link != null) {
                        if (link.contains("aifugo://aifubook.com/product")) {
                            val l = link.indexOf("id=")
                            val r = link.indexOf("&")
                            val productId = link.substring(l + 3, r)
                            LogUtil.d("", "productId=$productId")
                            val l1 = link.indexOf("inviteCode=")
                            val r1 = link.length
                            val bundle = Bundle()
                            if (l1 + 11 != r1) {
                                val inviteCode = link.substring(l1 + 11, r1)
                                LogUtil.d("", "inviteCode=$inviteCode")
                                bundle.putString("inviteCode", inviteCode)
                            } else {
                                bundle.putString("inviteCode", "")
                            }
                            bundle.putString("productId", productId)
                            var intent = Intent()
                            intent.setClass(requireContext(), ProductDetailsActivity::class.java)
                            bundle?.let {
                                intent.putExtras(it)
                            }
                            startActivity(intent)
                        } else if (link.contains(ApiService.weburl)) {
                            if (link.contains("?")) {
                                val arr = link.split("\\?".toRegex()).toTypedArray()
                                val recognizeIntent = Intent()
                                recognizeIntent.putExtra(IntentKey.INVITECODE, arr[1])
                                recognizeIntent.setClass(
                                    requireActivity(),
                                    TeacherWebviewActivity::class.java
                                )
                                startActivity(recognizeIntent)
                            }
                        } else if (link.contains("aifugo://aifubook.com/regist")) {
                            val l = link.indexOf("inviteCode=")
                            val r = link.length
                            if (l + 11 != r) {
                                val inviteCode = link.substring(l + 11, r)
                                LogUtil.d("", "inviteCode=$inviteCode")
                                if (!StringUtils.isEmpty(inviteCode)) {
                                    //TODO ????????????????????????
                                    if (!checkLogin()) {
                                        //????????????????????????
                                        val recommendId = SharedPreferencesUtil.get(
                                            activity,
                                            KeyCom.RECOMMEND_ID,
                                            ""
                                        )
                                        if (!StringUtils.isEmpty(recommendId)) {
                                            ToastUtil.showToast("???????????????????????????", false)
                                        } else {
                                            //??????????????????
                                            val map = HashMap<String, String>()
                                            map["inviteCode"] = inviteCode
                                            viewModel.flowBindChief(map)
//                                            homeModel.bindChief(map, type_bind_chief)
                                        }
                                    } else {
                                        //????????????
                                        val bundle = Bundle()
                                        bundle.putString("inviteCode", inviteCode)
                                        var intent =
                                            Intent(requireContext(), LoginNewActivity::class.java)
                                        intent.putExtras(bundle)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    /**
     * true????????????
     * @return
     */
    protected fun checkLogin(): Boolean {
        return SharedPreferencesUtil.get(
            MyApp.getInstance().applicationContext,
            KeyCom.IS_LOGIN,
            "0"
        ) == "0"
    }

    override fun onLocationChanged(tencentLocation: TencentLocation?, p1: Int, p2: String?) {
        if (tencentLocation != null) {
            LogUtil.d(
                "http",
                tencentLocation.getLongitude()
                    .toString() + "inDownLocation" + tencentLocation.getProvider()
            )
            SharedPreferencesUtil.put(
                context,
                ShareKey.LONGTITUDE,
                "" + tencentLocation.getLongitude()
            )
            SharedPreferencesUtil.put(
                context,
                ShareKey.LATITUTE,
                "" + tencentLocation.getLatitude()
            )
            SharedPreferencesUtil.put(context, ShareKey.ADDRESS, "" + tencentLocation.getAddress())
            var map = HashMap<String, String>()
            map["longitude"] = tencentLocation.getLongitude().toString()
            map["latitude"] = tencentLocation.getLatitude().toString()
            viewModel.flowNearShop(map)
        }
    }

    override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {

    }

//    override fun everyonebusClick(everyOneBuys: EveryOneBuys) {
//        toShopDetailsListActivity(null)
//    }

    //???????????????????????????
    override fun everyonebusrecyclerviewClick(everyOneBuysProductBean: ProductListBean.list) {
        val bundle = Bundle()
        bundle.putString("productId", "" + everyOneBuysProductBean.id)
        var intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(requireContext(), ProductDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun clickgroup(imageview: ImageView) {
        var intent = Intent()
        intent.setClass(requireContext(), GrouponActivity::class.java)
        startActivity(intent)
    }

    override fun clickcategory(type: Int) {
        when (type) {
            1 -> { //????????????
                imagecategorytoshop(newbookid)
                LogUtil.d("newbookid " + newbookid)
            }
            2 -> { //????????????
                var bundle = Bundle()
                bundle.putString(IntentKey.FROMWHERE, IntentKey.FROMRANK)
                bundle.putString(IntentKey.GRADE, mainActivity.grade)
                bundle.putString(IntentKey.SHOPID, mainActivity.shopid)
                bundle.putSerializable(IntentKey.CLASSFICATION, mainActivity.mainTypeBeanList)
                toShopDetailsListActivity(bundle)
            }
            3 -> {
                imagecategorytoshop(spciabookid)
                LogUtil.d("speciabookid " + spciabookid)
            }
        }
    }

    //???????????????
    override fun recommandClick(productListBeaninListBean: ProductListBean.list) {
        val bundle = Bundle()
        bundle.putString("productId", "" + productListBeaninListBean.id)
        var intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(requireContext(), ProductDetailsActivity::class.java)
        startActivity(intent)
    }

    //???????????????
    override fun addCartClick(productListBeaninListBean: ProductListBean.list) {
        val map: MutableMap<String, String> = HashMap()
        map["productId"] = "" + productListBeaninListBean.id
        map["count"] = "" + 1
        viewModel.addcart(map)
    }

    //??????banner
    /*
    * ("???????????????????????????   0--?????????????????????,1--???????????????,2--?????????????????????")
    private int linkType;
    * */
    override fun bannerClick(data: ItemsDTO) {
        if (data.linkType == 0) {
            val bundle = Bundle()
            bundle.putString("productId", "" + data.value)
            val intent = Intent()
            intent.setClass(requireActivity(), ProductDetailsActivity::class.java)
            intent.putExtras(bundle)
            requireActivity().startActivity(intent)
        } else if (data.linkType == 2) {
            val bundle = Bundle()
            val intent = Intent()
            bundle.putString("inType", 0.toString() + "")
            bundle.putString("shopId", data.value)
            intent.setClass(requireActivity(), ShopDetailsActivity::class.java)
            intent.putExtras(bundle)
            requireActivity().startActivity(intent)
        } else if (data.linkType == 1) {
            val reviewIntent = Intent()
            reviewIntent.setClass(requireActivity(), TeacherCheckActivity::class.java)
            startActivity(reviewIntent)
        }
    }

    //????????????
    override fun subjectClick(typebeanchildrenbean: TypeBean.ChildrenBean) {
        if (mainActivity.shopid.equals("")) {
            ToastUitl.showShort(context, "?????????????????????????????????")
            return
        }
        var bundle = Bundle()
        bundle.putString(IntentKey.SUBJECTNAME, typebeanchildrenbean.name)
        bundle.putString(IntentKey.GRADE, mainActivity.grade)
        bundle.putString(IntentKey.SHOPID, mainActivity.shopid)
        bundle.putSerializable(IntentKey.CLASSFICATION, mainActivity.mainTypeBeanList)
        bundle.putString(IntentKey.FROMWHERE, IntentKey.FROMMAINSUBJECT)
        toShopDetailsListActivity(bundle)
    }

//    override fun headiconClick(typebeanchildrenbeaninsie: TypeBean.ChildrenBean.ChildrenBeans) {
//        toShopDetailsListActivity(null)
//    }

    private fun toShopDetailsListActivity(bundle: Bundle?) {
        var intent = Intent()
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        intent.setClass(requireContext(), ShopDetailListActivity::class.java)
        startActivity(intent)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtil.d("hidden: $hidden")
        if (!hidden) {
            loadbannerlistsorteveryonebuys()
        }
    }
}
