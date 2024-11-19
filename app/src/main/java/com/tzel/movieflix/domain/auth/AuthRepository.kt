package com.tzel.movieflix.domain.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    suspend fun getTemporaryToken(): String?

    suspend fun createAccessToken(requestToken: String): Boolean

    suspend fun getUserId(): String

    fun getAuthExceptions(): StateFlow<AuthException?>

    suspend fun clearAuthExceptions()
}
