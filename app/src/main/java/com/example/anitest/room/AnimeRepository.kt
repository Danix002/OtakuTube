package com.example.anitest.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AnimeRepository(private val dao: AnimeDao) {
    val allAnime: Flow<List<AnimeEntity>> = dao.getAllAnime()

    suspend fun insert(anime: AnimeEntity) {
        dao.insert(anime)
    }

    fun getAnimeById(id: String): AnimeEntity {
        return dao.getAnimeById(id)
    }

    fun delete(anime: AnimeEntity) {
        dao.delete(anime)
    }

    fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun updateAnimeById(id: String, name: String, img: String) {
       dao.updateAnimeById(id, name, img)
    }

    fun getMaxInsertOrder(): Int?{
        return dao.getMaxInsertOrder()
    }

    fun getOldestInsertedAnime(): AnimeEntity? {
        return dao.getOldestInsertedAnime()
    }
}


