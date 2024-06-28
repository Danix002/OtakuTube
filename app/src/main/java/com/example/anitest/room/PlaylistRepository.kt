package com.example.anitest.room

import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeDetail
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val dao: PlaylistDao) {
    val allPlaylist: Flow<List<PlaylistEntity>> = dao.getPlaylists()

    suspend fun insert(playlist: PlaylistEntity) {
        dao.insert(playlist)
    }

    fun getPlaylist(name: String): PlaylistWithList {
        return dao.getPlaylistWithList(name)
    }

    suspend fun insert(playlist: String, anime : AnimeDetail) {
        dao.insert(
            PlayListAnimeRelation(
                playlist,
                anime.anime_id,
                anime.name,
                anime.img_url
            )
        )
    }
}


