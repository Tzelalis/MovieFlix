package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.LocalTemporaryRequestToken
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteSession
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken

interface ConfigurationDataSource {
    suspend fun getAvailableLanguages(): List<RemoteLanguage?>

    suspend fun setSaveLanguage(language: LocalLanguage)

    suspend fun getSavedLanguage(): LocalLanguage?

    suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestToken?

    suspend fun createSession(requestToken: String): RemoteSession?

    suspend fun saveSessionId(sessionId: String)

    suspend fun saveTemporaryToken(tempToken: LocalTemporaryRequestToken?)

    suspend fun getTemporaryToken(): LocalTemporaryRequestToken?

    suspend fun getSessionId(): String?
}