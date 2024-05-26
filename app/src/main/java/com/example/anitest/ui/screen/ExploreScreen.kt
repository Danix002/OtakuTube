package com.example.anitest.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.anitest.R
import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.CategoryRow
import com.example.myapplication.MyViewModel

@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavHostController) {
    var genresList by remember { mutableStateOf(emptyList<Genre>()) }
    LaunchedEffect(Unit) {
        genresList = viewModel.getGenres()
    }
    Scaffold (
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel , navController)
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)) {
            // SCREEN BODY
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(16.dp)
            )
            Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                genresList.forEach{
                    CategoryRow(viewModel, category = it)
                }
            }
        }
    }
}

