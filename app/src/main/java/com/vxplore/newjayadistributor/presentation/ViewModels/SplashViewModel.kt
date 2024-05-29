package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.BuildConfig
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val versionName = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.versionName to versionName,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        versionName.value = BuildConfig.VERSION_NAME
        Log.d("cdhnju",versionName.value)
        viewModelScope.launch {
            delay(3000)
            goToProperLogin()
        }
    }

    private fun goToProperLogin() {
        if (repo.getIsLoggedIn()) {
            navigation {
                navigate(Routes.home.full) {
                    popUpTo(Routes.splash.full)
                }
            }
        } else {
            navigation {
                navigate(Routes.enterNumber.full) {
                    popUpTo(Routes.splash.full)
                }
            }
        }
    }
}