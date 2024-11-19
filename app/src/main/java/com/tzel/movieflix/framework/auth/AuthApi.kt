package com.tzel.movieflix.framework.auth

import com.tzel.movieflix.data.auth.model.RemoteAccessTokenRequest
import com.tzel.movieflix.data.auth.model.RemoteAccessTokenResponse
import com.tzel.movieflix.data.auth.model.RemoteSessionRequest
import com.tzel.movieflix.data.auth.model.RemoteSessionResponse
import com.tzel.movieflix.data.auth.model.RemoteTemporaryRequestTokenResponse
import com.tzel.movieflix.data.configuration.model.RemoteAccountDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/4/auth/request_token")
    suspend fun requestTemporaryRequestToken(): Response<RemoteTemporaryRequestTokenResponse>

    @POST("/4/auth/access_token")
    suspend fun createAccessToken(@Body requestToken: RemoteAccessTokenRequest): Response<RemoteAccessTokenResponse>

    @POST("/3/authentication/session/convert/4")
    suspend fun createSessionId(@Body accessToken: RemoteSessionRequest): Response<RemoteSessionResponse>

    @GET("/3/account")
    suspend fun accountDetails(@Query("session_id") sessionId: String): Response<RemoteAccountDetails>
}
