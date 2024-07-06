package com.example.anitest

import com.example.anitest.model.Anime
import com.example.anitest.services.AnimeService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test


class ServiceUnitTest {
    @Test
    fun testConnectionTest() = runBlocking {
        val service = AnimeService()
        val connection = service.testConnection()
        println("Conn: $connection")
        assertTrue(connection)
    }

    @Test
    fun getAnimePopularTest() = runBlocking {
        val service = AnimeService()
        val animeList = service.getPopularAnime(0)
        println("Anime list: $animeList")
        assertNotEquals(animeList, emptyList<Anime>())
    }
}