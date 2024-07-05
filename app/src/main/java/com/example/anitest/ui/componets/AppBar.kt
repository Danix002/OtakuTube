package com.example.anitest.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(viewModel: MyViewModel, navController: NavHostController) {
    var searchString : String by remember { mutableStateOf("") }
    var searchFlag by remember { mutableStateOf(false) }
    var backNavFlag by remember { mutableStateOf(false) }
    var searchLoaded by remember { mutableStateOf(false) }
    val animeSearch by viewModel.animeSearch.collectAsState()

    val focusManager = LocalFocusManager.current

    var searchWidth = Modifier.fillMaxWidth()

    if(navController.currentDestination?.route == "home_screen") {
        searchWidth = Modifier.width(234.dp)
    }

    LaunchedEffect(searchFlag) {
        if(searchFlag) {
            viewModel.forgetAnimeSearch()
            viewModel.setAnimeSearch(searchString)
            searchFlag = false
            searchLoaded = true
        }
    }

    Column {
        TopAppBar(
            modifier = Modifier
                .height(65.dp),
            navigationIcon = {
                Box {
                    IconButton(
                        onClick = {
                            if(!viewModel.getFlagSearch()){
                                viewModel.setIsLoadedAnimeScreen(flag = false)
                                navController.navigateUp()
                            }else{
                                viewModel.closeSearch()
                            }
                            viewModel.forgetAnimeSearch()
                            backNavFlag = true },
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back button",
                            tint = Color.White
                        )
                    }
                }
            },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(modifier = searchWidth.padding(8.dp).clip(RoundedCornerShape(30.dp))) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                focusManager.clearFocus()
                                searchFlag = true
                                backNavFlag = false
                                viewModel.setIsLoadedAnimeScreen(flag = false)
                            }),
                            singleLine = true,
                            leadingIcon = {
                                IconButton(onClick = { searchFlag = true; backNavFlag = false; viewModel.setIsLoadedAnimeScreen(flag = false) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }
                            },
                            value = searchString,
                            onValueChange = { value: String -> searchString = value },
                            placeholder = {
                                Text(
                                    text = "Search anime...",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            },
                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(100, 70, 120).copy(alpha = 0.8f),
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    if(navController.currentDestination?.route == "home_screen"){
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = CenterEnd){
                            Filter(viewModel)
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent.copy(alpha = 0f)
            )
        )

        if(searchFlag){
            AnimeSearchLoaderSkeleton()
        }

        if (searchLoaded && !backNavFlag) {
            viewModel.openSearch()
            if (animeSearch.size > 0) {
                AnimeSearchLoader(
                    animeSearch = animeSearch,
                    viewModel = viewModel,
                    navController = navController
                )
            }else{
                Box(
                    contentAlignment = TopCenter,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    if(searchString.trim().length < 2){
                        Text(text = "You need to be more specific!",
                            textAlign = TextAlign.Center,
                            color = Color.White)
                    }else{
                        if(searchLoaded) {
                            Text(
                                text = "No anime found :(",
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        } else {
            viewModel.closeSearch()
        }
    }
}



