package com.example.anitest.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeInformationApi {
    @GET("anime/{name}")
    fun getAnimeInformations(@Path("name") name: String): Call<List<AnimeInformation>>
}

data class AnimeInformation(
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

