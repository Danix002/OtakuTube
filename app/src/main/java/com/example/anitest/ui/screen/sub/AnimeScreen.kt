package com.example.anitest.ui.screen.sub

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.anitest.model.AnimeInformation
import com.example.anitest.navigation.Screen
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AnimeScreen(viewModel: MyViewModel, name: String) {
    val animeInfoState = remember { mutableStateOf<List<AnimeInformation>>(emptyList()) }

    LaunchedEffect(name) {
        viewModel.getAnimeInformations(name).collectLatest { animeInfo ->
            animeInfoState.value = animeInfo
        }
    }
    //println(animeInfoState.value)

    Box {
        // SCREEN BODY
        Text(text = "anime/$name _screen")
    }
}

