package com.example.anitest.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.anitest.model.AnimeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class AnimeRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playlistRepository: PlaylistRepository

    private val playlistEntity = PlaylistEntity("TestPlaylist", "TestImg")
    private val animeDetail = AnimeDetail("1", "TestAnime", "TestImgUrl")

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        println("setup")
        Dispatchers.setMain(dispatcher)
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        playlistDao = database.daoPlaylist()
        println("end setup")
    }

    @Test
    fun insertTest() = runTest ( timeout = 1000.seconds){
        testScope.launch {
            playlistDao.insert(playlistEntity)
        }

        // advanceUntilIdle()
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->

            println("Database contains: $playlists")
            assertEquals(playlistEntity, playlists[0])
            return@collect
        }
        assertTrue(true)
    }

    @Test
    fun deleteTest() = runTest {
        playlistDao.insert(playlistEntity)
        playlistDao.delete(PlaylistEntity("TestPlaylist", ""))
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            assertEquals(0, playlists.size)
            println("Database contains: $playlists")
        }
    }

    @Test
    fun insertAnimeToPlaylistTest() = runTest {
        playlistRepository.insert("TestPlaylist", animeDetail)
        val playlistWithAnime = playlistDao.getPlaylistWithList("TestPlaylist")
        assertEquals(1, playlistWithAnime.playlists.size)
        assertEquals(animeDetail.anime_id, playlistWithAnime.playlists[0].animeId)
        println("Database contains: $playlistWithAnime")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun closeDb() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
        database.close()
    }
}