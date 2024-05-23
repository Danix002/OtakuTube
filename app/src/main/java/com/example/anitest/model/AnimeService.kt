package com.example.anitest.model

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

    private val baseURL = "http://192.168.1.5:3000"
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
        val animeJson = Util.GET(httpClient, "$baseURL/genre/$genre/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(animeJson.readText(), type)
    }

}

