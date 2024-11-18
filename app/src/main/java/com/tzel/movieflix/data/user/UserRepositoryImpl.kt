package com.tzel.movieflix.data.user

import com.tzel.movieflix.data.core.mapper.RemoteStatusMapper
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val remoteStatusMapper: RemoteStatusMapper
) : UserRepository {
    override suspend fun rateMovie(movieId: String, rating: Double): Boolean {
        return remoteStatusMapper(
            dataSource.rateMovie(
                movieId = movieId,
                rating = RemoteRateMovieRequest(rating)
            )
        )
    }

    override suspend fun addToWatchlist(movieId: String, status: Boolean): Boolean {
        return remoteStatusMapper(dataSource.addToWatchlist(movieId, status))
    }
}