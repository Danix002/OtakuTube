package com.example.anitest.ui.screen.sub

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.room.AnimeEntity
import com.example.anitest.ui.componets.AnimeThumbnail
import com.example.anitest.ui.componets.AnimeTitles
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackGroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.BoxAnimeInformations
import com.example.anitest.ui.componets.EpisodesDialog
import com.example.anitest.ui.componets.EpisodesLoader
import com.example.anitest.ui.componets.Sagas
import com.example.myapplication.MyViewModel
import java.util.regex.Pattern

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimeScreen(viewModel: MyViewModel, navController: NavHostController, name: String, id: String, context: Context) {
    val animeInfoTrailer by viewModel.animeInfoTrailer.collectAsState()
    val animeInfo by viewModel.animeInfo.collectAsState()
    val isLoaded by viewModel.isAnimeScreenLoaded.collectAsState()

    LaunchedEffect(Unit) {
        if (!isLoaded) {
            viewModel.setIsLoadedAnimeScreen(flag = true)

            viewModel.forgetAnimeInfo()
            viewModel.forgetAnimeInfoTrailer()

            viewModel.setAnimeInfoTrailer(name)

            viewModel.setAnimeInfo(id)

            var indexDB = viewModel.getMaxInsertOrderAnime()
            if(indexDB == null){
                indexDB = 1
            }else{
                if(indexDB == 10){
                    viewModel.deleteAll()
                    indexDB = 0
                }
                indexDB += 1
            }
            viewModel.insertAnime(AnimeEntity(id, name, animeInfo!!.img_url, indexDB))
        }
    }

    Scaffold(
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel, navController)
        },
        topBar = {
            AppBar(viewModel, navController)
        }
    ) { contentPadding ->
        BackGroundImage(contentPadding, content = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (animeInfo != null && animeInfoTrailer != null) {
                    AnimeThumbnail(
                        img = if (animeInfoTrailer!!.isNotEmpty()) {
                            animeInfoTrailer!![0].image
                        } else {
                            animeInfo!!.img_url
                        },
                        trailer = if (animeInfoTrailer!!.isNotEmpty() && animeInfoTrailer!![0].trailer != null) {
                            extractId(animeInfoTrailer!![0].trailer)
                        } else {
                            ""
                        },
                        viewModel = viewModel,
                    )
                    if (animeInfo!!.status != "Upcoming") {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(112, 82, 137),
                                contentColor = Color.White
                            ),
                            onClick = { viewModel.openEpisodes() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(16.dp)
                        ) {
                            Text(text = "Episodes")
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Watch episodes"
                            )
                        }
                        EpisodesLoader(
                            context,
                            viewModel,
                            id.contains("dub")
                        )
                    }
                    val delimiters = "[,;:.-]+|\\s{3,}".toRegex()
                    var titles: List<String> = listOf("")
                    if(animeInfo!!.othername != null && animeInfo!!.othername.isNotEmpty()) {
                        titles = animeInfo!!.othername[0]
                            .trim('[', ']')
                            .split(delimiters)
                            .map { it.trim() }
                            .filter { it.isNotBlank() }
                    }
                    AnimeTitles(animeInfo!!.name, titles)
                    BoxAnimeInformations(animeInfo!!.about, animeInfo!!.type, animeInfo!!.release, animeInfo!!.genres, animeInfo!!.status)
                    Text(
                        text = "Recommended",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                    )
                    Sagas(viewModel, navController, id)
                } else {
                    AnimeScreenSkeleton()
                }
            }
        })
    }
}

fun extractId(url: String) : String{
    val pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?feature=player_embedded&v=|embed\\%2F|v=|%2Fvideos%2F|%2Fv%2F)[^#\\&\\?]*"

    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(url)

    return if (matcher.find()) {
        matcher.group()
    } else {
        ""
    }
}
