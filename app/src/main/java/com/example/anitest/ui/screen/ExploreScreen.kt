package com.example.anitest.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.anitest.ui.componets.AppBar
import com.example.anitest.ui.componets.BackGroundImage
import com.example.anitest.ui.componets.BottomNavigation
import com.example.anitest.ui.componets.CategoryRow
import com.example.anitest.ui.componets.CategoryRowSkeleton
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavHostController) {
    val genresList by viewModel.genres.collectAsState()
    val isLoaded by viewModel.isExploreScreenLoaded.collectAsState()
    var itemsToShow by remember { mutableIntStateOf(4) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if(!isLoaded) {
            viewModel.setGenres()
            viewModel.setIsLoadedExploreScreen(flag = true)
        }
    }

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
                Column (modifier = Modifier.verticalScroll(scrollState)){
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
            })
    }
}

