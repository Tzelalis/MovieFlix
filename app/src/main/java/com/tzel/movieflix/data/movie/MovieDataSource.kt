package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse

interface MovieDataSource {
    suspend fun getPopularMovies(page: Int): RemoteMovieResponse

    suspend fun getMovieDetails(movieId: String): RemoteMovieDetailsResponse

    suspend fun getSimilarMovies(movieId: String, page: Int): RemoteMovieResponse

    suspend fun getMovieReviews(movieId: String, page: Int): RemoteReviewsResponse
}