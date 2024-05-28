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
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private var passwordVisibility = mutableStateOf(false)
    private val password = mutableStateOf("")
    private val recoverPassword = mutableStateOf("")
    private val confirmPassword = mutableStateOf("")
    private val username = mutableStateOf("")
    private val recoverUsername = mutableStateOf("")
    private val recoverPasswordDialog = mutableStateOf(false)
    private val otpInput = mutableStateOf("")
    private val loading = mutableStateOf(false)
    private val recoverLoading = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.username -> {
                username.value = arg as String
            }

            MyDataIds.password -> {
                password.value = arg as String
            }

            MyDataIds.passwordVisibility -> {
                passwordVisibility.value = !passwordVisibility.value
            }

            MyDataIds.signUpClick -> {
                onClickedSignIn(
                )
            }

            MyDataIds.recoverPasswordClick -> {
                recoverPasswordDialog.value = true
            }

            MyDataIds.otpIntput -> {
                otpInput.value = arg as String
            }

            MyDataIds.resetPassword -> {
                onClickedReset()
            }

            MyDataIds.confirmPassword -> {
                confirmPassword.value = arg as String
            }

            MyDataIds.recoverUserName -> {
                recoverUsername.value = arg as String
            }

            MyDataIds.recoverPassword -> {
                recoverPassword.value = arg as String
            }

            MyDataIds.onDismissDialog -> {
                recoverPasswordDialog.value = false
            }

            MyDataIds.verify -> {
                recoverPass()
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }


    private fun onClickedSignIn() {
        if (!username.value.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$".toRegex())) {
            toast("Enter valid email")
            return
        }
        if (password.value.isEmpty()) {
            toast("Please provide password")
            return
        }
        userLogin()
    }

    private fun onClickedReset() {
        if (recoverUsername.value.isEmpty()) {
            toast("Please provide email")
            return
        }
        if (!recoverUsername.value.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$".toRegex())) {
            toast("Enter valid email")
            return
        }
        if (otpInput.value.isEmpty()) {
            toast("Please provide email")
            return
        }
        if (recoverPassword.value.isEmpty()) {
            toast("Please provide password")
            return
        }
        if (confirmPassword.value.isEmpty()) {
            toast("Please provide Confirm password")
            return
        }
        if (recoverPassword.value.length <= 5 || confirmPassword.value.length <= 5) {
            toast("password & Confirm password should be greater than 5")
            return
        }
        if (recoverPassword.value != confirmPassword.value) {
            toast("password and confirm password not matched")
            return
        }
        resetPassword()
    }

    init {
        setUp()
    }

    private fun setUp() {
        mapData(
            MyDataIds.passwordVisibility to passwordVisibility,
            MyDataIds.username to username,
            MyDataIds.password to password,
            MyDataIds.recoverpasswordDialog to recoverPasswordDialog,
            MyDataIds.otpIntput to otpInput,
            MyDataIds.confirmPassword to confirmPassword,
            MyDataIds.recoverUserName to recoverUsername,
            MyDataIds.recoverPassword to recoverPassword,
            MyDataIds.loading to loading,
            MyDataIds.recoverLoading to recoverLoading,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun userLogin() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.login(username.value, password.value)
                if (response != null && response.status) {
                    Log.d("hujf", response.toString())
                    loading.value = !loading.value
                    repo.saveUser(response.user_id)
                    repo.saveUser(response.user_id)
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
                } else {
                    toast("Wrong email or password")
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.d("dbbdk", e.message.toString())
            }finally {
                loading.value = false
            }
        }
    }

    private fun recoverPass() {
        viewModelScope.launch {
            try {
                val response = repo.getOtp(recoverUsername.value)
                if (response?.status == true) {
                    Log.d("hgbj", response.toString())
                    toast(response.message)
                } else {
                    toast(response!!.message)
                    toast("failed to send OTP")
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("hgbj", "Error: ${e.message}")
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            try {
                //recoverUsername.value = repo.getUserId()!!
                Log.d("dbbdk",  recoverUsername.value)
                val response =
                    repo.resetPassword(
                        recoverUsername.value,
                        otpInput.value,
                        recoverPassword.value,
                    )
                Log.d("dbbdk", response.toString())
                if (response?.status == true) {
                    withContext(Dispatchers.Main) {
                        recoverPasswordDialog.value = false
                        username.value = recoverUsername.value
                        toast(response.message)
                        clearRecoverInputField()
                    }
                } else {
                    if (response != null) {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.d("dbbdk", e.message.toString())
            }
        }
    }

    private fun clearRecoverInputField() {
        recoverUsername.value = ""
        recoverPassword.value = ""
        confirmPassword.value = ""
        otpInput.value = ""
    }
    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }
}