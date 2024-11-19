package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import retrofit2.Response
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("/3/configuration/languages")
    suspend fun getAvailableLanguages(): Response<List<RemoteLanguage?>>
}