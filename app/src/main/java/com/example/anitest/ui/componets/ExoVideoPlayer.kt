package com.example.anitest.ui.componets

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer(viewModel: MyViewModel, context: Context, onBack: () -> Unit) {
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val ids by viewModel.episodesIds.collectAsState()
    val startEpisode by viewModel.currentEpisode.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var loadingNext by remember {
        mutableStateOf(false)
    }
    var loadingPrev by remember {
        mutableStateOf(false)
    }

    var currentEpisodeIndex by remember {
        mutableStateOf(-1)
    }

    DisposableEffect(Unit) {
        exoPlayer.clearMediaItems()
        val url = startEpisode!!.ep[startEpisode!!.ep.size - 1].link
        val id = startEpisode!!.index
        exoPlayer.addMediaItem(
            MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
        )
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.release()
        }
    }

    fun loadEpisodes() {
        if ((ids?.size ?: 1) == 1) {
            return
        }

        val indexOfCurrentEpisode = exoPlayer.currentMediaItem!!.mediaId.toInt() - 1
        currentEpisodeIndex = indexOfCurrentEpisode + 1

        if (!loadingPrev && !exoPlayer.hasPreviousMediaItem() && indexOfCurrentEpisode != 0 && !loadingNext && !exoPlayer.hasNextMediaItem() && indexOfCurrentEpisode != ids!!.size - 1) {
            loadingNext = true
            loadingPrev = true
            coroutineScope.launch {
                val nextEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode + 1])
                val url = nextEpisode!!.ep[nextEpisode.ep.size - 1].link
                val id = nextEpisode.index
                exoPlayer.addMediaItem(
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                loadingNext = false
                val prevEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode - 1])
                val urlPrev = prevEpisode!!.ep[prevEpisode.ep.size - 1].link
                val idPrev = prevEpisode.index
                exoPlayer.addMediaItem(
                    0,
                    MediaItem.Builder().setMediaId(idPrev.toString()).setUri(Uri.parse(urlPrev))
                        .build()
                )
                loadingPrev = false
            }

        } else if (!loadingNext && !exoPlayer.hasNextMediaItem() && indexOfCurrentEpisode != ids!!.size - 1) {
            loadingNext = true
            coroutineScope.launch {
                val nextEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode + 1])
                val url = nextEpisode!!.ep[nextEpisode.ep.size - 1].link
                val id = nextEpisode.index
                exoPlayer.addMediaItem(
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                loadingNext = false
            }

        } else if (!loadingPrev && !exoPlayer.hasPreviousMediaItem() && indexOfCurrentEpisode != 0) {
            loadingPrev = true
            coroutineScope.launch {
                val prevEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode - 1])
                val url = prevEpisode!!.ep[prevEpisode.ep.size - 1].link
                val id = prevEpisode.index
                exoPlayer.addMediaItem(
                    0,
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                loadingPrev = false
            }
        }

    }

    LaunchedEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY && exoPlayer.playWhenReady) {
                    loadEpisodes()
                }
            }
        }
        exoPlayer.addListener(listener)
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )

    Row (verticalAlignment = Alignment.CenterVertically){
        IconButton(
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
            onClick = {
                onBack()
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
        }
        if(currentEpisodeIndex > 0){
            Text(text = "Ep.${currentEpisodeIndex}", color = Color.White, fontSize = 16.sp,  style = MaterialTheme.typography.titleMedium)
        }
    }
}


