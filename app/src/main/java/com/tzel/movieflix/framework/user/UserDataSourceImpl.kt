package com.tzel.movieflix.framework.user

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.data.user.UserDataSource
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.data.user.model.RemoteWatchlistRequest
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.ext.requireNotNull
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: UserApi
) : UserDataSource {
    override suspend fun rateMovie(movieId: String, rating: RemoteRateMovieRequest): RemoteStatusResponse {
        return executeOn.background {
            api.rateMovie(
                movieId = movieId,
                rate = rating
            ).requireNotNull()
        }
    }

    override suspend fun addToWatchlist(
        userId: String,
        movieId: String,
        status: Boolean,
    ): RemoteStatusResponse {
        return executeOn.background {
            api.addToWatchlist(
                userId = userId,
                watchlist = RemoteWatchlistRequest(id = movieId, watchlist = status)
            ).requireNotNull()
        }
    }
}