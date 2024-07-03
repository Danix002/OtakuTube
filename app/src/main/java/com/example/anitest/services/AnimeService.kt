package com.example.anitest.services

import com.example.anitest.model.Anime
import com.example.anitest.model.AnimeInfo
import com.example.anitest.model.AnimeTrailer
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
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import java.io.IOException

class AnimeService {
    private val gson = Gson()
    private val URLNPM = "https://server-1-otakutube.onrender.com"
    private val URLPYTHON = "https://server-2-otakutube.onrender.com"

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
        if(testConnection()) {
            val animeJson = Util.GET(httpClient, "$URLNPM/genre/$genre/$page") ?: return emptyList()
            val type = object : TypeToken<List<Anime>>() {}.type
            return gson.fromJson(animeJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getAllAnime(page : Number): List<Anime> {
        if(testConnection()) {
            val allAnimeJson = Util.GET(httpClient, "$URLNPM/allAnime/$page") ?: return emptyList()
            val type = object : TypeToken<List<Anime>>() {}.type
            return gson.fromJson(allAnimeJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getPopularAnime(page : Number): List<Anime> {
        if(testConnection()) {
            val popularAnimeJson = Util.GET(httpClient, "$URLNPM/Popular/$page") ?: return emptyList()
            val type = object : TypeToken<List<Anime>>() {}.type
            return gson.fromJson(popularAnimeJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getEpisode(episodeId: String): Episode? {
        if(testConnection()) {
            val episodeJson = Util.GET(httpClient, "$URLNPM/getEpisode/$episodeId") ?: return null
            val type = object : TypeToken<Episode>() {}.type
            return gson.fromJson(episodeJson.readText(), type)
        }else{
            return null
        }
    }

    suspend fun getSimplePopularAnime(page : Number): List<Anime> {
        if(testConnection()) {
            val popularAnimeJson =
                Util.GET(httpClient, "$URLNPM/PopularNoDescription/$page") ?: return emptyList()
            val type = object : TypeToken<List<Anime>>() {}.type
            return gson.fromJson(popularAnimeJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getAnimeInfo(id : String): AnimeInfo {
        if(testConnection()){
            val animeInfoJson = Util.GET(httpClient, "$URLNPM/getAnime/$id") ?: return AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList())
            val type = object : TypeToken<AnimeInfo>() {}.type
            return gson.fromJson(animeInfoJson.readText(), type)
        }
        else{
            return AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList() )
        }
    }

    suspend fun getGenres(): List<Genre> {
        if(testConnection()) {
            val genresJson = Util.GET(httpClient, "$URLNPM/genre") ?: return emptyList()
            val type = object : TypeToken<List<Genre>>() {}.type
            return gson.fromJson(genresJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getAnimeSearch(id: String): List<Anime> {
        if(testConnection()) {
            val searchJson = Util.GET(httpClient, "$URLNPM/search/$id") ?: return emptyList()
            val type = object : TypeToken<List<Anime>>() {}.type
            return gson.fromJson(searchJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getEpisodes(episodes : List<String>): List<Episode> {
        if(testConnection()) {
            var requestEpisodesString = episodes[0]
            val episodesJson: HttpResponse?
            if (episodes.size > 1) {
                requestEpisodesString = episodes.joinToString(separator = "@")
                episodesJson = Util.GET(httpClient, "$URLNPM/getEpisodes/${requestEpisodesString}")
                    ?: return emptyList()
            } else {
                episodesJson = Util.GET(httpClient, "$URLNPM/getEpisode/${requestEpisodesString}")
                    ?: return emptyList()
            }
            val type = object : TypeToken<List<Episode>>() {}.type
            return gson.fromJson(episodesJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun getAnimeTrailer(name : String): List<AnimeTrailer> {
        if(testConnection()) {
            val trailerJson = Util.GET(httpClient, "$URLPYTHON/anime/$name") ?: return emptyList()
            val type = object : TypeToken<List<AnimeTrailer>>() {}.type
            return gson.fromJson(trailerJson.readText(), type)
        }else{
            return emptyList()
        }
    }

    suspend fun testConnection(): Boolean {
        var responseNPM : HttpResponse? = null
        var responsePython: HttpResponse? = null
        val tryResult = try {
            withTimeout(10000) {
                responseNPM = httpClient.get<HttpResponse>(URLNPM)
                responsePython = httpClient.get<HttpResponse>(URLPYTHON)
                if ((responseNPM == null || responseNPM?.status == HttpStatusCode.BadGateway) || responsePython == null){
                    return@withTimeout false
                }else{
                    return@withTimeout true
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("Request timed out: ${e.message}")
            return false
        } catch (e: IOException) {
            println("IO Exception: ${e.message}")
            return false
        } catch (e: Exception) {
            println("Unknown exception: ${e.message}")
            return false
        }
        println("Try result: " + tryResult)
        return  tryResult
    }
}


