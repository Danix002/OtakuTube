package com.example.anitest.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.anitest.navigation.Screen
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.CategoryRow
import com.example.myapplication.MyViewModel

@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavHostController) {
        Scaffold (
            bottomBar = {
                BottomNavigation(viewModel , navController )
            }
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                // SCREEN BODY
                Column () {
                    CategoryRow(viewModel, category = "action")
                }
            }
        }
}

