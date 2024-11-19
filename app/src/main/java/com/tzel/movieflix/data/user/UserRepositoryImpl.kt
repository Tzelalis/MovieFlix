package com.tzel.movieflix.data.user

import com.tzel.movieflix.data.core.mapper.RemoteStatusMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieMapper
import com.tzel.movieflix.data.user.mapper.RemoteMovieStatesMapper
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.domain.user.entity.MovieStates
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val remoteStatusMapper: RemoteStatusMapper,
    private val remoteMovieMapper: RemoteMovieMapper,
    private val remoteMovieStatesMapper: RemoteMovieStatesMapper
) : UserRepository {
    override suspend fun rateMovie(movieId: String, rating: Double): Boolean? {
        return remoteStatusMapper(
            dataSource.rateMovie(
                movieId = movieId,
                rating = RemoteRateMovieRequest(rating)
            )
        )
    }

    override suspend fun addToWatchlist(userId: String, movieId: String, status: Boolean): Boolean? {
        return remoteStatusMapper(dataSource.addToWatchlist(userId = userId, movieId = movieId, status = status))
    }

    override suspend fun getWatchlist(userId: String, page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getWatchlist(userId = userId, page = page))
    }

    override suspend fun getMovieStates(movieId: String): MovieStates {
        return remoteMovieStatesMapper(dataSource.getMovieStates(movieId = movieId))
    }
}