package com.example.anitest.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitest.model.Anime
import com.example.myapplication.MyViewModel

@Composable
fun CategoryRow(viewModel: MyViewModel, category: String) {
    var animeList by remember { mutableStateOf(emptyList<Anime>()) }
    LaunchedEffect(category) {
        animeList = viewModel.getAnimeByGenre(0, category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Cyan)
                .wrapContentHeight() // Si adatta all'altezza del contenuto
        ) {
            Text(
                text = category,
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // Si adatta all'altezza del contenuto
            ) {
                items(animeList) { anime ->
                    AnimeCard(anime)
                }
            }
        }
    }
}