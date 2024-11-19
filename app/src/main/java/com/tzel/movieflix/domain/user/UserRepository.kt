package com.tzel.movieflix.domain.user

import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.user.entity.MovieStates

interface UserRepository {
    suspend fun rateMovie(movieId: String, rating: Double): Boolean?

    suspend fun addToWatchlist(userId: String, movieId: String, status: Boolean): Boolean?

    suspend fun getWatchlist(userId: String, page: Int): MovieResult

    suspend fun getMovieStates(movieId: String): MovieStates
}