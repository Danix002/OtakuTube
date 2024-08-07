package com.example.anitest.room;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM Playlist")
    fun getPlaylists(): Flow<List<PlaylistEntity>>

    @Insert
    suspend fun insert(newPlaylist: PlaylistEntity)

    @Insert
    suspend fun insert(animeAdd: PlaylistAnimeRelationEntity)

    @Transaction
    @Query("SELECT * FROM Playlist p WHERE p.name = :name")
    fun getPlaylistWithList(name:String): PlaylistWithList

    @Delete
    fun delete(playlistEntity: PlaylistEntity)

    @Delete
    fun delete(animeRelation: PlaylistAnimeRelationEntity)
}
