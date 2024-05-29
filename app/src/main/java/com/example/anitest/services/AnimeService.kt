package com.example.anitest.services

import com.example.anitest.model.Anime
import com.example.anitest.model.Genre
import com.example.anitest.utils.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpRedirect
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.statement.readText
import kotlinx.serialization.json.Json

class AnimeService {

    private val baseURLDANIport = "http://192.168.1.5:3000"
    private val baseURLDANIfix = "http://192.168.1.7:3000"
    private val baseURLALE = "http://172.20.10.3:3000"
    private val gson = Gson()

    private val httpClient get() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                ignoreUnknownKeys = true
            })
        }
        install(HttpRedirect) {
            checkHttpMethod = true
        }

    }

    suspend fun getAnimeByGenre(page: Number, genre : String): List<Anime> {
        val animeJson = Util.GET(httpClient, "$baseURLALE/genre/$genre/$page") ?: return emptyList()
        //val animeJson = Util.GET(httpClient, "$baseURLDANIport/genre/$genre/$page") ?: return emptyList()
        //val animeJson = Util.GET(httpClient, "$baseURLDANIfix/genre/$genre/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(animeJson.readText(), type)
    }

    suspend fun getGenres(): List<Genre> {
        val genresJson = Util.GET(httpClient, "$baseURLALE/genre") ?: return emptyList()
        //val animeJson = Util.GET(httpClient, "$baseURLDANIport/genre") ?: return emptyList()
        //val genresJson = Util.GET(httpClient, "$baseURLDANIfix/genre") ?: return emptyList()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genresJson.readText(), type)
    }

}

