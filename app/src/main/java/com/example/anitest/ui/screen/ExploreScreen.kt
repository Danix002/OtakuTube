package com.example.anitest.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.model.Genre
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackgroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.CategoryRow
import com.example.anitest.ui.componets.CategoryRowSkeleton
import com.example.myapplication.MyViewModel

@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavHostController) {
    val genresList by viewModel.genres.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setGenres()
    }

    Scaffold (
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel , navController)
        },
        topBar = {
            AppBar()
        }

    ) { contentPadding ->
            BackgroundImage(contentPadding, content = {
                Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                    if (genresList != null) {
                        genresList.forEach{
                            CategoryRow(viewModel, category = it, navController)
                        }
                    } else {
                        for (i in 1..10) {
                            CategoryRowSkeleton()
                        }
                    }
                }
            })
    }
}

