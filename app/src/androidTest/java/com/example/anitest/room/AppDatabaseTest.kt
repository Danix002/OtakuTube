package com.example.anitest.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.anitest.model.AnimeDetail
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var database: AppDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playlistRepository: PlaylistRepository

    private val playlistEntity = PlaylistEntity("TestPlaylist", "TestImg")
    private val animeDetail = AnimeDetail("1", "TestAnime", "TestImgUrl")

    @Before
    public override fun setUp() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        playlistDao = database.daoPlaylist()
    }

    @Test
    fun insertTest() = runBlocking {
        playlistDao.insert(playlistEntity)
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            Assert.assertEquals(1, playlists.size)
            Assert.assertEquals(playlistEntity, playlists[0])
            println("Database contains: $playlists")
        }
    }

    @Test
    fun deleteTest() = runBlocking {
        playlistDao.insert(playlistEntity)
        playlistDao.delete(PlaylistEntity("TestPlaylist", ""))
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            Assert.assertEquals(0, playlists.size)
            println("Database contains: $playlists")
        }
    }

    @Test
    fun insertAnimeToPlaylistTest() = runBlocking {
        playlistRepository.insert("TestPlaylist", animeDetail)
        val playlistWithAnime = playlistDao.getPlaylistWithList("TestPlaylist")
        Assert.assertEquals(1, playlistWithAnime.playlists.size)
        Assert.assertEquals(animeDetail.anime_id, playlistWithAnime.playlists[0].animeId)
        println("Database contains: $playlistWithAnime")
    }

    @After
    fun closeDb() {
        database.close()
    }
}