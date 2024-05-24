package com.example.anitest.utils

import io.ktor.client.HttpClient
import android.content.ContentValues
import android.util.Log
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import java.util.concurrent.TimeoutException

class Util {



    companion object {
        /***
         * Do a  Http Request
         * of Method: GET
         *
         *  @param client : httpClient  to use
         *  @param url: Url of the desired request
         *  @return the response of request, null if there is an error
         *
         ***/
        suspend fun GET(client: HttpClient, url: String): HttpResponse? {
            try {
                val response  = client.get<HttpResponse>(url)
                return response
            } catch (e: ClientRequestException) {
                Log.d(ContentValues.TAG, "ClientRequestException ${e.message}")

            } catch (e: ServerResponseException) {
                Log.d(ContentValues.TAG, "ServerResponseException ${e.message}")

            } catch (e: TimeoutException) {
                Log.d(ContentValues.TAG, "TimeoutException ${e.message}")

            } catch (e : RedirectResponseException) {
                Log.d(ContentValues.TAG, "REDIRECT ${e.message}")
                Log.d(ContentValues.TAG, "REDIRECT EVENT ${e}")

            } catch (e : Exception) {
                Log.d(ContentValues.TAG, "exception ${e.message}")

            } finally {
                client.close()
            }
            return null
        }
    }

}