package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.LocData
import com.vxplore.newjayadistributor.model.LocationDatum
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val addLocationList = mutableStateListOf<LocData>()
    private val password = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val locationSearch = mutableStateOf("")
    private val locationId = mutableStateOf("")
    private val indexRouteId = mutableStateOf(0)
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

            MyDataIds.locationSearch -> {
                locationSearch.value = arg as String
                addLocationList()
            }

            MyDataIds.addLocation -> {
                addLocation()
            }
            MyDataIds.routeId->{
                indexRouteId.value = arg as Int
                val lId = addLocationList[indexRouteId.value].location_id
                repo.setLocationId(lId)
                Log.d("cdhubgc",lId)
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.loadingState to loadingState,
            MyDataIds.locationSearch to locationSearch,
            MyDataIds.addLocationList to addLocationList,
        )
        addLocationList()
    }

    private fun addLocationList() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response =
                    repo.searchLocation(userId.value, locationSearch.value, password.value)
                if (response?.status == true) {
                    addLocationList.clear()
                    addLocationList.addAll(response.data)
                   /* val locationIds = response.data.location_id
                    repo.setLocationId(locationIds)
                    Log.d("kjicdn", locationIds)*/
                }
            } catch (e: Exception) {
                //todo
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun addLocation() {
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        locationId.value = repo.getLocationId()!!
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.saveLocation(userId.value,locationId.value,password.value)
                if (response?.status == true){
                    toast(response.message)
                    navigation {
                        navigate(Routes.deliveryPoints.full)
                    }
                }else{
                    if (response != null) {
                        toast(response.message)
                    }
                }
            }
            catch (e: Exception) {
                //todo
            } finally {
                loadingState.value = false
            }
        }
    }
}
