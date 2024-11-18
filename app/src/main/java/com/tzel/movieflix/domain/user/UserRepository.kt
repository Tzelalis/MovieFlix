package com.tzel.movieflix.domain.user

interface UserRepository {
    suspend fun rateMovie(movieId: String, rating: Double): Boolean

    suspend fun addToWatchlist(movieId: String, status: Boolean): Boolean
}