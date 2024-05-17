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
import com.vxplore.newjayadistributor.model.OrderDetailsDatum
import com.vxplore.newjayadistributor.model.OrderStatusDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val loadingState = mutableStateOf(false)
    private val userID = mutableStateOf("")
    private val password = mutableStateOf("")
    private val orderId = mutableStateOf("")
    private val orderAmount = mutableStateOf("")
    private val count = mutableStateOf("")
    private val date = mutableStateOf("")
    private val orderID = mutableStateOf("")
    private val orderStatus = mutableStateListOf<OrderStatusDatum>()
    private val lostInternet = mutableStateOf(false)
    val orderAmountState: State<String> get() = orderAmount
    val orderIdState: State<String> get() = orderId
    val countState: State<String> get() = count
    val dateState: State<String> get() = date
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
            MyDataIds.tryagain -> {
                lostInternet.value = false
                orderStatus()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.orderIdState to orderIdState,
            MyDataIds.orderAmountState to orderAmountState,
            MyDataIds.countState to countState,
            MyDataIds.dateState to dateState,
            MyDataIds.orderStatus to orderStatus,
            MyDataIds.lostInternet to lostInternet,
        )
        orderStatus()
    }

    private fun orderStatus() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        orderID.value = repo.getOrderReceivedId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.orderStatus(userID.value,orderID.value,password.value)
                if (response?.status == true){
                    orderId.value = response.data.joinToString { it.order_id }
                    orderAmount.value = response.data.joinToString { it.order_amount_string }
                    count.value = response.data.joinToString { it.count_order_item.toString() }
                    date.value = response.data.joinToString { it.order_date }
                    orderStatus.clear()
                    orderStatus.addAll(response.data)
                }
            }catch (e: Exception) {
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