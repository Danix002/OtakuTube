package com.example.anitest.ui.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.R
import com.example.anitest.model.Anime
import com.example.anitest.room.PlaylistEntity

@Composable
fun PlaylistCard(playlist: PlaylistEntity): Unit {
    val playlistFav : PlaylistEntity = PlaylistEntity("Favorite", R.drawable.standard_library.toString());
    Box (modifier = Modifier
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
        Image(painter = if(playlist.name == "Favorite") painterResource(Integer.parseInt(playlistFav.img)) else rememberAsyncImagePainter(playlist.img),
            contentDescription = "anime image",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun PlaylistCreationPopup() {
    var nameValue by remember { mutableStateOf("") }
    var searchValue by remember { mutableStateOf("") }
    var animeSearched by remember {
        mutableStateOf(emptyList<Anime>())
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {
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
                    }),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = {
                            // richiesta al server
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
                    itemsIndexed(animeSearched) { index, item ->
                        AnimeCardSkeleton()
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row ( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(102, 90, 110)
                    ),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(100, 70, 120).copy(alpha = 0.9f)
                    ),
                    onClick = { /*TODO*/ }) {
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