package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int): Response<RemoteMovieResponse>

    @GET("/3/movie/{movie_id}?append_to_response=credits")
    suspend fun fetchMovieDetails(@Path("movie_id") movieId: String): Response<RemoteMovieDetailsResponse>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun fetchSimilarMovies(@Path("movie_id") movieId: String, @Query("page") page: Int): Response<RemoteMovieResponse>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchMovieReviews(@Path("movie_id") movieId: String, @Query("page") page: Int): Response<RemoteReviewsResponse>
}