package com.example.anitest.ui.componets

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
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

    LaunchedEffect(searchFlag) {
        if(searchFlag) {
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
                        onClick = { navController.navigateUp(); backNavFlag = true },
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            },
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(30.dp))
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            focusManager.clearFocus()
                            searchFlag = true
                            backNavFlag = false
                        }),
                        singleLine = true,
                        leadingIcon = {
                            IconButton(onClick = { searchFlag = true; backNavFlag = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = ""
                                )
                            }
                        },
                        value = searchString,
                        onValueChange = { value: String -> searchString = value },
                        placeholder = {
                            Text(
                                text = "Search anime...",
                                fontSize = 14.sp
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
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent.copy(alpha = 0f))
        )
        if (searchLoaded && !backNavFlag) {
            AnimeSearchLoader(animeSearch = animeSearch, viewModel = viewModel, navController = navController)
        }
    }
}



