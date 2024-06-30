package com.example.anitest.room;

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.coroutines.flow.StateFlow

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val img: Int
)

