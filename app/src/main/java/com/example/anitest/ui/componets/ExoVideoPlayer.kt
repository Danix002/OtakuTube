package com.example.anitest.ui.componets

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.myapplication.MyViewModel

@Composable
fun VideoPlayer(urls: List<String?>, index : Int, context: Context, onBack: () -> Unit) {
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(key1 = urls) {
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


