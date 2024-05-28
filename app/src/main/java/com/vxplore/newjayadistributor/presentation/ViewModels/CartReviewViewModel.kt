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
import com.vxplore.newjayadistributor.model.LocationDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartReviewViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val loadingState = mutableStateOf(false)
    private val cartList = mutableStateListOf<CartProduct>()
    private val userID = mutableStateOf("")
    private val cartID = mutableStateOf("")
    private val password = mutableStateOf("")
    private val taxable = mutableStateOf("")
    private val previous = mutableStateOf("")
    private val tax = mutableStateOf("")
    private val discount = mutableStateOf("")
    private val total = mutableStateOf("")
    private val cartsList = mutableStateListOf<CartProduct>()
    private val indexRouteId = mutableStateOf(0)
    private val locationList = mutableStateListOf<LocationDatum>()
    private val userId = mutableStateOf("")
    private val locationId = mutableStateOf("")
    private val index = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)


    val taxableState: State<String> get() = taxable
    val previousState: State<String> get() = previous
    val taxState: State<String> get() = tax
    val discountState: State<String> get() = discount
    val totalState: State<String> get() = total
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

            MyDataIds.addItem -> {
                navigation { navigate(Routes.placeOrder.full) }
            }

            MyDataIds.confirmPlaceOrder -> {

                placeOrder()
            }

            MyDataIds.delete -> {
                indexRouteId.value = arg as Int
                val cartId = (cartList[indexRouteId.value].cart_id)
                repo.setCartId(cartId)
                Log.d("hfuc", cartId)
                removeProduct()
            }

            MyDataIds.selectLocationId -> {
                index.value = arg as String
                locationId.value = index.value
                Log.d("yghnjg", index.value)
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
                viewCartProduct()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.cartList to cartList,
            MyDataIds.taxableState to taxableState,
            MyDataIds.previousState to previousState,
            MyDataIds.taxState to taxState,
            MyDataIds.discountState to discountState,
            MyDataIds.totalState to totalState,
            MyDataIds.location to locationList,
            MyDataIds.lostInternet to lostInternet,
        )
        viewCartProduct()
        locationList()
    }

    private fun viewCartProduct() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.showCart(userID.value, password.value)
                if (response?.status == true) {
                    taxable.value = response.taxable_amount_string
                    previous.value = response.previous_outstanding_amount_string
                    tax.value = response.tax_amount_string
                    discount.value = response.discount_amount_string
                    total.value = response.current_total_amount_string
                    cartList.clear()
                    cartList.addAll(response.cart_products)
                    loadingState.value = false
                }
            }catch (e: Exception) {
                handleNoConnectivity()
            } finally {
                loadingState.value = false
            }

        }
    }

    private fun removeProduct() {
        loadingState.value = true
        userID.value = repo.getUserId()!!
        cartID.value = repo.getCartId()!!
        viewModelScope.launch {
            try {
                val response = repo.remove(userID.value, cartID.value)
                if (response?.status == true) {
                    toast(response.message)
                    viewCartProduct()
                    loadingState.value = false
                } else {
                    if (response != null) {
                        toast(response.message)
                        loadingState.value = false
                    }
                }
            }catch (e: Exception) {
                handleNoConnectivity()
            } finally {
                loadingState.value = false
            }

        }
    }

    private fun locationList() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.fetchLocation(userId.value, password.value)
                if (response?.status == true) {
                    locationList.clear()
                    locationList.addAll(response.data)
                }
            } catch (e: Exception) {
                handleNoConnectivity()
            } finally {
                loadingState.value = false
            }
        }
    }
    private fun placeOrder() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        Log.d("bgvbvg", locationId.value)
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.placeOrder(userId.value, locationId.value,password.value)
                if (response?.status == true) {
                    navigation {
                        navigate(Routes.thankYou.full)
                    }
                }else{
                    if (response != null) {
                        toast(response.message)
                    }
                }
            }catch (e: Exception) {
                handleNoConnectivity()
            } finally {
                loadingState.value = false
                locationId.value = ""
                Log.d("dvfv", locationId.value)
            }
        }
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }
}
