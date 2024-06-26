package com.example.anitest.ui.componets

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.myapplication.MyViewModel

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer(viewModel: MyViewModel, index : Int, context: Context, onBack: () -> Unit) {
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val episodesLinks by viewModel.episodes.collectAsState()
    var urls by remember { mutableStateOf(emptyList<String?>()) }

    LaunchedEffect(episodesLinks) {
        episodesLinks?.let {
            urls = it.map { episode -> episode?.ep?.get(episode.ep.size-1)?.link ?: "" }
        }
    }

    DisposableEffect(key1 = urls) {
        exoPlayer.clearMediaItems()
        val mediaItems = urls.map { url ->
            MediaItem.fromUri(Uri.parse(url))
        }
        mediaItems.forEach { exoPlayer.addMediaItem(it) }
        exoPlayer.seekTo(index, 0)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.release()
        }
    }

    exoPlayer.addListener(object : Player.Listener {
        override fun onPositionDiscontinuity(reason: Int) {
            if (reason == Player.DISCONTINUITY_REASON_SEEK) {
                val currentIndex = exoPlayer.currentWindowIndex
                println("Sei all'episodio " + (currentIndex + 1))
                if (currentIndex == 0) {

                } else if (currentIndex == exoPlayer.mediaItemCount - 1) {

                } else{

                }
            }
        }
    })

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

    IconButton(
        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
        onClick = {
            onBack()
        }
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
    }
}


