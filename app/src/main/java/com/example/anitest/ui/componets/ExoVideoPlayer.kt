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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.UnrecognizedInputFormatException
import androidx.media3.ui.PlayerView
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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
    val startEpisode by viewModel.currentEpisode.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var playerError by remember { mutableStateOf(false) }
    var loadingNext by remember {
        mutableStateOf(false)
    }
    var loadingPrev by remember {
        mutableStateOf(false)
    }
    DisposableEffect(Unit) {
        exoPlayer.clearMediaItems()
        /**
        val mediaItems = urls.map { url ->
            MediaItem.Builder().setMediaId(url).setUri(Uri.parse(url)).build()
        }
        mediaItems.forEach {
            exoPlayer.addMediaItem(it) }
        **/
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

    fun loadEpisodes(currentIndex: Int){
        if ((ids?.size ?: 1) == 1) {
            return
        }

        val indexOfCurrentEpisode = exoPlayer.currentMediaItem!!.mediaId.toInt() - 1

        if ( !loadingPrev && !exoPlayer.hasPreviousMediaItem() && indexOfCurrentEpisode != 0  && !loadingNext && !exoPlayer.hasNextMediaItem() && indexOfCurrentEpisode != ids!!.size - 1) {
            loadingNext = true
            loadingPrev = true
            coroutineScope.launch {
                println("Caricando next")
                val nextEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode+1])
                println("next episode: " + nextEpisode)
                val url = nextEpisode!!.ep[nextEpisode.ep.size - 1].link
                val id = nextEpisode.index
                exoPlayer.addMediaItem(
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                println("Next caricato:" + nextEpisode.index)
                loadingNext = false
                val prevEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode-1])
                val urlPrev = prevEpisode!!.ep[prevEpisode.ep.size - 1].link
                val idPrev = prevEpisode.index
                exoPlayer.addMediaItem(0,
                    MediaItem.Builder().setMediaId(idPrev.toString()).setUri(Uri.parse(urlPrev)).build()
                )
                println("Prev caricato:" + prevEpisode.index)
                loadingPrev = false
            }

        } else if (!loadingNext && !exoPlayer.hasNextMediaItem() && indexOfCurrentEpisode != ids!!.size - 1)
        {
            // load next
            loadingNext = true
            coroutineScope.launch {
                println("Caricando next")
                val nextEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode+1])
                println("next episode: " + nextEpisode)
                val url = nextEpisode!!.ep[nextEpisode.ep.size - 1].link
                val id = nextEpisode.index
                exoPlayer.addMediaItem(
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                println("Next caricato:" + nextEpisode.index)
                loadingNext = false
            }

        } else if (!loadingPrev && !exoPlayer.hasPreviousMediaItem() && indexOfCurrentEpisode != 0 ) {
            // load previous
            loadingPrev = true
            println("Caricando prev")
            coroutineScope.launch {
                val prevEpisode = viewModel.getPublicEpisode(ids!![indexOfCurrentEpisode-1])
                val url = prevEpisode!!.ep[prevEpisode.ep.size - 1].link
                val id = prevEpisode.index
                exoPlayer.addMediaItem(0,
                    MediaItem.Builder().setMediaId(id.toString()).setUri(Uri.parse(url)).build()
                )
                println("Prev caricato:" + prevEpisode.index)
                loadingPrev = false
            }
        }


        /**loading = true
        if (currentIndex == 0) {

            if (exoPlayer.getMediaItemAt( 1).mediaId == "") {
                coroutineScope.launch {
                    var nextEpisode = viewModel.getPublicEpisode(ids?.get(1) ?: "")
                    var mediaNext =
                        nextEpisode?.ep?.get(nextEpisode.ep.size-1)?.let {
                            MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                nextEpisode.ep[nextEpisode.ep.size-1].link)).build()
                        }
                    if (mediaNext != null) {
                        exoPlayer.replaceMediaItem(1, mediaNext)
                    }
                }
            }
        } else if (currentIndex == exoPlayer.mediaItemCount - 1) {
            if (exoPlayer.getMediaItemAt( currentIndex -1).mediaId == "") {
                coroutineScope.launch {
                    var previousEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex -1) ?: "")
                    var mediaNext =
                        previousEpisode?.ep?.get(previousEpisode.ep.size-1)?.let {
                            MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                previousEpisode.ep[previousEpisode.ep.size-1].link)).build()
                        }
                    if (mediaNext != null) {
                        exoPlayer.replaceMediaItem(currentIndex - 1, mediaNext)
                    }
                }

            }
        } else{
            if (exoPlayer.getMediaItemAt(currentIndex - 1).mediaId == "") {
                coroutineScope.launch {
                    var previousEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex -1) ?: "")
                    var mediaNext =
                        previousEpisode?.ep?.get(previousEpisode.ep.size-1)?.let {
                            MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                previousEpisode.ep[previousEpisode.ep.size-1].link)).build()
                        }
                    if (mediaNext != null) {

                        exoPlayer.replaceMediaItem(currentIndex - 1, mediaNext)
                    }
                }
            }
            if (exoPlayer.getMediaItemAt( currentIndex +1).mediaId == "") {
                coroutineScope.launch {
                    var nextEpisode = viewModel.getPublicEpisode(ids?.get(currentIndex + 1) ?: "")
                    var mediaNext =
                        nextEpisode?.ep?.get(nextEpisode.ep.size-1)?.let {
                            MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                nextEpisode.ep[nextEpisode.ep.size-1].link)).build()
                        }
                    if (mediaNext != null) {
                        exoPlayer.replaceMediaItem(currentIndex + 1, mediaNext)
                    }
                }
            }
        }
        loading = false**/
    }

    LaunchedEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY && exoPlayer.playWhenReady) {
                    val currentIndex = exoPlayer.currentMediaItemIndex
                    loadEpisodes(currentIndex)
                }
            }
        }
        exoPlayer.addListener(listener)
    }

    /**
    exoPlayer.addListener(object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            playerError = true
            val currentIndex = exoPlayer.currentMediaItemIndex
            val currentEpisode = exoPlayer.getMediaItemAt(currentIndex - 1).mediaId == ""
            if(currentEpisode && loading){
                /*coroutineScope.launch {
                    while(loading)
                        delay(20000)
                }*/

                println("######### SONO NEL IF ###################")
            }else if(currentEpisode && !loading){
                loading = true
                coroutineScope.launch {
                    var episode = viewModel.getPublicEpisode(ids?.get(currentIndex) ?: "")
                    var media =
                        episode?.ep?.get(episode.ep.size-1)?.let {
                            MediaItem.Builder().setMediaId(it.link).setUri(Uri.parse(
                                episode.ep[episode.ep.size-1].link)).build()
                        }
                    if (media != null) {
                        exoPlayer.replaceMediaItem(currentIndex, media)
                    }
                    exoPlayer.play()
                    loading = false
                }
            }
            playerError = false
        }
    })
    **/
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

    /**if (loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .align(Alignment.Center)
        )
    }*/

    IconButton(
        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
        onClick = {
            onBack()
        }
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
    }
}


