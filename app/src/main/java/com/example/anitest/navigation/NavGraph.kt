package com.example.anitest.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anitest.ui.screen.HomeScreen
import com.example.anitest.ui.screen.LibScreen
import com.example.anitest.ui.screen.ProfileScreen
import com.example.anitest.ui.screen.ZappingScreen
import com.example.anitest.ui.screen.sub.AnimeScreen
import com.example.myapplication.MyViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MyViewModel,
    context: Context,
    startDestination: String
) {
    NavHost(navController,  startDestination ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(viewModel, navController)
        }
        composable(
            route = Screen.Zapping.route
        ) {
            ZappingScreen(viewModel, navController)
        }
        composable(
            route = Screen.Library.route
        ) {
            LibScreen(viewModel, navController)
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(viewModel, navController)
        }
        composable("anime/{name}_{id}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val id = backStackEntry.arguments?.getString("id") ?: ""
            AnimeScreen(viewModel, navController, name, id, context)
        }
    }
}