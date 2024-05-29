package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {

    private val opendialog = mutableStateOf(false)
    private val nameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val receivedCount = mutableStateOf("")
    private val receivedAmount = mutableStateOf("")
    private val StockCount = mutableStateOf("")
    private val StockAmount = mutableStateOf("")
    private val dueDeliveryCount = mutableStateOf("")
    private val dueDeliveryAmount = mutableStateOf("")
    private val trackCount = mutableStateOf("")
    private val trackAmount = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val user_id = mutableStateOf("")
    private val password = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)

    val receivedCountState: State<String> get() = receivedCount
    val receivedAmountState: State<String> get() = receivedAmount
    val StockCountState: State<String> get() = StockCount
    val StockAmountState: State<String> get() = StockAmount
    val dueDeliveryCountState: State<String> get() = dueDeliveryCount
    val dueDeliveryAmountState: State<String> get() = dueDeliveryAmount
    val trackCountState: State<String> get() = trackCount
    val trackAmountState: State<String> get() = trackAmount
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.logout -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.dismiss -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.Confirm -> {
                opendialog.value = !opendialog.value
                navigation {
                    navigate(Routes.enterNumber.full) {
                        popUpTo(Routes.home.full)
                    }
                }
                doLogOut()
            }

            MyDataIds.orderReceive -> {
                navigation {
                    navigate(Routes.orderReceive.full)
                }
            }

            MyDataIds.trackOrder -> {
                navigation {
                    navigate(Routes.trackOrder.full)
                }
            }

            MyDataIds.dueDelivery -> {
                navigation {
                    navigate(Routes.dueDelivery.full)
                }
            }

            MyDataIds.myStocks -> {
                navigation {
                    navigate(Routes.myStock.full)
                }
            }

            MyDataIds.placeOrder -> {
                navigation {
                    navigate(Routes.placeOrder.full)
                }
            }

            MyDataIds.myOffers -> {
                navigation {
                    navigate(Routes.myOffers.full)
                }
            }

            MyDataIds.back -> {
                popBackStack()
            }

            MyDataIds.orderReceived -> {
                navigation {
                    navigate(Routes.orderReceive.full)
                }
            }
            MyDataIds.deliveryPoints -> {
                navigation {
                    navigate(Routes.deliveryPoints.full)
                }
            }
            MyDataIds.history -> {
                navigation {
                    navigate(Routes.orderHistory.full)
                }
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
                dashboard()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.opendialog to opendialog,
            MyDataIds.nameState to nameState,
            MyDataIds.emailState to emailState,
            MyDataIds.loadingState to loadingState,
            MyDataIds.receivedCountState to receivedCountState,
            MyDataIds.receivedAmountState to receivedAmountState,
            MyDataIds.StockCountState to StockCountState,
            MyDataIds.StockAmountState to StockAmountState,
            MyDataIds.dueDeliveryCountState to dueDeliveryCountState,
            MyDataIds.dueDeliveryAmountState to dueDeliveryAmountState,
            MyDataIds.trackCountState to trackCountState,
            MyDataIds.trackAmountState to trackAmountState,
            MyDataIds.loadingState to loadingState,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        viewModelScope.launch {
            nameState.value = repo.getName()!!
            emailState.value = repo.getEmailId()!!
        }
        dashboard()
    }

    private fun doLogOut() {
        repo.setIsLoggedIn(false)
        repo.removeUser()
        navigation {
            navigate(Routes.enterNumber.full) {
                popUpTo(Routes.home.full)
            }
        }
    }

    private fun dashboard() {
        loadingState.value = true
        viewModelScope.launch {
            user_id.value = repo.getUserId()!!
            Log.d("dcyhd",user_id.value)
            password.value = repo.getPassCode()!!
            Log.d("dcyhd",password.value)
            try {
                val response =  repo.dashBoard(user_id.value,password.value)
                if (response?.status == true){
                    val list = response.data
                    receivedCount.value = list.order_received.count.toString()
                    receivedAmount.value = list.order_received.amount_string
                    Log.d("dcyhd",receivedAmount.value)
                    StockCount.value = list.my_stock.count.toString()
                    StockAmount.value = list.my_stock.amount_string
                    dueDeliveryCount.value = list.due_delivery.count.toString()
                    dueDeliveryAmount.value = list.due_delivery.amount_string
                    trackCount.value = list.track_order.count.toString()
                    trackAmount.value = list.track_order.amount_string
                }
            }catch (e: Exception) {
                handleNoConnectivity()
                Log.e("hgbj", "Error: ${e.message}")
            }finally {
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