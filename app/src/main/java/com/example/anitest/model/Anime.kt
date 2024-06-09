package com.example.anitest.model

data class Anime(
    val name: String,
    val img_url: String,
    val anime_id: String
) {
    override fun toString(): String {
        return name
    }
}


