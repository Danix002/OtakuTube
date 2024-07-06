package com.example.anitest

import com.example.anitest.model.Anime
import com.example.anitest.services.AnimeService
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.runBlocking


import org.junit.Assert.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
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
        val animes = service.getPopularAnime(0)
        println("Conn: $animes")
        assertNotEquals(animes, emptyList<Anime>())
    }
}