package com.tzel.movieflix.domain.user

interface UserRepository {
    fun rateMovie(movieId: String, rate: Double)
}