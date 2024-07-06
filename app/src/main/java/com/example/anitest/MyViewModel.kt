package com.example.myapplication

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.anitest.R
import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeDetail
import com.example.anitest.model.AnimeInfo
import com.example.anitest.model.AnimeTrailer
import com.example.anitest.model.Episode
import com.example.anitest.model.Genre
import com.example.anitest.navigation.Screen
import com.example.anitest.room.AnimeEntity
import com.example.anitest.room.AnimeRepository
import com.example.anitest.room.AppDatabase
import com.example.anitest.room.PlaylistAnimeRelationEntity
import com.example.anitest.room.PlaylistEntity
import com.example.anitest.room.PlaylistRepository
import com.example.anitest.room.PlaylistWithList
import com.example.anitest.room.UserEntity
import com.example.anitest.room.UserRepository
import com.example.anitest.services.AnimeService
import com.example.anitest.utils.NavigationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val animeService = AnimeService()
    private val repositoryPlaylist : PlaylistRepository
    val allPlaylist: Flow<List<PlaylistEntity>>
    private val repositoryUser : UserRepository
    var user: Flow<UserEntity>
    private val repositoryAnime : AnimeRepository
    var allAnime: Flow<List<AnimeEntity>>

    init {
        val daoPlaylist = AppDatabase.getDatabase(application).daoPlaylist()
        repositoryPlaylist = PlaylistRepository(daoPlaylist)
        allPlaylist = repositoryPlaylist.allPlaylist
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val a = getPlaylist("Favourite");
                if (a == null) {
                    insertPlaylist(PlaylistEntity(name = "Favourite", img = ""))
                }
            }
        }

        val daoUser = AppDatabase.getDatabase(application).daoUser()
        repositoryUser = UserRepository(daoUser)
        user = repositoryUser.user
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val a = getUser()
                if (a != null) {
                    if (a.firstOrNull() == null) {
                        insertUser(UserEntity(id = 1, name = "User", img = R.drawable.avatar1))
                    }
                }
            }
        }

        val daoAnime = AppDatabase.getDatabase(application).daoAnime()
        repositoryAnime = AnimeRepository(daoAnime)
        allAnime = repositoryAnime.allAnime
    }

    fun insertPlaylist(playlist: PlaylistEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryPlaylist.insert(playlist)
        }
    }

    fun insertAnime(anime: AnimeEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryAnime.insert(anime)
        }
    }

    fun deletePlaylist(playlist: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryPlaylist.delete(playlist)
        }
    }

    fun deleteRelation(relation: PlaylistAnimeRelationEntity)  = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryPlaylist.deleteRelation(relation)
        }
    }

    fun deleteUser(id: Int)  = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryUser.delete(id)
        }
    }

    fun deleteAnime(anime: AnimeEntity)  = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryAnime.delete(anime)
        }
    }

    suspend fun deleteAll()  = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryAnime.deleteAll()
        }
    }.join()

    suspend fun getMaxInsertOrderAnime(): Int?{
        return withContext(Dispatchers.IO) {
            repositoryAnime.getMaxInsertOrder()
        }
    }

    suspend fun getOldestInsertedAnime(): AnimeEntity? {
        return withContext(Dispatchers.IO) {
            repositoryAnime.getOldestInsertedAnime()
        }
    }

    fun insertUser(user: UserEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repositoryUser.insert(user)
        }
    }

    fun insertPlaylist(playlist: String, anime: AnimeDetail) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                repositoryPlaylist.insert(playlist, anime)
            } catch (exception: SQLiteConstraintException) {
                // key already exist
            }
        }
    }

    suspend fun getPlaylist(name: String): PlaylistWithList {
        var playlist : PlaylistWithList = PlaylistWithList(PlaylistEntity("",""), emptyList())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playlist = repositoryPlaylist.getPlaylist(name)
            }
        }.join()
        return playlist

    }

    suspend fun getUser(): Flow<UserEntity>? {
        var user : Flow<UserEntity>? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                user = repositoryUser.getUserById()
            }
        }.join()
        return user
    }

    suspend fun getAnime(id: String): AnimeEntity? {
        var anime : AnimeEntity? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                anime = repositoryAnime.getAnimeById(id)
            }
        }.join()
        return anime
    }

    suspend fun updateUser(name: String, img: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repositoryUser.updateUserById(name, img)
            }
        }.join()
    }

    suspend fun updateAnime(id: String, name: String, img: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repositoryAnime.updateAnimeById(id, name, img)
            }
        }.join()
    }

    private val _animeInfoTrailer = MutableStateFlow<List<AnimeTrailer>?>(null)
    val animeInfoTrailer: StateFlow<List<AnimeTrailer>?> get() = _animeInfoTrailer

    private val _animeInfo = MutableStateFlow<AnimeInfo?>(null)
    val animeInfo: StateFlow<AnimeInfo?> get() = _animeInfo

    private val _animeForGenres = MutableStateFlow<HashMap<String, List<Anime>>>(hashMapOf())
    val animeForGenres: StateFlow<HashMap<String, List<Anime>>> get() = _animeForGenres

    private val _popularAnime = MutableStateFlow<List<Anime>>(emptyList())
    val popularAnime: StateFlow<List<Anime>> get() = _popularAnime

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> get() = _genres

    private val _animeSearch = MutableStateFlow<List<Anime>>(emptyList())
    val animeSearch: StateFlow<List<Anime>> get() = _animeSearch

    private val _currentEpisodeIndex = MutableStateFlow(0)
    val currentEpisodeIndex: StateFlow<Int> get() = _currentEpisodeIndex

    private val _episodes = MutableStateFlow<List<Episode?>?>(emptyList())
    val episodes: StateFlow<List<Episode?>?> get() = _episodes

    private val _isSearchScreenOpen = MutableLiveData(false)
    val isSearchScreenOpen: LiveData<Boolean> get() = _isSearchScreenOpen

    private val _isEpisodesButtonOpen = MutableLiveData(false)
    val isEpisodesButtonOpen: LiveData<Boolean> get() = _isEpisodesButtonOpen

    private val _isAnimeScreenLoaded = MutableStateFlow(false)
    val isAnimeScreenLoaded: StateFlow<Boolean> get() = _isAnimeScreenLoaded

    private val _isZappingScreenLoaded = MutableStateFlow(false)
    val isZappingScreenLoaded: StateFlow<Boolean> get() = _isZappingScreenLoaded

    private val _isExploreScreenLoaded = MutableStateFlow(false)
    val isExploreScreenLoaded: StateFlow<Boolean> get() = _isExploreScreenLoaded

    private val _isCategoryRowLoaded = MutableStateFlow(HashMap<String, Boolean>(hashMapOf()))
    val isCategoryRowLoaded: StateFlow<HashMap<String, Boolean>> get() = _isCategoryRowLoaded

    private val _episodesIds = MutableStateFlow<List<String>?>(null)
    val episodesIds: StateFlow<List<String>?> get() = _episodesIds

    private val _connection = MutableStateFlow<Boolean>(true)
    val connection: StateFlow<Boolean> get() = _connection


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

    private val _currentEpisode = MutableStateFlow<Episode?>(null)
    val currentEpisode: StateFlow<Episode?> get() = _currentEpisode

    private val _isEpisodeLoaded = MutableStateFlow(false)
    val isEpisodeLoaded: StateFlow<Boolean> get() = _isEpisodeLoaded

    private val _filterRequest = MutableStateFlow<List<Genre>?>(null)
    val filterRequest: StateFlow<List<Genre>?> get() = _filterRequest

    fun initEpisodes(n: Int) {
        var i = n
        _episodes.value = emptyList()
        while (i > 0) {
            _episodes.value = _episodes.value!!.plus(null)
            i--
        }
    }

    fun addEpisodes(episode: Episode) {
        if(_episodes.value != null && episode.index >= 0) {
            var list = _episodes.value!!.toMutableList()
            list.set(episode.index-1, episode)
            _episodes.value = list
        }
    }

    fun removeAllFilterRequest() {
        _filterRequest.value = emptyList()
    }

    suspend fun setEpisode(episodeId: String) {
        viewModelScope.launch {
            var tmp = getEpisode(episodeId)
            if (tmp != null) {
                if ((tmp.index-1) >= 0) {
                    _currentEpisode.value = tmp
                }
            }
        }.join()
    }

    fun addFilterRequest(genre: Genre) {
        if(_filterRequest.value != null) {
            if(!(_filterRequest.value!!.contains(genre))) {
                _filterRequest.value = _filterRequest.value?.plus(genre)
            }
        }else{
            _filterRequest.value = listOf(genre)
        }
    }

    fun removeFilterRequest(genre: Genre) {
        if(_filterRequest.value != null) {
            if(_filterRequest.value!!.contains(genre)) {
                _filterRequest.value = _filterRequest.value?.minus(genre)
            }
        }
    }

    private suspend fun getEpisode(episodeId: String): Episode? {
        val animeEpisode = MutableStateFlow<Episode?>(null)
        withContext(Dispatchers.IO) {
            runCatching {
                animeEpisode.value = animeService.getEpisode(episodeId)
            }.onFailure {
                it.printStackTrace()
            }
        }
        return animeEpisode.value
    }

    suspend fun getPublicEpisode(episodeId: String): Episode? {
        return getEpisode(episodeId)
    }

    fun setIsLoadedAnimeScreen(flag : Boolean) {
        _isAnimeScreenLoaded.value = flag
        if(!flag){
            setIsLoadedEpisode(flag)
        }
    }

    fun setIsLoadedZappingScreen(flag : Boolean) {
        _isZappingScreenLoaded.value = flag
    }

    fun setIsLoadedExploreScreen(flag : Boolean) {
        _isExploreScreenLoaded.value = flag
    }

    fun setIsLoadedEpisode(flag : Boolean) {
        _isEpisodeLoaded.value = flag
    }

    fun setIsLoadedCategory(category: String, flag : Boolean) {
        if(_isCategoryRowLoaded.value.containsKey(category)){
            _isCategoryRowLoaded.value.set(category, flag)
        }else{
            isCategoryRowLoaded.value.put(category, flag)
        }
    }

    fun isLoadedEpisode(): Boolean {
        return _isEpisodeLoaded.value
    }

    fun openEpisodes() {
        _isEpisodesButtonOpen.value = true
    }

    fun closeEpisodes() {
        _isEpisodesButtonOpen.value = false
    }

    fun openSearch() {
        _isSearchScreenOpen.value = true
    }

    fun closeSearch() {
        _isSearchScreenOpen.value = false
    }

    fun getFlagSearch(): Boolean {
        if(_isSearchScreenOpen.value != null)
            return _isSearchScreenOpen.value!!
        else
            return false
    }

    fun isLoadedAnimeScreen(): Boolean {
        return _isAnimeScreenLoaded.value
    }

    suspend fun setAnimeInfo(id: String){
        viewModelScope.launch {
            _animeInfo.value = getAnimeInfo(id)
        }.join()
        _episodesIds.value = _animeInfo.value?.episode_id ?: emptyList()

    }

    fun setCurrentEpisodeIndex(index: Int) {
        _currentEpisodeIndex.value = index
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
        _episodesIds.value = null
    }

    fun forgetAnimeSearch(){
        _animeSearch.value = emptyList()
    }

    suspend fun setAnimeSearch(id: String){
        viewModelScope.launch {
            _animeSearch.value = getAnimeSearch(id)
        }.join()
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

    suspend fun addPopularAnime(page: Number){
        viewModelScope.launch {
            if(page == 0) {
                _popularAnime.value = getPopularAnime(page)
            }else {
                val currentPopularAnimeList = _popularAnime.value
                val newPopularAnimeList = getPopularAnime(page)
                _popularAnime.value = currentPopularAnimeList + newPopularAnimeList
            }
        }.join()
    }

    fun setGenres() {
        viewModelScope.launch {
            _genres.value = getGenres()
            _genres.value.forEach {
                setIsLoadedCategory(it.id, false)
            }
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

    private suspend fun getPopularAnime(page : Number): List<Anime> {
        val popularAnime = MutableStateFlow<List<Anime>>(emptyList())
        withContext(Dispatchers.IO) {
            runCatching {
                popularAnime.value = animeService.getSimplePopularAnime(page) ?: emptyList()
            }.onFailure {
                it.printStackTrace()
            }
        }
        return popularAnime.value
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

    suspend fun testConnection(): Boolean {
        val connectionTest : Boolean = animeService.testConnection()
        _connection.value = connectionTest
        return connectionTest;
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