package com.example.anitest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.AnimeDetail
import com.example.anitest.navigation.Screen
import com.example.anitest.room.PlaylistEntity
import com.example.anitest.room.PlaylistWithList
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackGroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.CategoryRow
import com.example.anitest.ui.componets.CategoryRowSkeleton
import com.example.anitest.ui.componets.PlaylistCard
import com.example.anitest.ui.componets.PlaylistCreationPopup
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LibScreen(viewModel: MyViewModel, navController: NavHostController) {
    val playlists by viewModel.allPlaylist.collectAsState(initial = emptyList())
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
                .padding(4.dp))
            {
                if (openCreationPopup) {
                    PlaylistCreationPopup(viewModel, onDismiss = { openCreationPopup = false })
                }
                Text(text = "Yours Lists", fontSize = 24.sp, color = Color.White)
                LazyRow ( verticalAlignment = Alignment.CenterVertically ){
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
                                IconButton(onClick = { }) {
                                    Icon(imageVector = Icons.Filled.AddBox, contentDescription = "", modifier = Modifier.size(128.dp),  tint = Color(112, 82, 137))
                                }
                            }
                        }
                        PlaylistCard(playlist = item, viewModel, navController)
                    }
                }
            }
        })
    }
}