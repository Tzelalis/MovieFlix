package com.tzel.movieflix.framework.user

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.user.UserDataSource
import com.tzel.movieflix.data.user.model.RemoteMovieStatesResponse
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.data.user.model.RemoteWatchlistRequest
import com.tzel.movieflix.utils.ext.requireNotNull
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val api: UserApi) : UserDataSource {
    override suspend fun rateMovie(movieId: String, rating: RemoteRateMovieRequest): RemoteStatusResponse {
        return api.rateMovie(
            movieId = movieId,
            rate = rating
        ).requireNotNull()
    }

    override suspend fun addToWatchlist(
        userId: String,
        movieId: String,
        status: Boolean,
    ): RemoteStatusResponse {
        return api.addToWatchlist(
            userId = userId,
            watchlist = RemoteWatchlistRequest(id = movieId, watchlist = status)
        ).requireNotNull()
    }

    override suspend fun getWatchlist(userId: String, page: Int): RemoteMovieResponse {
        return api.getWatchlist(userId = userId, page = page).requireNotNull()
    }

    override suspend fun getMovieStates(movieId: String): RemoteMovieStatesResponse {
        return api.getMovieStates(movieId = movieId).requireNotNull()
    }
}