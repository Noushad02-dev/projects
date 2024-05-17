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
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.LocationDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryPointsViewModel @Inject constructor(
    private val repo: Repository
):WirelessViewModel(){
    private val locationList = mutableStateListOf<LocationDatum>()
    private val password = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
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
           MyDataIds.addLocationClick->{
               navigation {
                   navigate(Routes.addLocation.full)
               }
           }
           MyDataIds.tryagain -> {
               lostInternet.value = false
               locationList()
           }
       }
    }
    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.locationList to locationList,
            MyDataIds.loadingState to loadingState,
            MyDataIds.lostInternet to lostInternet,
        )
        locationList()
    }
    private fun locationList(){
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.fetchLocation(userId.value,password.value)
                if (response?.status == true){
                    locationList.clear()
                    locationList.addAll(response.data)
                }
            }
            catch (e:Exception){
                handleNoConnectivity()
            }
            finally {
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