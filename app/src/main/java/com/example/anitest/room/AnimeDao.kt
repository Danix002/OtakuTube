package com.example.anitest.room;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM Anime WHERE anime_id = :id")
    fun getAnimeById(id: String): AnimeEntity

    @Query("SELECT * FROM Anime")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM Anime ORDER BY insertOrder ASC LIMIT 1")
    fun getOldestInsertedAnime(): AnimeEntity?

    @Query("SELECT MAX(insertOrder) FROM Anime")
    fun getMaxInsertOrder(): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newAnime: AnimeEntity)

    @Query("UPDATE Anime SET name = :name, anime_id = :id, img = :img  WHERE anime_id = :id")
    suspend fun updateAnimeById(id: String, name: String, img: String)

    @Delete
    fun delete(animeEntity: AnimeEntity)

    @Query("DELETE FROM Anime")
    fun deleteAll()
}
