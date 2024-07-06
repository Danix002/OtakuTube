package com.example.anitest.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.MyViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun YouTubePlayer(
    youTubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
    viewModel: MyViewModel
) {
    val isEpisodesButtonOpen by viewModel.isEpisodesButtonOpen.observeAsState()
    val isSearchScreenOpen by viewModel.isSearchScreenOpen.observeAsState()

    var youTubePlayerInstance by remember { mutableStateOf<YouTubePlayer?>(null) }

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            YouTubePlayerView(context = it).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayerInstance = youTubePlayer
                        youTubePlayer.loadVideo(youTubeVideoId, 0f)
                    }
                })
            }
        }
    )

    LaunchedEffect(isEpisodesButtonOpen) {
        if (isEpisodesButtonOpen == true) {
            youTubePlayerInstance?.pause()
        }
    }

    LaunchedEffect(isSearchScreenOpen) {
        if (isSearchScreenOpen == true) {
            youTubePlayerInstance?.pause()
        }
    }
}

