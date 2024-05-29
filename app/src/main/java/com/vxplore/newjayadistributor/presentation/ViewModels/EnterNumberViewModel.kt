package com.vxplore.newjayadistributor.presentation.ViewModels


import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterNumberViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val number = mutableStateOf("")
    private val loading = mutableStateOf(false)
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.number -> {
                number.value = arg as String
                if (number.value.length > 10){
                    toast("Enter 10 digits numbers only")
                }
                Log.d("hbdch",number.value)
                repo.setMobileNo(number.value)
            }

            MyDataIds.btnVerify -> {
                verify()
            }
        }
    }

    private fun verify() {
        if (number.value.isNotEmpty() && number.value.length == 10) {
            loading.value = true
            viewModelScope.launch {
                try {
                    val response = repo.mobileVerify(number.value)
                    delay(3000)

                    if (response?.status == true) {
                        toast(response.message)
                        navigation {
                            navigate(Routes.otp.full)
                        }
                    } else {
                        if (response != null) {
                            toast(response.message)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("cddchnju",e.message.toString())
                } finally {
                    loading.value = false
                }
            }
        } else {
            toast("Enter valid Mobile Number")
        }
    }


    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.number to number,
            MyDataIds.loading to loading,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
    }
}
