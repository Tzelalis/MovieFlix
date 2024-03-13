package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.mapper.RemoteMovieMapper
import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val remoteMovieMapper: RemoteMovieMapper
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getPopularMovies(page))
    }
}