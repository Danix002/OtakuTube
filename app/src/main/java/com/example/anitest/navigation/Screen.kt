package com.example.anitest.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object Zapping: Screen("zapping_screen")
    object Library: Screen("lib_screen")
    object Profile: Screen("profile_screen")


}