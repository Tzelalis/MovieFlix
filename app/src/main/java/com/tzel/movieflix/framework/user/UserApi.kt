package com.tzel.movieflix.framework.user

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.data.user.model.RemoteWatchlistRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @POST("/3/movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: String,
        @Body rate: RemoteRateMovieRequest,
    ): Response<RemoteStatusResponse>

    @POST("/3/account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") userId: String,
        @Body watchlist: RemoteWatchlistRequest
    ): Response<RemoteStatusResponse>

    @GET("/3/account/{user_id}/watchlist/movies?sort_by=created_at.desc")
    suspend fun getWatchlist(
        @Path("user_id") userId: String,
        @Query("page") page: Int
    ): Response<RemoteMovieResponse>
}