package com.tzel.movieflix.framework.user

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.data.user.model.RemoteRateMovieRequest
import com.tzel.movieflix.data.user.model.RemoteWatchlistRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("/3/movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: String,
        @Body rate: RemoteRateMovieRequest,
    ): Response<RemoteStatusResponse>

    @POST("/3/account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: String,
        @Body watchlist: RemoteWatchlistRequest
    ): Response<RemoteStatusResponse>
}