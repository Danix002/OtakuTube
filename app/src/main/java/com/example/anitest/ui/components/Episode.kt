package com.example.anitest.ui.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.launch

@Composable
fun EpisodeButton(index: Number, isDubbed: Boolean, onWatch: ()-> Unit, onDownload: ()-> Unit, isLoading: Boolean) {

    val principalColor = Color(112, 82, 137)

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
            .clickable { onWatch() }
    ) {
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch episode", tint = Color.White)
        Text(
            text = "Ep. $index",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
        Spacer(
            Modifier
                .weight(1f)
                .background(Color.Green)
        )
        if(isDubbed){
            Text(
                text = "Dub",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(129, 81, 86))
                    .padding(vertical = 3.dp, horizontal = 12.dp)
            )
        }
        IconButton(
            onClick = { onDownload() },
            modifier = Modifier
                .height(22.dp)
            ){
            Icon(imageVector = if (isLoading) Icons.Filled.Restore else Icons.Filled.Download,
                contentDescription = "Watch episode",
                tint = principalColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .padding(3.dp)
            )
        }
    }
}

@SuppressLint("SourceLockedOrientationActivity", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodesDialog(context: Context, viewModel: MyViewModel, isDubbed: Boolean){
    val isEpisodesButtonOpen by viewModel.isEpisodesButtonOpen.observeAsState()
    val activity = context as Activity
    val episodes by viewModel.episodesIds.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var loading by remember {
        mutableIntStateOf(-1)
    }

    var loadingWatch by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current

    if(isEpisodesButtonOpen == true) {
        Dialog(
            properties = DialogProperties(
            usePlatformDefaultWidth = false
            ),
            onDismissRequest = { viewModel.closeEpisodes() }
        ){
            Box(modifier = Modifier
                .background(Color(242, 218, 255))
                .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(episodes ?: emptyList()) { index, ep ->
                        if(index == 0){
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Row {
                                    IconButton(
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        onClick = { viewModel.closeEpisodes() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "",
                                            tint = Color(112, 82, 137)
                                        )
                                    }
                                    Text(text = "Episodes",
                                        color = Color(112, 82, 137),
                                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                        EpisodeButton(
                            index = index + 1,
                            isDubbed = isDubbed,
                            isLoading = loading == index,
                            onWatch = {
                                coroutineScope.launch {
                                    loadingWatch = true
                                    viewModel.setEpisode(ep)
                                    loadingWatch = false
                                    activity.requestedOrientation =
                                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                }
                            },
                            onDownload = {
                                coroutineScope.launch {
                                    if (loading >= 0) return@launch
                                    loading = index
                                    val epLink = viewModel.getPublicEpisode(ep)
                                    if (epLink != null) uriHandler.openUri(epLink.ep.last().link)
                                    loading = -1
                                }
                            }
                        )
                    }
                }
                if (loadingWatch) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.9f))
                            .fillMaxSize()
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Filled.HourglassBottom, contentDescription = "", tint = Color.White)
                            Text(text = "Loading...", color = Color.White)
                        }

                    }
                }
            }
        }
    }
    OpenVideoPlayer(context = context, viewModel = viewModel)
}

@Composable
fun EpisodesLoader(context: Context, viewModel: MyViewModel, isDubbed: Boolean){
    val isLoaded by viewModel.isEpisodeLoaded.collectAsState()
    val episodes by viewModel.episodesIds.collectAsState()
    LaunchedEffect(Unit) {
        if(!isLoaded){
            viewModel.setIsLoadedEpisode(flag = true)
            viewModel.forgetEpisodes()
            episodes?.let { viewModel.initEpisodes(it.size) }
        }
    }

    EpisodesDialog(context = context , viewModel = viewModel, isDubbed = isDubbed)
}

@Composable
fun OpenVideoPlayer(context: Context, viewModel: MyViewModel){
    var showPlayer by remember { mutableStateOf(false) }
    val activity = context as Activity
    val currentEpisode by viewModel.currentEpisode.collectAsState()

    if(activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ){
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                showPlayer = false
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }){
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                currentEpisode?.let {
                    VideoPlayer(
                        onBack = {
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            viewModel.openEpisodes()
                        },
                        context = context,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}