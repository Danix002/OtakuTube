package com.example.anitest.ui.screen.sub

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackgroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimeScreen(viewModel: MyViewModel, navController: NavHostController, name: String, id: String) {
    val animeInfoTrailer by viewModel.animeInfoTrailer.collectAsState()
    val animeInfo by viewModel.animeInfo.collectAsState()

    LaunchedEffect(Unit) {
        // Collect the flow from getAnimeInformations and update the state
        viewModel.getAnimeInfoTrailer(name)
        // Call getAnime function to fetch the anime information
        viewModel.getAnimeInfo(id)
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
                animeInfo?.let { Text(text = it.about) }
            }
        })
    }
}

