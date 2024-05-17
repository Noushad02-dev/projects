package com.vxplore.newjayadistributor.presentation.ViewModels

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.AddProductListData
import com.vxplore.newjayadistributor.model.CartProduct
import com.vxplore.newjayadistributor.model.OrderDetailsDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DueDeliveryDetailsViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val loadingState = mutableStateOf(false)
    private val profilename = mutableStateOf("")
    private val openDialog = mutableStateOf(false)
    private val partiesSearch = mutableStateOf("")
    private val selectedText = mutableStateOf("CB")
    private val productQty = mutableStateOf("")
    private val productPrice = mutableStateOf("")
    private val orderDtls = mutableStateListOf<OrderDetailsDatum>()
    private val userID = mutableStateOf("")
    private val orderID = mutableStateOf("")
    private val password = mutableStateOf("")
    private val taxable = mutableStateOf("")
    private val tax = mutableStateOf("")
    private val discount = mutableStateOf("")
    private val total = mutableStateOf("")
    private val storeName = mutableStateOf("")
    private val orderId = mutableStateOf("")
    private val productId = mutableStateOf("")
    private val route = mutableStateOf("")
    private val orderAmount = mutableStateOf("")
    private val count = mutableStateOf("")
    private val date = mutableStateOf("")
    private val qty = mutableStateOf("")
    private val productName = mutableStateOf("")
    private val pdf = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)

    val pdfState: State<String> get() = pdf
    private val addProductList = mutableStateListOf<AddProductListData>()
    var pdfDownloadCallback: PdfDownloadCallback? = null
    val taxableState: State<String> get() = taxable
    val taxState: State<String> get() = tax
    val discountState: State<String> get() = discount
    val totalState: State<String> get() = total
    val storeNameState: State<String> get() = storeName
    val orderIdState: State<String> get() = orderId
    val routeState: State<String> get() = route
    val orderAmountState: State<String> get() = orderAmount
    val countState: State<String> get() = count
    val dateState: State<String> get() = date
    val qtyState: State<String> get() = qty
    val productNameState: State<String> get() = productName


    fun setSelectedText(text: String, id: String) {
        selectedText.value = id
        Log.d("hcvf", selectedText.value)
    }

    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.back -> {
                popBackStack()
            }

            MyDataIds.profilename -> {
                profilename.value = arg as String
            }

            MyDataIds.add -> {
                openDialog.value = true
                //fetchAddProductList()
            }

            MyDataIds.addProduct -> {
                openDialog.value = false
                addProduct()
            }

            MyDataIds.cancelDialog -> {
                openDialog.value = false
            }

            MyDataIds.partiesSearch -> {
                partiesSearch.value = arg as String
                //fetchAddProductList()
            }

            MyDataIds.search -> {
                fetchAddProductList()
            }

            MyDataIds.productQty -> {
                productQty.value = arg as String
            }

            MyDataIds.productPrice -> {
                productPrice.value = arg as String
            }

            MyDataIds.confirmDispatch -> {
                confirmDispatchOrder()
            }

            MyDataIds.remove -> {
                val (clickedRouteIndex, clickedProductId) = arg as Pair<Int, String>
                val clickedRoute = orderDtls.getOrNull(clickedRouteIndex)
                if (clickedRoute != null) {
                    val orderId = clickedRoute.order_id
                    repo.setOrderReceivedId(orderId)
                    repo.setProductId(clickedProductId)
                }
                removeProduct()
            }

            MyDataIds.update -> {
                val (clickedRouteIndex, clickedProductId, productsName) = arg as Triple<Int, String, String>
                val clickedRoute = orderDtls.getOrNull(clickedRouteIndex)
                if (clickedRoute != null) {
                    val orderId = clickedRoute.order_id
                    repo.setOrderReceivedId(orderId)
                    repo.setProductId(clickedProductId)
                    productName.value = productsName
                }
            }

            MyDataIds.updateQuantity -> {
                updateProduct()
            }
            MyDataIds.downloadPdf->{
                download()
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
                orderDetails()
            }
        }
    }


    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.profilename to profilename,
            MyDataIds.partiesSearch to partiesSearch,
            MyDataIds.openDialog to openDialog,
            MyDataIds.productQty to productQty,
            MyDataIds.productPrice to productPrice,
            MyDataIds.taxableState to taxableState,
            MyDataIds.taxState to taxState,
            MyDataIds.discountState to discountState,
            MyDataIds.totalState to totalState,
            MyDataIds.storeNameState to storeNameState,
            MyDataIds.orderIdState to orderIdState,
            MyDataIds.routeState to routeState,
            MyDataIds.orderAmountState to orderAmountState,
            MyDataIds.countState to countState,
            MyDataIds.dateState to dateState,
            MyDataIds.qtyState to qtyState,
            MyDataIds.ordersDetails to orderDtls,
            MyDataIds.productNameState to productNameState,
            MyDataIds.addProductList to addProductList,
            MyDataIds.pdfState to pdfState,
            MyDataIds.lostInternet to lostInternet,
        )
        orderDetails()
    }

    private fun orderDetails() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.orderDetails(userID.value, orderId.value, password.value)
                if (response?.status == true) {
                    taxable.value =
                        response.data.joinToString { it.taxable_amount_string }// Accessing order_amount_string
                    tax.value = response.data.joinToString { it.taxes_amount_string }
                    discount.value = response.data.joinToString { it.discount_amount_string }
                    total.value = response.data.joinToString { it.total_amount_string }

                    storeName.value = response.data.joinToString { it.store_name }
                    orderId.value = response.data.joinToString { it.order_id }
                    route.value = response.data.joinToString { it.route }
                    orderAmount.value = response.data.joinToString { it.order_amount_string }
                    count.value = response.data.joinToString { it.count_order_item.toString() }
                    date.value = response.data.joinToString { it.order_date }
                    qty.value =
                        response.data.joinToString { it.ordered_products.joinToString { it.quantity.toString() } }
                    orderDtls.clear()
                    orderDtls.addAll(response.data)
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun confirmDispatchOrder() {
        loadingState.value = true
        orderId.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.confirmDispatch(password.value, orderId.value)
                if (response?.status == true) {
                    toast(response.message)
                    navigation {
                        navigate(Routes.home.full)
                    }
                } else {
                    if (response != null) {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun removeProduct() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        productId.value = repo.getProductId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.removeOrderProduct(
                    userID.value,
                    orderId.value,
                    productId.value,
                    password.value
                )
                if (response?.status == true) {
                    orderDetails()
                    toast(response.message)
                } else {
                    if (response != null) {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun updateProduct() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        productId.value = repo.getProductId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.updateProduct(
                    userID.value,
                    orderId.value,
                    productId.value,
                    password.value,
                    profilename.value
                )
                if (response?.status == true) {
                    if (response?.status == true) {
                        orderDetails()
                        toast(response.message)
                    } else {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun fetchAddProductList() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.addProductList(
                    userID.value,
                    orderId.value,
                    password.value,
                    partiesSearch.value
                )
                if (response?.status == true) {
                    val pId = response.data.product_id
                    repo.setProductId(pId)
                    addProductList.clear()
                    addProductList.addAll(listOf(response.data))
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun addProduct() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        productId.value = repo.getProductId()!!
        viewModelScope.launch {
            try {
                val response = repo.addProduct(userID.value,orderId.value,password.value,productId.value, selectedText.value,productQty.value,productPrice.value)
                if (response?.status == true) {
                    if (response.status) {
                        orderDetails()
                        toast(response.message)
                    } else {
                        toast(response.message)
                    }
                }
            }catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }
    fun download() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderId.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.pdf(userID.value, orderId.value, password.value)
                if (response?.status == true) {
                    val pdfUrl = response.data
                    pdfDownloadCallback?.invoke(pdfUrl)
                    pdf.value = pdfUrl
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }
    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }
}

typealias PdfDownloadCallback = (pdfUrl: String) -> Unit