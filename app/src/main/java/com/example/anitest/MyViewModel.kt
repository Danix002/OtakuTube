package com.example.myapplication


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.anitest.navigation.Screen
import com.example.anitest.services.AnimeService
import com.example.anitest.utils.NavigationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val animeService = AnimeService()

    var navigationItems = mutableStateOf(listOf(
        NavigationItem(
            title = "Zapping",
            route = Screen.Zapping.route,
            hasNews = false,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        ),
        NavigationItem(
            title = "Explore",
            route = Screen.Home.route,
            hasNews = false,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Library",
            route = Screen.Library.route,
            hasNews = false,
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit
        ),
        NavigationItem(
            title = "Profile",
            route = Screen.Profile.route,
            hasNews = false,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        ),
    ))

    var selectedNavItem: MutableState<String> = mutableStateOf(Screen.Home.route)

    suspend fun getAnimeByGenre(page: Number, genre: String) : List<Anime> {
        val animeByGenre = MutableStateFlow<List<Anime>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                animeByGenre.value = animeService.getAnimeByGenre(page, genre) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return animeByGenre.value
    }

    suspend fun getGenres() : List<Genre> {
        val genres = MutableStateFlow<List<Genre>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                genres.value = animeService.getGenres() ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return genres.value
    }
}