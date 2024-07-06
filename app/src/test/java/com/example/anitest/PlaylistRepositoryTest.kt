package com.example.anitest.room

import android.content.Context
import androidx.room.Room

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.anitest.model.AnimeDetail
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class PlaylistRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playlistRepository: PlaylistRepository

    private val playlistEntity = PlaylistEntity("TestPlaylist", "TestImg")
    private val animeDetail = AnimeDetail("1", "TestAnime", "TestImgUrl")

    @BeforeEach
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()
        playlistDao = database.daoPlaylist()
    }

    @AfterEach
    fun closeDb() {
        database.close()
    }

    @Test
    fun `when insert playlist then dao insert is called and database is updated`() = runBlocking {
        // when
        playlistDao.insert(playlistEntity)

        // then
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            assertEquals(1, playlists.size)
            assertEquals(playlistEntity, playlists[0])
            println("Database contains: $playlists")
        }
    }

    @Test
    fun `when delete playlist then dao delete is called and database is updated`() = runBlocking {
        // given
        playlistDao.insert(playlistEntity)

        // when
        playlistDao.delete(PlaylistEntity("TestPlaylist", ""))

        // then
        val allPlaylists = playlistDao.getPlaylists()
        allPlaylists.collect { playlists ->
            assertEquals(0, playlists.size)
            println("Database contains: $playlists")
        }
    }

    @Test
    fun `when insert playlist with anime then dao insert relation is called and database is updated`() = runBlocking {
        // when
        playlistRepository.insert("TestPlaylist", animeDetail)

        // then
        val playlistWithAnime = playlistDao.getPlaylistWithList("TestPlaylist")
        assertEquals(1, playlistWithAnime.playlists.size)
        assertEquals(animeDetail.anime_id, playlistWithAnime.playlists[0].animeId)
        println("Database contains: $playlistWithAnime")
    }
}
