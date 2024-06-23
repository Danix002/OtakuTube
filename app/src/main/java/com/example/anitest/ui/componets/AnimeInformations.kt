package com.example.anitest.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Anime
import com.example.myapplication.MyViewModel

@Composable
fun AnimeTitles(name: String, titles: List<String>) {
    var expandedTitles by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(112, 82, 137).copy(alpha = 0.5f))

    ) {
        Box(modifier = Modifier.width(190.dp)) {
            Text(
                text = name,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier
            .width(25.dp)
            .padding(start = 12.dp, end = 12.dp)
            .background(Color.Red)
            .height(20.dp)
        )
        Box(modifier = Modifier.width(190.dp)) {
            Text(
                text = titles[titles.size - 1],
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(
            Modifier
                .weight(1f)
        )
        Box (modifier = Modifier.width(30.dp), contentAlignment = Alignment.CenterEnd){
            IconButton( onClick = { expandedTitles = expandedTitles.not() }) {
                Icon(tint = Color.White, imageVector = Icons.Filled.MoreVert, contentDescription = "")
            }
            DropdownMenu(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp),
                expanded = expandedTitles,
                onDismissRequest = { expandedTitles = false }) {
                titles.forEach {
                    if(it != "") {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp, bottom = 2.dp), text = it, color = Color.White
                        )
                    }
                }
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp) , text = name, color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxAnimeInformations(about: String, type: String, release: String, genres: List<String>, status: String){
    var expandedInfo by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clip(RoundedCornerShape(6.dp))
        .background(Color(242, 218, 255))
    ) {
        Column (modifier = Modifier.padding(4.dp)) {
            Text(
                text = buildAnnotatedString {
                    append("Plot: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(about)
                },
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = if (expandedInfo) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                Modifier.height(5.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Type: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(type)
                },
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(
                Modifier.height(5.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Release: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(release)
                },
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(
                Modifier.height(5.dp)
            )
            FlowRow{
                Text(
                    text = buildAnnotatedString {
                        append("Genres: ")
                        addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    },
                    color = Color.Black,
                    fontSize = 14.sp
                )
                genres.forEach {
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(129, 81, 86)),
                    ) {
                        Text(
                            text = "$it ",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(1.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(
                        Modifier.width(2.dp)
                    )
                }
            }
            Spacer(
                Modifier.height(5.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Status: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(status)
                },
                color = Color.Black,
                fontSize = 14.sp
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp), contentAlignment = Alignment.Center) {
                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { expandedInfo = expandedInfo.not() }) {
                    Icon(
                        tint = Color.Black,
                        imageVector = if (expandedInfo) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun Sagas(viewModel: MyViewModel, navController: NavHostController, id: String){
    val animeSearch by viewModel.animeSearch.collectAsState()
    var isLoaded by remember { mutableStateOf(false) }
    val flagSearch by viewModel.isSearchScreenOpen.observeAsState()

    LaunchedEffect(flagSearch) {
        isLoaded = false
        if(!viewModel.getFlagSearch()) {
            viewModel.forgetAnimeSearch()
            viewModel.setAnimeSearch(id.split("-")[0])
            isLoaded = true
        }
    }

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        if(!isLoaded) {
            items(3){
                AnimeCardSkeleton()
            }
        }else {
            itemsIndexed(animeSearch) { _, anime ->
                if(anime.anime_id != id)
                    AnimeCard(anime, navController, viewModel)
            }
        }
    }
}