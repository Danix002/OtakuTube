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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeInfo
import com.example.anitest.model.AnimeTrailer
import com.example.anitest.model.Episode
import com.example.anitest.model.Genre
import com.example.anitest.navigation.Screen
import com.example.anitest.services.AnimeService
import com.example.anitest.utils.NavigationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyViewModel : ViewModel() {
    private val animeService = AnimeService()

    private val _animeInfoTrailer = MutableStateFlow<List<AnimeTrailer>?>(null)
    val animeInfoTrailer: StateFlow<List<AnimeTrailer>?> get() = _animeInfoTrailer

    private val _animeInfo = MutableStateFlow<AnimeInfo?>(null)
    val animeInfo: StateFlow<AnimeInfo?> get() = _animeInfo

    private val _animeForGenres = MutableStateFlow<HashMap<String, List<Anime>>>(hashMapOf())
    val animeForGenres: StateFlow<HashMap<String, List<Anime>>> get() = _animeForGenres

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> get() = _genres

    private val _animeSearch = MutableStateFlow<List<Anime>>(emptyList())
    val animeSearch: StateFlow<List<Anime>> get() = _animeSearch

    private val _currentEP = MutableStateFlow<Int>(0)
    val currentEP: StateFlow<Int> get() = _currentEP

    private val _episodes = MutableStateFlow<List<Episode>?>(emptyList())
    val episodes: StateFlow<List<Episode>?> get() = _episodes

    private val _isEpisodesButtonOpen = MutableLiveData(false)
    val isEpisodesButtonOpen: LiveData<Boolean> get() = _isEpisodesButtonOpen

    private val _isAnimeScreenLoaded = MutableStateFlow(false)
    val isAnimeScreenLoaded: StateFlow<Boolean> get() = _isAnimeScreenLoaded

    var navigationItems = mutableStateOf(
        listOf(
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
        )
    )

    var selectedNavItem: MutableState<String> = mutableStateOf(Screen.Home.route)

    fun openEpisodes() {
        _isEpisodesButtonOpen.value = true
    }

    fun closeEpisodes() {
        _isEpisodesButtonOpen.value = false
    }

    fun setIsLoadedAnimeScreen( flag : Boolean ) {
        _isAnimeScreenLoaded.value = flag
    }

    fun isLoadedAnimeScreen(): Boolean {
        return _isAnimeScreenLoaded.value
    }

    suspend fun setAnimeInfo(id: String): List<String> {
        viewModelScope.launch {
            _animeInfo.value = getAnimeInfo(id)
        }.join()
        return _animeInfo.value?.episode_id ?: emptyList()
    }

    fun setCurrentEP(link: Int) {
        _currentEP.value = link
    }

    fun setEpisodes(episodes: List<String>) {
        viewModelScope.launch {
            _episodes.value = getEpisodes(episodes)
        }
    }

    fun forgetEpisodes(){
        _episodes.value = null
    }

    fun forgetAnimeInfo(){
        _animeInfo.value = null
    }

    fun forgetAnimeSearch(){
        _animeSearch.value = emptyList()
    }

    fun setAnimeSearch(id: String){
        println(id)
        viewModelScope.launch {
            _animeSearch.value = getAnimeSearch(id)
            println(_animeSearch.value)
        }
    }

    suspend fun addAnimeByGenre(page: Number, genre: String): Boolean{
        var flagLoading = true
        viewModelScope.launch {
            if(_animeForGenres.value.containsKey(genre)) {
                if(page == 0)
                    _animeForGenres.value.set(genre, getAnimeByGenre(page, genre))
                else {
                    val currentAnimeListByGenre = _animeForGenres.value.get(genre)
                    val newAnimeListByGenre = getAnimeByGenre(page, genre)
                    _animeForGenres.value.set(genre, currentAnimeListByGenre!! + newAnimeListByGenre)
                    flagLoading = (newAnimeListByGenre.size == 0)
                }
            }else
                _animeForGenres.value.put(genre, getAnimeByGenre(page, genre))
        }.join()
        return flagLoading
    }

    fun setGenres() {
        viewModelScope.launch {
            _genres.value = getGenres()
        }
    }

    fun setAnimeInfoTrailer(name: String) {
        viewModelScope.launch {
            _animeInfoTrailer.value = getAnimeInfoTrailer(name)
        }
    }

    fun forgetAnimeInfoTrailer() {
        _animeInfoTrailer.value = null
    }

    private suspend fun getAnimeByGenre(page: Number, genre: String): List<Anime> {
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

    private suspend fun getEpisodes(episodes: List<String>): List<Episode> {
        val animeEpisodes = MutableStateFlow<List<Episode>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                animeEpisodes.value = animeService.getEpisodes(episodes) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return animeEpisodes.value
    }

    private suspend fun getAnimeSearch(id: String): List<Anime> {
        val animeSearch = MutableStateFlow<List<Anime>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                animeSearch.value = animeService.getAnimeSearch(id) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return animeSearch.value
    }

    private suspend fun getAllAnime(page : Number): List<Anime> {
        val allAnime = MutableStateFlow<List<Anime>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                allAnime.value = animeService.getAllAnime(page) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return allAnime.value
    }

    private suspend fun getAnimeInfo(id : String): AnimeInfo {
        val animeInfo = MutableStateFlow<AnimeInfo>(
            AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList() )
        )
        withContext(Dispatchers.IO) {
            runCatching {
                animeInfo.value = animeService.getAnimeInfo(id) ?: AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList() )
            }.onFailure {
                it.printStackTrace()
            }
        }
        return animeInfo.value
    }

    private suspend fun getGenres(): List<Genre> {
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

    private suspend fun getAnimeInfoTrailer(name: String): List<AnimeTrailer> {
        val animeTrailers = MutableStateFlow<List<AnimeTrailer>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                animeTrailers.value = animeService.getAnimeTrailer(name) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }

        return animeTrailers.value
    }

}