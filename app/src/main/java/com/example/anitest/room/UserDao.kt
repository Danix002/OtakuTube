package com.example.anitest.room;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): Flow<UserEntity>

    @Insert
    suspend fun insert(newUser: UserEntity)

    @Query("UPDATE User SET name = :name WHERE id = :id")
    suspend fun updateUserNameById(id: Int, name: String)

    @Query("UPDATE User SET img = :img WHERE id = :id")
    suspend fun updateUserImgById(id: Int, img: Int)

    @Delete
    fun delete(userEntity: UserEntity)
}
