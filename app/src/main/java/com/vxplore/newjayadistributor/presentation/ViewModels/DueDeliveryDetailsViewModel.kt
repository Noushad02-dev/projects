package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DueDeliveryDetailsViewModel @Inject constructor(
):WirelessViewModel(){
    private val loadingState = mutableStateOf(false)
    private val profilename = mutableStateOf("")
    private val openDialog = mutableStateOf(false)
    private val partiesSearch = mutableStateOf("")
    private val selectedText = mutableStateOf("CB")
    private val productQty = mutableStateOf("")
    private val productPrice = mutableStateOf("")

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
            MyDataIds.profilename->{
                profilename.value = arg as String
            }
            MyDataIds.add->{
                openDialog.value = true
            }
            MyDataIds.addProduct->{
                openDialog.value = false
            }
            MyDataIds.cancelDialog->{
                openDialog.value = false
            }
            MyDataIds.partiesSearch -> {
                partiesSearch.value = arg as String
            }
            MyDataIds.productQty->{
                productQty.value = arg as String
            }
            MyDataIds.productPrice->{
                productPrice.value = arg as String
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
            )
    }
}