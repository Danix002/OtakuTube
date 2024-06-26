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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer(viewModel: MyViewModel, index : Int, context: Context, onBack: () -> Unit) {
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val episodesLinks by viewModel.episodes.collectAsState()
    val ids by viewModel.episodesIds.collectAsState()
    var urls by remember { mutableStateOf(
        episodesLinks!!.let {
            it.map { episode -> episode?.ep?.get(episode.ep.size-1)?.link ?: "" }
        }
    ) }
    val coroutineScope = rememberCoroutineScope()

    /**
    LaunchedEffect(episodesLinks) {
        episodesLinks?.let {
            urls = it.map { episode -> episode?.ep?.get(episode.ep.size-1)?.link ?: "" }
        }
    }
     **/

    DisposableEffect(Unit) {
        exoPlayer.clearMediaItems()
        val mediaItems = urls.map { url ->
            MediaItem.Builder().setMediaId(url).setUri(Uri.parse(url)).build()
        }



        mediaItems.forEach {
            exoPlayer.addMediaItem(it) }
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
                val currentIndex = exoPlayer.currentMediaItemIndex
                println("Sei all'episodio " + (currentIndex + 1))


                if ((ids?.size ?: 1) == 1) {
                    return
                }

                if (currentIndex == 0) {
                    if (exoPlayer.getMediaItemAt( 1).mediaId == "") {
                        coroutineScope.launch {
                            println(ids)
                            var nextEpisode = viewModel.getPublicEpisode(ids?.get(1) ?: "" )
                            var mediaNext =
                                nextEpisode?.ep?.get(nextEpisode.ep.size-1)?.let {
                                    MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                        nextEpisode.ep[nextEpisode.ep.size-1].link)).build()
                                }
                            if (mediaNext != null) {
                                exoPlayer.replaceMediaItem( 1, mediaNext )
                                println("NEXTEP::Caricato")
                            }
                        }

                    }
                } else if (currentIndex == exoPlayer.mediaItemCount - 1) {
                    if (exoPlayer.getMediaItemAt( currentIndex -1).mediaId == "") {
                        coroutineScope.launch {
                            println(ids)
                            var previousEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex -1) ?: "" )
                            var mediaNext =
                                previousEpisode?.ep?.get(previousEpisode.ep.size-1)?.let {
                                    MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                        previousEpisode.ep[previousEpisode.ep.size-1].link)).build()
                                }
                            if (mediaNext != null) {
                                exoPlayer.replaceMediaItem( currentIndex - 1, mediaNext )
                                println("PREVEP::Caricato")
                            }
                        }

                    }
                } else{
                    if (exoPlayer.getMediaItemAt( currentIndex -1).mediaId == "") {
                        coroutineScope.launch {

                            var previousEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex -1) ?: "" )
                            var mediaNext =
                                previousEpisode?.ep?.get(previousEpisode.ep.size-1)?.let {
                                    MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                        previousEpisode.ep[previousEpisode.ep.size-1].link)).build()
                                }
                            if (mediaNext != null) {
                                exoPlayer.replaceMediaItem( currentIndex - 1, mediaNext )
                                println("PREVEP::Caricato")
                            }
                        }

                    }
                    if (exoPlayer.getMediaItemAt( currentIndex +1).mediaId == "") {
                        coroutineScope.launch {

                            var nextEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex + 1) ?: "" )
                            var mediaNext =
                                nextEpisode?.ep?.get(nextEpisode.ep.size-1)?.let {
                                    MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                        nextEpisode.ep[nextEpisode.ep.size-1].link)).build()
                                }
                            if (mediaNext != null) {
                                exoPlayer.replaceMediaItem( currentIndex + 1, mediaNext )
                                println("NEXTEP::Caricato")
                            }

                        }

                    }
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


