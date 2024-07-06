package com.example.anitest.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.ui.components.AppBar
import com.example.anitest.ui.components.BackGroundImage
import com.example.anitest.ui.components.BottomNavigation
import com.example.anitest.ui.components.PopularAnimeRow
import com.example.myapplication.MyViewModel

@Composable
fun ZappingScreen(viewModel: MyViewModel, navController: NavHostController) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Popular",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                PopularAnimeRow(viewModel, navController)
            }
        })
    }
}