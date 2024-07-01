package com.example.anitest.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MyViewModel

@Composable
fun Navigation(viewModel: MyViewModel, context: Context, startDestination: String) {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController, viewModel, context, startDestination )
}