package com.tzel.movieflix.data.user

interface UserDataSource {
    fun rateMovie(movieId: String, rate: Double)
}