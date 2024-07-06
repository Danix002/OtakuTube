package com.example.anitest.room


import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.anitest.model.AnimeDetail
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class PlaylistRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playlistRepository: PlaylistRepository

    private val playlistEntity = PlaylistEntity("TestPlaylist", "TestImg")
    private val animeDetail = AnimeDetail("1", "TestAnime", "TestImgUrl")

    @Before
    fun initDb() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).allowMainThreadQueries().build()
        playlistDao = database.daoPlaylist()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertTest() = runBlocking {
        playlistDao.insert(playlistEntity)
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            assertEquals(1, playlists.size)
            assertEquals(playlistEntity, playlists[0])
            println("Database contains: $playlists")
        }
    }

    @Test
    fun deleteTest() = runBlocking {
        playlistDao.insert(playlistEntity)
        playlistDao.delete(PlaylistEntity("TestPlaylist", ""))
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            assertEquals(0, playlists.size)
            println("Database contains: $playlists")
        }
    }

    @Test
    fun insertAnimeToPlaylistTest() = runBlocking {
        playlistRepository.insert("TestPlaylist", animeDetail)
        val playlistWithAnime = playlistDao.getPlaylistWithList("TestPlaylist")
        assertEquals(1, playlistWithAnime.playlists.size)
        assertEquals(animeDetail.anime_id, playlistWithAnime.playlists[0].animeId)
        println("Database contains: $playlistWithAnime")
    }
}
