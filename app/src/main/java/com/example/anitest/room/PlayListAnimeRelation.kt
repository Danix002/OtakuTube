package com.example.anitest.room

import androidx.room.Entity

@Entity(tableName = "PlayListAnimeRelation", primaryKeys = ["playlistName", "animeId"])
data class PlayListAnimeRelation(
    val playlistName: String,
    val animeName: String,
    val animeId: String,
    val animeImg: String,
)
