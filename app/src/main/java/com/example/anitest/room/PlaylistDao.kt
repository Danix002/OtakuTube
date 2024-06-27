package com.example.anitest.room;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM Playlist")
    fun getPlaylists(): Flow<List<PlaylistEntity>>

    @Insert
    suspend fun insert(newPlaylist: PlaylistEntity)
}
