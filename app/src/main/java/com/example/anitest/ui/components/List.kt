package com.example.anitest.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.anitest.ui.theme.LightOtakuColorScheme
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryRow(viewModel: MyViewModel, category: Genre, navController: NavHostController) {
    val animeHashMap by viewModel.animeForGenres.collectAsState()
    var animeList by remember { mutableStateOf(emptyList<Anime>()) }
    val isLoaded by viewModel.isCategoryRowLoaded.collectAsState()
    var loadingCard by remember { mutableStateOf(false) }
    var loadingCategory by remember { mutableStateOf(false) }
    var page by remember { mutableIntStateOf(1) }
    val nothingElse by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!(isLoaded.get(category.id)!!)) {
            loadingCard = false
            viewModel.addAnimeByGenre(0, category.id)
            animeList = animeHashMap.get(category.id)!!
            if(animeList.isEmpty()) {
                CoroutineScope(Dispatchers.Default).launch {
                    while (animeList.isEmpty()) {
                        loadingCard = false
                        viewModel.setIsLoadedCategory(category.id, flag = false)
                        delay(30000)
                        viewModel.addAnimeByGenre(0, category.id)
                        animeList = animeHashMap.get(category.id)!!
                    }
                    loadingCard = true
                    viewModel.setIsLoadedCategory(category.id, flag = true)
                }
            }
            loadingCard = true
            viewModel.setIsLoadedCategory(category.id, flag = true)
        }else{
            loadingCard = false
            delay(3000)
            loadingCard = true
        }
        loadingCategory = isLoaded.get(category.id)!!
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
            color = LightOtakuColorScheme.onSecondary,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(LightOtakuColorScheme.secondary.copy(alpha = 0.3f))
                .padding(vertical = 4.dp, horizontal = 10.dp)
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if((!loadingCategory) || (!loadingCard)) {
                items(3){
                    AnimeCardSkeleton()
                }
            }else {
                itemsIndexed(animeList) { index, anime ->
                    AnimeCard(anime, navController, viewModel, false)
                    if ((index == (animeList.size-1))) {
                        if (!nothingElse) AnimeLoaderButton(onClick = { page++ }, loading, true)
                        else Text(
                            text = "No other anime :(",
                            modifier = Modifier.padding(start = 16.dp),
                            color = LightOtakuColorScheme.onPrimary
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

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularAnimeRow(viewModel: MyViewModel, navController: NavHostController) {
    val popular by viewModel.popularAnime.collectAsState()
    var pageAnime by remember { mutableIntStateOf(1) }
    val nothingElse by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    val isLoaded by viewModel.isZappingScreenLoaded.collectAsState()
    var selectedAnime by remember { mutableIntStateOf(0) }

    LaunchedEffect (Unit) {
        if(!isLoaded || popular.isEmpty()) {
            viewModel.addPopularAnime(0)
            viewModel.setIsLoadedZappingScreen(flag = true)
        }
    }

    LaunchedEffect (pageAnime) {
        if(pageAnime > 1) {
            loading = true
            viewModel.addPopularAnime(pageAnime)
            loading = false
        }
    }

    val pagerState = rememberPagerState(pageCount = { popular.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedAnime = page
        }
    }

    val coroutineScope = rememberCoroutineScope()

    if(!isLoaded) {
        AnimePopularCardSkeleton()
    }else {
        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(512.dp)
                    .padding(8.dp)
            ) {
                AnimePopularCard(
                    anime = popular[page],
                    navController = navController,
                    viewModel = viewModel
                )

                if ((page == (popular.size - 1))) {
                    if (!nothingElse)
                        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                            AnimeLoaderButton(onClick = { pageAnime++ }, loading, false)
                        }
                    else Text(
                        text = "No other anime :(",
                        modifier = Modifier.padding(start = 16.dp),
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }

    }

    if(popular != null && popular.isNotEmpty()) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxHeight()
        ) {
            val screenHeight = maxHeight
            val flagHeight = screenHeight < 100.dp
            if(!flagHeight){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                }
            }
        }
    }

}