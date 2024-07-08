
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.anitest.model.AnimeDetail
import com.example.anitest.room.AppDatabase
import com.example.anitest.room.PlaylistAnimeRelationEntity
import com.example.anitest.room.PlaylistDao
import com.example.anitest.room.PlaylistEntity
import com.example.anitest.room.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnimeRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playlistRepository: PlaylistRepository

    private val playlistEntity = PlaylistEntity("TestPlaylist", "https://gogocdn.net/cover/one-piece-1708412053.png")
    private val animeDetail = AnimeDetail("One Piece", "https://gogocdn.net/cover/one-piece-1708412053.png", "one-piece")

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        playlistDao = database.daoPlaylist()
        playlistRepository = PlaylistRepository(playlistDao)
    }

    @Test
    fun insertTest() = testScope.runTest {
        playlistDao.insert(playlistEntity)
        playlistDao.insert(PlaylistAnimeRelationEntity(playlistEntity.name, animeDetail.name, animeDetail.img_url, animeDetail.anime_id))

        val playlists = playlistDao.getPlaylists().first()
        val anime = playlistDao.getPlaylistWithList(playlistEntity.name).playlists[0].animeName
        assertEquals(playlistEntity, playlists[0])
        assertEquals(animeDetail.name, anime)
    }

    @Test
    fun deleteTest() = testScope.runTest {
        playlistDao.insert(playlistEntity)
        playlistDao.delete(PlaylistEntity("TestPlaylist", "https://gogocdn.net/cover/one-piece-1708412053.png"))

        val playlists = playlistDao.getPlaylists().first()
        assertEquals(0, playlists.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun closeDb() {
        Dispatchers.resetMain()
        database.close()
    }
}
