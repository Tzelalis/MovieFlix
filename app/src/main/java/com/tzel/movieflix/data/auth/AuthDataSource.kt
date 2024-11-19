package com.tzel.movieflix.data.auth

import com.tzel.movieflix.data.auth.model.RemoteAccessTokenResponse
import com.tzel.movieflix.data.auth.model.RemoteSessionResponse
import com.tzel.movieflix.data.auth.model.RemoteTemporaryRequestTokenResponse
import com.tzel.movieflix.domain.auth.AuthException
import kotlinx.coroutines.flow.StateFlow

interface AuthDataSource {
    suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestTokenResponse?

    suspend fun createAccessToken(requestToken: String): RemoteAccessTokenResponse?

    suspend fun createSessionId(accessToken: String): RemoteSessionResponse?

    suspend fun saveAccessTokenAndAccountId(accessToken: String, accountId: String)

    suspend fun saveSessionId(sessionId: String)

    suspend fun getUserId(): String

    fun getAuthExceptions(): StateFlow<AuthException?>

    suspend fun clearAuthExceptions()
}
