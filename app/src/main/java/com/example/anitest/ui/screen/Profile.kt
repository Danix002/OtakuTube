package com.example.anitest.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.ui.components.AppBar
import com.example.anitest.ui.components.BackGroundImage
import com.example.anitest.ui.components.BottomNavigation
import com.example.anitest.ui.components.UserProfile
import com.example.myapplication.MyViewModel

@Composable
fun ProfileScreen(viewModel: MyViewModel, navController: NavHostController) {
    Scaffold (
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel , navController)
        },
        topBar = {
            AppBar(viewModel, navController)
        }
    ) { contentPadding ->
        BackGroundImage(contentPadding, content = {
            UserProfile(viewModel)
        })
    }
}