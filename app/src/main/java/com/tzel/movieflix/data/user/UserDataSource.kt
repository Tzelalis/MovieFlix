package com.tzel.movieflix.data.user

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest

interface UserDataSource {
    suspend fun rateMovie(movieId: String, rating: RemoteRateMovieRequest): RemoteStatusResponse

    suspend fun addToWatchlist(userId: String, movieId: String, status: Boolean): RemoteStatusResponse
}