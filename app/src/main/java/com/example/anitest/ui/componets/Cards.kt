package com.example.anitest.ui.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.model.Anime
import com.example.myapplication.MyViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCard(anime: Anime, navController: NavHostController, viewModel: MyViewModel) {
    var openDialog = remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    Column (
        modifier = Modifier
            .width(128.dp)
            .combinedClickable(
                onClick = {
                    viewModel.setIsLoadedAnimeScreen(false)
                    navController.navigate("anime/${anime.name}_${anime.anime_id}")
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    openDialog.value = true
                }
            )
    ) {
        if (openDialog.value) {
            DialogWithImage(
                onDismissRequest = { openDialog.value = false },
                anime = anime
            )
        }
        Image(
            painter = rememberAsyncImagePainter(anime.img_url),
            contentDescription = "anime image",
            modifier = Modifier
                .height(180.dp)
                .width(128.dp)
                .clip(RoundedCornerShape(8.dp))

        )
        Text(
            text = anime.name,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun AnimePopularCard(anime: Anime, navController: NavHostController, viewModel: MyViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    viewModel.setIsLoadedAnimeScreen(false)
                    navController.navigate("anime/${anime.name}_${anime.anime_id}")
                }
                .align(Alignment.Center)
        ) {
            Image(
                painter = rememberAsyncImagePainter(anime.img_url),
                contentDescription = "anime image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = anime.name,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun AnimeBigCard(anime: Anime) {
    Column (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(112, 82, 137).copy(alpha = 1f))
            .width(256.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(anime.img_url),
            contentDescription = "anime image",
            modifier = Modifier
                .height(360.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .shadow(16.dp)
        )
        Text(
            text = anime.name,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        )
    }
}

@Composable
fun AnimePopularCardSkeleton() {
    Box(
        modifier = Modifier.fillMaxWidth().height(470.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(350.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
        }
    }
}
@Composable
fun AnimeCardSkeleton() {
    Column(
        modifier = Modifier
            .height(200.dp)
            .width(128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .width(128.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.5f))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.5f))
        )
    }
}

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    anime: Anime
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        AnimeBigCard(anime)
    }
}