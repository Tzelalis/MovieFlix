package com.tzel.movieflix.framework.auth

import com.tzel.movieflix.data.auth.AuthDataSource
import com.tzel.movieflix.data.auth.model.RemoteAccessTokenRequest
import com.tzel.movieflix.data.auth.model.RemoteAccessTokenResponse
import com.tzel.movieflix.data.auth.model.RemoteSessionRequest
import com.tzel.movieflix.data.auth.model.RemoteSessionResponse
import com.tzel.movieflix.data.auth.model.RemoteTemporaryRequestTokenResponse
import com.tzel.movieflix.domain.auth.AuthException
import com.tzel.movieflix.domain.auth.InvalidAccessToken
import com.tzel.movieflix.domain.auth.InvalidRequestToken
import com.tzel.movieflix.domain.auth.InvalidWebToken
import com.tzel.movieflix.domain.auth.NoSessionIdFound
import com.tzel.movieflix.domain.auth.SessionIdIsNotValid
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.ext.requireNotNull
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: AuthApi,
    private val dao: AuthDao,
    private val broadcast: AuthenticationBroadcast
) : AuthDataSource {
    override suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestTokenResponse {
        return executeOn.background {
            val response = api.requestTemporaryRequestToken()

            if (response.body()?.statusCode?.toInt() == INVALID_REQUEST_TOKEN) {
                broadcast.updateData(InvalidRequestToken())
            }

            response.requireNotNull()
        }
    }

    override suspend fun createAccessToken(requestToken: String): RemoteAccessTokenResponse {
        return executeOn.background {
            val response = api.createAccessToken(RemoteAccessTokenRequest(requestToken))

            if (response.body()?.statusCode == INVALID_REQUEST_TOKEN) {
                broadcast.updateData(InvalidWebToken())
            }
            response.requireNotNull()
        }
    }

    override suspend fun saveAccessTokenAndAccountId(accessToken: String, accountId: String) {
        dao.saveAccessTokenAndAccountId(accessToken = accessToken, accoundId = accountId)
    }

    override suspend fun createSessionId(accessToken: String): RemoteSessionResponse {
        return executeOn.background {
            val response = api.createSessionId(RemoteSessionRequest(accessToken))

            if (response.body()?.statusCode == INVALID_ACCESS_TOKEN) {
                broadcast.updateData(InvalidAccessToken())
            }

            response.requireNotNull()
        }
    }

    override suspend fun saveSessionId(sessionId: String) {
        dao.saveSessionId(sessionId)
    }

    override suspend fun getUserId(): String {
        val localUserId = dao.getConfiguration()?.user?.userId

        if (localUserId != null) return localUserId

        val sessionId = dao.getConfiguration()?.user?.sessionId
        if (sessionId == null) {
            broadcast.updateData(NoSessionIdFound())
            throw NoSessionIdFound()
        }

        val response = api.accountDetails(sessionId)
        if (response.body()?.statusCode == NO_PERMISSION) {
            broadcast.updateData(SessionIdIsNotValid())
            throw SessionIdIsNotValid()
        }

        val userId = response.requireNotNull().id ?: throw Exception("User id is null")
        return userId
    }

    override fun getAuthExceptions(): StateFlow<AuthException?> {
        return broadcast.data
    }

    override suspend fun clearAuthExceptions() {
        broadcast.clearData()
    }

    companion object {
        private const val INVALID_REQUEST_TOKEN = 33
        private const val NO_PERMISSION = 3
        private const val INVALID_PARAMETERS = 5
        private const val INVALID_ACCESS_TOKEN = 35
    }
}
