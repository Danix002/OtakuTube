package com.example.anitest.room;

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anitest.model.Anime

@Entity(tableName = "Playlist")
data class PlaylistEntity(
    @PrimaryKey
    val name: String,
    val img: String
    //val anime: List<Anime>
)
