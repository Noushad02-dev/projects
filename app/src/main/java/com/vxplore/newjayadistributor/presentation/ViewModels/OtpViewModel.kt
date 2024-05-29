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
class OtpViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val otp = mutableStateOf("")
    private val mobile = mutableStateOf("")
    private val loading = mutableStateOf(false)
    private val numberState = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.otp -> {
                otp.value = arg as String
            }
            MyDataIds.resend->{
                reSend()
            }

            MyDataIds.verify -> {
                verify()
              /*  if ( otp.value.isNotEmpty()) {
                   *//* loading.value = !loading.value
                    viewModelScope.launch {
                        mobile.value = repo.getMobileNo()!!
                        try {
                            val response = repo.otpVerify(mobile.value, otp.value)
                            if (response?.status == true) {
                                repo.saveUser(response.user_id)
                                repo.saveUser(response.user_id)
                                toast(response.message)
                                viewModelScope.launch {
                                    // delay(3000)
                                    repo.setIsLoggedIn(true)
                                    repo.saveUser(response.user_id)
                                    Log.d("ncj",response.user_id)
                                    repo.setEmailId(response.email)
                                    Log.d("ncj",response.email)
                                    repo.setName(response.name)
                                    Log.d("ncj",response.name)
                                    repo.setPassCode(response.password)
                                    Log.d("ncj",response.password)
                                    navigation {
                                        navigate(Routes.home.full)
                                        //
                                        // loading.value = !loading.value
                                        *//**//* {
                                             popUpTo(Routes.login.full)
                                         }*//**//*
                                    }
                                }
                            }else {
                                if (response != null) {
                                    toast(response.message)
                                }
                            }
                        } catch (e: Exception) {
                            // Handle exception
                        } finally {
                            loading.value = false
                        }
                    }*//*
                    verify()
                } else {
                    toast("Please enter valid OTP")
                }*/
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.otp to otp,
            MyDataIds.loading to loading,
            MyDataIds.numberState to numberState,
        )
        viewModelScope.launch {
            numberState.value = repo.getMobileNo()!!
            Log.d("jbvg",numberState.value)
        }
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
    }
    private fun reSend(){
        viewModelScope.launch {
            mobile.value = repo.getMobileNo()!!
            val response = repo.mobileVerify(mobile.value)
            if (response?.status == true){
                toast(response.message)
            }
        }
    }

    private fun verify(){
        loading.value = !loading.value
        viewModelScope.launch {
            mobile.value = repo.getMobileNo()!!
            try {
                val response = repo.otpVerify(mobile.value,otp.value)
                if (response?.status == true){
                    viewModelScope.launch {
                        // delay(3000)
                        repo.setIsLoggedIn(true)
                        repo.saveUser(response.user_id)
                        Log.d("ncj",response.user_id)
                        repo.setEmailId(response.email)
                        Log.d("ncj",response.email)
                        repo.setName(response.name)
                        Log.d("ncj",response.name)
                        repo.setPassCode(response.password)
                        Log.d("ncj",response.password)
                        navigation {
                            navigate(Routes.home.full)
                            //
                            // loading.value = !loading.value
                            /* {
                                 popUpTo(Routes.login.full)
                             }*/
                        }
                    }
                }
                else {
                    toast("Wrong email or password")
                }
            } catch (e: Exception) {
                //handleNoConnectivity()
                Log.d("dbbdk", e.message.toString())
            }finally {
                loading.value = false
            }
        }
    }
}