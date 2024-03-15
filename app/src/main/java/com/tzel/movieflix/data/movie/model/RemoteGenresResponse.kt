package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteGenresResponse(
    val genres: List<RemoteGenre?>?
)

@JsonClass(generateAdapter = true)
data class RemoteGenre(
    val id: String?,
    val name: String?
)