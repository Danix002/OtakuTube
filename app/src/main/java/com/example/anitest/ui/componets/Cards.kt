package com.example.anitest.ui.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeDetail
import com.example.anitest.ui.theme.LightOtakuColorScheme
import com.example.myapplication.MyViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCard(anime: Anime, navController: NavHostController, viewModel: MyViewModel, fill: Boolean) {
    var openDialog = remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
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
                anime = anime,
                viewModel
            )
        }
        if (fill) {
            Image(
                painter = rememberAsyncImagePainter(anime.img_url),
                contentDescription = "anime image",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(anime.img_url),
                contentDescription = "anime image",
                modifier = Modifier
                    .height(180.dp)
                    .width(128.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Text(
            text = anime.name,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun AnimeCardWithoutOnClick(anime: Anime) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(128.dp)
    ) {
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
            textAlign = TextAlign.Center,
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
fun AnimeBigCard(anime: Anime, viewModel: MyViewModel) {
    var open by remember {
        mutableStateOf(false)
    }
    val allPlaylist by viewModel.allPlaylist.collectAsState(initial = emptyList())

    Column (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                LightOtakuColorScheme.primary
            )
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
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Text(
                text = anime.name,
                color = LightOtakuColorScheme.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier
                    .width(224.dp)
                    .wrapContentHeight()
                    .padding(8.dp)
            )
            IconButton(
                onClick = { open = true },
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
                modifier = Modifier.wrapContentWidth()
            ){
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "",  tint = LightOtakuColorScheme.onPrimary)
            }
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }, modifier = Modifier.width(256.dp).background(LightOtakuColorScheme.primaryContainer)) {
            for (playlist in allPlaylist) {
                DropdownMenuItem(
                    modifier = Modifier.background(LightOtakuColorScheme.primaryContainer),
                    text = {
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Add to " +playlist.name, color = LightOtakuColorScheme.onPrimaryContainer)
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "", tint = LightOtakuColorScheme.onPrimaryContainer )
                        }
                           },
                    onClick = {
                        viewModel.insertPlaylist(playlist = playlist.name, anime = AnimeDetail(name = anime.name, img_url = anime.img_url, anime_id = anime.anime_id))
                        open = false
                    }
                )
            }
        }

    }
}

@Composable
fun AnimePopularCardSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(512.dp),
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
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
        }
    }
}
@Composable
fun AnimeCardSkeleton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
    anime: Anime,
    viewModel: MyViewModel
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        AnimeBigCard(anime, viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCardPlaylist(anime: Anime,  onClick: () -> Unit, list:  List<Anime>) {

    var isSelected by remember {
        mutableStateOf(list.contains(anime))
    }

    Box (
        modifier = Modifier.combinedClickable(
            onClick = {
                onClick()
                isSelected = list.contains(anime)
            },
        )
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(128.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(anime.img_url),
                contentDescription = "anime image",
                modifier = Modifier
                    .height(180.dp)
                    .width(128.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(241, 218, 255))
            )
            Text(
                text = anime.name,
                color = LightOtakuColorScheme.onSurface,
                fontSize = 16.sp,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
        if (isSelected) {
            Box(modifier = Modifier
                .background(LightOtakuColorScheme.primary.copy(alpha = 0.5f))
                .height(200.dp)
                .width(128.dp)
                .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "", tint = LightOtakuColorScheme.primary, modifier = Modifier.fillMaxSize() )
            }
        }

    }

}