package com.tzel.movieflix.data.configuration.model

import com.squareup.moshi.Json

data class RemoteLanguage(
    @Json(name = "iso_639_1") val code: String?,
    @Json(name = "english_name") val englishName: String?,
    val name: String?
)