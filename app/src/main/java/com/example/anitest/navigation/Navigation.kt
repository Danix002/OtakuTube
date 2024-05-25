package com.example.anitest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MyViewModel

@Composable
fun Navigation(viewModel: MyViewModel) {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController, viewModel)
}