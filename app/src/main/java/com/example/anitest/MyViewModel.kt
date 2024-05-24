package com.example.myapplication


import androidx.lifecycle.ViewModel
import com.example.anitest.model.Anime
import com.example.anitest.services.AnimeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val animeService = AnimeService()
    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

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

}