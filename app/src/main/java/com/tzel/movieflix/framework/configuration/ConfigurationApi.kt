package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteSession
import com.tzel.movieflix.data.configuration.model.RemoteSessionRequest
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConfigurationApi {
    @GET("/3/configuration/languages")
    suspend fun getAvailableLanguages(): Response<List<RemoteLanguage?>>

    @GET("/3/authentication/token/new")
    suspend fun requestTemporaryRequestToken(): Response<RemoteTemporaryRequestToken>

    @POST("/3/authentication/session/new")
    suspend fun createSession(@Body requestToken: RemoteSessionRequest): Response<RemoteSession>
}