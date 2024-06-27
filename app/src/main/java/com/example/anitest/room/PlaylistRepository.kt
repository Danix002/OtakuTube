package com.example.anitest.room

import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val dao: PlaylistDao) {
    val allPlaylist: Flow<List<PlaylistEntity>> = dao.getPlaylists()

    suspend fun insert(playlist: PlaylistEntity) {
        dao.insert(playlist)
    }
}


