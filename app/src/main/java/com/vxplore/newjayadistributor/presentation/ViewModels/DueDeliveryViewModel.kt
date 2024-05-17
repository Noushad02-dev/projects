package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.DueDatum
import com.vxplore.newjayadistributor.model.OrderReceiveDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DueDeliveryViewModel @Inject constructor(
    private val repo: Repository
) :WirelessViewModel(){
    private val loadingState = mutableStateOf(false)
    private val dueDelivery = mutableStateListOf<DueDatum>()
    private val indexRouteId = mutableStateOf(0)
    private val password = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when(id){
            MyDataIds.back->{
                popBackStack()
            }
            MyDataIds.dueDetails->{
                indexRouteId.value = arg as Int
                val receivedOrderId = dueDelivery[indexRouteId.value].order_id
                repo.setOrderReceivedId(receivedOrderId)
                navigation {
                    navigate(Routes.dueDeliveryDetails.full)
                }
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
                dueDelivery()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.dueDelivery to dueDelivery,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        dueDelivery()
    }
    private fun dueDelivery() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.dueDelivery(userId.value,password.value)
                if (response?.status == true){
                    dueDelivery.clear()
                    dueDelivery.addAll(response.data)
                }else{
                    if (response != null) {
                        //toast(response.message)
                    }
                }
            }catch (e: Exception) {
                handleNoConnectivity()
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