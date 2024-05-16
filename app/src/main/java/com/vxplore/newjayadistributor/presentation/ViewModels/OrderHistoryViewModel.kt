package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.DueDatum
import com.vxplore.newjayadistributor.model.HistoryDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val loadingState = mutableStateOf(false)
    private val orderHistoryList = mutableStateListOf<HistoryDatum>()
    private val indexRouteId = mutableStateOf(0)
    private val password = mutableStateOf("")
    private val userId = mutableStateOf("")
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

            MyDataIds.historyDetails -> {
                indexRouteId.value = arg as Int
                val receivedOrderId = orderHistoryList[indexRouteId.value].order_id
                repo.setOrderReceivedId(receivedOrderId)
                navigation {
                    navigate(Routes.orderDetailsHistory.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.orderHistoryList to orderHistoryList,
        )
        orderList()
    }

    private fun orderList() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.orderHistory(userId.value,password.value)
                if (response?.status == true){
                    orderHistoryList.clear()
                    orderHistoryList.addAll(response.data)
                }
            }catch (e: Exception) {
                //todo
            } finally {
                loadingState.value = false
            }
        }
    }
}