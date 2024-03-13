package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int): Response<RemoteMovieResponse>
}