package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
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
import com.vxplore.newjayadistributor.model.CartProduct
import com.vxplore.newjayadistributor.model.OrderDetailsDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val route = mutableStateOf("")
    private val orderAmount = mutableStateOf("")
    private val count = mutableStateOf("")
    private val date = mutableStateOf("")
    private val qty = mutableStateOf("")
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

    fun setSelectedText(text: String) {
        selectedText.value = text
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
            }

            MyDataIds.addProduct -> {
                openDialog.value = false
            }

            MyDataIds.cancelDialog -> {
                openDialog.value = false
            }

            MyDataIds.partiesSearch -> {
                partiesSearch.value = arg as String
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
                        response.data.joinToString { it.order_amount_string }// Accessing order_amount_string
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
                val response = repo.confirmDispatch(password.value,orderId.value)
                if (response?.status == true){
                    toast(response.message)
                    navigation {
                        navigate(Routes.home.full)
                    }
                }else{
                    if (response != null) {
                        toast(response.message)
                    }
                }
            }catch (e: Exception) {
                Log.e("ViewModel", "Error fetching order details: ${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

}