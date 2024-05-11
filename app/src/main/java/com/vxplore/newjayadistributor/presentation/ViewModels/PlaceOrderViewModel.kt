package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
):WirelessViewModel(){
    private val selectedCategoryTabId = mutableStateOf("")
    private val productQty = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val selectedText = mutableStateOf("CB")

    fun setSelectedText(text: String) {
        selectedText.value = text
        Log.d("hcvf",selectedText.value)
    }
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
            MyDataIds.productQty->{
                productQty.value = arg as String
            }
            MyDataIds.stockUpdate->{
                navigation {
                    navigate(Routes.home.full)
                }
            }
            MyDataIds.viewCart->{
                navigation {
                    navigate(Routes.viewCart.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.productQty to productQty,
            MyDataIds.loadingState to loadingState,
        )
        setSoftInputMode(SoftInputMode.adjustPan)
    }
}