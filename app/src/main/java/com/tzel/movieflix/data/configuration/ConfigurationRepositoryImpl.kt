package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.mapper.LanguageToLocalLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.LocalLanguageToLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.LocalTemporaryRequestTokenToTemporaryRequestTokenMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteAvailableLanguagesMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteSessionMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteTemporaryTokenToLocalTemporaryTokenMapper
import com.tzel.movieflix.data.configuration.mapper.TemporaryTokenToLocalTemporaryTokenMapper
import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.domain.configuration.entity.TemporaryRequestToken
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val dataSource: ConfigurationDataSource,
    private val remoteAvailableLanguagesMapper: RemoteAvailableLanguagesMapper,
    private val languageToLocalLanguageMapper: LanguageToLocalLanguageMapper,
    private val localLanguageToLanguageMapper: LocalLanguageToLanguageMapper,
    private val remoteSessionMapper: RemoteSessionMapper,
    private val localTemporaryRequestTokenToTemporaryRequestTokenMapper: LocalTemporaryRequestTokenToTemporaryRequestTokenMapper,
    private val temporaryTokenToLocalTemporaryTokenMapper: TemporaryTokenToLocalTemporaryTokenMapper,
    private val remoteTemporaryTokenToLocalTemporaryTokenMapper: RemoteTemporaryTokenToLocalTemporaryTokenMapper
) : ConfigurationRepository {
    override suspend fun getAvailableLanguages(): List<Language> {
        return remoteAvailableLanguagesMapper(dataSource.getAvailableLanguages())
    }

    override suspend fun saveLanguage(language: Language) {
        dataSource.setSaveLanguage(languageToLocalLanguageMapper(language))
    }

    override suspend fun getSavedLanguage(): Language? {
        return localLanguageToLanguageMapper(dataSource.getSavedLanguage())
    }

    override suspend fun getBackupLanguage(): Language {
        return DEFAULT_LANGUAGE
    }

    override suspend fun createSession(requestToken: String): Boolean {
        val sessionId = remoteSessionMapper(dataSource.createSession(requestToken)) ?: return false

        dataSource.saveSessionId(sessionId)
        dataSource.saveTemporaryToken(null)

        return true
    }

    override suspend fun getSavedSessionId(): String? {
        return dataSource.getSessionId()
    }

    override suspend fun getTemporaryToken(): TemporaryRequestToken? {
        val localToken =  temporaryTokenToLocalTemporaryTokenMapper(dataSource.getTemporaryToken())

        if(localToken != null) { return localToken }

        val remoteToken = dataSource.requestTemporaryRequestToken()
        val localNewToken = remoteTemporaryTokenToLocalTemporaryTokenMapper(remoteToken) ?: return null

        dataSource.saveTemporaryToken(localNewToken)

        return localTemporaryRequestTokenToTemporaryRequestTokenMapper(localNewToken)
    }

    companion object {
        private val DEFAULT_LANGUAGE = Language("en", "English", name = "English")
    }
}