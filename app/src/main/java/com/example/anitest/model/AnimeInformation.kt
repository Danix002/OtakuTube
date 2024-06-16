package com.example.anitest.model

data class AnimeTrailer(
    val categories: List<Categories>,
    val createdAt: String,
    val dayViews: String,
    val dub: String,
    val durationEpisodes: String,
    val episodes: String,
    val id: String,
    val image: String,
    val jtitle: String,
    val language: String,
    val link: String,
    val malId: String,
    val malVote: String,
    val mangaworldId: String,
    val monthViews: String,
    val name: String,
    val release: String,
    val season: String,
    val state: String,
    val story: String,
    val studio: String,
    val totViews: String,
    val trailer: String,
    val weekViews: String,
    val year: String
)

data class AnimeInfo(
    val name: String,
    val img_url: String,
    val about: String,
    val episode_id: List<String>,
    val type: String,
    val release: String,
    val genres: List<String>,
    val status: String,
    val othername: List<String>,
)

