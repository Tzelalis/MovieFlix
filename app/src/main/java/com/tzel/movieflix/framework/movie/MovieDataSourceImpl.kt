package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.composable.api.requireNotNull
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: MovieApi
) : MovieDataSource {
    override suspend fun getPopularMovies(page: Int): RemoteMovieResponse {
        return executeOn.background {
            api.fetchPopularMovies(page = page).requireNotNull()
        }
    }
}