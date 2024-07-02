package com.example.anitest.room;

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.coroutines.flow.StateFlow

@Entity(tableName = "Anime")
data class AnimeEntity(
    @PrimaryKey
    val anime_id: String,
    val name: String,
    val img: String,
    val insertOrder: Int
)

