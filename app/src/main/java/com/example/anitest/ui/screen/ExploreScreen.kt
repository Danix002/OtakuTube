package com.example.anitest.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.model.Genre
import com.example.anitest.ui.components.AppBar
import com.example.anitest.ui.components.BackGroundImage
import com.example.anitest.ui.components.BottomNavigation
import com.example.anitest.ui.components.CategoryRow
import com.example.anitest.ui.components.CategoryRowSkeleton
import com.example.anitest.ui.theme.LightOtakuColorScheme
import com.example.myapplication.MyViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavHostController) {
    val genresList by viewModel.genres.collectAsState()
    val isLoaded by viewModel.isExploreScreenLoaded.collectAsState()
    val filterRequest by viewModel.filterRequest.collectAsState()
    var itemsToShow by remember { mutableIntStateOf(4) }
    val scrollState = rememberScrollState()
    var filterRequestFlag by remember { mutableStateOf(false) }
    var reload by remember { mutableStateOf(false) }
    var filterRequestElement by remember { mutableStateOf<List<Genre>>(listOf(Genre("", ""))) }

    LaunchedEffect(Unit) {
        if(!isLoaded) {
            viewModel.setGenres()
            viewModel.setIsLoadedExploreScreen(flag = true)
        }
    }

    LaunchedEffect(filterRequest) {
        if(filterRequest != null){
            if(filterRequest!!.isNotEmpty()) {
                filterRequestFlag = true
                if(filterRequestElement.size > filterRequest!!.size){
                    reload = true
                }else{
                    reload = false
                }
                filterRequestElement = filterRequest!!
            }else{
                filterRequestFlag = false
            }
        }
    }

    Scaffold (
        containerColor = LightOtakuColorScheme.secondary,
        bottomBar = {
            BottomNavigation(viewModel , navController)
        },
        topBar = {
            AppBar(viewModel, navController)
        }
    ) { contentPadding ->
            BackGroundImage(contentPadding, content = {
                if(!filterRequestFlag) {
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        if (genresList != null) {
                            genresList.take(itemsToShow).forEach { category ->
                                CategoryRow(viewModel, category, navController)
                            }
                        } else {
                            for (i in 1..4) {
                                CategoryRowSkeleton()
                            }
                        }
                    }

                    LaunchedEffect(scrollState.value) {
                        if (scrollState.value == scrollState.maxValue) {
                            if (itemsToShow < genresList.size) {
                                itemsToShow += 4
                            }
                        }
                    }
                }else{
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        if(!reload) {
                            filterRequest?.let {
                                filterRequest!!.forEach {
                                    CategoryRow(viewModel, it, navController)
                                }
                            }
                        }else{
                            CategoryRowSkeleton()
                            reload = false
                        }
                    }
                }
            })
    }
}

