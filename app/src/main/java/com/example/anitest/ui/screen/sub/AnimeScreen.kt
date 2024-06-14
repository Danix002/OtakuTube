package com.example.anitest.ui.screen.sub

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.ui.componets.AnimeThumbnail
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackgroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.EpisodesDialog
import com.example.myapplication.MyViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimeScreen(viewModel: MyViewModel, navController: NavHostController, name: String, id: String, context: Context) {
    val animeInfoTrailer by viewModel.animeInfoTrailer.collectAsState()
    val animeInfo by viewModel.animeInfo.collectAsState()
    val episodes by viewModel.episodes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.forgetAnimeInfo()
        viewModel.forgetAnimeInfoTrailer()
        viewModel.setAnimeInfoTrailer(name)
        var episodeIds = viewModel.setAnimeInfo(id)
        println("########")
        println(episodeIds)
        if (episodeIds.isEmpty()) episodeIds = animeInfo?.let { listOf(it.name.split(" ", ignoreCase = false, ).joinToString("-")) }!!
        viewModel.setEpisodes(episodeIds)
    }

    Scaffold (
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel , navController)
        },
        topBar = {
            AppBar()
        }

    ) { contentPadding ->
        BackgroundImage(contentPadding, content = {
            Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                if(animeInfo != null) {
                    AnimeThumbnail(img = animeInfo!!.img_url, trailer = "eI2ijvh5hhE")
                } else {
                 // T O D O
                }
                EpisodesDialog(context, viewModel, episodes)
            }

        })
    }
}

