package com.example.pricearzapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pricearzapplication.Screen.HomeScree
import com.example.pricearzapplication.Screen.SplashScreen

@Composable
fun SetUpNAvGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Home.route){
            HomeScree()
        }
        composable(Screen.Splash.route){
            SplashScreen(navHostController = navController)
        }
    }

}