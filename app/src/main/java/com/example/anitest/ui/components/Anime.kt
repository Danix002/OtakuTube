package com.example.anitest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.myapplication.MyViewModel

@Composable
fun AnimeButton(name: String, onAnimeInformations: ()-> Unit) {
    val principalColor = Color(100, 70, 120)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))
            .padding(2.dp)
            .background(principalColor)
            .padding(start = 8.dp)
            .padding(bottom = 10.dp, end = 4.dp, top = 10.dp)
            .shadow(16.dp)
            .clickable { onAnimeInformations() }
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun AnimeSearchLoader(animeSearch: List<Anime>, viewModel: MyViewModel, navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(bottom = 72.dp, top = 4.dp)
                .padding(horizontal = 4.dp),
            columns = GridCells.Adaptive(132.dp),
            horizontalArrangement = Arrangement.Center,
            content = {
                itemsIndexed(animeSearch) { index, anime ->
                    AnimeCard(anime = anime, navController = navController , viewModel = viewModel, fill = false)
                }
            }
        )
    }
}


@Composable
fun AnimeSearchLoaderSkeleton(){
    val list = listOf(1,1,1,1,1,1,1,1,1,1,1,1)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(bottom = 72.dp, top = 4.dp)
                .padding(horizontal = 4.dp),
            columns = GridCells.Adaptive(132.dp),
            horizontalArrangement = Arrangement.Center,
            content = {
                itemsIndexed(list) { _, _ ->
                    Box (
                        modifier = Modifier.padding(4.dp).fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        AnimeCardSkeleton()
                    }
                }
            }
        )
    }
}
