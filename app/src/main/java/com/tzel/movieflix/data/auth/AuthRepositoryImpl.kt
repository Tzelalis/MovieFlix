package com.tzel.movieflix.data.auth

import com.tzel.movieflix.domain.auth.AuthException
import com.tzel.movieflix.domain.auth.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun getTemporaryToken(): String? {
        val remoteToken = dataSource.requestTemporaryRequestToken() ?: return null
        if (remoteToken.token == null) return null

        return remoteToken.token
    }

    override suspend fun createAccessToken(requestToken: String): Boolean {
        val accessTokenResponse = dataSource.createAccessToken(requestToken) ?: return false

        if (accessTokenResponse.accessToken == null || accessTokenResponse.accountId == null || accessTokenResponse.success != true) return false
        dataSource.saveAccessTokenAndAccountId(accessTokenResponse.accessToken, accessTokenResponse.accountId)


        val session = dataSource.createSessionId(accessTokenResponse.accessToken)
        if (session?.success != true || session.sessionId == null) return false
        dataSource.saveSessionId(session.sessionId)

        return true
    }

    override suspend fun getUserId(): String {
        return dataSource.getUserId()
    }

    override fun getAuthExceptions(): StateFlow<AuthException?> {
        return dataSource.getAuthExceptions()
    }

    override suspend fun clearAuthExceptions() {
        dataSource.clearAuthExceptions()
    }
}
