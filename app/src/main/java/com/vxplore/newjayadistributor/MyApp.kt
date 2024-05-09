package com.vxplore.newjayadistributor

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.debduttapanda.j3lib.MyScreen
import com.vxplore.newjayadistributor.presentation.Screen.HomeScreen
import com.vxplore.newjayadistributor.presentation.Screen.LoginPage
import com.vxplore.newjayadistributor.presentation.Screen.SplashScreen
import com.vxplore.newjayadistributor.presentation.ViewModels.HomeViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.LoginViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.SplashViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Routes.splash.full
    ) {
        MyScreen(
            navController, Routes.splash,
            wirelessViewModel = { hiltViewModel<SplashViewModel>() }
        ) {
            SplashScreen()
        }
        MyScreen(
            navController, Routes.login,
            wirelessViewModel = { hiltViewModel<LoginViewModel>() }
        ) {
            LoginPage()
        }
        MyScreen(
            navController, Routes.home,
            wirelessViewModel = { hiltViewModel<HomeViewModel>() }
        ) {
            HomeScreen()
        }
    }
}