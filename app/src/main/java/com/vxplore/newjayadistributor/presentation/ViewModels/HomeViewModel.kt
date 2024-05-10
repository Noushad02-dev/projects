package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {

    private val opendialog = mutableStateOf(false)
    private val nameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val routeState = mutableStateOf("")
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
            MyDataIds.logout -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.dismiss -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.Confirm -> {
                opendialog.value = !opendialog.value
                navigation {
                    navigate(Routes.login.full) {
                        popUpTo(Routes.home.full)
                    }
                }
                //doLogOut()
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
            MyDataIds.myStocks->{
                navigation {
                    navigate(Routes.myStock.full)
                }
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
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun doLogOut() {
        repo.setIsLoggedIn(false)
        repo.removeUser()
        navigation {
            navigate(Routes.login.full) {
                popUpTo(Routes.home.full)
            }
        }
    }
}