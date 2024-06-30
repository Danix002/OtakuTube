package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.R
import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeDetail
import com.example.anitest.room.PlayListAnimeRelation
import com.example.anitest.room.PlaylistEntity
import com.example.anitest.room.PlaylistWithList
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlaylistCard(playlist: PlaylistEntity, viewModel: MyViewModel, navController: NavHostController) {
    val playlistFav : PlaylistEntity = PlaylistEntity("Favorite", R.drawable.standard_library.toString());

    var playlistWithList by remember {
        mutableStateOf(
            PlaylistWithList(playlist = PlaylistEntity("", ""), playlists = emptyList())
        )
    }
    var open by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    Box (modifier = Modifier
        .clickable {
            coroutineScope.launch {
                playlistWithList = viewModel.getPlaylist(playlist.name)
                open = true
            }

        }
        .width(164.dp)
        .padding(vertical = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(modifier = Modifier
            .rotate(-8f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .height(180.dp)
            .width(128.dp)
        )
        Box(modifier = Modifier
            .rotate(8f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .height(180.dp)
            .width(128.dp)
        )
        Image(painter = if(playlist.name == "Favourite") painterResource(Integer.parseInt(playlistFav.img)) else rememberAsyncImagePainter(playlist.img),
            contentDescription = "anime image",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(241, 218, 255))
                .height(180.dp)
                .width(128.dp)
        )
        Box (contentAlignment = Alignment.Center, modifier = Modifier
            .padding(bottom = 8.dp)
            .width(128.dp)
        ) {
            Box (modifier = Modifier
                .blur(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray.copy(alpha = 0.5f))
                .fillMaxWidth()
                .height(20.dp))
            Text(maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 12.dp), text = playlist.name, color = Color.White)
        }
    }
    if (open) {
        PlaylistDialog({ open = false },
            {
                coroutineScope.launch {
                    viewModel.delete(it)
                    playlistWithList = viewModel.getPlaylist(playlist.name)
                    open = false
                    open = true
                }
            },
            playlistWithList, viewModel, navController)
    }

}

@Composable
fun PlaylistDialog(onDismiss: () -> Unit, onRemoveAnime: (anime: PlayListAnimeRelation) -> Unit, playlistWithList: PlaylistWithList, viewModel: MyViewModel, navController: NavHostController ) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(241, 218, 255))
                .padding(12.dp)
        ) {
            Text(text = playlistWithList.playlist.name, fontWeight = FontWeight.Bold,fontSize = 20.sp, color = Color(112, 82, 137))
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(playlistWithList.playlists) { index, item ->
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable {
                            viewModel.setIsLoadedAnimeScreen(false)
                            navController.navigate("anime/${item.animeId}_${item.animeName}")
                        }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(item.animeImg),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                                .width(48.dp)
                                .height(64.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = item.animeId, modifier = Modifier.width(128.dp), color = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            modifier = Modifier.width(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(186, 26, 26)
                            ),
                            onClick = {
                                onRemoveAnime(item)
                            }) {
                            Text(text = "X", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(186, 26, 26)
                ),
                onClick = {
                    viewModel.delete(playlist = playlistWithList.playlist.name)
                    playlistWithList.playlists.forEach {
                        viewModel.delete(relation = it)
                    }
                    onDismiss()
                }) {
                Text(text = "Delete Playlist", color = Color.White)
            }
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistCreationPopup(viewModel: MyViewModel, onDismiss: () -> Unit) {
    var nameValue by remember { mutableStateOf("") }
    var searchValue by remember { mutableStateOf("") }
    val animeSearched by viewModel.animeSearch.collectAsState()
    var animeSelected by remember { mutableStateOf( mutableListOf<Anime>()) }
    var animeSelectedSize by remember { mutableStateOf( animeSelected.size.toString()) }

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchValue) {
        if (searchValue == "") {
            viewModel.forgetAnimeSearch()
            viewModel.setAnimeSearch("A")
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(241, 218, 255))
                .padding(12.dp)
        ) {
            Text(text = "Create your list", fontWeight = FontWeight.Bold,fontSize = 20.sp, color = Color(112, 82, 137))
            Spacer(modifier = Modifier.height(16.dp))
            Row (verticalAlignment = Alignment.CenterVertically ){
                Text(text = "Name:",fontSize = 16.sp, color = Color(112, 82, 137))
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {

                    }),
                    singleLine = true,
                    value = nameValue,
                    onValueChange = { value: String -> nameValue = value },
                    placeholder = {
                        Text(
                            text = "Playlist name...",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    },
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(100, 70, 120).copy(alpha = 0.8f),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .border(border = BorderStroke(1.dp, Color(100, 70, 120)))
                    .background(Color(238, 221, 246))
                    .padding(16.dp)
            ) {
                TextField(
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // richiesta al server
                        coroutineScope.launch {
                            focusManager.clearFocus()
                            viewModel.forgetAnimeSearch()
                            viewModel.setAnimeSearch(searchValue)
                        }
                    }),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = {
                            // richiesta al server
                            coroutineScope.launch {
                                viewModel.forgetAnimeSearch()
                                viewModel.setAnimeSearch(searchValue)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    },
                    value = searchValue,
                    onValueChange = { value: String -> searchValue = value },
                    placeholder = {
                        Text(
                            text = "Search anime...",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    },
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(102, 90, 110).copy(alpha = 0.8f),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (animeSearched.isEmpty()) {
                        items(5) {
                            AnimeCardSkeleton()
                        }
                    } else {
                        itemsIndexed(animeSearched) { index, item ->
                            AnimeCardPlaylist(
                                item,
                                {
                                    if (animeSelected.contains(item)) {
                                        animeSelected.remove(item)
                                    } else {
                                        animeSelected.add(item)
                                    }
                                    animeSelectedSize = animeSelected.size.toString()
                                },
                                list = animeSelected
                            )

                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Hai selezionato: $animeSelectedSize Anime")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row ( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(102, 90, 110)
                    ),
                    onClick = {
                        viewModel.forgetAnimeSearch()
                        onDismiss()
                    }) {
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    enabled = nameValue.isNotEmpty() && animeSelected.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(100, 70, 120).copy(alpha = 0.9f)
                    ),
                    onClick = {
                        viewModel.insert(
                            PlaylistEntity(name = nameValue, img = animeSelected.first().img_url)
                        )
                        animeSelected.forEach { item ->
                            viewModel.insert(playlist = nameValue, anime = AnimeDetail(item.name, item.img_url, item.anime_id))
                        }
                        onDismiss()
                    }) {
                    Text(
                        text = "Confirm",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}