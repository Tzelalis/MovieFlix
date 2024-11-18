package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.model.RemoteAccessToken
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteSessionRequest
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConfigurationApi {
    @GET("/3/configuration/languages")
    suspend fun getAvailableLanguages(): Response<List<RemoteLanguage?>>

    @POST("/4/auth/request_token")
    suspend fun requestTemporaryRequestToken(): Response<RemoteTemporaryRequestToken>

    @POST("/4/auth/access_token")
    suspend fun createAccessToken(@Body requestToken: RemoteSessionRequest): Response<RemoteAccessToken>
}