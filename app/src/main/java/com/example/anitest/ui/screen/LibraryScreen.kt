package com.example.anitest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.anitest.room.AnimeEntity
import com.example.anitest.room.PlaylistEntity
import com.example.anitest.ui.components.AnimeCard
import com.example.anitest.ui.components.AnimeCardWithoutOnClick
import com.example.anitest.ui.components.AppBar
import com.example.anitest.ui.components.BackGroundImage
import com.example.anitest.ui.components.BottomNavigation
import com.example.anitest.ui.components.PlaylistCard
import com.example.anitest.ui.components.PlaylistCreationPopup
import com.example.myapplication.MyViewModel

@Composable
fun LibScreen(viewModel: MyViewModel, navController: NavHostController) {
    val playlists by viewModel.allPlaylist.collectAsState(initial = emptyList())
    val recentlyAnime by viewModel.allAnime.collectAsState(initial = emptyList())
    var openCreationPopup by remember { mutableStateOf(false) }
    val connection by viewModel.connection.collectAsState()
    Scaffold (
        containerColor = Color(102, 90, 110),
        bottomBar = {
            BottomNavigation(viewModel , navController)
        },
        topBar = {
            AppBar(viewModel, navController)
        }
    ) { contentPadding ->
        BackGroundImage(contentPadding, content = {
            Column (modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(4.dp)
            )
            {
                if (openCreationPopup) {
                    PlaylistCreationPopup(viewModel, onDismiss = { openCreationPopup = false })
                }
                Text(text = "Yours Lists", fontSize = 24.sp, color = Color.White)
                LazyRow (verticalAlignment = Alignment.CenterVertically){
                    itemsIndexed(playlists) {index: Int, item: PlaylistEntity ->
                        if (index == 0 && connection) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clickable {
                                        openCreationPopup = true
                                    }
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(241, 218, 255))
                                    .padding(horizontal = 32.dp, vertical = 64.dp)
                            ) {
                                IconButton(onClick = { openCreationPopup = true }) {
                                    Icon(imageVector = Icons.Filled.AddBox, contentDescription = "", modifier = Modifier.size(128.dp),  tint = Color(112, 82, 137))
                                }
                            }
                        }
                        PlaylistCard(playlist = item, viewModel, navController)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Recently viewed anime", fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(bottom = 12.dp))
                if(recentlyAnime.isNotEmpty()) {
                    if(connection){
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            itemsIndexed(recentlyAnime) { index: Int, item: AnimeEntity ->
                                AnimeCard(
                                    anime = Anime(item.name, item.img, item.anime_id, ""),
                                    navController = navController,
                                    viewModel = viewModel,
                                    fill = false
                                )
                            }
                        }
                    }else{
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            itemsIndexed(recentlyAnime) { index: Int, item: AnimeEntity ->
                                AnimeCardWithoutOnClick(
                                    anime = Anime(item.name, item.img, item.anime_id, "")
                                )
                            }
                        }
                    }
                }else{
                    Text(text = "You haven't viewed any anime recently :(",
                        fontSize = 16.sp, color = Color.White,
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        })
    }
}