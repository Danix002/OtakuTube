package com.example.anitest.room;

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Playlist")
data class PlaylistEntity(
    @PrimaryKey
    val name: String,
    val img: String,
)

data class PlaylistWithList(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "playlistName"
    )
    val playlists: List<PlaylistAnimeRelationEntity>
)
