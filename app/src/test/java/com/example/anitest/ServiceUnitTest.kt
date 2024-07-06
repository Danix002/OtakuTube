package com.example.anitest

import com.example.anitest.model.Anime
import com.example.anitest.services.AnimeService
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.Assert.*
import org.junit.jupiter.api.Assertions

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
        Assertions.assertTrue(connection)
    }

    @Test
    fun getAnimePopularTest() = runBlocking {
        val service = AnimeService()
        val animes = service.getPopularAnime(0)
        println("Conn: $animes")
        Assertions.assertNotEquals(animes, emptyList<Anime>())
    }
}