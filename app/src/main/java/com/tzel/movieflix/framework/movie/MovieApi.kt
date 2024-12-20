package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.model.RemoteGenresResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String?
    ): Response<RemoteMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("language") language: String?,
        @Query("append_to_response") includes: String? = null
    ): Response<RemoteMovieDetailsResponse>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun fetchSimilarMovies(@Path("movie_id") movieId: String, @Query("page") page: Int): Response<RemoteMovieResponse>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchMovieReviews(@Path("movie_id") movieId: String, @Query("page") page: Int): Response<RemoteReviewsResponse>

    @GET("/3/genre/movie/list")
    suspend fun fetchGenres(@Query("language") language: String?): Response<RemoteGenresResponse>

    @GET("/3/discover/movie?sort_by=popularity.desc&include_adult=false")
    suspend fun fetchMoviesByGenre(@Query("with_genres") genreId: String, @Query("page") page: Int): Response<RemoteMovieResponse>

    @GET("3/search/movie?include_adult=false")
    suspend fun searchMovies(
        @Query("query") title: String,
        @Query("page") page: Int,
        @Query("language") language: String?
    ): Response<RemoteMovieResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int,
        @Query("language") language: String?
    ): Response<RemoteMovieResponse>

    @GET("/3/trending/movie/{time_window}")
    suspend fun getTrending(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int,
        @Query("language") language: String?
    ): Response<RemoteMovieResponse>
}