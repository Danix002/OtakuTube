package com.example.anitest.room

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

    fun delete(playlist: String) {
        dao.delete(PlaylistEntity(playlist, ""))
    }

    fun deleteRelation(relation: PlaylistAnimeRelationEntity) {
        dao.delete(relation)
    }
    suspend fun insert(playlist: String, anime : AnimeDetail) {
        dao.insert(
            PlaylistAnimeRelationEntity(
                playlist,
                anime.anime_id,
                anime.name,
                anime.img_url
            )
        )
    }
}


