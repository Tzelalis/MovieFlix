package com.tzel.movieflix.data.user.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieStatesResponse(
    val id: Int?,
    val favorite: Boolean?,
    val remoteMovieStatesRated: RemoteMovieStatesRated?,
    val watchlist: Boolean?
)

@JsonClass(generateAdapter = true)
data class RemoteMovieStatesRated(val value: Int?)