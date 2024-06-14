package com.example.anitest.services

import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeInfo
import com.example.anitest.model.Episode
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimeService {

    private val baseURLDANIport = "http://192.168.1.5:3000"
    private val baseURLDANIfix = "http://192.168.1.8:3000"
    private val baseURLALE = "http://172.20.10.3:3000"
    private val gson = Gson()
    private val URL = baseURLALE

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
        val animeJson = Util.GET(httpClient, "$URL/genre/$genre/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(animeJson.readText(), type)
    }

    suspend fun getAllAnime(page : Number): List<Anime> {
        val allAnimeJson = Util.GET(httpClient, "$URL/allAnime/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(allAnimeJson.readText(), type)
    }

    suspend fun getAnimeInfo(id : String): AnimeInfo {
        val animeInfoJson = Util.GET(httpClient, "$URL/getAnime/$id") ?: return AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList() )
        val type = object : TypeToken<AnimeInfo>() {}.type
        return gson.fromJson(animeInfoJson.readText(), type)
    }

    suspend fun getGenres(): List<Genre> {
        val genresJson = Util.GET(httpClient, "$URL/genre") ?: return emptyList()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genresJson.readText(), type)
    }

    suspend fun getEpisodes(episodes : List<String>): List<Episode> {
        val genresJson = Util.GET(httpClient, "$URL/getEpisodes/${episodes.joinToString(separator = "@")}") ?: return emptyList()
        println(genresJson.readText())
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>?>(genresJson.readText(), type).map { Episode(0, it) }
    }





    object RetrofitClient {
        private val baseURLDANIport = "http://192.168.1.5:5000"
        private val baseURLDANIfix = "http://192.168.1.3:5000"
        private val baseURLALE = "http://172.20.10.3:5000"
        private val URLPYTHON = baseURLALE

        val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(URLPYTHON)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}


