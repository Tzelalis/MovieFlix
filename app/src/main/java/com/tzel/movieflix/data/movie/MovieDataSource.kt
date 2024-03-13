package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.model.RemoteMovieResponse

interface MovieDataSource {
    suspend fun getPopularMovies(page: Int): RemoteMovieResponse
}