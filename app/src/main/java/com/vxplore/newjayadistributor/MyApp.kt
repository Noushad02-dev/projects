package com.vxplore.newjayadistributor

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.debduttapanda.j3lib.MyScreen
import com.vxplore.newjayadistributor.presentation.Screen.DueDeliveryDetailsScreen
import com.vxplore.newjayadistributor.presentation.Screen.DueDeliveryScreen
import com.vxplore.newjayadistributor.presentation.Screen.HomeScreen
import com.vxplore.newjayadistributor.presentation.Screen.LoginPage
import com.vxplore.newjayadistributor.presentation.Screen.MyStockScreen
import com.vxplore.newjayadistributor.presentation.Screen.OrderDetailsScreen
import com.vxplore.newjayadistributor.presentation.Screen.OrderReceiveScreen
import com.vxplore.newjayadistributor.presentation.Screen.OrderStatusScreen
import com.vxplore.newjayadistributor.presentation.Screen.SplashScreen
import com.vxplore.newjayadistributor.presentation.Screen.TrackOrderScreen
import com.vxplore.newjayadistributor.presentation.ViewModels.DueDeliveryDetailsViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.DueDeliveryViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.HomeViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.LoginViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.MyStockViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.OrderDetailsViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.OrderReceiveViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.OrderStatusViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.SplashViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.TrackOrderViewModel

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
        MyScreen(
            navController, Routes.orderReceive,
            wirelessViewModel = { hiltViewModel<OrderReceiveViewModel>() }
        ) {
            OrderReceiveScreen()
        }
        MyScreen(
                navController, Routes.orderDetails,
        wirelessViewModel = { hiltViewModel<OrderDetailsViewModel>() }
        ) {
        OrderDetailsScreen()
    }
        MyScreen(
            navController, Routes.trackOrder,
            wirelessViewModel = { hiltViewModel<TrackOrderViewModel>() }
        ) {
            TrackOrderScreen()
        }
        MyScreen(
            navController, Routes.orderStatus,
            wirelessViewModel = { hiltViewModel<OrderStatusViewModel>() }
        ) {
            OrderStatusScreen()
        }
        MyScreen(
            navController, Routes.dueDelivery,
            wirelessViewModel = { hiltViewModel<DueDeliveryViewModel>() }
        ) {
            DueDeliveryScreen()
        }
        MyScreen(
            navController, Routes.dueDeliveryDetails,
            wirelessViewModel = { hiltViewModel<DueDeliveryDetailsViewModel>() }
        ) {
            DueDeliveryDetailsScreen()
        }
        MyScreen(
            navController, Routes.myStock,
            wirelessViewModel = { hiltViewModel<MyStockViewModel>() }
        ) {
            MyStockScreen()
        }

    }
}