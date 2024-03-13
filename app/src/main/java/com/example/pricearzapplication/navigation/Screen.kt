package com.example.pricearzapplication.navigation

sealed class Screen(val route: String) {

    object Splash :Screen("Splash")
    object Home : Screen("Home")

}