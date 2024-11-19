package com.tzel.movieflix.domain.user.entity

data class MovieStates(
    val id: Int?,
    val favorite: Boolean,
    val watchlist: Boolean,
    val rated: Int?
)
