package com.example.anitest.ui.components

import androidx.compose.foundation.background
import com.example.anitest.ui.theme.LightOtakuColorScheme
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
import com.example.myapplication.MyViewModel

@Composable
fun AnimeTitles(name: String, titles: List<String>) {
    var expandedTitles by remember {
        mutableStateOf(false)
    }
    
    Spacer(modifier = Modifier.height(2.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(LightOtakuColorScheme.primary.copy(alpha = 0.5f))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                color = LightOtakuColorScheme.onPrimary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier
            .width(25.dp)
            .padding(start = 12.dp, end = 12.dp)
            .background(LightOtakuColorScheme.primary)
            .height(20.dp)
        )
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = titles[titles.size - 1],
                color = LightOtakuColorScheme.onPrimary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(
            Modifier.weight(0.1f)
        )
        Box (modifier = Modifier.width(30.dp), contentAlignment = Alignment.CenterEnd){
            IconButton( onClick = { expandedTitles = expandedTitles.not() }) {
                Icon(tint = LightOtakuColorScheme.onPrimary, imageVector = Icons.Filled.MoreVert, contentDescription = "")
            }
            DropdownMenu(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightOtakuColorScheme.tertiaryContainer)
                    .padding(horizontal = 16.dp),
                expanded = expandedTitles,
                onDismissRequest = { expandedTitles = false }) {
                titles.forEach {
                    if(it != "") {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp, bottom = 2.dp), text = it, color = LightOtakuColorScheme.onTertiaryContainer
                        )
                    }
                }
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp) , text = name, color = LightOtakuColorScheme.onTertiaryContainer
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
        .background(LightOtakuColorScheme.primaryContainer)
    ) {
        Column (modifier = Modifier.padding(4.dp)) {
            Text(
                text = buildAnnotatedString {
                    append("Type: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(type)
                },
                color = LightOtakuColorScheme.onPrimaryContainer,
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
                color = LightOtakuColorScheme.onPrimaryContainer,
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
                    color = LightOtakuColorScheme.onPrimaryContainer,
                    fontSize = 14.sp
                )
                genres.forEach {
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(LightOtakuColorScheme.tertiary),
                    ) {
                        Text(
                            text = "$it ",
                            color = LightOtakuColorScheme.onTertiary,
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
                color = LightOtakuColorScheme.onPrimaryContainer,
                fontSize = 14.sp
            )
            Spacer(
                Modifier.height(5.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Plot: ")
                    addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 5)
                    append(about)
                },
                color = LightOtakuColorScheme.onPrimaryContainer,
                fontSize = 14.sp,
                maxLines = if (expandedInfo) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp), contentAlignment = Alignment.Center) {
                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { expandedInfo = expandedInfo.not() }) {
                    Icon(
                        tint = LightOtakuColorScheme.onPrimaryContainer,
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
                    AnimeCard(anime, navController, viewModel, false)
            }
        }
    }
}