package com.example.anitest.ui.componets

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/*@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {
    var isFullScreen by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
        }
    }

    DisposableEffect(lifecycleOwner) {
        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(youtubeVideoId, 0f)
            }
        }

        val fullScreenListener = object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullScreen = true
            }

            override fun onExitFullscreen() {
                isFullScreen = false
            }
        }

        youTubePlayerView.initialize(listener)
        youTubePlayerView.addFullscreenListener(fullScreenListener)
        lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            factory = { youTubePlayerView }
        )
    }
}*/

@Composable
fun YouTubePlayer(
    youTubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {
    var isFullScreen by remember { mutableStateOf(false) }
    val fullscreenViewContainer by remember { mutableStateOf<YouTubePlayerView?>(null) }

    val context = LocalContext.current

    val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
        .controls(1)
        .fullscreen(1)
        .build()


    val listener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.loadVideo(youTubeVideoId, 0f)
        }
    }

    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    println("Ho preso l'evento di click!")
                    isFullScreen = true
                    visibility = View.GONE
                    fullscreenViewContainer?.visibility  = View.VISIBLE
                    fullscreenViewContainer?.addView(fullscreenView)
                    println(fullscreenView.isEnabled)
                }

                override fun onExitFullscreen() {
                    // Codice da eseguire quando esce dalla modalità fullscreen
                }
            })
        }
    }

    youTubePlayerView.initialize(listener, iFramePlayerOptions)

    lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            factory = { youTubePlayerView }
        )
    }

}

