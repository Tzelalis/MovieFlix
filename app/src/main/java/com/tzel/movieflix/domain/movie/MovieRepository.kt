package com.tzel.movieflix.domain.movie

import com.tzel.movieflix.domain.movie.entity.MovieResult

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): MovieResult
}