package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.myapplication.MyViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryRow(viewModel: MyViewModel, category: Genre, navController: NavHostController) {
    val animeHashMap by viewModel.animeForGenres.collectAsState()
    var animeList by remember { mutableStateOf(emptyList<Anime>()) }

    var isLoaded by remember { mutableStateOf(false) }
    var page by remember { mutableIntStateOf(1) }
    var nothingElse by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = category) {
        if (!isLoaded) {
            viewModel.addAnimeByGenre(0, category.id)
            animeList = animeHashMap.get(category.id)!!
            isLoaded = true
        }
    }

    LaunchedEffect (page) {
        if (page > 0) {
            loading = true
            viewModel.addAnimeByGenre(page, category.id)
            animeList = animeHashMap.get(category.id)!!
            loading = false
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = category.titolo,
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(vertical = 4.dp, horizontal = 10.dp)
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if(!isLoaded) {
                items(3){
                    AnimeCardSkeleton()
                }
            }else {
                itemsIndexed(animeList) { index, anime ->
                    AnimeCard(anime, navController, viewModel)
                    if ( (index == (animeList.size-1)) ) {
                        if (!nothingElse) AnimeLoaderButton(onClick = { page++ }, loading)
                        else Text(
                            text = "No other anime :(",
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CategoryRowSkeleton() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray.copy(alpha = 0.5f))
                .padding(start = 8.dp)
                .height(32.dp)
                .width(100.dp)
                .padding(vertical = 4.dp, horizontal = 10.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
                items(3){
                    AnimeCardSkeleton()
                }
        }
    }
}
