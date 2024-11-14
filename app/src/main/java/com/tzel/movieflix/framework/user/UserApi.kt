package com.tzel.movieflix.framework.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("/3/movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: String,
        @Body rate: Double, //create body class for this
    )
}