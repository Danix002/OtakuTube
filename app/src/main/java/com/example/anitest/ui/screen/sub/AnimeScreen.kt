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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
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
        viewModel.forgetEpisodes()
        viewModel.forgetAnimeInfoTrailer()

        viewModel.setAnimeInfoTrailer(name)
        var episodesId = viewModel.setAnimeInfo(id)
        if (episodesId.isEmpty()) episodesId = animeInfo?.let {
            listOf(it.name.toLowerCase().replace(Regex("[^a-z0-9]+"), "-").replace(Regex("-+"), "-").trim('-')) }!!
            viewModel.setEpisodes(episodesId)
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
                    episodes?.let { EpisodesDialog(context, viewModel, it, id.contains("dub")) }
                } else {
                 // T O D O
                }

            }

        })
    }
}

