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
        val animeJson = Util.GET(httpClient, "$URLNPM/genre/$genre/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(animeJson.readText(), type)
    }

    suspend fun getAllAnime(page : Number): List<Anime> {
        val allAnimeJson = Util.GET(httpClient, "$URLNPM/allAnime/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(allAnimeJson.readText(), type)
    }

    suspend fun getPopularAnime(page : Number): List<Anime> {
        val popularAnimeJson = Util.GET(httpClient, "$URLNPM/Popular/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(popularAnimeJson.readText(), type)
    }
    suspend fun getEpisode(episodeId: String): Episode? {
        val episodeJson = Util.GET(httpClient, "$URLNPM/getEpisode/$episodeId") ?: return null
        val type = object : TypeToken<Episode>() {}.type
        return gson.fromJson(episodeJson.readText(), type)
    }

    suspend fun getSimplePopularAnime(page : Number): List<Anime> {
        val popularAnimeJson = Util.GET(httpClient, "$URLNPM/PopularNoDescription/$page") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(popularAnimeJson.readText(), type)
    }

    suspend fun getAnimeInfo(id : String): AnimeInfo {
        val animeInfoJson = Util.GET(httpClient, "$URLNPM/getAnime/$id") ?: return AnimeInfo("","","", emptyList(), "", "", emptyList(), "", emptyList() )
        val type = object : TypeToken<AnimeInfo>() {}.type
        return gson.fromJson(animeInfoJson.readText(), type)
    }

    suspend fun getGenres(): List<Genre> {
        val genresJson = Util.GET(httpClient, "$URLNPM/genre") ?: return emptyList()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genresJson.readText(), type)
    }

    suspend fun getAnimeSearch(id: String): List<Anime> {
        val searchJson = Util.GET(httpClient, "$URLNPM/search/$id") ?: return emptyList()
        val type = object : TypeToken<List<Anime>>() {}.type
        return gson.fromJson(searchJson.readText(), type)
    }

    suspend fun getEpisodes(episodes : List<String>): List<Episode> {
        var requestEpisodesString = episodes[0]
        val episodesJson: HttpResponse?
        if(episodes.size > 1) {
            requestEpisodesString = episodes.joinToString(separator = "@")
            episodesJson = Util.GET(httpClient, "$URLNPM/getEpisodes/${requestEpisodesString}") ?: return emptyList()
        }else{
            episodesJson = Util.GET(httpClient, "$URLNPM/getEpisode/${requestEpisodesString}") ?: return emptyList()
        }
        val type = object : TypeToken<List<Episode>>() {}.type
        return gson.fromJson(episodesJson.readText(), type)
    }

    suspend fun getAnimeTrailer(name : String): List<AnimeTrailer> {
        val trailerJson = Util.GET(httpClient, "$URLPYTHON/anime/$name") ?: return emptyList()
        val type = object : TypeToken<List<AnimeTrailer>>() {}.type
        return gson.fromJson(trailerJson.readText(), type)
    }

    suspend fun testConnection(): Boolean {
        var responseNPM : HttpResponse? = null
        var responsePython: HttpResponse? = null
        val tryResult = try {
            withTimeout(10000) {
                responseNPM = Util.GET(httpClient, URLNPM)
                responsePython = Util.GET(httpClient, URLNPM)
                if ((responseNPM == null || responseNPM?.status == HttpStatusCode.BadGateway) || responsePython == null){
                    false
                }else{
                    true
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("Request timed out: ${e.message}")
            false
        } catch (e: IOException) {
            println("IO Exception: ${e.message}")
            false
        } catch (e: Exception) {
            println("Unknown exception: ${e.message}")
            false
        }
        println("Try result: " + tryResult)
        return  tryResult
    }
}


