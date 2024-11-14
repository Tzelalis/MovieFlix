package com.tzel.movieflix.domain.configuration

import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.domain.configuration.entity.TemporaryRequestToken

interface ConfigurationRepository {
    suspend fun getAvailableLanguages(): List<Language>

    suspend fun saveLanguage(language: Language)

    suspend fun getSavedLanguage(): Language?

    suspend fun getBackupLanguage(): Language

    suspend fun createSession(requestToken: String): Boolean

    suspend fun getSavedSessionId(): String?

    suspend fun getTemporaryToken(): TemporaryRequestToken?
}