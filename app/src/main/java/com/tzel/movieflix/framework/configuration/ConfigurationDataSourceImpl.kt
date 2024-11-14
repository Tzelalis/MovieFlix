package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.ConfigurationDataSource
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.LocalTemporaryRequestToken
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteSession
import com.tzel.movieflix.data.configuration.model.RemoteSessionRequest
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.ext.requireNotNull
import javax.inject.Inject

class ConfigurationDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: ConfigurationApi,
    private val dao: ConfigurationDao,
) : ConfigurationDataSource {
    override suspend fun getAvailableLanguages(): List<RemoteLanguage?> {
        return executeOn.background {
            api.getAvailableLanguages().requireNotNull()
        }
    }

    override suspend fun setSaveLanguage(language: LocalLanguage) {
        return executeOn.background {
            dao.safeSaveLanguage(lang = language)
        }
    }

    override suspend fun getSavedLanguage(): LocalLanguage? {
        return dao.getConfiguration()?.language
    }

    override suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestToken {
        return executeOn.background {
            api.requestTemporaryRequestToken().requireNotNull()
        }
    }

    override suspend fun createSession(requestToken: String): RemoteSession {
        return executeOn.background {
            api.createSession(RemoteSessionRequest(requestToken)).requireNotNull()
        }
    }

    override suspend fun saveSessionId(sessionId: String) {
        dao.saveSessionId(sessionId = sessionId)
    }

    override suspend fun saveTemporaryToken(tempToken: LocalTemporaryRequestToken?) {
        dao.saveTemporaryToken(
            tempToken = tempToken?.requestToken,
            expiresAt = tempToken?.expiresAt
        )
    }

    override suspend fun getTemporaryToken(): LocalTemporaryRequestToken? {
        return dao.getConfiguration()?.temporaryToken
    }

    override suspend fun getSessionId(): String? {
        return dao.getConfiguration()?.sessionId
    }
}