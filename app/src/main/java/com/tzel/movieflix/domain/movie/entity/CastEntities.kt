package com.tzel.movieflix.domain.movie.entity

data class Cast(
    val id: String,
    val name: String?,
    val imageUrl: String?
) {
    val profileUrl: String
        get() = "$PROFILE_BASE_URL$id"

    companion object {
        private const val PROFILE_BASE_URL = "https://www.themoviedb.org/person/"
    }
}