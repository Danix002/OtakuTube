package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.launch

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
                    AnimeCard(anime, navController, viewModel, false)
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

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularAnimeRow(viewModel: MyViewModel, navController: NavHostController) {
    val popular by viewModel.popularAnime.collectAsState()
    var pageAnime by remember { mutableIntStateOf(1) }
    val nothingElse by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }
    var selectedAnime by remember { mutableIntStateOf(0) }
    var expandedInfo by remember { mutableStateOf(false) }

    LaunchedEffect (Unit) {
        if(!isLoaded) {
            viewModel.addPopularAnime(0)
            isLoaded = true
        }
    }

    LaunchedEffect (pageAnime) {
        if(pageAnime > 0) {
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
                    .height(470.dp)
                    .padding(8.dp)
            ) {
                AnimePopularCard(
                    anime = popular[page],
                    navController = navController,
                    viewModel = viewModel
                )
                if ((page == (popular.size - 1))) {
                    if (!nothingElse)
                        Box(modifier = Modifier.align(Alignment.TopEnd)) {
                            AnimeLoaderButton(onClick = { pageAnime++ }, loading)
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous")
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color(242, 218, 255))
    ) {
        if(isLoaded) {
            Column(modifier = Modifier.padding(8.dp)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp), contentAlignment = Alignment.Center) {
                    IconButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { expandedInfo = expandedInfo.not() }
                    ) {
                        Icon(
                            tint = Color.Black,
                            imageVector = if (expandedInfo) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                            contentDescription = ""
                        )
                    }
                }
                Text(
                    text = "Description",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.height(32.dp),
                    text = popular[selectedAnime].description,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                /*AnimatedVisibility(
                    visible = expandedInfo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                    ) {

                }*/
            }
        }
    }
}