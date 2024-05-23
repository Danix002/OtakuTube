package com.example.myapplication


import androidx.lifecycle.ViewModel
import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel : ViewModel() {
    private val animeService = AnimeService()
    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

    suspend fun getAnimeByGenre(page: Number, genre: String) : List<Anime> {
        runCatching {
            _animeList.value = (animeService.getAnimeByGenre(page, genre)) ?: emptyList()
        }.onFailure {
            it.printStackTrace()
        }
        return _animeList.value
    }

}